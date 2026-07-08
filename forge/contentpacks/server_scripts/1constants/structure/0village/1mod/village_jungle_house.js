// =================================================================
// 战利品表定义区域
// =================================================================

const village_jungle_base_crops = [

    {
        groupName: "common",
        groupWeight: 70, 
        min: 2, max: 3,
        items: [
            { id: 'minecraft:wheat_seeds', ratio: 5 },
            { reference: 'lootjs:wheat_and_wheat_crate', ratio: 20 },

            { id: 'minecraft:melon_seeds', ratio: 5 },
            { reference: 'lootjs:melon_and_melon_crate', ratio: 20 },

            { id: 'minecraft:pumpkin_seeds', ratio: 5 },
            { reference: 'lootjs:pumpkin_and_pumpkin_crate', ratio: 20 },

            { reference: 'lootjs:cocoabeans_and_cocoabeans_crate', ratio: 20 },
        ]
    },

    {
        groupName: "special",
        groupWeight: 30, 
        min: 2, max: 3,
        items: [

            { reference: 'lootjs:rice_and_rice_crate', ratio: 25 },

            { reference: 'lootjs:tomato', ratio: 20 },
            { reference: 'lootjs:tomato_seeds', ratio: 5 },

            { reference: 'lootjs:potato_and_potato_crate', ratio: 50 },

            { reference: 'lootjs:onion_and_onion_crate', ratio: 25 },

            { id: 'farmersdelight:cabbage_seeds', ratio: 5 },
            { reference: 'farmersdelight:cabbage', ratio: 20 },

            { reference: 'lootjs:lettuce_seeds', ratio: 5 },
            { reference: 'lootjs:lettuce', ratio: 20 },

        ]
    },
]


const village_jungle_essential_wealth_equipment = [
    {
        groupName: "Villager Daily Essentials",
        groupWeight: 30, 
        min: 2, max: 3,
        items: [
            { id: 'minecraft:lantern', ratio: 10 },
            { id: 'minecraft:charcoal', ratio: 10 },
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
            { id: 'farm_and_charm:rope', ratio: 10 },    

            { id: 'supplementaries:sack', ratio: 10 },       
            { id: 'farmersdelight:wooden_basket', ratio: 10 },       
            { id: 'kaleidoscope_cookery:fruit_basket', ratio: 10 },       

            { id: 'kaleidoscope_cookery:stockpot', ratio: 10 }, 
            { id: 'kaleidoscope_cookery:pot', ratio: 10 }, 
            { id: 'kaleidoscope_cookery:stockpot_lid', ratio: 10 }, 
            { id: 'kaleidoscope_cookery:kitchen_shovel', ratio: 10 }, 

            { id: 'farmersdelight:cooking_pot', ratio: 10 }, 
            { id: 'farmersdelight:skillet', ratio: 10 },    
        ]
    },
]

const village_jungle_dish = [
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

            { id: 'youkaisfeasts:steamed_egg_in_bamboo', ratio: 10 },
            { id: 'youkaisfeasts:tutu_congee', ratio: 10 },
            { id: 'vanillacookbook:cooked_bamboo', ratio: 10 },
            { id: 'kitchenkarrot:bamboo_potato', ratio: 10 },

            { id: 'kaleidoscope_cookery:bamboo_tube_rice', ratio: 10 },

            { id: 'vanillacookbook:grilled_melon', ratio: 10 },
        ]
    },

    {
        groupName: "special",
        groupWeight: 30, 
        min: 2, max: 3,
        items: [

            { id: 'youkaisfeasts:kaguya_hime', ratio: 10 },

            { id: 'farmersdelight:stuffed_pumpkin', ratio: 10 },
            { id: 'kaleidoscope_chinesefood:pumpkin_porridge', ratio: 10 },
            { id: 'farmersdelight:pumpkin_soup', ratio: 10 },
        ]
    },
]


