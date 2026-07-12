const food_supply = [
    {
        groupName: "common_food",
        groupWeight: 70,
        min: 2, max: 4,
        items: [
            { reference: "lootjs:pumpkin_slice_and_pumpkin_slice_block" },
            { reference: "lootjs:melon_slice_and_melon_slice_block" },
            { reference: "lootjs:corn_and_corn_block" },
            { reference: "lootjs:apple_and_apple_block" },
            { reference: "lootjs:beetroot_and_beetroot_block" },

            { reference: "lootjs:brown_mushroom_and_brown_mushroom_block" },
            { reference: "lootjs:red_mushroom_and_red_mushroom_block" },
            { reference: "lootjs:carrot_and_carrot_block" },
            { reference: "lootjs:egg_and_egg_block" },
            { reference: "lootjs:potato_and_potato_block" },
            { reference: "lootjs:cabbage_and_cabbage_block" },

            { reference: "lootjs:lettuce_and_lettuce_block" },
            { reference: "lootjs:onion_and_onion_block" },
            { reference: "lootjs:strawberry_and_strawberry_block" },
            { reference: "lootjs:tomato_and_tomato_block" },
            { reference: "lootjs:rice_and_rice_block" },
            { reference: "lootjs:wheat_and_wheat_block" }, 


        ]
    },

    {
        groupName: "rare_food",
        groupWeight: 30,
        min: 2, max: 4,
        items: [
            { reference: "lootjs:cocoabeans_and_cocoabeans_block" },

            { reference: "lootjs:corn_seeds_and_corn_seeds_block" },

            { reference: "lootjs:berry_and_berry_block" },
            { reference: "lootjs:glowberry_and_glowberry_block" },
            { reference: "lootjs:chinese_cabbage_and_chinese_cabbage_block" },
            { reference: "lootjs:eggplant_and_eggplant_block" },
            { reference: "lootjs:fennel_and_fennel_block" },
            { reference: "lootjs:garlic_chive_and_garlic_chive_block" },
            { reference: "lootjs:garlic_and_garlic_block" },
            { reference: "lootjs:greenonion_and_greenonion_block" },

            { reference: "lootjs:pineapple_and_pineapple_block" },
            { reference: "lootjs:bell_pepper_green_and_bell_pepper_green_block" },
            { reference: "lootjs:bell_pepper_red_and_bell_pepper_red_block" },
            { reference: "lootjs:bell_pepper_yellow_and_bell_pepper_yellow_block" },
            { reference: "lootjs:coffee_and_coffee_block" },
            { reference: "lootjs:roasted_coffee_and_roasted_coffee_block" },
            // { reference: "lootjs:cotton_and_cotton_block" },
            { reference: "lootjs:acorn_and_acorn_block" },
            { reference: "lootjs:cherry_and_cherry_block" },
            { reference: "lootjs:pinecone_and_pinecone_block" },
            { reference: "lootjs:rosehip_and_rosehip_block" },
            { reference: "lootjs:black_grape_and_black_grape_block" },
            { reference: "lootjs:red_grape_and_red_grape_block" },
            { reference: "lootjs:white_grape_and_white_grape_block" },
            { reference: "lootjs:cucumber_and_cucumber_block" },
            { reference: "lootjs:soybean_and_soybean_block" },
            { reference: "lootjs:redbean_and_redbean_block" },

            { reference: "lootjs:salt" },
            { reference: "lootjs:pepper_and_pepper_block" },

            { id: "kaleidoscope_tavern:grape" },
            { id: "kaleidoscope_tavern:green_grape" },
            // { id: "youkaisfeasts:tea_leaves" }

            { reference: "lootjs:white_eggplant_and_white_eggplant_block" },
            { reference: "lootjs:avocado_and_avocado_block" },
            { reference: "lootjs:bayberry_and_bayberry_block" },
            { reference: "lootjs:blueberry_and_blueberry_block" },
            { reference: "lootjs:cranberry_and_cranberry_block" },
            { reference: "lootjs:durian_flesh_and_durian_flesh_block" },
            { reference: "lootjs:fig_and_fig_block" },
            { reference: "lootjs:gearo_berry_and_gearo_berry_block" },
            { reference: "lootjs:hamimelon_slice_and_hamimelon_slice_block" },
            { reference: "lootjs:hawberry_and_hawberry_block" },
            { reference: "lootjs:kiwi_and_kiwi_block" },
            { reference: "lootjs:lemon_and_lemon_block" },
            { reference: "lootjs:lime_and_lime_block" },
            { reference: "lootjs:lychee_and_lychee_block" },
            { reference: "lootjs:mango_and_mango_block" },
            { reference: "lootjs:mangosteen_and_mangosteen_block" },
            { reference: "lootjs:orange_and_orange_block" },
            { reference: "lootjs:papaya_and_papaya_block" },
            { reference: "lootjs:peach_and_peach_block" },
            { reference: "lootjs:pear_and_pear_block" },
            { reference: "lootjs:persimmon_and_persimmon_block" }
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