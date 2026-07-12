const paper = [
    {        
        groupName: "paper",
        groupWeight: 20,
        min: 1, max: 1,
        items: [
            { id: 'minecraft:paper', ratio: 10 },
        ]
    },
]

const iron_nugget = [
    {        
        groupName: "iron_nugget",
        groupWeight: 20,
        min: 1, max: 1,
        items: [
            { id: 'minecraft:iron_nugget', ratio: 10 },
        ]
    },
]

const mushroom = [
    {        
        groupName: "mushroom",
        groupWeight: 20,
        min: 1, max: 1,
        items: [
            { id: 'minecraft:red_mushroom', ratio: 10 },
            { id: 'minecraft:brown_mushroom', ratio: 10 },
        ]
    },
]

const Vine = [
    {
        groupName: "Vine",
        groupWeight: 50,
        min: 1, max: 1,
        items: [
            { id: 'minecraft:vine', ratio: 20 },
        ]
    }
];

const Sand = [
    {
        groupName: "Sand",
        groupWeight: 50,
        min: 1, max: 1,
        items: [
            { id: 'minecraft:sand', ratio: 20 },
        ]
    }
];

const seagrass = [
    {
        groupName: "seagrass",
        groupWeight: 50,
        min: 1, max: 1,
        items: [
            { id: 'minecraft:seagrass', ratio: 20 },
        ]
    }
];

const ash = [
    {
        groupName: "Ash",
        groupWeight: 50,
        min: 1, max: 1,
        items: [
            { id: 'supplementaries:ash', ratio: 20 },
        ]
    }
];

const cobweb = [
    {
        groupName: "cobweb",
        groupWeight: 15,
        min: 1, max: 1,
        items: [
                { id: 'minecraft:cobweb', ratio: 10 }
        ]
    },
]

const netherrack = [
    {
        groupName: "netherrack",
        groupWeight: 15,
        min: 1, max: 1,
        items: [
            { id: 'minecraft:netherrack', ratio: 10 }
        ]
    },
]

// const treasure_map = [
//     {
//         groupName: "treasure_map",
//         groupWeight: 15,
//         min: 1, max: 1,
//         items: [
//             { 
//                 id: 'minecraft:map', ratio: 10, jsonFunction:         
//                 {
//                     "function": "minecraft:sequence",
//                     "functions": [
//                         {
//                             "destination": "minecraft:buried_treasure", // ⭐ 1.21+ 必须明确指定寻找的结构目标
//                             "decoration": "minecraft:red_x",
//                             "function": "minecraft:exploration_map",
//                             "skip_existing_chunks": false,
//                             "zoom": 1
//                         },
//                         {
//                             "function": "minecraft:set_name",
//                             "name": {
//                                 "translate": "filled_map.buried_treasure"
//                             },
//                             "target": "item_name"
//                         }
//                     ]
//                 }
//             },
//         ]
//     },
// ]