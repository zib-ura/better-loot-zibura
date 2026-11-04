// Carpenter — wood-based recursive template proposal. No biome mappings or JS functions.

// param: exactly one key, whose value is an array or a registry name resolving to an array.

// Nested template calls inherit wood; output may appear inside content entries.

// Requires the NEW Java recursive template/output resolver (not supported by the current parser).



const carpenter_woods = ["oak", "spruce", "birch", "jungle", "acacia", "dark_oak", "cherry", "pale_oak", "poplar"];

const carpenter_bamboo_woods = ["bamboo"];

const carpenter_mangrove_woods = ["mangrove"];


const carpenterUtilityTemplates = [
    { id: "minecraft:chest", ratio: 10 },
    { id: "lolmcv:{wood}_chest", ratio: 10 },
    { id: "minecraft:crafting_table", ratio: 10 },
    { id: "minecraft:barrel", ratio: 10 },
    { id: "minecraft:loom", ratio: 10 },
    { id: "minecraft:fletching_table", ratio: 10 },
    { id: "minecraft:cartography_table", ratio: 10 },
    { id: "minecraft:composter", ratio: 10 },
    { id: "minecraft:chiseled_bookshelf", ratio: 10 },
    { id: "minecraft:lectern", ratio: 10 },
    { id: "minecraft:bowl", ratio: 10 },
    { id: "minecraft:ladder", ratio: 10 },
    { id: "farm_and_charm:sturdy_ladder", ratio: 10 },
    { id: "woodenshears:wooden_shears", ratio: 10 },
    { id: "farmersdelight:wooden_basket", ratio: 10 },
    { id: "kaleidoscope_cookery:fruit_basket", ratio: 10 }
];

const carpenterFurnitureTemplates = [
    { id: "minecraft:{wood}_door", ratio: 10 },
    { id: "minecraft:{wood}_trapdoor", ratio: 10 },
    { id: "minecraft:{wood}_sign", ratio: 10 },
    { id: "minecraft:{wood}_hanging_sign", ratio: 10 },
    { id: "minecraft:{wood}_shelf", ratio: 10 },
    { id: "kaleidoscope_cookery:table_{wood}", ratio: 10 },
    { id: "kaleidoscope_cookery:chair_{wood}", ratio: 10 },
    { id: "kaleidoscope_cookery:cook_stool_{wood}", ratio: 10 },
    { id: "farmersdelight:{wood}_cabinet", ratio: 10 },
    { id: "kaleidoscope_tavern:stepladder", ratio: 10 },
    { id: "kaleidoscope_chinesefood:bowl_stack", ratio: 10 },
    { id: "kaleidoscope_cookery:kitchenware_racks", ratio: 10 },
    { id: "youkaisfeasts:{wood}_dining_table", ratio: 10 },
    { id: "storagedelight:{wood}_single_door_cabinet", ratio: 10 },
    { id: "storagedelight:{wood}_cabinet_with_glass_doors", ratio: 10 },
    { id: "storagedelight:glass_{wood}_cabinet", ratio: 10 },
    { id: "storagedelight:small_{wood}_drawers", ratio: 10 },
    { id: "storagedelight:{wood}_bookshelf_with_door", ratio: 10 },
    { id: "storagedelight:{wood}_drawer_with_books", ratio: 10 },
    { id: "storagedelight:{wood}_drawer_with_door", ratio: 10 },
    { id: "storagedelight:{wood}_drawer", ratio: 10 },
    { id: "handcrafted:{wood}_shelf", ratio: 10 },
    { id: "handcrafted:{wood}_table", ratio: 10 },
    { id: "handcrafted:{wood}_side_table", ratio: 10 },
    { id: "handcrafted:{wood}_bench", ratio: 10 },
    { id: "handcrafted:{wood}_drawer", ratio: 10 },
    { id: "handcrafted:{wood}_counter", ratio: 10 },
    { id: "handcrafted:{wood}_desk", ratio: 10 },
    { id: "handcrafted:{wood}_chair", ratio: 10 },
    { id: "handcrafted:{wood}_cupboard", ratio: 10 },
    { id: "handcrafted:{wood}_fancy_bed", ratio: 10 },
    { id: "handcrafted:{wood}_nightstand", ratio: 10 },
    { id: "handcrafted:{wood}_dining_bench", ratio: 10 },
    { id: "beautify:{wood}_blinds", ratio: 10 },
    { id: "beautify:{wood}_picture_frame", ratio: 10 }
];

const carpenterMaterialTemplates = [
    { id: "minecraft:{wood}_log", ratio: 15 },
    { id: "minecraft:{wood}_planks", ratio: 20 },
    { id: "minecraft:stick", ratio: 15 },
];

const carpenterSaplingTemplates = [
    { id: "minecraft:{wood}_sapling", ratio: 10 },
];

const carpenterAxesTemplates = [
    { id: "minecraft:stone_axe", ratio: 30, damage: [0.3, 0.4] },
    { id: "minecraft:iron_axe", ratio: 10, damage: [0.5, 0.6] },
];

const carpenterSawmillTemplates = [
    { id: "sawmill:sawmill", ratio: 10 },
];

