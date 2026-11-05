package com.zibura.better_loot_zibura.loot.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import java.util.Map.Entry;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.logging.LogUtils;
import com.zibura.better_loot_zibura.loot.unification.ItemUnificationSolver;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.resources.Identifier;
import org.slf4j.Logger;

import java.io.Reader;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;


public final class AllDataLoader {

    private static final Logger LOGGER = LogUtils.getLogger();

    private AllDataLoader() {}

    public static void loadAllData(ResourceManager manager) {
        LOGGER.info("[BetterLoot] Reloading custom data from the active server resource packs");
        ItemUnificationSolver.clear();

        loadUnifications(
                manager,
                "better_loot_zibura/item_unifications/convertible",
                (key, json) -> ItemUnificationSolver.parseAndPut(
                        key,
                        json,
                        ItemUnificationSolver.CONVERTIBLE_MAP
                )
        );

        loadUnifications(
                manager,
                "better_loot_zibura/item_unifications/inconvertible",
                (key, json) -> ItemUnificationSolver.parseAndPut(
                        key,
                        json,
                        ItemUnificationSolver.INCONVERTIBLE_MAP
                )
        );

        ItemUnificationSolver.rebuildAllMap();

        // Keep Forge's behavior: merge every raw loot_pools registry before
        // compiling explicit template/output roots. Do NOT change the directory.
        JsonObject rawRegistry = new JsonObject();
        scanJsonFiles(manager, "better_loot_zibura/loot_pools", (id, element) -> {
            if (!element.isJsonObject()) {
                throw new IllegalArgumentException("Expected pool registry object: " + id);
            }
            for (Entry<String, JsonElement> entry : element.getAsJsonObject().entrySet()) {
                // Forge policy: a later resource overrides an earlier registry key.
                rawRegistry.add(entry.getKey(), entry.getValue().deepCopy());
            }
        });

        // Expand templates and Matrix DSL before publishing the evaluation registry.
        // Reference linking is a separate subsequent step.
        JsonObject expandedRegistry = LootRecursiveTemplateExpander.expand(rawRegistry);
        // Match the Forge pipeline: recursive templates first, then Matrix DSL.
        JsonObject matrixRegistry = LootMatrixExpander.expand(expandedRegistry);
        LOGGER.info("[BetterLoot] Template/Matrix expansion: {} raw keys -> {} template keys -> {} matrix keys",
                rawRegistry.size(), expandedRegistry.size(), matrixRegistry.size());

        // Link only against the compiled registry, exactly as in Forge.
        // No runtime registry context or excluded special-generator entry points.
        try {
            LootReferenceResolver resolver = new LootReferenceResolver(matrixRegistry);
            Map<String, JsonObject> compiled = new LinkedHashMap<>();
            scanJsonFiles(manager, "better_loot_zibura/loot_bindings", (id, element) -> {
                final JsonArray expanded;
                try {
                    expanded = resolver.link(element);
                } catch (IllegalArgumentException ex) {
                    LOGGER.error("[BetterLoot] Compilation error in binding resource {}", id, ex);
                    throw ex;
                }
                for (JsonElement entry : expanded) {
                    JsonObject binding = entry.getAsJsonObject();
                    JsonElement target = binding.get("target");
                    if (target == null || !target.isJsonPrimitive()
                            || !target.getAsJsonPrimitive().isString()) {
                        throw new IllegalArgumentException("Invalid binding target in " + id);
                    }
                    compiled.put(target.getAsString(), binding);
                }
            });

            // The old live bindings remain unchanged if compilation fails.
            LootBindingLoader.replaceBindings(compiled);
            LOGGER.info("[BetterLoot] Compiled and published {} loot bindings", compiled.size());
        } catch (Exception ex) {
            LOGGER.error("[BetterLoot] Loot compilation failed; previous bindings retained", ex);
            throw new IllegalStateException("BetterLoot loot compilation failed", ex);
        }
    }

    public static void loadUnifications(
            ResourceManager manager,
            String subFolder,
            BiConsumer<String, JsonObject> consumer
    ) {
        scanJsonFiles(manager, subFolder, (id, element) -> {
            if (element.isJsonObject()) {
                String path = id.getPath();
                String fileName = path.substring(path.lastIndexOf('/') + 1);
                String key = fileName.substring(0, fileName.length() - ".json".length());
                consumer.accept(key, element.getAsJsonObject());
            }
        });
    }

    public static void loadJsonsFromAllMods(
            ResourceManager manager,
            String subFolder,
            Consumer<JsonElement> jsonConsumer
    ) {
        scanJsonFiles(manager, subFolder, (id, element) -> jsonConsumer.accept(element));
    }

    /**
     * Read from the current SERVER_DATA ResourceManager, not directly from mod JARs.
     *
     * Keep subFolder unchanged, including its intentional better_loot_zibura prefix.
     * E.g. data/<namespace>/better_loot_zibura/loot_pools/*.json.
     * ResourceManager provides the winning resource for duplicate identifiers.
     */
    private static void scanJsonFiles(
            ResourceManager manager,
            String subFolder,
            BiConsumer<Identifier, JsonElement> fileProcessor
    ) {
        Map<Identifier, Resource> resources = manager.listResources(
                subFolder,
                id -> id.getPath().endsWith(".json")
        );
        LOGGER.info("[BetterLoot] Scanning {}: {} JSON resources", subFolder, resources.size());

        resources.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(entry -> {
                    Identifier id = entry.getKey();
                    try (Reader reader = entry.getValue().openAsReader()) {
                        JsonElement element = JsonParser.parseReader(reader);
                        if (element != null) {
                            fileProcessor.accept(id, element);
                        }
                    } catch (Exception e) {
                        LOGGER.error("[BetterLoot] Failed to load JSON: {}", id, e);
                        throw new IllegalStateException("Failed to load resource " + id, e);
                    }
                });
    }
}
