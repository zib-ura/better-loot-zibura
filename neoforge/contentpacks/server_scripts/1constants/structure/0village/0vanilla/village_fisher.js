const village_fisher_fish = [
    {
        groupName: "raw fish",
        groupWeight: 20,
        min: 2, max: 3,
        items: [
            { id: 'minecraft:cod', ratio: 10 },
            { id: 'farmersdelight:cod_slice', ratio: 10 },
            { id: 'minecraft:salmon', ratio: 10 },
            { id: 'farmersdelight:salmon_slice', ratio: 10 },
            { id: 'minecraft:pufferfish', ratio: 10 },
            { id: 'cratedelight:cod_crate', ratio: 1, max: 2},
            { id: 'cratedelight:salmon_crate', ratio: 1, max: 2},
            { id: 'youkaisfeasts:raw_lamprey', ratio: 10 },
            { id: 'youkaisfeasts:raw_lamprey_fillet', ratio: 10 },
            { id: 'youkaisfeasts:roe', ratio: 2 },
            { id: 'kaleidoscope_chinesefood:yellow_croaker', ratio: 5 },
            { id: 'minecraft:ink_sac', ratio: 10 },
            { id: 'kaleidoscope_cookery:sashimi', ratio: 10 },
            { reference: 'lootjs:calamari', ratio: 10 },


        ]
    }
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
    }
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
    [village_fisher_equipement, 2, 4],
    [village_fisher_products, 3, 4],

];

const loot_village_fisher = [
    [village_fisher_content, 1],
];