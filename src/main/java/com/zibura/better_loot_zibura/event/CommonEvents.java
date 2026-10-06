package com.zibura.better_loot_zibura.event;

import com.zibura.better_loot_zibura.better_loot_zibura;
import com.zibura.better_loot_zibura.loot.condition.MatchBiomeRegistryCondition;
import com.zibura.better_loot_zibura.loot.function.FillSeedBundleFunction;
import com.zibura.better_loot_zibura.loot.unification.ConvertibleLootTableGenerator;
import com.zibura.better_loot_zibura.loot.unification.ConvertibleRecipeHandler;
import com.zibura.better_loot_zibura.loot.util.AllDataLoader;
import com.zibura.better_loot_zibura.loot.util.InjectFinalPools;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.loot.condition.LootConditionType;
import net.minecraft.loot.function.LootFunctionType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public final class CommonEvents implements ModInitializer {

    public static LootFunctionType FILL_SEED_BUNDLE;

    @Override
    public void onInitialize() {
        // 1. 数据与战利品修改器初始化
        AllDataLoader.loadAllData();
        InjectFinalPools.init();

        // 2. 注册战利品函数与条件
        registerLootTypes();

        // 3. 服务端启动回调 (对应原 ServerStartedEvent)
        ServerLifecycleEvents.SERVER_STARTED.register(server -> {
            ConvertibleRecipeHandler.injectConvertibleRecipes(server.getRecipeManager());
            ConvertibleLootTableGenerator.injectIntoServer(server);
        });
    }

    private void registerLootTypes() {
        FILL_SEED_BUNDLE = Registry.register(
                Registries.LOOT_FUNCTION_TYPE,
                new Identifier(better_loot_zibura.MOD_ID, "fill_seed_bundle"),
                new LootFunctionType(new FillSeedBundleFunction.Serializer())
        );

        MatchBiomeRegistryCondition.TYPE = Registry.register(
                Registries.LOOT_CONDITION_TYPE,
                new Identifier(better_loot_zibura.MOD_ID, "match_biome_registry"),
                new LootConditionType(new MatchBiomeRegistryCondition.Serializer())
        );
    }
}