const village_temple_material = [
    {   
        groupName: "ore",
        groupWeight: 50, 
        min: 2, max: 3,
        items: [

            { id: "minecraft:quartz", ratio: 10 },
            { id: "minecraft:obsidian", ratio: 10 },
            { id: "minecraft:copper_ingot", ratio: 10 },
            { id: "minecraft:gold_ingot", ratio: 10 },
            { id: "minecraft:emerald", ratio: 10 },
            { id: "minecraft:lapis_lazuli", ratio: 10 },
            { id: "minecraft:amethyst_shard", ratio: 10 },
        ]
    },

    {   
        groupName: "brewing",
        groupWeight: 50, 
        min: 2, max: 3,
        items: [
            { id: "minecraft:redstone", ratio: 10 },
            { id: "minecraft:sugar", ratio: 10 },
            { id: "minecraft:glowstone_dust", ratio: 10 },
            { id: "minecraft:magma_cream", ratio: 10 },
            { id: "minecraft:nether_wart", ratio: 20 },
            { id: "minecraft:blaze_rod", ratio: 10 },
            { id: "minecraft:glistering_melon_slice", ratio: 10 },
            { id: "minecraft:phantom_membrane", ratio: 10 },
            { id: "minecraft:golden_carrot", ratio: 10 },
            { id: "minecraft:turtle_scute", ratio: 10 },
            { id: "minecraft:gunpowder", ratio: 10 },
            { id: "minecraft:pufferfish", ratio: 10 },

            { id: "minecraft:ender_pearl", ratio: 10 },

        ]
    },
]

const village_temple_equipment = [
    {   
        groupName: "equipment",
        groupWeight: 50, 
        min: 2, max: 3,
        items: [
            { id: "minecraft:potion", ratio: 10 , potion: "minecraft:water" , max: 3}, 
            { id: "minecraft:book", ratio: 10 },
            { id: "minecraft:writable_book", ratio: 10 },
            { id: "minecraft:candle", ratio: 10 },            
            { id: "supplementaries:candle_holder", ratio: 10 },            
        ]
    },
]   

const village_temple_product = [
    {   
        groupName: "bauble",
        groupWeight: 50, 
        min: 1, max: 1,
        items: [

            { id: "touhou_little_maid:nimble_fabric", ratio: 10 },
            { id: "touhou_little_maid:magic_protect_bauble", ratio: 10 },
            { id: "touhou_little_maid:wireless_io", ratio: 10 },
            { id: "touhou_little_maid:shrine", ratio: 1 },

        ]
    },

    {   
        groupName: "potion",
        groupWeight: 50, 
        min: 1, max: 1,
        items: [
            { id: "minecraft:potion", ratio: 10, potion: "minecraft:healing" },
            { id: "minecraft:potion", ratio: 10, potion: "minecraft:strength" },
            { id: "minecraft:potion", ratio: 10, potion: "minecraft:swiftness" },
            { id: "minecraft:potion", ratio: 10, potion: "minecraft:night_vision" },
            { id: "minecraft:potion", ratio: 10, potion: "minecraft:invisibility" },
        ]
    },
]
// =================================================================
// 事件注册区域
// =================================================================
const village_temple_content = [
    [village_temple_equipment, 1, 1],
    [village_temple_material, 2, 3],
    [village_temple_product, 2, 2],
];

const loot_village_temple = [
    [village_temple_content, 1],
];