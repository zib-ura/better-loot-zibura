const shipwreck_treasure_crops = [
    {
        groupName: "New World Crops",
        groupWeight: 50,
        min: 2,
        max: 3,
        items: [
            // ------- 火把花、瓶子草、灯笼果 (来自 Trail and Tales & 模组) -------
            { id: 'minecraft:torchflower', ratio: 10 },
            { id: 'minecraft:torchflower_seeds', ratio: 10 },
            { id: 'minecraft:pitcher_plant', ratio: 10 },
            { id: 'minecraft:pitcher_pod', ratio: 10 },
            { id: 'trailandtales_delight:lantern_fruit', ratio: 10 },
            { id: 'trailandtales_delight:lantern_fruit_seeds', ratio: 10 },
            { id: 'minecraft:sniffer_egg', ratio: 10 },


            // // ------- 来自遥远大陆的作物 (原版 & 模组) -------
            // { id: 'minecraft:cocoa_beans', ratio: 10 },
            // { id: 'minecraft:sugar_cane', ratio: 8 },
            // { id: 'minecraft:pumpkin_seeds', ratio: 8 },
            // { id: 'minecraft:melon_seeds', ratio: 8 },
            // { id: 'farmersdelight:tomato_seeds', ratio: 10 },
            // { id: 'farmersdelight:onion', ratio: 6 },
            // { id: 'farmersdelight:cabbage_seeds', ratio: 8 },
            // { id: 'farmersdelight:rice', ratio: 8 },
            // { id: 'rusticdelight:coffee_beans', ratio: 8 },
            // { id: 'rusticdelight:bell_pepper_seeds', ratio: 6 },
            // { id: 'rusticdelight:cotton_seeds', ratio: 6 },
            // { id: 'farm_and_charm:strawberry_seeds', ratio: 6 },
            // { id: 'farm_and_charm:barley_seeds', ratio: 6 },
            // { id: 'casualnessdelight:capsicum_seeds', ratio: 6 },   // 辣椒种子
            // { id: 'brewery:hops', ratio: 6 },                      // 啤酒花
        ]
    }
];

const shipwreck_treasure_valuables = [
    {
        groupName: "Valuable Cargo",
        groupWeight: 25,
        min: 2,
        max: 3,
        items: [
            { id: 'minecraft:diamond', ratio: 5 },
            { id: 'minecraft:emerald', ratio: 20 },
            { id: 'minecraft:gold_ingot', ratio: 10 },

            { id: 'minecraft:golden_apple', ratio: 5 },
            { id: 'trailandtales_delight:golden_lantern_fruit', ratio: 5 },
            { id: 'minecraft:enchanted_golden_apple', ratio: 5 },
            { id: 'mynethersdelight:enchanted_golden_egg', ratio: 5 },
            { id: 'kaleidoscope_cookery:golden_salad', ratio: 5 },
            { id: 'minecraft:totem_of_undying', ratio: 5 },
            { id: 'kitchenkarrot:shiny_pizza', ratio: 5 },

            { id: 'minecraft:sea_lantern', ratio: 10 },



            // { id: 'minecraft:gold_nugget', ratio: 10 },
            // { id: 'minecraft:iron_ingot', ratio: 8 },
            // { id: 'minecraft:copper_ingot', ratio: 8 },
            // { id: 'minecraft:amethyst_shard', ratio: 6 },
            { id: 'minecraft:nautilus_shell', ratio: 5 },          // 鹦鹉螺壳 (航海象征)
            { id: 'minecraft:heart_of_the_sea', ratio: 5 },        // 海洋之心 (极其稀有)
            // { id: 'supplementaries:antique_ink', ratio: 4 },       // 古式墨水 (古籍贸易)
            // { id: 'minecraft:lapis_lazuli', ratio: 6 },
            // { id: 'minecraft:glow_ink_sac', ratio: 5 },

            { id: 'touhou_little_maid:drown_protect_bauble', ratio: 10 },
        ]
    },
];
// // 第三组：航海用品与地图 (Nautical Supplies & Maps)
// const SHIPWRECK_NAUTICAL = [
//     {
//         groupName: "Nautical Supplies",
//         groupWeight: 20,
//         min: 1,
//         max: 3,
//         items: [
//             { id: 'minecraft:map', ratio: 10 },
//             { id: 'minecraft:compass', ratio: 10 },
//             { id: 'minecraft:clock', ratio: 6 },
//             { id: 'minecraft:spyglass', ratio: 6 },
//             { id: 'minecraft:paper', ratio: 12 },
//             { id: 'minecraft:book', ratio: 8 },
//             { id: 'minecraft:glass_bottle', ratio: 10 },
//             { id: 'farmersdelight:rope', ratio: 10 },
//             { id: 'supplementaries:rope', ratio: 8 },
//             { id: 'minecraft:chain', ratio: 5 },
//             { id: 'aeronautics:aviators_goggles', ratio: 4 },       // 飞行员护目镜 (导航感)
//             { id: 'explorerscompass:explorerscompass', ratio: 3 }, // 结构罗盘
//             { id: 'naturescompass:naturescompass', ratio: 3 },     // 自然罗盘
//         ]
//     }
// ];

