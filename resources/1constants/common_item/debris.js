// Debris pools v3 | Minecraft Java Edition 26.3 | ocean ruins narrative loot
// 仅 item 级别；所有 ratio = 10；不设置 min/max。
// 数组引用需要由物品池加载器递归展开；组合后的重复 id 建议去重，以免意外叠加权重。

// ===== 石质建筑：石料、石砖、碎石及风化残渣 =====
const stone_debris_materials = [
    { id: "minecraft:stone", ratio: 10 },
    { id: "minecraft:cobblestone", ratio: 10 },
    // { id: "minecraft:mossy_cobblestone", ratio: 10 },
    { id: "minecraft:stone_bricks", ratio: 10 },
    { id: "minecraft:cracked_stone_bricks", ratio: 10 },
    // { id: "minecraft:mossy_stone_bricks", ratio: 10 },
    { id: "minecraft:andesite", ratio: 10 },

    // 彻底破坏后的碎料
    { id: "minecraft:gravel", ratio: 10 },
    { id: "minecraft:flint", ratio: 10 },
    { id: "minecraft:cobblestone_slab", ratio: 10 },
];

// ===== 砂岩建筑：砂岩结构、崩解砂料 =====
const sandstone_debris_materials = [
    { id: "minecraft:sandstone", ratio: 10 },
    { id: "minecraft:cut_sandstone", ratio: 10 },
    { id: "minecraft:chiseled_sandstone", ratio: 10 },
    { id: "minecraft:smooth_sandstone", ratio: 10 },

    // 彻底风化后的碎料
    { id: "minecraft:sand", ratio: 10 },
];

// ===== 木质建筑：木材、木制结构及破碎残料 =====
const wood_debris_materials = [
    { id: "minecraft:oak_log", ratio: 10 },
    { id: "minecraft:oak_planks", ratio: 10 },
    { id: "minecraft:oak_slab", ratio: 10 },
    // { id: "minecraft:oak_stairs", ratio: 10 },
    // { id: "minecraft:oak_fence", ratio: 10 },
    // { id: "minecraft:oak_trapdoor", ratio: 10 },

    // 破碎、腐朽后的残留物
    { id: "minecraft:stick", ratio: 10 },
    { id: "minecraft:oak_button", ratio: 10 },
    { id: "minecraft:charcoal", ratio: 10 },
];

// ===== 深色橡木建筑 =====
const dark_oak_debris_materials = [
    { id: "minecraft:dark_oak_log", ratio: 10 },
    { id: "minecraft:dark_oak_planks", ratio: 10 },
    // { id: "minecraft:dark_oak_stairs", ratio: 10 },
    // { id: "minecraft:dark_oak_fence", ratio: 10 },
    // { id: "minecraft:dark_oak_trapdoor", ratio: 10 },

    // 破碎、腐朽后的残留物
    { id: "minecraft:stick", ratio: 10 },
    { id: "minecraft:dark_oak_button", ratio: 10 },
    { id: "minecraft:charcoal", ratio: 10 },
];

// ===== 泥土、黏土与砖砌建筑 =====
const clay_debris_materials = [
    { id: "minecraft:dirt", ratio: 10 },
    { id: "minecraft:coarse_dirt", ratio: 10 },
    { id: "minecraft:clay", ratio: 10 },
    { id: "minecraft:bricks", ratio: 10 },
    { id: "minecraft:terracotta", ratio: 10 },

    // 建筑崩解后的碎料
    { id: "minecraft:clay_ball", ratio: 10 },
    { id: "minecraft:brick", ratio: 10 },
    { id: "minecraft:mud", ratio: 10 },
    { id: "minecraft:packed_mud", ratio: 10 },
];

// ===== 深板岩建筑 =====
const deepslate_debris_materials = [
    { id: "minecraft:deepslate", ratio: 10 },
    { id: "minecraft:cobbled_deepslate", ratio: 10 },
    { id: "minecraft:polished_deepslate", ratio: 10 },
    { id: "minecraft:deepslate_bricks", ratio: 10 },
    { id: "minecraft:cracked_deepslate_bricks", ratio: 10 },
    { id: "minecraft:deepslate_tiles", ratio: 10 },
    { id: "minecraft:cracked_deepslate_tiles", ratio: 10 },

    // 破碎后的石质残料
    { id: "minecraft:gravel", ratio: 10 },
    { id: "minecraft:flint", ratio: 10 },
];

