package com.zibura.better_loot_zibura.event;

import com.zibura.better_loot_zibura.better_loot_zibura;
import com.zibura.better_loot_zibura.loot.condition.MatchBiomeRegistryCondition;
import com.zibura.better_loot_zibura.loot.function.FillSeedBundleFunction;
import com.zibura.better_loot_zibura.loot.unification.ConvertibleLootTableGenerator;
import com.zibura.better_loot_zibura.loot.unification.ConvertibleRecipeHandler;
import com.zibura.better_loot_zibura.loot.util.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.event.server.ServerStartedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.RegisterEvent;

public final class CommonEvents {

    private CommonEvents() {}

    // ==========================================
    // 1. MOD 事件总线
    // ==========================================
    @Mod.EventBusSubscriber(modid = better_loot_zibura.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ModBusEvents {

        @SubscribeEvent
        public static void onCommonSetup(FMLCommonSetupEvent event) {
            // 直接转发给工具类加载
            event.enqueueWork(AllDataLoader::loadAllData);
        }

        public static LootItemFunctionType FILL_SEED_BUNDLE;

        @SubscribeEvent
        public static void onRegister(RegisterEvent event) {
            event.register(Registries.LOOT_FUNCTION_TYPE, helper -> {
                FILL_SEED_BUNDLE = new LootItemFunctionType(new FillSeedBundleFunction.Serializer());
                helper.register(ResourceLocation.fromNamespaceAndPath(better_loot_zibura.MOD_ID, "fill_seed_bundle"), FILL_SEED_BUNDLE);
            });

            event.register(Registries.LOOT_CONDITION_TYPE, helper -> {
                MatchBiomeRegistryCondition.TYPE = new LootItemConditionType(new MatchBiomeRegistryCondition.Serializer());
                helper.register(ResourceLocation.fromNamespaceAndPath(better_loot_zibura.MOD_ID, "match_biome_registry"), MatchBiomeRegistryCondition.TYPE);
            });
        }
    }

    // ==========================================
    // 2. FORGE 事件总线
    // ==========================================
    @Mod.EventBusSubscriber(modid = better_loot_zibura.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class ForgeBusEvents {

        @SubscribeEvent
        public static void onLootTableLoad(LootTableLoadEvent event) {
            InjectFinalPools.injectLootPools(event);

        }

//        @SubscribeEvent
//        public static void onAddReloadListeners(AddReloadListenerEvent event) {
//            event.addListener(new ItemUnificationLoader());
//            event.addListener(new LootDataLoader());
//            event.addListener(new LootBindingLoader());
//        }
//        @SubscribeEvent
//        public static void onAddReloadListeners(AddReloadListenerEvent event) {
//            // 只注册这一个监听器即可
//            event.addListener(new BetterLootReloadListener());
//        }

        @SubscribeEvent
        public static void onServerStarted(ServerStartedEvent event) {
            ConvertibleRecipeHandler.injectConvertibleRecipes(event.getServer().getRecipeManager());
            ConvertibleLootTableGenerator.injectIntoServer(event.getServer());
        }

//        @SubscribeEvent
//        public static void onDatapackSync(OnDatapackSyncEvent event) {
//            if (event.getPlayer() == null) {
//                MinecraftServer server = event.getPlayerList().getServer();
//                ConvertibleRecipeHandler.injectConvertibleRecipes(server.getRecipeManager());
//                ConvertibleLootTableGenerator.injectIntoServer(server);
//            }
//        }
    }
}