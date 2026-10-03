//package com.zibura.better_loot_zibura.event;
//
//import com.zibura.better_loot_zibura.better_loot_zibura;
//import com.zibura.better_loot_zibura.loot.condition.MatchBiomeRegistryCondition;
//import com.zibura.better_loot_zibura.loot.function.FillSeedBundleFunction;
//import com.zibura.better_loot_zibura.loot.function.LazyEnchantRandomlyFunction;
//import com.zibura.better_loot_zibura.loot.util.*;
//import com.zibura.better_loot_zibura.loot.unification.ConvertibleLootTableGenerator;
//
//import net.minecraft.core.registries.Registries;
//import net.minecraft.resources.Identifier;
//import net.minecraft.server.packs.PackSelectionConfig;
//import net.neoforged.bus.api.SubscribeEvent;
//import net.neoforged.fml.common.EventBusSubscriber;
//import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
//import net.neoforged.neoforge.event.LootTableLoadEvent;
//import net.neoforged.neoforge.event.server.ServerStartedEvent;
//import net.neoforged.neoforge.registries.RegisterEvent;
//import com.zibura.better_loot_zibura.loot.unification.ConvertibleRecipePack;
//
//import net.minecraft.network.chat.Component;
//import net.minecraft.server.packs.PackLocationInfo;
//import net.minecraft.server.packs.PackResources;
//import net.minecraft.server.packs.PackType;
//import net.minecraft.server.packs.repository.Pack;
//import net.minecraft.server.packs.repository.PackSource;
//import net.neoforged.neoforge.event.AddPackFindersEvent;
//
//import java.util.Optional;
//import java.util.stream.Stream;
//public final class CommonEvents {
//
//    private CommonEvents() {}
//
//    // ==========================================
//    // 1. MOD 事件总线
//    // ==========================================
//    @EventBusSubscriber(modid = better_loot_zibura.MOD_ID)
//    public static class ModBusEvents {
//
//        @SubscribeEvent
//        public static void onCommonSetup(FMLCommonSetupEvent event) {
//            event.enqueueWork(AllDataLoader::loadAllData);
//        }
//
//        @SubscribeEvent
//        public static void onAddPackFinders(AddPackFindersEvent event) {
//            if (event.getPackType() != PackType.SERVER_DATA) {
//                return;
//            }
//
//            PackLocationInfo location = new PackLocationInfo(
//                    "better_loot_zibura:convertible_recipes",
//                    Component.literal("Better Loot Convertible Recipes"),
//                    PackSource.BUILT_IN,
//                    Optional.empty()
//            );
//
//            Pack.ResourcesSupplier supplier = new Pack.ResourcesSupplier() {
//                @Override
//                public PackResources openMetadata(PackLocationInfo location) {
//                    return new ConvertibleRecipePack();
//                }
//
//                @Override
//                public Stream<PackResources> openResources(
//                        PackLocationInfo location,
//                        Pack.Metadata metadata
//                ) {
//                    return Stream.of(new ConvertibleRecipePack());
//                }
//            };
//
//            Pack pack = Pack.readMetaAndCreate(
//                    location,
//                    supplier,
//                    PackType.SERVER_DATA,
//                    new PackSelectionConfig(
//                            true,
//                            Pack.Position.TOP,
//                            false
//                    )
//            );
//
//            if (pack != null) {
//                event.addRepositorySource(consumer -> consumer.accept(pack));
//            }
//        }
//    }
//
//    @EventBusSubscriber(modid = better_loot_zibura.MOD_ID)
//    public static class ModLootFunctions {
//
//        @SubscribeEvent
//        public static void onRegister(RegisterEvent event) {
//
//            // ==========================================
//            // Loot Functions
//            // 26.3 注册表中直接注册 MapCodec
//            // ==========================================
//            event.register(Registries.LOOT_FUNCTION_TYPE, helper -> {
//
//                helper.register(
//                        Identifier.fromNamespaceAndPath(
//                                better_loot_zibura.MOD_ID,
//                                "fill_seed_bundle"
//                        ),
//                        FillSeedBundleFunction.CODEC
//                );
//
//                helper.register(
//                        Identifier.fromNamespaceAndPath(
//                                better_loot_zibura.MOD_ID,
//                                "lazy_enchant_randomly"
//                        ),
//                        LazyEnchantRandomlyFunction.CODEC
//                );
//            });
//
//            // ==========================================
//            // Loot Conditions
//            // 26.3 同样直接注册 MapCodec
//            // ==========================================
//            event.register(Registries.LOOT_CONDITION_TYPE, helper -> {
//
//                helper.register(
//                        Identifier.fromNamespaceAndPath(
//                                better_loot_zibura.MOD_ID,
//                                "match_biome_registry"
//                        ),
//                        MatchBiomeRegistryCondition.CODEC
//                );
//            });
//        }
//    }
//
//    // ==========================================
//    // 2. GAME 事件总线
//    // ==========================================
//    @EventBusSubscriber(modid = better_loot_zibura.MOD_ID)
//    public static class ForgeBusEvents {
//
//        @SubscribeEvent
//        public static void onLootTableLoad(LootTableLoadEvent event) {
//            InjectFinalPools.injectLootPools(event);
//        }
//
//        @SubscribeEvent
//        public static void onServerStarted(ServerStartedEvent event) {
////            ConvertibleRecipeHandler.injectConvertibleRecipes(
////                    event.getServer().getRecipeManager()
////            );
//
//            ConvertibleLootTableGenerator.injectIntoServer(
//                    event.getServer()
//            );
//        }
//    }
//}


