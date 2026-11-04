const village_profession_item = [
    {
        groupName: "shepherd and tannery",
        groupWeight: 25,
        min: 1,  max: 1,
        items: [
            { id: "minecraft:white_banner", ratio: 10 },
            { id: "minecraft:white_carpet", ratio: 10 },
            { id: "supplementaries:bunting_white", ratio: 10 },
            { id: "supplementaries:flag_white", ratio: 10 },
            { id: "minecraft:white_bed", ratio: 10 },
            { id: "minecraft:painting", ratio: 10 },
            { id: "minecraft:item_frame", ratio: 10 },

        ]
    },


    {
        groupName: "cartographer",
        groupWeight: 25,
        min: 1, max: 1,
        items: [
            { id: "minecraft:map", ratio: 10 },
            { id: "minecraft:compass", ratio: 10 },
            { id: "minecraft:spyglass", ratio: 10 },
            { id: "minecraft:book", ratio: 10 },
            { id: "minecraft:writable_book", ratio: 10 },
            { id: "supplementaries:globe", ratio: 10 },
            { id: "minecraft:clock", ratio: 10 },
            { id: "minecraft:painting", ratio: 10 },
            { id: "minecraft:paper", ratio: 10 },

        ]
    },

    {
        groupName: "mason",
        groupWeight: 25,
        min: 1, max: 1,
        items: [
            { id: "minecraft:furnace", ratio: 10 },
            { id: "minecraft:armor_stand", ratio: 10 },
            { id: "supplementaries:hat_stand", ratio: 10 },
            { id: "supplementaries:statue", ratio: 10 },
            { id: "supplementaries:stone_lamp", ratio: 10 },
            { id: "supplementaries:pedestal", ratio: 10 },

        ]
    },

    {
        groupName: "smith",
        groupWeight: 25,
        min: 1, max: 1,
        items: [

            { id: "minecraft:piston", ratio: 10 },
            { id: "minecraft:lantern", ratio: 10 },
            { id: "minecraft:bucket", ratio: 10 },
            { id: "minecraft:lightning_rod", ratio: 10 },

        ]
    },

    {
        groupName: "empty",
        groupWeight: 100,
        items: [
            { id: "empty" },
        ]
    },
]


// Trade goods grouped by villagers' purchasing purposes.
// Profession items are also eligible; every real item has ratio: 10.

// 价格分级：
// 便宜：Food Storage、Productive
// 中等：Decoration、Travel、Knowledge、Security
// 昂贵：Food Preparation、Furniture、Liquid Wealth
//
// Food Preparation：仅制作 Dish 需要炊具，且炊具可以租借。
// Food Storage：主要根据家庭食物总量决定需求。
// Furniture：已有床，额外家具昂贵。
// Liquid Wealth：代表保留的现金资产，用于体现财富水平。
// Empty：仅保留少量随机性，不用于直接表现贫富。

// Cooking and food preparation
const village_common_food_preparation_trade_goods = [
    { id: "kaleidoscope_cookery:stockpot", ratio: 10 },
    { id: "kaleidoscope_cookery:pot", ratio: 10 },
    { id: "kaleidoscope_cookery:stockpot_lid", ratio: 10 },
    { id: "kaleidoscope_cookery:kitchen_shovel", ratio: 10 },
    { id: "kaleidoscope_cookery:teapot", ratio: 10 },
    { id: "farmersdelight:cooking_pot", ratio: 10 },
    { id: "farmersdelight:skillet", ratio: 10 },
    { id: "trailandtales_delight:pottery_cooking_pot", ratio: 10 },
    { id: "minecraft:bowl", ratio: 10 },
    { id: "minecraft:flint_and_steel", ratio: 10 },

];

// Food storage and transport
const village_plains_food_storage_trade_goods = [
    { id: "minecraft:glass_bottle", ratio: 10 },
    { id: "kaleidoscope_chinesefood:pickle_jar", ratio: 10 },
    { id: "farmersdelight:wooden_basket", ratio: 10 },
    { id: "kaleidoscope_cookery:fruit_basket", ratio: 10 },
    { id: "minecraft:barrel", ratio: 10 },
    // { id: "supplementaries:sack", ratio: 10 },
];

// Food storage and transport
const village_desert_food_storage_trade_goods = [
    { id: "minecraft:glass_bottle", ratio: 10 },
    { id: "minecraft:bowl", ratio: 10 },
];

