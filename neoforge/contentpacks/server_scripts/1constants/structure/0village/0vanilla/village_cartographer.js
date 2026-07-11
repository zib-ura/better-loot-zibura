const village_cartographer_products = [
    {
        groupName: "maps_and_guides",
        groupWeight: 20,
        min: 1, max: 2,
        items: [
            { id: 'minecraft:map', ratio: 10 },
            { id: 'supplementaries:slice_map', ratio: 10 },

        ]
    },
]

const village_cartographer_materials = [
    {
        groupName: "materials",
        groupWeight: 20,
        min: 1, max: 1,
        items: [
            { id: 'minecraft:glass_pane', ratio: 10 },
            { id: 'chalk:black_chalk', ratio: 2 },
            { id: 'chalk:white_chalk', ratio: 2 },
            { id: 'chalk:red_chalk', ratio: 2 },
            { id: 'chalk:chalk_box', ratio: 2 },
            { id: 'minecraft:book', ratio: 15 },
            { id: 'minecraft:writable_book', ratio: 5 },
            { id: 'supplementaries:antique_ink', ratio: 5 },
            { id: 'supplementaries:globe', ratio: 5 }, 
            { id: 'supplementaries:globe_sepia', ratio: 5 },
            { id: 'minecraft:compass', ratio: 10 },
            { id: 'minecraft:spyglass', ratio: 10 },
            { id: 'naturescompass:naturescompass', ratio: 5 },
            { id: 'explorerscompass:explorerscompass', ratio: 5 },
        ]
    },
]



// =================================================================
// 事件注册区域
// =================================================================
const village_cartographer_content = [
    [village_cartographer_products, 2, 3],
    [village_cartographer_materials, 4, 5],
    [village_chess, 1, 1],
    [paper, 10, 18],
];

const loot_village_cartographer = [
    [village_cartographer_content, 1],
];