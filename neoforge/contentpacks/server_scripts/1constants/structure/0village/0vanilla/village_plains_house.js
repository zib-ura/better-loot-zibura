// =================================================================
// 战利品表定义区域（Ratio 调整版：作物 10 / 种子 5）
// =================================================================

// 1. 原版与农夫乐事作物及物品包
const vanilla_village_plains_base_crops_and_bags = [
    {
        groupName: "Vanilla & Farmer's Delight Crops",
        groupWeight: 90, 
        min: 2, max: 3,
        items: [

            { id: 'minecraft:wheat_seeds', ratio: 5 },
            { reference: 'lootjs:wheat_and_wheat_crate', ratio: 20 },

            { reference: 'lootjs:carrot_and_carrot_crate', ratio: 25 },

            { reference: 'lootjs:potato_and_potato_crate', ratio: 25 },

            { id: 'minecraft:beetroot_seeds', ratio: 5 },
            { reference: 'lootjs:beetroot_and_beetroot_crate', ratio: 20 },

            { id: 'minecraft:pumpkin_seeds', ratio: 5 },
            { reference: 'lootjs:pumpkin_and_pumpkin_crate', ratio: 20 },

            { reference: 'lootjs:apple_and_apple_crate', ratio: 25 },

            { reference: 'lootjs:tomato_seeds', ratio: 5 },
            { reference: 'lootjs:tomato_and_tomato_crate', ratio: 20 },

            { reference: 'lootjs:onion_and_onion_crate', ratio: 25 },

            { id: 'farmersdelight:cabbage_seeds', ratio: 5 },
            { reference: 'lootjs:cabbage_and_cabbage_crate', ratio: 20 },

            { reference: 'lootjs:lettuce_seeds', ratio: 5 },
            { reference: 'lootjs:lettuce_and_lettuce_crate', ratio: 20 },

            { reference: 'lootjs:rice_and_rice_crate', ratio: 25 },

            { id: 'minecraft:sugar_cane', ratio: 25 },

            { reference: 'lootjs:pepper_seeds', ratio: 5 },
            { reference: 'lootjs:pepper_and_pepper_crate', ratio: 20 },

            { id: 'youkaisfeasts:tea_seeds', ratio: 5 },
            { id: 'youkaisfeasts:green_tea_leaves', ratio: 10 }, 
            { id: 'youkaisfeasts:white_tea_leaves', ratio: 10 },

        ]
    },
];

// 2. 其他模组的蔬菜与作物
const vanilla_village_plains_other_modded_crops = [
    // =================================================================
    // Group 1: Other Modded Crops
    // =================================================================
    {
        groupName: "Other Modded Crops",
        groupWeight: 90, 
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

            // --- Farm & Charm: Crops ---
            // 1. 草莓系列 (有种子，有作物，有Crate)
            { id: 'farm_and_charm:strawberry_seeds', ratio: 5 },
            { reference: 'farm_and_charm:strawberry', ratio: 20 }, 

            // 2. 大麦系列 (无Crate注册，直接拉满)
            { id: 'farm_and_charm:barley_seeds', ratio: 5 },
            { id: 'farm_and_charm:barley', ratio: 20 }, // 凑满总额 25

            // --- Youkais Feasts (妖怪茶会) ---
            // 3. 黄瓜系列 (有种子，有作物，有Crate)
            { id: 'lootjs:cucumber_seeds', ratio: 5 },
            { id: 'lootjs:cucumber', ratio: 20 },

            // 4. 燕麦系列 (有种子，有作物，有Crate；注意：原版有配置合并，用 reference)
            { reference: 'lootjs:oat_seeds', ratio: 5 },
            { reference: 'lootjs:oat', ratio: 20 },

            // 5. 大豆系列 (无种子，大豆兼任，有Crate)
            { id: 'lootjs:soybean', ratio: 25 },

            // 6. 红豆系列 (无种子，红豆兼任，有Crate)
            { id: 'lootjs:redbean', ratio: 25 },

            // // 7. 茶叶/茶树系列 (无Crate，属于直接25类型)
            // { id: 'youkaisfeasts:tea_seeds', ratio: 5 },
            // { id: 'youkaisfeasts:green_tea_leaves', ratio: 10 }, 
            // { id: 'youkaisfeasts:white_tea_leaves', ratio: 10 },

            // --- Dumplings Delight (饺子乐事) ---
            // 8. 大白菜系列 (有种子，有作物，有Crate)
            { id: 'dumplings_delight:chinese_cabbage_seeds', ratio: 5 },
            { id: 'dumplings_delight:chinese_cabbage', ratio: 20 },

            // 9. 茴香系列 (有种子，有作物，有Crate)
            { id: 'dumplings_delight:fennel_seeds', ratio: 5 },
            { id: 'dumplings_delight:fennel', ratio: 20 },

            // 10. 大蒜系列 (蒜瓣充当种子，有作物，有Crate)
            { id: 'dumplings_delight:garlic_clove', ratio: 5 },  
            { id: 'dumplings_delight:garlic', ratio: 20 },

            // 11. 韭菜系列 (有种子，有作物，有Crate)
            { id: 'dumplings_delight:garlic_chive_seeds', ratio: 5 },
            { id: 'dumplings_delight:garlic_chive', ratio: 20 },

            // 12. 小葱系列 (无单独种子，有Crate)
            { id: 'dumplings_delight:greenonion', ratio: 25 },

            // --- Thai Delight & Vintage Delight ---
            // 13. 木瓜系列 (有种子，有作物，有Crate；注意：木瓜在配置中被合并过)
            { reference: 'lootjs:papaya_seeds', ratio: 5 }, 
            { reference: 'lootjs:papaya', ratio: 20 },

            // 14. 青柠系列 (树苗充当种子，有作物，有Crate)
            { id: 'muffins_thaidelight:lime_sapling', ratio: 5 }, 
            { id: 'muffins_thaidelight:lime', ratio: 20 },

            // 15. 花生系列 (无单独种子，花生兼任，有Crate)
            { id: 'vintagedelight:peanut', ratio: 25 }
        ]
    },

];

