const village_fisher_fish = [
    {
        groupName: "raw fish",
        groupWeight: 20,
        min: 1, max: 1,
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

            { id: 'minecraft:pufferfish_bucket', ratio: 25 },
            { id: 'minecraft:cod_bucket', ratio: 25 },
            { id: 'minecraft:salmon_bucket', ratio: 25 },
            { id: 'minecraft:tropical_fish_bucket', ratio: 25 },
            { id: 'youkaishomecoming:lamprey_bucket', ratio: 25 },
            { id: 'minecraft:bucket', ratio: 25 },
            { id: 'minecraft:water_bucket', ratio: 25 },
        ]
    },
];

const village_fisher_products = [
    {
        groupName: "products",
        groupWeight: 20,
        min: 2, max: 3,
        items: [
            
            { id: 'vintagedelight:salted_cod', ratio: 10 },
            { id: 'vintagedelight:salted_salmon', ratio: 10 },
            { id: 'vintagedelight:surstromming', ratio: 10 },

        ]
    },
];

const village_fisher_equipement = [
    {        
        groupName: "equipment",
        groupWeight: 20,
        min: 1, max: 1, enchantChance: 0.0,  damage: [0.2, 0.8],
        items: [
            { id: 'minecraft:fishing_rod', ratio: 20 },
            { id: 'farmersdelight:iron_knife', ratio: 10 },
            { id: 'kaleidoscope_cookery:iron_kitchen_knife', ratio: 10 },
        ]
    },
]

// =================================================================
// 事件注册区域
// =================================================================
const village_fisher_content = [
    [village_fisher_fish, 3, 4],
    [village_fisher_equipement, 1, 1],
    [village_fisher_products, 2, 3],

];

const loot_village_fisher = [
    [village_fisher_content, 1],
];