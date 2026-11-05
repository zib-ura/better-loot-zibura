const common_pickaxe = [
    { id: "minecraft:iron_pickaxe", ratio: 10, max: 1 },
    { id: "minecraft:copper_pickaxe", ratio: 10, max: 1 },
    { id: "minecraft:stone_pickaxe", ratio: 10, max: 1 },
];

const common_axe = [
    { id: "minecraft:iron_axe", ratio: 10, max: 1 },
    { id: "minecraft:copper_axe", ratio: 10, max: 1 },
    { id: "minecraft:stone_axe", ratio: 10, max: 1 },
];

const common_shovel = [
    { id: "minecraft:iron_shovel", ratio: 10, max: 1 },
    { id: "minecraft:copper_shovel", ratio: 10, max: 1 },
    { id: "minecraft:stone_shovel", ratio: 10, max: 1 },
];

const common_hoe = [
    { id: "minecraft:iron_hoe", ratio: 10, max: 1 },
    { id: "minecraft:copper_hoe", ratio: 10, max: 1 },
    { id: "minecraft:stone_hoe", ratio: 10, max: 1 },
];

const all_common_tools = [
    common_hoe,
    common_shovel,
    common_axe,
    common_pickaxe,
];

const all_rare_material_tools = [
    { id: "minecraft:diamond_sword", ratio: 10 },
    { id: "minecraft:diamond_axe", ratio: 10 },
    { id: "minecraft:diamond_pickaxe", ratio: 10 },
    { id: "minecraft:diamond_shovel", ratio: 10 },
    { id: "minecraft:diamond_hoe", ratio: 10 },
]
const common_farming_tools = [
    common_hoe,
    common_shovel,

    { id: "kaleidoscope_cookery:sickle", ratio: 10, max: 1 },
    { id: "minecraft:water_bucket", ratio: 10, max: 1 },
    { id: "minecraft:bucket", ratio: 10, max: 1 },
];

const common_cartography_tools = [
    { id: "minecraft:compass", ratio: 10, max: 1 },
    { id: "naturescompass:naturescompass", ratio: 3, max: 1 },
    { id: "explorerscompass:explorerscompass", ratio: 3, max: 1 },
];

const common_butcher_tools = [
    common_sword,
    common_axe,

    { reference: "lootjs:small_iron_knife", ratio: 10, max: 1 },
    { id: "farmersdelight:copper_knife", ratio: 10, max: 1 },
    { id: "farmersdelight:cutting_board", ratio: 10, max: 1 },

    { id: "kaleidoscope_cookery:iron_kitchen_knife", ratio: 10, max: 1 },
    { id: "kaleidoscope_cookery:copper_kitchen_knife", ratio: 10, max: 1 },
    { id: "kaleidoscope_cookery:chopping_board", ratio: 10, max: 1 },
];

const common_fisher_tools = [
    { id: "empty", ratio: 1 },

    { id: "minecraft:fishing_rod", ratio: 10, max: 1 },
    { id: "minecraft:bucket", ratio: 10, max: 1 },
    { id: "minecraft:water_bucket", ratio: 10, max: 1 },

    // { id: "farmersdelight:iron_knife", ratio: 10, max: 1 },
    // { id: "farmersdelight:cutting_board", ratio: 10, max: 1 },
    //
    // { id: "kaleidoscope_cookery:iron_kitchen_knife", ratio: 10, max: 1 },
    // { id: "kaleidoscope_cookery:chopping_board", ratio: 10, max: 1 },
];

const common_cooking_tools = [
    { id: "kaleidoscope_cookery:stockpot", ratio: 10, max: 1 },
    { id: "kaleidoscope_cookery:pot", ratio: 10, max: 1 },
    { id: "kaleidoscope_cookery:stockpot_lid", ratio: 10, max: 1 },
    { id: "kaleidoscope_cookery:kitchen_shovel", ratio: 10, max: 1 },
    { id: "kaleidoscope_cookery:teapot", ratio: 10, max: 1 },

    { id: "farmersdelight:cooking_pot", ratio: 10, max: 1 },
    { id: "farmersdelight:skillet", ratio: 10, max: 1 },

    // { id: "youkaisfeasts:stockpot", ratio: 10, max: 1 },
    // { id: "youkaisfeasts:short_iron_pot", ratio: 10, max: 1 },
    // { id: "youkaisfeasts:small_iron_pot", ratio: 10, max: 1 },
    // { id: "youkaisfeasts:steamer_pot", ratio: 10, max: 1 },
    // { id: "youkaisfeasts:kettle", ratio: 10, max: 1 },

    { id: "trailandtales_delight:pottery_cooking_pot", ratio: 10, max: 1 },
];

