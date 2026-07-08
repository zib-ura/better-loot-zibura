const food_supply = [
    {
        groupName: "common_food",
        groupWeight: 70,
        min: 2, max: 4,
        items: [
            { reference: "lootjs:pumpkin_and_pumpkin_crate" },
            { reference: "lootjs:melon_and_melon_crate" },
            { reference: "lootjs:corn_and_corn_crate" },
            { reference: "lootjs:apple_and_apple_crate" },
            { reference: "lootjs:beetroot_and_beetroot_crate" },

            { reference: "lootjs:mushroom_brown_and_mushroom_brown_crate" },
            { reference: "lootjs:mushroom_red_and_mushroom_red_crate" },
            { reference: "lootjs:carrot_and_carrot_crate" },
            { reference: "lootjs:egg_and_egg_crate" },
            { reference: "lootjs:potato_and_potato_crate" },
            { reference: "lootjs:sugar_and_sugar_crate" },
            { reference: "lootjs:cabbage_and_cabbage_crate" },

            { reference: "lootjs:lettuce_and_lettuce_crate" },
            { reference: "lootjs:onion_and_onion_crate" },
            { reference: "lootjs:strawberry_and_strawberry_crate" },
            { reference: "lootjs:tomato_and_tomato_crate" },
            { reference: "lootjs:rice_and_rice_crate" },
            { reference: "lootjs:wheat_and_wheat_crate" }, 


        ]
    },

    {
        groupName: "rare_food",
        groupWeight: 30,
        min: 2, max: 4,
        items: [
            { reference: "lootjs:cocoabeans_and_cocoabeans_crate" },

            { reference: "lootjs:corn_kernel_and_corn_kernel_crate" },

            { reference: "lootjs:berry_and_berry_crate" },
            { reference: "lootjs:glowberry_and_glowberry_crate" },
            { reference: "lootjs:chinese_cabbage_and_chinese_cabbage_crate" },
            { reference: "lootjs:eggplant_and_eggplant_crate" },
            { reference: "lootjs:fennel_and_fennel_crate" },
            { reference: "lootjs:garlic_chive_and_garlic_chive_crate" },
            { reference: "lootjs:garlic_and_garlic_crate" },
            { reference: "lootjs:greenonion_and_greenonion_crate" },

            { reference: "lootjs:pineapple_and_pineapple_crate" },
            { reference: "lootjs:bell_pepper_green_and_bell_pepper_green_crate" },
            { reference: "lootjs:bell_pepper_red_and_bell_pepper_red_crate" },
            { reference: "lootjs:bell_pepper_yellow_and_bell_pepper_yellow_crate" },
            { reference: "lootjs:coffee_and_coffee_crate" },
            { reference: "lootjs:roasted_coffee_and_roasted_coffee_crate" },
            // { reference: "lootjs:cotton_and_cotton_crate" },
            { reference: "lootjs:acorn_and_acorn_crate" },
            { reference: "lootjs:cherry_and_cherry_crate" },
            { reference: "lootjs:pinecone_and_pinecone_crate" },
            { reference: "lootjs:rosehip_and_rosehip_crate" },
            { reference: "lootjs:black_grape_and_black_grape_crate" },
            { reference: "lootjs:red_grape_and_red_grape_crate" },
            { reference: "lootjs:white_grape_and_white_grape_crate" },
            { reference: "lootjs:cucumber_and_cucumber_crate" },
            { reference: "lootjs:soybean_and_soybean_crate" },
            { reference: "lootjs:redbean_and_redbean_crate" },

            { id: "kaleidoscope_chinesefood:salt" },
            { id: "kaleidoscope_cookery:red_chili" },
            { id: "kaleidoscope_cookery:green_chili" },
            { id: "kaleidoscope_tavern:grape" },
            { id: "kaleidoscope_tavern:green_grape" },
            // { id: "youkaisfeasts:tea_leaves" }

        ]
    },
];

// =================================================================
// 事件注册区域
// =================================================================
const food_supply_content = [
    [food_supply, 8, 10],
];

const loot_food_supply = [
    [food_supply_content, 1],
];