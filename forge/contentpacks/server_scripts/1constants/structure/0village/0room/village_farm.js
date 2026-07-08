const village_farm_equipment = [
    {   
        groupName: "common_equipment",
        groupWeight: 95, 
        min: 1, max: 2,
        items: [
            { id: 'minecraft:iron_hoe', ratio: 10 , enchantChance: 0.0,  damage: [0.7, 0.8] },
            { id: 'minecraft:stone_hoe', ratio: 20 , enchantChance: 0.0,  damage: [0.7, 0.8] },
            { id: 'kaleidoscope_cookery:sickle', ratio: 10 , enchantChance: 0.0 , damage: [0.7, 0.8] },
            { id: 'farmersdelight:flint_knife', ratio: 10 , enchantChance: 0.0 , damage: [0.7, 0.8] },
            { id: 'farmersdelight:iron_knife', ratio: 10 , enchantChance: 0.0 , damage: [0.7, 0.8] },
            { id: 'moredelight:stone_knife', ratio: 10 , enchantChance: 0.0 , damage: [0.7, 0.8] },
            { id: 'minecraft:water_bucket', ratio: 10 },
        ]
    },
    {   
        groupName: "rare_equipment",
        groupWeight: 5, 
        min: 1, max: 1,
        items: [
            { id: 'minecraft:diamond_hoe', ratio: 10 , enchantChance: 0.0,  damage: [0.7, 0.8] },
        ]
    },
]

// =================================================================
// 农田种子定义区域（统一按 common:special = 80:20 分割）
// =================================================================

const village_plains_farm_seed = [
    {   
        groupName: "common",
        groupWeight: 80, 
        min: 2, max: 3,
        items: [
            { id: 'minecraft:wheat_seeds', ratio: 5 },
            { id: 'minecraft:beetroot_seeds', ratio: 5 },
            { id: 'minecraft:pumpkin_seeds', ratio: 5 },
            { reference: 'lootjs:tomato_seeds', ratio: 5 },
            { id: 'farmersdelight:cabbage_seeds', ratio: 5 },
            { reference: 'lootjs:lettuce_seeds', ratio: 5 },
            { reference: 'lootjs:pepper_seeds', ratio: 5 },
            { id: 'youkaisfeasts:tea_seeds', ratio: 5 },
        ]
    },
    {   
        groupName: "special",
        groupWeight: 20, 
        min: 1, max: 2,
        items: [
            { reference: 'lootjs:black_grape_seeds', ratio: 5 },
            { reference: 'lootjs:white_grape_seeds', ratio: 5 },
            { reference: 'lootjs:red_grape_seeds', ratio: 5 },
            { id: 'farm_and_charm:strawberry_seeds', ratio: 5 },
            { id: 'farm_and_charm:barley_seeds', ratio: 5 },
            { id: 'lootjs:cucumber_seeds', ratio: 5 },
            { reference: 'lootjs:oat_seeds', ratio: 5 },
            { id: 'dumplings_delight:chinese_cabbage_seeds', ratio: 5 },
            { id: 'dumplings_delight:fennel_seeds', ratio: 5 },
            { id: 'dumplings_delight:garlic_clove', ratio: 5 },
            { id: 'dumplings_delight:garlic_chive_seeds', ratio: 5 },
            { reference: 'lootjs:papaya_seeds', ratio: 5 }, 
            { id: 'muffins_thaidelight:lime_sapling', ratio: 5 },
        ]
    },
]

const village_savanna_farm_seed = [
    {   
        groupName: "common",
        groupWeight: 80, 
        min: 2, max: 3,
        items: [
            { id: 'minecraft:wheat_seeds', ratio: 20 },
            { reference: 'lootjs:corn_seeds', ratio: 20 },
            { id: 'minecraft:melon_seeds', ratio: 20 },
        ]
    },
    {   
        groupName: "special",
        groupWeight: 20, 
        min: 1, max: 2,
        items: [
            { id: 'minecraft:carrot', ratio: 5 },
            { id: 'minecraft:potato', ratio: 5 },
            { reference: 'lootjs:tomato_seeds', ratio: 5 },
            { id: 'farmersdelight:cabbage_seeds', ratio: 5 },
            { reference: 'lootjs:lettuce_seeds', ratio: 5 },
        ]
    },
]

