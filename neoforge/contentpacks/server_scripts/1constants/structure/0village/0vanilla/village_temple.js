const vanilla_village_temple_material = [
    {   
        groupName: "colourful",
        groupWeight: 70, 
        min: 2, max: 4,
        items: [
            { id: 'minecraft:bone_meal', ratio: 3 },
            { id: 'minecraft:iron_ingot', ratio: 3 },
            { id: 'minecraft:quartz', ratio: 3 },
            { id: 'minecraft:nautilus_shell', ratio: 1 , max: 1 },

            { id: 'minecraft:flint', ratio: 3 },
            { id: 'minecraft:obsidian', ratio: 3 },
            { id: 'minecraft:ink_sac', ratio: 3 },

            { id: 'minecraft:brick', ratio: 2 },
            { id: 'minecraft:nether_brick', ratio: 2 },
            { id: 'minecraft:copper_ingot', ratio: 2 },
            { id: 'minecraft:honeycomb', ratio: 2 },
            { id: 'minecraft:gold_ingot', ratio: 2 },
            { id: 'minecraft:golden_apple', ratio: 1 , max: 1 },
            

            { id: 'minecraft:emerald', ratio: 5 },
            { id: 'minecraft:ender_pearl', ratio: 5 },

            { id: 'minecraft:lapis_lazuli', ratio: 7 },
            { id: 'minecraft:heart_of_the_sea', ratio: 1 , max: 1 },
            { id: 'minecraft:diamond', ratio: 1 , max: 1 },

            { id: 'minecraft:glow_ink_sac', ratio: 6 },
            { id: 'minecraft:prismarine_shard', ratio: 2 },
            { id: 'minecraft:prismarine_crystals', ratio: 2 },

            { id: 'minecraft:amethyst_shard', ratio: 10 },
        ]
    },

    {   
        groupName: "useful",
        groupWeight: 10, 
        min: 3, max: 8,
        items: [
            { id: 'minecraft:redstone', ratio: 10 },
            { id: 'minecraft:sugar', ratio: 10 },
            { id: 'minecraft:glowstone_dust', ratio: 10 },
            { id: 'minecraft:magma_cream', ratio: 10 },
            { id: 'minecraft:nether_wart', ratio: 10 },
            { id: 'minecraft:blaze_rod', ratio: 10 },
            { id: 'minecraft:glistering_melon_slice', ratio: 10 },
            { id: 'minecraft:ghast_tear', ratio: 1 , max: 1 },
            { id: 'minecraft:phantom_membrane', ratio: 10 },
            { id: 'minecraft:golden_carrot', ratio: 10 },
            { id: 'minecraft:turtle_scute', ratio: 10 },
            { id: 'minecraft:gunpowder', ratio: 10 },
            { id: 'minecraft:pufferfish', ratio: 10 },
        ]
    },
]

const vanilla_village_temple_equipment = [
    {   
        groupName: "equipment",
        groupWeight: 70, 
        min: 3, max: 8,
        items: [
            { id: 'minecraft:potion', ratio: 20 , potion: "minecraft:water" , max: 3}, 
            { id: 'minecraft:book', ratio: 20 },
            { id: 'minecraft:paper', ratio: 10 },
            { id: 'farmersdelight:canvas', ratio: 5 },
            { id: 'minecraft:writable_book', ratio: 15 },
            { id: 'minecraft:lantern', ratio: 5 },            
            { id: 'supplementaries:candle_holder', ratio: 5 },            
            { id: 'beautify:lamp_candelabra', ratio: 5 },
        ]
    },
]   


// =================================================================
// 事件注册区域
// =================================================================
const village_temple_content = [
    [vanilla_village_temple_equipment, 2, 4],
    [vanilla_village_temple_material, 4, 8],
];

const loot_village_temple = [
    [village_temple_content, 1],
];