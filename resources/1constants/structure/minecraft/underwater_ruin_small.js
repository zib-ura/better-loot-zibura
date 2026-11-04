// ============================================================================
// 海底遗迹 v7：按参考文件的 content + groupWeight + min/max 结构生成
// 场景仅用于保证物品来源合理、启发扩展；不要求玩家识别故事。
// 不预设建筑用途，也不预设物品出现于废墟形成之前。
// ============================================================================

// 建筑材料与海洋环境复用原有物品数组。
// 固定执行一次基础场景抽取，并非每件基础物品都必出。
const underwater_ruin_cold_base_scenes = [
    {
        groupName: "cold_structure",
        groupWeight: 20,
        min: 1, max: 2,
        items: stone_debris_materials
    },

    {
        groupName: "seabed",
        groupWeight: 20,
        min: 1, max: 2,
        items: environment_debris_ocean
    },

    {
        groupName: "lost_everyday_objects",
        groupWeight: 30,
        min: 1, max: 2,
        items: household_debris_remnants
    },

];

const underwater_ruin_warm_base_scenes = [
    {
        groupName: "warm_structure",
        groupWeight: 20,
        min: 1, max: 2,
        items: sandstone_debris_materials
    },

    {
        groupName: "seabed",
        groupWeight: 20,
        min: 1, max: 2,
        items: environment_debris_ocean
    },

    {
        groupName: "lost_everyday_objects",
        groupWeight: 30,
        min: 1, max: 2,
        items: household_debris_remnants
    },
];

// 可能场景：每次 roll 按 groupWeight 选择一个组，再按组内 min/max 与 ratio 抽物品。
// 同一物品可属于多个合理场景；不额外强制组合或去重。
const underwater_ruin_optional_scenes = [
    // 捕鱼与采集活动
    {
        groupName: "fishing_stop",
        groupWeight: 15,
        min: 1, max: 2,
        items: fishing_debris_remnants
    },

    // 经过遗迹的人遗失的导航用品
    {
        groupName: "wayfarer",
        groupWeight: 20,
        min: 1, max: 2,
        items: navigation_debris_remnants
    },

    // 废墟搜寻、挖掘与材料回收
    {
        groupName: "salvage",
        groupWeight: 10,
        min: 1, max: 2,
        items: [
            { id: "minecraft:stone_pickaxe", ratio: 10 },
            { id: "minecraft:stone_shovel", ratio: 10 },
        ]
    },

    // 溺尸活动留下的物品
    {
        groupName: "drowned_presence",
        groupWeight: 20,
        min: 1, max: 2,
        items: [
            { id: "minecraft:rotten_flesh", ratio: 10 },
            { id: "minecraft:bone", ratio: 10 },
            { id: "minecraft:stone_spear", ratio: 10 },
        ]
    },

    // 鹦鹉螺骑乘者经过或停留
    {
        groupName: "nautilus_passage",
        groupWeight: 10,
        min: 1, max: 2,
        items: [
            { id: "minecraft:pufferfish", ratio: 10 },
            { id: "minecraft:saddle", ratio: 10 },
            { id: "minecraft:lead", ratio: 10 },
            { id: "minecraft:nautilus_shell", ratio: 10 },
            { id: "minecraft:copper_nautilus_armor", ratio: 10 },
            { id: "minecraft:iron_nautilus_armor", ratio: 10 },
            { id: "minecraft:golden_nautilus_armor", ratio: 10 },
            { id: "minecraft:diamond_nautilus_armor", ratio: 10 },
        ]
    },

    // 散落或遗失的财物
    {
        groupName: "scattered_valuables",
        groupWeight: 10,
        min: 1, max: 2,
        items: [
            { id: "minecraft:emerald", ratio: 10 },
            { id: "minecraft:gold_nugget", ratio: 10 },
            { id: "minecraft:golden_helmet", ratio: 10 },
        ]
    },

];


// 大型遗迹特殊奖励：80 / 20 是两组之间的相对权重。
const underwater_ruin_big_special = [
    {
        groupName: "protected_find",
        groupWeight: 80,
        min: 1, max: 2,
        items: [
            { id: "minecraft:gold_ingot", ratio: 10 },
            { id: "minecraft:golden_apple", ratio: 10 },
            // { id: "minecraft:enchanted_book", ratio: 10 },
        ]
    },

    {
        groupName: "enchanted_book",
        groupWeight: 20,
        min: 1, max: 1,
        items: [
            { id: "minecraft:book", ratio: 10, enchantRandomly: ["minecraft:aqua_affinity"] },
            { id: "minecraft:book", ratio: 10, enchantRandomly: ["minecraft:depth_strider"] },
            { id: "minecraft:book", ratio: 10, enchantRandomly: ["minecraft:luck_of_the_sea"] },
            { id: "minecraft:book", ratio: 10, enchantRandomly: ["minecraft:lure"] },
            { id: "minecraft:book", ratio: 10, enchantRandomly: ["minecraft:respiration"] },
            { id: "minecraft:book", ratio: 10, enchantRandomly: ["minecraft:channeling"] },
            { id: "minecraft:book", ratio: 10, enchantRandomly: ["minecraft:impaling"] },
            { id: "minecraft:book", ratio: 10, enchantRandomly: ["minecraft:loyalty"] },
            { id: "minecraft:book", ratio: 10, enchantRandomly: ["minecraft:riptide"] },
            { id: "minecraft:book", ratio: 10, enchantRandomly: ["kattersstructures:ocean_heart"] },

        ]
    },
];

// 藏宝图独立物品池，按需要在 content 中单独调用。
const underwater_ruin_treasure_map = [
    {
        groupName: "treasure_map",
        groupWeight: 50,
        min: 1, max: 1,
        items: treasure_map_item
    },
    {
        groupName: "empty",
        groupWeight: 50,
        items: [
            { id: "empty" },
        ]
    },
];

const underwater_ruin_small_content = [
    [underwater_ruin_warm_base_scenes, 1, 1, { matchBiome: "#minecraft:has_structure/ocean_ruin_warm" }],
    [underwater_ruin_cold_base_scenes, 1, 1, { matchBiome: "#minecraft:has_structure/ocean_ruin_cold" }],
    [underwater_ruin_optional_scenes, 1, 1],
    [underwater_ruin_treasure_map, 1, 1],
];

const underwater_ruin_big_content = [
    [underwater_ruin_warm_base_scenes, 1, 1, { matchBiome: "#minecraft:has_structure/ocean_ruin_warm" }],
    [underwater_ruin_cold_base_scenes, 1, 1, { matchBiome: "#minecraft:has_structure/ocean_ruin_cold" }],
    [underwater_ruin_optional_scenes, 1, 2],
    [underwater_ruin_big_special, 1, 1],
    [underwater_ruin_treasure_map, 1, 1],
];


const loot_underwater_ruin_small = [
    [underwater_ruin_small_content, 1]
];


const loot_underwater_ruin_big = [
    [underwater_ruin_big_content, 1]
];
