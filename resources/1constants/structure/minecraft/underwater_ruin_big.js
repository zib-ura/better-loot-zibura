// const underwater_ruin_big = [
//     // 大型建筑坍塌后散落的碎片
//     {
//         groupName: "collapsed_structure",
//         groupWeight: 70,
//         min: 1, max: 3,
//         items: [
//             { id: "minecraft:brick", ratio: 12, min: 1, max: 5 },
//             { id: "minecraft:stone_bricks", ratio: 10, min: 1, max: 3 },
//             { id: "minecraft:cracked_stone_bricks", ratio: 8, min: 1, max: 3 },
//             { id: "minecraft:mossy_stone_bricks", ratio: 8, min: 1, max: 3 },
//             { id: "minecraft:clay_ball", ratio: 10, min: 1, max: 4 },
//             { id: "minecraft:flint", ratio: 6, min: 1, max: 2 },
//             { id: "minecraft:gravel", ratio: 10, min: 1, max: 3 },
//             { id: "minecraft:prismarine_shard", ratio: 4, min: 1, max: 2 },
//         ]
//     },
//
//     // 淹没的居所与仓库中的日用品
//     {
//         groupName: "submerged_household",
//         groupWeight: 75,
//         min: 1, max: 3,
//         items: [
//             { id: "minecraft:bowl", ratio: 10, min: 1, max: 2 },
//             { id: "minecraft:glass_bottle", ratio: 10, min: 1, max: 3 },
//             { id: "minecraft:potion", ratio: 6, potion: "minecraft:water" },
//             { id: "minecraft:flower_pot", ratio: 5 },
//             { id: "minecraft:paper", ratio: 8, min: 1, max: 3 },
//             { id: "minecraft:book", ratio: 5 },
//             { id: "minecraft:leather", ratio: 10, min: 1, max: 3 },
//             { id: "minecraft:leather_boots", ratio: 7 },
//             { id: "minecraft:leather_chestplate", ratio: 5 },
//             { id: "minecraft:stick", ratio: 10, min: 1, max: 4 },
//             { id: "minecraft:string", ratio: 8, min: 1, max: 3 },
//             { id: "minecraft:bone", ratio: 6, min: 1, max: 3 },
//             { id: "minecraft:rotten_flesh", ratio: 8, min: 1, max: 3 },
//         ]
//     },
//
//     // 沉没的渔业仓库与旧捕鱼装备
//     {
//         groupName: "sunken_fishery",
//         groupWeight: 60,
//         min: 1, max: 3,
//         items: [
//             { id: "minecraft:fishing_rod", enchantRandomly: [], ratio: 10 },
//             { id: "minecraft:tripwire_hook", ratio: 8, min: 1, max: 2 },
//             { id: "minecraft:string", ratio: 12, min: 1, max: 4 },
//             { id: "minecraft:cod", ratio: 10, min: 1, max: 4 },
//             { id: "minecraft:salmon", ratio: 8, min: 1, max: 3 },
//             { id: "minecraft:pufferfish", ratio: 4 },
//             { id: "minecraft:tropical_fish", ratio: 4 },
//             { id: "minecraft:ink_sac", ratio: 6, min: 1, max: 2 },
//             { id: "minecraft:nautilus_shell", ratio: 2 },
//             { id: "minecraft:lily_pad", ratio: 5 },
//             { id: "minecraft:kelp", ratio: 8, min: 1, max: 3 },
//         ]
//     },
//
//     // 古代聚落保存的粮食储备
//     {
//         groupName: "ancient_food_storage",
//         groupWeight: 65,
//         min: 2, max: 4,
//         items: [
//             { id: "minecraft:wheat", ratio: 15, min: 2, max: 5 },
//             { id: "minecraft:coal", ratio: 12, min: 1, max: 4 },
//             { id: "minecraft:dried_kelp", ratio: 10, min: 1, max: 4 },
//             { id: "minecraft:bread", ratio: 6, min: 1, max: 2 },
//             { id: "minecraft:potato", ratio: 8, min: 1, max: 3 },
//             { id: "minecraft:poisonous_potato", ratio: 5 },
//             { id: "minecraft:wheat_seeds", ratio: 8, min: 1, max: 4 },
//             { id: "minecraft:beetroot_seeds", ratio: 6, min: 1, max: 3 },
//             { id: "minecraft:rotten_flesh", ratio: 8, min: 1, max: 3 },
//         ]
//     },
//
//     // 旧居民留下的工具和装备
//     {
//         groupName: "abandoned_equipment",
//         groupWeight: 55,
//         min: 1, max: 2, damage: [0.2, 0.5],
//         items: [
//             { id: "minecraft:wooden_axe", ratio: 8 },
//             { id: "minecraft:wooden_shovel", ratio: 8 },
//             { id: "minecraft:stone_axe", ratio: 10 },
//             { id: "minecraft:stone_shovel", ratio: 10 },
//             { id: "minecraft:stone_hoe", ratio: 8 },
//             { id: "minecraft:iron_nugget", ratio: 10, min: 1, max: 3 },
//             { id: "minecraft:leather_helmet", ratio: 6 },
//             { id: "minecraft:leather_boots", ratio: 8 },
//             { id: "minecraft:leather_chestplate", ratio: 5 },
//             { id: "minecraft:golden_helmet", ratio: 3 },
//             { id: "minecraft:shield", ratio: 2 },
//         ]
//     },
//
//     // 沉没聚落曾经积累的财富
//     {
//         groupName: "sunken_valuables",
//         groupWeight: 65,
//         min: 1, max: 3,
//         items: [
//             { id: "minecraft:gold_nugget", ratio: 15, min: 1, max: 5 },
//             { id: "minecraft:iron_nugget", ratio: 12, min: 1, max: 4 },
//             { id: "minecraft:emerald", ratio: 6, min: 1, max: 2 },
//             { id: "minecraft:lapis_lazuli", ratio: 7, min: 1, max: 3 },
//             { id: "minecraft:gold_ingot", ratio: 3 },
//             { id: "minecraft:iron_ingot", ratio: 5, min: 1, max: 2 },
//             { id: "minecraft:prismarine_crystals", ratio: 3, min: 1, max: 2 },
//             { id: "minecraft:nautilus_shell", ratio: 2 },
//         ]
//     },
//
//     // 海底废墟的古老文献与航海记录
//     {
//         groupName: "ancient_records",
//         groupWeight: 35,
//         min: 1, max: 2,
//         items: [
//             { id: "minecraft:paper", ratio: 12, min: 1, max: 4 },
//             { id: "minecraft:book", ratio: 10 },
//             { id: "minecraft:map", ratio: 8 },
//             { id: "minecraft:compass", ratio: 5 },
//             { id: "minecraft:clock", ratio: 3 },
//             {
//                 id: "minecraft:book",
//                 ratio: 3,
//                 enchantLevels: [20, 30]
//             },
//         ]
//     },
//
//     // 航海者留下的藏宝线索
//     {
//         groupName: "buried_treasure_clues",
//         groupWeight: 25,
//         min: 1, max: 1,
//         items: [
//             {
//                 id: "minecraft:map",
//                 ratio: 10,
//                 jsonFunction: [
//                     {
//                         type: "minecraft:exploration_map",
//                         destination: "minecraft:buried_treasure",
//                         decoration: "minecraft:red_x",
//                         zoom: 1,
//                         skip_existing_chunks: false
//                     },
//                     {
//                         function: "minecraft:set_name",
//                         name: {
//                             translate: "filled_map.buried_treasure"
//                         },
//                         target: "item_name"
//                     }
//                 ]
//             },
//             { id: "minecraft:compass", ratio: 5 },
//             { id: "minecraft:map", ratio: 5 },
//         ]
//     },
//
//     // 极少数箱子中保存完好的珍贵遗物
//     {
//         groupName: "rare_relics",
//         groupWeight: 18,
//         min: 1, max: 1,
//         items: [
//             {
//                 id: "minecraft:book",
//                 ratio: 8,
//                 enchantLevels: [29, 30]
//             },
//             {
//                 id: "minecraft:fishing_rod",
//                 ratio: 6,
//                 enchantLevels: [25, 30]
//             },
//             { id: "minecraft:golden_apple", ratio: 2 },
//             { id: "minecraft:gold_ingot", ratio: 6, min: 1, max: 2 },
//             { id: "minecraft:emerald", ratio: 8, min: 1, max: 3 },
//             { id: "minecraft:nautilus_shell", ratio: 4 },
//             { id: "minecraft:heart_of_the_sea", ratio: 1 },
//         ]
//     },
// ];
//
// const underwater_ruin_big_enchanted_book = [
//     {
//         groupName: "underwater_ruin_big",
//         groupWeight: 50,
//         min: 1, max: 1,
//         items: [
//             { id: "minecraft:book", ratio: 10, enchantRandomly: ["minecraft:aqua_affinity"] },
//             { id: "minecraft:book", ratio: 10, enchantRandomly: ["minecraft:depth_strider"] },
//             { id: "minecraft:book", ratio: 10, enchantRandomly: ["minecraft:luck_of_the_sea"] },
//             { id: "minecraft:book", ratio: 10, enchantRandomly: ["minecraft:lure"] },
//             { id: "minecraft:book", ratio: 10, enchantRandomly: ["minecraft:respiration"] },
//             { id: "minecraft:book", ratio: 10, enchantRandomly: ["minecraft:channeling"] },
//             { id: "minecraft:book", ratio: 10, enchantRandomly: ["minecraft:impaling"] },
//             { id: "minecraft:book", ratio: 10, enchantRandomly: ["minecraft:loyalty"] },
//             { id: "minecraft:book", ratio: 10, enchantRandomly: ["minecraft:riptide"] },
//             { id: "minecraft:book", ratio: 10, enchantRandomly: ["kattersstructures:ocean_heart"] },
//
//         ]
//     },
//     {
//         groupName: "empty",
//         groupWeight: 50,
//         items: [
//             { id: "empty" },
//         ]
//     },
// ];
//
//
// // =================================================================
// // 事件注册区域
// // =================================================================
// const underwater_ruin_big_content = [
//     [underwater_ruin_big, 4, 5],
//     [underwater_ruin_big_enchanted_book, 1, 1],
// ];
//
// const loot_underwater_ruin_big = [
//     [underwater_ruin_big_content, 1],
// ];