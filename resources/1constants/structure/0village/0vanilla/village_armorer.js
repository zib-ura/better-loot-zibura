const village_armorer_products = [
    {
        groupName: "common_armor",
        groupWeight: 95,
        min: 1, max: 1,

        damage: [0.7, 0.8],
        items: [
            common_metal_armors,
            horse_wolf_armors,
            { id: "minecraft:shield", ratio: 20 },
        ]
    },

    {
        groupName: "rare_armor",
        groupWeight: 5,
        min: 1,  max: 1,
        
        damage: [0.7, 0.8],
        items: [
            // rare_material_armors,
            { id: "minecraft:diamond", ratio: 10 },
          
        ]
    }
];



// =================================================================
// 事件注册区域
// =================================================================
const village_armorer_content = [
    [village_armorer_products, 1, 2],
    [village_smith_common_products,    1, 2],
    [village_smith_materials,          2, 4],
    [iron_nugget, 3, 4],
    [name_tag,    1, 1],
];

const loot_village_armorer = [
    [village_armorer_content, 1],
];