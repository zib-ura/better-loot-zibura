package com.zibura.better_loot_zibura.event;

import com.zibura.better_loot_zibura.better_loot_zibura;
import com.zibura.better_loot_zibura.loot.condition.MatchBiomeRegistryCondition;
import com.zibura.better_loot_zibura.loot.function.FillSeedBundleFunction;
import com.zibura.better_loot_zibura.loot.function.LazyEnchantRandomlyFunction; // 新增导包
import com.zibura.better_loot_zibura.loot.util.*;
import com.zibura.better_loot_zibura.loot.unification.ConvertibleLootTableGenerator;
import com.zibura.better_loot_zibura.loot.unification.ConvertibleRecipeHandler;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.event.LootTableLoadEvent;
import net.neoforged.neoforge.event.server.ServerStartedEvent;
import net.neoforged.neoforge.registries.RegisterEvent;

public final class CommonEvents {

    private CommonEvents() {}

    // ==========================================
    // 1. MOD 事件总线
    // ==========================================
    @EventBusSubscriber(modid = better_loot_zibura.MOD_ID)
    public static class ModBusEvents {

        @SubscribeEvent
        public static void onCommonSetup(FMLCommonSetupEvent event) {
            event.enqueueWork(AllDataLoader::loadAllData);
        }
    }

    @EventBusSubscriber(modid = better_loot_zibura.MOD_ID)
    public static class ModLootFunctions {

        public static LootItemFunctionType<FillSeedBundleFunction> FILL_SEED_BUNDLE;
        public static LootItemFunctionType<LazyEnchantRandomlyFunction> LAZY_ENCHANT_RANDOMLY; // 新增字段

        @SubscribeEvent
        public static void onRegister(RegisterEvent event) {
            event.register(Registries.LOOT_FUNCTION_TYPE, helper -> {
                // 原有的 FillSeedBundleFunction
                FILL_SEED_BUNDLE = new LootItemFunctionType<>(FillSeedBundleFunction.CODEC);
                helper.register(
                        ResourceLocation.fromNamespaceAndPath(better_loot_zibura.MOD_ID, "fill_seed_bundle"),
                        FILL_SEED_BUNDLE
                );

                // 注册 LazyEnchantRandomlyFunction
                LAZY_ENCHANT_RANDOMLY = new LootItemFunctionType<>(LazyEnchantRandomlyFunction.CODEC);
                helper.register(
                        ResourceLocation.fromNamespaceAndPath(better_loot_zibura.MOD_ID, "lazy_enchant_randomly"),
                        LAZY_ENCHANT_RANDOMLY
                );
            });

            // 注册 Loot Condition (1.21.1)
            event.register(Registries.LOOT_CONDITION_TYPE, helper -> {
                MatchBiomeRegistryCondition.TYPE = new LootItemConditionType(MatchBiomeRegistryCondition.CODEC);
                helper.register(
                        ResourceLocation.fromNamespaceAndPath(better_loot_zibura.MOD_ID, "match_biome_registry"),
                        MatchBiomeRegistryCondition.TYPE
                );
            });
        }
    }

    // ==========================================
    // 2. GAME 事件总线 (原 FORGE 总线)
    // ==========================================
    @EventBusSubscriber(modid = better_loot_zibura.MOD_ID)
    public static class ForgeBusEvents {

        @SubscribeEvent
        public static void onLootTableLoad(LootTableLoadEvent event) {
            InjectFinalPools.injectLootPools(event);
        }

        @SubscribeEvent
        public static void onServerStarted(ServerStartedEvent event) {
            ConvertibleRecipeHandler.injectConvertibleRecipes(event.getServer().getRecipeManager());
            ConvertibleLootTableGenerator.injectIntoServer(event.getServer());
        }
    }
}