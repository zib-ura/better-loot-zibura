// =================================================================
// 战利品表定义区域
// =================================================================

const vanilla_village_beach_base_crops = [

    {
        groupName: "base_crops",
        groupWeight: 80, 
        min: 2, max: 3,
        items: [
            { id: 'minecraft:wheat_seeds', ratio: 5 },
            { reference: 'lootjs:wheat_and_wheat_crate', ratio: 20 },
            { id: 'farmersdelight:cabbage_seeds', ratio: 5 },
            { reference: 'farmersdelight:cabbage', ratio: 20 },
            { id: 'minecraft:beetroot_seeds', ratio: 5 },
            { reference: 'lootjs:beetroot_and_beetroot_crate', ratio: 20 },
            { id: 'pineapple_delight:pineapple_crop', ratio: 5 },
            { reference: 'lootjs:pineapple_and_pineapple_crate', ratio: 20 },
        ]
    },
    

]

const vanilla_village_beach_essential_wealth_equipment = [

    // {
    //     groupName: "Wood",
    //     groupWeight: 0, 
    //     min: 3, max: 8,
    //     items: [

    //     ]
    // },


    {
        groupName: "Villager Daily Essentials",
        groupWeight: 30, 
        min: 3, max: 4,
        items: [
            // --- Household Utilities ---
            { id: 'minecraft:glass_bottle', ratio: 10 },
            { id: 'minecraft:bowl', ratio: 10 },
            { id: 'farmersdelight:iron_knife', ratio: 10, max: 1, enchantChance: 0.0, damage: [0.7, 0.8]},
        ]
    },

    {
        groupName: "Village Wealth",
        groupWeight: 15,
        min: 3, max: 4,
        items: [
            { id: 'minecraft:emerald', ratio: 20 },
        ]
    },


    {
        groupName: "Equipment",
        groupWeight: 30,
        min: 1, max: 1,
        items: [

            { id: 'minecraft:bundle', ratio: 10 },       
            // { id: 'farmersdelight:wooden_basket', ratio: 10 },       
            // { id: 'kaleidoscope_cookery:fruit_basket', ratio: 10 },       

            { id: 'kaleidoscope_cookery:stockpot', ratio: 10 }, 
            { id: 'kaleidoscope_cookery:pot', ratio: 10 }, 
            { id: 'kaleidoscope_cookery:stockpot_lid', ratio: 10 }, 
            { id: 'kaleidoscope_cookery:kitchen_shovel', ratio: 10 }, 

            { id: 'farmersdelight:cooking_pot', ratio: 10 }, 
            { id: 'farmersdelight:skillet', ratio: 10 },    
        ]
    },
]

const vanilla_village_beach_dish = [
    {
        groupName: "common",
        groupWeight: 70,
        min: 2, max: 3,
        items: [
            { id: 'minecraft:bread', ratio: 10 },
            { id: 'farmersdelight:cabbage_rolls', ratio: 10 },
            { id: 'pineapple_delight:pineapple_pie', ratio: 10 },
            { id: 'pineapple_delight:pineapple_cake', ratio: 10 },
            { id: 'pineapple_delight:pineapple_fried_rice', ratio: 10 },

        ]
    },

]

const vanilla_village_beach_drinks = [
    {
        groupName: "drinks",
        groupWeight: 5,
        min: 1, max: 2,
        items: [
            { id: 'minecraft:potion', ratio: 10 , potion: "minecraft:water" }, 
            { id: 'pineapple_delight:pineapple_juice', ratio: 10 },
            { id: 'pineapple_delight:pineapple_milk_shake', ratio: 10 },
        ]
    },


]


// =================================================================
// 事件注册区域
// =================================================================
const village_beach_house_content = [
    [vanilla_village_beach_base_crops, 1, 1],
    [vanilla_village_beach_dish, 3, 4],
    [vanilla_village_beach_essential_wealth_equipment, 4, 4],
    [vanilla_village_beach_drinks, 1, 2],

];

const loot_village_beach_house = [
    [village_beach_house_content, 1],
];