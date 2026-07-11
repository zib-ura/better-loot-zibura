// =================================================================
// 战利品表定义区域
// =================================================================
const village_plains_base_crops = [
    {
        groupName: "Vanilla & Farmer's Delight Crops",
        groupWeight: 90, 
        min: 2, max: 3,
        items: [

            { reference: 'lootjs:wheat_seeds', ratio: 5 },
            { reference: 'lootjs:wheat_and_wheat_block', ratio: 20 },

            { reference: 'lootjs:carrot_and_carrot_block', ratio: 25 },

            { reference: 'lootjs:potato_and_potato_block', ratio: 25 },

            { reference: 'lootjs:beetroot_seeds', ratio: 5 },
            { reference: 'lootjs:beetroot_and_beetroot_block', ratio: 20 },

            { reference: 'lootjs:pumpkin_seeds', ratio: 5 },
            { reference: 'lootjs:pumpkin_slice_and_pumpkin_slice_block', ratio: 20 },

            { reference: 'lootjs:apple_and_apple_block', ratio: 25 },

            { reference: 'lootjs:tomato_seeds', ratio: 5 },
            { reference: 'lootjs:tomato_and_tomato_block', ratio: 20 },

            { reference: 'lootjs:onion_and_onion_block', ratio: 25 },

            { reference: 'lootjs:cabbage_seeds', ratio: 5 },
            { reference: 'lootjs:cabbage_and_cabbage_block', ratio: 20 },

            { reference: 'lootjs:lettuce_seeds', ratio: 5 },
            { reference: 'lootjs:lettuce_and_lettuce_block', ratio: 20 },

            { reference: 'lootjs:rice_and_rice_block', ratio: 25 },

            { reference: 'lootjs:egg_and_egg_block', ratio: 25 },

            { reference: 'lootjs:pepper_seeds', ratio: 5 },
            { reference: 'lootjs:pepper_and_pepper_block', ratio: 20 },

            { id: 'youkaisfeasts:tea_seeds', ratio: 5 },
            { id: 'youkaisfeasts:green_tea_leaves', ratio: 10 }, 
            { id: 'youkaisfeasts:white_tea_leaves', ratio: 10 },

        ]
    },
];

