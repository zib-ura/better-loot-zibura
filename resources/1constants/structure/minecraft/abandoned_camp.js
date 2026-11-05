const abandoned_camp_exploration = [
    {
        groupName: "exploration",
        groupWeight: 50,
        min: 2, max: 4,
        items: common_exploration_tools
    },
]

const abandoned_camp_food = [
    {
        groupName: "food",
        groupWeight: 50,
        min: 2, max: 4,
        items: common_survival_supplies
    },
]

const abandoned_camp_potion = [
    {
        groupName: "potion",
        groupWeight: 50,
        min: 1, max: 2,
        items: all_positive_potion_item
    },
]

const abandoned_camp_combat_common = [
    {
        groupName: "common_armors",
        groupWeight: 45,
        min: 1, max: 1, enchantLevels: [0, 10], damage: [0.2, 0.8],
        items: [
            common_metal_armors,
            { id: "minecraft:shield", ratio: 10 },
        ]
    },

    {
        groupName: "common_weapons_and_tools",
        groupWeight: 45,
        min: 1, max: 1, enchantLevels: [0, 10], damage: [0.2, 0.8],
        items: [
            all_common_weapons,
            all_common_tools,
        ]
    },

]

const abandoned_camp_combat_rare = [

    {
        groupName: "rare_armors",
        groupWeight: 5,
        min: 1, max: 1, enchantLevels: [0, 10], damage: [0.2, 0.8],
        items: [
            rare_material_armors,
        ]
    },

    {
        groupName: "rare_weapons",
        groupWeight: 5,
        min: 1, max: 1, enchantLevels: [0, 10], damage: [0.2, 0.8],
        items: [
            all_rare_material_tools,
            rare_material_weapons,
        ]
    },
    {
        groupName: "common_armors",
        groupWeight: 45,
        min: 1, max: 1, enchantLevels: [0, 10], damage: [0.2, 0.8],
        items: [
            common_metal_armors,
            { id: "minecraft:shield", ratio: 10 },
        ]
    },

    {
        groupName: "common_weapons_and_tools",
        groupWeight: 45,
        min: 1, max: 1, enchantLevels: [0, 10], damage: [0.2, 0.8],
        items: [
            all_common_weapons,
            all_common_tools,
        ]
    },
]

const abandoned_camp_common_chest_map = [
    {
        groupName: "abandoned_camp_map",
        groupWeight: 100,
        min: 1, max: 1,
        items: [
            { id: "minecraft:abandoned_camp_map", ratio: 10, jsonFunction: [{"name": {"translate": "filled_map.bamboo_camp_map"}, "target": "item_name", "function": "minecraft:set_name"}, {"decoration": "minecraft:abandoned_camp", "destination": "#minecraft:on_abandoned_camp_bamboo_jungle", "function": "minecraft:exploration_map"}, {"item_filter": {"predicates": {"minecraft:map_id": {}}}, "on_fail": {"type": "minecraft:discard"}, "function": "minecraft:filtered"}] },
            { id: "minecraft:abandoned_camp_map", ratio: 10, jsonFunction: [{"name": {"translate": "filled_map.cherry_grove_camp_map"}, "target": "item_name", "function": "minecraft:set_name"}, {"decoration": "minecraft:abandoned_camp", "destination": "#minecraft:on_abandoned_camp_cherry_grove", "function": "minecraft:exploration_map"}, {"item_filter": {"predicates": {"minecraft:map_id": {}}}, "on_fail": {"type": "minecraft:discard"}, "function": "minecraft:filtered"}] },
            { id: "minecraft:abandoned_camp_map", ratio: 10, jsonFunction: [{"name": {"translate": "filled_map.birch_forest_camp_map"}, "target": "item_name", "function": "minecraft:set_name"}, {"decoration": "minecraft:abandoned_camp", "destination": "#minecraft:on_abandoned_camp_birch_forest", "function": "minecraft:exploration_map"}, {"item_filter": {"predicates": {"minecraft:map_id": {}}}, "on_fail": {"type": "minecraft:discard"}, "function": "minecraft:filtered"}] },
            { id: "minecraft:abandoned_camp_map", ratio: 10, jsonFunction: [{"name": {"translate": "filled_map.dappled_forest_camp_map"}, "target": "item_name", "function": "minecraft:set_name"}, {"decoration": "minecraft:abandoned_camp", "destination": "#minecraft:on_abandoned_camp_dappled_forest", "function": "minecraft:exploration_map"}, {"item_filter": {"predicates": {"minecraft:map_id": {}}}, "on_fail": {"type": "minecraft:discard"}, "function": "minecraft:filtered"}] },
            { id: "minecraft:abandoned_camp_map", ratio: 10, jsonFunction: [{"name": {"translate": "filled_map.flower_forest_camp_map"}, "target": "item_name", "function": "minecraft:set_name"}, {"decoration": "minecraft:abandoned_camp", "destination": "#minecraft:on_abandoned_camp_flower_forest", "function": "minecraft:exploration_map"}, {"item_filter": {"predicates": {"minecraft:map_id": {}}}, "on_fail": {"type": "minecraft:discard"}, "function": "minecraft:filtered"}] },
            { id: "minecraft:abandoned_camp_map", ratio: 10, jsonFunction: [{"name": {"translate": "filled_map.pale_garden_camp_map"}, "target": "item_name", "function": "minecraft:set_name"}, {"decoration": "minecraft:abandoned_camp", "destination": "#minecraft:on_abandoned_camp_pale_garden", "function": "minecraft:exploration_map"}, {"item_filter": {"predicates": {"minecraft:map_id": {}}}, "on_fail": {"type": "minecraft:discard"}, "function": "minecraft:filtered"}] },
            { id: "minecraft:abandoned_camp_map", ratio: 10, jsonFunction: [{"name": {"translate": "filled_map.swamp_camp_map"}, "target": "item_name", "function": "minecraft:set_name"}, {"decoration": "minecraft:abandoned_camp", "destination": "#minecraft:on_abandoned_camp_swamp", "function": "minecraft:exploration_map"}, {"item_filter": {"predicates": {"minecraft:map_id": {}}}, "on_fail": {"type": "minecraft:discard"}, "function": "minecraft:filtered"}] },
            { id: "minecraft:abandoned_camp_map", ratio: 10, jsonFunction: [{"name": {"translate": "filled_map.windswept_forest_camp_map"}, "target": "item_name", "function": "minecraft:set_name"}, {"decoration": "minecraft:abandoned_camp", "destination": "#minecraft:on_abandoned_camp_windswept_forest", "function": "minecraft:exploration_map"}, {"item_filter": {"predicates": {"minecraft:map_id": {}}}, "on_fail": {"type": "minecraft:discard"}, "function": "minecraft:filtered"}] },
        ]
    },
];