// // 第四组：香料与奢侈品 (Spices & Luxuries)
// const SHIPWRECK_SPICES = [
//     {
//         groupName: "Spices & Luxuries",
//         groupWeight: 15,
//         min: 2,
//         max: 5,
//         items: [
//             { id: 'minecraft:sugar', ratio: 10 },
//             { id: 'minecraft:honey_bottle', ratio: 6 },
//             { id: 'minecraft:cocoa_beans', ratio: 8 },
//             { id: 'seeddelight:coffee_beans_bag', ratio: 6 },      // 烘焙咖啡豆
//             { id: 'brewery:dried_barley', ratio: 6 },
//             { id: 'brewery:dried_corn', ratio: 6 },
//             { id: 'brewery:dried_oat', ratio: 6 },
//             { id: 'barbequesdelight:chili_powder', ratio: 5 },
//             { id: 'barbequesdelight:pepper_powder', ratio: 5 },
//             { id: 'barbequesdelight:cumin_powder', ratio: 5 },
//             { id: 'kaleidoscope_cookery:green_chili', ratio: 4 },
//             { id: 'kaleidoscope_cookery:red_chili', ratio: 4 },
//             { id: 'youkaisfeasts:tea_leaves', ratio: 8 },
//             { id: 'youkaisfeasts:green_tea_leaves', ratio: 6 },
//             { id: 'youkaisfeasts:black_tea_leaves', ratio: 6 },
//             { id: 'vanillacookbook:chocolate', ratio: 4 },
//             { id: 'seeddelight:rosehip', ratio: 4 },
//             { id: 'supplementaries:flax_seeds', ratio: 5 },
//             { id: 'voidlessframework:salt', ratio: 5 },
//         ]
//     }
// ];
    


const shipwreck_treasure_music_discs = [
    // =================================================================
    // GROUP: 音乐唱片 (Music Discs)
    // =================================================================
    {
        groupName: "Music Discs",
        groupWeight: 25,
        min: 1, max: 1,
        items: [
            // { "id": "minecraft:music_disc_pigstep", "ratio": 1},
            { "id": "minecraft:music_disc_chirp", "ratio": 1},
            { "id": "minecraft:music_disc_cat", "ratio": 1},
            { "id": "minecraft:music_disc_mellohi", "ratio": 1},
            { "id": "minecraft:music_disc_far", "ratio": 1},
            { "id": "minecraft:music_disc_blocks", "ratio": 1},
            { "id": "minecraft:music_disc_relic", "ratio": 1},
            { "id": "minecraft:music_disc_strad", "ratio": 1},
            { "id": "minecraft:music_disc_otherside", "ratio": 1},
            { "id": "minecraft:music_disc_stal", "ratio": 1},
            { "id": "minecraft:music_disc_mall", "ratio": 1},
            { "id": "minecraft:music_disc_13", "ratio": 1},
            { "id": "minecraft:music_disc_11", "ratio": 1},
            { "id": "minecraft:music_disc_ward", "ratio": 1},
            { "id": "minecraft:music_disc_wait", "ratio": 1},
            { "id": "minecraft:music_disc_5", "ratio": 1},

            { "id": "supplementaries:music_disc_heave_ho", "ratio": 1},
            { "id": "takesapillage:music_disc_bastille_blues", "ratio": 1},
            { "id": "berry_good:music_disc_fox", "ratio": 1},
            { "id": "aeronautics:music_disc_cloud_skipper", "ratio": 1},
            { "id": "immersiveenchanting:music_disc_biblioclasm", "ratio": 1},
            { "id": "immersiveenchanting:music_disc_arcane_memories", "ratio": 1},
        ]
    },

    {
        groupName: "empty",
        groupWeight: 50,
        items: [
            { "id": "empty" },
        ]
    },
];

