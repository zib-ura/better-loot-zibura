// =================================================================
// 战利品表定义区域
// =================================================================

const village_butcher_meat = [
    {
        groupName: "meat",
        groupWeight: 90, 
        min: 3, max: 5,
        items: [
            { id: 'minecraft:beef', ratio: 10 },
            { id: 'minecraft:porkchop', ratio: 10 },
            { id: 'minecraft:chicken', ratio: 10 },
            { id: 'minecraft:mutton', ratio: 10 },
            { id: 'minecraft:bone', ratio: 10 },

            { reference: 'lootjs:bacon', ratio: 10 },
            { reference: 'lootjs:minced_beef', ratio: 10 },
            { reference: 'lootjs:chicken_cuts', ratio: 10 },         
            { reference: 'lootjs:ham', ratio: 5 },

            { id: 'kaleidoscope_cookery:oil', ratio: 10 },

        ]
    },
]


const village_butcher_equipment = [
    {
        groupName: "Equipment",
        groupWeight: 20,
        min: 1, max: 1, enchantChance: 0.0, damage: [0.2, 0.8],
        items: [
            { id: 'farmersdelight:iron_knife', ratio: 10 },
            { id: 'farmersdelight:cutting_board', ratio: 10 },

            { id: 'kaleidoscope_cookery:iron_kitchen_knife', ratio: 10 },
            { id: 'kaleidoscope_cookery:chopping_board', ratio: 10 },

            // { id: 'minecraft:lantern', ratio: 5 },
        ]
    },
]

const desert_village_butcher_meat = [
    {
        groupName: "desert_meat",
        groupWeight: 90, 
        min: 3, max: 5,
        items: [
            { id: 'minecraft:bone', ratio: 10 },
            { id: 'kaleidoscope_cookery:oil', ratio: 10 },

            { id: 'minecraft:beef', ratio: 10 },
            { id: 'minecraft:mutton', ratio: 10 },
            { id: 'minecraft:chicken', ratio: 10 },
            { id: 'minecraft:rabbit', ratio: 10 }, 

            { reference: 'lootjs:minced_beef', ratio: 10 },
            { reference: 'lootjs:chicken_cuts', ratio: 10 },        
            { reference: 'lootjs:raw_lamb_chops', ratio: 20 },
            { id: 'kaleidoscope_cookery:raw_cut_small_meats', ratio: 10 }
        ]
    },
]

const village_butcher_products = [
    {
        groupName: "Products",
        groupWeight: 5,
        min: 1, max: 1,
        items: [
            { id: 'rationcraft:corned_beef', ratio: 10 },
            { id: 'rationcraft:salted_pork', ratio: 10 },
        ]
    },
]

const desert_village_butcher_products = [
    {
        groupName: "Products",
        groupWeight: 5,
        min: 1, max: 1,
        items: [
            { id: 'rationcraft:corned_beef', ratio: 10 },
            { id: 'rationcraft:salted_pork', ratio: 10 },
        ]
    },
]
// =================================================================
// 事件注册区域
// =================================================================
const village_butcher_content = [
    [village_butcher_meat, 4, 6, { matchBiome: '#kubejs:not_desert' }],
    [village_butcher_products, 0, 1, { matchBiome: '#kubejs:not_desert' }],
    [desert_village_butcher_meat, 4, 6, { matchBiome: 'minecraft:desert' }],
    [desert_village_butcher_products, 0, 1, { matchBiome: 'minecraft:desert' }],
    [village_butcher_equipment, 2, 3],
];

const loot_village_butcher = [
    [village_butcher_content, 1],
];