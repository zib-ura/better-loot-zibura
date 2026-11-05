package com.zibura.better_loot_zibura.loot.unification;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.zibura.better_loot_zibura.better_loot_zibura;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Items;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

/**
 * Minecraft 26.3 reloadable-recipe adapter.
 *
 * Generates the same conversion JSON as the original 263 handler, then
 * exposes it under the recipe registry's resource directory before decoding.
 * This preserves vanilla/datapack recipes and avoids writing to frozen maps.
 */
public final class ConvertibleRecipeHandler {

    private ConvertibleRecipeHandler() {
    }

    /**
     * Expose generated conversion recipes as virtual data-pack resources for
     * 26.3's reloadable recipe registry, without mutating frozen registries.
     * Existing data-pack entries always take precedence on a key collision.
     */
    public static ResourceManager addGeneratedRecipes(ResourceManager original) {
        Map<Identifier, JsonObject> jsons = generateRecipeJsons();
        if (jsons.isEmpty()) return original;
        return new GeneratedRecipesResourceManager(original, jsons);
    }

    private static Map<Identifier, JsonObject> generateRecipeJsons() {
        Map<String, List<String>> convertibleMap = ItemUnificationSolver.CONVERTIBLE_MAP;
        Map<Identifier, JsonObject> result = new LinkedHashMap<>();
        if (convertibleMap == null || convertibleMap.isEmpty()) return result;

        int generated = 0;

        for (Map.Entry<String, List<String>> entry : convertibleMap.entrySet()) {
            String groupKey = entry.getKey();

            List<Item> validItems =
                    findValidItems(entry.getValue());

            if (validItems.size() < 2) {
                continue;
            }

            for (Item source : validItems) {
                boolean sourceHasBottle =
                        isBottledItem(source);

                for (Item target : validItems) {
                    if (source == target) {
                        continue;
                    }

                    boolean targetHasBottle =
                            isBottledItem(target);

                    /*
                     * If both items already have glass-bottle crafting
                     * remainder semantics, a normal shapeless conversion
                     * would create an unwanted extra bottle.
                     */
                    if (sourceHasBottle && targetHasBottle) {
                        continue;
                    }

                    Identifier sourceId =
                            BuiltInRegistries.ITEM.getKey(source);

                    Identifier targetId =
                            BuiltInRegistries.ITEM.getKey(target);

                    Identifier recipeId =
                            createRecipeId(
                                    groupKey,
                                    sourceId,
                                    targetId
                            );

                    if (result.containsKey(recipeId)) continue;

                    boolean needsBottle = !sourceHasBottle && targetHasBottle;
                    result.put(recipeId, createRecipeJson(sourceId, targetId, needsBottle));
                    generated++;
                }
            }
        }

        if (generated > 0) {
            better_loot_zibura.LOGGER.info(
                    "Prepared {} convertible recipe resources",
                    generated
            );
        }
        return result;
    }

    private static List<Item> findValidItems(
            List<String> configuredItems
    ) {
        List<Item> result = new ArrayList<>();
        Set<Item> seen = new HashSet<>();

        if (configuredItems == null || configuredItems.isEmpty()) {
            return result;
        }

        for (String rawId : configuredItems) {
            if (rawId == null || rawId.isBlank()) {
                continue;
            }

            Identifier id =
                    Identifier.tryParse(rawId);

            if (id == null) {
                better_loot_zibura.LOGGER.warn(
                        "Invalid convertible item identifier: {}",
                        rawId
                );
                continue;
            }

            if (!BuiltInRegistries.ITEM.containsKey(id)) {
                continue;
            }

            Item item =
                    BuiltInRegistries.ITEM.getValue(id);

            if (item == null || item == Items.AIR) {
                continue;
            }

            if (seen.add(item)) {
                result.add(item);
            }
        }

        return result;
    }

    private static boolean isBottledItem(Item item) {
        ItemStackTemplate remainder =
                item.getCraftingRemainder();

        return remainder != null
                && remainder.item().value() == Items.GLASS_BOTTLE;
    }