// ===== 凝灰岩与铜质建筑 =====
const tuff_copper_debris_materials = [
    { id: "minecraft:tuff", ratio: 10 },
    { id: "minecraft:polished_tuff", ratio: 10 },
    { id: "minecraft:tuff_bricks", ratio: 10 },
    { id: "minecraft:chiseled_tuff", ratio: 10 },
    { id: "minecraft:copper_grate", ratio: 10 },
    { id: "minecraft:exposed_copper", ratio: 10 },
    { id: "minecraft:weathered_copper", ratio: 10 },
    { id: "minecraft:oxidized_copper", ratio: 10 },

    // 破碎与腐蚀后的残料
    { id: "minecraft:gravel", ratio: 10 },
    { id: "minecraft:raw_copper", ratio: 10 },
    { id: "minecraft:copper_nugget", ratio: 10 },
];

// ===== 下界砖建筑 =====
const nether_brick_debris_materials = [
    { id: "minecraft:netherrack", ratio: 10 },
    { id: "minecraft:nether_bricks", ratio: 10 },
    { id: "minecraft:cracked_nether_bricks", ratio: 10 },
    { id: "minecraft:nether_brick_fence", ratio: 10 },
    { id: "minecraft:red_nether_bricks", ratio: 10 },

    // 破碎后的砖块与残料
    { id: "minecraft:nether_brick", ratio: 10 },
    { id: "minecraft:gravel", ratio: 10 },
];

// ===== 黑石与玄武岩建筑 =====
const blackstone_debris_materials = [
    { id: "minecraft:blackstone", ratio: 10 },
    { id: "minecraft:polished_blackstone", ratio: 10 },
    { id: "minecraft:polished_blackstone_bricks", ratio: 10 },
    { id: "minecraft:cracked_polished_blackstone_bricks", ratio: 10 },
    { id: "minecraft:basalt", ratio: 10 },
    { id: "minecraft:polished_basalt", ratio: 10 },

    // 破碎后的矿石残料
    { id: "minecraft:gravel", ratio: 10 },
    { id: "minecraft:blackstone_slab", ratio: 10 },
];

// ===== 冰雪建筑 =====
const snow_debris_materials = [
    { id: "minecraft:snow_block", ratio: 10 },
    { id: "minecraft:ice", ratio: 10 },
    { id: "minecraft:packed_ice", ratio: 10 },

    // 破碎、融化后的残留
    { id: "minecraft:snowball", ratio: 10 },
];

// ===== 传送门遗迹 =====
const portal_debris_materials = [
    { id: "minecraft:obsidian", ratio: 10 },
    { id: "minecraft:crying_obsidian", ratio: 10 },
    { id: "minecraft:netherrack", ratio: 10 },
    { id: "minecraft:magma_block", ratio: 10 },

    // 破坏与火焰侵蚀后的残留
    { id: "minecraft:flint", ratio: 10 },
    { id: "minecraft:gravel", ratio: 10 },
];





// ===== 环境：只描述自然侵蚀、附着与沉积 =====
const environment_debris_ocean = [
    { id: "minecraft:seagrass", ratio: 10 },
    { id: "minecraft:kelp", ratio: 10 },
    { id: "minecraft:sea_pickle", ratio: 10 },
    { id: "minecraft:sand", ratio: 10 },
    { id: "minecraft:gravel", ratio: 10 },
    { id: "minecraft:clay_ball", ratio: 10 },
    { id: "minecraft:ink_sac", ratio: 10 },
];

const environment_debris_desert = [
    { id: "minecraft:sand", ratio: 10 },
    { id: "minecraft:red_sand", ratio: 10 },
    { id: "minecraft:dead_bush", ratio: 10 },
    { id: "minecraft:bone", ratio: 10 },
];

const environment_debris_overgrown = [
    { id: "minecraft:vine", ratio: 10 },
    { id: "minecraft:moss_block", ratio: 10 },
    { id: "minecraft:moss_carpet", ratio: 10 },
    { id: "minecraft:rooted_dirt", ratio: 10 },
    { id: "minecraft:hanging_roots", ratio: 10 },
    { id: "minecraft:brown_mushroom", ratio: 10 },
    { id: "minecraft:red_mushroom", ratio: 10 },
];

const environment_debris_frozen = [
    { id: "minecraft:snowball", ratio: 10 },
    { id: "minecraft:snow_block", ratio: 10 },
    { id: "minecraft:ice", ratio: 10 },
    { id: "minecraft:packed_ice", ratio: 10 },
];

const environment_debris_underground = [
    { id: "minecraft:gravel", ratio: 10 },
    { id: "minecraft:tuff", ratio: 10 },
    { id: "minecraft:dripstone_block", ratio: 10 },
    { id: "minecraft:pointed_dripstone", ratio: 10 },
    { id: "minecraft:glow_lichen", ratio: 10 },
    { id: "minecraft:cobweb", ratio: 10 },
];

