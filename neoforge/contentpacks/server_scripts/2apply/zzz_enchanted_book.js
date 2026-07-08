// 1. 定义基础魔咒数组（以 1.21.1 为基准）
let enchantmentsWithLevels = [
    // ==================== [原版 Minecraft] ====================
    "minecraft:smite", 
    "minecraft:soul_speed", 
    "minecraft:sweeping_edge", // 1.20.5+ 的 ID
    "minecraft:swift_sneak", 
    "minecraft:thorns", 
    "minecraft:unbreaking", 
    "minecraft:wind_burst",        // 1.21+ 新增
    "minecraft:bane_of_arthropods", 
    "minecraft:blast_protection", 
    "minecraft:breach",            // 1.21+ 新增
    "minecraft:density",           // 1.21+ 新增
    "minecraft:depth_strider", 
    "minecraft:efficiency", 
    "minecraft:feather_falling", 
    "minecraft:fire_aspect", 
    "minecraft:fire_protection", 
    "minecraft:fortune", 
    "minecraft:frost_walker", 
    "minecraft:impaling", 
    "minecraft:knockback", 
    "minecraft:looting", 
    "minecraft:loyalty", 
    "minecraft:luck_of_the_sea", 
    "minecraft:lure", 
    "minecraft:piercing", 
    "minecraft:power", 
    "minecraft:projectile_protection", 
    "minecraft:protection", 
    "minecraft:punch", 
    "minecraft:quick_charge", 
    "minecraft:respiration", 
    "minecraft:riptide", 
    "minecraft:sharpness"
];

enchantmentsWithLevels = cleanEnchantments(enchantmentsWithLevels);



[
    // "enchantencore:weaving_aspect",

    // "farmersdelight:backstabbing",

    // "create:capacity",
    // "create:potato_recovery",

    // "kaleidoscope_end:void_assault", 
    // "kaleidoscope_end:void_echo",

    // "kattersstructures:heavy",
    // "kattersstructures:light",
    // // "kattersstructures:raj_ambient",
    // // "kattersstructures:raj_lightning",
    // // "kattersstructures:raj_minion",

    // // "nova_structures:boss_behaviour",
    // // "nova_structures:conductivity_curse",
    // "nova_structures:ghasted",
    // "nova_structures:gravity",
    // "nova_structures:illagers_bane",
    // "nova_structures:outreach",
    // "nova_structures:piercing",
    // "nova_structures:power",
    // // "nova_structures:shulker_boss",
    // // "nova_structures:shulker_miniboss",
    // "nova_structures:traveler",

    // "touhou_little_maid:impeding",
    // "touhou_little_maid:speedy",
];

let enchantmentsWithoutLevels = [
    // "kaleidoscope_end:void_walker",
    // "kaleidoscope_nether:lava_walker",
    // "kattersstructures:blunt",
    // "kattersstructures:enlightning",
    // "kattersstructures:ocean_heart",
    // "kattersstructures:web_walker",
    "minecraft:aqua_affinity",
    "minecraft:channeling",
    "minecraft:flame",
    "minecraft:infinity",
    "minecraft:mending",
    "minecraft:multishot",
    "minecraft:silk_touch",
    // "mynethersdelight:poaching",
    // "nova_structures:antidote",
    // "nova_structures:multishot",
    // "nova_structures:photosynthesis",
    // "nova_structures:wax_wings",
    // "nova_structures:wither_coated",
    // "supplementaries:stasis",
    // "touhou_little_maid:enders_ender",
];

let allAvailableEnchantments = enchantmentsWithLevels.concat(enchantmentsWithoutLevels);


generateEnchantmentLootTables(enchantmentsWithLevels, enchantmentsWithoutLevels) 



// // ==========================================
// // 0. 图书馆 (Library)
// // ==========================================
// let libraryTables = [
//     "minecraft:chests/buried_treasure",
//     "minecraft:chests/stronghold_library",
//     "minecraft:chests/end_city_treasure",
//     // "trek:village/village_library",
//     // "trek:village/village_library_secret",
//     // "nova_structures:chests/witch_villa/library",
//     // "nova_structures:chests/illager_hideout_library",

//     // "lootjs:desert_basement_treasure",
// ];