    private static JsonObject createRecipeJson(
            Identifier source,
            Identifier target,
            boolean needsBottle
    ) {
        JsonObject recipe = new JsonObject();

        recipe.addProperty(
                "type",
                "minecraft:crafting_shapeless"
        );

        JsonArray ingredients = new JsonArray();

        // Minecraft 26.3 ingredients use item ID strings, not {"item": "..."} objects.
        ingredients.add(source.toString());

        if (needsBottle) {
            ingredients.add("minecraft:glass_bottle");
        }

        recipe.add(
                "ingredients",
                ingredients
        );

        JsonObject result =
                new JsonObject();

        result.addProperty(
                "id",
                target.toString()
        );

        result.addProperty(
                "count",
                1
        );

        recipe.add(
                "result",
                result
        );

        return recipe;
    }

    private static Identifier createRecipeId(
            String groupKey,
            Identifier source,
            Identifier target
    ) {
        String path =
                "convertible/"
                        + sanitizeGroupKey(groupKey)
                        + "/"
                        + source.getNamespace()
                        + "/"
                        + source.getPath()
                        + "/to/"
                        + target.getNamespace()
                        + "/"
                        + target.getPath();

        return Identifier.fromNamespaceAndPath(
                better_loot_zibura.MOD_ID,
                path
        );
    }

    private static String sanitizeGroupKey(
            String value
    ) {
        if (value == null || value.isBlank()) {
            return "unknown";
        }

        return value
                .toLowerCase(Locale.ROOT)
                .replaceAll(
                        "[^a-z0-9_.-]",
                        "_"
                );
    }
    /** Delegating ResourceManager; only the recipe directory is supplemented. */
    private static final class GeneratedRecipesResourceManager implements ResourceManager {
        private final ResourceManager delegate;
        private final Map<Identifier, Resource> generated;
        private final String recipeDirectory = Registries.elementsDirPath(Registries.RECIPE);

        GeneratedRecipesResourceManager(ResourceManager delegate, Map<Identifier, JsonObject> recipes) {
            this.delegate = delegate;
            PackResources source = delegate.listPacks().findFirst().orElseThrow(
                    () -> new IllegalStateException("No server data pack to source generated recipes")
            );
            Map<Identifier, Resource> resources = new LinkedHashMap<>();
            for (Map.Entry<Identifier, JsonObject> recipe : recipes.entrySet()) {
                // FileToIdConverter.registry(Registries.RECIPE) uses this exact path.
                Identifier file = recipe.getKey().withPath(
                        recipeDirectory + "/" + recipe.getKey().getPath() + ".json"
                );
                byte[] jsonBytes = recipe.getValue().toString().getBytes(StandardCharsets.UTF_8);
                resources.put(file, new Resource(source, () -> new ByteArrayInputStream(jsonBytes)));
            }
            this.generated = Collections.unmodifiableMap(resources);
        }

        @Override
        public java.util.Set<String> getNamespaces() {
            return delegate.getNamespaces();
        }

        @Override
        public Optional<Resource> getResource(Identifier id) {
            Optional<Resource> current = delegate.getResource(id);
            return current.isPresent() ? current : Optional.ofNullable(generated.get(id));
        }

        @Override
        public List<Resource> getResourceStack(Identifier id) {
            List<Resource> existing = delegate.getResourceStack(id);
            Resource extra = generated.get(id);
            return existing.isEmpty() && extra != null ? List.of(extra) : existing;
        }

        @Override
        public Map<Identifier, Resource> listResources(String directory, Selector selector) {
            Map<Identifier, Resource> original = delegate.listResources(directory, selector);
            if (!recipeDirectory.equals(directory)) return original;
            Map<Identifier, Resource> merged = new LinkedHashMap<>(original);
            generated.forEach((id, resource) -> {
                if (selector.isIncluded(id)) merged.putIfAbsent(id, resource);
            });
            return merged;
        }

        @Override
        public Map<Identifier, List<Resource>> listResourceStacks(String directory, Selector selector) {
            Map<Identifier, List<Resource>> original = delegate.listResourceStacks(directory, selector);
            if (!recipeDirectory.equals(directory)) return original;
            Map<Identifier, List<Resource>> merged = new LinkedHashMap<>(original);
            generated.forEach((id, resource) -> {
                if (selector.isIncluded(id)) merged.putIfAbsent(id, List.of(resource));
            });
            return merged;
        }

        @Override
        public Stream<PackResources> listPacks() {
            return delegate.listPacks();
        }
    }
}