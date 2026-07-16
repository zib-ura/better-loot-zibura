$G.addCustomLoot_1_20_1 = function(lootTable, config, minRolls, maxRolls, conditionJson) {

    const CONDITION_MAP = {
        'matchTool': { api: 'matchMainHand', type: 'normal' }, 
        'matchMainHand': { api: 'matchMainHand', type: 'normal' },
        'matchOffHand': { api: 'matchOffHand', type: 'normal' },
        'matchEquip': { api: 'matchEquip', type: 'normal' },
        'survivesExplosion': { api: 'survivesExplosion', type: 'boolean' },
        'matchTime': { api: 'timeCheck', type: 'normal' }, 
        'matchWeather': { api: 'weatherCheck', type: 'weather' }, 
        'matchBiome': { api: 'biome', type: 'normal' }, 
        'matchDimension': { api: 'anyDimension', type: 'normal' }, 
        'matchStructure': { api: 'anyStructure', type: 'normal' }, 
        'isLightLevel': { api: 'lightLevel', type: 'normal' }, 
        'randomChance': { api: 'randomChance', type: 'normal' },
        'randomTableBonus': { api: 'randomChanceWithEnchantment', type: 'normal' }, 
        'randomChanceWithEnchantment': { api: 'randomChanceWithEnchantment', type: 'normal' },
        'killedByPlayer': { api: 'killedByPlayer', type: 'boolean' },
        'matchEntity': { api: 'matchEntity', type: 'callback' },
        'matchDirectAttacker': { api: 'matchDirectKiller', type: 'callback' }, 
        'matchAttacker': { api: 'matchKiller', type: 'callback' },
        'matchPlayer': { api: 'matchPlayer', type: 'callback' },
        'matchPlayerCustom': { api: 'playerPredicate', type: 'callback' },
        'matchEntityCustom': { api: 'entityPredicate', type: 'callback' },
        'matchAttackerCustom': { api: 'killerPredicate', type: 'callback' },
        'matchDirectAttackerCustom': { api: 'directKillerPredicate', type: 'callback' },
        'matchDamageSource': { api: 'matchDamageSource', type: 'callback' },
        'matchDistance': { api: 'distanceToKiller', type: 'normal' }, 
        'matchAllOf': { api: 'and', type: 'callback' }, 
        'matchAnyOf': { api: 'or', type: 'callback' },  
        'matchCustomCondition': { api: 'customCondition', type: 'normal' }
    };
    const CONDITION_KEYS = Object.keys(CONDITION_MAP);

    // =================================================================
    // 辅助函数：通过模组是否存在检测附魔有效性
    // =================================================================
    function isEnchantmentValid(enchantId) {
        if (!enchantId) return false;
        let parts = enchantId.split(':');
        if (parts.length < 2) return false;
        let modId = parts[0];
        // 如果是原版，直接保留，否则检测模组是否加载
        if (modId === 'minecraft') {
            return allAvailableEnchantments.includes(enchantId);
        }
        return Platform.isLoaded(modId);
    }
    function isPotionValid(potionId) {
        if (!potionId) return false;
        let parts = potionId.split(':');
        if (parts.length < 2) return false;
        let modId = parts[0];
        if (modId === 'minecraft') {
            // 这里替换成你全局存储的可用药水/效果列表，例如 allAvailablePotions
            return allAvailablePotions.includes(potionId);
        }
        return Platform.isLoaded(modId);
    }
    // =================================================================
    // 动态解析 1.21 风格的顶层条件到 modifier 上
    // =================================================================
    if (conditionJson && typeof conditionJson === 'object') {
        Object.keys(conditionJson).forEach(key => {
            if (CONDITION_MAP[key]) {
                let targetApi = CONDITION_MAP[key].api;
                let targetType = CONDITION_MAP[key].type;
                let conditionValue = conditionJson[key];

                if (targetType === 'boolean') {
                    if (conditionValue === true) lootTable[targetApi]();
                }
                else if (targetType === 'weather') {
                    if (Array.isArray(conditionValue)) {
                        let weatherObj = {};
                        if (conditionValue[0] !== null) weatherObj.raining = conditionValue[0];
                        if (conditionValue[1] !== null) weatherObj.thundering = conditionValue[1];
                        lootTable[targetApi](weatherObj);
                    } else {
                        lootTable[targetApi](conditionValue);
                    }
                }
                else if ((key === 'matchAnyOf' || key === 'matchAllOf') && Array.isArray(conditionValue)) {
                    lootTable[targetApi](subCondition => {
                        conditionValue.forEach(subObj => {
                            Object.keys(subObj).forEach(subKey => {
                                if (CONDITION_MAP[subKey]) {
                                    let subApi = CONDITION_MAP[subKey].api;
                                    if (Array.isArray(subObj[subKey])) {
                                        subCondition[subApi].apply(subCondition, subObj[subKey]);
                                    } else {
                                        subCondition[subApi](subObj[subKey]);
                                    }
                                }
                            });
                        });
                    });
                }
                else if (Array.isArray(conditionValue)) {
                    lootTable[targetApi].apply(lootTable, conditionValue);
                }
                else {
                    lootTable[targetApi](conditionValue);
                }
            }
        });
    }

    if (typeof conditionJson === 'function') {
        conditionJson(lootTable);
    }

    // =================================================================
    // 配置预清洗与有效性过滤合并
    // =================================================================
    lootTable.pool(pool => { 
        pool.rolls([minRolls, maxRolls]);
        
        let cleanConfig = [];

        // 建立去重引用映射表
        const configMap = {};
        if (typeof mergeItem !== 'undefined' && Array.isArray(mergeItem)) {
            mergeItem.forEach(entry => { configMap[entry.result] = entry.candidates; });
        }
        const resolvedCache = {};

        config.forEach(group => {
            let cleanGroup = {
                groupName: group.groupName || "default",
                groupWeight: group.groupWeight || 1,
                min: group.min,
                max: group.max,
                damage: group.damage !== undefined ? group.damage : undefined,
                enchantChance: group.enchantChance !== undefined ? group.enchantChance : 0,
                enchantLevels: group.enchantLevels !== undefined ? group.enchantLevels : undefined,
                exactEnchants: group.exactEnchants !== undefined ? group.exactEnchants : undefined,
                enchantRandomly: group.enchantRandomly !== undefined ? group.enchantRandomly : undefined,
                conditions: {},
                items: []
            };

            // 提取组级条件
            CONDITION_KEYS.forEach(key => {
                if (group[key] !== undefined) {
                    cleanGroup.conditions[key] = group[key];
                }
            });

            if (group.items) {
                group.items.forEach(rawItem => {
                    let item = Object.assign({}, rawItem);

                    // --- 步骤 1: 特殊 crateLootReferencesToItems 解析 ---
                    if (item.reference) {
                        let match = item.reference.match(/^lootjs:([a-zA-Z0-9_]+)_and_\1_block$/);
                        if (match) {
                            let baseName = match[1];
                            let registryEntry = (typeof finalFoodSupplyRegistry !== 'undefined') ? finalFoodSupplyRegistry.find(r => r.name === baseName) : null;
                            let finalId = null;

                            if (registryEntry) {
                                if (registryEntry.crates && registryEntry.crates.length > 0) {
                                    let targetCrateResult = registryEntry.crates[0];
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
                                    } else if (!Item.of(targetCrateResult).isEmpty()) {
                                        finalId = targetCrateResult;
                                    }
                                }
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
                                    } else if (!Item.of(targetSingle).isEmpty()) {
                                        finalId = targetSingle;
                                    }
                                }
                            }
                            if (finalId) {
                                item.id = finalId;
                                delete item.reference;
                            } else {
                                item = null; 
                            }
                        }
                    }

                    if (!item) return;

                    // --- 步骤 2: 妖怪之山兼容 ---
                    if (Platform.isLoaded('youkaishomecoming') && item.id && item.id.startsWith('youkaisfeasts:')) {
                        item.id = item.id.replace('youkaisfeasts:', 'youkaishomecoming:');
                    }

                    // --- 步骤 3: 解析一般物品引用 LootReferencesToItem ---
                    if (item.reference) {
                        let refId = item.reference;
                        if (configMap[refId]) {
                            if (resolvedCache[refId]) {
                                item.id = resolvedCache[refId];
                                delete item.reference;
                            } else {
                                let candidates = configMap[refId];
                                let foundValidId = null;
                                for (let candidate of candidates) {
                                    if (Item.of(candidate).isEmpty() === false) {
                                        foundValidId = candidate;
                                        break;
                                    }
                                }
                                if (foundValidId) {
                                    item.id = foundValidId;
                                    resolvedCache[refId] = foundValidId;
                                    delete item.reference;
                                }
                            }
                        }
                    }

                    // --- 步骤 4: 过滤并清理附魔列表 (Core Logic) ---
                    let finalEnchantRandomly = item.enchantRandomly ?? group.enchantRandomly;
                    if (finalEnchantRandomly && Array.isArray(finalEnchantRandomly)) {
                        // 清理不存在的附魔
                        let filteredEnchants = finalEnchantRandomly.filter(e => isEnchantmentValid(e));
                        if (filteredEnchants.length === 0) {
                            // 如果过滤完没有可用魔咒，直接抛弃此物品项
                            return;
                        }
                        item.enchantRandomly = filteredEnchants;
                    }

                    // --- 步骤 4 (药水版本): 过滤并清理药水列表 (兼容字符串与数组) ---
                    let finalPotion = item.potion ?? group.potion;
                    if (finalPotion) {
                        if (Array.isArray(finalPotion)) {
                            let filteredPotion = finalPotion.filter(p => isPotionValid(p));
                            if (filteredPotion.length === 0) {
                                return; // 如果无可用药水，抛弃此项
                            }
                            item.potion = filteredPotion;
                        } else if (typeof finalPotion === 'string') {
                            // 如果是单个字符串，直接检测其有效性
                            if (!isPotionValid(finalPotion)) {
                                return; // 如果药水无效，直接抛弃此物品项（不再加入池子）
                            }
                            item.potion = finalPotion;
                        }
                    }
                    // --- 步骤 5: 原书转远古书转换 (Core Logic) ---
                    if (item.id === "minecraft:book" && item.enchantRandomly && Array.isArray(item.enchantRandomly) && item.enchantRandomly.length === 1) {
                        if (Item.exists("immersiveenchanting:ancient_book")) {
                            item.id = "immersiveenchanting:ancient_book";
                        }
                    }

                    // --- 步骤 6: 判断物品/引用的有效性并推入配置组 ---
                    let isValid = false;

                    if (item.id === 'empty' || item.type === 'empty' || item.empty === true) {
                        isValid = true;
                    } else if (item.reference || item.type === 'reference') {
                        isValid = true; 
                    } else {
                        if (item.id && Item.exists(item.id)) {
                            isValid = true;
                        }
                    }

                    if (isValid) {
                        cleanGroup.items.push(item);
                    }
                });
            }

            let totalRatio = 0;
            cleanGroup.items.forEach(item => {
                totalRatio += (item.ratio !== undefined ? item.ratio : 1);
            });
            cleanGroup._totalRatio = totalRatio;

            if (totalRatio > 0) {
                cleanConfig.push(cleanGroup);
            }
        });

        // =================================================================
        // 构建 1.20 战利品项（使用 cleanConfig）
        // =================================================================
        let entries = [];
        let commonMultiplier = 100000;

        cleanConfig.forEach(group => {
            group.items.forEach(item => {
                let isSpecial = item.id === 'empty' || item.type === 'empty' || item.empty === true || item.reference || item.type === 'reference';
                let entry;
                let isNormalItem = false;
                let isReference = false;

                let itemWeight = (group.groupWeight * (item.ratio || 1) * commonMultiplier) / group._totalRatio;

                if (item.id === 'empty' || item.type === 'empty' || item.empty === true) {
                    entry = LootEntry.withChance({ "type": "minecraft:empty" } , itemWeight);
                } else if (item.reference || item.type === 'reference') {
                    entry = LootEntry.withChance({
                        "type": "minecraft:loot_table",
                        "name": item.reference || item.id
                    }, itemWeight);
                    isReference = true;
                } else {
                    // // 如果该项目是“书 + 恰好1个随机附魔”且转为了远古书
                    // if (item.id === "immersiveenchanting:ancient_book" && item.enchantRandomly && item.enchantRandomly.length === 1) {
                    //     let targetEnchant = item.enchantRandomly[0];
                    //     // 封装远古书，默认等级为 1
                    //     entry = LootEntry.withChance(Item.of("immersiveenchanting:ancient_book").withNBT({ StoredEnchantments: [{ id: targetEnchant, lvl: 1 }] }), itemWeight);
                    //     isNormalItem = true;
                    //     // 清理此处的附魔标记避免二次注入
                    //     delete item.enchantRandomly;
                    // } else {
                    //     entry = LootEntry.withChance(item.id, itemWeight);
                    //     isNormalItem = true;
                    // }
                    entry = LootEntry.withChance(item.id, itemWeight);
                    isNormalItem = true;
                }

                CONDITION_KEYS.forEach(key => {
                    let conditionValue = item[key] !== undefined ? item[key] : group.conditions[key];
                    if (conditionValue !== undefined) {
                        let targetApi = CONDITION_MAP[key].api;
                        let targetType = CONDITION_MAP[key].type;

                        entry.when(c => {
                            if (targetType === 'boolean') {
                                if (conditionValue === true) c[targetApi]();
                            }
                            else if (targetType === 'weather') {
                                if (Array.isArray(conditionValue)) {
                                    let weatherObj = {};
                                    if (conditionValue[0] !== null) weatherObj.raining = conditionValue[0];
                                    if (conditionValue[1] !== null) weatherObj.thundering = conditionValue[1];
                                    c[targetApi](weatherObj);
                                } else {
                                    c[targetApi](conditionValue);
                                }
                            }
                            else if (Array.isArray(conditionValue)) {
                                c[targetApi].apply(c, conditionValue);
                            }
                            else if (targetType === 'callback' || typeof conditionValue === 'function') {
                                c[targetApi](conditionValue);
                            }
                            else {
                                c[targetApi](conditionValue);
                            }
                        });
                    }
                });

                if (item.randomChance !== undefined) {
                    entry.when((c) => c.randomChance(item.randomChance));
                }

                let maxCount = item.max ?? group.max ?? 1;
                let minCount = (item.max !== undefined && group.min !== undefined && item.max < group.min) ? 0 : (item.min ?? group.min ?? 1);
                let finalDamage = item.damage ?? group.damage;
                let finalEnchantChance = item.enchantChance ?? group.enchantChance ?? 0;
                let finalEnchantLevels = item.enchantLevels ?? group.enchantLevels;
                let finalExactEnchants = item.exactEnchants ?? group.exactEnchants;
                let finalEnchantRandomly = item.enchantRandomly ?? group.enchantRandomly;

                // 新增：补全 nbt、jsonFunction 和 potion 的合并逻辑
                let finalNbt = item.nbt ?? group.nbt;
                let finalJsonFunction = item.jsonFunction ?? group.jsonFunction;
                let finalPotion = item.potion ?? group.potion;

                if (isNormalItem) {
                    entry.limitCount([minCount, maxCount]);
                    
                    if (finalNbt) entry.addNBT(finalNbt);
                    if (finalJsonFunction) {
                        entry.functions(ItemFilter.ALWAYS_TRUE, (f) => {
                            f.customFunction(finalJsonFunction);
                        });
                    }
                    if (finalPotion) entry.addPotion(finalPotion);
                


                    if (finalDamage !== undefined) entry.damage(finalDamage);
                    
                    if (finalEnchantLevels !== undefined && finalEnchantLevels !== null) {
                        if (Array.isArray(finalEnchantLevels)) {
                            entry.enchantWithLevels(finalEnchantLevels);
                        }
                    }
                    if (finalExactEnchants) {
                        entry.enchant(builder => {
                            for (let id in finalExactEnchants) {
                                builder.withEnchantment(id, finalExactEnchants[id]);
                            }
                        });
                    }
                    if (finalEnchantRandomly) {

                        // if (Array.isArray(finalEnchantRandomly)) {
                        //     // 原版物品或普通附魔书处理
                        //     if (item.id === "minecraft:book" || item.id === "minecraft:enchanted_book") {
                        //         // 拆分为有无等级进行构建
                        //         let withLevels = finalEnchantRandomly.filter(e => enchantmentsWithLevels.includes(e));
                        //         let withoutLevels = finalEnchantRandomly.filter(e => enchantmentsWithoutLevels.includes(e));
                                
                        //         // 此处若附魔混合，直接委托原版 enchantRandomly 分发
                        //         entry.enchantRandomly(finalEnchantRandomly);
                        //     } else {
                        //         entry.enchantRandomly(finalEnchantRandomly);
                        //     }
                        // } else {
                        //     entry.enchantRandomly(); 
                        // }
                        
                    // ==========================================
                    // 统一的有等级 / 无等级附魔注入模块 (1.20.1)
                    // ==========================================
                    if (finalEnchantRandomly) {
                        if (Array.isArray(finalEnchantRandomly)) {
                            let withLevels = finalEnchantRandomly.filter(e => enchantmentsWithLevels.includes(e));
                            let withoutLevels = finalEnchantRandomly.filter(e => enchantmentsWithoutLevels.includes(e));

                            // 1. 如果是远古书 (包括被自动清洗的项)，直接写入 NBT
                            if (item.id === "immersiveenchanting:ancient_book") {
                                let storedEnchants = [];
                                withLevels.forEach(e => storedEnchants.push({ id: e, lvl: 1 }));
                                withoutLevels.forEach(e => storedEnchants.push({ id: e, lvl: 1 }));
                                
                                if (storedEnchants.length > 0) {
                                    entry.addNBT({ StoredEnchantments: storedEnchants });
                                }
                            } 
                            // 2. 统一处理其他所有物品（包括原版书和普通装备）
                            else {
                                // 有等级附魔：利用原版/LootJS的随机附魔
                                if (withLevels.length > 0) {
                                    entry.enchantRandomly(withLevels);
                                }
                                // 无等级附魔：利用附魔构造器打上 1 级
                                if (withoutLevels.length > 0) {
                                    // let enchants = withoutLevels.map(e => ({ id: e, lvl: 1 }));
                                    // if (item.id === "minecraft:book") {
                                    //     entry.functions(ItemFilter.ALWAYS_TRUE, (f) => {
                                    //         f.customFunction({ "enchantments": ["minecraft:sharpness"], "function": "minecraft:enchant_randomly"});
                                    //     });
                                    //     // entry.addJson({ "enchantments": ["minecraft:swift_sneak"],"function": "minecraft:enchant_randomly"});
                                    // } else {
                                    //     entry.addNBT({ Enchantments: enchants });
                                    // }
                                    entry.functions(ItemFilter.ALWAYS_TRUE, (f) => {
                                        f.customFunction({ "enchantments": withoutLevels, "function": "minecraft:enchant_randomly"});
                                    });
                                }
                            }
                        } else {
                            entry.enchantRandomly(); 
                        }
                    }
                    }
                    
                }

                if (isReference) {
                    entry.limitCount([minCount, maxCount]);
                }
                
                entries.push(entry);
            });
        });

        if (entries.length > 0) {
            pool.addWeightedLoot(entries);
        }
    });
};