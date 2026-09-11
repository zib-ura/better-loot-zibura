package com.zibura.better_loot_zibura.loot.util;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.logging.LogUtils;
import com.zibura.better_loot_zibura.loot.SpecificLoot.CarpenterLootGenerator;
import com.zibura.better_loot_zibura.loot.SpecificLoot.ShepherdLootGenerator;
import com.zibura.better_loot_zibura.loot.unification.ConvertibleLootTableGenerator;
import com.zibura.better_loot_zibura.loot.unification.ItemUnificationSolver;
import net.neoforged.fml.ModList;
import net.neoforged.neoforgespi.language.IModFileInfo;
import org.slf4j.Logger;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import static com.zibura.better_loot_zibura.better_loot_zibura.MOD_ID;

public final class AllDataLoader {

    private static final Logger LOGGER = LogUtils.getLogger();

    private AllDataLoader() {}

    public static void loadAllData() {
        ItemUnificationSolver.clear();
        loadUnifications("better_loot_zibura/item_unifications/convertible", (key, json) ->
                ItemUnificationSolver.parseAndPut(key, json, ItemUnificationSolver.CONVERTIBLE_MAP)
        );
        loadUnifications("better_loot_zibura/item_unifications/inconvertible", (key, json) ->
                ItemUnificationSolver.parseAndPut(key, json, ItemUnificationSolver.INCONVERTIBLE_MAP)
        );
        ItemUnificationSolver.rebuildAllMap();

        ConvertibleLootTableGenerator.rebuildConvertibleLootTables();

        LootEvaluationContext.clear();
        InjectFinalPools.clearRegisteredPools();

        loadJsonsFromAllMods("better_loot_zibura/loot_pools", element -> {
            if (element.isJsonObject()) {
                LootEvaluationContext.registerAll(element.getAsJsonObject());
            }
        });

        CarpenterLootGenerator.initCarpenterTemplates();
        ShepherdLootGenerator.initShepherdTemplates();

        // 清理缓存后先全部收集（后加载覆盖同名 target），最后统一提交生效
        LootBindingLoader.clear();
        loadJsonsFromAllMods("better_loot_zibura/loot_bindings", LootBindingLoader::collectBindings);
        LootBindingLoader.commitAllBindings();
    }

    public static void loadUnifications(String subFolder, BiConsumer<String, JsonObject> consumer) {
        scanJsonFiles(subFolder, (path, jsonElement) -> {
            if (jsonElement.isJsonObject()) {
                String fileName = path.getFileName().toString();
                String key = fileName.substring(0, fileName.lastIndexOf('.'));
                consumer.accept(key, jsonElement.getAsJsonObject());
            }
        });
    }

    public static void loadJsonsFromAllMods(String subFolder, Consumer<JsonElement> jsonConsumer) {
        scanJsonFiles(subFolder, (path, jsonElement) -> jsonConsumer.accept(jsonElement));
    }

    /**
     * 通用 JSON 扫描与解析入口
     */
    private static void scanJsonFiles(String subFolder, BiConsumer<Path, JsonElement> fileProcessor) {
        for (IModFileInfo modInfo : ModList.get().getModFiles()) {
            Path dir = modInfo.getFile().findResource("data", MOD_ID, subFolder);
            if (dir == null || !Files.exists(dir) || !Files.isDirectory(dir)) continue;

            try (var stream = Files.walk(dir)) {
                stream.filter(p -> p.toString().endsWith(".json")).forEach(jsonPath -> {
                    try (InputStream in = Files.newInputStream(jsonPath);
                         InputStreamReader reader = new InputStreamReader(in, StandardCharsets.UTF_8)) {
                        JsonElement jsonElement = JsonParser.parseReader(reader);
                        if (jsonElement != null) {
                            fileProcessor.accept(jsonPath, jsonElement);
                        }
                    } catch (Exception e) {
                        LOGGER.error("Failed to parse JSON file: {}", jsonPath, e);
                    }
                });
            } catch (Exception e) {
                LOGGER.error("Failed to walk directory [{}] in mod [{}]", dir, modInfo.moduleName(), e);
            }
        }
    }
}