//package com.zibura.better_loot_zibura.loot.util;
//
//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;
//import com.google.gson.JsonElement;
//import com.zibura.better_loot_zibura.loot.SpecificLoot.CarpenterLootGenerator;
//import com.zibura.better_loot_zibura.loot.SpecificLoot.SeedBagLootGenerator;
//import com.zibura.better_loot_zibura.loot.SpecificLoot.ShepherdLootGenerator;
//import net.minecraft.resources.ResourceLocation;
//import net.minecraft.server.packs.resources.ResourceManager;
//import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
//import net.minecraft.util.profiling.ProfilerFiller;
//
//import java.util.Map;
//
//public class LootDataLoader extends SimpleJsonResourceReloadListener {
//    private static final Gson GSON = new GsonBuilder().create();
//
//    public LootDataLoader() {
//        super(GSON, "better_loot_zibura/loot_pools");
//    }
//
//    @Override
//    protected void apply(Map<ResourceLocation, JsonElement> jsonMap, ResourceManager rm, ProfilerFiller profiler) {
////        LootTargetTracker.clear();
//
//        LootEvaluationContext.clear();
//        InjectFinalPools.clearRegisteredPools();
//
//        jsonMap.forEach((location, jsonElement) -> {
//            if (jsonElement.isJsonObject()) {
//                LootEvaluationContext.registerAll(jsonElement.getAsJsonObject());
//            }
//        });
//        // 初始化木匠与牧羊人动态内容
//        CarpenterLootGenerator.initCarpenterTemplates();
//        ShepherdLootGenerator.initShepherdTemplates();
//        SeedBagLootGenerator.initSeedBagTemplates();
//
//    }
//}