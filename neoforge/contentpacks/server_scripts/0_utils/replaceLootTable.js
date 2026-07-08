$G.replaceLootTable_1_21_1 = function(targetId, referenceId) {
    LootJS.lootTables(event => {
        if (event.hasLootTable(targetId)) {
            let Loot = event.getLootTable(targetId);
            Loot.createPool(pool => {
                pool.addEntry(LootEntry.reference(referenceId));
            });
        }
    });
}

$G.replaceLootTable_1_20_1 = function(targetId, referenceId) {
    LootJS.modifiers(event => {
        event.addLootTableModifier(targetId)
        .addLoot(
            LootEntry.ofJson(
                {
                    "type": "minecraft:loot_table",
                    "name": referenceId
                },
            )
        )
    });
}