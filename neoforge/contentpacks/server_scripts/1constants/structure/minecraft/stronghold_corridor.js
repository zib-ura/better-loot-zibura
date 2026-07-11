// =================================================================
// 要塞（Stronghold）战利品表定义区域
// =================================================================


const stronghold_armors_and_weapons = [

    {
        groupName: "common_armors",
        groupWeight: 80,
        min: 1, max: 1, enchantChance: 1, enchantLevels: [20, 30], damage: [0.1, 0.2],
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
        enchantChance: 1, 
        enchantLevels: [20, 30], 
        damage: [0.1, 0.2],
        items: [
            { id: 'immersive_armors:divine_helmet', ratio: 1.5 },
            { id: 'immersive_armors:divine_chestplate', ratio: 1.5 },
            { id: 'immersive_armors:divine_leggings', ratio: 1.5 },
            { id: 'immersive_armors:divine_boots', ratio: 1.5 },

            { id: 'minecraft:diamond_helmet', ratio: 1.5 },
            { id: 'minecraft:diamond_chestplate', ratio: 1.5 },
            { id: 'minecraft:diamond_leggings', ratio: 1.5 },
            { id: 'minecraft:diamond_boots', ratio: 1.5 },
        ]
    },

    {
        groupName: "common_weapons",
        groupWeight: 80,
        min: 1, 
        max: 1, 
        enchantChance: 1, 
        enchantLevels: [20, 30], 
        damage: [0.1, 0.2],
        items: [
            { id: 'minecraft:iron_sword', ratio: 2 },
            { id: 'minecraft:iron_axe', ratio: 2 },
            { id: 'minecraft:stone_sword', ratio: 2 },
            { id: 'minecraft:stone_axe', ratio: 2 },
        ]
    },

    {
        groupName: "rare_weapons", 
        groupWeight: 20,
        min: 1, 
        max: 1, 
        enchantChance: 1, 
        enchantLevels: [20, 30], 
        damage: [0.1, 0.2],
        items: [
            { id: 'minecraft:diamond_sword', ratio: 1.5 },
            { id: 'minecraft:diamond_axe', ratio: 1.5 },
        ]
    },
];


const stronghold_passed_people = [
    {
        groupName: "undead_remains",
        groupWeight: 60,
        min: 1, max: 1,
        items: [
            { id: 'minecraft:rotten_flesh', ratio: 15 }, 
            { id: 'minecraft:bone', ratio: 15 },         
        ]
    },
]

const stronghold_ender_eyes = [
    {
        groupName: "portal_clues",
        groupWeight: 40,
        min: 1, max: 2,
        items: [
            { id: 'minecraft:blaze_powder', ratio: 8 },  
            { id: 'minecraft:ender_pearl', ratio: 8 },  
            { id: 'minecraft:ender_eye', ratio: 8 },     
        ]
    },
];

const stronghold_villager_and_treasure = [
    {
        groupName: "villager_economy",
        groupWeight: 50,
        min: 2, max: 3,
        items: [
            { id: 'minecraft:emerald', ratio: 15 },   
        ]
    },
    {
        groupName: "civilization_blocks",
        groupWeight: 35,
        min: 1, max: 1,
        items: [
            { id: 'minecraft:lectern', ratio: 5 },
            { id: 'minecraft:fletching_table', ratio: 5 },
            { id: 'minecraft:smithing_table', ratio: 5 },
            { id: 'minecraft:blast_furnace', ratio: 5 },
            { id: 'minecraft:stonecutter', ratio: 5 },
            { id: 'minecraft:grindstone', ratio: 5 },
            { id: 'minecraft:brewing_stand', ratio: 5 },
            { id: 'minecraft:cauldron', ratio: 5 },
            { id: 'minecraft:anvil', ratio: 5 },
        ]
    },
]
const stronghold_nametag = [
    {
        groupName: "special_utility",
        groupWeight: 15,
        min: 1, max: 1,
        items: [
            { id: 'minecraft:name_tag', ratio: 10 },    
        ]
    },
];

// =================================================================
// 事件注册区域
// =================================================================

const stronghold_chest_content = [
    [stronghold_armors_and_weapons, 3, 4],              
    [stronghold_passed_people, 5, 6],    
    [stronghold_ender_eyes, 1, 2],                     
    [stronghold_villager_and_treasure, 2, 3],   
    [stronghold_nametag, 2, 3],
];

const loot_stronghold = [
    [stronghold_chest_content, 1],
];