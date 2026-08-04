$G.addEnchantedBook_1_21_1 = function() {
    LootJS.lootTables(event => {

        // 通用处理函数：负责检测战利品表并添加对应附魔书条目（现在默认全部清理）
        function addEnchantedBooksToLoot(tables, enchantments, rolls) {
            // 不再判断 shouldClean，直接全部调用 cleanEnchantments 过滤
            let targetEnchants = cleanEnchantments(enchantments);
            
            // 映射生成 lootjs 的书本标识符
            let books = targetEnchants.map(enchant => `lootjs:${enchant.replace(':', '_')}_book`);

            tables.forEach(table => {
                if (event.hasLootTable(table)) {
                    let pool = event.getLootTable(table).createPool().rolls(rolls);
                    books.forEach(book => pool.addEntry(LootEntry.reference(book)));
                }
            });
        }

        // 循环遍历配置并调用通用函数进行处理
        structureEnchantmentConfigs.forEach(cfg => {
            // 移除了第四个参数 cfg.clean
            addEnchantedBooksToLoot(cfg.tables, cfg.enchants, cfg.rolls);
        });
    });
}

// $G.addEnchantedBook_1_20_1 = function() {
//     LootJS.modifiers(event => {
//         event.addLootTableModifier("minecraft:entities/creeper")
//         .pool(pool => {
//             pool.rolls([1, 3])
//             .addWeightedLoot([
//                 LootEntry.of("minecraft:apple")
//                 .enchantRandomly()
//                 .addNBT({ customKey: "customValue" })
//             ])
//         });
//     });
// }

$G.addEnchantedBook_1_20_1 = function() {
    // 检查是否存在 Immersive Enchanting 的远古书，如果没有则回退到原版附魔书
    let isAncientBookEnabled = Item.exists("immersiveenchanting:ancient_book")
    let bookItemId = isAncientBookEnabled ? "immersiveenchanting:ancient_book" : "minecraft:enchanted_book";

    LootJS.modifiers(event => {
        
        // 核心处理函数：为 1.20.1 结构的战利品表注入附魔书
        function addEnchantedBooksToLoot_1_20_1(tables, enchants, rolls) {
            // 过滤掉未启用的附魔
            let targetEnchants = cleanEnchantments(enchants);
            if (targetEnchants.length === 0) return;

            // 分类：将当前结构需要的附魔，拆分为“有等级”和“无等级”两组
            let withLevels = targetEnchants.filter(e => enchantmentsWithLevels.includes(e));
            let withoutLevels = targetEnchants.filter(e => enchantmentsWithoutLevels.includes(e));

            // 遍历目标战利品表
            tables.forEach(table => {
                // 1.20.1 推荐直接使用针对指定战利品表的 modifier
                let modifier = event.addLootTableModifier(table);

                modifier.pool(pool => {
                    pool.rolls(rolls);

                    // 数组用于存放该 Pool 下所有的战利品条目
                    let entries = [];

                    // 逻辑 A：处理有等级的附魔 (With Levels)
                    withLevels.forEach(enchant => {
                        if (isAncientBookEnabled) {
                            // 远古书：带特定 NBT 注入，默认 1 级
                            entries.push(
                                LootEntry.of(bookItemId).addNBT({ StoredEnchantments: [{ id: enchant, lvl: 1 }] })
                            );
                        } else {
                            // 原版书：使用 enchantRandomly 动态赋予该附魔及其随机等级
                            entries.push(
                                LootEntry.of("minecraft:book").enchantRandomly([enchant])
                            );
                        }
                    });

                    // 逻辑 B：处理无等级的附魔 (Without Levels)
                    withoutLevels.forEach(enchant => {
                        if (isAncientBookEnabled) {
                            // 远古书：依然带 NBT 注入
                            entries.push(
                                LootEntry.of(bookItemId).addNBT({ StoredEnchantments: [{ id: enchant, lvl: 1 }] })
                            );
                        } else {
                            // 原版书：无等级附魔使用 Item.of(...).enchant(**, 1) 包装后传入
                            // 注意：LootJS 接收 Item.of() 实例作为物品
                            entries.push(
                                LootEntry.of(Item.of("minecraft:enchanted_book").enchant(enchant, 1))
                            );
                        }
                    });

                    // 将所有生成的条目以等权重（单条目默认权重 1）加入到当前 Pool
                    if (entries.length > 0) {
                        pool.addWeightedLoot(entries);
                    }
                });
            });
        }

        // 遍历你给出的核心重构配置区
        structureEnchantmentConfigs.forEach(cfg => {
            addEnchantedBooksToLoot_1_20_1(cfg.tables, cfg.enchants, cfg.rolls);
        });

    });
};