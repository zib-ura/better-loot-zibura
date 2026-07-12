const t_and_t_pillager_outpost_equipment = [
    {
        groupName: "Equipment",
        groupWeight: 30,
        min: 1, max: 1,
        items: [
            { id: 'supplementaries:sack', ratio: 10 },       
            { id: 'farmersdelight:wooden_basket', ratio: 10 },       
            { id: 'kaleidoscope_cookery:fruit_basket', ratio: 10 },       
            { id: 'youkaisfeasts:kettle', ratio: 10 },     
            { id: 'kaleidoscope_cookery:stockpot', ratio: 10 }, 
            { id: 'kaleidoscope_cookery:pot', ratio: 10 }, 
            { id: 'kaleidoscope_cookery:stockpot_lid', ratio: 10 }, 
            { id: 'kaleidoscope_cookery:kitchen_shovel', ratio: 10 }, 
            { id: 'kaleidoscope_cookery:teapot', ratio: 10 }, 
            { id: 'farmersdelight:cooking_pot', ratio: 10 }, 
            { id: 'farmersdelight:skillet', ratio: 10 },    
            { id: 'kaleidoscope_chinesefood:pickle_jar', ratio: 10 },
            { id: 'farmersdelight:iron_knife', ratio: 10, max: 1, enchantChance: 0.0, damage: [0.7, 0.8]},

        ]
    }
];

const t_and_t_pillager_outpost_food_supply_content = [
    // 基础物资
    [food_supply, 2, 3],
    [t_and_t_pillager_outpost_equipment, 4, 4],

    // 平原村庄 (Plains)
    [village_plains_dish, 3, 4, { matchBiome: '#minecraft:has_structure/village_plains' } ],
    [village_plains_drinks, 1, 2, { matchBiome: '#minecraft:has_structure/village_plains' } ],

    // 沙漠村庄 (Desert)
    [village_desert_dish, 3, 4, { matchBiome: '#minecraft:has_structure/village_desert' } ],
    [village_desert_drinks, 1, 2, { matchBiome: '#minecraft:has_structure/village_desert' } ],

    // 热带草原村庄 (Savanna)
    [village_savanna_dish, 3, 4, { matchBiome: '#minecraft:has_structure/village_savanna' } ],
    [village_savanna_drinks, 1, 2, { matchBiome: '#minecraft:has_structure/village_savanna' } ],

    // 针叶林村庄 (Taiga)
    [village_taiga_dish, 3, 4, { matchBiome: '#minecraft:has_structure/village_taiga' } ],
    [village_taiga_drinks, 1, 2, { matchBiome: '#minecraft:has_structure/village_taiga' } ],

    // 积雪平原村庄 (Snowy)
    [village_snowy_dish, 3, 4, { matchBiome: '#minecraft:has_structure/village_snowy' } ],
    [village_snowy_drinks, 1, 2, { matchBiome: '#minecraft:has_structure/village_snowy' } ],

    // 坏地 (Badlands)
    [village_badlands_dish, 3, 4, { matchBiome: '#minecraft:is_badlands' } ],
    [village_badlands_drinks, 1, 2, { matchBiome: '#minecraft:is_badlands' } ],

    // 海滩 (Beach)
    [village_beach_dish, 3, 4, { matchBiome: '#minecraft:is_beach' } ],
    [village_beach_drinks, 1, 2, { matchBiome: '#minecraft:is_beach' } ],

    // 森林 (Forest)
    [village_plains_dish, 3, 4, { matchBiome: '#minecraft:is_forest' } ],
    [village_plains_drinks, 1, 2, { matchBiome: '#minecraft:is_forest' } ],

    // 丛林 (Jungle)
    [village_jungle_dish, 3, 4, { matchBiome: '#minecraft:is_jungle' } ],
    [village_jungle_drinks, 1, 2, { matchBiome: '#minecraft:is_jungle' } ],

    // 海洋 (Ocean)
    [village_ocean_dish, 3, 4, { matchBiome: '#minecraft:is_ocean' } ],
    [village_ocean_drinks, 1, 2, { matchBiome: '#minecraft:is_ocean' } ],

];

const loot_t_and_t_pillager_outpost_food_supply = [
    [t_and_t_pillager_outpost_food_supply_content, 1],
];