// // 3. 花卉、稻草与相关方块
// const flowers_and_straw = [
//     // =================================================================
//     // Group 1: Flowers & Straw Items
//     // =================================================================
//     {
//         groupName: "Flowers & Straw",
//         groupWeight: 90, 
//         min: 2, max: 6, 
//         items: [
//             // --- Vanilla Small Flowers ---
//             { id: 'minecraft:dandelion', ratio: 5 },
//             { id: 'minecraft:poppy', ratio: 5 },
//             { id: 'minecraft:azure_bluet', ratio: 5 },
//             { id: 'minecraft:red_tulip', ratio: 5 },
//             { id: 'minecraft:orange_tulip', ratio: 5 },
//             { id: 'minecraft:white_tulip', ratio: 5 },
//             { id: 'minecraft:pink_tulip', ratio: 5 },
//             { id: 'minecraft:oxeye_daisy', ratio: 5 },
//             { id: 'minecraft:cornflower', ratio: 5 },

//             // --- Straw ---
//             { id: 'farmersdelight:straw', ratio: 10 }
//         ]
//     },
//     // =================================================================
//     // Group 2: Straw Bales
//     // =================================================================
//     {
//         groupName: "Straw Bales",
//         groupWeight: 10, 
//         min: 1, max: 2,
//         items: [
//             { id: 'farmersdelight:straw_bale', ratio: 10 }
//         ]
//     }
// ];



