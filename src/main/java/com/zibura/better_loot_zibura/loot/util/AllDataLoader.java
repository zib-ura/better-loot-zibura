//package com.zibura.better_loot_zibura.loot.util;
//
//import com.google.gson.JsonArray;
//import com.google.gson.JsonElement;
//import com.google.gson.JsonObject;
//import com.google.gson.JsonParser;
//import com.mojang.logging.LogUtils;
////import com.zibura.better_loot_zibura.loot.unification.ConvertibleLootTableGenerator;
//import com.zibura.better_loot_zibura.loot.unification.ItemUnificationSolver;
//import net.minecraft.resources.ResourceLocation;
//import net.minecraft.server.packs.resources.Resource;
//import net.minecraft.server.packs.resources.ResourceManager;
//import org.slf4j.Logger;
//
//import java.io.Reader;
//import java.util.Comparator;
//import java.util.LinkedHashMap;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//import java.util.function.BiConsumer;
//import java.util.function.Consumer;
//
//public final class AllDataLoader {
//
//    private static final Logger LOGGER = LogUtils.getLogger();
//
//    private AllDataLoader() {}
//
//    /** Compile first; publish bindings only after the whole compilation succeeds. */
//    public static void loadAllData(ResourceManager manager) {
//        try {
//            JsonObject registry = new JsonObject();
//            scanJsonFiles(manager, "better_loot_zibura/loot_pools", (id, element) -> {
//                if (!element.isJsonObject()) {
//                    throw new IllegalArgumentException("Expected pool registry object: " + id);
//                }
//                for (Map.Entry<String, JsonElement> entry : element.getAsJsonObject().entrySet()) {
//                    // Temporary policy: later scanned entries replace earlier ones.
//                    registry.add(entry.getKey(), entry.getValue().deepCopy());
//                }
//            });
//
//            JsonObject merged = LootReferenceResolver.withGeneratedTemplates(registry);
//            LootReferenceResolver resolver = new LootReferenceResolver(merged);
//            Map<String, JsonObject> compiled = new LinkedHashMap<>();
//            scanJsonFiles(manager, "better_loot_zibura/loot_bindings", (id, element) -> {
//                JsonArray expanded = resolver.link(element);
//                for (JsonElement entry : expanded) {
//                    JsonObject binding = entry.getAsJsonObject();
//                    JsonElement targetElement = binding.get("target");
//                    if (targetElement == null || !targetElement.isJsonPrimitive()
//                            || !targetElement.getAsJsonPrimitive().isString()) {
//                        throw new IllegalArgumentException("Invalid target in " + id);
//                    }
//                    compiled.put(targetElement.getAsString(), binding);
//                }
//            });
//
//            // Do not clear the live map before successful compilation.
//            LootBindingLoader.replaceBindings(compiled);
//            LOGGER.info("[BetterLoot] Compiled {} loot bindings", compiled.size());
//        } catch (Exception ex) {
//            LOGGER.error("[BetterLoot] Loot compilation failed; previous bindings retained", ex);
//            // Intentionally fail reload rather than silently loading an inconsistent snapshot.
//            throw new IllegalStateException("BetterLoot loot compilation failed", ex);
//        }
//
//        ItemUnificationSolver.clear();
//        loadUnifications(manager, "better_loot_zibura/item_unifications/convertible",
//                (key, json) -> ItemUnificationSolver.parseAndPut(
//                        key, json, ItemUnificationSolver.CONVERTIBLE_MAP));
//        loadUnifications(manager, "better_loot_zibura/item_unifications/inconvertible",
//                (key, json) -> ItemUnificationSolver.parseAndPut(
//                        key, json, ItemUnificationSolver.INCONVERTIBLE_MAP));
//        ItemUnificationSolver.rebuildAllMap();
//        LOGGER.info("[BetterLoot] ResourceManager data loading completed");
//    }
//
//    public static void loadUnifications(
//            ResourceManager manager,
//            String subFolder,
//            BiConsumer<String, JsonObject> consumer
//    ) {
//        scanJsonFiles(manager, subFolder, (id, jsonElement) -> {
//            if (jsonElement.isJsonObject()) {
//                String path = id.getPath();
//                String fileName = path.substring(path.lastIndexOf('/') + 1);
//                String key = fileName.substring(0, fileName.length() - 5);
//
//                consumer.accept(key, jsonElement.getAsJsonObject());
//            }
//        });
//    }
//
//    public static void loadJsonsFromAllMods(
//            ResourceManager manager,
//            String subFolder,
//            Consumer<JsonElement> jsonConsumer
//    ) {
//        scanJsonFiles(
//                manager,
//                subFolder,
//                (id, jsonElement) -> jsonConsumer.accept(jsonElement)
//        );
//    }
//
//    private static void scanJsonFiles(
//            ResourceManager manager,
//            String subFolder,
//            BiConsumer<ResourceLocation, JsonElement> fileProcessor
//    ) {
//        Map<ResourceLocation, Resource> resources =
//                manager.listResources(
//                        subFolder,
//                        id -> id.getPath().endsWith(".json")
//                );
//
//        resources.entrySet().stream()
//                .sorted(Map.Entry.comparingByKey())
//                .forEach(entry -> {
//                    ResourceLocation id = entry.getKey();
//
//                    try (Reader reader = entry.getValue().openAsReader()) {
//                        JsonElement jsonElement = JsonParser.parseReader(reader);
//
//                        if (jsonElement != null) {
//                            fileProcessor.accept(id, jsonElement);
//                        }
//                    } catch (Exception e) {
//                        LOGGER.error(
//                                "[BetterLoot] Failed to load JSON: {}",
//                                id,
//                                e
//                        );
//                        throw new IllegalStateException("Failed to load resource " + id, e);
//                    }
//                });
//    }
//}