const common_herder_tools = [
    { id: "minecraft:lead", ratio: 10 },
    { id: "minecraft:bucket", ratio: 10, max: 1 },
];

const common_shepherd_tools = [
    { id: "minecraft:shears", ratio: 10, max: 1 },
    { id: "minecraft:lead", ratio: 10 },
    { id: "woodenshears:wooden_shears", ratio: 10, max: 1 },
];



const common_utility_tools = [
    { id: "minecraft:flint_and_steel", ratio: 10, max: 1 },
    { id: "minecraft:shears", ratio: 10, max: 1 },
    { id: "minecraft:bucket", ratio: 10, max: 1 },
    { id: "minecraft:brush", ratio: 10, max: 1 },
    { id: "minecraft:spyglass", ratio: 10, max: 1 },
];

const common_climbing_items = [
    { id: "minecraft:ladder", ratio: 20 },
    { id: "minecraft:scaffolding", ratio: 10 },
    // { id: "minecraft:vine", ratio: 10 },

    { id: "farm_and_charm:sturdy_ladder", ratio: 10 },
    { id: "create:copper_ladder", ratio: 10 },
    { id: "farmersdelight:safety_net", ratio: 10 },
    { id: "supplementaries:rope_arrow", ratio: 10 },
];

const common_lighting_items = [
    { id: "minecraft:torch", ratio: 20 },
    { id: "minecraft:lantern", ratio: 10 },
    { id: "minecraft:copper_lantern", ratio: 10 },
    { id: "minecraft:candle", ratio: 10 },
    // { id: "minecraft:white_candle", ratio: 10 },
    // { id: "minecraft:redstone_torch", ratio: 10 },
    // { id: "minecraft:glow_berries", ratio: 10 },
];

const common_transport_items = [
    { id: "minecraft:saddle", ratio: 5, max: 1 },
    { id: "minecraft:lead", ratio: 10 },
    { id: "minecraft:oak_boat", ratio: 10, max: 1 },
    // { id: "minecraft:minecart", ratio: 10, max: 1 },
    // { id: "minecraft:chest_minecart", ratio: 5, max: 1 },
];

const common_mobility_items = [
    { id: "minecraft:ender_pearl", ratio: 10, max: 1 },
    // { id: "minecraft:wind_charge", ratio: 10 },
    // { id: "minecraft:slime_block", ratio: 10 },
    { id: "minecraft:water_bucket", ratio: 10, max: 1 },
    // { id: "minecraft:hay_block", ratio: 10 },
];

const common_basic_materials = [
    { id: "minecraft:stick", ratio: 20 },
    { id: "minecraft:string", ratio: 10 },
    { id: "minecraft:flint", ratio: 10 },
    { id: "minecraft:iron_ingot", ratio: 10 },
    { id: "minecraft:copper_ingot", ratio: 10 },
    { id: "minecraft:oak_planks", ratio: 10 },
    { id: "minecraft:oak_log", ratio: 5 },
];

const common_survival_supplies = [
    { id: "minecraft:bread", ratio: 20 },
    { id: "minecraft:baked_potato", ratio: 10 },
    { id: "minecraft:dried_kelp", ratio: 10 },
    { id: "minecraft:cookie", ratio: 10 },
    { id: "minecraft:honey_bottle", ratio: 10, max: 1 },
    { id: "minecraft:golden_carrot", ratio: 5 },
];

const common_storage_items = [
    { id: "minecraft:bundle", ratio: 20, max: 1 },
    { id: "minecraft:chest", ratio: 10 },
    { id: "minecraft:barrel", ratio: 10 },
    { id: "travelersbackpack:standard", ratio: 2, max: 1 },
];

const common_exploration_tools = [
    common_lighting_items,
    common_climbing_items,
    common_utility_tools,
    common_transport_items,
    common_storage_items,
    common_cartography_tools,
];

