const stronghold_library_books_equipment = [
    {   
        groupName: "Book",
        groupWeight: 70, 
        min: 2, max: 3,
        items: [
            { id: 'minecraft:book', ratio: 20 },

    ]},

]

// =================================================================
// 事件注册区域
// =================================================================
const stronghold_library_content = [
    [stronghold_library_books_equipment, 3, 4],
    [ash, 4, 5],
];

const loot_stronghold_library = [
    [stronghold_library_content, 1],
];