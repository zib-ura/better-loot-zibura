const woodland_mansion_weapon = [
    {
        groupName: "Combat Gear",
        groupWeight: 30,
        min: 1, max: 1, enchantChance: 0.0,  damage: [0.5, 1],
        items: [
            { id: 'minecraft:iron_axe', ratio: 10 },
            { id: 'minecraft:iron_sword', ratio: 10 },
            { id: 'minecraft:crossbow', ratio: 10 },

            // { id: 'minecraft:iron_ingot', ratio: 5 },
            // { id: 'minecraft:stick', ratio: 5 },


        ]
    },
];


const woodland_mansion_Treasure = [
    {
        groupName: "treasure",
        groupWeight: 30,
        min: 1, max: 2,
        items: [
            { id: 'minecraft:totem_of_undying', ratio: 5 },
            { id: 'minecraft:enchanted_golden_apple', ratio: 5 },
            { id: 'minecraft:golden_apple', ratio: 10 }, 
            { id: 'minecraft:ender_pearl', ratio: 10 }, 
            { id: 'minecraft:vex_armor_trim_smithing_template', ratio: 10 },
 
            { id: 'touhou_little_maid:ultramarine_orb_elixir', ratio: 5 },

        ]
    },

    {
        groupName: "diamond_equipment_armors",
        groupWeight: 20,
        min: 1, max: 1,
        items: [
            { id: 'minecraft:diamond_helmet', ratio: 5 },
            { id: 'minecraft:diamond_chestplate', ratio: 5 },
            { id: 'minecraft:diamond_leggings', ratio: 5 },
            { id: 'minecraft:diamond_boots', ratio: 5 },
        ]
    },

];

const woodland_mansion_ore = [
    {
        groupName: "ingot_block",
        groupWeight: 25,  
        min: 2,
        max: 3,
        items: [
            { reference: 'lootjs:iron_ingot_and_iron_ingot_block', ratio: 10 },
            { reference: 'lootjs:copper_ingot_and_copper_ingot_block', ratio: 10 },
            { reference: 'lootjs:gold_ingot_and_gold_ingot_block', ratio: 10 },
            { reference: 'lootjs:redstone_and_redstone_block', ratio: 10 },
            { reference: 'lootjs:lapis_and_lapis_block', ratio: 10 },
            { reference: 'lootjs:diamond_and_diamond_block', ratio: 5 },
            { reference: 'lootjs:emerald_and_emerald_block', ratio: 10 },
            { reference: 'lootjs:quartz_and_quartz_block', ratio: 10 },
        ]
    },

];


const woodland_mansion_music = [
    {
        groupName: "jukebox",
        groupWeight: 25,  
        min: 1,
        max: 1,
        items: [
            { id: 'minecraft:jukebox', ratio: 10 },
        ]
    },
    {
        groupName: "noteblock",
        groupWeight: 50,
        min: 1,
        max: 1,
        items: [
            { "id": "minecraft:note_block" },
        ]
    },
];

const woodland_mansion_music_discs = [
    {
        groupName: "Music Discs",
        groupWeight: 25,
        min: 1, max: 1,
        items: [
            // { "id": "minecraft:music_disc_pigstep", "ratio": 1},
            { "id": "minecraft:music_disc_chirp", "ratio": 1},
            { "id": "minecraft:music_disc_cat", "ratio": 1},
            { "id": "minecraft:music_disc_mellohi", "ratio": 1},
            { "id": "minecraft:music_disc_far", "ratio": 1},
            { "id": "minecraft:music_disc_blocks", "ratio": 1},
            { "id": "minecraft:music_disc_relic", "ratio": 1},
            { "id": "minecraft:music_disc_strad", "ratio": 1},
            { "id": "minecraft:music_disc_otherside", "ratio": 1},
            { "id": "minecraft:music_disc_stal", "ratio": 1},
            { "id": "minecraft:music_disc_mall", "ratio": 1},
            { "id": "minecraft:music_disc_13", "ratio": 1},
            { "id": "minecraft:music_disc_11", "ratio": 1},
            { "id": "minecraft:music_disc_ward", "ratio": 1},
            { "id": "minecraft:music_disc_wait", "ratio": 1},
            { "id": "minecraft:music_disc_5", "ratio": 1},

            { "id": "supplementaries:music_disc_heave_ho", "ratio": 1},
            { "id": "takesapillage:music_disc_bastille_blues", "ratio": 1},
            { "id": "berry_good:music_disc_fox", "ratio": 1},
            { "id": "aeronautics:music_disc_cloud_skipper", "ratio": 1},
            { "id": "immersiveenchanting:music_disc_biblioclasm", "ratio": 1},
            { "id": "immersiveenchanting:music_disc_arcane_memories", "ratio": 1},
        ]
    },
];

