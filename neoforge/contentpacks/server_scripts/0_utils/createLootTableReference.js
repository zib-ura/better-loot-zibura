/**
 * Function to automatically create loot tables based on the main table ID and entriesArray
 * @param {string} tableId Main loot table ID (e.g., "minecraft:chests/woodland_mansion")
 * @param {Array} entriesArray Format: [[subTableDataArray, weight], [subTableDataArray, weight]]
 * Where the subTableDataArray format is: [[products, minRolls, maxRolls, conditionCallback (e.g.  c => c.biome("minecraft:desert"))], ...]
 */
/**
 * 自动根据主表 ID 和子表数组创建战利品表的函数
 * @param {string} tableId 主战利品表 ID (例如 "minecraft:chests/woodland_mansion")
 * @param {Array} entriesArray 格式: [[子表数据数组, 权重], [子表数据数组, 权重]]
 * 其中子表数据数组格式: [[物品组数组, 最小抽数, 最大抽数, 条件(e.g.  c => c.biome("minecraft:desert"))], ...]
 */


$G.createLootTableReference_1_21_1 = function(tableId, entriesArray, mode) {

    // 1. 自动化处理物品引用与合并（由于是嵌套数组，需要双重循环解包）
    entriesArray.forEach(([subTableData]) => {
        if (Array.isArray(subTableData)) {
            subTableData.forEach(([lootGroup]) => {
                crateLootReferencesToItems(lootGroup);
                LootReferencesToItem(lootGroup, mergeItem);
                youkaisCompat(lootGroup)

            });
        }
    });

    // 2. 自动化清空原表
    if (mode === "REPLACE") {
        clearLootTable([tableId]);
    }

    // 第一步：动态生成子表 ID 并填充具体物品
    LootJS.lootTables(event => {
        entriesArray.forEach(([subTableData, weight], index) => {
            const subTableName = `${tableId}_sub_${index}`;
            
            // 1. 显式创建子表
            event.create(subTableName); 
            
            subTableData.forEach(([products, min, max, conditionCallback]) => {
                addCustomLoot(event, subTableName, products, min, max, conditionCallback);
            })
        });
    });

    // 第二步：将这些动态生成的子表挂载到主表上
    LootJS.lootTables(event => {
        if (event.hasLootTable(tableId)) {
            let mainLoot = event.getLootTable(tableId);

            mainLoot.createPool(pool => {
                entriesArray.forEach(([config, weight], index) => {
                    let subTableName = `${tableId}_sub_${index}`;
                    
                    // 创建引用条目
                    let entry = LootEntry.reference(subTableName);     
                    if (weight !== undefined) {
                        entry = entry.withWeight(weight);
                    }              
                    pool.addEntry(entry);
                });
            });
        }
    });
}

$G.createLootTableReference_1_20_1 = function(tableId, entriesArray, mode) {
    // 1. 自动化处理物品引用与合并（由于是嵌套数组，需要双重循环解包）
    entriesArray.forEach(([subTableData]) => {
        if (Array.isArray(subTableData)) {
            subTableData.forEach(([lootGroup]) => {
                crateLootReferencesToItems(lootGroup);
                LootReferencesToItem(lootGroup, mergeItem);
                youkaisCompat(lootGroup)

            });
        }
    });

    // 2. 自动化清空原表
    if (mode === "REPLACE") {
        clearLootTable([tableId]);
    }
    
    LootJS.modifiers(event => {
        // 1. 先计算总权重（决定时间轴的总长度）
        let totalWeight = 0;
        entriesArray.forEach(([_, weight]) => {
            totalWeight += weight;
        });

        // 2. 遍历数组，按权重分配时间段
        let currentOffset = 0; // 记录当前时间段的起点
        
        entriesArray.forEach(([actualArray, weight]) => {
            // 计算当前选项在时间轴上的落点范围 [min, max]
            let minTime = currentOffset;
            let maxTime = currentOffset + weight - 1;
            
            // 累加偏移量，给下一个选项用
            currentOffset += weight;

            // ================= CRITICAL FIX =================
            // 核心修复：每一次循环，都必须重新向 event 申请一个全新的独立控制句柄！
            // 这样 6 个时间段才能互不干扰，形成并列的“多选一”关系
            let independentLoot = event.addLootTableModifier(tableId);
            let modifier = independentLoot.timeCheck(totalWeight, minTime, maxTime);
            // ================================================

            if (Array.isArray(actualArray)) {
                actualArray.forEach(([products, min, max, conditionCallback]) => {
                    // ⭐ 核心修复：每个独立的子组，都去申请一个全新的独立修改器，并套上时间轴
                    let independentLoot = event.addLootTableModifier(tableId);
                    let modifier = independentLoot.timeCheck(totalWeight, minTime, maxTime);
                    
                    // 传入这个绝对独立的 modifier
                    addCustomLoot(modifier, products, min, max, conditionCallback);
                });
            }
        });
    });
}