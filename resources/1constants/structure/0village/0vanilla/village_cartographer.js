const village_cartographer_maps_and_guides = [
    {
        groupName: "maps_and_guides",
        groupWeight: 100,
        min: 1, max: 1,
        items: [
            { id: "minecraft:map", ratio: 10 },
            { id: "supplementaries:slice_map", ratio: 10 },
        ]
    }
];

const village_cartographer_cartography_tools = [
    {
        groupName: "cartography_tools",
        groupWeight: 100,
        min: 1, max: 1,
        items: common_cartography_tools
    }
];

const village_cartographer_records_and_ink = [
    {
        groupName: "records_and_ink",
        groupWeight: 100,
        min: 1, max: 1,
        items: records_and_ink
    }
];

const village_cartographer_office_decorations = [
    {
        groupName: "office_decorations",
        groupWeight: 100,
        min: 1, max: 1,
        items: [
            { id: "supplementaries:globe", ratio: 10 },
            { id: "supplementaries:globe_sepia", ratio: 10 },
            common_indoor_decoration,
        ]
    }
];

const village_cartographer_marking_materials = [
    {
        groupName: "marking_materials",
        groupWeight: 100,
        min: 1, max: 1,
        items: [
            { id: "minecraft:white_banner", ratio: 10 },
            { id: "minecraft:blue_banner", ratio: 5 },
            { id: "minecraft:red_banner", ratio: 5 },
            { id: "minecraft:frame", ratio: 12 }, 
            { id: "minecraft:glow_item_frame", ratio: 4 }, 
        ]
    }
];

// =================================================================
// 事件注册区域
// =================================================================

const village_cartographer_content = [
    [village_cartographer_maps_and_guides, 1, 2],
    [village_cartographer_cartography_tools, 1, 2], 
    [village_cartographer_records_and_ink, 2, 3], 
    [village_cartographer_office_decorations, 1, 1], 
    [village_cartographer_marking_materials, 1, 2], 
    [village_chess, 1, 1],
    [paper, 4, 5],
];

const loot_village_cartographer = [
    [village_cartographer_content, 1],
];