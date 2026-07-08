// =================================================================
// 战利品表定义区域
// =================================================================

const vanilla_abandoned_mineshaft_ore = [
    {
        groupName: "common_ore",
        groupWeight: 80, 
        items: [
            { id: 'minecraft:raw_iron', ratio: 10, min: 1, max: 5 },
            { id: 'minecraft:raw_gold', ratio: 5, min: 1, max: 3 },
            { id: 'minecraft:redstone', ratio: 5, min: 4, max: 9 },
            { id: 'minecraft:lapis_lazuli', ratio: 5, min: 4, max: 9 },
            { id: 'minecraft:coal', ratio: 10, min: 3, max: 8 },

            { id: 'create:raw_zinc', ratio: 10, min: 3, max: 8 },

        ]
    },

    {
        groupName: "rare_ore",
        groupWeight: 20, 
        items: [
            { id: 'minecraft:diamond', ratio: 10, min: 1, max: 2 },
        ]
    },
]

const vanilla_abandoned_mineshaft_shaft = [

    {
        groupWeight: 40, 
        items: [
            
            { id: 'minecraft:rail', ratio: 45, min: 4, max: 8 },
            // { id: 'minecraft:powered_rail', ratio: 5, min: 1, max: 4 },
            // { id: 'minecraft:detector_rail', ratio: 5, min: 1, max: 4 },
            // { id: 'minecraft:activator_rail', ratio: 5, min: 1, max: 4 },
            { id: 'minecraft:torch', ratio: 15, min: 8, max: 16 },
            { id: 'minecraft:oak_planks', ratio: 10, min: 4, max: 16 },
            { id: 'minecraft:oak_log', ratio: 10, min: 4, max: 16 },
            { id: 'minecraft:oak_fence', ratio: 5, min: 2, max: 8 },
            { id: 'minecraft:chain', ratio: 5, min: 1, max: 5 },
            { id: 'minecraft:cobweb', ratio: 10, min: 1, max: 3 }
        ]
    },
]


const vanilla_abandoned_mineshaft_equipment = [
    {
        groupName: "common_equipment",
        groupWeight: 90,
        min: 1, max: 1,
        items: [        
            { id: 'minecraft:iron_pickaxe', ratio: 5, enchantChance: 0 , damage: [0.4, 0.6] },
            { id: 'minecraft:iron_shovel', ratio: 5, enchantChance: 0, damage: [0.4, 0.6] },
            { id: 'minecraft:lantern', ratio: 5 },
        ]
    },

    {
        groupName: "rare_equipment",
        groupWeight: 10,
        min: 1, max: 1,
        items: [
            { id: 'minecraft:diamond_pickaxe', ratio: 1, enchantChance: 1.0, enchantLevels: [20, 30], damage: [0.7, 0.9] },
            { id: 'minecraft:diamond_shovel', ratio: 1, enchantChance: 1.0, enchantLevels: [20, 30], damage: [0.7, 0.9] },
        ]
    },
]

const vanilla_abandoned_mineshaft_supply = [

    {
        groupWeight: 40, 
        items: [
            
            { id: 'minecraft:torch', ratio: 10, min: 1, max: 1 },

        ]
    },
]

// =================================================================
// 事件注册区域
// =================================================================
const abandoned_mineshaft_content = [
    [vanilla_abandoned_mineshaft_ore, 2, 4],
    [vanilla_abandoned_mineshaft_equipment, 1, 3],
    [vanilla_abandoned_mineshaft_supply, 5, 6],
    [shipwreck_treasure_pottery_sherd, 1, 1],
    [cobweb, 4, 5],
];

const loot_abandoned_mineshaft = [
    [abandoned_mineshaft_content, 1],
];