const village_common_food_storage_trade_goods = [
    { id: "minecraft:glass_bottle", ratio: 10 },
    { id: "farmersdelight:wooden_basket", ratio: 10 },
    { id: "kaleidoscope_cookery:fruit_basket", ratio: 10 },
    { id: "minecraft:barrel", ratio: 10 },
    // { id: "supplementaries:sack", ratio: 10 },
];
// Farming and gathering
const village_common_productive_trade_goods = [
    // 铜制工具
    { id: "minecraft:copper_hoe", ratio: 10 },
    { id: "minecraft:copper_shovel", ratio: 10 },
    { id: "minecraft:copper_axe", ratio: 10 },

    // 铁制工具
    { id: "minecraft:iron_hoe", ratio: 10 },
    { id: "minecraft:iron_shovel", ratio: 10 },
    { id: "minecraft:iron_axe", ratio: 10 },
    // { id: "minecraft:iron_pickaxe", ratio: 10 },
    // { id: "minecraft:shears", ratio: 10 },

    { id: "kaleidoscope_cookery:sickle", ratio: 10 },
    // { id: "farmersdelight:rope", ratio: 10 },
    // { id: "supplementaries:rope", ratio: 10 },
    { id: "minecraft:bucket", ratio: 10 },
];

// household-owned production tools, separated by primary occupation.
const village_farm_productive_trade_goods = [
    // 铜制工具
    { id: "minecraft:copper_hoe", ratio: 10 },
    { id: "minecraft:copper_shovel", ratio: 10 },

    // 铁制工具
    { id: "minecraft:iron_hoe", ratio: 10 },
    { id: "minecraft:iron_shovel", ratio: 10 },

    { id: "kaleidoscope_cookery:sickle", ratio: 10 },
    { id: "minecraft:bucket", ratio: 10 },
];

const village_forest_productive_trade_goods = [
    // 铜制工具
    { id: "minecraft:copper_axe", ratio: 10 },
    { id: "minecraft:copper_shovel", ratio: 10 },

    // 铁制工具
    { id: "minecraft:iron_axe", ratio: 10 },
    { id: "minecraft:iron_shovel", ratio: 10 },
];

const village_herder_productive_trade_goods = [
    // { id: "minecraft:shears", ratio: 10 },
    { id: "minecraft:lead", ratio: 10 },
    { id: "minecraft:bucket", ratio: 10 },

    // 铜制工具
    { id: "minecraft:copper_axe", ratio: 10 },

    // 铁制工具
    { id: "minecraft:iron_axe", ratio: 10 },
];

const village_fisher_productive_trade_goods = [
    { id: "minecraft:fishing_rod", ratio: 10 },
    { id: "minecraft:bucket", ratio: 10 },
];

const village_hunter_productive_trade_goods = [
    { id: "minecraft:bow", ratio: 20 },
    { id: "minecraft:crossbow", ratio: 10 },

    // 石制武器
    { id: "minecraft:stone_axe", ratio: 10 },
    { id: "minecraft:stone_spear", ratio: 10 },

    // 铜制武器
    { id: "minecraft:copper_axe", ratio: 10 },
    { id: "minecraft:copper_spear", ratio: 10 },

    // 铁制武器
    { id: "minecraft:iron_axe", ratio: 10 },
    { id: "minecraft:iron_spear", ratio: 10 },
];

// Travel and carrying belongings
const village_common_travel_trade_goods = [
    { id: "minecraft:bundle", ratio: 20 },
    { id: "travelersbackpack:standard", ratio: 2 },
    { id: "minecraft:saddle", ratio: 2 },
    { id: "minecraft:map", ratio: 10 },
    { id: "minecraft:compass", ratio: 10 },
    { id: "minecraft:spyglass", ratio: 10 },
    { id: "minecraft:brush", ratio: 10 },
];

// Reading, writing and record keeping
const village_common_knowledge_trade_goods = [
    // { id: "minecraft:paper", ratio: 10 },
    { id: "minecraft:book", ratio: 10 },
    { id: "minecraft:writable_book", ratio: 10 },

];


// Personal and village defense
const village_common_security_trade_goods = [
    { id: "minecraft:shield", ratio: 10 },
    { id: "minecraft:bow", ratio: 10 },
    { id: "minecraft:crossbow", ratio: 10 },
    { id: "supplementaries:slingshot", ratio: 10 },
    { id: "supplementaries:quiver", ratio: 10 },

    // 石制武器
    { id: "minecraft:stone_sword", ratio: 10 },
    { id: "minecraft:stone_spear", ratio: 10 },

    // 铜制武器
    { id: "minecraft:copper_sword", ratio: 10 },
    { id: "minecraft:copper_spear", ratio: 10 },

    // 铁制武器
    { id: "minecraft:iron_sword", ratio: 10 },
    { id: "minecraft:iron_spear", ratio: 10 },

    // 铜甲系列
    { id: "minecraft:copper_helmet", ratio: 10 },
    { id: "minecraft:copper_chestplate", ratio: 10 },

    // 铁甲系列
    { id: "minecraft:iron_helmet", ratio: 10 },
    { id: "minecraft:iron_chestplate", ratio: 10 },

    // 链甲系列
    { id: "minecraft:chainmail_helmet", ratio: 10 },
    { id: "minecraft:chainmail_chestplate", ratio: 10 },
];

