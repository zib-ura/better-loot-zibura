$G.crateLootReferencesToItems_1_21_1 = function(lootGroups) {
}

$G.crateLootReferencesToItems_1_20_1 = function(lootGroups) {

    lootGroups.forEach(group => {
        group.items = group.items.map(item => {
            if (!item.reference) return item;

            // 1. 提取出 reference 里的基础名称
            let match = item.reference.match(/^lootjs:([a-zA-Z0-9_]+)_and_\1_crate$/);
            
            // 如果不符合这个特定格式，直接原样返回，不做任何替换
            if (!match) return item; 

            // 格式匹配成功，match[1] 就是提取出来的基础名称（例如 "cabbage"）
            let baseName = match[1];

            // 2. 去 finalFoodSupplyRegistry 查找配置
            let registryEntry = finalFoodSupplyRegistry.find(r => r.name === baseName);
            let finalId = null;

            if (registryEntry) {
                // --- 优先级 1：检查 crates 是否存在 ---
                if (registryEntry.crates && registryEntry.crates.length > 0) {
                    let targetCrateResult = registryEntry.crates[0];
                    
                    // 【改动点】：如果 crate 是 lootjs 开头，走 mergeItem 的候选遍历逻辑
                    if (targetCrateResult.startsWith("lootjs:")) {
                        let crateConfig = mergeItem.find(m => m.result === targetCrateResult);
                        
                        if (crateConfig && crateConfig.candidates) {
                            for (let i = 0; i < crateConfig.candidates.length; i++) {
                                if (!Item.of(crateConfig.candidates[i]).isEmpty()) {
                                    finalId = crateConfig.candidates[i];
                                    break; 
                                }
                            }
                        }
                    } else {
                        // 【新逻辑】：如果 crate 不是 lootjs 开头，直接检测物品本身是否存在
                        if (!Item.of(targetCrateResult).isEmpty()) {
                            finalId = targetCrateResult;
                        }
                    }
                }

                // --- 优先级 2：如果上面没找到（没有符合的箱子），查找 singles ---
                if (!finalId && registryEntry.singles && registryEntry.singles.length > 0) {
                    let targetSingle = registryEntry.singles[0];
                    
                    if (targetSingle.startsWith("lootjs:")) {
                        let singleConfig = mergeItem.find(m => m.result === targetSingle);
                        if (singleConfig && singleConfig.candidates) {
                            for (let i = 0; i < singleConfig.candidates.length; i++) {
                                if (!Item.of(singleConfig.candidates[i]).isEmpty()) {
                                    finalId = singleConfig.candidates[i];
                                    break;
                                }
                            }
                        }
                    } else {
                        // 如果单品不是 lootjs 开头（如 minecraft:potato），直接检测它在游戏中是否存在
                        if (!Item.of(targetSingle).isEmpty()) {
                            finalId = targetSingle;
                        }
                    }
                }
            }


            // 3. 构建新对象
            if (finalId) {
                // 找到了有效物品，只保留 ratio 并且赋予新的 id
                return {
                    id: finalId,
                    ratio: item.ratio
                };
            }
            return null; 
        }).filter(item => item !== null); // 把 null 彻底从数组里删掉
    });
}