//package com.zibura.better_loot_zibura.loot.util;
//
//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;
//import com.google.gson.JsonElement;
//import com.google.gson.JsonObject;
//import net.minecraft.resources.ResourceLocation;
//import net.minecraft.server.packs.resources.ResourceManager;
//import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
//import net.minecraft.util.profiling.ProfilerFiller;
//import net.minecraftforge.event.AddReloadListenerEvent;
//import net.minecraftforge.eventbus.api.SubscribeEvent;
//import net.minecraftforge.fml.common.Mod;
//
//import java.util.Map;
//
//public class ItemUnificationLoader extends SimpleJsonResourceReloadListener {
//
//    private static final Gson GSON = new GsonBuilder().create();
//
//    public ItemUnificationLoader() {
//        super(GSON, "better_loot_zibura/item_unifications");
//    }
//
//
//    @Override
//    protected void apply(Map<ResourceLocation, JsonElement> jsonMap, ResourceManager rm, ProfilerFiller profiler) {
//        ItemUnificationRegistry.clear();
//
//        jsonMap.forEach((location, jsonElement) -> {
//            if (!jsonElement.isJsonObject()) return;
//            JsonObject obj = jsonElement.getAsJsonObject();
//
//            // 示例 path: convertible/crop/tomato -> key 为 tomato
//            String path = location.getPath();
//            String fileName = path.substring(path.lastIndexOf('/') + 1);
//
//            if (path.startsWith("convertible/")) {
//                ItemUnificationRegistry.parseAndPut(fileName, obj, ItemUnificationRegistry.CONVERTIBLE_MAP);
//            } else if (path.startsWith("inconvertible/")) {
//                ItemUnificationRegistry.parseAndPut(fileName, obj, ItemUnificationRegistry.INCONVERTIBLE_MAP);
//            }
//        });
//
//        ItemUnificationRegistry.rebuildAllMap();
//        ConvertibleLootTableGenerator.rebuildConvertibleLootTables();
//    }
//}