package com.zibura.better_loot_zibura.event;

import com.zibura.better_loot_zibura.better_loot_zibura;
import com.zibura.better_loot_zibura.loot.condition.MatchBiomeRegistryCondition;
import com.zibura.better_loot_zibura.loot.condition.SynchronizedSlotCondition;
import com.zibura.better_loot_zibura.loot.function.FillSeedBundleFunction;
import com.zibura.better_loot_zibura.loot.function.LazyEnchantRandomlyFunction;
import com.zibura.better_loot_zibura.loot.unification.ConvertibleItemsPreviewLootTableGenerator;
import com.zibura.better_loot_zibura.loot.util.*;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.PackSelectionConfig;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.event.LootTableLoadEvent;
import net.neoforged.neoforge.event.server.ServerStartedEvent;
import net.neoforged.neoforge.registries.RegisterEvent;
import com.zibura.better_loot_zibura.loot.unification.ConvertibleRecipePack;

import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackLocationInfo;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.neoforged.neoforge.event.AddPackFindersEvent;

import java.util.Optional;
import java.util.stream.Stream;
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

        @SubscribeEvent
        public static void onAddPackFinders(AddPackFindersEvent event) {
            if (event.getPackType() != PackType.SERVER_DATA) {
                return;
            }

            PackLocationInfo location = new PackLocationInfo(
                    "better_loot_zibura:convertible_recipes",
                    Component.literal("Better Loot Convertible Recipes"),
                    PackSource.BUILT_IN,
                    Optional.empty()
            );

            Pack.ResourcesSupplier supplier = new Pack.ResourcesSupplier() {
                @Override
                public PackResources openMetadata(PackLocationInfo location) {
                    return new ConvertibleRecipePack();
                }

                @Override
                public Stream<PackResources> openResources(
                        PackLocationInfo location,
                        Pack.Metadata metadata
                ) {
                    return Stream.of(new ConvertibleRecipePack());
                }
            };

            Pack pack = Pack.readMetaAndCreate(
                    location,
                    supplier,
                    PackType.SERVER_DATA,
                    new PackSelectionConfig(
                            true,
                            Pack.Position.TOP,
                            false
                    )
            );

            if (pack != null) {
                event.addRepositorySource(consumer -> consumer.accept(pack));
            }
        }
    }

    @EventBusSubscriber(modid = better_loot_zibura.MOD_ID)
    public static class ModLootFunctions {

        @SubscribeEvent
        public static void onRegister(RegisterEvent event) {

            // ==========================================
            // Loot Functions
            // 26.3 注册表中直接注册 MapCodec
            // ==========================================
            event.register(Registries.LOOT_FUNCTION_TYPE, helper -> {

                helper.register(
                        Identifier.fromNamespaceAndPath(
                                better_loot_zibura.MOD_ID,
                                "fill_seed_bundle"
                        ),
                        FillSeedBundleFunction.CODEC
                );

                helper.register(
                        Identifier.fromNamespaceAndPath(
                                better_loot_zibura.MOD_ID,
                                "lazy_enchant_randomly"
                        ),
                        LazyEnchantRandomlyFunction.CODEC
                );
            });

            // ==========================================
            // Loot Conditions
            // 26.3 同样直接注册 MapCodec
            // ==========================================
            event.register(Registries.LOOT_CONDITION_TYPE, helper -> {

                helper.register(
                        Identifier.fromNamespaceAndPath(
                                better_loot_zibura.MOD_ID,
                                "match_biome_registry"
                        ),
                        MatchBiomeRegistryCondition.CODEC
                );

                helper.register(
                        Identifier.fromNamespaceAndPath(
                                better_loot_zibura.MOD_ID,
                                "synchronized_slot"
                        ),
                        SynchronizedSlotCondition.CODEC
                );
            });
        }
    }

    // ==========================================
    // 2. GAME 事件总线
    // ==========================================
    @EventBusSubscriber(modid = better_loot_zibura.MOD_ID)
    public static class ForgeBusEvents {

        @SubscribeEvent
        public static void onLootTableLoad(LootTableLoadEvent event) {
            InjectFinalPools.injectLootPools(event);
        }

        @SubscribeEvent
        public static void onServerStarted(ServerStartedEvent event) {
//            ConvertibleRecipeHandler.injectConvertibleRecipes(
//                    event.getServer().getRecipeManager()
//            );

            ConvertibleItemsPreviewLootTableGenerator.injectIntoServer(
                    event.getServer()
            );
        }
    }
}