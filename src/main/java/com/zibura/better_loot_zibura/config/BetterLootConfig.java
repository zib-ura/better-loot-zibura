package com.zibura.better_loot_zibura.config;

import net.neoforged.fml.ModContainer;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.ModConfigSpec;

public class BetterLootConfig {
    public static final ModConfigSpec COMMON_SPEC;
    public static final ModConfigSpec.BooleanValue ENABLE_CONVERTIBLE_RECIPES;
    public static final ModConfigSpec.BooleanValue ENABLE_CONVERTIBLE_LOOT_TABLES;

    public static final ModConfigSpec.BooleanValue ENABLE_LOOT_ADDITIONS_AND_PACKS;

    static {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();

        builder.push("convertible_unification");
        ENABLE_CONVERTIBLE_RECIPES = builder
                .comment(
                        "Whether to register 1-to-1 shapeless conversion recipes for items of the same category in 'convertible'.",
                        "是否为 convertible 中的同类物品注册互相转换的配方 (1对1无序配方)"
                )
                .define("enableRecipes", true);

        ENABLE_CONVERTIBLE_LOOT_TABLES = builder
                .comment(
                        "Whether to register a unified loot table for each item category in 'convertible'.",
                        "是否为 convertible 中的每一类物品注册一个统合战利品表"
                )
                .define("enableLootTables", true);
        builder.pop();

        builder.push("loot_injection");
        ENABLE_LOOT_ADDITIONS_AND_PACKS = builder
                .comment("Enable both custom loot additions and bundled datapack imports from the packs folder.",
                         "同时控制战利品添加和 packs 文件夹内数据包的导入；修改后请重启游戏或服务器。")
                .define("enableLootAdditionsAndPacks", true);
        builder.pop();

        COMMON_SPEC = builder.build();
    }

    public static void register(ModContainer modContainer) {
        modContainer.registerConfig(ModConfig.Type.COMMON, COMMON_SPEC);
    }
}