const vanilla_village_plains_essential_wealth_equipment = [

    {
        groupName: "Villager Daily Essentials",
        groupWeight: 30, 
        min: 2, max: 3,
        items: [
            { id: 'minecraft:lantern', ratio: 10 },
            { id: 'minecraft:glass_bottle', ratio: 10 },
            { id: 'minecraft:bowl', ratio: 10 },
            { id: 'farmersdelight:rope', ratio: 10 },
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
            { id: 'youkaisfeasts:kettle', ratio: 10 },     
            { id: 'kaleidoscope_cookery:stockpot', ratio: 10 }, 
            { id: 'kaleidoscope_cookery:pot', ratio: 10 }, 
            { id: 'kaleidoscope_cookery:stockpot_lid', ratio: 10 }, 
            { id: 'kaleidoscope_cookery:kitchen_shovel', ratio: 10 }, 
            { id: 'kaleidoscope_cookery:teapot', ratio: 10 }, 
            { id: 'farmersdelight:cooking_pot', ratio: 10 }, 
            { id: 'farmersdelight:skillet', ratio: 10 },    
            { id: 'kaleidoscope_chinesefood:pickle_jar', ratio: 10 }
        ]
    }
];
const vanilla_village_plains_dish = [

    {
        groupName: "common europe",
        groupWeight: 35,
        min: 2, max: 3,
        items: [
            { id: 'minecraft:bread', ratio: 10 },

            { id: 'moredelight:chicken_salad', ratio: 10 },
            { id: 'moredelight:potato_salad', ratio: 10 },

            { id: 'farmersdelight:cabbage_rolls', ratio: 10 },
            { id: 'farmersdelight:onion_soup', ratio: 10 },
            { id: 'farmersdelight:pumpkin_soup', ratio: 10 },
            { id: 'farmersdelight:chicken_soup', ratio: 10 },
            { id: 'farmersdelight:beef_stew', ratio: 10 },
            { id: 'farmersdelight:mixed_salad', ratio: 10 },

            { id: 'kitchenkarrot:cooked_vegan_pork', ratio: 10 },
            { id: 'kitchenkarrot:cooked_vegan_beef', ratio: 10 },
            { id: 'kitchenkarrot:flower_cake', ratio: 10 },
            { id: 'kitchenkarrot:seed_pie', ratio: 10 },
            { id: 'kitchenkarrot:fruit_cereal_porridge', ratio: 10 },
            { id: 'kitchenkarrot:chinese_crepe', ratio: 10 },

            { id: 'vanillacookbook:trail_mix', ratio: 10 },

            { id: 'farm_and_charm:farmer_salad', ratio: 10 },
            { id: 'farm_and_charm:goulash', ratio: 10 },
            { id: 'farm_and_charm:oat_pancake', ratio: 10 },
            { id: 'farm_and_charm:roasted_chicken', ratio: 10 },
            { id: 'farm_and_charm:sausage_with_oat_patty', ratio: 10 },
            { id: 'farm_and_charm:beef_patty_with_vegetables', ratio: 10 },
            { id: 'farm_and_charm:barley_patties_with_potatoes', ratio: 10 },
            { id: 'farm_and_charm:pasta_with_onion_sauce', ratio: 10 },
            { id: 'farm_and_charm:potato_soup', ratio: 10 },
            { id: 'farm_and_charm:onion_soup', ratio: 10 },
            { id: 'farm_and_charm:barley_soup', ratio: 10 },
            { id: 'farm_and_charm:simple_tomato_soup', ratio: 10 },
            { id: 'farm_and_charm:farmers_bread', ratio: 10 },
            { id: 'farm_and_charm:stuffed_chicken', ratio: 10 },
            { id: 'farm_and_charm:potato_with_roast_meat', ratio: 10 },

            { id: 'brewery:potato_salad', ratio: 10 },
            { id: 'brewery:mashed_potatoes', ratio: 10 },
            { id: 'brewery:sausage', ratio: 10 },
            { id: 'brewery:half_chicken', ratio: 10 },
            { id: 'brewery:dumplings', ratio: 10 },
            { id: 'brewery:fried_chicken', ratio: 10 },
            { id: 'brewery:pork_knuckle', ratio: 10 },
        ]
    },
    {
        groupName: "common asia",
        groupWeight: 35,
        min: 2, max: 3,
        items: [

            { id: 'casualnessdelight:bowl_of_sweet_rice', ratio: 10 },

            { id: 'kitchenkarrot:rice_cake', ratio: 10 },

            { id: 'farmersdelight:dumplings', ratio: 10 },
            { id: 'farmersdelight:fried_rice', ratio: 10 },
            { id: 'farmersdelight:bone_broth', ratio: 10 },
            { id: 'kaleidoscope_chinesefood:chinese_sauerkraut', ratio: 10 },
            { id: 'kaleidoscope_chinesefood:maocai', ratio: 10 },
            { id: 'kaleidoscope_chinesefood:pumpkin_porridge', ratio: 10 },
            { id: 'kaleidoscope_chinesefood:salted_egg', ratio: 10 },
            { id: 'kaleidoscope_chinesefood:century_egg', ratio: 10 },
            { id: 'kaleidoscope_chinesefood:tomato_egg_drop_soup', ratio: 10 },
            { id: 'kaleidoscope_chinesefood:century_egg_congee', ratio: 10 },

            { id: 'kaleidoscope_cookery:country_style_mixed_vegetables', ratio: 10 },
            { id: 'kaleidoscope_cookery:tomato_beef_brisket_soup', ratio: 10 },
            { id: 'kaleidoscope_cookery:shengjian_mantou', ratio: 10 },
            { id: 'kaleidoscope_cookery:zongzi', ratio: 10 },
            { id: 'kaleidoscope_cookery:dumpling', ratio: 10 },
            { id: 'kaleidoscope_cookery:baozi', ratio: 10 },
            { id: 'kaleidoscope_cookery:mantou', ratio: 10 },
            { id: 'kaleidoscope_cookery:cooked_meatball', ratio: 10 },
            { id: 'youkaisfeasts:tofu', ratio: 10 },
            { id: 'youkaisfeasts:bun', ratio: 10 },
            { id: 'youkaisfeasts:pork_rice_ball', ratio: 10 },

        ]
    },
    {
        groupName: "special europe",
        groupWeight: 15,
        min: 2, max: 3,
        items: [
            { id: 'minecraft:pumpkin_pie', ratio: 10 },

            { id: 'farmersdelight:stuffed_pumpkin', ratio: 10 },
            { id: 'farmersdelight:shepherds_pie', ratio: 10 },
            { id: 'farmersdelight:roast_chicken', ratio: 10 },
            { id: 'farmersdelight:vegetable_noodles', ratio: 10 },
            { id: 'farmersdelight:ratatouille', ratio: 10 },

            { id: 'farm_and_charm:farmers_breakfast', ratio: 10 },
            
            { id: 'moredelight:creamy_pasta_with_chicken_cuts', ratio: 10 },
            { id: 'moredelight:creamy_pasta_with_ham', ratio: 10 },
        ]
    },
    {
        groupName: "special asia",
        groupWeight: 15,
        min: 2, max: 3,
        items: [

            { id: 'kaleidoscope_chinesefood:red_rice_roll', ratio: 10 },
            { id: 'kaleidoscope_chinesefood:wonton_noodles', ratio: 10 },
            { id: 'kaleidoscope_chinesefood:sauerkraut_beef_noodles', ratio: 10 },
            { id: 'kaleidoscope_chinesefood:beef_with_scrambled_eggs_rice', ratio: 10 },
            { id: 'kaleidoscope_chinesefood:tomato_egg_noodles', ratio: 10 },
            { id: 'kaleidoscope_chinesefood:four_joy_meatballs', ratio: 10 },
            { id: 'kaleidoscope_chinesefood:yangzhou_fried_rice', ratio: 10 },
            { id: 'kaleidoscope_chinesefood:steamed_rice_rolls', ratio: 10 },

            { id: 'kaleidoscope_chinesefood:pork_chili_noodles', ratio: 10 },
            { id: 'kaleidoscope_chinesefood:stir_fried_three_fresh_vegetables_rice', ratio: 10 },
            { id: 'kaleidoscope_chinesefood:stir_fried_yellow_beef_rice', ratio: 10 },
            { id: 'kaleidoscope_chinesefood:twice_cooked_pork_rice', ratio: 10 },
            { id: 'kaleidoscope_chinesefood:sichuan_wonton', ratio: 10 },
            { id: 'kaleidoscope_chinesefood:sichuan_boiled_fish', ratio: 10 },
            { id: 'kaleidoscope_chinesefood:sichuan_boiled_pork_slices', ratio: 10 },



            { id: 'kaleidoscope_cookery:spicy_blood_stew', ratio: 10 },
            { id: 'kaleidoscope_cookery:spicy_rabbit_head', ratio: 10 },
            { id: 'kaleidoscope_cookery:stuffed_tiger_skin_pepper', ratio: 10 },
            { id: 'kaleidoscope_cookery:yakitori', ratio: 10 },
            { id: 'kaleidoscope_cookery:spicy_chicken', ratio: 10 },
            { id: 'kaleidoscope_cookery:fish_flavored_shredded_pork_rice_bowl', ratio: 10 },
            { id: 'kaleidoscope_cookery:stir_fried_pork_with_peppers_rice_bowl', ratio: 10 },
            { id: 'kaleidoscope_cookery:braised_beef_rice_bowl', ratio: 10 },
            { id: 'kaleidoscope_cookery:stir_fried_beef_offal_rice_bowl', ratio: 10 },
            { id: 'kaleidoscope_cookery:delicious_egg_fried_rice', ratio: 10 },
            { id: 'kaleidoscope_cookery:fried_caterpillar', ratio: 10 },
            { id: 'kaleidoscope_cookery:four_joy_meatball_soup', ratio: 10 },
            { id: 'kaleidoscope_cookery:udon_noodle', ratio: 10 },
            { id: 'kaleidoscope_cookery:sweet_and_sour_pork_rice_bowl', ratio: 10 },
            { id: 'kaleidoscope_cookery:scramble_egg_with_tomatoes_rice_bowl', ratio: 10 },

            { id: 'kaleidoscope_nether:luosifen', ratio: 10 },
            { id: 'kaleidoscope_nether:chongqing_noodles', ratio: 10 },
            { id: 'kaleidoscope_nether:mapo_tofu_rice', ratio: 10 },
            { id: 'kaleidoscope_nether:spicy_pot_rice', ratio: 10 },
            { id: 'kaleidoscope_nether:pepper_pork_belly_chicken_soup', ratio: 10 },
            { id: 'kaleidoscope_nether:couples_lung_slice', ratio: 10 },
            { id: 'kaleidoscope_nether:braised_lion_head', ratio: 10 },
            { id: 'kaleidoscope_nether:braised_pork_rice', ratio: 10 },
            
            { id: 'youkaisfeasts:longevity_noodles', ratio: 10 },
            { id: 'youkaisfeasts:tuscan_salmon', ratio: 10 },
            { id: 'youkaisfeasts:lions_head', ratio: 10 },
            // { id: 'youkaisfeasts:rice_powder_pork', ratio: 10 },
            { id: 'youkaisfeasts:sauce_grilled_fish', ratio: 10 },

            { id: 'kitchenkarrot:curry_udon', ratio: 10 },
        ]
    },
]



