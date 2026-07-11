// =================================================================
// 战利品表定义区域
// =================================================================

const village_ocean_base_crops = [

    {
        groupName: "common",
        groupWeight: 70, 
        min: 2, max: 3,
        items: [
            { reference: 'lootjs:wheat_seeds', ratio: 5 },
            { reference: 'lootjs:wheat_and_wheat_block', ratio: 20 },

            { reference: 'lootjs:kelp_and_kelp_block', ratio: 25 },

        ]
    },

    {
        groupName: "special",
        groupWeight: 30, 
        min: 2, max: 3,
        items: [
            { reference: 'lootjs:rice_and_rice_block', ratio: 25 },

            { reference: 'lootjs:potato_and_potato_block', ratio: 25 },

            { reference: 'lootjs:onion_and_onion_block', ratio: 25 },

            { reference: 'lootjs:cabbage_seeds', ratio: 5 },
            { reference: 'lootjs:cabbage', ratio: 20 },

            { reference: 'lootjs:lettuce_seeds', ratio: 5 },
            { reference: 'lootjs:lettuce', ratio: 20 },

            { reference: 'lootjs:egg', ratio: 25 },
        ]
    },
]

const village_ocean_wealth_equipment = [
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

const village_ocean_dish = [
    {
        groupName: "common",
        groupWeight: 70,
        min: 2, max: 3,
        items: [


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
            



            { id: 'vanillacookbook:kelp_salad', ratio: 10 },          
            { id: 'kitchenkarrot:crispy_bread_with_kelp', ratio: 10 },           
            { id: 'farmersdelight:kelp_roll_slice', ratio: 10 },   
            { id: 'kaleidoscope_chinesefood:seaweed_egg_drop_soup', ratio: 10 },            
            { id: 'youkaisfeasts:onigili', ratio: 10 },  
   
            { id: 'culturaldelights:tropical_roll', ratio: 10 },
            { id: 'culturaldelights:sushi', ratio: 10 },
            { id: 'culturaldelights:pufferfish_roll', ratio: 10 },
            { id: 'culturaldelights:midori_roll_slice', ratio: 10 },
            { id: 'culturaldelights:egg_roll', ratio: 10 },
            { id: 'culturaldelights:chicken_roll_slice', ratio: 10 },
            { id: 'culturaldelights:calamari_roll', ratio: 10 },
            { id: 'culturaldelights:tamago', ratio: 10 },
            { id: 'culturaldelights:rice_ball', ratio: 10 },


            { id: 'vintagedelight:salted_cod', ratio: 10 },
            { id: 'vintagedelight:salted_salmon', ratio: 10 },
            { id: 'vintagedelight:surstromming', ratio: 10 },
            { id: 'brewinandchewin:kippers', ratio: 10 },
            { id: 'culturaldelights:pickle', ratio: 10 },
            { id: 'vintagedelight:pickled_onion', ratio: 10 },
            { id: 'vintagedelight:pickled_beetroot', ratio: 10 },
            { id: 'vintagedelight:pickle', ratio: 10 },
            { id: 'vintagedelight:pickled_egg', ratio: 10 },
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

            { id: 'kaleidoscope_cookery:oil_splashed_fish', ratio: 10 },
            { id: 'kaleidoscope_cookery:braised_fish_rice_bowl', ratio: 10 },
            

            { id: 'youkaisfeasts:seafood_miso_soup', ratio: 10 },
            { id: 'youkaisfeasts:han_palace', ratio: 10 },
            { id: 'youkaisfeasts:shirayuki', ratio: 10 },
            { id: 'youkaisfeasts:grilled_eel_over_rice', ratio: 10 },

            { id: 'farmersdelight:kelp_roll', ratio: 10 },  
            { id: 'farmersdelight:noodle_soup', ratio: 10 },  
            { id: 'farmersdelight:rice_roll_medley_block', ratio: 10 },  
           
            { id: 'culturaldelights:midori_roll', ratio: 10 },
            { id: 'culturaldelights:chicken_roll', ratio: 10 },
            { id: 'culturaldelights:exotic_roll_medley', ratio: 10 },

            { id: 'rustic_delight:fried_calamari', ratio: 10 },

        ]
    },
]


// =================================================================
// 事件注册区域
// =================================================================
const village_ocean_house_content = [
    [village_ocean_base_crops, 2, 2],
    [village_ocean_wealth_equipment, 3, 4],
    [village_ocean_dish, 4, 4],

];

const loot_village_ocean_house = [
    [village_ocean_house_content, 1],
];