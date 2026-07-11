// 放入 server_scripts 文件夹下
$G.addCustomLoot_1_21_1 = function(event, lootTableId, config, minRolls, maxRolls, conditionJson) {
    // 1. 检查主战利品表是否存在
    if (!event.hasLootTable(lootTableId)) {
        return;
    }

    // =================================================================
    // 1.21.1 风格的键名条件映射表
    // =================================================================
    const CONDITION_MAP = {
        'matchTool': 'matchTool',
        'matchMainHand': 'matchTool', // 兼容旧版写法映射到新版
        'survivesExplosion': 'survivesExplosion',
        'randomChance': 'randomChance',
        'killedByPlayer': 'killedByPlayer',
        'matchBiome': 'matchBiome',
        'matchDimension': 'matchDimension',
        // 根据你 1.21.1 装的 LootJS 具体支持的 API 补充...
    };
    const CONDITION_KEYS = Object.keys(CONDITION_MAP);

    // =================================================================
    // 【升级：配置预清洗】提取组级参数与条件兜底
    // =================================================================
    let cleanConfig = [];

    config.forEach(group => {
        let cleanGroup = {
            groupName: group.groupName || "default",
            groupWeight: group.groupWeight || 1,
            min: group.min,
            max: group.max,
            // 提取组级参数
            damage: group.damage !== undefined ? group.damage : undefined,
            enchantChance: group.enchantChance !== undefined ? group.enchantChance : 0,
            enchantLevels: group.enchantLevels !== undefined ? group.enchantLevels : undefined,
            exactEnchants: group.exactEnchants !== undefined ? group.exactEnchants : undefined,
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
            group.items.forEach(item => {
                let isValid = false;

                if (item.id === 'empty' || item.type === 'empty' || item.empty === true) {
                    isValid = true; 
                } else if (item.reference || item.type === 'reference') {
                    let refId = item.reference || item.id;
                    if (event.hasLootTable(refId)) {
                        isValid = true;
                    } else {
                    }
                } else {
                    if (Item.exists(item.id)) {
                        isValid = true;
                    } else {
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

    // =================================================================
    // 2. 权重计算准备（固定基数 100000）
    // =================================================================
    const commonMultiplier = 100000;

    cleanConfig.forEach(group => {
        let totalRatio = 0;
        group.items.forEach(item => {
            totalRatio += (item.ratio !== undefined ? item.ratio : 1);
        });
        group._totalRatio = totalRatio;
    });

    // =================================================================
    // 3. 执行注入（应用组级参数与条件映射）
    // =================================================================
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
                } else {
                    entry = LootEntry.of(item.id);
                    isNormalItem = true;
                }

                // 3.1 计算并设置权重
                let itemWeight = (group.groupWeight * (item.ratio || 1) * commonMultiplier) / group._totalRatio;
                entry.withWeight(Math.round(itemWeight));

                // 3.2 注入【条件映射】（条目级优先，组级兜底）
                CONDITION_KEYS.forEach(key => {
                    let conditionValue = item[key] !== undefined ? item[key] : group.conditions[key];
                    
                    if (conditionValue !== undefined) {
                        let targetApi = CONDITION_MAP[key];
                        // 1.21.1 推荐直接在 entry 上链式调用条件 API
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

                // 3.3 数量合并逻辑
                let maxCount = item.max ?? group.max ?? 1;
                let minCount = (item.max !== undefined && group.min !== undefined && item.max < group.min) ? 0 : (item.min ?? group.min ?? 1);

                // 3.4 组级参数继承与合并（核心逻辑搬运）
                let finalDamage = item.damage ?? group.damage;
                let finalEnchantChance = item.enchantChance ?? group.enchantChance ?? 0;
                let finalEnchantLevels = item.enchantLevels ?? group.enchantLevels;
                let finalExactEnchants = item.exactEnchants ?? group.exactEnchants;

                if (isNormalItem) {
                    entry.setCount([minCount, maxCount]);
                    if (item.nbt) entry.setCustomData(item.nbt); // 1.21 变更为 setCustomData
                    if (item.jsonFunction) entry.jsonFunction(item.jsonFunction);
                    if (item.potion) entry.addPotion(item.potion);
                    
                    // 应用最终耐久伤害伤害
                    if (finalDamage !== undefined) {
                        entry.damage(finalDamage);
                    }
                    
                    // 应用最终附魔
                    if (finalEnchantChance > 0) {
                        if (finalEnchantLevels !== undefined && finalEnchantLevels !== null) {
                            entry.enchantWithLevels(finalEnchantLevels);
                        }
                        if (finalExactEnchants) {
                            entry.enchant(builder => {
                                for (let enchId in finalExactEnchants) {
                                    builder.withEnchantment(enchId, finalExactEnchants[enchId]);
                                }
                            });
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
        // ⭐【核心修改】如果传入了条件回调函数，将其安全地包裹在 pool.when() 中执行
        // 💡【优化后】将 JSON 条件注入到 1.21.1 的 pool 中
        if (conditionJson && typeof conditionJson === 'object') {
            pool.when(c => {
                const applyConditions = (ctx, json) => {
                    Object.keys(json).forEach(key => {
                        if (typeof ctx[key] === 'function') {
                            let val = json[key];
                            
                            // 针对 1.21.1 的 matchAnyOf / matchAllOf 复杂嵌套处理
                            if ((key === 'matchAnyOf' || key === 'matchAllOf') && Array.isArray(val)) {
                                ctx[key](subCtx => {
                                    val.forEach(subJson => applyConditions(subCtx, subJson));
                                });
                            } 
                            else if (Array.isArray(val)) {
                                ctx[key].apply(ctx, val);
                            } else {
                                ctx[key](val);
                            }
                        } else {
                        }
                    });
                };
                applyConditions(c, conditionJson);
            });
        }
    });
};