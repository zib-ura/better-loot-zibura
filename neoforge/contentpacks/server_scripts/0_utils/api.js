// LootJS.modifiers(event => {
//     // event.removeGlobalModifier("examplemod:example_loot_change"),
//     event.addLootTableModifier("minecraft:entities/creeper")
//     .removeLoot(Ingredient.all)
//     .addLoot(Ingredient.of("minecraft:gunpowder"), 100)
//     .pool(pool => {
//         pool.rolls([1, 3])
//         .randomChance(0.3)
//         .addLoot(
//             LootEntry.of("minecraft:apple")
//             .when((c) => c.randomChance(0.5))
//             .limitCount([5, 10])
//             .damage(5)
//             .enchantRandomly()
//             .addNBT({ customKey: "customValue" })
//             .enchantWithLevels([10, 20])
//             .addPotion(Internal.Potion_),
//             LootEntry.ofJson
//             (
//                 {
//                     "type": "minecraft:empty"
//                 },
//             ),
//             LootEntry.ofJson(
//                 {
//                     "type": "minecraft:loot_table",
//                     "name": "minecraft:chests/simple_dungeon"
//                 },
//             )
//         )
//         .addAlternativesLoot(LootEntry.of("minecraft:apple"))
//         .addSequenceLoot(LootEntry.of("minecraft:apple"))
//         .addWeightedLoot([Item.of("minecraft:gunpowder").withChance(50)])
//         .or((or) => {
//             or.anyBiome("minecraft:jungle");
//             or.lightLevel(0, 7);
//         })
//     });
// });


// ServerEvents.chestLootTables(event => {
    
//     event.addChest("lootjs:chests/desert_pyramid", builder => {});
// });