$G.generateEnchantmentLootTables_1_21_1 = function(enchantmentsWithLevels, enchantmentsWithoutLevels) {
    LootJS.lootTables(event => {

        // ==================== 随机附魔处理 (enchantments) ====================
        enchantmentsWithLevels.forEach(fullEnchantId => {
            const tableName = fullEnchantId.replace(":", "_");

            let levelsComponent = {};
            levelsComponent[fullEnchantId] = 1;

            // 基础的普通书 Entry
            const vanillaBookEntry = LootEntry.of("minecraft:book").enchantRandomly([fullEnchantId]);

            // 构建 alternative 容器需要的数组
            let entries = [];

            // 如果存在沉浸附魔模组，优先将其作为第一顺位放入（保留各自原本的概率/条件）
            if (Item.exists("immersiveenchanting:ancient_book")) {
                entries.push(
                    LootEntry.of("immersiveenchanting:ancient_book").jsonFunction({
                        "function": "minecraft:set_components",
                        "components": {
                            "minecraft:stored_enchantments": {
                                "levels": levelsComponent
                            }
                        }
                    })
                );
            }
            
            // 普通书作为最后的保底进入数组
            entries.push(vanillaBookEntry);

            // 使用 apply 动态传入数组，生成 alternative 结构
            event.create(`lootjs:${tableName}_book`).createPool(pool => {
                pool.addEntry(LootEntry.alternative.apply(null, entries));
            });
        });

        // ==================== 固定附魔处理 (enchantmentsWithoutLevels) ====================
        enchantmentsWithoutLevels.forEach(fullEnchantId => {
            const tableName = fullEnchantId.replace(":", "_");

            let levelsComponent = {};
            levelsComponent[fullEnchantId] = 1;

            // 基础的普通书 Entry
            const vanillaBookEntry = LootEntry.of("minecraft:book").enchant(builder => {
                builder.withEnchantment(fullEnchantId, 1);
            });

            let entries = [];

            // 优先尝试古代书
            if (Item.exists("immersiveenchanting:ancient_book")) {
                entries.push(
                    LootEntry.of("immersiveenchanting:ancient_book").jsonFunction({
                        "function": "minecraft:set_components",
                        "components": {
                            "minecraft:stored_enchantments": {
                                "levels": levelsComponent
                            }
                        }
                    })
                );
            }

            // 普通书保底
            entries.push(vanillaBookEntry);

            // 使用 apply 动态传入数组，生成 alternative 结构
            event.create(`lootjs:${tableName}_book`).createPool(pool => {
                pool.addEntry(LootEntry.alternative.apply(null, entries));
            });
        });
    });
}


$G.generateEnchantmentLootTables_1_20_1 = function(enchantmentsWithLevels, enchantmentsWithoutLevels) {
    // const allAvailableEnchantments = enchantmentsWithLevels.concat(enchantmentsWithoutLevels);
    // allAvailableEnchantments.forEach(fullEnchantId => {
    //     const tableName = fullEnchantId.replace(":", "_");
    //     createLootTable(`lootjs:${tableName}_book`)
    // })

    // LootJS.modifiers(event => {

    //     // ==================== 随机附魔处理 (enchantments) ====================
    //     allAvailableEnchantments.forEach(fullEnchantId => {
    //         const tableName = fullEnchantId.replace(":", "_");

    //         let levelsComponent = {};
    //         levelsComponent[fullEnchantId] = 1;

    //         // 基础的普通书 Entry
    //         const vanillaBookEntry = LootEntry.of("minecraft:book").enchantRandomly([fullEnchantId]);

    //         // 构建 alternative 容器需要的数组
    //         let entries = [];

    //         // 如果存在沉浸附魔模组，优先将其作为第一顺位放入（保留各自原本的概率/条件）
    //         if (Item.exists("immersiveenchanting:ancient_book")) {
    //             entries.push(
    //                 LootEntry.of("immersiveenchanting:ancient_book").addNBT(
    //                     {StoredEnchantments:[{id:fullEnchantId,lvl:1}]}
    //                 )
    //             );
    //         }
            
    //         // 普通书作为最后的保底进入数组
    //         entries.push(vanillaBookEntry);

    //         event.addLootTableModifier(`lootjs:${tableName}_book`).pool(pool => {
    //             pool.addAlternativesLoot.apply(pool, entries);
    //         });
    //     });
    // })
}