const carpenterBambooMaterials = [
    { id: "minecraft:bamboo_block", ratio: 15 },
    { id: "minecraft:bamboo_planks", ratio: 20 },
    { id: "minecraft:stick", ratio: 15 },
];

const carpenterBambooSapling = [
    { id: "minecraft:bamboo", ratio: 10 },
];

const carpenterMangroveSapling = [
    { id: "minecraft:mangrove_propagule", ratio: 10 },
];


const carpenter_utility_group_template = [
    {
        groupName: "utility",
        groupWeight: 100,
        min: 1, max: 1,
        items: [
            { template: "carpenterUtilityTemplates" },
        ]
    },
];

const carpenter_furniture_group_template = [
    {
        groupName: "furniture",
        groupWeight: 100,
        min: 1, max: 1,
        items: [
            { template: "carpenterFurnitureTemplates" },
        ]
    },
];

const carpenter_materials_group_template = [
    {
        groupName: "materials",
        groupWeight: 100,
        min: 2, max: 3,
        items: [
            { template: "carpenterMaterialTemplates" },
        ]
    },
];

const carpenter_sapling_group_template = [
    {
        groupName: "sapling",
        groupWeight: 100,
        min: 1, max: 1,
        items: [
            { template: "carpenterSaplingTemplates" },
        ]
    },
];

const carpenter_axes_group_template = [
    {
        groupName: "axes",
        groupWeight: 100,
        min: 1, max: 1,
        items: [
            { template: "carpenterAxesTemplates" },
        ]
    },
];

const carpenter_sawmills_group_template = [
    {
        groupName: "sawmills",
        groupWeight: 100,
        min: 1, max: 1,
        items: [
            { template: "carpenterSawmillTemplates" },
        ]
    },
];


const carpenter_bamboo_materials_group_template = [
    {
        groupName: "materials",
        groupWeight: 100,
        min: 2, max: 3,
        items: [
            "carpenterBambooMaterials"
        ]
    },
];

const carpenter_bamboo_sapling_group_template = [
    {
        groupName: "sapling",
        groupWeight: 100,
        min: 1, max: 1,
        items: [
            "carpenterBambooSapling"
        ]
    },
];

const carpenter_mangrove_sapling_group_template = [
    {
        groupName: "sapling",
        groupWeight: 100,
        min: 1, max: 1,
        items: [
            "carpenterMangroveSapling"
        ]
    },
];


const carpenter_content_template = [
    [{ template: "carpenter_utility_group_template", output: "carpenter_{wood}_pools_utility" }, 1, 2],
    [{ template: "carpenter_furniture_group_template", output: "carpenter_{wood}_pools_furniture" }, 1, 2],
    [{ template: "carpenter_materials_group_template", output: "carpenter_{wood}_pools_materials" }, 2, 3],
    [{ template: "carpenter_sapling_group_template", output: "carpenter_{wood}_pools_sapling" }, 1, 2],
    [{ template: "carpenter_axes_group_template", output: "carpenter_{wood}_pools_axes" }, 1, 2],
    [{ template: "carpenter_sawmills_group_template", output: "carpenter_{wood}_pools_sawmills" }, 1, 1],
];


const carpenter_content_template_bamboo = [
    [{ template: "carpenter_utility_group_template", output: "carpenter_{wood}_pools_utility" }, 1, 2],
    [{ template: "carpenter_furniture_group_template", output: "carpenter_{wood}_pools_furniture" }, 1, 2],
    [{ template: "carpenter_bamboo_materials_group_template", output: "carpenter_{wood}_pools_materials" }, 2, 3],
    [{ template: "carpenter_bamboo_sapling_group_template", output: "carpenter_{wood}_pools_sapling" }, 1, 2],
    [{ template: "carpenter_axes_group_template", output: "carpenter_{wood}_pools_axes" }, 1, 2],
    [{ template: "carpenter_sawmills_group_template", output: "carpenter_{wood}_pools_sawmills" }, 1, 1],
];


const carpenter_content_template_mangrove = [
    [{ template: "carpenter_utility_group_template", output: "carpenter_{wood}_pools_utility" }, 1, 2],
    [{ template: "carpenter_furniture_group_template", output: "carpenter_{wood}_pools_furniture" }, 1, 2],
    [{ template: "carpenter_materials_group_template", output: "carpenter_{wood}_pools_materials" }, 2, 3],
    [{ template: "carpenter_mangrove_sapling_group_template", output: "carpenter_{wood}_pools_sapling" }, 1, 2],
    [{ template: "carpenter_axes_group_template", output: "carpenter_{wood}_pools_axes" }, 1, 2],
    [{ template: "carpenter_sawmills_group_template", output: "carpenter_{wood}_pools_sawmills" }, 1, 1],
];


// Top-level output creates one independent content per wood.

// Inner output creates reusable group registry entries for that same wood.

const carpenter_contents = [

    { template: "carpenter_content_template", param: { wood: "carpenter_woods" }, output: "carpenter_{wood}" },

    { template: "carpenter_content_template_bamboo", param: { wood: "carpenter_bamboo_woods" }, output: "carpenter_{wood}" },

    { template: "carpenter_content_template_mangrove", param: { wood: "carpenter_mangrove_woods" }, output: "carpenter_{wood}" },

];


