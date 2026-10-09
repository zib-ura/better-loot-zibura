package com.zibura.better_loot_zibura.loot.unification;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.mojang.serialization.JsonOps;
import com.zibura.better_loot_zibura.better_loot_zibura;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Recipe;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 * Minecraft 26.2 + Fabric + Mojang mappings.
 *
 * Generates convertible recipes during RecipeManager#prepare.
 *
 * Vanilla has already parsed normal datapack recipes at this point.
 * Dynamically generated JSON is decoded through Recipe.CODEC before
 * being inserted into the recipe map.
 */
public final class ConvertibleRecipeHandler {

    private ConvertibleRecipeHandler() {
    }

    public static void inject(
            Map<Identifier, Recipe<?>> recipes,
            HolderLookup.Provider registries
    ) {
        Map<String, List<String>> convertibleMap =
                ItemUnificationSolver.CONVERTIBLE_MAP;

        if (convertibleMap == null || convertibleMap.isEmpty()) {
            return;
        }

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

                    if (recipes.containsKey(recipeId)) {
                        continue;
                    }

                    boolean needsBottle =
                            !sourceHasBottle && targetHasBottle;

                    JsonObject json =
                            createRecipeJson(
                                    sourceId,
                                    targetId,
                                    needsBottle
                            );

                    Recipe<?> recipe;

                    try {
                        recipe = Recipe.CODEC
                                .parse(
                                        registries.createSerializationContext(
                                                JsonOps.INSTANCE
                                        ),
                                        json
                                )
                                .getOrThrow(JsonParseException::new);
                    } catch (Exception exception) {
                        better_loot_zibura.LOGGER.error(
                                "Failed to generate convertible recipe {}",
                                recipeId,
                                exception
                        );
                        continue;
                    }

                    recipes.put(recipeId, recipe);
                    generated++;
                }
            }
        }

        if (generated > 0) {
            better_loot_zibura.LOGGER.info(
                    "Injected {} convertible recipes",
                    generated
            );
        }
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

        JsonObject sourceIngredient =
                new JsonObject();

        sourceIngredient.addProperty(
                "item",
                source.toString()
        );

        ingredients.add(sourceIngredient);

        if (needsBottle) {
            JsonObject bottleIngredient =
                    new JsonObject();

            bottleIngredient.addProperty(
                    "item",
                    "minecraft:glass_bottle"
            );

            ingredients.add(bottleIngredient);
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
}