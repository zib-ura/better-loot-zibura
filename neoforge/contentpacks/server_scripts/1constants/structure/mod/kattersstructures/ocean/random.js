const kattersstructures_ocean_random_wood = [
    {        
        groupName: "wood",
        groupWeight: 20,
        min: 2, max: 4,
        items: [
            // --- spruce Series  ---
            { id: 'minecraft:spruce_planks', ratio: 15 },
            { id: 'minecraft:spruce_slab', ratio: 15 },
            { id: 'minecraft:spruce_fence', ratio: 10 },
            { id: 'minecraft:spruce_fence_gate', ratio: 10 },
            { id: 'minecraft:spruce_boat', ratio: 5 },

            // --- Oak Series  ---
            { id: 'minecraft:oak_planks', ratio: 15 },
            { id: 'minecraft:oak_slab', ratio: 15 },
            { id: 'minecraft:oak_fence', ratio: 10 },
            { id: 'minecraft:oak_fence_gate', ratio: 10 },
            { id: 'minecraft:oak_boat', ratio: 5 },

            
            // --- jungle Series  ---
            { id: 'minecraft:jungle_planks', ratio: 15 },
            { id: 'minecraft:jungle_slab', ratio: 15 },
            { id: 'minecraft:jungle_fence', ratio: 10 },
            { id: 'minecraft:jungle_fence_gate', ratio: 10 },
            { id: 'minecraft:jungle_boat', ratio: 5 },


            // --- bamboo Series  ---
            { id: 'minecraft:bamboo_planks', ratio: 15 },
            { id: 'minecraft:bamboo_slab', ratio: 15 },
            { id: 'minecraft:bamboo_fence', ratio: 10 },
            { id: 'minecraft:bamboo_fence_gate', ratio: 10 },
            { id: 'minecraft:bamboo_raft', ratio: 5 },

            // --- other ---
            { id: 'minecraft:barrel', ratio: 15 },
            { id: 'minecraft:lantern', ratio: 15 },


        ]
    },
]

// =================================================================
// 事件注册区域
// =================================================================

const kattersstructures_ocean_random_content = [
    [village_fisher_products, 3, 4],
    [village_fisher_equipement, 2, 4],
    // [kattersstructures_ocean_random_wood, 2, 4],
    
];

const loot_kattersstructures_ocean_random = [
    [kattersstructures_ocean_random_content, 1],
];