// =================================================================
// 战利品表定义区域
// =================================================================

const simple_dungeon_equipment = [

    {
        groupName: "common_armors",
        groupWeight: 80,
        min: 1, max: 1, enchantChance: 1, enchantLevels: [8, 10], damage: [0.7, 0.8],
        items: [

            { id: 'immersive_armors:bone_helmet', ratio: 6 },
            { id: 'immersive_armors:bone_chestplate', ratio: 6 },
            { id: 'immersive_armors:bone_leggings', ratio: 6 },
            { id: 'immersive_armors:bone_boots', ratio: 6 },

            { id: 'immersive_armors:warrior_helmet', ratio: 2 },
            { id: 'immersive_armors:warrior_chestplate', ratio: 2 },
            { id: 'immersive_armors:warrior_leggings', ratio: 2 },
            { id: 'immersive_armors:warrior_boots', ratio: 2 },

            { id: 'minecraft:iron_helmet', ratio: 2 },
            { id: 'minecraft:iron_chestplate', ratio: 2 },
            { id: 'minecraft:iron_leggings', ratio: 2 },
            { id: 'minecraft:iron_boots', ratio: 2 },

            { id: 'minecraft:chainmail_helmet', ratio: 2 },
            { id: 'minecraft:chainmail_chestplate', ratio: 2 },
            { id: 'minecraft:chainmail_leggings', ratio: 2 },
            { id: 'minecraft:chainmail_boots', ratio: 2 },

            { id: 'immersive_armors:heavy_helmet', ratio: 2 },
            { id: 'immersive_armors:heavy_chestplate', ratio: 2 },
            { id: 'immersive_armors:heavy_leggings', ratio: 2 },
            { id: 'immersive_armors:heavy_boots', ratio: 2 },


        ]
    },

    {
        groupName: "rare_armors",
        groupWeight: 20,
        min: 1, 
        max: 1, 
        enchantChance: 0.7, 
        enchantLevels: [20, 30], 
        damage: [0.2, 0.3],
        items: [
            { id: 'immersive_armors:divine_helmet', ratio: 1.5 },
            { id: 'immersive_armors:divine_chestplate', ratio: 1.5 },
            { id: 'immersive_armors:divine_leggings', ratio: 1.5 },
            { id: 'immersive_armors:divine_boots', ratio: 1.5 },

            { id: 'minecraft:diamond_helmet', ratio: 1.5 },
            { id: 'minecraft:diamond_chestplate', ratio: 1.5 },
            { id: 'minecraft:diamond_leggings', ratio: 1.5 },
            { id: 'minecraft:diamond_boots', ratio: 1.5 }
        ]
    },

    {
        groupName: "common_weapons",
        groupWeight: 80,
        min: 1, 
        max: 1, 
        enchantChance: 0.5, 
        enchantLevels: [10, 15], 
        damage: [0.7, 0.8],
        items: [
            { id: 'minecraft:iron_sword', ratio: 2 },
            { id: 'minecraft:iron_axe', ratio: 2 },
            { id: 'minecraft:stone_sword', ratio: 2 },
            { id: 'minecraft:stone_axe', ratio: 2 }
        ]
    },

    {
        groupName: "rare_weapons", 
        groupWeight: 20,
        min: 1, 
        max: 1, 
        enchantChance: 0.7, 
        enchantLevels: [20, 30], 
        damage: [0.2, 0.3],
        items: [
            { id: 'minecraft:diamond_sword', ratio: 1.5 },
            { id: 'minecraft:diamond_axe', ratio: 1.5 }
        ]
    }
];

const simple_dungeon_resources_treasure = [
    // {
    //     groupName: "ore",
    //     groupWeight: 40,
    //     min: 2, max: 4, 
    //     items: [
    //         { id: 'minecraft:iron_ingot', ratio: 10 },
    //         { id: 'minecraft:coal', ratio: 15 },
    //         { id: 'minecraft:copper_ingot', ratio: 10 },
    //         { id: 'minecraft:lapis_lazuli', ratio: 10 },
    //         { id: 'minecraft:redstone', ratio: 10 },
    //         { id: 'create:zinc_ingot', ratio: 10 },

    //     ]
    // },

    {
        groupName: "drops",
        groupWeight: 50,
        min: 3, max: 4,
        items: [
            { id: 'minecraft:bone', ratio: 10 },
            { id: 'minecraft:gunpowder', ratio: 10 },
            { id: 'minecraft:rotten_flesh', ratio: 10 },
            { id: 'minecraft:string', ratio: 10 },
            { id: 'minecraft:spider_eye', ratio: 10 },
            // { id: 'minecraft:ender_pearl', ratio: 10 },


        ]
    },

    {
        groupName: "treasure",
        groupWeight: 10,
        min: 1, max: 2,
        items: [
            { id: 'minecraft:diamond', ratio: 5 },
            // { id: 'minecraft:emerald', ratio: 20 },
            { id: 'minecraft:gold_ingot', ratio: 10 },
            
            { id: 'minecraft:golden_apple', ratio: 5 },
            { id: 'trailandtales_delight:golden_lantern_fruit', ratio: 5 },
            { id: 'minecraft:enchanted_golden_apple', ratio: 5 },
            { id: 'mynethersdelight:enchanted_golden_egg', ratio: 5 },


        ]
    },
];

const simple_dungeon_supply = [
    // {
    //     groupName: "ore",
    //     groupWeight: 40,
    //     min: 2, max: 4, 
    //     items: [
    //         { id: 'minecraft:iron_ingot', ratio: 10 },
    //         { id: 'minecraft:coal', ratio: 15 },
    //         { id: 'minecraft:copper_ingot', ratio: 10 },
    //         { id: 'minecraft:lapis_lazuli', ratio: 10 },
    //         { id: 'minecraft:redstone', ratio: 10 },
    //         { id: 'create:zinc_ingot', ratio: 10 },

    //     ]
    // },

    {
        groupName: "supply",
        groupWeight: 50,
        min: 3, max: 4,
        items: [
            { id: 'minecraft:torch', ratio: 10 },
            { id: 'minecraft:stick', ratio: 10 },
        ]
    },
];
// =================================================================
// 事件注册区域
// =================================================================
const simple_dungeon_content = [
    [simple_dungeon_equipment, 2, 4],
    [simple_dungeon_resources_treasure, 3, 5],
    [shipwreck_treasure_music_discs, 2, 2],
    [shipwreck_treasure_smithing_templates_common, 2, 2],
    [simple_dungeon_supply, 3, 4],
];

const loot_simple_dungeon = [
    [simple_dungeon_content, 1],
];