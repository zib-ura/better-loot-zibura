//package com.zibura.better_loot_zibura.loot.util;
//
//import net.minecraft.server.packs.resources.ResourceManager;
//import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
//
//public class BetterLootReloadListener implements ResourceManagerReloadListener {
//
//    @Override
//    public void onResourceManagerReload(ResourceManager resourceManager) {
//        // 在重载时直接复用 AllDataLoader，保证永远按固定顺序单线程加载
//        AllDataLoader.loadAllData();
//    }
//}