const environment_debris_nether = [
    { id: "minecraft:netherrack", ratio: 10 },
    { id: "minecraft:basalt", ratio: 10 },
    { id: "minecraft:blackstone", ratio: 10 },
    { id: "minecraft:soul_soil", ratio: 10 },
    { id: "minecraft:soul_sand", ratio: 10 },
    { id: "minecraft:bone", ratio: 10 },
];

const environment_debris_sculk = [
    { id: "minecraft:sculk", ratio: 10 },
    { id: "minecraft:sculk_vein", ratio: 10 },
    { id: "minecraft:sculk_catalyst", ratio: 10 },
];






// ===== 遗留物：按活动用途复用 =====
const household_debris_remnants = [
    { id: "minecraft:stick", ratio: 10 },
    { id: "minecraft:paper", ratio: 10 },
    { id: "minecraft:bowl", ratio: 10 },
    { id: "minecraft:glass_bottle", ratio: 10 },
    { id: "minecraft:flower_pot", ratio: 10 },
    { id: "minecraft:leather", ratio: 10 },
    { id: "minecraft:feather", ratio: 10 },
];

const food_debris_remnants = [
    { id: "minecraft:poisonous_potato", ratio: 10 },
    { id: "minecraft:rotten_flesh", ratio: 10 },
    { id: "minecraft:bone_meal", ratio: 10 },
    { id: "minecraft:wheat_seeds", ratio: 10 },
    { id: "minecraft:beetroot_seeds", ratio: 10 },
    { id: "minecraft:melon_seeds", ratio: 10 },
    { id: "minecraft:pumpkin_seeds", ratio: 10 },
];

const farming_debris_remnants = [
    { id: "minecraft:wheat", ratio: 10 },
    { id: "minecraft:potato", ratio: 10 },
    { id: "minecraft:beetroot", ratio: 10 },
    { id: "minecraft:hay_block", ratio: 10 },
    { id: "minecraft:composter", ratio: 10 },
    { id: "minecraft:wooden_hoe", ratio: 10 },
    { id: "minecraft:wooden_shovel", ratio: 10 },
];

const fishing_debris_remnants = [
    { id: "minecraft:fishing_rod", ratio: 10 },
    { id: "minecraft:string", ratio: 10 },
    { id: "minecraft:tripwire_hook", ratio: 10 },
    { id: "minecraft:cod", ratio: 10 },
    { id: "minecraft:salmon", ratio: 10 },
    // { id: "minecraft:dried_kelp", ratio: 10 },
];

const mining_debris_remnants = [
    { id: "minecraft:stone_pickaxe", ratio: 10 },
    { id: "minecraft:stone_shovel", ratio: 10 },
    { id: "minecraft:rail", ratio: 10 },
    { id: "minecraft:powered_rail", ratio: 10 },
    { id: "minecraft:detector_rail", ratio: 10 },
    { id: "minecraft:torch", ratio: 10 },
    { id: "minecraft:coal", ratio: 10 },
    { id: "minecraft:raw_iron", ratio: 10 },
];

const combat_debris_remnants = [
    { id: "minecraft:arrow", ratio: 10 },
    { id: "minecraft:bone", ratio: 10 },
    { id: "minecraft:string", ratio: 10 },
    { id: "minecraft:leather", ratio: 10 },
    { id: "minecraft:iron_nugget", ratio: 10 },
    { id: "minecraft:chain", ratio: 10 },
];

const study_debris_remnants = records_and_ink

// 航海活动：记录航路、测向、观测、运输物资。
// 其中 map 只是普通地图；真正的藏宝图放在海底遗迹的探索奖励中。
const navigation_debris_remnants = [
    { id: "minecraft:map", ratio: 10 },
    common_cartography_tools,
    { id: "minecraft:paper", ratio: 10 },
    { id: "minecraft:barrel", ratio: 10 },
    { id: "minecraft:oak_boat", ratio: 10 },
];

const mechanism_debris_remnants = [
    { id: "minecraft:tripwire_hook", ratio: 10 },
    { id: "minecraft:string", ratio: 10 },
    { id: "minecraft:dispenser", ratio: 10 },
    { id: "minecraft:arrow", ratio: 10 },
    { id: "minecraft:redstone", ratio: 10 },
    { id: "minecraft:lever", ratio: 10 },
    { id: "minecraft:sticky_piston", ratio: 10 },
];

const alchemy_debris_remnants = [
    { id: "minecraft:glass_bottle", ratio: 10 },
    { id: "minecraft:brewing_stand", ratio: 10 },
    { id: "minecraft:nether_wart", ratio: 10 },
    { id: "minecraft:spider_eye", ratio: 10 },
    { id: "minecraft:blaze_powder", ratio: 10 },
];

