$G.registerLootTables = function(tablesObj) {
    for (const [path, varName] of Object.entries(tablesObj)) {
        createLootTableReference(path, varName);
    }
}