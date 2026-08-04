$G.createLootTable2Pool_1_21_1 = function(config) {
    LootJS.lootTables(event => {
        config.forEach(group => {
            // =================================================================
            // 1. 在安全的外层作用域，进行存在性校验，只保留【纯字符串/ID】
            // =================================================================
            let validSingleId = null;
            let validCrateId = null;

            // 校验单品 (singles)
            if (group.singles) {
                for (let id of group.singles) {
                    if (id.startsWith("lootjs:")) {
                        // 如果是以 lootjs: 开头的引用表，必须在游戏内真实存在
                        if (event.hasLootTable(id)) {
                            validSingleId = id;
                            break;
                        }
                    } else if (Item.exists(id)) {
                        // 如果是普通物品，检查是否存在
                        validSingleId = id;
                        break; 
                    }
                }
            }

            // 校验箱子 (crates) —— 同样支持普通物品 ID 或 lootjs 引用的遍历校验
            if (group.crates) {
                for (let id of group.crates) {
                    if (id.startsWith("lootjs:")) {
                        if (event.hasLootTable(id)) {
                            validCrateId = id;
                            break;
                        }
                    } else if (Item.exists(id)) {
                        validCrateId = id;
                        break;
                    }
                }
            }

            // =================================================================
            // 2. 只有当单品或箱子至少有一个合法存在时，才创建全新的虚拟战利品表
            // =================================================================
            if (validSingleId || validCrateId) {              
                let tbl = event.create(`lootjs:${group.name}_and_${group.name}_block`);
                
                // 注入单品池
                if (validSingleId) {
                    tbl.createPool(pool => {
                        let entry;
                        if (validSingleId.startsWith("lootjs:")) {
                            entry = LootEntry.reference(validSingleId);
                        } else {
                            entry = LootEntry.of(validSingleId);
                        }
                        pool.addEntry(entry);
                    });
                }
                
                // 注入箱子池 —— 采用相同的动态 Entry 生成逻辑
                if (validCrateId) {
                    tbl.createPool(pool => {
                        let entry;
                        if (validCrateId.startsWith("lootjs:")) {
                            entry = LootEntry.reference(validCrateId);
                        } else {
                            entry = LootEntry.of(validCrateId);
                        }
                        pool.addEntry(entry);
                    });
                }
            }
        });
    });
}

$G.createLootTable2Pool_1_20_1 = function(config) {
    // // 1. 注册虚拟战利品表结构
    // ServerEvents.genericLootTables(event => {
    //     config.forEach(group => {
    //         // 检查 singleEntries 中是否有至少一个有效项
    //         let hasSingle = group.singles.some(id => id.startsWith("lootjs:") || Item.exists(id));
    //         let hasCrate = group.crates && group.crates[0];

    //         if (hasSingle || hasCrate) {
    //             event.addGeneric(`lootjs:${group.name}_and_${group.name}_block`, builder => {});
    //         }
    //     });
    // });

    // LootJS.modifiers(event => {
    //     config.forEach(group => {
    //         let singleEntry = null;

    //         for (let id of group.singles) {
    //             if (id.startsWith("lootjs:")) {
    //                 singleEntry = LootEntry.ofJson({
    //                     "type": "minecraft:loot_table",
    //                     "name": id
    //                 });
    //                 break;
    //             } else if (Item.exists(id)) {
    //                 singleEntry = LootEntry.of(id);
    //                 break; 
    //             }
    //         }

    //         let crateEntry = (group.crates && group.crates[0]) ? LootEntry.ofJson({
    //             "type": "minecraft:loot_table",
    //             "name": group.crates[0]
    //         }) : null;

    //         if (singleEntry || crateEntry) {
                
    //             let tbl = event.addLootTableModifier(`lootjs:${group.name}_and_${group.name}_block`)

    //             if (singleEntry) {
    //                 tbl.pool(pool => {
    //                     pool.addLoot(singleEntry);
    //                 });
    //             }

    //             if (crateEntry) {
    //                 tbl.pool(pool => {
    //                     pool.addLoot(crateEntry);
    //                 });
    //             }
    //         }
    //     });
    // });
}