// 放入 server_scripts 文件夹下
$G.addCustomLoot_1_20_1 = function(lootTable, config, minRolls, maxRolls, conditionJson) {

    // =================================================================
    // 核心桥接：将 1.21.1 风格的键名动态映射并转换至 1.20.1 风格的 API
    // =================================================================
    const CONDITION_MAP = {
        // 基础物品与手持检查
        'matchTool': { api: 'matchMainHand', type: 'normal' }, 
        'matchMainHand': { api: 'matchMainHand', type: 'normal' },
        'matchOffHand': { api: 'matchOffHand', type: 'normal' },
        'matchEquip': { api: 'matchEquip', type: 'normal' },
        
        // 环境检查
        'survivesExplosion': { api: 'survivesExplosion', type: 'boolean' },
        'matchTime': { api: 'timeCheck', type: 'normal' }, 
        'matchWeather': { api: 'weatherCheck', type: 'weather' }, 
        'matchBiome': { api: 'biome', type: 'normal' }, 
        'matchDimension': { api: 'anyDimension', type: 'normal' }, 
        'matchStructure': { api: 'anyStructure', type: 'normal' }, 
        'isLightLevel': { api: 'lightLevel', type: 'normal' }, 
        
        // 几率检查
        'randomChance': { api: 'randomChance', type: 'normal' },
        'randomTableBonus': { api: 'randomChanceWithEnchantment', type: 'normal' }, 
        'randomChanceWithEnchantment': { api: 'randomChanceWithEnchantment', type: 'normal' },
        
        // 实体与击杀检查
        'killedByPlayer': { api: 'killedByPlayer', type: 'boolean' },
        'matchEntity': { api: 'matchEntity', type: 'callback' },
        'matchDirectAttacker': { api: 'matchDirectKiller', type: 'callback' }, 
        'matchAttacker': { api: 'matchKiller', type: 'callback' },
        'matchPlayer': { api: 'matchPlayer', type: 'callback' },
        
        // 自定义谓词检查
        'matchPlayerCustom': { api: 'playerPredicate', type: 'callback' },
        'matchEntityCustom': { api: 'entityPredicate', type: 'callback' },
        'matchAttackerCustom': { api: 'killerPredicate', type: 'callback' },
        'matchDirectAttackerCustom': { api: 'directKillerPredicate', type: 'callback' },
        'matchDamageSource': { api: 'matchDamageSource', type: 'callback' },
        'matchDistance': { api: 'distanceToKiller', type: 'normal' }, 
        
        // 逻辑容器与自定义
        'matchAllOf': { api: 'and', type: 'callback' }, 
        'matchAnyOf': { api: 'or', type: 'callback' },  
        'matchCustomCondition': { api: 'customCondition', type: 'normal' }
    };

    // 💡【核心重写】动态解析 1.21.1 风格的 JSON 条件并应用到 1.20.1 的 lootTable (Modifier) 上
    // 💡【优化后】动态解析 1.21.1 风格的 JSON 条件并应用到 1.20.1 的 lootTable (Modifier) 上
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
                // 如果是 matchAnyOf 或 matchAllOf，且传入的是数组
                else if ((key === 'matchAnyOf' || key === 'matchAllOf') && Array.isArray(conditionValue)) {
                    // 1.20.1 的 and/or 接收一个回调函数，里面用闭包继续链式调用
                    lootTable[targetApi](subCondition => {
                        conditionValue.forEach(subObj => {
                            // 递归解析子条件
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

    // ⭐【核心修复】1.20.1 的条件必须直接加在 Modifier（修改器）对象上，而不是通过 .when()
    if (typeof conditionJson === 'function') {
        conditionJson(lootTable);
    }

    // 使用 pool 创建一个新的独立池子
    lootTable.pool(pool => { 
        
        // 设置注入池的随机次数
        pool.rolls([minRolls, maxRolls]);
        
        const CONDITION_KEYS = Object.keys(CONDITION_MAP);

        // --- 1. 权重计算准备 与 配置预清洗 ---
        let commonMultiplier = 100000;


        let cleanConfig = [];

        config.forEach(group => {
            let totalRatio = 0;
            let cleanGroup = {
                groupName: group.groupName || "default",
                groupWeight: group.groupWeight || 1,
                min: group.min,
                max: group.max,
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
                    let isSpecial = item.id === 'empty' || item.type === 'empty' || item.empty === true || item.reference || item.type === 'reference';
                    if (isSpecial || Item.exists(item.id)) {
                        totalRatio += (item.ratio !== undefined ? item.ratio : 1);
                        cleanGroup.items.push(item);
                    } else {
                    }                
                });
            }

            cleanGroup._totalRatio = totalRatio;
            if (totalRatio > 0) {
                cleanConfig.push(cleanGroup);
            }
        });
        
        let entries = [];

        // --- 2. 遍历清洗后的配置并添加 Entry ---
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
                    entry = LootEntry.withChance(item.id, itemWeight);
                    isNormalItem = true;
                }

                // =============================================================
                // 【单项条目级条件映射注入】
                // =============================================================
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
                // =============================================================

                if (item.randomChance !== undefined) {
                    entry.when((c) => c.randomChance(item.randomChance));
                }

                let maxCount = item.max ?? group.max ?? 1;
                let minCount = (item.max !== undefined && group.min !== undefined && item.max < group.min) ? 0 : (item.min ?? group.min ?? 1);

                let finalDamage = item.damage ?? group.damage;
                let finalEnchantChance = item.enchantChance ?? group.enchantChance ?? 0;
                let finalEnchantLevels = item.enchantLevels ?? group.enchantLevels;
                let finalExactEnchants = item.exactEnchants ?? group.exactEnchants;

                if (isNormalItem) {
                    entry.limitCount([minCount, maxCount]);

                    if (item.nbt) entry.addNBT(item.nbt);
                    if (item.jsonfunction) entry.jsonFunction(item.jsonfunction); 
                    if (item.potion) entry.addPotion(item.potion);
                    
                    if (finalDamage !== undefined) {
                        entry.damage(finalDamage);
                    }
                    
                    if (finalEnchantChance > 0) {
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