const village_snowy_farm_seed = [
    {   
        groupName: "common",
        groupWeight: 80, 
        min: 2, max: 3,
        items: [
            { id: 'minecraft:wheat_seeds', ratio: 20 },
            { id: 'minecraft:beetroot_seeds', ratio: 20 },
            { id: 'minecraft:potato', ratio: 20 }, 
        ]
    },
    {   
        groupName: "special",
        groupWeight: 20, 
        min: 1, max: 2,
        items: [
            { id: 'farmersdelight:cabbage_seeds', ratio: 10 },
            { reference: 'lootjs:lettuce_seeds', ratio: 10 },
        ]
    },
]

const village_taiga_farm_seed = [
    {   
        groupName: "common",
        groupWeight: 80, 
        min: 2, max: 3,
        items: [
            { id: 'minecraft:wheat_seeds', ratio: 10 },
            { id: 'minecraft:pumpkin_seeds', ratio: 10 },
            { id: 'minecraft:beetroot_seeds', ratio: 10 },
            { id: 'farm_and_charm:barley_seeds', ratio: 10 },
            { id: 'berry_good:sweet_berry_pips', ratio: 15 },
        ]
    },
    {   
        groupName: "special",
        groupWeight: 20, 
        min: 1, max: 2,
        items: [
            { reference: 'lootjs:tomato_seeds', ratio: 5 },
            { id: 'farmersdelight:cabbage_seeds', ratio: 5 },
            { reference: 'lootjs:lettuce_seeds', ratio: 5 },
        ]
    },
]

const village_desert_farm_seed = [
    {   
        groupName: "common",
        groupWeight: 80, 
        min: 2, max: 3,
        items: [
            { id: 'minecraft:wheat_seeds', ratio: 10 },
            { reference: 'lootjs:tomato_seeds', ratio: 10 },
            { reference: 'lootjs:pepper_seeds', ratio: 10 },
            { id: 'minecraft:melon_seeds', ratio: 10 },
            { reference: 'lootjs:black_grape_seeds', ratio: 8 },
            { reference: 'lootjs:white_grape_seeds', ratio: 8 },
            { reference: 'lootjs:red_grape_seeds', ratio: 8 },
            { id: 'dumplings_delight:eggplant_seeds', ratio: 10 },
        ]
    },
    {   
        groupName: "special",
        groupWeight: 20, 
        min: 1, max: 2,
        items: [
            { id: 'farmersdelight:cabbage_seeds', ratio: 5 },
            { reference: 'lootjs:lettuce_seeds', ratio: 5 },
        ]
    },
]



// =================================================================
// 新增：群系扩展农田种子定义区域（统一按 common:special = 80:20 分割）
// =================================================================

const village_badland_farm_seed = [
    {   
        groupName: "common",
        groupWeight: 80, 
        min: 2, max: 3,
        items: [
            { id: 'minecraft:wheat_seeds', ratio: 20 },
        ]
    },
    {   
        groupName: "special",
        groupWeight: 20, 
        min: 1, max: 2,
        items: [
            { reference: 'lootjs:tomato_seeds', ratio: 5 },
            { id: 'farmersdelight:cabbage_seeds', ratio: 5 },
            { reference: 'lootjs:lettuce_seeds', ratio: 5 },
        ]
    },
]

const village_beach_farm_seed = [
    {   
        groupName: "common",
        groupWeight: 80, 
        min: 2, max: 3,
        items: [
            { id: 'minecraft:wheat_seeds', ratio: 10 },
            { id: 'farmersdelight:cabbage_seeds', ratio: 10 },
            { id: 'minecraft:beetroot_seeds', ratio: 10 },
            { id: 'pineapple_delight:pineapple_crop', ratio: 10 }, // 菠萝通常直接种植自身
        ]
    },
    {   
        groupName: "special",
        groupWeight: 20, 
        min: 1, max: 2,
        items: [
            { reference: 'lootjs:tomato_seeds', ratio: 5 },
            { reference: 'lootjs:lettuce_seeds', ratio: 5 },
        ]
    },
]

