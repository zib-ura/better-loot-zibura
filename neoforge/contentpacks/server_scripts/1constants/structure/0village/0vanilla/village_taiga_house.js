// =================================================================
// 战利品表定义区域
// =================================================================

const village_taiga_base_crops = [
    {
        groupName: "base_crops",
        groupWeight: 80, 
        min: 2, 
        max: 3,
        items: [
            { reference: 'lootjs:wheat_seeds', ratio: 10 },
            { reference: 'lootjs:wheat_and_wheat_block', ratio: 40 },
            
            { reference: 'lootjs:pumpkin_seeds', ratio: 10 },
            { reference: 'lootjs:pumpkin_slice_and_pumpkin_slice_block', ratio: 40 },

            { reference: 'lootjs:potato_and_potato_block', ratio: 50 },

            { reference: 'lootjs:beetroot_seeds', ratio: 10 },
            { reference: 'lootjs:beetroot_and_beetroot_block', ratio: 40 },

            { reference: 'lootjs:barley', ratio: 20 },
            { reference: 'lootjs:barley_seeds', ratio: 5 },

        ]
    }
];

const village_taiga_other_crops = [
    {
        groupName: "taiga",
        groupWeight: 80, 
        min: 2, 
        max: 3,
        items: [
            // --- 甜浆果 Sweet Berries 系列 (有作物、有种子[加Mod项]、箱子) ---
            { reference: 'lootjs:sweet_berries', ratio: 15 },
            { reference: 'lootjs:sweet_berry_pips', ratio: 5 },
            { reference: 'lootjs:berry_and_berry_block', ratio: 10 },

            // --- 啤酒花 Hops 系列 (仅作物，无种子与箱子，保留原25分配) ---
            { reference: 'lootjs:hops', ratio: 25 },

            // --- 红蘑菇 Red Mushroom 系列 (有作物、箱子，无种子) ---
            { reference: 'lootjs:red_mushroom', ratio: 25 },

            // --- 棕蘑菇 Brown Mushroom 系列 (有作物、箱子，无种子) ---
            { reference: 'lootjs:brown_mushroom', ratio: 25 },
        ]
    },

    {
        groupName: "other",
        groupWeight: 20, 
        min: 2, 
        max: 3,
        items: [
            // --- 胡萝卜 Carrot 系列 (仅作物，总权重 25) ---
            { reference: 'lootjs:carrot', ratio: 25 },

            // --- 番茄 Tomato 系列 (作物+种子，总权重 25) ---
            { reference: 'lootjs:tomato', ratio: 20 },
            { reference: 'lootjs:tomato_seeds', ratio: 5 },

            // --- 洋葱 Onion 系列 (仅作物，总权重 25) ---
            { reference: 'lootjs:onion', ratio: 25 },

            // --- 卷心菜 Cabbage 系列 (作物+种子，总权重 25) ---
            { reference: 'lootjs:cabbage', ratio: 20 },
            { reference: 'lootjs:cabbage_seeds', ratio: 5 },

            // --- 生菜 Lettuce 系列 (作物+种子，总权重 25) ---
            { reference: 'lootjs:lettuce', ratio: 20 },
            { reference: 'lootjs:lettuce_seeds', ratio: 5 },

            { reference: 'lootjs:honey_bottle', ratio: 25 },

            { reference: 'lootjs:blueberry', ratio: 25 },
            { reference: 'lootjs:hawberry_sapling', ratio: 5 },
            { reference: 'lootjs:hawberry', ratio: 20 },
            { reference: 'lootjs:persimmon_sapling', ratio: 5 },
            { reference: 'lootjs:persimmon', ratio: 20 },

            { reference: 'lootjs:egg', ratio: 25 },
        ]
    },
];
const village_taiga_essential_wealth_equipment = [
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

const village_taiga_dish = [
    {
        groupName: "common",
        groupWeight: 35,
        min: 2, max: 3,
        items: [

            { id: 'minecraft:bread', ratio: 10 },

            { id: 'minecraft:mushroom_stew', ratio: 10 },
            { id: 'vanillacookbook:mushroom_scrambled_eggs', ratio: 10 },
            { id: 'youkaisfeasts:mushroom_soup', ratio: 10 },
            { id: 'farmersdelight:bone_broth', ratio: 10 },
            { id: 'rusticdelight:fried_mushrooms', ratio: 10 },    

            { id: 'berry_good:sweet_berry_meatballs', ratio: 10 },

            { id: 'minecraft:cooked_beef', ratio: 10 },      
            { id: 'minecraft:cooked_porkchop', ratio: 10 },   
            { id: 'minecraft:cooked_mutton', ratio: 10 },    
            { id: 'minecraft:cooked_chicken', ratio: 10 },   
        ]
    },

    {
        groupName: "special",
        groupWeight: 15, 
        min: 2, max: 3,
        items: [
            { id: 'minecraft:mushroom_stew', ratio: 1 },

            { id: 'farmersdelight:grilled_salmon', ratio: 10 },

            { id: 'kaleidoscope_cookery:brown_mushroom_pot_soup', ratio: 10 },            
            { id: 'kaleidoscope_cookery:red_mushroom_pot_soup', ratio: 10 },    

        ]
    },
]

const village_taiga_drinks = [
    {
        groupName: "berry",
        groupWeight: 5,
        min: 1, max: 2,
        items: [
            { id: 'minecraft:potion', ratio: 10 , potion: "minecraft:water" }, 

            { id: 'fruitsdelight:hawberry_tea', ratio: 10 },
            
            { id: 'kitchenkarrot:mead', ratio: 10 },
            // { id: 'kaleidoscope_tavern:honey_wine', ratio: 10 },
            { id: 'youkaisfeasts:mead', ratio: 10 },
            // { id: 'kaleidoscope_tavern:red_queen', ratio: 10 },
            // { id: 'kaleidoscope_tavern:plum_wine', ratio: 10 },
            // { id: 'kaleidoscope_tavern:sweet_berry_wine', ratio: 10 },

        ]
    },
]



const village_taiga_bakery_products = [
    {
        groupName: "taiga_cakes",
        groupWeight: 15,
        min: 1, max: 1,
        items: [
            { id: 'bakery:sweetberry_cake', ratio: 10 },
            { id: 'vanillacookbook:berry_cake', ratio: 10 },
        ]
    },

    {
        groupName: "taiga_tarts_pies",
        groupWeight: 15,
        min: 1, max: 1,
        items: [
            { id: 'farmersdelight:sweet_berry_cheesecake', ratio: 10 },
            { id: 'kitchenkarrot:sweet_berry_tart', ratio: 10 },
            { id: 'vanillacookbook:berry_pie', ratio: 10 },


        ]
    },

    {
        groupName: "taiga_dessert_puddings_cold",
        groupWeight: 15,
        min: 1, max: 1,
        items: [
            { id: 'fruitsdelight:blueberry_custard', ratio: 10 },
            { id: 'vanillacookbook:berry_ice_cream', ratio: 10 },
        ]
    },

    {
        groupName: "taiga_dessert_teatime_treats_slices",
        groupWeight: 15,
        min: 3, max: 4,
        items: [
            { id: 'farmersdelight:sweet_berry_cheesecake_slice', ratio: 10 },
            { id: 'farmersdelight:sweet_berry_cookie', ratio: 10 },
            { id: 'vanillacookbook:berry_cookie', ratio: 10 },
            { id: 'vanillacookbook:berry_pancake', ratio: 10 },
            { id: 'vanillacookbook:berry_brownie', ratio: 10 },
            { id: 'vanillacookbook:berry_candy', ratio: 10 },
            { id: 'vanillacookbook:berry_cake_slice', ratio: 10 },
            { id: 'bakery:sweetberry_cake_slice', ratio: 10 },
            { id: 'bakery:sweetberry_glazed_cookie', ratio: 10 },
            { id: 'bakery:sweetberry_cupcake', ratio: 10 },

            { id: 'fruitsdelight:dried_persimmon', ratio: 10 },
            { id: 'fruitsdelight:persimmon_cookie', ratio: 10 },
            { id: 'fruitsdelight:blueberry_muffin', ratio: 10 },
            { id: 'fruitsdelight:hawberry_stick', ratio: 10 },
            { id: 'fruitsdelight:hawberry_roll', ratio: 10 },
            { id: 'fruitsdelight:hawberry_sheet', ratio: 10 },
            { id: 'rusticdelight:fruit_beignet', ratio: 10 },

        ]
    },

    {
        groupName: "taiga_bread",
        groupWeight: 15,
        min: 3, max: 4,
        items: [
            { id: 'minecraft:bread', ratio: 10 },
        ]
    },

]

const village_taiga_bakery_baking_material = [
    {
        groupName: "baking_material",
        groupWeight: 15,
        min: 3, max: 4,
        items: basic_baking_material
    },

    {
        groupName: "taiga_jam",
        groupWeight: 15,
        min: 1, max: 1,
        items: [

            { reference: 'lootjs:sweetberry_jam', ratio: 10 },
            { id: 'fruitsdelight:blueberry_jam', ratio: 10 },
            { id: 'fruitsdelight:hawberry_jam', ratio: 10 },
            { id: 'fruitsdelight:persimmon_jam', ratio: 10 },

        ]
    },

]
// =================================================================
// 事件注册区域
// =================================================================
const village_taiga_house_content = [
    [village_taiga_base_crops, 1, 1],
    [village_taiga_other_crops, 1, 1],
    [village_taiga_dish, 3, 4],
    [village_taiga_essential_wealth_equipment, 4, 4],
    [village_taiga_drinks, 1, 2],
];

const village_taiga_bakery_content = [

    [village_taiga_bakery_products, 8, 8],
    [village_taiga_bakery_baking_material, 5, 5],
    [village_smith_small_iron_knife, 1, 1],

];


const loot_village_taiga_house = [
    [village_taiga_house_content, 8],
    [carpenterData.taiga, 1], 
    [village_taiga_bakery_content, 1],
];