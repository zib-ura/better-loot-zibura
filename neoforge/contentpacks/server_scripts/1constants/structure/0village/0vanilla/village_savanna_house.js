// =================================================================
// 战利品表定义区域（已按比例与组合规则重构）
// =================================================================

const village_savanna_base_crops = [
    {
        groupName: "base_crops",
        groupWeight: 100, 
        min: 2, 
        max: 3,
        items: [
            { id: 'minecraft:wheat_seeds', ratio: 5 },
            { reference: 'lootjs:wheat_and_wheat_block', ratio: 20 },

            { reference: 'lootjs:corn_seeds', ratio: 5 },
            { reference: 'lootjs:corn_and_corn_block', ratio: 20 },

            
        ]
    }
];

const village_savanna_other_crops = [
    {
        groupName: "savanna special",
        groupWeight: 80, 
        min: 2, max: 3,
        items: [
            
            { id: 'minecraft:melon_seeds', ratio: 5 },
            { id: 'minecraft:melon', ratio: 20 },
        ]
    },
    {
        groupName: "other",
        groupWeight: 20,
        min: 2, 
        max: 3,
        items: [

            { id: 'minecraft:carrot', ratio: 25 },

            { id: 'minecraft:potato', ratio: 25 },

            { reference: 'lootjs:tomato', ratio: 20 },
            { reference: 'lootjs:tomato_seeds', ratio: 5 },

            { reference: 'lootjs:onion', ratio: 25 },

            { id: 'farmersdelight:cabbage', ratio: 20 },
            { id: 'farmersdelight:cabbage_seeds', ratio: 5 },

            { reference: 'lootjs:lettuce', ratio: 20 },
            { reference: 'lootjs:lettuce_seeds', ratio: 5 },

            { reference: 'lootjs:rice', ratio: 25 },

            { id: 'rusticdelight:bell_pepper_yellow', ratio: 25 },

            { id: 'rusticdelight:bell_pepper_red', ratio: 25 },

            { id: 'rusticdelight:bell_pepper_green', ratio: 25 },
            
            { reference: 'lootjs:pepper_seeds', ratio: 5 },
            { reference: 'lootjs:pepper', ratio: 20 },

            { reference: 'lootjs:egg', ratio: 25 },

        ]
    }
];
const village_savanna_essential_wealth_equipment = [

    {
        groupName: "Villager Daily Essentials",
        groupWeight: 30, 
        min: 2, max: 3,
        items: [
            // --- Household Utilities ---
            { id: 'minecraft:lantern', ratio: 10 },
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

const village_savanna_dish = [
    {
        groupName: "common",
        groupWeight: 70,
        min: 2, max: 3,
        items: [
            { id: 'minecraft:bread', ratio: 10 },

            // corn dishes
            { id: 'corn_delight:cornbread', ratio: 10 },
            { id: 'corn_delight:tortilla', ratio: 10 },
            { id: 'corn_delight:tortilla_chip', ratio: 10 },
            { id: 'corn_delight:popcorn', ratio: 10 },
            { id: 'corn_delight:popcorn_box', ratio: 10 },
            { id: 'corn_delight:caramel_popcorn', ratio: 10 },
            { id: 'corn_delight:grilled_corn', ratio: 10 },
            { id: 'corn_delight:boiled_corn', ratio: 10 },
            { id: 'corn_delight:creamed_corn', ratio: 10 },

            { id: 'farm_and_charm:roasted_corn', ratio: 10 },

            // --- Minecraft 原版烤肉类 ---
            { id: 'minecraft:cooked_beef', ratio: 10 },       // 牛排
            { id: 'minecraft:cooked_porkchop', ratio: 10 },   // 熟猪排
            { id: 'minecraft:cooked_mutton', ratio: 10 },     // 熟羊肉
            { id: 'minecraft:cooked_chicken', ratio: 10 },    // 熟鸡肉
            // { id: 'minecraft:cooked_rabbit', ratio: 10 },     // 烤兔肉
            // { id: 'minecraft:cooked_cod', ratio: 10 },        // 熟鳕鱼
            // { id: 'minecraft:cooked_salmon', ratio: 10 }      // 熟鲑鱼
            { id: 'culturaldelights:popcorn', ratio: 10 },
            { id: 'culturaldelights:tortilla_chips', ratio: 10 },
            { id: 'culturaldelights:elote', ratio: 10 },
            { id: 'culturaldelights:creamed_corn', ratio: 10 },
            { id: 'culturaldelights:smoked_corn', ratio: 10 },
            { id: 'culturaldelights:empanada', ratio: 10 },

        ]
    },

    {
        groupName: "special",
        groupWeight: 30, 
        min: 2, max: 3,
        items: [
            { id: 'farmersdelight:smoked_ham', ratio: 10 },

            { id: 'corn_delight:corn_soup', ratio: 10 },
            { id: 'corn_delight:cornbread_stuffing', ratio: 10 },
            { id: 'corn_delight:nachos_bowl', ratio: 10 },
            { id: 'corn_delight:taco', ratio: 10 },
            { id: 'farmersdelight:mutton_wrap', ratio: 10 },        
            // { id: 'corn_delight:nachos_block', ratio: 10 },
            { id: 'corn_delight:corn_dog', ratio: 10 },
            { id: 'corn_delight:classic_corn_dog', ratio: 10 },

            { id: 'farm_and_charm:lamb_with_corn', ratio: 10 },
            { id: 'kaleidoscope_nether:corn_carrot_pork_rib_soup', ratio: 10 },

            { id: 'vintagedelight:stuffed_burrito', ratio: 10 },

            { id: 'rusticdelight:bell_pepper_pasta', ratio: 10 },

            { id: 'culturaldelights:fish_taco', ratio: 10 },
            { id: 'culturaldelights:chicken_taco', ratio: 10 },
            { id: 'culturaldelights:beef_burrito', ratio: 10 },
            { id: 'culturaldelights:pork_wrap', ratio: 10 },
        ]
    },
]

const village_savanna_drinks = [
    {
        groupName: "corn",
        groupWeight: 5,
        min: 1, max: 2,
        items: [
            { id: 'minecraft:potion', ratio: 5 , potion: "minecraft:water" }, 

            { id: 'corn_delight:creamy_corn_drink', ratio: 10 },

        ]
    },
]

// =================================================================
// 事件注册区域
// =================================================================

const village_savanna_house_content = [
    [village_savanna_base_crops, 1, 1],
    [village_savanna_other_crops, 1, 1],
    [village_savanna_dish, 3, 4],
    [village_savanna_essential_wealth_equipment, 4, 4],
    [village_savanna_drinks, 1, 2],
];

const loot_village_savanna_house = [
    [village_savanna_house_content, 9],
    [carpenterData.savanna, 1], 
];