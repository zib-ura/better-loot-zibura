// =========================================================================
// 1. 声明 1.20.1 和 1.21.1 独立的药水注册表数据
// =========================================================================

// 1.20.1 完整药水 ID 列表 (包含 14 种经典药水及其延长/强化版本)
const POTIONS_1_20_1 = [
    "minecraft:water", "minecraft:awkward", "minecraft:thick", "minecraft:mundane",
    "minecraft:healing", "minecraft:strong_healing",
    "minecraft:regeneration", "minecraft:long_regeneration", "minecraft:strong_regeneration",
    "minecraft:swiftness", "minecraft:long_swiftness", "minecraft:strong_swiftness",
    "minecraft:strength", "minecraft:long_strength", "minecraft:strong_strength",
    "minecraft:fire_resistance", "minecraft:long_fire_resistance",
    "minecraft:night_vision", "minecraft:long_night_vision",
    "minecraft:invisibility", "minecraft:long_invisibility",
    "minecraft:water_breathing", "minecraft:long_water_breathing",
    "minecraft:leaping", "minecraft:long_leaping", "minecraft:strong_leaping",
    "minecraft:slow_falling", "minecraft:long_slow_falling",
    "minecraft:weakness", "minecraft:long_weakness",
    "minecraft:slowness", "minecraft:long_slowness", "minecraft:strong_slowness",
    "minecraft:poison", "minecraft:long_poison", "minecraft:strong_poison",
    "minecraft:harming", "minecraft:strong_harming",
    "minecraft:turtle_master", "minecraft:long_turtle_master", "minecraft:strong_turtle_master"
];

// 1.21.1 完整药水 ID 列表 (在 1.20.1 的基础上额外增加 1.21 试炼药水系列)
const POTIONS_1_21_1 = POTIONS_1_20_1.concat([
    "minecraft:oozing", "minecraft:long_oozing", "minecraft:strong_oozing",                     // 喷涌药水
    "minecraft:weaving", "minecraft:long_weaving", "minecraft:strong_weaving",                  // 编织药水
    "minecraft:infestation", "minecraft:long_infestation", "minecraft:strong_infestation",      // 盘绕药水
    "minecraft:wind_charged", "minecraft:long_wind_charged", "minecraft:strong_wind_charged"    // 风能充电药水
]);

// =========================================================================
// 2. 无分发器、直接进行版本判定，动态暴露“当前版本”所有可用的药水列表
// =========================================================================

const currentMCVersion = String(Platform.getMinecraftVersion());

// 根据当前加载的游戏版本，自动为全局变量 allAvailablePotions 赋上对应的数据
let allAvailablePotions;
if (currentMCVersion === "2101") {
    allAvailablePotions = POTIONS_1_21_1; // 如果是 1.21.1 运行环境，载入含有新药水的列表
} else {
    allAvailablePotions = POTIONS_1_20_1; // 如果是 1.20.1 运行环境，载入旧版列表
}