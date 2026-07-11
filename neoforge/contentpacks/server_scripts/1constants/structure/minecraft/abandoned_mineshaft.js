// =================================================================
// 战利品表定义区域
// =================================================================

const abandoned_mineshaft_ore = [
    {
        groupName: "common_ore",
        groupWeight: 80, 
        min: 2, max: 3,
        items: [
            { reference: 'lootjs:raw_iron_and_raw_iron_block', ratio: 10 },
            { reference: 'lootjs:raw_gold_and_raw_gold_block', ratio: 5 },
            { reference: 'lootjs:redstone_and_redstone_block', ratio: 5 },
            { reference: 'lootjs:lapis_lazuli_and_lapis_lazuli_block', ratio: 5 },
            { reference: 'lootjs:coal_and_coal_block', ratio: 10 },
            { reference: 'lootjs:raw_zinc_and_raw_zinc_block', ratio: 10 },
        ]
    },

    {
        groupName: "rare_ore",
        groupWeight: 20, 
        min: 2, max: 3,
        items: [
            { reference: 'lootjs:diamond', ratio: 10 },
        ]
    },
]

const abandoned_mineshaft_equipment = [
    {
        groupName: "common_equipment",
        groupWeight: 90,
        min: 1, max: 1, enchantChance: 0 , damage: [0.4, 0.6],
        items: [        
            { id: 'minecraft:iron_pickaxe', ratio: 5 },
            { id: 'minecraft:iron_shovel', ratio: 5 },
        ]
    },

    {
        groupName: "rare_equipment",
        groupWeight: 10,
        min: 1, max: 1, enchantChance: 1.0, enchantLevels: [20, 30], damage: [0.7, 0.9],
        items: [
            { id: 'minecraft:diamond_pickaxe', ratio: 1 },
            { id: 'minecraft:diamond_shovel', ratio: 1 },
        ]
    },
]

const abandoned_mineshaft_lighting = [
    {
        groupWeight: 40, 
        min: 4, max: 5,
        items: [
            { id: 'minecraft:lantern', ratio: 10 },
        ]
    },
]

const abandoned_mineshaft_climbing = [
    {
        groupWeight: 40, 
        min: 4, max: 5,
        items: [
            { id: 'minecraft:ladder', ratio: 10 },
            { id: 'farmersdelight:rope', ratio: 10 },
            { id: 'supplementaries:rope', ratio: 10 },
            { id: 'farm_and_charm:sturdy_ladder', ratio: 10 },
            { id: 'minecraft:scaffolding', ratio: 10 },
            { id: 'create:copper_ladder', ratio: 10 },
            { id: 'farmersdelight:safety_net', ratio: 10 },
            { id: 'supplementaries:rope_arrow', ratio: 10 }
        ]
    }
]
// =================================================================
// 事件注册区域
// =================================================================
const abandoned_mineshaft_content = [
    [abandoned_mineshaft_ore, 5, 6],
    [abandoned_mineshaft_equipment, 1, 3],
    [abandoned_mineshaft_lighting, 2, 2],
    [abandoned_mineshaft_climbing, 1, 1],
    [shipwreck_treasure_pottery_sherd, 1, 1],
    // [cobweb, 4, 5],
];

const loot_abandoned_mineshaft = [
    [abandoned_mineshaft_content, 1],
];