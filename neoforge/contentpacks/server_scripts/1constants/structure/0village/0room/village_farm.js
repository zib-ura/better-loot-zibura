const village_farm_equipment = [
    {   
        groupName: "common_equipment",
        groupWeight: 95, 
        min: 1, max: 1, enchantChance: 0.0, damage: [0.5, 0.9],
        items: [
            { id: 'minecraft:iron_hoe', ratio: 10 },
            { id: 'kaleidoscope_cookery:sickle', ratio: 10 },
            { id: 'farmersdelight:iron_knife', ratio: 10 },
            { id: 'minecraft:water_bucket', ratio: 10 },
        ]
    },
    {   
        groupName: "rare_equipment",
        groupWeight: 5, 
        min: 1, max: 1, enchantChance: 0.0, damage: [0.7, 0.8],
        items: [
            { id: 'minecraft:diamond_hoe', ratio: 10 },
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
            { reference: 'lootjs:wheat_seeds_and_wheat_seeds_block', ratio: 5 },
            { reference: 'lootjs:beetroot_seeds_and_beetroot_seeds_block', ratio: 5 },
            { reference: 'lootjs:pumpkin_seeds_and_pumpkin_seeds_block', ratio: 5 },
            { reference: 'lootjs:tomato_seeds', ratio: 5 },
            { reference: 'lootjs:cabbage_seeds', ratio: 5 },
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
            { reference: 'lootjs:strawberry_seeds', ratio: 5 },
            { reference: 'lootjs:barley_seeds', ratio: 5 },
            { reference: 'lootjs:cucumber_seeds', ratio: 5 },
            { reference: 'lootjs:oat_seeds', ratio: 5 },
            { reference: 'lootjs:chinese_cabbage_seeds', ratio: 5 },
            { reference: 'lootjs:fennel_seeds', ratio: 5 },
            { reference: 'lootjs:garlic_clove', ratio: 5 },
            { reference: 'lootjs:garlic_chive_seeds', ratio: 5 },
            { reference: 'lootjs:papaya_seeds', ratio: 5 }, 
            { reference: 'lootjs:lime_sapling', ratio: 5 },
        ]
    },
]

