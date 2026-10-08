package com.zibura.better_loot_zibura.event;

import com.zibura.better_loot_zibura.better_loot_zibura;
import com.zibura.better_loot_zibura.loot.condition.MatchBiomeRegistryCondition;
import com.zibura.better_loot_zibura.loot.condition.SynchronizedSlotCondition;
import com.zibura.better_loot_zibura.loot.function.FillSeedBundleFunction;
import com.zibura.better_loot_zibura.loot.util.LootBindingLoader;
import com.zibura.better_loot_zibura.loot.unification.ConvertibleRecipeHandler;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.event.server.ServerStartedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegisterEvent;

import static net.minecraft.resources.ResourceLocation.fromNamespaceAndPath;

/** Minecraft 1.20.1 / Forge 47.x */
public final class CommonEvents {
    private CommonEvents() {}

    @Mod.EventBusSubscriber(
            modid = better_loot_zibura.MOD_ID,
            bus = Mod.EventBusSubscriber.Bus.MOD
    )
    public static class ModBusEvents {

        public static LootItemFunctionType FILL_SEED_BUNDLE;

        @SubscribeEvent
        public static void onRegister(RegisterEvent event) {

            event.register(Registries.LOOT_FUNCTION_TYPE, helper -> {

                FILL_SEED_BUNDLE = new LootItemFunctionType(
                        new FillSeedBundleFunction.Serializer()
                );

                helper.register(
                        fromNamespaceAndPath(
                                better_loot_zibura.MOD_ID,
                                "fill_seed_bundle"
                        ),
                        FILL_SEED_BUNDLE
                );
            });

            event.register(Registries.LOOT_CONDITION_TYPE, helper -> {

                MatchBiomeRegistryCondition.TYPE =
                        new LootItemConditionType(
                                new MatchBiomeRegistryCondition.Serializer()
                        );

                helper.register(
                        fromNamespaceAndPath(
                                better_loot_zibura.MOD_ID,
                                "match_biome_registry"
                        ),
                        MatchBiomeRegistryCondition.TYPE
                );

                SynchronizedSlotCondition.TYPE =
                        new LootItemConditionType(
                                new SynchronizedSlotCondition.Serializer()
                        );

                helper.register(
                        fromNamespaceAndPath(
                                better_loot_zibura.MOD_ID,
                                "synchronized_slot"
                        ),
                        SynchronizedSlotCondition.TYPE
                );
            });
        }
    }

    @Mod.EventBusSubscriber(modid = better_loot_zibura.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class ForgeBusEvents {
        @SubscribeEvent
        public static void onLootTableLoad(LootTableLoadEvent event) {
            LootBindingLoader.applyBinding(event);
        }

        @SubscribeEvent
        public static void onServerStarted(ServerStartedEvent event) {
            ConvertibleRecipeHandler.injectConvertibleRecipes(event.getServer().getRecipeManager());
        }
    }
}