const village_cherry_farm_seed = [
    {   
        groupName: "common",
        groupWeight: 80, 
        min: 2, max: 3,
        items: [
            { id: 'minecraft:wheat_seeds', ratio: 5 },
            { id: 'minecraft:beetroot_seeds', ratio: 5 },
            { id: 'minecraft:pumpkin_seeds', ratio: 5 },
            { reference: 'lootjs:tomato_seeds', ratio: 5 },
            { id: 'farmersdelight:cabbage_seeds', ratio: 5 },
            { reference: 'lootjs:lettuce_seeds', ratio: 5 },
            { reference: 'lootjs:pepper_seeds', ratio: 5 },
            { id: 'youkaisfeasts:redbean', ratio: 5 },
            { id: 'youkaisfeasts:soybean', ratio: 5 },
            { id: 'youkaisfeasts:tea_seeds', ratio: 5 },
            

        ]
    },
    // {   
    //     groupName: "special",
    //     groupWeight: 20, 
    //     min: 1, max: 2,
    //     items: [
    //         { reference: 'lootjs:tomato_seeds', ratio: 5 },
    //         { id: 'farmersdelight:cabbage_seeds', ratio: 5 },
    //     ]
    // },
]

const village_dark_forest_farm_seed = [
    {   
        groupName: "common",
        groupWeight: 80, 
        min: 2, max: 3,
        items: [
            { id: 'minecraft:wheat_seeds', ratio: 10 },
            { id: 'minecraft:potato', ratio: 15 },       // 土豆可直接作种子
            { id: 'minecraft:apple', ratio: 5 },          // 对应原 apple 系列
            // { id: 'minecraft:red_mushroom', ratio: 10 },  
            // { id: 'minecraft:brown_mushroom', ratio: 10 },
        ]
    },
    {   
        groupName: "special",
        groupWeight: 20, 
        min: 1, max: 2,
        items: [
            { id: 'minecraft:carrot', ratio: 5 },
            { reference: 'lootjs:tomato_seeds', ratio: 5 },
            { reference: 'lootjs:onion', ratio: 5 }, // 洋葱通常直接种植
            { id: 'farmersdelight:cabbage_seeds', ratio: 5 },
            { reference: 'lootjs:lettuce_seeds', ratio: 5 },
        ]
    },
]

const village_jungle_farm_seed = [
    {   
        groupName: "common",
        groupWeight: 80, 
        min: 2, max: 3,
        items: [
            { id: 'minecraft:wheat_seeds', ratio: 10 },
            { id: 'minecraft:melon_seeds', ratio: 15 },
            { id: 'minecraft:pumpkin_seeds', ratio: 10 },
            { id: 'minecraft:cocoa_beans', ratio: 15 }, // 可种植的可可豆
        ]
    },
    {   
        groupName: "special",
        groupWeight: 20, 
        min: 1, max: 2,
        items: [
            { reference: 'lootjs:rice_bag', ratio: 5 }, // 对应水稻
            { reference: 'lootjs:tomato_seeds', ratio: 5 },
            { id: 'minecraft:potato', ratio: 5 },
            { reference: 'lootjs:onion', ratio: 5 },
            { id: 'farmersdelight:cabbage_seeds', ratio: 5 },
            { reference: 'lootjs:lettuce_seeds', ratio: 5 },
        ]
    },
]

const village_mushroom_farm_seed = [
    {   
        groupName: "common",
        groupWeight: 80, 
        min: 2, max: 3,
        items: [
            { id: 'minecraft:wheat_seeds', ratio: 10 },
            { id: 'minecraft:potato', ratio: 15 },
            // { id: 'minecraft:red_mushroom', ratio: 15 },  
            // { id: 'minecraft:brown_mushroom', ratio: 15 },
        ]
    },
    {   
        groupName: "special",
        groupWeight: 20, 
        min: 1, max: 2,
        items: [
            { id: 'minecraft:carrot', ratio: 5 },
            { reference: 'lootjs:tomato_seeds', ratio: 5 },
            { reference: 'lootjs:onion', ratio: 5 },
            { id: 'farmersdelight:cabbage_seeds', ratio: 5 },
            { reference: 'lootjs:lettuce_seeds', ratio: 5 },
        ]
    },
]