// ==========================================
// 核心重构配置区 (其他各类遗迹结构)
// ==========================================
let structureEnchantmentConfigs = [
    {
        // 0. 图书馆 (Library)
        rolls: [1, 3],
        clean: true, 
        tables: [
            "minecraft:chests/buried_treasure",
            "minecraft:chests/stronghold_library",
            "minecraft:chests/end_city_treasure",
            "ctov:chests/village/village_library",

            // "trek:village/village_library",
            // "trek:village/village_library_secret",
            // "nova_structures:chests/witch_villa/library",
            // "nova_structures:chests/illager_hideout_library",
            // "lootjs:desert_basement_treasure",
        ],
        // 核心：直接把存储了所有附魔的变量传给 enchants 属性
        enchants: allAvailableEnchantments 
    },

    {
        // 1. 废弃矿井 (Mineshaft)
        rolls: [1, 3],
        clean: false,
        tables: [
            "minecraft:chests/abandoned_mineshaft",
            // "mvs:mineshaft/rare",
            // "nova_structures:chests/badland_miner_outpost",
            // "nova_structures:chests/badland_miner_outpost_forge",
        ],
        enchants: [
            "minecraft:fortune",
            "minecraft:silk_touch",
            "minecraft:unbreaking",
            "minecraft:efficiency",
            // "nova_structures:outreach",
        ]
    },
    {
        // 2. 沙漠神殿 (Desert Pyramid)
        rolls: [1, 3],
        clean: true,
        tables: [
            "minecraft:chests/desert_pyramid",
        ],
        enchants: [
            "minecraft:bane_of_arthropods",
            "minecraft:knockback",
            "minecraft:looting",
            "minecraft:projectile_protection",
            "minecraft:protection",
            "minecraft:sharpness",
            "minecraft:smite",
            "minecraft:sweeping_edge",
            "minecraft:blast_protection",
            // "nova_structures:photosynthesis",
        ]
    },
    {
        // 3. 丛林神庙 (Jungle Temple)
        rolls: [1, 3],
        clean: true,
        tables: [
            "minecraft:chests/jungle_temple",
            // "nova_structures:chests/jungle_ruins/jungle_ruins_main_temple"
        ],
        enchants: [
            "minecraft:bane_of_arthropods",
            "minecraft:knockback",
            "minecraft:looting",
            "minecraft:projectile_protection",
            "minecraft:protection",
            "minecraft:sharpness",
            "minecraft:smite",
            "minecraft:sweeping_edge",
            "minecraft:thorns",
            // "nova_structures:antidote",
            // "nova_structures:photosynthesis"
        ]
    },
    {
        // 4. 掠夺者相关 (Pillager)
        rolls: [1, 2],
        clean: false,
        tables: [
            "minecraft:chests/pillager_outpost",
            // "trek:pillagers/pillager_mansion",
            // "trek:pillagers/outpost_barrel",
            // "trek:overworld/rare/abandoned_castle_pillager",
            // "takesapillage:chests/bastille/church",
            // "nova_structures:chests/pillager_outpost_treasure",
            // "nova_structures:chests/illager_hideout_tresure",
            // "minecraft:chests/woodland_mansion",
            // "mvs:pillager"
        ],
        enchants: [
            "minecraft:infinity",
            "minecraft:looting",
            "minecraft:multishot",
            "minecraft:piercing",
            "minecraft:power",
            "minecraft:punch",
            "minecraft:unbreaking",
            "minecraft:quick_charge",
            // "kattersstructures:blunt",
            // "kattersstructures:light",
            // "kattersstructures:heavy",
            // "nova_structures:multishot",
            // "nova_structures:piercing",
            // "nova_structures:power",
            // "supplementaries:stasis"
        ]
    },
    {
        // 5. 水下遗迹 (Underwater Ruin)
        rolls: [1, 1],
        clean: false,
        tables: [
            "minecraft:chests/underwater_ruin_small",
            "minecraft:chests/underwater_ruin_big", 
            // "trek:overworld/rare/island_village_1/top_barrels",
            // "trek:overworld/rare/mushroom_island/top",
            // "trek:overworld/rare/villager_island/good_loot"
        ],
        enchants: [
            "minecraft:aqua_affinity",
            "minecraft:depth_strider",
            "minecraft:luck_of_the_sea",
            "minecraft:lure",
            "minecraft:respiration",
            "minecraft:channeling",
            "minecraft:impaling",
            "minecraft:loyalty",
            "minecraft:riptide"
        ]
    }
];


addEnchantedBook()