const vanilla_village_plains_drinks = [
    {
        groupName: "tea",
        groupWeight: 5,
        min: 1, max: 2,
        items: [

            { id: 'minecraft:potion', ratio: 10 , potion: "minecraft:water" }, 

            { id: 'youkaisfeasts:genmai_tea', ratio: 10 },
            { id: 'youkaisfeasts:cornflower_tea', ratio: 10 },
            { id: 'youkaisfeasts:yellow_tea', ratio: 10 },
            { id: 'youkaisfeasts:dark_tea', ratio: 10 },
            { id: 'youkaisfeasts:oolong_tea', ratio: 10 },
            { id: 'youkaisfeasts:black_tea', ratio: 10 },
            { id: 'youkaisfeasts:white_tea', ratio: 10 },
            { id: 'youkaisfeasts:green_tea', ratio: 10 },
            { id: 'youkaisfeasts:green_water', ratio: 10 },


            { id: 'kaleidoscope_chinesefood:hk_milk_tea', ratio: 10 },
            { id: 'kaleidoscope_cookery:flower_tea', ratio: 10 },
            { id: 'kaleidoscope_cookery:tieguanyin', ratio: 10 },
            { id: 'kaleidoscope_cookery:barley_tea', ratio: 10 },
            { id: 'kaleidoscope_chinesefood:lapsang', ratio: 10 },
        ]
    },

    // {
    //     groupName: "condiments",
    //     groupWeight: 5,
    //     min: 1, max: 2,
    //     items: [
    //         { id: 'rusticdelight:cooking_oil', ratio: 10 },
    //         { id: 'youkaisfeasts:soy_sauce_bottle', ratio: 10 },
    //     ]
    // },
    
    {
        groupName: "wine",
        groupWeight: 5,
        min: 1, max: 2,
        items: [
            { id: 'youkaisfeasts:mio', ratio: 10 },
            // { id: 'youkaisfeasts:red_wine_bottle', ratio: 10 },
            // { id: 'youkaisfeasts:white_wine_bottle', ratio: 10 },
            { id: 'brewery:beer_hops', ratio: 3 },
            { id: 'brewery:beer_barley', ratio: 3 },
            { id: 'brewery:beer_wheat', ratio: 3 },
            { id: 'kitchenkarrot:mead', ratio: 10 },
            { id: 'kitchenkarrot:acorn_wine', ratio: 10 },
            // { id: 'kaleidoscope_tavern:wine', ratio: 10 },
        ]
    },

    {
        groupName: "juice",
        groupWeight: 5,
        min: 1, max: 2,
        items: [
            { id: 'vanillacookbook:apple_juice', ratio: 10 },
            { id: 'farmersdelight:apple_cider', ratio: 10 },
            { id: 'farm_and_charm:strawberry_tea', ratio: 10 },

        ]
    },
]

