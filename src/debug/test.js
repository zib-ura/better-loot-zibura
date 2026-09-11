// LootJS.lootTables(event => {
//     event.create("lootjs:test")
// //     .createPool(pool => {
// //                 pool.addEntry(LootEntry.of("exposure:aged_photograph").jsonFunction({
// //     function: "minecraft:set_components",
// //     components: {
// //         "exposure:photograph_frame": {
// //             identifier: {
// //                 texture: "exposure:textures/exposure/village/village_4.png"
// //             }
// //         }
// //     }
// // }))
// //     })
// })

// const test_photo = [
//     // =================================================================
//     // Group 1: Photos & Misc
//     // =================================================================
//     {
//         groupName: "Photos & Misc",
//         groupWeight: 5,
//         min: 1, max: 1,
//         items: [
//             {
//                 id: "exposure:aged_photograph",
//                 ratio: 100,
//                 jsonfunction: {
//                     "function": "minecraft:set_components",
//                     "components": {
//                         "exposure:photograph_frame": {
//                             "identifier": {
//                                 "texture": "exposure:textures/exposure/village/attack_1.png"
//                             }
//                         }
//                     }                
//                 }
//             },
//         ]
//     }
// ]

// LootJS.lootTables(event => {

//     const Loot = event.getLootTable("minecraft:chests/village/village_plains_house");

//     addCustomLoot(Loot, test_photo, 1, 1);
// });

/**
 * When Entry is rolled:
 * Handle all children by their own. Just a convenience entry.
 */


// LootJS.lootTables(event => {
//     event
//     const Loot = event.create("lootjs:test");
//     Loot.createPool(pool => {
//         pool.addEntry(
//             LootEntry.group(
//     LootEntry.of("minecraft:diamond").when(c => c),
//     LootEntry.of("minecraft:emerald").when(c => c),
//     LootEntry.of("minecraft:iron_ingot").when(c => c)
//             )
//         );
//     })
// });