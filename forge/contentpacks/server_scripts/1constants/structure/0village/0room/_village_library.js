const village_library_books_equipment = [
    {   
        groupName: "Book",
        groupWeight: 70, 
        min: 2, max: 3,
        items: [
            { id: 'minecraft:book', ratio: 20 },
            { id: 'minecraft:paper', ratio: 10 },
            { id: 'farmersdelight:canvas', ratio: 5 },
            { id: 'minecraft:writable_book', ratio: 15 ,max: 3},
            { id: 'minecraft:lantern', ratio: 5 },
        ]
    },
]

const village_library_enchanted_books = [
    {   
        groupName: "Enchanted Book",
        groupWeight: 70, 
        min: 1, max: 1,
        items: [
            // {
            //     id: 'minecraft:book',
            //     ratio: 5,
            //     enchantChance: 1.0,
            //     enchantLevels: [5, 10],
            //     treasure: true // 允许出冰霜行者等宝藏附魔
            // },
            // {
            //     id: 'minecraft:book',
            //     ratio: 5,
            //     enchantChance: 1.0,
            //     enchantLevels: [10, 15],
            //     treasure: true // 允许出冰霜行者等宝藏附魔
            // },
            // {
            //     id: 'minecraft:book',
            //     ratio: 5,
            //     enchantChance: 1.0,
            //     enchantLevels: [15, 20],
            //     treasure: true // 允许出冰霜行者等宝藏附魔
            // },
            // {
            //     id: 'minecraft:book',
            //     ratio: 5,
            //     enchantChance: 1.0,
            //     enchantLevels: [20, 25],
            //     treasure: true // 允许出冰霜行者等宝藏附魔
            // },
            // {
            //     id: 'minecraft:enchanted_book',
            //     ratio: 5,
            //     enchantChance: 1.0,
            //     enchantLevels: [25, 30],
            //     treasure: true // 允许出冰霜行者等宝藏附魔
            // },
        ]
    },
]

const village_library_drinks = [
    {   
        groupName: "Coffee",
        groupWeight: 50, 
        min: 1, max: 1,
        items: [
            // { id: 'youkaisfeasts:americano', ratio: 10 },
            // { id: 'youkaisfeasts:ristretto', ratio: 10 },
            // { id: 'youkaisfeasts:latte', ratio: 10 },
            // { id: 'youkaisfeasts:con_panna', ratio: 10 },
            // { id: 'youkaisfeasts:cappuccino', ratio: 10 },
            // { id: 'youkaisfeasts:macchiato', ratio: 10 },
            // { id: 'youkaisfeasts:mocha', ratio: 10 },
            { id: 'rusticdelight:coffee', ratio: 10 },
            { id: 'rusticdelight:milk_coffee', ratio: 10 },
            { id: 'rusticdelight:chocolate_coffee', ratio: 10 },
            { id: 'rusticdelight:honey_coffee', ratio: 10 },
            { id: 'rusticdelight:syrup_coffee', ratio: 10 },
            { id: 'rusticdelight:dark_coffee', ratio: 10 },
        ]
    },

    {   
        groupName: "Tea",
        groupWeight: 50, 
        min: 1, max: 1,
        items: [
            { id: 'farm_and_charm:strawberry_tea', ratio: 10 },
            { id: 'youkaisfeasts:green_tea', ratio: 10 },
            { id: 'youkaisfeasts:white_tea', ratio: 10 },
            { id: 'youkaisfeasts:black_tea', ratio: 10 },
            { id: 'youkaisfeasts:oolong_tea', ratio: 10 },
            { id: 'youkaisfeasts:dark_tea', ratio: 10 },
            { id: 'youkaisfeasts:yellow_tea', ratio: 10 },
            { id: 'youkaisfeasts:genmai_tea', ratio: 10 },
            { id: 'youkaisfeasts:saidi_tea', ratio: 10 },
        ]
    },
]

// =================================================================
// 事件注册区域
// =================================================================
const village_library_content = [
    [village_library_books_equipment, 4, 6],
    [village_library_drinks, 1, 2],
    [paper, 4, 5],
];

const loot_village_library = [
    [village_library_content, 1],
];