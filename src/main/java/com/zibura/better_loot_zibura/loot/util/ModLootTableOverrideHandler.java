//package com.example.examplemod.loot.util;
//
//import com.google.gson.Gson;
//import com.google.gson.JsonArray;
//import com.google.gson.JsonObject;
//import com.google.gson.JsonParser;
//import com.mojang.logging.LogUtils;
//import net.minecraft.resources.ResourceLocation;
//import net.minecraft.world.level.storage.loot.Deserializers;
//import net.minecraft.world.level.storage.loot.LootTable;
//import net.neoforged.neoforge.event.LootTableLoadEvent;
//import net.neoforged.neoforge.eventbus.api.EventPriority;
//import net.neoforged.neoforge.eventbus.api.SubscribeEvent;
//import net.neoforged.fml.ModList;
//import net.neoforged.fml.common.Mod;
//import net.neoforged.neoforge.forgespi.language.IModFileInfo;
//import org.slf4j.Logger;
//
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.nio.charset.StandardCharsets;
//import java.nio.file.Files;
//import java.nio.file.Path;
//
//@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
//public class ModLootTableOverrideHandler {
//
//    private static final Logger LOGGER = LogUtils.getLogger();
//    private static final String MOD_ID = "examplemod";
//    private static final Gson LOOT_GSON = Deserializers.createLootTableSerializer().create();
//
//    @SubscribeEvent(priority = EventPriority.HIGHEST)
//    public static void onLootTableLoad(LootTableLoadEvent event) {
//        ResourceLocation tableId = event.getName();
//        String targetId = tableId.toString();
//
//        if (!LootTargetTracker.isActive(targetId)) {
//            return;
//        }
//
//        LootTable emptyTable = loadEmptyLootTable(tableId);
//        if (emptyTable != null) {
//            event.setTable(emptyTable);
//            LOGGER.debug("Overrode loot table [{}] with empty template.", targetId);
//        }
//    }
//
//    /**
//     * 设计思路：
//     * 目标路径: data/examplemod/better_loot_zibura/empty_tables/<namespace>/loot_tables/<path>.json
//     * 示例: ctov:chests/village/village_badlands_house
//     * -> 查找: data/examplemod/better_loot_zibura/empty_tables/ctov/loot_tables/chests/village/village_badlands_house.json
//     */
//    private static LootTable loadEmptyLootTable(ResourceLocation tableId) {
//        String targetNamespace = tableId.getNamespace();
//        String targetPath = tableId.getPath();
//
//        String relativePath = "better_loot_zibura/empty_tables/" + targetNamespace + "/loot_tables/" + targetPath + ".json";
//
//        for (IModFileInfo modInfo : ModList.get().getModFiles()) {
//            Path targetFile = modInfo.getFile().findResource("data", MOD_ID, relativePath);
//            if (Files.exists(targetFile)) {
//                try (InputStream in = Files.newInputStream(targetFile);
//                     InputStreamReader reader = new InputStreamReader(in, StandardCharsets.UTF_8)) {
//                    JsonObject json = JsonParser.parseReader(reader).getAsJsonObject();
//
//                    if (json.has("pools") && json.get("pools").isJsonArray()) {
//                        JsonArray pools = json.getAsJsonArray("pools");
//                        for (int i = 0; i < pools.size(); i++) {
//                            if (pools.get(i).isJsonObject() && !pools.get(i).getAsJsonObject().has("name")) {
//                                pools.get(i).getAsJsonObject().addProperty("name", "pool_" + i);
//                            }
//                        }
//                    }
//
//                    return ModLootTableOverrideHandler.LOOT_GSON.fromJson(json, LootTable.class);
//                } catch (Exception e) {
//                    LOGGER.error("Failed to load empty loot table file: {}", targetFile, e);
//                }
//            }
//        }
//
//        return null;
//    }
//}