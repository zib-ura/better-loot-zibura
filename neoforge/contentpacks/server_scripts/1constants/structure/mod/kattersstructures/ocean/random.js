const kattersstructures_ocean_random_fish = [
    {
        groupName: "raw fish",
        groupWeight: 20,
        min: 2, max: 3,
        items: [

            { reference: 'lootjs:cod_and_cod_block', ratio: 25 },
            { reference: 'lootjs:salmon_and_salmon_block', ratio: 25 },
            { reference: 'lootjs:tropical_fish_and_tropical_fish_block', ratio: 25 },
            { reference: 'lootjs:pufferfish_and_pufferfish_block', ratio: 25 },

            { id: 'youkaisfeasts:raw_lamprey', ratio: 25 },
            { id: 'youkaisfeasts:raw_lamprey_fillet', ratio: 25 },
            { id: 'youkaisfeasts:roe', ratio: 5 },
            { id: 'kaleidoscope_chinesefood:yellow_croaker', ratio: 25 },

            { id: 'minecraft:ink_sac', ratio: 25 },
            { reference: 'lootjs:calamari', ratio: 25 },

            { id: 'minecraft:kelp', ratio: 25 },
      

        ]
    }
];

const kattersstructures_ocean_random_products = [
    {
        groupName: "products",
        groupWeight: 20,
        min: 2, max: 3,
        items: [
            
            { id: 'vintagedelight:salted_cod', ratio: 10 },
            { id: 'vintagedelight:salted_salmon', ratio: 10 },
            { id: 'vintagedelight:surstromming', ratio: 10 },

        ]
    }
];

const kattersstructures_ocean_random_fishing_rod = [
    {        
        groupName: "fishing_rod",
        groupWeight: 20,
        min: 1, max: 1, enchantChance: 1, enchantLevels: [40, 50], damage: [0.8, 0.9],
        items: [
            { id: 'minecraft:fishing_rod', ratio: 20 },
        ]
    },
]

const kattersstructures_ocean_random_ship_bucket = [
    {        
        groupName: "ship",
        groupWeight: 20,
        min: 1, max: 1, 
        items: [

            // --- 普通船系列 ---
            { id: 'minecraft:oak_boat', ratio: 10 },
            { id: 'minecraft:jungle_boat', ratio: 10 },
            { id: 'minecraft:bamboo_raft', ratio: 10 }, 

            // --- 运输船系列 (带箱子的船) ---
            { id: 'minecraft:oak_chest_boat', ratio: 10 },
            { id: 'minecraft:jungle_chest_boat', ratio: 10 },
            { id: 'minecraft:bamboo_chest_raft', ratio: 10 }, 
        ]
    },

    {        
        groupName: "bucket",
        groupWeight: 20,
        min: 1, max: 1, 
        items: [
            { id: 'minecraft:bucket', ratio: 10 },
        ]
    },
]

const kattersstructures_ocean_random_material = [
    {        
        groupName: "finshing_rod",
        groupWeight: 20,
        min: 3, max: 4, 
        items: [
            { id: 'minecraft:string', ratio: 10 },
            { id: 'minecraft:stick', ratio: 10 },

        ]
    },
]

const kattersstructures_ocean_random_fish_content = [
    [kattersstructures_ocean_random_fish, 3, 4],
    [kattersstructures_ocean_random_products, 3, 4],
];

const kattersstructures_ocean_random_equipment_content = [
    [kattersstructures_ocean_random_fishing_rod, 3, 4],
    [kattersstructures_ocean_random_ship_bucket, 7, 8],
    [kattersstructures_ocean_random_material, 3, 4],



];

const loot_kattersstructures_ocean_random = [
    [kattersstructures_ocean_random_fish_content, 9],
    [kattersstructures_ocean_random_equipment_content, 1],

];