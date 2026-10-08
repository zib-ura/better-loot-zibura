package com.zibura.better_loot_zibura.loot.util;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.logging.LogUtils;
import com.zibura.better_loot_zibura.loot.unification.ItemUnificationSolver;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import org.slf4j.Logger;

import java.io.Reader;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public final class AllDataLoader {
    private static final Logger LOGGER = LogUtils.getLogger();

    private AllDataLoader() {}


    private static volatile RegistryAccess.Frozen currentRegistries;

    public static void setRegistryAccess(RegistryAccess.Frozen registries) {
        currentRegistries = registries;
    }

    public static RegistryAccess.Frozen getRegistryAccess() {
        return currentRegistries;
    }
    public static void loadAllData(ResourceManager manager) {
        ItemUnificationSolver.clear();
        loadUnifications(manager, "better_loot_zibura/item_unifications/convertible",
                (key, json) -> ItemUnificationSolver.parseAndPut(
                        key, json, ItemUnificationSolver.CONVERTIBLE_MAP));
        loadUnifications(manager, "better_loot_zibura/item_unifications/inconvertible",
                (key, json) -> ItemUnificationSolver.parseAndPut(
                        key, json, ItemUnificationSolver.INCONVERTIBLE_MAP));
        ItemUnificationSolver.rebuildAllMap();

        // loot_pools 和 biome 模板在构建阶段预展开；运行时只读取 loot_bindings。
        LootBindingLoader.clear();
        loadJsonsFromAllMods(manager, "better_loot_zibura/loot_bindings",
                LootBindingLoader::collectBindings);

        LOGGER.info("[BetterLoot] ResourceManager data loading completed");
    }

    public static void loadUnifications(ResourceManager manager, String subFolder,
                                        BiConsumer<String, JsonObject> consumer) {
        scanJsonFiles(manager, subFolder, (id, jsonElement) -> {
            if (jsonElement.isJsonObject()) {
                String path = id.getPath();
                String fileName = path.substring(path.lastIndexOf('/') + 1);
                String key = fileName.substring(0, fileName.length() - 5);
                consumer.accept(key, jsonElement.getAsJsonObject());
            }
        });
    }

    public static void loadJsonsFromAllMods(ResourceManager manager, String subFolder,
                                            Consumer<JsonElement> jsonConsumer) {
        scanJsonFiles(manager, subFolder, (id, element) -> jsonConsumer.accept(element));
    }

    private static void scanJsonFiles(ResourceManager manager, String subFolder,
                                      BiConsumer<ResourceLocation, JsonElement> fileProcessor) {
        Map<ResourceLocation, Resource> resources = manager.listResources(
                subFolder, id -> id.getPath().endsWith(".json"));
        resources.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(entry -> {
                    ResourceLocation id = entry.getKey();
                    try (Reader reader = entry.getValue().openAsReader()) {
                        JsonElement element = JsonParser.parseReader(reader);
                        if (element != null) fileProcessor.accept(id, element);
                    } catch (Exception e) {
                        LOGGER.error("[BetterLoot] Failed to load JSON: {}", id, e);
                    }
                });
    }
}
