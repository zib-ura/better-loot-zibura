// =================================================================
// 战利品表定义区域
// =================================================================

const vanilla_village_desert_base_crops = [

    {
        groupName: "base_crops",
        groupWeight: 80, 
        min: 2, max: 3,
        items: [
            { id: 'minecraft:wheat_seeds', ratio: 10 },
            { reference: 'lootjs:wheat_and_wheat_crate', ratio: 40 },

            { reference: 'lootjs:tomato_seeds', ratio: 10 },
            { reference: 'lootjs:tomato_and_tomato_crate', ratio: 40 },

            { reference: 'lootjs:onion_and_onion_crate', ratio: 25 },

            { reference: 'lootjs:pepper_seeds', ratio: 5 },
            { reference: 'lootjs:pepper_and_pepper_crate', ratio: 20 },

        ]
    },
    

]

const vanilla_village_desert_other_crops = [
    {
        groupName: "desert special",
        groupWeight: 80, 
        min: 2, max: 3,
        items: [
            { reference: 'lootjs:black_grape_seeds', ratio: 5 },
            { reference: 'lootjs:black_grape', ratio: 20 },

            { reference: 'lootjs:white_grape_seeds', ratio: 5 },
            { reference: 'lootjs:white_grape', ratio: 20 },

            { reference: 'lootjs:red_grape_seeds', ratio: 5 },
            { reference: 'lootjs:red_grape', ratio: 20 },

            { id: "kaleidoscope_tavern:grape", ratio: 25 },

            { id: "kaleidoscope_tavern:green_grape", ratio: 25 },

            { id: "kaleidoscope_tavern:grapevine", ratio: 25 },

            { id: 'dumplings_delight:eggplant_seeds', ratio: 5 },
            { id: 'dumplings_delight:eggplant', ratio: 20 },

            // { id: 'minecraft:apple', ratio: 25 },
            
            { id: 'minecraft:melon_seeds', ratio: 5 },
            { id: 'minecraft:melon', ratio: 20 },
        ]
    },
    {
        groupName: "other",
        groupWeight: 20, 
        min: 2, max: 4,
        items: [
            { id: 'minecraft:carrot', ratio: 25 },

            { id: 'minecraft:potato', ratio: 25 },

            { id: 'farmersdelight:cabbage_seeds', ratio: 5 },
            { id: 'farmersdelight:cabbage', ratio: 20 },

            { reference: 'lootjs:lettuce_seeds', ratio: 5 },
            { reference: 'lootjs:lettuce', ratio: 20 },

            { reference: 'lootjs:rice', ratio: 15 },
        ]
    },

]
const vanilla_village_desert_essential_wealth_equipment = [

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

const vanilla_village_desert_dish = [
    {
        groupName: "common",
        groupWeight: 70,
        min: 2, max: 3,
        items: [
            { id: 'minecraft:bread', ratio: 10 },
            { id: 'minecraft:cooked_beef', ratio: 10 },       // 牛排
            { id: 'minecraft:cooked_porkchop', ratio: 10 },   // 熟猪排
            { id: 'minecraft:cooked_mutton', ratio: 10 },     // 熟羊肉
            { id: 'minecraft:cooked_chicken', ratio: 10 },    // 熟鸡肉

            { id: 'farmersdelight:tomato_sauce', ratio: 10 },
            { id: 'kaleidoscope_nether:roujiamo', ratio: 10 },
            { id: 'kaleidoscope_cookery:samsa', ratio: 10 },
            { id: 'kaleidoscope_cookery:meat_pie', ratio: 10 },
            { id: 'kaleidoscope_cookery:mantou', ratio: 10 },
            { id: 'farmersdelight:barbecue_stick', ratio: 10 },

            { id: 'barbequesdelight:grilled_beef_skewer', ratio: 10 },
            { id: 'barbequesdelight:grilled_chicken_skewer', ratio: 10 },
            { id: 'barbequesdelight:grilled_lamb_skewer', ratio: 10 },
            { id: 'barbequesdelight:grilled_potato_skewer', ratio: 10 },
            { id: 'barbequesdelight:grilled_vegetable_skewer', ratio: 10 },
            { id: 'barbequesdelight:grilled_rabbit_skewer', ratio: 10 },
            // { id: 'barbequesdelight:grilled_salmon_skewer', ratio: 10 },
            // { id: 'barbequesdelight:grilled_cod_skewer', ratio: 10 },
            { id: 'barbequesdelight:kebab_wrap', ratio: 10 },

        ]
    },

    {
        groupName: "special",
        groupWeight: 30, 
        min: 2, max: 3,
        items: [
            { id: 'minecraft:rabbit_stew', ratio: 10 },

            { id: 'farmersdelight:fish_stew', ratio: 10 },          
            { id: 'farmersdelight:mutton_wrap', ratio: 10 },          
            { id: 'farmersdelight:roasted_mutton_chops', ratio: 10 },
            
            { id: 'kaleidoscope_chinesefood:yangrou_paomo', ratio: 10 },
            { id: 'kaleidoscope_chinesefood:lamb_pilaf', ratio: 10 },

            { id: 'kaleidoscope_cookery:numbing_spicy_chicken', ratio: 10 },
            { id: 'kaleidoscope_chinesefood:dry_pot_chicken', ratio: 10 },
            { id: 'kaleidoscope_chinesefood:dry_pot_potatoes', ratio: 10 },
            { id: 'kaleidoscope_chinesefood:big_plate_chicken_noodles', ratio: 10 },
            
            { id: 'kaleidoscope_cookery:apple_platter', ratio: 10 },
            { id: 'kaleidoscope_cookery:tomato_platter', ratio: 10 },
            { id: 'kaleidoscope_cookery:watermelon_platter', ratio: 10 },

        ]
    },
]

const vanilla_village_desert_drinks = [
    {
        groupName: "drinks",
        groupWeight: 5,
        min: 1, max: 2,
        items: [
            { id: 'minecraft:potion', ratio: 10 , potion: "minecraft:water" }, 
            { id: 'farmersdelight:melon_juice', ratio: 10 },          
            // { id: 'kaleidoscope_tavern:wine', ratio: 10 },          
            // { id: 'youkaisfeasts:red_wine_bottle', ratio: 10 },
            // { id: 'youkaisfeasts:white_wine_bottle', ratio: 10 },
        ]
    },
    {
        groupName: "condiment",
        groupWeight: 5,
        min: 1, max: 2,
        items: [
            { id: 'barbequesdelight:chili_powder', ratio: 10 },
            { id: 'barbequesdelight:cumin_powder', ratio: 10 },
            { id: 'barbequesdelight:pepper_powder', ratio: 10 },
        ]
    },

]


// =================================================================
// 事件注册区域
// =================================================================
const village_desert_house_content = [
    [vanilla_village_desert_base_crops, 1, 1],
    [vanilla_village_desert_other_crops, 1, 1],
    [vanilla_village_desert_dish, 3, 4],
    [vanilla_village_desert_essential_wealth_equipment, 4, 4],
    [vanilla_village_desert_drinks, 1, 2],
];

const loot_village_desert_house = [
    [village_desert_house_content, 1],
];