const shipwreck_treasure_smithing_templates_common = [
    {
        groupName: "Smithing Templates",
        groupWeight: 25,  
        min: 1,
        max: 1,
        items: [
            // { id: 'minecraft:netherite_upgrade_smithing_template', ratio: 10 },
            { id: 'minecraft:bolt_armor_trim_smithing_template', ratio: 10 },
            { id: 'minecraft:coast_armor_trim_smithing_template', ratio: 10 },
            { id: 'minecraft:dune_armor_trim_smithing_template', ratio: 10 },
            // { id: 'minecraft:eye_armor_trim_smithing_template', ratio: 10 },
            { id: 'minecraft:flow_armor_trim_smithing_template', ratio: 10 },
            { id: 'minecraft:host_armor_trim_smithing_template', ratio: 10 },
            { id: 'minecraft:raiser_armor_trim_smithing_template', ratio: 10 },
            // { id: 'minecraft:rib_armor_trim_smithing_template', ratio: 10 },
            { id: 'minecraft:sentry_armor_trim_smithing_template', ratio: 10 },
            { id: 'minecraft:shaper_armor_trim_smithing_template', ratio: 10 },
            { id: 'minecraft:silence_armor_trim_smithing_template', ratio: 10 },
            // { id: 'minecraft:snout_armor_trim_smithing_template', ratio: 10 },
            // { id: 'minecraft:spire_armor_trim_smithing_template', ratio: 10 },
            { id: 'minecraft:tide_armor_trim_smithing_template', ratio: 10 },
            { id: 'minecraft:vex_armor_trim_smithing_template', ratio: 10 },
            { id: 'minecraft:ward_armor_trim_smithing_template', ratio: 10 },
            { id: 'minecraft:wayfinder_armor_trim_smithing_template', ratio: 10 },
            { id: 'minecraft:wild_armor_trim_smithing_template', ratio: 10 },
            { id: 'supplementaries:blast_armor_trim_smithing_template', ratio: 10 },
            // { id: 'kaleidoscope_end:ender_dragon_smithing_template', ratio: 10 }
        ]
    },

    {
        groupName: "empty",
        groupWeight: 50,
        items: [
            { "id": "empty" },
        ]
    },
];
const shipwreck_treasure_smithing_templates_exclusive = [
    {
        groupName: "Smithing Templates",
        groupWeight: 25,  
        min: 1,
        max: 1,
        items: [
            { id: 'minecraft:coast_armor_trim_smithing_template', ratio: 10 },
            { id: 'minecraft:tide_armor_trim_smithing_template', ratio: 10 },
        ]
    },

    {
        groupName: "empty",
        groupWeight: 50,
        items: [
            { "id": "empty" },
        ]
    },
];
const shipwreck_treasure_pottery_sherd = [
    {
        groupName: "pottery_sherd",
        groupWeight: 25,
        min: 1, max: 2,
        items: [
            { id: 'minecraft:angler_pottery_sherd', ratio: 4 },
            { id: 'minecraft:archer_pottery_sherd', ratio: 4 },
            { id: 'minecraft:arms_up_pottery_sherd', ratio: 4 },
            { id: 'minecraft:blade_pottery_sherd', ratio: 4 },
            { id: 'minecraft:brewer_pottery_sherd', ratio: 4 },
            { id: 'minecraft:burn_pottery_sherd', ratio: 4 },
            { id: 'minecraft:danger_pottery_sherd', ratio: 4 },
            { id: 'minecraft:explorer_pottery_sherd', ratio: 4 },
            { id: 'minecraft:flow_pottery_sherd', ratio: 4 },
            { id: 'minecraft:friend_pottery_sherd', ratio: 4 },
            { id: 'minecraft:guster_pottery_sherd', ratio: 4 },
            { id: 'minecraft:heart_pottery_sherd', ratio: 4 },
            { id: 'minecraft:heartbreak_pottery_sherd', ratio: 4 },
            { id: 'minecraft:howl_pottery_sherd', ratio: 4 },
            { id: 'minecraft:miner_pottery_sherd', ratio: 4 },
            { id: 'minecraft:mourner_pottery_sherd', ratio: 4 },
            { id: 'minecraft:plenty_pottery_sherd', ratio: 4 },
            { id: 'minecraft:prize_pottery_sherd', ratio: 4 },
            { id: 'minecraft:scrape_pottery_sherd', ratio: 4 },
            { id: 'minecraft:sheaf_pottery_sherd', ratio: 4 },
            { id: 'minecraft:shelter_pottery_sherd', ratio: 4 },
            { id: 'minecraft:skull_pottery_sherd', ratio: 4 },
            { id: 'minecraft:snort_pottery_sherd', ratio: 4 }
        ]
    },

    {
        groupName: "empty",
        groupWeight: 50,
        items: [
            { "id": "empty" },
        ]
    },
];

