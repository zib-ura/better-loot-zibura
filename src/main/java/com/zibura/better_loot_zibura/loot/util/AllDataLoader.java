package com.zibura.better_loot_zibura.loot.util;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.logging.LogUtils;
import com.zibura.better_loot_zibura.loot.SpecificLoot.CarpenterLootGenerator;
import com.zibura.better_loot_zibura.loot.SpecificLoot.ShepherdLootGenerator;
import com.zibura.better_loot_zibura.loot.unification.ConvertibleItemsPreviewLootTableGenerator;
import com.zibura.better_loot_zibura.loot.unification.ItemUnificationSolver;
import net.neoforged.fml.ModList;
import net.neoforged.neoforgespi.language.IModFileInfo;
import org.slf4j.Logger;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import static com.zibura.better_loot_zibura.better_loot_zibura.MOD_ID;

public final class AllDataLoader {

    private static final Logger LOGGER = LogUtils.getLogger();

    private AllDataLoader() {}

    public static void loadAllData() {
        ItemUnificationSolver.clear();

        loadUnifications(
                "better_loot_zibura/item_unifications/convertible",
                (key, json) ->
                        ItemUnificationSolver.parseAndPut(
                                key,
                                json,
                                ItemUnificationSolver.CONVERTIBLE_MAP
                        )
        );

        loadUnifications(
                "better_loot_zibura/item_unifications/inconvertible",
                (key, json) ->
                        ItemUnificationSolver.parseAndPut(
                                key,
                                json,
                                ItemUnificationSolver.INCONVERTIBLE_MAP
                        )
        );

        ItemUnificationSolver.rebuildAllMap();

        ConvertibleItemsPreviewLootTableGenerator.rebuildConvertibleLootTables();

        MixedDataResolver.clear();
        InjectFinalPools.clearRegisteredPools();

        loadJsonsFromAllMods(
                "better_loot_zibura/loot_pools",
                element -> {
                    if (element.isJsonObject()) {
                        MixedDataResolver.registerAll(
                                element.getAsJsonObject()
                        );
                    }
                }
        );

        CarpenterLootGenerator.initCarpenterTemplates();
        ShepherdLootGenerator.initShepherdTemplates();

        // 清理缓存后先全部收集（后加载覆盖同名 target），最后统一提交生效
        LootBindingLoader.clear();

        loadJsonsFromAllMods(
                "better_loot_zibura/loot_bindings",
                LootBindingLoader::collectBindings
        );

        LootBindingLoader.commitAllBindings();
    }

    public static void loadUnifications(
            String subFolder,
            BiConsumer<String, JsonObject> consumer
    ) {
        scanJsonFiles(subFolder, (relativePath, jsonElement) -> {
            if (!jsonElement.isJsonObject()) {
                return;
            }

            // relativePath 使用 "/" 作为 Jar 内部路径分隔符
            int slashIndex = relativePath.lastIndexOf('/');
            String fileName = slashIndex >= 0
                    ? relativePath.substring(slashIndex + 1)
                    : relativePath;

            int extensionIndex = fileName.lastIndexOf('.');
            String key = extensionIndex >= 0
                    ? fileName.substring(0, extensionIndex)
                    : fileName;

            consumer.accept(
                    key,
                    jsonElement.getAsJsonObject()
            );
        });
    }

    public static void loadJsonsFromAllMods(
            String subFolder,
            Consumer<JsonElement> jsonConsumer
    ) {
        scanJsonFiles(
                subFolder,
                (relativePath, jsonElement) ->
                        jsonConsumer.accept(jsonElement)
        );
    }

    /**
     * 扫描所有已加载 Mod 中指定目录下的 JSON 文件。
     *
     * <p>这里不再通过 Path / Files.walk() 遍历 Mod Jar，
     * 而是使用新版 NeoForge JarContents API。</p>
     */
    private static void scanJsonFiles(
            String subFolder,
            BiConsumer<String, JsonElement> fileProcessor
    ) {
        String folder = "data/" + MOD_ID + "/" + subFolder;

        for (IModFileInfo modInfo : ModList.get().getModFiles()) {
            var modFile = modInfo.getFile();
            var contents = modFile.getContents();

            try {
                contents.visitContent(
                        folder,
                        (relativePath, resource) -> {
                            if (!relativePath.endsWith(".json")) {
                                return;
                            }

                            try (InputStream in =
                                         contents.openFile(relativePath)) {

                                if (in == null) {
                                    LOGGER.warn(
                                            "Unable to open JSON file [{}] in mod [{}]",
                                            relativePath,
                                            modFile.getId()
                                    );
                                    return;
                                }

                                try (InputStreamReader reader =
                                             new InputStreamReader(
                                                     in,
                                                     StandardCharsets.UTF_8
                                             )) {

                                    JsonElement jsonElement =
                                            JsonParser.parseReader(reader);

                                    if (jsonElement != null) {
                                        fileProcessor.accept(
                                                relativePath,
                                                jsonElement
                                        );
                                    }
                                }

                            } catch (Exception e) {
                                LOGGER.error(
                                        "Failed to parse JSON file [{}] in mod [{}]",
                                        relativePath,
                                        modFile.getId(),
                                        e
                                );
                            }
                        }
                );

            } catch (Exception e) {
                LOGGER.error(
                        "Failed to scan directory [{}] in mod [{}]",
                        folder,
                        modFile.getId(),
                        e
                );
            }
        }
    }
}