package com.zibura.better_loot_zibura.loot.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.logging.LogUtils;
//import com.zibura.better_loot_zibura.loot.unification.ConvertibleLootTableGenerator;
import com.zibura.better_loot_zibura.loot.unification.ItemUnificationSolver;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import org.slf4j.Logger;

import java.io.Reader;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public final class AllDataLoader {

    private static final Logger LOGGER = LogUtils.getLogger();

    private AllDataLoader() {}

    /** Compile first; publish bindings only after the whole compilation succeeds. */
    public static void loadAllData(ResourceManager manager) {
        try {
            JsonObject registry = new JsonObject();
            Map<String, ResourceLocation> poolSources = new LinkedHashMap<>();
            scanJsonFiles(manager, "better_loot_zibura/loot_pools", (id, element) -> {
                if (!element.isJsonObject()) {
                    throw new IllegalArgumentException("Expected pool registry object: " + id);
                }
                for (Map.Entry<String, JsonElement> entry : element.getAsJsonObject().entrySet()) {
                    // Temporary policy: later scanned entries replace earlier ones.
                    registry.add(entry.getKey(), entry.getValue().deepCopy());
                    poolSources.put(entry.getKey(), id);
                }
            });

            LOGGER.info("[BetterLoot] Loaded {} raw loot pool keys from {} files", registry.size(),
                    poolSources.values().stream().distinct().count());
            LOGGER.info("[BetterLoot] CTOV bakery key present: {} (source: {})",
                    registry.has("loot_ctov_village_bakery"), poolSources.get("loot_ctov_village_bakery"));
            JsonObject merged = LootReferenceResolver.withGeneratedTemplates(registry);
            LootReferenceResolver resolver = new LootReferenceResolver(merged);
            Map<String, JsonObject> compiled = new LinkedHashMap<>();
            scanJsonFiles(manager, "better_loot_zibura/loot_bindings", (id, element) -> {
                JsonArray expanded;
                try {
                    expanded = resolver.link(element);
                } catch (IllegalArgumentException ex) {
                    LOGGER.error("[BetterLoot] Compilation error in binding {}. Available raw pool keys ({}): {}",
                            id, registry.size(), registry.keySet(), ex);
                    throw ex;
                }
                for (JsonElement entry : expanded) {
                    JsonObject binding = entry.getAsJsonObject();
                    JsonElement targetElement = binding.get("target");
                    if (targetElement == null || !targetElement.isJsonPrimitive()
                            || !targetElement.getAsJsonPrimitive().isString()) {
                        throw new IllegalArgumentException("Invalid target in " + id);
                    }
                    compiled.put(targetElement.getAsString(), binding);
                }
            });

            // Do not clear the live map before successful compilation.
            LootBindingLoader.replaceBindings(compiled);
            LOGGER.info("[BetterLoot] Compiled {} loot bindings", compiled.size());
        } catch (Exception ex) {
            LOGGER.error("[BetterLoot] Loot compilation failed; previous bindings retained", ex);
            // Intentionally fail reload rather than silently loading an inconsistent snapshot.
            throw new IllegalStateException("BetterLoot loot compilation failed", ex);
        }

        ItemUnificationSolver.clear();
        loadUnifications(manager, "better_loot_zibura/item_unifications/convertible",
                (key, json) -> ItemUnificationSolver.parseAndPut(
                        key, json, ItemUnificationSolver.CONVERTIBLE_MAP));
        loadUnifications(manager, "better_loot_zibura/item_unifications/inconvertible",
                (key, json) -> ItemUnificationSolver.parseAndPut(
                        key, json, ItemUnificationSolver.INCONVERTIBLE_MAP));
        ItemUnificationSolver.rebuildAllMap();
        LOGGER.info("[BetterLoot] ResourceManager data loading completed");
    }

    public static void loadUnifications(
            ResourceManager manager,
            String subFolder,
            BiConsumer<String, JsonObject> consumer
    ) {
        scanJsonFiles(manager, subFolder, (id, jsonElement) -> {
            if (jsonElement.isJsonObject()) {
                String path = id.getPath();
                String fileName = path.substring(path.lastIndexOf('/') + 1);
                String key = fileName.substring(0, fileName.length() - 5);

                consumer.accept(key, jsonElement.getAsJsonObject());
            }
        });
    }

    public static void loadJsonsFromAllMods(
            ResourceManager manager,
            String subFolder,
            Consumer<JsonElement> jsonConsumer
    ) {
        scanJsonFiles(
                manager,
                subFolder,
                (id, jsonElement) -> jsonConsumer.accept(jsonElement)
        );
    }

    private static void scanJsonFiles(
            ResourceManager manager,
            String subFolder,
            BiConsumer<ResourceLocation, JsonElement> fileProcessor
    ) {
        Map<ResourceLocation, Resource> resources =
                manager.listResources(
                        subFolder,
                        id -> id.getPath().endsWith(".json")
                );

        LOGGER.info("[BetterLoot] Scanning {}: {} JSON resources", subFolder, resources.size());
        if (subFolder.equals("better_loot_zibura/loot_pools")) {
            resources.keySet().stream().sorted().forEach(id -> LOGGER.info("[BetterLoot] Pool file: {}", id));
        }
        resources.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(entry -> {
                    ResourceLocation id = entry.getKey();

                    try (Reader reader = entry.getValue().openAsReader()) {
                        JsonElement jsonElement = JsonParser.parseReader(reader);

                        if (jsonElement != null) {
                            fileProcessor.accept(id, jsonElement);
                        }
                    } catch (Exception e) {
                        LOGGER.error(
                                "[BetterLoot] Failed to load JSON: {}",
                                id,
                                e
                        );
                        throw new IllegalStateException("Failed to load resource " + id, e);
                    }
                });
    }
}