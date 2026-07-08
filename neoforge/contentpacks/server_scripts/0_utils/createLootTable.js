$G.createLootTable_1_20_1 = function(lootTableId) {
    // ServerEvents.genericLootTables(event => {
    //     event.addGeneric(lootTableId, builder => {builder.addPool(pool => {})});    
    // });
}

$G.createLootTable_1_21_1 = function(lootTableId) {
    LootJS.lootTables(event => {
        event.create(lootTableId); 
    }); 
}