const woodland_mansion_wools_and_dyes = [
    {
        groupName: "wools",
        groupWeight: 50,
        min: 3, max: 5,
        items: [
            { id: 'minecraft:white_wool', ratio: 5 },
            { id: 'minecraft:orange_wool', ratio: 5 },
            { id: 'minecraft:magenta_wool', ratio: 5 },
            { id: 'minecraft:light_blue_wool', ratio: 5 },
            { id: 'minecraft:yellow_wool', ratio: 5 },
            { id: 'minecraft:lime_wool', ratio: 5 },
            { id: 'minecraft:pink_wool', ratio: 5 },
            { id: 'minecraft:gray_wool', ratio: 5 },
            { id: 'minecraft:light_gray_wool', ratio: 5 },
            { id: 'minecraft:cyan_wool', ratio: 5 },
            { id: 'minecraft:purple_wool', ratio: 5 },
            { id: 'minecraft:blue_wool', ratio: 5 },
            { id: 'minecraft:brown_wool', ratio: 5 },
            { id: 'minecraft:green_wool', ratio: 5 },
            { id: 'minecraft:red_wool', ratio: 5 },
            { id: 'minecraft:black_wool', ratio: 5 }
        ]
    },

    {
        groupName: "dyes",
        groupWeight: 50,
        min: 3, max: 5,
        items: [
            { id: 'minecraft:white_dye', ratio: 5 },
            { id: 'minecraft:orange_dye', ratio: 5 },
            { id: 'minecraft:magenta_dye', ratio: 5 },
            { id: 'minecraft:light_blue_dye', ratio: 5 },
            { id: 'minecraft:yellow_dye', ratio: 5 },
            { id: 'minecraft:lime_dye', ratio: 5 },
            { id: 'minecraft:pink_dye', ratio: 5 },
            { id: 'minecraft:gray_dye', ratio: 5 },
            { id: 'minecraft:light_gray_dye', ratio: 5 },
            { id: 'minecraft:cyan_dye', ratio: 5 },
            { id: 'minecraft:purple_dye', ratio: 5 },
            { id: 'minecraft:blue_dye', ratio: 5 },
            { id: 'minecraft:brown_dye', ratio: 5 },
            { id: 'minecraft:green_dye', ratio: 5 },
            { id: 'minecraft:red_dye', ratio: 5 },
            { id: 'minecraft:black_dye', ratio: 5 }
        ]
    },

    {
        groupName: "product",
        groupWeight: 50,
        min: 2, max: 3,
        items: [
            { id: 'minecraft:loom', ratio: 5 , max: 1 },
            { id: 'minecraft:gray_banner', ratio: 5 },
            { id: 'minecraft:black_banner', ratio: 5 },
            { id: 'minecraft:white_banner', ratio: 5 },

        ]
    }
];

const woodland_mansion_alcohol = [
    {
        groupName: "alcohol",
        groupWeight: 25,
        min: 1, max: 1,
        items: [
            { id: "brewinandchewin:dread_nog", "ratio": 1 },
            { id: "kaleidoscope_tavern:rum", "ratio": 1 },
            { id: 'minecraft:ominous_bottle', "ratio": 1 },

        ]
    },
];

const all_village_house_special_dish = [
    {
        groupName: "desert_house",
        groupWeight: 10, 
        min: 2, max: 3,
        items: village_desert_dish.find(group => group.groupName === "special").items
    },
    {
        groupName: "plains_house_europe",
        groupWeight: 10, 
        min: 2, max: 3,
        items: village_plains_dish.find(group => group.groupName === "special europe").items
    },
    {
        groupName: "plains_house_asia",
        groupWeight: 10, 
        min: 2, max: 3,
        items: village_plains_dish.find(group => group.groupName === "special asia").items
    },
    {
        groupName: "savanna_house",
        groupWeight: 10, 
        min: 2, max: 3,
        items: village_savanna_dish.find(group => group.groupName === "special").items
    },
    {
        groupName: "snowy_house",
        groupWeight: 10, 
        min: 2, max: 3,
        items: village_snowy_dish.find(group => group.groupName === "special").items
    },
    // {
    //     groupName: "taiga_house",
    //     groupWeight: 10, 
    //     min: 2, max: 3,
    //     items: village_taiga_dish.find(group => group.groupName === "special").items
    // },
    // {
    //     groupName: "dark_forest_house",
    //     groupWeight: 10, 
    //     min: 2, max: 3,
    //     items: village_dark_forest_dish.find(group => group.groupName === "special").items
    // },
    // {
    //     groupName: "mushroom_house",
    //     groupWeight: 15, 
    //     min: 2, max: 3,
    //     items: village_mushroom_dish.find(group => group.groupName === "special").items
    // },
    // {
    //     groupName: "swamp_house",
    //     groupWeight: 10, 
    //     min: 2, max: 3,
    //     items: village_swamp_dish.find(group => group.groupName === "special").items
    // },
    // {
    //     groupName: "jungle_house",
    //     groupWeight: 10, 
    //     min: 2, max: 3,
    //     items: village_jungle_dish.find(group => group.groupName === "special").items
    // },
    // {
    //     groupName: "ocean_house",
    //     groupWeight: 10, 
    //     min: 2, max: 3,
    //     items: village_ocean_dish.find(group => group.groupName === "special").items
    // }
];


// =================================================================
// 事件注册区域
// =================================================================

// 1. weapon
const woodland_mansion_weapon_content = [
    [woodland_mansion_weapon, 16, 20],
];

// 2. treasure
const woodland_mansion_treasure_content = [
    [woodland_mansion_Treasure, 2, 4],
    [woodland_mansion_ore, 3, 4],
];

// 3. music
const woodland_mansion_music_content = [
    [woodland_mansion_music, 4, 6],
    [woodland_mansion_music_discs, 3, 5],
];

// 4. wools_and_dyes
const woodland_mansion_wools_and_dyes_content = [
    [woodland_mansion_wools_and_dyes, 7, 8],
];

// 5. cuisine
const woodland_mansion_cuisine_content = [
    [all_village_house_special_dish, 6, 7],
    [woodland_mansion_alcohol, 2, 3],
];

const loot_woodland_mansion = [
    [woodland_mansion_weapon_content, 1],
    [woodland_mansion_treasure_content, 1], 
    [woodland_mansion_music_content, 1],
    [woodland_mansion_wools_and_dyes_content, 1],
    [woodland_mansion_cuisine_content, 1],
    [food_supply_content, 1]
];