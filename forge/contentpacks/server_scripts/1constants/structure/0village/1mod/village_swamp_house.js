// =================================================================
// 战利品表定义区域
// =================================================================

const village_swamp_base_crops = [

    {
        groupName: "common",
        groupWeight: 70, 
        min: 2, max: 3,
        items: [
            { id: 'minecraft:wheat_seeds', ratio: 5 },
            { reference: 'lootjs:wheat_and_wheat_crate', ratio: 20 },

            { reference: 'lootjs:rice_and_rice_crate', ratio: 100 },

        ]
    },

    {
        groupName: "special",
        groupWeight: 30, 
        min: 2, max: 3,
        items: [

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

const village_swamp_essential_wealth_equipment = [
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

const village_swamp_dish = [
    {
        groupName: "common",
        groupWeight: 70,
        min: 2, max: 3,
        items: [

            { id: 'kaleidoscope_cookery:slime_ball_meal', ratio: 10 },
            { id: 'vanillacookbook:jelly', ratio: 10 },
            { id: 'kaleidoscope_cookery:qingtuan', ratio: 10 },


            { id: 'minecraft:cooked_cod', ratio: 10 },
            { id: 'minecraft:cooked_salmon', ratio: 10 },

            { id: 'vanillacookbook:fish_stew', ratio: 10 },

            { id: 'barbequesdelight:grilled_cod_skewer', ratio: 10 },
            { id: 'barbequesdelight:grilled_salmon_skewer', ratio: 10 },

            { id: 'farmersdelight:salmon_roll', ratio: 10 },
            { id: 'farmersdelight:cod_roll', ratio: 10 },            

            { id: 'rusticdelight:calamari_roll', ratio: 10 },    

            { id: 'kaleidoscope_cookery:pufferfish_soup', ratio: 10 },
            { id: 'kaleidoscope_cookery:seafood_miso_soup', ratio: 10 },            

            
            { id: 'minecraft:cooked_beef', ratio: 10 },       // 牛排
            { id: 'minecraft:cooked_porkchop', ratio: 10 },   // 熟猪排
            { id: 'minecraft:cooked_mutton', ratio: 10 },     // 熟羊肉
            { id: 'minecraft:cooked_chicken', ratio: 10 },    // 熟鸡肉
            // rice
            { reference: 'lootjs:cooked_rice', ratio: 10 },
            { id: 'moredelight:cooked_rice_with_chicken_cuts', ratio: 10 },
            { id: 'moredelight:cooked_rice_with_beef', ratio: 10 },
            { id: 'moredelight:cooked_rice_with_porkchop', ratio: 10 },

        ]
    },

    {
        groupName: "special",
        groupWeight: 30, 
        min: 2, max: 3,
        items: [
            { id: 'farmersdelight:fish_stew', ratio: 10 },  
            { id: 'farmersdelight:baked_cod_stew', ratio: 10 },  
            { id: 'farmersdelight:squid_ink_pasta', ratio: 10 },  


            { id: 'kaleidoscope_chinesefood:yellow_croaker_soup', ratio: 10 },
            { id: 'kaleidoscope_chinesefood:sichuan_boiled_fish', ratio: 10 },
            { id: 'kaleidoscope_chinesefood:yellow_croaker_soup', ratio: 10 },

            { id: 'kaleidoscope_cookery:oil_splashed_fish', ratio: 10 },
            { id: 'kaleidoscope_cookery:braised_fish_rice_bowl', ratio: 10 },
            

            { id: 'youkaisfeasts:seafood_miso_soup', ratio: 10 },
            { id: 'youkaisfeasts:han_palace', ratio: 10 },
            { id: 'youkaisfeasts:shirayuki', ratio: 10 },
            { id: 'youkaisfeasts:grilled_eel_over_rice', ratio: 10 },
        
        ]
    },
]

// =================================================================
// 事件注册区域
// =================================================================
const village_swamp_house_content = [
    [village_swamp_base_crops, 1, 1],
    // [village_swamp_other_crops, 1, 1],
    [village_swamp_dish, 3, 4],
    [village_swamp_essential_wealth_equipment, 4, 4],

];

const loot_village_swamp_house = [
    [village_swamp_house_content, 9],
    [carpenterData.swamp, 1], 
];