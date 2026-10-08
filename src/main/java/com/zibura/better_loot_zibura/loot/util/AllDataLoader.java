package com.zibura.better_loot_zibura.loot.util;

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
import java.util.Comparator;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public final class AllDataLoader {

    private static final Logger LOGGER = LogUtils.getLogger();

    private AllDataLoader() {}

    public static void loadAllData(ResourceManager manager) {
        ItemUnificationSolver.clear();

        loadUnifications(
                manager,
                "better_loot_zibura/item_unifications/convertible",
                (key, json) -> ItemUnificationSolver.parseAndPut(
                        key, json, ItemUnificationSolver.CONVERTIBLE_MAP
                )
        );

        loadUnifications(
                manager,
                "better_loot_zibura/item_unifications/inconvertible",
                (key, json) -> ItemUnificationSolver.parseAndPut(
                        key, json, ItemUnificationSolver.INCONVERTIBLE_MAP
                )
        );

        ItemUnificationSolver.rebuildAllMap();
//        ConvertibleLootTableGenerator.rebuildConvertibleLootTables();

        // loot_pools references and biome templates are resolved by Python at build time.
        // Only pre-expanded loot_bindings are consumed at runtime.

        LootBindingLoader.clear();

        loadJsonsFromAllMods(
                manager,
                "better_loot_zibura/loot_bindings",
                LootBindingLoader::collectBindings
        );

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
                    }
                });
    }
}