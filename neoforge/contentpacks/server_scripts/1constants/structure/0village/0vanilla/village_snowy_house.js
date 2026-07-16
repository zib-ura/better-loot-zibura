// =================================================================
// 战利品表定义区域
// =================================================================

const village_snowy_crops = [

    {
        groupName: "common",
        groupWeight: 70, 
        min: 2, max: 3,
        items: [
            { reference: 'lootjs:wheat_seeds', ratio: 10 },
            { reference: 'lootjs:wheat_and_wheat_block', ratio: 40 },

            { reference: 'lootjs:potato_and_potato_block', ratio: 50 },

            { reference: 'lootjs:beetroot_seeds', ratio: 10 },
            { reference: 'lootjs:beetroot_and_beetroot_block', ratio: 40 },



        ]
    },

    {
        groupName: "special",
        groupWeight: 30, 
        min: 2, max: 3,
        items: [

            { reference: 'lootjs:onion', ratio: 25 },

            { reference: 'lootjs:cabbage_seeds', ratio: 5 },
            { reference: 'lootjs:cabbage', ratio: 20 },

            { reference: 'lootjs:lettuce_seeds', ratio: 5 },
            { reference: 'lootjs:lettuce', ratio: 20 },

            { reference: 'lootjs:blueberry', ratio: 25 },
            { reference: 'lootjs:persimmon_sapling', ratio: 5 },
            { reference: 'lootjs:persimmon', ratio: 20 },

            { reference: 'lootjs:egg', ratio: 25 },
        ]
    },
]

const village_snowy_essential_wealth_equipment = [


    {
        groupName: "Villager Daily Essentials",
        groupWeight: 30, 
        min: 2, max: 3,
        items: [
            // --- Household Utilities ---
            { id: 'minecraft:lantern', ratio: 10 },
            { id: 'minecraft:coal', ratio: 10 },
            { id: 'minecraft:charcoal', ratio: 10 },
            { id: 'minecraft:glass_bottle', ratio: 10 },
            { id: 'minecraft:bowl', ratio: 10 },
            { id: 'supplementaries:sack', ratio: 10 },       
            { id: 'farmersdelight:wooden_basket', ratio: 10 },       
            { id: 'kaleidoscope_cookery:fruit_basket', ratio: 10 },      
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

]

const village_snowy_dish = [
    {
        groupName: "common",
        groupWeight: 35,
        min: 2, max: 3,
        items: [
            { id: 'fruitsdelight:blueberry_jam', ratio: 10 },
            { id: 'fruitsdelight:blueberry_muffin', ratio: 10 },
            { id: 'fruitsdelight:blueberry_custard', ratio: 10 },
            { id: 'fruitsdelight:persimmon_jam', ratio: 10 },
            { id: 'fruitsdelight:persimmon_cookie', ratio: 10 },
            { id: 'fruitsdelight:dried_persimmon', ratio: 10 },

            { id: 'minecraft:baked_potato', ratio: 10 },
            // { id: 'minecraft:cooked_beef', ratio: 10 },
            // { id: 'minecraft:cooked_porkchop', ratio: 10 },
            // { id: 'minecraft:cooked_mutton', ratio: 10 },
            // { id: 'minecraft:cooked_chicken', ratio: 10 },
            { id: 'minecraft:beetroot_soup', ratio: 10 },     
            { id: 'minecraft:bread', ratio: 10 },

            // { id: 'farmersdelight:smoked_ham', ratio: 10 },
            // // { id: 'farmersdelight:cooked_bacon', ratio: 10 },
            // { id: 'farmersdelight:bone_broth', ratio: 10 },

            { id: 'dumplings_delight:pork_cabbage_boiled_dumpling', ratio: 10 },
            { id: 'dumplings_delight:mutton_boiled_dumpling', ratio: 10 },
            { id: 'dumplings_delight:pork_potato_boiled_dumpling', ratio: 10 },
            // { id: 'vanillacookbook:mushroom_scrambled_eggs', ratio: 10 },

            { id: 'vanillacookbook:mashed_potatoes', ratio: 10 },
            { id: 'brewery:mashed_potatoes', ratio: 10 },
            { id: 'vanillacookbook:potato_pancake', ratio: 10 },
            
            { id: 'kaleidoscope_cookery:donkey_soup', ratio: 10 },
            { id: 'kaleidoscope_cookery:chicken_and_mushroom_stew', ratio: 10 },
            { id: 'kaleidoscope_cookery:beef_meatball_soup', ratio: 10 },
            { id: 'kaleidoscope_cookery:borscht', ratio: 10 },
            { id: 'kaleidoscope_cookery:braised_beef_with_potatoes', ratio: 10 },
            { id: 'kaleidoscope_cookery:lamb_and_radish_soup', ratio: 10 },
            { id: 'kaleidoscope_cookery:pork_bone_soup', ratio: 10 },

            { id: 'kitchenkarrot:beetroot_salad', ratio: 10 }

        ]
    },

    {
        groupName: "special",
        groupWeight: 15, 
        min: 2, max: 3,
        items: [
            { id: 'minecraft:beetroot_soup', ratio: 1 },     

            // { id: 'youkaisfeasts:mushroom_soup', ratio: 10 },
            // { id: 'farmersdelight:mushroom_rice', ratio: 10 },
            // { id: 'dumplings_delight:pork_mushroom_wonton', ratio: 10 },
            // { id: 'dumplings_delight:mushroom_boiled_dumpling', ratio: 10 },
            // { id: 'dumplings_delight:chicken_mushroom_boiled_dumpling', ratio: 10 },
            // { id: 'rusticdelight:fried_mushrooms', ratio: 10 },

            { id: 'rationcraft:corned_beef', ratio: 10 },
            { id: 'rationcraft:salted_pork', ratio: 10 },

            { id: 'kaleidoscope_cookery:fried_spring_roll', ratio: 10 },
            { id: 'kaleidoscope_cookery:dough_drop_soup', ratio: 10 },

            { id: 'youkaisfeasts:borscht', ratio: 10 },
        ]
    },
]

const village_snowy_drinks = [
    {
        groupName: "vodka",
        groupWeight: 5,
        min: 1, max: 2,
        items: [
            { id: 'minecraft:potion', ratio: 10 , potion: "minecraft:water" }, 

            { id: 'kitchenkarrot:vodka', ratio: 10 },
            { id: 'brewinandchewin:vodka', ratio: 10 },
            { id: 'kaleidoscope_tavern:vodka', ratio: 10 },
        ]
    },
]


// =================================================================
// 事件注册区域
// =================================================================
const village_snowy_house_content = [
    [village_snowy_crops, 2, 2],
    [village_snowy_dish, 1, 1],
    [village_snowy_essential_wealth_equipment, 1, 1],
    [common_cooking_equipment, 1, 1],
    [village_snowy_drinks, 1, 2],
];


const loot_village_snowy_house = [
    [village_snowy_house_content, 9],
    // [carpenter_snowy, 1], 
];