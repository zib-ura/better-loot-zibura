// =================================================================
// 战利品表定义区域
// =================================================================

const vanilla_village_taiga_base_crops = [
    {
        groupName: "base_crops",
        groupWeight: 80, 
        min: 2, 
        max: 3,
        items: [
            { id: 'minecraft:wheat_seeds', ratio: 10 },
            { reference: 'lootjs:wheat_and_wheat_crate', ratio: 40 },
            
            { id: 'minecraft:pumpkin_seeds', ratio: 10 },
            { reference: 'lootjs:pumpkin_and_pumpkin_crate', ratio: 40 },

            { reference: 'lootjs:potato_and_potato_crate', ratio: 50 },

            { id: 'minecraft:beetroot_seeds', ratio: 10 },
            { reference: 'lootjs:beetroot_and_beetroot_crate', ratio: 40 },

            { id: 'farm_and_charm:barley', ratio: 20 },
            { id: 'farm_and_charm:barley_seeds', ratio: 5 },
        ]
    }
];

const vanilla_village_taiga_other_crops = [
    {
        groupName: "taiga",
        groupWeight: 80, 
        min: 2, 
        max: 3,
        items: [
            // --- 甜浆果 Sweet Berries 系列 (有作物、有种子[加Mod项]、箱子) ---
            { id: 'minecraft:sweet_berries', ratio: 15 },
            { id: 'berry_good:sweet_berry_pips', ratio: 5 },
            { reference: 'lootjs:berry_and_berry_crate', ratio: 10 },

            // --- 啤酒花 Hops 系列 (仅作物，无种子与箱子，保留原25分配) ---
            { id: 'brewery:hops', ratio: 25 },



            // --- 红蘑菇 Red Mushroom 系列 (有作物、箱子，无种子) ---
            { id: 'minecraft:red_mushroom', ratio: 15 },
            { reference: 'lootjs:mushroom_red_and_mushroom_red_crate', ratio: 10 },

            // --- 棕蘑菇 Brown Mushroom 系列 (有作物、箱子，无种子) ---
            { id: 'minecraft:brown_mushroom', ratio: 15 },
            { reference: 'lootjs:mushroom_brown_and_mushroom_brown_crate', ratio: 10 },
        ]
    },

    {
        groupName: "other",
        groupWeight: 20, 
        min: 2, 
        max: 3,
        items: [
            // --- 胡萝卜 Carrot 系列 (仅作物，总权重 25) ---
            { id: 'minecraft:carrot', ratio: 25 },

            // --- 番茄 Tomato 系列 (作物+种子，总权重 25) ---
            { reference: 'lootjs:tomato', ratio: 20 },
            { reference: 'lootjs:tomato_seeds', ratio: 5 },

            // --- 洋葱 Onion 系列 (仅作物，总权重 25) ---
            { reference: 'lootjs:onion', ratio: 25 },

            // --- 卷心菜 Cabbage 系列 (作物+种子，总权重 25) ---
            { id: 'farmersdelight:cabbage', ratio: 20 },
            { id: 'farmersdelight:cabbage_seeds', ratio: 5 },

            // --- 生菜 Lettuce 系列 (作物+种子，总权重 25) ---
            { reference: 'lootjs:lettuce', ratio: 20 },
            { reference: 'lootjs:lettuce_seeds', ratio: 5 },

            { id: 'minecraft:honey_bottle', ratio: 25 },

        ]
    },
];
const vanilla_village_taiga_essential_wealth_equipment = [
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

const vanilla_village_taiga_dish = [
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

            { id: 'minecraft:cooked_beef', ratio: 10 },       // 牛排
            { id: 'minecraft:cooked_porkchop', ratio: 10 },   // 熟猪排
            { id: 'minecraft:cooked_mutton', ratio: 10 },     // 熟羊肉
            { id: 'minecraft:cooked_chicken', ratio: 10 },    // 熟鸡肉
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

const vanilla_village_taiga_drinks = [
    {
        groupName: "berry",
        groupWeight: 5,
        min: 1, max: 2,
        items: [
            { id: 'minecraft:potion', ratio: 10 , potion: "minecraft:water" }, 

            { id: 'kitchenkarrot:mead', ratio: 10 },
            // { id: 'kaleidoscope_tavern:honey_wine', ratio: 10 },
            { id: 'youkaisfeasts:mead', ratio: 10 },
            // { id: 'kaleidoscope_tavern:red_queen', ratio: 10 },
            // { id: 'kaleidoscope_tavern:plum_wine', ratio: 10 },
            // { id: 'kaleidoscope_tavern:sweet_berry_wine', ratio: 10 },

        ]
    },
]



const vanilla_village_taiga_bakery_dessert = [
    {
        groupName: "dessert",
        groupWeight: 15,
        min: 1, max: 2,
        items: [
            { id: 'brewinandchewin:sweet_berry_jam', ratio: 10 },
            // { id: 'vanillacookbook:berry_cookie', ratio: 10 },
            // { id: 'vanillacookbook:berry_juice', ratio: 10 },
            // { id: 'vanillacookbook:berry_milk', ratio: 10 },
            // { id: 'vanillacookbook:berry_candy', ratio: 10 },
            // { id: 'vanillacookbook:berry_pancake', ratio: 10 },
            { id: 'moredelight:toast_with_sweet_berries', ratio: 10 },
            { id: 'farmersdelight:sweet_berry_cookie', ratio: 10 },

            // { id: 'vanillacookbook:berry_pie', ratio: 10 },
            // { id: 'vanillacookbook:berry_cake_slice', ratio: 10 },
            // { id: 'vanillacookbook:berry_cake', ratio: 10 },
            // { id: 'vanillacookbook:berry_brownie', ratio: 10 },
            { id: 'kitchenkarrot:sweet_berry_tart', ratio: 10 },
            { id: 'farmersdelight:sweet_berry_cheesecake', ratio: 10 },
            { id: 'moredelight:toast_with_sweet_berries', ratio: 10 },
            { id: 'bakery:sweetberry_cupcake', ratio: 10 },
            { id: 'bakery:sweetberry_glazed_cookie', ratio: 10 },
            { id: 'bakery:sweetberry_cake', ratio: 10 },
        ]
    },

]

const vanilla_village_taiga_bakery_cooking_material = [
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
            { reference: 'lootjs:berry_and_berry_crate', ratio: 10 },
            { reference: 'lootjs:sugar_and_sugar_crate', ratio: 10 },
            { reference: 'lootjs:wheat_and_wheat_crate', ratio: 10 },
        ]
    },
]
// =================================================================
// 事件注册区域
// =================================================================
const village_taiga_house_content = [
    [vanilla_village_taiga_base_crops, 1, 1],
    [vanilla_village_taiga_other_crops, 1, 1],
    [vanilla_village_taiga_dish, 3, 4],
    [vanilla_village_taiga_essential_wealth_equipment, 4, 4],
    [vanilla_village_taiga_drinks, 1, 2],
];

const village_taiga_bakery_content = [

    [vanilla_village_taiga_bakery_dessert, 3, 3],
    [vanilla_village_taiga_bakery_cooking_material, 10, 10],

];


const loot_village_taiga_house = [
    [village_taiga_house_content, 8],
    [carpenterData.taiga, 1], 
    [village_taiga_bakery_content, 1],
];