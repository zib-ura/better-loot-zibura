const ruined_portal_nether_materials = [
    {
        groupName: "Nether Resources",
        groupWeight: 99,
        min: 1, max: 1,
        items: [

            { id: 'minecraft:obsidian', ratio: 20 },
            { id: 'minecraft:crying_obsidian', ratio: 10 },
            { id: 'minecraft:flint_and_steel', ratio: 10 },
            { id: 'minecraft:fire_charge', ratio: 5 },
            { id: 'minecraft:glowstone_dust', ratio: 10 },
            { id: 'minecraft:glowstone', ratio: 5 },
            { id: 'minecraft:quartz', ratio: 10 },
            { id: 'minecraft:nether_brick', ratio: 5 },
            { id: 'minecraft:magma_cream', ratio: 5 },
            { id: 'minecraft:nether_wart', ratio: 5 },
            { id: 'minecraft:soul_sand', ratio: 5 },
            { id: 'minecraft:gilded_blackstone', ratio: 5 },
            // { id: 'minecraft:chain', ratio: 5 },
            // { id: 'minecraft:iron_bars', ratio: 5 },
            // { id: 'minecraft:gold_block', ratio: 5 },
            // { id: 'minecraft:gold_ingot', ratio: 10 },
            // { id: 'minecraft:gold_nugget', ratio: 10 },
            // { id: 'minecraft:experience_bottle', ratio: 5 },
            { id: 'minecraft:blaze_powder', ratio: 5 },
            { id: 'minecraft:blaze_rod', ratio: 5 }

        ]
    },

    {
        groupName: "Rare Valuables",
        groupWeight: 1,
        min: 1, max: 1,
        items: [
            // { id: 'minecraft:diamond', ratio: 8 },
            { id: 'minecraft:ancient_debris', ratio: 1 },
            { id: 'minecraft:netherite_scrap', ratio: 1 },
            { id: 'minecraft:nether_star', ratio: 1 },
            { id: 'minecraft:ghast_tear', ratio: 1 },

        ]
    },
];

const ruined_portal_gold_armor = [
    {
        groupName: "Gold Armor Pieces",
        groupWeight: 85,
        min: 1, max: 1,
        items: [
            { id: 'minecraft:golden_helmet', ratio: 25 },
            { id: 'minecraft:golden_chestplate', ratio: 25 },
            { id: 'minecraft:golden_leggings', ratio: 25 },
            { id: 'minecraft:golden_boots', ratio: 25 }
        ]
    },

    // {
    //     groupName: "Special Gold Armor",
    //     groupWeight: 15,
    //     min: 1, max: 1,
    //     items: [
    //         { id: 'minecraft:golden_horse_armor', ratio: 10 }
    //     ]
    // }
];

const ruined_portal_supply = [
    {
        groupName: "fire_charge",
        groupWeight: 50,
        min: 1, max: 1,
        items: [
            { id: 'minecraft:potion', ratio: 10 , potion: "minecraft:fire_resistance" }, 
            { id: 'touhou_little_maid:fire_protect_bauble', ratio: 10 }, 
        ]
    },
    {
        groupName: "empty",
        groupWeight: 50,
        items: [
            { id: 'empty', ratio: 10 }, 
        ]
    },
];
// =================================================================
// 事件注册区域
// =================================================================
const ruined_portal_content = [
    [ruined_portal_nether_materials, 6, 7],
    [netherrack, 14, 15],
    [ruined_portal_gold_armor, 1, 1],
    [ruined_portal_supply, 1, 1],
];

const loot_ruined_portal = [
    [ruined_portal_content, 1],
];