const village_ocean_farm_seed = [
    {   
        groupName: "common",
        groupWeight: 80, 
        min: 2, max: 3,
        items: [
            { id: 'minecraft:wheat_seeds', ratio: 20 },
            { id: 'minecraft:kelp', ratio: 20 }, // 对应海带
        ]
    },
    {   
        groupName: "special",
        groupWeight: 20, 
        min: 1, max: 2,
        items: [
            { reference: 'lootjs:rice_bag', ratio: 5 },
            { id: 'minecraft:potato', ratio: 5 },
            { reference: 'lootjs:onion', ratio: 5 },
            { id: 'farmersdelight:cabbage_seeds', ratio: 5 },
            { reference: 'lootjs:lettuce_seeds', ratio: 5 },
        ]
    },
]

const village_swamp_farm_seed = [
    {   
        groupName: "common",
        groupWeight: 80, 
        min: 2, max: 3,
        items: [
            { id: 'minecraft:wheat_seeds', ratio: 10 },
            { reference: 'lootjs:rice', ratio: 30 }, // 沼泽高度契合水稻，提高权重
        ]
    },
    {   
        groupName: "special",
        groupWeight: 20, 
        min: 1, max: 2,
        items: [
            { reference: 'lootjs:tomato_seeds', ratio: 5 },
            { id: 'minecraft:potato', ratio: 5 },
            { reference: 'lootjs:onion', ratio: 5 },
            { id: 'farmersdelight:cabbage_seeds', ratio: 5 },
            { reference: 'lootjs:lettuce_seeds', ratio: 5 },
        ]
    },
]

const village_other_farm_seed = [
    {   
        groupName: "common",
        groupWeight: 80, 
        min: 2, max: 3,
        items: [
            { id: 'minecraft:wheat_seeds', ratio: 15 },
            { id: 'minecraft:potato', ratio: 5 },
            { id: 'minecraft:carrot', ratio: 5 },

        ]
    },
]
// =================================================================
// 事件注册区域
// =================================================================
const village_farm_content = [
    [village_farm_equipment,        2, 3],

    [village_plains_farm_seed,      4, 6, { matchBiome: '#minecraft:has_structure/village_plains' }],
    [village_savanna_farm_seed,     4, 6, { matchBiome: '#minecraft:has_structure/village_savanna' }],
    [village_snowy_farm_seed,       4, 6, { matchBiome: '#minecraft:has_structure/village_snowy' }],
    [village_taiga_farm_seed,       4, 6, { matchBiome: '#minecraft:is_taiga' }],
    [village_desert_farm_seed,      4, 6, { matchBiome: '#minecraft:has_structure/village_desert' }],

    [village_badland_farm_seed,     4, 6, { matchBiome: '#minecraft:is_badlands' }],          
    [village_beach_farm_seed,       4, 6, { matchBiome: '#minecraft:is_beach' }],                 
    [village_cherry_farm_seed,      4, 6, { matchBiome: 'minecraft:cherry_grove' }],           
    [village_dark_forest_farm_seed, 4, 6, { matchBiome: 'minecraft:dark_forest' }],      
    [village_jungle_farm_seed,      4, 6, { matchBiome: '#minecraft:is_jungle' }],              
    [village_mushroom_farm_seed,    4, 6, { matchBiome: 'minecraft:mushroom_fields' }],   
    [village_ocean_farm_seed,       4, 6, { matchBiome: '#minecraft:is_ocean' }],                
    [village_swamp_farm_seed,       4, 6, { matchBiome: 'minecraft:swamp' }],

    
    [village_other_farm_seed,       2, 3, /* { matchBiome: '' } */],

];

const loot_village_farm = [
    [village_farm_content, 1],
];