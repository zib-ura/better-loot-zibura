package com.zibura.better_loot_zibura.event;

import com.zibura.better_loot_zibura.better_loot_zibura;
import com.zibura.better_loot_zibura.loot.condition.MatchBiomeRegistryCondition;
import com.zibura.better_loot_zibura.loot.condition.SynchronizedSlotCondition;
import com.zibura.better_loot_zibura.loot.function.FillSeedBundleFunction;
import com.zibura.better_loot_zibura.loot.unification.ConvertibleLootTableGenerator;
import com.zibura.better_loot_zibura.loot.util.AllDataLoader;
import com.zibura.better_loot_zibura.loot.util.LootBindingLoader;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.loot.condition.LootConditionType;
import net.minecraft.loot.function.LootFunctionType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public final class CommonEvents implements ModInitializer {

    public static LootFunctionType<FillSeedBundleFunction> FILL_SEED_BUNDLE;
    public static LootConditionType MATCH_BIOME_REGISTRY;
    public static LootConditionType SYNCHRONIZED_SLOT;

    @Override
    public void onInitialize() {
        registerLootTypes();

        AllDataLoader.loadAllData();

        LootTableEvents.MODIFY.register(
                (key, tableBuilder, source, registries) ->
                        LootBindingLoader.applyBinding(
                                key.getValue(),
                                tableBuilder,
                                registries
                        )
        );

        ConvertibleLootTableGenerator.init();
    }

    private static void registerLootTypes() {

        FILL_SEED_BUNDLE = Registry.register(
                Registries.LOOT_FUNCTION_TYPE,
                Identifier.of(
                        better_loot_zibura.MOD_ID,
                        "fill_seed_bundle"
                ),
                new LootFunctionType<>(
                        FillSeedBundleFunction.CODEC
                )
        );

        MATCH_BIOME_REGISTRY = Registry.register(
                Registries.LOOT_CONDITION_TYPE,
                Identifier.of(
                        better_loot_zibura.MOD_ID,
                        "match_biome_registry"
                ),
                new LootConditionType(
                        MatchBiomeRegistryCondition.CODEC
                )
        );
        SYNCHRONIZED_SLOT = Registry.register(
                Registries.LOOT_CONDITION_TYPE,
                Identifier.of(
                        better_loot_zibura.MOD_ID,
                        "synchronized_slot"
                ),
                new LootConditionType(
                        SynchronizedSlotCondition.CODEC
                )
        );
    }
}