const village_jungle_drinks = [
    {
        groupName: "juice",
        groupWeight: 5,
        min: 1, max: 2,
        items: [
            { id: 'minecraft:potion', ratio: 10 , potion: "minecraft:water" }, 

            { id: 'vanillacookbook:melon_juice', ratio: 10 },
            { id: 'farmersdelight:melon_juice', ratio: 10 },
            { id: 'kaleidoscope_tavern:watermelon_juice', ratio: 10 },

            { id: 'vanillacookbook:chocolate_milk', ratio: 10 },
            { id: 'farmersdelight:hot_cocoa', ratio: 10 },

        ]
    },
]


const village_jungle_bakery_bread = [
    {
        groupName: "bread",
        groupWeight: 15,
        min: 1, max: 2,
        items: [
            { id: 'kitchenkarrot:leafy_fresh_chocolate', ratio: 10 },
            { id: 'kitchenkarrot:chocolate_croissant', ratio: 10 },
            { id: 'minecraft:cookie', ratio: 10 },

        ]
    },
]

const village_jungle_bakery_dessert = [
    {
        groupName: "dessert_chocolate",
        groupWeight: 15,
        min: 1, max: 2,
        items: [

            { id: 'vanillacookbook:chocolate_cake', ratio: 10 },
            { id: 'vanillacookbook:brownie', ratio: 10 },
            { id: 'vanillacookbook:chocolate_pudding', ratio: 10 },
            { id: 'rusticdelight:chocolate_pancake', ratio: 10 },
            { id: 'farmersdelight:chocolate_pie', ratio: 10 },
            { id: 'rusticdelight:chocolate_pancakes', ratio: 10 },


            { id: 'bakery:chocolate_cake', ratio: 10 },
            { id: 'bakery:chocolate_glazed_cookie', ratio: 10 },
            { id: 'bakery:chocolate_truffle', ratio: 10 },
            { id: 'bakery:chocolate_tart', ratio: 10 },
            { id: 'brewinandchewin:cocoa_fudge', ratio: 10 },
        ]
    },


    {
        groupName: "dessert_pumpkin",
        groupWeight: 15,
        min: 1, max: 2,
        items: [

            { id: 'minecraft:pumpkin_pie', ratio: 10 },
            { id: 'kitchenkarrot:fried_pumpkin_cake', ratio: 10 },
            { id: 'rusticdelight:pumpkin_pancakes', ratio: 10 },
            { id: 'kaleidoscope_cookery:fondant_pie', ratio: 10 },
        ]
    },
]

const village_jungle_bakery_cooking_material = [
    {
        groupName: "cooking",
        groupWeight: 15,
        min: 1, max: 2,
        items: [
            { id: 'minecraft:milk_bucket', ratio: 10 },           
            { id: 'minecraft:egg', ratio: 10 },  
            { id: 'minecraft:wheat', ratio: 10 },
            { id: 'minecraft:sugar', ratio: 10 },
            { id: 'kaleidoscope_cookery:flour', ratio: 10 },
            { reference: 'lootjs:cocoabeans_and_cocoabeans_crate', ratio: 10 },
            { reference: 'lootjs:pumpkin_and_pumpkin_crate', ratio: 10 },
            { reference: 'lootjs:sugar_and_sugar_crate', ratio: 10 },
            { reference: 'lootjs:wheat_and_wheat_crate', ratio: 10 },
        ]
    },
]


// =================================================================
// 事件注册区域
// =================================================================
const village_jungle_house_content = [
    [village_jungle_base_crops, 2, 2],
    [village_jungle_dish, 3, 4],
    [village_jungle_essential_wealth_equipment, 4, 4],
    [village_jungle_drinks, 1, 2],
];

const village_jungle_bakery_content = [

    [village_jungle_bakery_bread, 3, 3],
    [village_jungle_bakery_dessert, 3, 3],
    [village_jungle_bakery_cooking_material, 10, 10],

];


const loot_village_jungle_house = [
    [village_jungle_house_content, 8],
    [carpenterData.jungle, 1], 
    [village_jungle_bakery_content, 1],
];