const village_savanna_farm_seed = [
    {   
        groupName: "common",
        groupWeight: 80, 
        min: 2, max: 3,
        items: [
            { reference: 'lootjs:wheat_seeds_and_wheat_seeds_block', ratio: 20 },
            { reference: 'lootjs:corn_seeds_and_corn_seeds_block', ratio: 20 },
            { reference: 'lootjs:melon_seeds_and_melon_seeds_block', ratio: 20 },
        ]
    },
    {   
        groupName: "special",
        groupWeight: 20, 
        min: 1, max: 2,
        items: [
            { reference: 'lootjs:carrot', ratio: 5 },
            { reference: 'lootjs:potato', ratio: 5 },
            { reference: 'lootjs:tomato_seeds', ratio: 5 },
            { reference: 'lootjs:cabbage_seeds', ratio: 5 },
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
            { reference: 'lootjs:wheat_seeds_and_wheat_seeds_block', ratio: 20 },
            { reference: 'lootjs:beetroot_seeds_and_beetroot_seeds_block', ratio: 20 },
            { reference: 'lootjs:potato_and_potato_block', ratio: 20 },
        ]
    },
    {   
        groupName: "special",
        groupWeight: 20, 
        min: 1, max: 2,
        items: [
            { reference: 'lootjs:cabbage_seeds', ratio: 10 },
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
            { reference: 'lootjs:wheat_seeds_and_wheat_seeds_block', ratio: 10 },
            { reference: 'lootjs:pumpkin_seeds_and_pumpkin_seeds_block', ratio: 10 },
            { reference: 'lootjs:beetroot_seeds_and_beetroot_seeds_block', ratio: 10 },
            { reference: 'lootjs:barley_seeds', ratio: 10 },
            { reference: 'lootjs:sweet_berry_pips', ratio: 15 },
        ]
    },
    {   
        groupName: "special",
        groupWeight: 20, 
        min: 1, max: 2,
        items: [
            { reference: 'lootjs:tomato_seeds', ratio: 5 },
            { reference: 'lootjs:cabbage_seeds', ratio: 5 },
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
            { reference: 'lootjs:wheat_seeds_and_wheat_seeds_block', ratio: 10 },            
            { reference: 'lootjs:tomato_seeds', ratio: 10 },
            { reference: 'lootjs:pepper_seeds', ratio: 10 },
            { reference: 'lootjs:melon_seeds_and_melon_seeds_block', ratio: 10 },
            { reference: 'lootjs:black_grape_seeds', ratio: 8 },
            { reference: 'lootjs:white_grape_seeds', ratio: 8 },
            { reference: 'lootjs:red_grape_seeds', ratio: 8 },
            { reference: 'lootjs:eggplant_seeds', ratio: 10 },
        ]
    },
    {   
        groupName: "special",
        groupWeight: 20, 
        min: 1, max: 2,
        items: [
            { reference: 'lootjs:cabbage_seeds', ratio: 5 },
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
            { reference: 'lootjs:wheat_seeds_and_wheat_seeds_block', ratio: 10 },            
        ]
    },
    {   
        groupName: "special",
        groupWeight: 20, 
        min: 1, max: 2,
        items: [
            { reference: 'lootjs:tomato_seeds', ratio: 5 },
            { reference: 'lootjs:cabbage_seeds', ratio: 5 },
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
            { reference: 'lootjs:wheat_seeds_and_wheat_seeds_block', ratio: 10 },            
            { reference: 'lootjs:cabbage_seeds', ratio: 10 },
            { reference: 'lootjs:beetroot_seeds_and_beetroot_seeds_block', ratio: 10 },
            { reference: 'lootjs:pineapple_sapling', ratio: 10 }, 
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
            { reference: 'lootjs:wheat_seeds_and_wheat_seeds_block', ratio: 5 },
            { reference: 'lootjs:beetroot_seeds_and_beetroot_seeds_block', ratio: 5 },
            { reference: 'lootjs:pumpkin_seeds_and_pumpkin_seeds_block', ratio: 5 },
            { reference: 'lootjs:tomato_seeds', ratio: 5 },
            { reference: 'lootjs:cabbage_seeds', ratio: 5 },
            { reference: 'lootjs:lettuce_seeds', ratio: 5 },
            { reference: 'lootjs:pepper_seeds', ratio: 5 },
            { reference: 'lootjs:redbean', ratio: 5 },
            { reference: 'lootjs:soybean', ratio: 5 },
            { reference: 'lootjs:tea_seeds', ratio: 5 },
            

        ]
    },
    // {   
    //     groupName: "special",
    //     groupWeight: 20, 
    //     min: 1, max: 2,
    //     items: [
    //         { reference: 'lootjs:tomato_seeds', ratio: 5 },
    //         { reference: 'lootjs:cabbage_seeds', ratio: 5 },
    //     ]
    // },
]