const vanilla_village_plains_bakery_dessert = [
{
        groupName: "dessert_base",
        groupWeight: 15,
        min: 1, max: 2,
        items: [
            { id: 'bakery:bundt_cake', ratio: 10 },
            { id: 'minecraft:cake', ratio: 10 },
            { id: 'farmersdelight:cake_slice', ratio: 10 },
            { id: 'kaleidoscope_cookery:fondant_pie', ratio: 10 },
            { id: 'kitchenkarrot:sweet_loaf', ratio: 10 },
            { id: 'kaleidoscope_cookery:sticky_candy', ratio: 10 },
            { id: 'bakery:cornet', ratio: 10 },
            { id: 'bakery:linzer_tart', ratio: 10 },
            { id: 'bakery:jam_roll', ratio: 10 },
            { id: 'bakery:pudding', ratio: 10 },
        ]
    },
    {
        groupName: "dessert_strawberry",
        groupWeight: 15,
        min: 1, max: 2,
        items: [
            { id: 'bakery:strawberry_cake', ratio: 10 },
            { id: 'farm_and_charm:grandmothers_strawberry_cake', ratio: 10 },
            { id: 'bakery:strawberry_cupcake', ratio: 10 },
            { id: 'bakery:strawberry_jam', ratio: 10 },
        ]
    },
    {
        groupName: "dessert_apple",
        groupWeight: 15,
        min: 1, max: 2,
        items: [
            { id: 'bakery:apple_pie', ratio: 10 },
            { id: 'farmersdelight:apple_pie', ratio: 10 },
            { id: 'youkaisfeasts:candy_apple', ratio: 10 },
            { id: 'bakery:apple_cupcake', ratio: 10 },
            { id: 'bakery:apple_jam', ratio: 10 },
            { id: 'vanillacookbook:apple_pie', ratio: 10 },
        ]
    },
    {
        groupName: "dessert_other_fruits",
        groupWeight: 5,
        min: 1, max: 2,
        items: [
            { id: 'minecraft:pumpkin_pie', ratio: 10 },
            { id: 'youkaisfeasts:tarte_lune', ratio: 10 },
        ]
    },

]

