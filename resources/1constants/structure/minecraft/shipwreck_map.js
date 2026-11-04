const shipwreck_treasure_map = [
    {
        groupName: "treasure_map",
        groupWeight: 100,
        min: 1, max: 1,
        items: treasure_map_item
    },
]

// =================================================================
// 事件注册区域
// =================================================================
const shipwreck_map_content = [
    [village_cartographer_maps_and_guides, 2, 3],
    [village_cartographer_cartography_tools, 1, 1], 
    [village_cartographer_records_and_ink, 1, 1], 
    [village_cartographer_office_decorations, 1, 1], 
    // [paper, 4, 5],
    [seagrass, 4, 5],
    [shipwreck_treasure_map, 1, 1],
];

const loot_shipwreck_map = [
    [shipwreck_map_content, 1],
];