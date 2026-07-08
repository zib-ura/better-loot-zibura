/**
 * Function to automatically create sub-tables based on the main table ID and index
 * @param {string} tableId Main loot table ID (e.g., "minecraft:chests/woodland_mansion")
 * @param {Array} entriesArray Format: [[products, minRolls, maxRolls, conditionCallback (e.g.  c => c.biome("minecraft:desert"))], ...]
 * Where "products" is an array of objects structured as follows:
 * [
 * {
 * groupName: string,      // Name of the item group
 * groupWeight: number,    // Spawn weight of the group itself
 * items: [                // List of item candidates within this group
 * { id: string, ratio: number }, ...
 * ]
 * }, ...
 * ]
 */


/**
 * 根据主表 ID 和索引自动生成子表
 * @param {string} tableId 主战利品表 ID (例如 "minecraft:chests/woodland_mansion")
 * @param {Array} entriesArray 格式: [[物品组数组, 最小抽数, 最大抽数, 条件(e.g.  c => c.biome("minecraft:desert"))], ...]
 * * 其中“物品组数组”的结构如下：
 * [
 * {
 * groupName: string,   // 物品组名称
 * groupWeight: number, // 该组自身的生成权重
 * items: [             // 该组内的备选物品列表
 * { id: string, ratio: number }, ...
 * ]
 * }, ...
 * ]
 */
$G.createLootTableConst_1_21_1 = function(tableId, entriesArray) {
    entriesArray.forEach(([lootGroup]) => {
        crateLootReferencesToItems(lootGroup);
        LootReferencesToItem(lootGroup, mergeItem);
        youkaisCompat(lootGroup)
    });
    clearLootTable([tableId])

    LootJS.lootTables(event => {
        entriesArray.forEach(([products, min, max, conditionCallback]) => {
            $G.addCustomLoot_1_21_1(event, tableId, products, min, max, conditionCallback);
        });
    });
};

$G.createLootTableConst_1_20_1 = function(tableId, entriesArray) {
    entriesArray.forEach(([lootGroup]) => {
        crateLootReferencesToItems(lootGroup);
        LootReferencesToItem(lootGroup, mergeItem);
        youkaisCompat(lootGroup)

        
    });
    clearLootTable([tableId])
    
    LootJS.modifiers(event => {
        entriesArray.forEach(([products, min, max, conditionCallback]) => {
            // ⭐ 核心修复：移到循环内部！每一次循环都创建一个独立的分支句柄
            let independentLoot = event.addLootTableModifier(tableId);
            
            // 将独立的句柄传进去，这样条件就会各自独立隔离
            $G.addCustomLoot_1_20_1(independentLoot, products, min, max, conditionCallback);
        });
    });
};