const vanilla_village_plains_bakery_bread = [
    {
        groupName: "bread",
        groupWeight: 15,
        min: 1, max: 2,
        items: [
            { id: 'minecraft:bread', ratio: 10 },
            { id: 'bakery:bread_with_jam', ratio: 10 },
            { id: 'bakery:misslilitu_biscuit', ratio: 10 },
            { id: 'bakery:waffle', ratio: 10 },
            { id: 'bakery:bun', ratio: 10 },
            { id: 'bakery:braided_bread', ratio: 10 },
            { id: 'bakery:toast', ratio: 10 },
            { id: 'bakery:baguette', ratio: 10 },
            { id: 'bakery:bread', ratio: 10 },
            { id: 'bakery:crusty_bread', ratio: 10 },
            { id: 'bakery:croissant', ratio: 10 },
        ]
    },

]

const vanilla_village_plains_bakery_cooking_material = [
    {
        groupName: "cooking",
        groupWeight: 15,
        min: 1, max: 2,
        items: [

            { id: 'kaleidoscope_cookery:flour', ratio: 10 },
            { id: 'minecraft:milk_bucket', ratio: 10 },           
            { id: 'minecraft:egg', ratio: 10 },             
            { reference: 'lootjs:apple_and_apple_crate', ratio: 10 },
            { reference: 'lootjs:strawberry_and_strawberry_crate', ratio: 10 },
            { reference: 'lootjs:sugar_and_sugar_crate', ratio: 10 },
            { reference: 'lootjs:wheat_and_wheat_crate', ratio: 10 },

        ]
    },

]

// =================================================================
// 事件注册区域
// =================================================================

const village_plains_bakery_content = [

    [vanilla_village_plains_bakery_dessert, 3, 3],
    [vanilla_village_plains_bakery_bread, 3, 3],
    [vanilla_village_plains_bakery_cooking_material, 10, 10],

];

const village_plains_house_content = [
    [vanilla_village_plains_base_crops_and_bags, 1, 1],
    [vanilla_village_plains_other_modded_crops, 1, 1],
    [vanilla_village_plains_dish, 3, 4],
    [vanilla_village_plains_essential_wealth_equipment, 4, 4],
    [vanilla_village_plains_drinks, 1, 2],
];


const loot_village_plains_house = [
    [village_plains_house_content, 8],
    [carpenterData.plains, 1], 
    [village_plains_bakery_content, 1],
]