const village_dark_forest_farm_seed = [
    {   
        groupName: "common",
        groupWeight: 80, 
        min: 2, max: 3,
        items: [
            { reference: 'lootjs:wheat_seeds_and_wheat_seeds_block', ratio: 5 },
            { reference: 'lootjs:potato_and_potato_block', ratio: 5 },
            { reference: 'lootjs:apple', ratio: 5 },         
            // { reference: 'lootjs:red_mushroom', ratio: 10 },  
            // { reference: 'lootjs:brown_mushroom', ratio: 10 },
        ]
    },
    {   
        groupName: "special",
        groupWeight: 20, 
        min: 1, max: 2,
        items: [
            { reference: 'lootjs:carrot', ratio: 5 },
            { reference: 'lootjs:tomato_seeds', ratio: 5 },
            { reference: 'lootjs:onion', ratio: 5 }, 
            { reference: 'lootjs:cabbage_seeds', ratio: 5 },
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
            { reference: 'lootjs:wheat_seeds_and_wheat_seeds_block', ratio: 10 },
            { reference: 'lootjs:melon_seeds_and_melon_seeds_block', ratio: 10 },
            { reference: 'lootjs:pumpkin_seeds_and_pumpkin_seeds_block', ratio: 10 },
            { reference: 'lootjs:cocoa_beans_and_cocoa_beans_block', ratio: 10 },
        ]
    },
    {   
        groupName: "special",
        groupWeight: 20, 
        min: 1, max: 2,
        items: [
            { reference: 'lootjs:rice', ratio: 5 }, 
            { reference: 'lootjs:tomato_seeds', ratio: 5 },
            { reference: 'lootjs:potato', ratio: 5 },
            { reference: 'lootjs:onion', ratio: 5 },
            { reference: 'lootjs:cabbage_seeds', ratio: 5 },
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
            { reference: 'lootjs:wheat_seeds_and_wheat_seeds_block', ratio: 10 },
            { reference: 'lootjs:potato_and_potato_block', ratio: 10 },
            // { reference: 'lootjs:red_mushroom', ratio: 15 },  
            // { reference: 'lootjs:brown_mushroom', ratio: 15 },
        ]
    },
    {   
        groupName: "special",
        groupWeight: 20, 
        min: 1, max: 2,
        items: [
            { reference: 'lootjs:carrot', ratio: 5 },
            { reference: 'lootjs:tomato_seeds', ratio: 5 },
            { reference: 'lootjs:onion', ratio: 5 },
            { reference: 'lootjs:cabbage_seeds', ratio: 5 },
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
            { reference: 'lootjs:wheat_seeds_and_wheat_seeds_block', ratio: 20 },
            { reference: 'lootjs:kelp', ratio: 20 }, 
        ]
    },
    {   
        groupName: "special",
        groupWeight: 20, 
        min: 1, max: 2,
        items: [
            { reference: 'lootjs:rice', ratio: 5 },
            { reference: 'lootjs:potato', ratio: 5 },
            { reference: 'lootjs:onion', ratio: 5 },
            { reference: 'lootjs:cabbage_seeds', ratio: 5 },
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
            { reference: 'lootjs:wheat_seeds_and_wheat_seeds_block', ratio: 1 },
            { reference: 'lootjs:rice_and_rice_block', ratio: 30 },
        ]
    },
    {   
        groupName: "special",
        groupWeight: 20, 
        min: 1, max: 2,
        items: [
            { reference: 'lootjs:tomato_seeds', ratio: 5 },
            { reference: 'lootjs:potato', ratio: 5 },
            { reference: 'lootjs:onion', ratio: 5 },
            { reference: 'lootjs:cabbage_seeds', ratio: 5 },
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
            { reference: 'lootjs:wheat_seeds_and_wheat_seeds_block', ratio: 15 },
            { reference: 'lootjs:potato_and_potato_block', ratio: 5 },
            { reference: 'lootjs:carrot_and_carrot_block', ratio: 5 },

        ]
    },
]
// =================================================================
// 事件注册区域
// =================================================================
const village_farm_content = [
    [village_farm_equipment,        2, 3],

    [village_plains_farm_seed,      2, 3, { matchBiome: '#minecraft:has_structure/village_plains' }],
    [village_savanna_farm_seed,     2, 3, { matchBiome: '#minecraft:has_structure/village_savanna' }],
    [village_snowy_farm_seed,       2, 3, { matchBiome: '#minecraft:has_structure/village_snowy' }],
    [village_taiga_farm_seed,       2, 3, { matchBiome: '#minecraft:is_taiga' }],
    [village_desert_farm_seed,      2, 3, { matchBiome: '#minecraft:has_structure/village_desert' }],

    [village_badland_farm_seed,     2, 3, { matchBiome: '#minecraft:is_badlands' }],          
    [village_beach_farm_seed,       2, 3, { matchBiome: '#minecraft:is_beach' }],                 
    [village_cherry_farm_seed,      2, 3, { matchBiome: 'minecraft:cherry_grove' }],           
    [village_dark_forest_farm_seed, 2, 3, { matchBiome: 'minecraft:dark_forest' }],      
    [village_jungle_farm_seed,      2, 3, { matchBiome: '#minecraft:is_jungle' }],              
    [village_mushroom_farm_seed,    2, 3, { matchBiome: 'minecraft:mushroom_fields' }],   
    [village_ocean_farm_seed,       2, 3, { matchBiome: '#minecraft:is_ocean' }],                
    [village_swamp_farm_seed,       2, 3, { matchBiome: 'minecraft:swamp' }],

    
    [village_other_farm_seed,       2, 3, /* { matchBiome: '' } */],

];

const loot_village_farm = [
    [village_farm_content, 1],
];