const village_plains_other_crops = [

    {
        groupName: "vegetable",
        groupWeight: 50, 
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
            { reference: 'lootjs:strawberry_seeds', ratio: 5 },
            { reference: 'lootjs:strawberry', ratio: 20 }, 

            // 2. 大麦系列 (无Crate注册，直接拉满)
            { reference: 'lootjs:barley_seeds', ratio: 5 },
            { reference: 'lootjs:barley', ratio: 20 }, // 凑满总额 25

            // --- Youkais Feasts (妖怪茶会) ---
            // 3. 黄瓜系列 (有种子，有作物，有Crate)
            { reference: 'lootjs:cucumber_seeds', ratio: 5 },
            { reference: 'lootjs:cucumber', ratio: 20 },

            // 4. 燕麦系列 (有种子，有作物，有Crate；注意：原版有配置合并，用 reference)
            { reference: 'lootjs:oat_seeds', ratio: 5 },
            { reference: 'lootjs:oat', ratio: 20 },

            // 5. 大豆系列 (无种子，大豆兼任，有Crate)
            { reference: 'lootjs:soybean', ratio: 25 },

            // 6. 红豆系列 (无种子，红豆兼任，有Crate)
            { reference: 'lootjs:redbean', ratio: 25 },

            // // 7. 茶叶/茶树系列 (无Crate，属于直接25类型)
            // { id: 'youkaisfeasts:tea_seeds', ratio: 5 },
            // { id: 'youkaisfeasts:green_tea_leaves', ratio: 10 }, 
            // { id: 'youkaisfeasts:white_tea_leaves', ratio: 10 },

            // --- Dumplings Delight (饺子乐事) ---
            // 8. 大白菜系列 (有种子，有作物，有Crate)
            { reference: 'lootjs:chinese_cabbage_seeds', ratio: 5 },
            { reference: 'lootjs:chinese_cabbage', ratio: 20 },

            // 9. 茴香系列 (有种子，有作物，有Crate)
            { reference: 'lootjs:fennel_seeds', ratio: 5 },
            { reference: 'lootjs:fennel', ratio: 20 },

            // 10. 大蒜系列 (蒜瓣充当种子，有作物，有Crate)
            { reference: 'lootjs:garlic_clove', ratio: 5 },  
            { reference: 'lootjs:garlic', ratio: 20 },

            // 11. 韭菜系列 (有种子，有作物，有Crate)
            { reference: 'lootjs:garlic_chive_seeds', ratio: 5 },
            { reference: 'lootjs:garlic_chive', ratio: 20 },

            // 12. 小葱系列 (无单独种子，有Crate)
            { reference: 'lootjs:greenonion', ratio: 25 },

            // 15. 花生系列 (无单独种子，花生兼任，有Crate)
            { reference: 'lootjs:peanut', ratio: 25 },


            { reference: 'lootjs:eggplant_seeds', ratio: 5 },
            { reference: 'lootjs:eggplant', ratio: 20 },
        
            { reference: 'lootjs:oat_seeds', ratio: 5 },
            { reference: 'lootjs:oat', ratio: 20 },

            { id: 'kaleidoscope_chinesefood:chinese_sauerkraut', ratio: 25 },


        ]
    },

    {
        groupName: "fruit",
        groupWeight: 50, 
        min: 2, max: 3,
        items: [

            { reference: 'lootjs:apple_sapling', ratio: 5 },
            { reference: 'lootjs:apple', ratio: 20 },
            { reference: 'lootjs:fig_sapling', ratio: 5 },
            { reference: 'lootjs:fig', ratio: 20 },
            { reference: 'lootjs:kiwi_sapling', ratio: 5 },
            { reference: 'lootjs:kiwi', ratio: 20 },
            { reference: 'lootjs:orange_sapling', ratio: 5 },
            { reference: 'lootjs:orange', ratio: 20 },
            { reference: 'lootjs:lemon_seeds', ratio: 5 },
            { reference: 'lootjs:lemon', ratio: 20 },
            { reference: 'lootjs:pear_sapling', ratio: 5 },
            { reference: 'lootjs:pear', ratio: 20 },

            { reference: 'lootjs:black_grape_seeds', ratio: 5 },
            { reference: 'lootjs:black_grape', ratio: 20 },
            { reference: 'lootjs:white_grape_seeds', ratio: 5 },
            { reference: 'lootjs:white_grape', ratio: 20 },
            { reference: 'lootjs:red_grape_seeds', ratio: 5 },
            { reference: 'lootjs:red_grape', ratio: 20 },
            
            { id: "kaleidoscope_tavern:grape", ratio: 25 },
            { id: "kaleidoscope_tavern:green_grape", ratio: 25 },
            { id: "kaleidoscope_tavern:grapevine", ratio: 25 },

            { reference: 'lootjs:strawberry_seeds', ratio: 5 },
            { reference: 'lootjs:strawberry', ratio: 20 }, 
            
            { reference: 'lootjs:lime_sapling', ratio: 5 }, 
            { reference: 'lootjs:lime', ratio: 20 }

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



const village_plains_essential_wealth_equipment = [

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

const village_plains_dish = [

    {
        groupName: "common europe",
        groupWeight: 35,
        min: 2, max: 3,
        items: [
            { id: 'minecraft:bread', ratio: 10 },

            { id: 'moredelight:chicken_salad', ratio: 10 },
            { id: 'moredelight:potato_salad', ratio: 10 },

            { id: 'farmersdelight:cabbage_rolls', ratio: 10 },
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

            { id: 'vintagedelight:cheese_pasta', ratio: 10 },
            { id: 'vintagedelight:roasted_peanut', ratio: 10 },
            { id: 'vintagedelight:oatmeal', ratio: 10 },

            { id: 'culturaldelights:poached_eggplants', ratio: 10 },
            { id: 'culturaldelights:smoked_eggplant', ratio: 10 },
            { id: 'culturaldelights:smoked_white_eggplant', ratio: 10 },
            { id: 'culturaldelights:hearty_salad', ratio: 10 },

            { id: 'rusticdelight:potato_salad', ratio: 10 },
                        
            { id: 'casualnessdelight:yorkshire_pudding', ratio: 10 },

        ]
    },
    {
        groupName: "common asia",
        groupWeight: 35,
        min: 2, max: 3,
        items: [
            { id: 'casualnessdelight:potato_bobo_chicken', ratio: 10 },
            { id: 'casualnessdelight:cabbage_bobo_chicken', ratio: 10 },
            { id: 'casualnessdelight:chicken_bobo_chicken', ratio: 10 },

            { id: 'casualnessdelight:bowl_of_sweet_rice', ratio: 10 },

            { id: 'kitchenkarrot:rice_cake', ratio: 10 },

            { id: 'farmersdelight:dumplings', ratio: 10 },
            { id: 'farmersdelight:fried_rice', ratio: 10 },
            { id: 'farmersdelight:bone_broth', ratio: 10 },
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

            { id: 'vintagedelight:pickled_egg', ratio: 10 },
            { id: 'vintagedelight:pickled_pepper', ratio: 10 },
            { reference: 'lootjs:pickle', ratio: 10 },
            { id: 'vintagedelight:kimchi', ratio: 10 },
            { id: 'vintagedelight:pickled_onion', ratio: 10 },
            { id: 'vintagedelight:pickled_beetroot', ratio: 10 },

            { id: 'rusticdelight:spring_rolls', ratio: 10 },
            { id: 'rusticdelight:fried_dumplings', ratio: 10 },

            { id: 'casualnessdelight:beef_noodles', ratio: 10 },
            { id: 'casualnessdelight:roast_gluten', ratio: 10 },
            { id: 'casualnessdelight:bowl_of_paper_wrapped_fish', ratio: 10 },

            { id: 'fruitsdelight:pear_with_rock_sugar', ratio: 10 },
        ]
    },
    {
        groupName: "special europe",
        groupWeight: 15,
        min: 2, max: 3,
        items: [
            { id: 'minecraft:pumpkin_pie', ratio: 10 },

            { id: 'farmersdelight:onion_soup', ratio: 10 },
            { id: 'farmersdelight:pumpkin_soup', ratio: 10 },
            { id: 'farmersdelight:chicken_soup', ratio: 10 },
            { id: 'farmersdelight:beef_stew', ratio: 10 },
            { id: 'farmersdelight:stuffed_pumpkin', ratio: 10 },
            { id: 'farmersdelight:shepherds_pie', ratio: 10 },
            { id: 'farmersdelight:roast_chicken', ratio: 10 },
            { id: 'farmersdelight:vegetable_noodles', ratio: 10 },
            { id: 'farmersdelight:ratatouille', ratio: 10 },

            { id: 'farm_and_charm:farmers_breakfast', ratio: 10 },
            
            { id: 'moredelight:creamy_pasta_with_chicken_cuts', ratio: 10 },
            { id: 'moredelight:creamy_pasta_with_ham', ratio: 10 },

            { id: 'vintagedelight:cucumber_salad', ratio: 10 },
            
            { id: 'culturaldelights:eggplant_parmesan', ratio: 10 },

            { id: 'casualnessdelight:quiche_lorraine', ratio: 10 },


            { id: 'brewinandchewin:pizza', ratio: 10 },
            { id: 'kitchenkarrot:feast_pizza', ratio: 10 },
            { id: 'vanillacookbook:pizza', ratio: 10 },
            { id: 'vintagedelight:cheese_pizza', ratio: 10 },
            { id: 'vintagedelight:meat_pizza', ratio: 10 },
        ]
    },
    {
        groupName: "special asia",
        groupWeight: 15,
        min: 2, max: 3,
        items: [

            { id: 'casualnessdelight:bobo_chicken', ratio: 10 },

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

            { id: 'vintagedelight:pickle_soup', ratio: 10 },
            { id: 'vintagedelight:ghostly_chili', ratio: 10 },
            { id: 'vintagedelight:pad_thai', ratio: 10 },

            { id: 'fruitsdelight:orange_chicken', ratio: 10 },
            { id: 'fruitsdelight:fig_chicken_stew', ratio: 10 },
            { id: 'fruitsdelight:orange_marinated_pork', ratio: 10 },
        ]
    },
]

const village_plains_drinks = [
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

            { id: 'fruitsdelight:kiwi_juice', ratio: 10 },
            { id: 'fruitsdelight:orange_juice', ratio: 10 },
            { id: 'fruitsdelight:lemon_juice', ratio: 10 },
            { id: 'fruitsdelight:pear_juice', ratio: 10 }
        ]
    },
]

const village_plains_bakery_products = [
    {
        groupName: "plains_cakes",
        groupWeight: 15,
        min: 1, max: 1,
        items: [
            { id: 'minecraft:cake', ratio: 10 },
            { id: 'bakery:strawberry_cake', ratio: 10 },
            { id: 'bakery:bundt_cake', ratio: 10 },
            { id: 'vanillacookbook:carrot_cake', ratio: 10 },
            { id: 'vanillacookbook:cheesecake', ratio: 10 },
            { id: 'vanillacookbook:ice_cream_cake', ratio: 10 },

            { id: 'rusticdelight:pancakes', ratio: 10 },
            { id: 'rusticdelight:vegetable_pancakes', ratio: 10 },
            { id: 'rusticdelight:pumpkin_pancakes', ratio: 10 },
        ]
    },

    {
        groupName: "plains_tarts_pies",
        groupWeight: 15,
        min: 1, max: 1,
        items: [
            { id: 'bakery:linzer_tart', ratio: 10 },
            { id: 'bakeries:egg_tart', ratio: 10 },
            { id: 'fruitsdelight:fig_tart', ratio: 10 },
            { id: 'fruitsdelight:lemon_tart', ratio: 10 },
            { id: 'kitchenkarrot:carrot_tart', ratio: 10 },
            { id: 'kitchenkarrot:egg_tart', ratio: 10 },
            { id: 'seeddelight:seed_tart', ratio: 10 },
            { id: 'youkaishomecoming:tarte_lune', ratio: 10 },

            { id: 'bakery:apple_pie', ratio: 10 },
            { id: 'farmersdelight:apple_pie', ratio: 10 },
            { id: 'kaleidoscope_cookery:fondant_pie', ratio: 10 },
            { id: 'kitchenkarrot:seed_pie', ratio: 10 },
            { id: 'kitchenkarrot:flower_cake', ratio: 10 },
            { id: 'minecraft:pumpkin_pie', ratio: 10 },
            { id: 'vanillacookbook:apple_pie', ratio: 10 },

        ]
    },

    {
        groupName: "plains_dessert_puddings_cold",
        groupWeight: 15,
        min: 1, max: 1,
        items: [
            { id: 'bakery:pudding', ratio: 10 },
            { id: 'fruitsdelight:fig_pudding', ratio: 10 },
            { id: 'vanillacookbook:pudding', ratio: 10 },        

            { id: 'fruitsdelight:kiwi_popsicle', ratio: 10 },
            { id: 'youkaishomecoming:big_popsicle', ratio: 10 },
            { id: 'youkaishomecoming:milk_popsicle', ratio: 10 },

            { id: 'vanillacookbook:ice_cream', ratio: 10 },
            { id: 'vanillacookbook:pumpsicle', ratio: 10 },
            { id: 'kaleidoscope_twilight:frozen_bun', ratio: 10 },
        ]
    },

    {
        groupName: "plains_dessert_teatime_treats_slices",
        groupWeight: 15,
        min: 3, max: 4,
        items: [
            // 小点心类
            { id: 'bakery:cornet', ratio: 10 },               
            { id: 'bakery:jam_roll', ratio: 10 },           
            { id: 'youkaisfeasts:candy_apple', ratio: 10 },   
            { id: 'kaleidoscope_cookery:sticky_candy', ratio: 10 }, 

            { id: 'fruitsdelight:baked_pear', ratio: 10 },

            { id: 'bakery:strawberry_glazed_cookie', ratio: 10 },
            { id: 'fruitsdelight:lemon_cookie', ratio: 10 },
            { id: 'rusticdelight:syrup_cookie', ratio: 10 },
            { id: 'vanillacookbook:potato_cookie', ratio: 10 },
            { id: 'vanillacookbook:pumpkin_cookie', ratio: 10 },
            { id: 'vintagedelight:oatmeal_cookie', ratio: 10 },

            { id: 'bakery:apple_cupcake', ratio: 10 },
            { id: 'bakery:strawberry_cupcake', ratio: 10 },

            { id: 'bakery:bundt_cake_slice', ratio: 10 },
            { id: 'bakery:strawberry_cake_slice', ratio: 10 },
            { id: 'farmersdelight:cake_slice', ratio: 10 },
            { id: 'vanillacookbook:carrot_cake_slice', ratio: 10 },
            { id: 'vanillacookbook:ice_cream_cake_slice', ratio: 10 },
            { id: 'bakery:apple_pie_slice', ratio: 10 },
            { id: 'bakery:linzer_tart_slice', ratio: 10 },
            { id: 'farmersdelight:apple_pie_slice', ratio: 10 },
            { id: 'farmersdelight:pumpkin_pie_slice', ratio: 10 }, 
            { id: 'youkaishomecoming:tarte_lune_slice', ratio: 10 },
            { id: 'bakery:pudding_slice', ratio: 10 },
            { id: 'fruitsdelight:fig_pudding_slice', ratio: 10 },

            { id: 'rusticdelight:fruit_beignet', ratio: 10 },
            { id: 'rusticdelight:pancake', ratio: 10 },
            { id: 'rusticdelight:vegetable_pancake', ratio: 10 },
            { id: 'rusticdelight:pumpkin_pancake', ratio: 10 },
        ]
    },

    {
        groupName: "plains_bread",
        groupWeight: 15,
        min: 3, max: 4,
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

const village_plains_bakery_baking_material = [
    {
        groupName: "baking_material",
        groupWeight: 15,
        min: 3, max: 4,
        items: basic_baking_material
    },

    {
        groupName: "plains_jam",
        groupWeight: 15,
        min: 1, max: 1,
        items: [

            { reference: 'lootjs:apple_jam', ratio: 10 },
            { id: 'bakery:strawberry_jam', ratio: 10 },
            { id: 'fruitsdelight:fig_jam', ratio: 10 },
            { id: 'fruitsdelight:kiwi_jam', ratio: 10 },
            { id: 'fruitsdelight:lemon_jam', ratio: 10 },
            { id: 'fruitsdelight:orange_jam', ratio: 10 },
            { id: 'fruitsdelight:pear_jam', ratio: 10 },

        ]
    },

]
// =================================================================
// 事件注册区域
// =================================================================

const village_plains_bakery_content = [

    [village_plains_bakery_products, 8, 8],
    [village_plains_bakery_baking_material, 5, 5],
    [village_smith_small_iron_knife, 1, 1],
];

const village_plains_house_content = [
    [village_plains_base_crops, 1, 1],
    [village_plains_other_crops, 1, 1],
    [village_plains_dish, 3, 4],
    [village_plains_essential_wealth_equipment, 4, 4],
    [village_plains_drinks, 1, 2],
];

const loot_village_plains_house = [
    [village_plains_house_content, 8],
    [carpenterData.plains, 1], 
    [village_plains_bakery_content, 1],
]