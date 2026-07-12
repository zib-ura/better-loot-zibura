const village_valuable_block = [
    {
        groupName: "Village Infrastructure & Utilities",
        groupWeight: 30,
        min: 1,
        max: 1,
        items: [
            { id: 'minecraft:enchanting_table', ratio: 10 }, 
            { id: 'minecraft:anvil', ratio: 10 },             
            { id: 'minecraft:brewing_stand', ratio: 10 },    
            { id: 'minecraft:saddle', ratio: 10 },    
                  

        ]
    }
];

const village_master_trades_armor_weapon = [
    {
        groupName: "Master Armorer Products",
        groupWeight: 25,
        min: 1,
        max: 1,        
        enchantChance: 1, 
        enchantLevels: [25, 30], 
        items: [
            { id: 'minecraft:diamond_chestplate', ratio: 10 },
            { id: 'minecraft:diamond_leggings', ratio: 10 },
            { id: 'minecraft:diamond_helmet', ratio: 10 },
            { id: 'minecraft:diamond_boots', ratio: 10 },
        ]
    },
    {
        groupName: "Master Weapon & Tool Smith Products",
        groupWeight: 25,
        min: 1,
        max: 1,        
        enchantChance: 1, 
        enchantLevels: [25, 30], 
        items: [
            { id: 'minecraft:diamond_sword', ratio: 10 },
            { id: 'minecraft:diamond_pickaxe', ratio: 10 },
            { id: 'minecraft:diamond_axe', ratio: 10 },
            { id: 'minecraft:diamond_shovel', ratio: 10 },
            { id: 'minecraft:crossbow', ratio: 10 },       
            { id: 'minecraft:bow', ratio: 10 },
        ]
    }
];

const village_expert_librarian_cleric = [
    {
        groupName: "Librarian & Cleric Master Stock",
        groupWeight: 40,
        min: 1,
        max: 2,
        items: [
            { id: 'minecraft:experience_bottle', ratio: 10 },
            { id: 'minecraft:golden_carrot', ratio: 10 },     
            { id: 'minecraft:glistering_melon_slice', ratio: 10 }, 
            { id: 'minecraft:ender_eye', ratio: 10 },         
        ]
    }
];

const village_treasure_emerald = [
    {
        groupName: "Village Currency",
        groupWeight: 50,  
        min: 2, 
        max: 3,
        items: [
            { id: 'minecraft:emerald_block', ratio: 5 },
            { id: 'minecraft:emerald', ratio: 20 },
        ]
    },

];

const village_treasure_quartz = [
    {
        groupName: "Overworld Quartz Supply",
        groupWeight: 80, 
        min: 4,          
        max: 5,          
        items: [
            { id: 'minecraft:quartz', ratio: 70 },      
            { id: 'minecraft:quartz_block', ratio: 20 }, 
            { id: 'minecraft:smooth_quartz', ratio: 10 },
        ]
    },
    {
        groupName: "empty", 
        groupWeight: 20, // 留有 20% 的空手而归概率，增加随机趣味
        items: [
            { id: "empty" }
        ]
    }
];
// const village_smithing_templates_exclusive = [
//     {
//         groupName: "Village Smithing Templates",
//         groupWeight: 30,  
//         min: 1,
//         max: 1,
//         items: [
//             { id: 'minecraft:sentry_armor_trim_smithing_template', ratio: 10 }, 
//             { id: 'minecraft:shaper_armor_trim_smithing_template', ratio: 10 },
//         ]
//     },
//     {
//         groupName: "empty",
//         groupWeight: 70, // 模板属于极低概率产出
//         items: [
//             { id: "empty" },
//         ]
//     }
// ];

// =================================================================
// 2. 事件注册与抽数控制区域
// =================================================================

const village_treasure_content = [
    [village_treasure_quartz, 2, 3],
    [village_valuable_block, 1, 1],                 
    [village_master_trades_armor_weapon, 1, 1],               
    [village_expert_librarian_cleric, 2, 4],             
    [village_treasure_emerald, 8, 12],           
    // [village_smithing_templates_exclusive, 1, 1],  /
];

const loot_village_treasure = [
    [village_treasure_content, 1], 
]