const shipwreck_treasure_enchanted_book = [
    {
        groupName: "underwater_ruin",
        groupWeight: 50,
        min: 1, max: 1,
        items: [
            { id: "minecraft:book", ratio: 10, enchantRandomly: ["minecraft:aqua_affinity"] },
            { id: "minecraft:book", ratio: 10, enchantRandomly: ["minecraft:depth_strider"] },
            { id: "minecraft:book", ratio: 10, enchantRandomly: ["minecraft:luck_of_the_sea"] },
            { id: "minecraft:book", ratio: 10, enchantRandomly: ["minecraft:lure"] },
            { id: "minecraft:book", ratio: 10, enchantRandomly: ["minecraft:respiration"] },
            { id: "minecraft:book", ratio: 10, enchantRandomly: ["minecraft:channeling"] },
            { id: "minecraft:book", ratio: 10, enchantRandomly: ["minecraft:impaling"] },
            { id: "minecraft:book", ratio: 10, enchantRandomly: ["minecraft:loyalty"] },
            { id: "minecraft:book", ratio: 10, enchantRandomly: ["minecraft:riptide"] },
            { id: "minecraft:book", ratio: 10, enchantRandomly: ["kattersstructures:ocean_heart"] },

        ]
    },
    {
        groupName: "empty",
        groupWeight: 50,
        items: [
            { "id": "empty" },
        ]
    },
];

const shipwreck_treasure_conduit = [
    {
        groupName: "conduit",
        groupWeight: 50,  
        min: 1, max: 1,
        items: [
            { id: "minecraft:conduit", ratio: 10 },
        ]
    },

    {
        groupName: "empty",
        groupWeight: 50,
        items: [
            { "id": "empty" },
        ]
    },
];
// =================================================================
// 事件注册区域
// =================================================================
const shipwreck_treasure_content = [
    [shipwreck_treasure_crops, 2, 3],
    [shipwreck_treasure_valuables, 2, 4],
    [shipwreck_treasure_music_discs, 2, 2],
    [shipwreck_treasure_smithing_templates_common, 2, 2],
    [shipwreck_treasure_smithing_templates_exclusive, 1, 1],
    [shipwreck_treasure_pottery_sherd, 2, 2],
    [shipwreck_treasure_enchanted_book, 1, 1],
    [shipwreck_treasure_conduit, 1, 1],
    [seagrass, 4, 5],
];

const loot_shipwreck_treasure = [
    [shipwreck_treasure_content, 1],
];