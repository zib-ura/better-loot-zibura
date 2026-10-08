//package com.zibura.better_loot_zibura.loot.util;
//
//import com.mojang.logging.LogUtils;
//import net.minecraft.resources.ResourceLocation;
//import net.minecraft.server.packs.resources.Resource;
//import net.minecraft.server.packs.resources.ResourceManager;
//import org.slf4j.Logger;
//
//import java.util.Map;
//
//public final class BetterLootResourceScanner {
//
//    private static final Logger LOGGER = LogUtils.getLogger();
//
//    private BetterLootResourceScanner() {}
//
//    public static void probe(ResourceManager manager) {
//        scan(manager, "loot_pools");
//        scan(manager, "loot_bindings");
//        scan(manager, "item_unifications/convertible");
//        scan(manager, "item_unifications/inconvertible");
//    }
//
//    private static void scan(ResourceManager manager, String directory) {
////        Map<ResourceLocation, Resource> resources =
////                manager.listResources(
////                        "better_loot_zibura/" + directory,
////                        id -> id.getNamespace().equals("better_loot_zibura")
////                                && id.getPath().endsWith(".json")
////                );
//        String prefix = "better_loot_zibura/" + directory;
//
//        Map<ResourceLocation, Resource> resources =
//                manager.listResources(
//                        prefix,
//                        id -> id.getPath().endsWith(".json")
//                );
//
//        LOGGER.info(
//                "[BetterLoot] {}: {} resources",
//                directory,
//                resources.size()
//        );
//
//        resources.forEach((id, resource) ->
//                LOGGER.info(
//                        "[BetterLoot] Found {} from {}",
//                        id,
//                        resource.sourcePackId()
//                )
//        );
//    }
//}