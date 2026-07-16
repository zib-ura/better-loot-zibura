$G.addCustomLoot_1_21_1 = function(event, lootTableId, config, minRolls, maxRolls, conditionJson) {
    if (!event.hasLootTable(lootTableId)) return;

    const CONDITION_MAP = {
        'matchTool': 'matchTool',
        'matchMainHand': 'matchTool', 
        'survivesExplosion': 'survivesExplosion',
        'randomChance': 'randomChance',
        'killedByPlayer': 'killedByPlayer',
        'matchBiome': 'matchBiome',
        'matchDimension': 'matchDimension',
        'matchTime': 'matchTime',
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
    // 配置预清洗与有效性过滤合并
    // =================================================================
    let cleanConfig = [];

    const configMap = {};
    if (typeof mergeItem !== 'undefined' && Array.isArray(mergeItem)) {
        mergeItem.forEach(entry => { configMap[entry.result] = entry.candidates; });
    }
    const resolvedCache = {};
    const skipRegex = /^lootjs:([a-zA-Z0-9_]+)_and_\1_block$/;

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

        CONDITION_KEYS.forEach(key => {
            if (group[key] !== undefined) {
                cleanGroup.conditions[key] = group[key];
            }
        });

        if (group.items) {
            group.items.forEach(rawItem => {
                let item = Object.assign({}, rawItem);

                if (Platform.isLoaded('youkaishomecoming') && item.id && item.id.startsWith('youkaisfeasts:')) {
                    item.id = item.id.replace('youkaisfeasts:', 'youkaishomecoming:');
                }

                if (item.reference) {
                    let refId = item.reference;
                    if (!skipRegex.test(refId)) {
                        if (configMap[refId]) {
                            if (resolvedCache[refId]) {
                                item.id = resolvedCache[refId];
                                delete item.reference;
                            } else {
                                let candidates = configMap[refId];
                                let foundValidId = null;
                                for (let candidate of candidates) {
                                    if (Item.exists(candidate)) {
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
                }

                // --- 步骤 1: 过滤并清理附魔列表 (Core Logic) ---
                let finalEnchantRandomly = item.enchantRandomly ?? group.enchantRandomly;
                if (finalEnchantRandomly && Array.isArray(finalEnchantRandomly)) {
                    let filteredEnchants = finalEnchantRandomly.filter(e => isEnchantmentValid(e));
                    if (filteredEnchants.length === 0) {
                        return; // 如果无可用魔咒，抛弃此项
                    }
                    item.enchantRandomly = filteredEnchants;
                }

                // --- 步骤 1 (药水版本): 过滤并清理药水列表 (Core Logic) ---
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
                // --- 步骤 2: 原书转远古书转换 (Core Logic) ---
                if (item.id === "minecraft:book" && item.enchantRandomly && Array.isArray(item.enchantRandomly) && item.enchantRandomly.length === 1) {
                    if (Item.exists("immersiveenchanting:ancient_book")) {
                        item.id = "immersiveenchanting:ancient_book";
                    }
                }

                let isValid = false;

                if (item.id === 'empty' || item.type === 'empty' || item.empty === true) {
                    isValid = true;
                } else if (item.reference || item.type === 'reference') {
                    let refId = item.reference || item.id;
                    if (event.hasLootTable(refId)) {
                        isValid = true;
                    }
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

        if (cleanGroup.items.length > 0) {
            cleanConfig.push(cleanGroup);
        }
    });

    if (cleanConfig.length === 0) return;

    const commonMultiplier = 100000;
    cleanConfig.forEach(group => {
        let totalRatio = 0;
        group.items.forEach(item => {
            totalRatio += (item.ratio !== undefined ? item.ratio : 1);
        });
        group._totalRatio = totalRatio;
    });

    let lootTable = event.getLootTable(lootTableId);
    lootTable.createPool(pool => {
        cleanConfig.forEach(group => {
            if (group._totalRatio === 0) return;

            group.items.forEach(item => {
                let entry;
                let isNormalItem = false;
                let isReference = false;

                if (item.id === 'empty' || item.type === 'empty' || item.empty === true) {
                    entry = LootEntry.empty();
                } else if (item.reference || item.type === 'reference') {
                    entry = LootEntry.reference(item.reference || item.id);
                    isReference = true;
                } 
                else {
                    // // 如果该项目是“书 + 恰好1个随机附魔”且转为了远古书
                    // if (item.id === "immersiveenchanting:ancient_book" && item.enchantRandomly && item.enchantRandomly.length === 1) {
                    //     let targetEnchant = item.enchantRandomly[0];
                    //     let levelsComponent = {};
                    //     levelsComponent[targetEnchant] = 1;
                    //     entry = LootEntry.of("immersiveenchanting:ancient_book").jsonFunction({
                    //         "function": "minecraft:set_components",
                    //         "components": {
                    //             "minecraft:stored_enchantments": {
                    //                 "levels": levelsComponent
                    //             }
                    //         }
                    //     })
                    //     // // 延迟在下方赋予 NBT (1.21.1 改为 setCustomData)
                    //     // item.nbt = Object.assign({}, item.nbt || {}, { StoredEnchantments: [{ id: targetEnchant, lvl: 1 }] });
                    //     // isNormalItem = true;
                    //     // delete item.enchantRandomly;
                    // } else {
                    //     entry = LootEntry.of(item.id);
                    //     isNormalItem = true;
                    // }
                        entry = LootEntry.of(item.id);
                        isNormalItem = true;                    
                }

                let itemWeight = (group.groupWeight * (item.ratio || 1) * commonMultiplier) / group._totalRatio;
                entry.withWeight(Math.round(itemWeight));

                CONDITION_KEYS.forEach(key => {
                    let conditionValue = item[key] !== undefined ? item[key] : group.conditions[key];
                    if (conditionValue !== undefined) {
                        let targetApi = CONDITION_MAP[key];
                        if (typeof entry[targetApi] === 'function') {
                            if (conditionValue === true) {
                                entry[targetApi]();
                            } else if (Array.isArray(conditionValue)) {
                                entry[targetApi].apply(entry, conditionValue);
                            } else {
                                entry[targetApi](conditionValue);
                            }
                        }
                    }
                });

                if (item.randomChance !== undefined) {
                    entry.randomChance(item.randomChance);
                }

                let maxCount = item.max ?? group.max ?? 1;
                let minCount = (item.max !== undefined && group.min !== undefined && item.max < group.min) ? 0 : (item.min ?? group.min ?? 1);

                let finalDamage = item.damage ?? group.damage;
                let finalEnchantChance = item.enchantChance ?? group.enchantChance ?? 0;
                let finalEnchantLevels = item.enchantLevels ?? group.enchantLevels;
                let finalExactEnchants = item.exactEnchants ?? group.exactEnchants;
                let finalEnchantRandomly = item.enchantRandomly ?? group.enchantRandomly;
                
                // 补全 nbt, jsonFunction 和 potion 的继承逻辑
                let finalNbt = item.nbt ?? group.nbt;
                let finalJsonFunction = item.jsonFunction ?? group.jsonFunction;
                let finalPotion = item.potion ?? group.potion;
                
                if (isNormalItem) {
                    entry.setCount([minCount, maxCount]);
                    
                    // 使用继承后的 final 变量进行判断和设置
                    if (finalNbt) entry.setCustomData(finalNbt); 
                    if (finalJsonFunction) entry.jsonFunction(finalJsonFunction);
                    if (finalPotion) entry.addPotion(finalPotion);
                    if (finalDamage !== undefined) entry.damage(finalDamage);
                    
                    if (finalEnchantLevels) {
                        entry.enchantWithLevels(finalEnchantLevels); 
                    }
                    if (finalExactEnchants) {
                        entry.enchant(builder => {
                            for (let enchId in finalExactEnchants) {
                                builder.withEnchantment(enchId, finalExactEnchants[enchId]);
                            }
                        });
                    }
                    // if (finalEnchantRandomly) {
                    //     if (Array.isArray(finalEnchantRandomly)) {
                    //         entry.enchantRandomly(finalEnchantRandomly); 
                    //     } else {
                    //         entry.enchantRandomly(); 
                    //     }
                    // }
                    
                    // ==========================================
                    // 统一的有等级 / 无等级附魔注入模块
                    // ==========================================
                    if (finalEnchantRandomly) {
                        if (Array.isArray(finalEnchantRandomly)) {
                            let withLevels = finalEnchantRandomly.filter(e => enchantmentsWithLevels.includes(e));
                            let withoutLevels = finalEnchantRandomly.filter(e => enchantmentsWithoutLevels.includes(e));

                            // 1. 如果是远古书 (包括被自动清洗的项)，使用特定的 StoredEnchantments NBT 写入方式
                            if (item.id === "immersiveenchanting:ancient_book") {
                                let levelsComponent = {};
                                
                                // 将有等级和无等级的附魔全部存入 levelsComponent 对象中，等级默认为 1
                                withLevels.forEach(e => { levelsComponent[e] = 1; });
                                withoutLevels.forEach(e => { levelsComponent[e] = 1; });
                                
                                // 只有在存在附魔时才创建并塞入 entries
                                if (Object.keys(levelsComponent).length > 0) {
                                    entry.jsonFunction({
                                            "function": "minecraft:set_components",
                                            "components": {
                                                "minecraft:stored_enchantments": {
                                                    "levels": levelsComponent
                                                }
                                            }
                                        })
                                    
                                }
                            }
                            // 2. 统一处理其他所有物品（包括原版附魔书、武器、工具与装备）
                            else {
                                // 有等级附魔：直接使用原版/LootJS的随机附魔
                                if (withLevels.length > 0) {
                                    entry.enchantRandomly(withLevels);
                                }
                                // 无等级附魔：通过附魔构造器将其强制以 1 级打在物品上
                                if (withoutLevels.length > 0) {
                                    entry.enchant(builder => {
                                        withoutLevels.forEach(e => {
                                            builder.withEnchantment(e, 1);
                                        });
                                    });
                                }
                            }
                        } else {
                            entry.enchantRandomly(); 
                        }
                    }
                }

                if (isReference) {
                    entry.setCount([minCount, maxCount]);
                }

                pool.addEntry(entry);
            });
        });

        pool.rolls([minRolls, maxRolls]);

        if (conditionJson && typeof conditionJson === 'object') {
            pool.when(c => {
                const applyConditions = (ctx, json) => {
                    Object.keys(json).forEach(key => {
                        let actualApiKey = CONDITION_MAP[key] || key;
                        if (typeof ctx[actualApiKey] === 'function') {
                            let val = json[key];
                            if ((actualApiKey === 'matchAnyOf' || actualApiKey === 'matchAllOf') && Array.isArray(val)) {
                                ctx[actualApiKey](subCtx => {
                                    val.forEach(subJson => applyConditions(subCtx, subJson));
                                });
                            } 
                            else if (Array.isArray(val)) {
                                ctx[actualApiKey].apply(ctx, val);
                            } else {
                                ctx[actualApiKey](val);
                            }
                        }
                    });
                };
                applyConditions(c, conditionJson);
            });
        } else if (typeof conditionJson === 'function') {
            pool.when(conditionJson);
        }
    });
};