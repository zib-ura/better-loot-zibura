/**
 * 替换配置数组中所有 `reference` 占位符为实际可用的首选 Candidate ID
 * @param {Array} lootGroups - 战利品组大数组 (例如 village_savanna_crops)
 * @param {Array} config - 重复物品映射配置 (DUPLICATE_ITEMS_CONFIG)
 * @returns {Array} 返回修改后的原数组
 */
$G.LootReferencesToItem_1_21_1 = function(lootGroups, config) {
    // 建立一个快速查找映射表：{ "lootjs:lettuce": ["mod:lettuce1", "mod:lettuce2"] }
    const configMap = {};
    config.forEach(entry => {
        configMap[entry.result] = entry.candidates;
    });

    // 缓存已解析出的具体 ID，避免多次对同一个 item 进行性能消耗较高的有效性检测
    const resolvedCache = {};

    // 匹配包含 "_and_" 且以 "_crate" 结尾的正则（如: lootjs:white_grape_and_white_grape_block）
    const skipRegex = /^lootjs:([a-zA-Z0-9_]+)_and_\1_block$/;

    // 遍历每一个大组（例如：Crops & Agriculture）
    lootGroups.forEach(group => {
        if (!group.items || !Array.isArray(group.items)) return;

        // 遍历组内的具体物品定义
        group.items.forEach(item => {
            // 如果该物品定义包含 reference 属性
            if (item.reference) {
                const refId = item.reference;
                if (skipRegex.test(refId)) {
                    return; 
                }
                // 如果在去重配置文件中找到了对应的候选列表
                if (configMap[refId]) {
                    // 如果缓存里已经算过了，直接用
                    if (resolvedCache[refId]) {
                        item.id = resolvedCache[refId];
                        delete item.reference; // 移除原 reference 键
                    } else {
                        const candidates = configMap[refId];
                        let foundValidId = null;

                        // 核心选择逻辑：只选第一个可行的（在游戏中存在的）ID
                        for (let candidate of candidates) {
                            // KubeJS 检查物品是否注册且存在的标准方法
                            if (Item.exists(candidate)) {
                                foundValidId = candidate;
                                break; // 找到第一个可行的就立即跳出循环
                            }
                        }

                        // 如果找到了有效 ID，进行替换
                        if (foundValidId) {
                            item.id = foundValidId;
                            resolvedCache[refId] = foundValidId; // 存入缓存
                            delete item.reference; // 移除原 reference 键
                        } else {
                            // 【改动点 1】：找不到有效 ID 时，打上一个待删除标记
                            item.shouldRemove = true;
                        }
                    }
                } else {
                    // 【改动点 2】：如果连 configMap 里都找不到对应的暗号映射，也标记为删除
                    item.shouldRemove = true;
                }
            }
        });

        // 【改动点 3】：遍历完当前组的 items 后，物理物理过滤掉所有标记了删除的物品
        group.items = group.items.filter(item => !item.shouldRemove);
    });

    return lootGroups;
}

$G.LootReferencesToItem_1_20_1 = function(lootGroups, config) {
    // 建立一个快速查找映射表：{ "lootjs:lettuce": ["mod:lettuce1", "mod:lettuce2"] }
    const configMap = {};
    config.forEach(entry => {
        configMap[entry.result] = entry.candidates;
    });

    // 缓存已解析出的具体 ID，避免多次对同一个 item 进行性能消耗较高的有效性检测
    const resolvedCache = {};

    // 遍历每一个大组（例如：Crops & Agriculture）
    lootGroups.forEach(group => {
        if (!group.items || !Array.isArray(group.items)) return;

        // 遍历组内的具体物品定义
        group.items.forEach(item => {
            // 如果该物品定义包含 reference 属性
            if (item.reference) {
                const refId = item.reference;

                // 如果在去重配置文件中找到了对应的候选列表
                if (configMap[refId]) {
                    // 如果缓存里已经算过了，直接用
                    if (resolvedCache[refId]) {
                        item.id = resolvedCache[refId];
                        delete item.reference; // 移除原 reference 键
                    } else {
                        const candidates = configMap[refId];
                        let foundValidId = null;

                        // 核心选择逻辑：只选第一个可行的（在游戏中存在的）ID
                        for (let candidate of candidates) {
                            // KubeJS 检查物品是否注册且存在的标准方法
                            if (Item.of(candidate).isEmpty() === false) {
                                foundValidId = candidate;
                                break; // 找到第一个可行的就立即跳出循环
                            }
                        }

                        // 如果找到了有效 ID，进行替换
                        if (foundValidId) {
                            item.id = foundValidId;
                            resolvedCache[refId] = foundValidId; // 存入缓存
                            delete item.reference; // 移除原 reference 键
                        } else {
                            // 【改动点 1】：找不到有效 ID 时，打上一个待删除标记
                            item.shouldRemove = true;
                        }
                    }
                } else {
                    // 【改动点 2】：如果连 configMap 里都找不到对应的暗号映射，也标记为删除
                    item.shouldRemove = true;
                }
            }
        });

        // 【改动点 3】：遍历完当前组的 items 后，物理物理过滤掉所有标记了删除的物品
        group.items = group.items.filter(item => !item.shouldRemove);
    });

    return lootGroups;
}