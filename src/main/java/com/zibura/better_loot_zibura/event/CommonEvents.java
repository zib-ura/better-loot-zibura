package com.zibura.better_loot_zibura.event;

import com.mojang.serialization.MapCodec;
import com.zibura.better_loot_zibura.better_loot_zibura;
import com.zibura.better_loot_zibura.config.BetterLootConfig;
import com.zibura.better_loot_zibura.loot.unification.ConvertibleLootTableGenerator;
import com.zibura.better_loot_zibura.loot.condition.MatchBiomeRegistryCondition;
import com.zibura.better_loot_zibura.loot.condition.SynchronizedSlotCondition;
import com.zibura.better_loot_zibura.loot.function.FillSeedBundleFunction;
import com.zibura.better_loot_zibura.loot.util.InjectFinalPools;
import com.zibura.better_loot_zibura.loot.util.LootBindingLoader;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.fabricmc.api.ModInitializer;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;

public final class CommonEvents implements ModInitializer {

    public static MapCodec<FillSeedBundleFunction> FILL_SEED_BUNDLE;
    public static MapCodec<MatchBiomeRegistryCondition> MATCH_BIOME_REGISTRY;

    @Override
    public void onInitialize() {
        // 1. 先注册自定义 Loot 类型，确保后续 Codec 可解析
        registerLootTypes();

        // Loading happens before reloadable loot registries are decoded, on startup and /reload.
        LootTableEvents.MODIFY.register((key, tableBuilder, source, registries) -> {
            if (BetterLootConfig.enableLootAdditionsAndPacks) {
                LootBindingLoader.applyBinding(key.identifier(), tableBuilder, registries);
            }
        });
        ConvertibleLootTableGenerator.init();

    }

    private void registerLootTypes() {
        FILL_SEED_BUNDLE = Registry.register(
                BuiltInRegistries.LOOT_FUNCTION_TYPE,
                Identifier.fromNamespaceAndPath(better_loot_zibura.MOD_ID, "fill_seed_bundle"),
                FillSeedBundleFunction.CODEC
        );

        MATCH_BIOME_REGISTRY = Registry.register(
                BuiltInRegistries.LOOT_CONDITION_TYPE,
                Identifier.fromNamespaceAndPath(better_loot_zibura.MOD_ID, "match_biome_registry"),
                MatchBiomeRegistryCondition.CODEC
        );

        Registry.register(
                BuiltInRegistries.LOOT_CONDITION_TYPE,
                Identifier.fromNamespaceAndPath(better_loot_zibura.MOD_ID, "synchronized_slot"),
                SynchronizedSlotCondition.CODEC
        );
    }
}