
const common_sword = [
    { id: "minecraft:iron_sword", ratio: 10, max: 1 },
    { id: "minecraft:copper_sword", ratio: 10, max: 1 },
    { id: "minecraft:stone_sword", ratio: 10, max: 1 },
];

const common_spear = [
    { id: "minecraft:iron_spear", ratio: 10, max: 1 },
    { id: "minecraft:copper_spear", ratio: 10, max: 1 },
    { id: "minecraft:stone_spear", ratio: 10, max: 1 },
];

const common_ranged_weapons = [
    { id: "minecraft:bow", ratio: 10, max: 1 },
    { id: "minecraft:crossbow", ratio: 10, max: 1 },
    { id: "supplementaries:slingshot", ratio: 10, max: 1 },
    { id: "supplementaries:quiver", ratio: 10, max: 1 },
];

const all_common_weapons = [
    common_sword,
    common_spear,
    common_ranged_weapons,
];

const common_metal_armors = [
    // 链甲系列
    { id: "minecraft:chainmail_helmet", ratio: 10, max: 1 },
    { id: "minecraft:chainmail_chestplate", ratio: 10, max: 1 },
    { id: "minecraft:chainmail_leggings", ratio: 10, max: 1 },
    { id: "minecraft:chainmail_boots", ratio: 10, max: 1 },

    // 铁甲与盾牌
    { id: "minecraft:iron_helmet", ratio: 10, max: 1 },
    { id: "minecraft:iron_chestplate", ratio: 10, max: 1 },
    { id: "minecraft:iron_leggings", ratio: 10, max: 1 },
    { id: "minecraft:iron_boots", ratio: 10, max: 1 },

    // 铜甲系列
    { id: "minecraft:copper_helmet", ratio: 10, max: 1 },
    { id: "minecraft:copper_chestplate", ratio: 10, max: 1 },
    { id: "minecraft:copper_leggings", ratio: 10, max: 1 },
    { id: "minecraft:copper_boots", ratio: 10, max: 1 },

    // 模块化盔甲 - 战士系列
    { id: "immersive_armors:warrior_helmet", ratio: 10, max: 1 },
    { id: "immersive_armors:warrior_chestplate", ratio: 10, max: 1 },
    { id: "immersive_armors:warrior_leggings", ratio: 10, max: 1 },
    { id: "immersive_armors:warrior_boots", ratio: 10, max: 1 },

    // 模块化盔甲 - 重甲系列
    { id: "immersive_armors:heavy_helmet", ratio: 10, max: 1 },
    { id: "immersive_armors:heavy_chestplate", ratio: 10, max: 1 },
    { id: "immersive_armors:heavy_leggings", ratio: 10, max: 1 },
    { id: "immersive_armors:heavy_boots", ratio: 10, max: 1 },
];

const horse_wolf_armors = [
    // 特殊装备：马铠/狼铠
    // 无耐久度的装备会忽略 damage 设置
    { id: "minecraft:iron_horse_armor", ratio: 10, max: 1 },
    // { id: "minecraft:golden_horse_armor", ratio: 10, max: 1 },
    { id: "minecraft:copper_horse_armor", ratio: 10, max: 1 },
    // { id: "minecraft:wolf_armor", ratio: 10, max: 1 },
];

const dungeon_armors = [
    { id: "immersive_armors:bone_helmet", ratio: 10, max: 1 },
    { id: "immersive_armors:bone_chestplate", ratio: 10, max: 1 },
    { id: "immersive_armors:bone_leggings", ratio: 10, max: 1 },
    { id: "immersive_armors:bone_boots", ratio: 10, max: 1 },
];

const rare_material_weapons = [
    { id: "minecraft:diamond_spear", ratio: 10 },
    { id: "minecraft:diamond_sword", ratio: 10 },
]

const rare_material_armors = [
    { id: "minecraft:diamond_helmet", ratio: 10 },
    { id: "minecraft:diamond_chestplate", ratio: 10 },
    { id: "minecraft:diamond_leggings", ratio: 10 },
    { id: "minecraft:diamond_boots", ratio: 10 },
]

const special_armors = [
    { id: "immersive_armors:divine_helmet", ratio: 10 },
    { id: "immersive_armors:divine_chestplate", ratio: 10 },
    { id: "immersive_armors:divine_leggings", ratio: 10 },
    { id: "immersive_armors:divine_boots", ratio: 10 },
]