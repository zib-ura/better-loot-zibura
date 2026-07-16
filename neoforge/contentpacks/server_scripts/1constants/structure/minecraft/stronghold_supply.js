const stronghold_supply_food = [
    {
        groupName: "common_food",
        groupWeight: 85,
        min: 2, max: 3,
        items: [
            { reference: "lootjs:pumpkin_slice_and_pumpkin_slice_block" },
            { reference: "lootjs:melon_slice_and_melon_slice_block" },
            { reference: "lootjs:corn_and_corn_block" },
            { reference: "lootjs:apple_and_apple_block" },
            { reference: "lootjs:beetroot_and_beetroot_block" },
            { reference: "lootjs:brown_mushroom_and_brown_mushroom_block" },
            { reference: "lootjs:red_mushroom_and_red_mushroom_block" },
            { reference: "lootjs:carrot_and_carrot_block" },
            { reference: "lootjs:potato_and_potato_block" },
            { reference: "lootjs:cabbage_and_cabbage_block" },
            { reference: "lootjs:lettuce_and_lettuce_block" },
            { reference: "lootjs:onion_and_onion_block" },
            { reference: "lootjs:tomato_and_tomato_block" },
            { reference: "lootjs:rice_and_rice_block" },
            { reference: "lootjs:wheat_and_wheat_block" }, 
            { reference: "lootjs:cookie_and_cookie_crate" },
            { reference: "lootjs:sugar_and_sugar_crate" },
            { reference: "lootjs:bread_and_bread_crate" },
            { reference: "lootjs:egg_and_egg_crate" },
        ]
    },
    {
        groupName: "rare_food",
        groupWeight: 15, 
        min: 1, max: 2,
        items: [
            { id: "minecraft:enchanted_golden_apple" },
        ]
    }
]

const stronghold_supply_resources = [
    {
        groupName: "basic_building_blocks",
        groupWeight: 50,
        min: 2, max: 3,
        items: [
            { id: "minecraft:oak_log" },
            { id: "minecraft:stone_bricks" },
            { id: "minecraft:cobblestone" }
        ]
    },
    {
        groupName: "mineral_blocks",
        groupWeight: 30,
        min: 2, max: 3,
        items: [
            { id: "minecraft:coal_block" },
            { id: "minecraft:copper_block" },
            { id: "minecraft:iron_block" },
        ]
    },
    {
        groupName: "rare_mineral_blocks",
        groupWeight: 10,
        min: 1, max: 2,
        items: [
            { id: "minecraft:gold_block" },
            { id: "minecraft:diamond_block" },
            { id: "minecraft:emerald_block" }
        ]
    },
    // {
    //     groupName: "utility_blocks",
    //     groupWeight: 15,
    //     min: 1, max: 3,
    //     items: [
    //         { id: "minecraft:bookshelf" },
    //         { id: "minecraft:obsidian" },
    //         { id: "minecraft:anvil" }
    //     ]
    // }
]

const stronghold_supply_lighting = [
    {
        groupWeight: 50, 
        min: 2, max: 3,
        items: [
            { id: 'minecraft:torch', ratio: 10 },
        ]
    },
]

// =================================================================
// 事件注册区域
// =================================================================

const stronghold_supply_content = [
    [stronghold_supply_food, 1, 1],              
    [stronghold_supply_resources, 3, 4],    
    [stronghold_supply_lighting, 2, 3],
];

const loot_stronghold_supply = [
    [stronghold_supply_content, 1],
];