const pillager_debris_remnants = [
    { id: "minecraft:crossbow", ratio: 10 },
    { id: "minecraft:arrow", ratio: 10 },
    { id: "minecraft:leather", ratio: 10 },
    { id: "minecraft:iron_nugget", ratio: 10 },
    { id: "minecraft:chain", ratio: 10 },
];

const nether_debris_remnants = [
    { id: "minecraft:bone", ratio: 10 },
    { id: "minecraft:coal", ratio: 10 },
    { id: "minecraft:blaze_rod", ratio: 10 },
    { id: "minecraft:nether_wart", ratio: 10 },
    { id: "minecraft:glass_bottle", ratio: 10 },
    { id: "minecraft:magma_cream", ratio: 10 },
];

const piglin_debris_remnants = [
    { id: "minecraft:gold_nugget", ratio: 10 },
    { id: "minecraft:golden_sword", ratio: 10 },
    { id: "minecraft:golden_axe", ratio: 10 },
    { id: "minecraft:chain", ratio: 10 },
    { id: "minecraft:bone", ratio: 10 },
];

const portal_debris_remnants = [
    { id: "minecraft:flint", ratio: 10 },
    { id: "minecraft:flint_and_steel", ratio: 10 },
    { id: "minecraft:fire_charge", ratio: 10 },
    { id: "minecraft:gold_nugget", ratio: 10 },
    { id: "minecraft:golden_hoe", ratio: 10 },
];
//
// // ===== 结构组合：只引用物品数组，避免在这里复制基础条目 =====
// const village_debris_items = [
//     wood_debris_materials,
//     clay_debris_materials,
//     household_debris_remnants,
//     food_debris_remnants,
//     farming_debris_remnants,
// ];
//
// const abandoned_village_debris_items = [
//     village_debris_items,
//     environment_debris_overgrown,
// ];
//
// const underwater_ruin_cold_debris_items = [
//     stone_debris_materials,
//     environment_debris_ocean,
// ];
//
// const underwater_ruin_warm_debris_items = [
//     sandstone_debris_materials,
//     environment_debris_ocean,
// ];
//
// const shipwreck_debris_items = [
//     wood_debris_materials,
//     environment_debris_ocean,
//     navigation_debris_remnants,
//     fishing_debris_remnants,
// ];
//
// const dungeon_debris_items = [
//     stone_debris_materials,
//     environment_debris_underground,
//     combat_debris_remnants,
// ];
//
// const stronghold_debris_items = [
//     stone_debris_materials,
//     environment_debris_underground,
//     study_debris_remnants,
//     mechanism_debris_remnants,
// ];
//
// const trial_chambers_debris_items = [
//     tuff_copper_debris_materials,
//     environment_debris_underground,
//     combat_debris_remnants,
//     mechanism_debris_remnants,
// ];
//
// const ancient_city_debris_items = [
//     deepslate_debris_materials,
//     environment_debris_sculk,
//     household_debris_remnants,
//     study_debris_remnants,
// ];
//
// const mineshaft_debris_items = [
//     wood_debris_materials,
//     stone_debris_materials,
//     environment_debris_underground,
//     mining_debris_remnants,
// ];
//
// const desert_pyramid_debris_items = [
//     sandstone_debris_materials,
//     environment_debris_desert,
//     mechanism_debris_remnants,
// ];
//
// const jungle_temple_debris_items = [
//     stone_debris_materials,
//     environment_debris_overgrown,
//     mechanism_debris_remnants,
// ];
//
// const igloo_debris_items = [
//     snow_debris_materials,
//     environment_debris_frozen,
//     household_debris_remnants,
//     alchemy_debris_remnants,
// ];
//
// const woodland_mansion_debris_items = [
//     dark_oak_debris_materials,
//     household_debris_remnants,
//     study_debris_remnants,
//     combat_debris_remnants,
// ];
//
// const pillager_outpost_debris_items = [
//     dark_oak_debris_materials,
//     pillager_debris_remnants,
//     farming_debris_remnants,
// ];
//
// const nether_fortress_debris_items = [
//     nether_brick_debris_materials,
//     environment_debris_nether,
//     nether_debris_remnants,
// ];
//
// const bastion_remnant_debris_items = [
//     blackstone_debris_materials,
//     environment_debris_nether,
//     piglin_debris_remnants,
// ];
//
// const ruined_portal_debris_items = [
//     portal_debris_materials,
//     environment_debris_nether,
//     portal_debris_remnants,
// ];