const abandoned_camp_secret_chest_map = [
    {
        groupName: "exploration_map",
        groupWeight: 100,
        min: 1, max: 1,
        items: [
            { id: "minecraft:buried_ancient_city_map", ratio: 10, jsonFunction: [{"decoration": "minecraft:ancient_city", "destination": "#minecraft:on_ancient_city_maps", "function": "minecraft:exploration_map"}, {"item_filter": {"predicates": {"minecraft:map_id": {}}}, "on_fail": {"type": "minecraft:discard"}, "function": "minecraft:filtered"}] },
            { id: "minecraft:buried_trial_chambers_map", ratio: 10, jsonFunction: [{"decoration": "minecraft:trial_chambers", "destination": "#minecraft:on_buried_trial_chambers_maps", "function": "minecraft:exploration_map"}, {"item_filter": {"predicates": {"minecraft:map_id": {}}}, "on_fail": {"type": "minecraft:discard"}, "function": "minecraft:filtered"}] },
            { id: "minecraft:buried_mineshaft_map", ratio: 10, jsonFunction: [{"decoration": "minecraft:mineshaft", "destination": "#minecraft:on_mineshaft_maps", "function": "minecraft:exploration_map"}, {"item_filter": {"predicates": {"minecraft:map_id": {}}}, "on_fail": {"type": "minecraft:discard"}, "function": "minecraft:filtered"}] },
            { id: "minecraft:desert_pyramid_map", ratio: 10, jsonFunction: [{"decoration": "minecraft:desert_pyramid", "destination": "#minecraft:on_desert_pyramid_maps", "function": "minecraft:exploration_map"}, {"item_filter": {"predicates": {"minecraft:map_id": {}}}, "on_fail": {"type": "minecraft:discard"}, "function": "minecraft:filtered"}] },
            { id: "minecraft:jungle_pyramid_map", ratio: 10, jsonFunction: [{"decoration": "minecraft:jungle_temple", "destination": "#minecraft:on_jungle_pyramid_maps", "function": "minecraft:exploration_map"}, {"item_filter": {"predicates": {"minecraft:map_id": {}}}, "on_fail": {"type": "minecraft:discard"}, "function": "minecraft:filtered"}] },
            { id: "minecraft:warm_ocean_ruins_map", ratio: 10, jsonFunction: [{"decoration": "minecraft:ocean_ruin_warm", "destination": "#minecraft:on_ocean_ruin_warm_maps", "function": "minecraft:exploration_map"}, {"item_filter": {"predicates": {"minecraft:map_id": {}}}, "on_fail": {"type": "minecraft:discard"}, "function": "minecraft:filtered"}] },
            { id: "minecraft:woodland_mansion_map", ratio: 10, jsonFunction: [{"destination": "#minecraft:on_woodland_mansion_maps", "function": "minecraft:exploration_map"}, {"item_filter": {"predicates": {"minecraft:map_id": {}}}, "on_fail": {"type": "minecraft:discard"}, "function": "minecraft:filtered"}] },
        ]
    },
];

const abandoned_camp_barrel_content = [
    [abandoned_camp_exploration, 2, 4],
]

const abandoned_camp_common_chest_content = [
    [abandoned_camp_exploration, 1, 2],
    [abandoned_camp_food, 2, 3],
    [abandoned_camp_common_chest_map, 1, 1],
]

const abandoned_camp_secret_chest_content = [
    [abandoned_camp_combat_rare, 2, 2],
    [abandoned_camp_secret_chest_map, 1, 1],
    [abandoned_camp_potion, 0, 2],
]

const loot_abandoned_camp_barrel = [
    [abandoned_camp_barrel_content, 1],
]

const loot_abandoned_camp_common_chest = [
    [abandoned_camp_common_chest_content, 1],
]

const loot_abandoned_camp_secret_chest = [
    [abandoned_camp_secret_chest_content, 1],
]