// const village_household_trade_goods =[]
// const village_productive_trade_goods=[]
// const village_textile_leather_trade_goods=[]
// const village_knowledge_trade_goods=[]
// const village_security_trade_goods=[]




// Household living, comfort and utilities
// Furniture trade goods are generated from biome, color and wood templates below.

// Decoration — Carpenter-style recursive template/output generation.
// Reuse shepherdWoolColorTemplates (four colored items plus painting).
// The shepherd template inherits {color} from the enclosing biome color expansion.
// Grouped biome array parameters -> selected full template -> biome color array.
// Each item type appears once per color; uncolored IDs intentionally repeat.
// Uncolored entries intentionally repeat once per color. Patterned carpet is its own type.
const village_decoration_common_items_template = [
    { template: "shepherdWoolColorTemplates" },

    { id: "minecraft:flower_pot", ratio: 10 },
    { id: "minecraft:item_frame", ratio: 10 },
    { id: "minecraft:lantern", ratio: 10 },
    { id: "minecraft:copper_lantern", ratio: 10 },
    { id: "minecraft:clock", ratio: 10 },

    { id: "supplementaries:statue", ratio: 10 },
    { id: "supplementaries:pedestal", ratio: 10 },
    { id: "minecraft:armor_stand", ratio: 10 },
    { id: "supplementaries:hat_stand", ratio: 10 },
    { id: "supplementaries:stone_lamp", ratio: 10 },
];

const village_decoration_plains_items_template = [
    { template: "shepherdWoolColorTemplates" },

    { id: "brewery:patterned_carpet", ratio: 10 },

    { id: "minecraft:flower_pot", ratio: 10 },
    { id: "minecraft:item_frame", ratio: 10 },
    { id: "minecraft:lantern", ratio: 10 },
    { id: "minecraft:copper_lantern", ratio: 10 },
    { id: "minecraft:candle", ratio: 10 },
    { id: "minecraft:clock", ratio: 10 },

    { id: "supplementaries:statue", ratio: 10 },
    { id: "supplementaries:pedestal", ratio: 10 },
    { id: "minecraft:armor_stand", ratio: 10 },
    { id: "supplementaries:hat_stand", ratio: 10 },
    { id: "supplementaries:stone_lamp", ratio: 10 },

];

const village_decoration_desert_items_template = [
    { template: "shepherdWoolColorTemplates" },

    { id: "minecraft:flower_pot", ratio: 10 },
    { id: "minecraft:item_frame", ratio: 10 },
    { id: "minecraft:lantern", ratio: 10 },
    { id: "minecraft:copper_lantern", ratio: 10 },
    { id: "minecraft:clock", ratio: 10 },

    { id: "minecraft:glass", ratio: 10 },
    { id: "minecraft:chiseled_sandstone", ratio: 10 },

];

// Shared wrapper: expand the full item template once for every biome color.
const village_decoration_regular_template = [
    { template: "village_decoration_common_items_template", param: { color: "village_{biome}_colors" } }
];

const village_decoration_plains_template = [
    { template: "village_decoration_plains_items_template", param: { color: "village_{biome}_colors" } }
];

const village_decoration_desert_template = [
    { template: "village_decoration_desert_items_template", param: { color: "village_{biome}_colors" } }
];

// Like carpenter_contents: shared biomes and special biomes use separate calls.
const village_decoration_regular_biomes = [
    "savanna", "taiga", "snowy", "badlands", "beach", "cherry",
    "dark_forest", "jungle", "mushroom", "ocean", "swamp"
];
const village_decoration_special_biomes = ["plains", "desert"];

const village_decoration_generation = [
    { template: "village_decoration_regular_template", param: { biome: "village_decoration_regular_biomes" }, output: "village_{biome}_decoration_trade_goods" },
    { template: "village_decoration_{biome}_template", param: { biome: "village_decoration_special_biomes" }, output: "village_{biome}_decoration_trade_goods" }
];


// Furniture trade templates (only this category is changed).

const villageTradeBedTemplate = [{ id: "minecraft:{color}_bed", ratio: 5 }];

const villageTradeFurnitureTemplate = [
    { template: "villageTradeBedTemplate", param: { color: "village_{biome}_colors" } },
    { id: "minecraft:chest", ratio: 10 },
    { id: "minecraft:furnace", ratio: 10 },
    { template: "carpenterFurnitureTemplates", param: { wood: "village_{biome}_woods" } }
];

// Eager compilation creates village_{biome}_furniture_trade_goods registries.
const village_trade_furniture_generation = [
    {
        template: "villageTradeFurnitureTemplate",
        param: { biome: "village_biomes" },
        output: "village_{biome}_furniture_trade_goods"
    }
];
