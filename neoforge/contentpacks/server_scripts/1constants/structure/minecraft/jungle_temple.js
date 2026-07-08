const vanilla_jungle_temple_treasure_equipment = [
    // =================================================================
    // GROUP: 稀有宝藏 & 附魔书
    // =================================================================
    {
        groupName: "Treasure",
        groupWeight: 20,
        min: 1, max: 2,
        items: [
            { id: 'minecraft:diamond', ratio: 10 },
            { id: 'minecraft:ender_pearl', ratio: 10 },
            { id: 'minecraft:emerald', ratio: 15 },
            { id: 'minecraft:golden_apple', ratio: 20 },
            { id: 'minecraft:enchanted_golden_apple', ratio: 5 },
            { id: 'minecraft:totem_of_undying', ratio: 5 },

        ]
    },

];

const jungle_temple_smithing_templates = [
    {
        groupName: "Smithing Templates",
        groupWeight: 25,  
        min: 1,
        max: 1,
        items: [
            { id: 'minecraft:wild_armor_trim_smithing_template', ratio: 10 },
        ]
    },

    {
        groupName: "empty",
        groupWeight: 50,
        items: [
            { "id": "empty" },
        ]
    },
];

const jungle_temple_gold = [
    {
        groupName: "gold",
        groupWeight: 50,
        min: 2, max: 3,
        items: [
            { id: 'minecraft:gold_ingot', ratio: 20 },
        ]
    },
];

// =================================================================
// 事件注册区域
// =================================================================
const jungle_temple_content = [
    [vanilla_jungle_temple_treasure_equipment, 3, 6],
    [jungle_temple_smithing_templates, 2, 2],
    [Vine, 5, 6],
    [jungle_temple_gold, 2, 3],
];

const loot_jungle_temple = [
    [jungle_temple_content, 1],
];