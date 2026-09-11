package com.zibura.better_loot_zibura.loot.util;

import com.zibura.better_loot_zibura.loot.model.AllLevelModel.GroupDTO;
import com.zibura.better_loot_zibura.loot.model.AllLevelModel.ItemDTO;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.zibura.better_loot_zibura.loot.unification.ItemUnificationSolver;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.TagParser;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.storage.loot.Deserializers;
import net.minecraft.world.level.storage.loot.IntRange;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.EmptyLootItem;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer;
import net.minecraft.world.level.storage.loot.entries.LootTableReference;
import net.minecraft.world.level.storage.loot.functions.*;
import net.minecraft.world.level.storage.loot.predicates.*;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;


import net.minecraft.advancements.critereon.LocationPredicate;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.predicates.LocationCheck;

import static com.mojang.text2speech.Narrator.LOGGER;

/**
 * 战利品构建与事件直接挂载类
 */
public final class InjectFinalPools {

    private InjectFinalPools() {}

    private static final double COMMON_MULTIPLIER = 100000.0;

    /**
     * 存储各个战利品表 ID 对应要注入的 LootPool 列表
     */
    private static final Map<ResourceLocation, List<LootPool>> PENDING_POOLS = new ConcurrentHashMap<>();


    /**
     * 存储各个战利品表 ID 理论预期配置的池总数量
     */
    private static final Map<ResourceLocation, AtomicInteger> EXPECTED_POOL_COUNTS = new ConcurrentHashMap<>();

    /**
     * 记录某个 target 理论上应该生成的池数量（+1）
     */
    public static void recordExpectedPool(String tableId) {
        if (tableId == null) return;
        ResourceLocation targetLoc = ResourceLocation.tryParse(tableId);
        if (targetLoc != null) {
            EXPECTED_POOL_COUNTS.computeIfAbsent(targetLoc, k -> new AtomicInteger(0)).incrementAndGet();
        }
    }


    // ==========================================
    // 1. Forge 事件监听：加载战利品表时直接注入挂载
    // ==========================================

    public static void injectLootPools(LootTableLoadEvent event) {
        ResourceLocation tableName = event.getName();
        List<LootPool> pools = PENDING_POOLS.get(tableName);

        int actualCount = (pools != null) ? pools.size() : 0;
        AtomicInteger expectedCounter = EXPECTED_POOL_COUNTS.get(tableName);
        int expectedCount = (expectedCounter != null) ? expectedCounter.get() : 0;

        // 执行注入
        if (pools != null && !pools.isEmpty()) {
            for (LootPool pool : pools) {
                event.getTable().addPool(pool);
            }
        }

        // 仅在实际注入数与预期数不一致时输出警告
        if (actualCount != expectedCount) {
            System.err.println(String.format(
                    "[better_loot_zibura] [警告] 战利品表 %s 的池数量不匹配！预期配置了 %d 个池，但实际只构建成功了 %d 个池（丢失/过滤了 %d 个）！",
                    tableName, expectedCount, actualCount, (expectedCount - actualCount)
            ));
        }
//        else if (actualCount > 0) {
//            // 数量一致时正常打印（如果需要精简控制台，也可以只保留上面的错误警告）
//            System.out.println("[better_loot_zibura] 成功向 " + tableName + " 注入了 " + actualCount + " 个战利品池（符合预期）。");
//        }
    }

    /**
     * 清空已挂载缓存（用于重载配置时）
     */
    public static void clearRegisteredPools() {
        PENDING_POOLS.clear();
        EXPECTED_POOL_COUNTS.clear();
    }

    // ==========================================
    // 2. 战利品构建与注册入口
    // ==========================================

    /**
     * 构建战利品池并直接挂载到指定的战利品表 ID
     */
    public static LootPool addCustomLoot(
            String tableId,
            List<GroupDTO> configList,
            int minRolls,
            int maxRolls,
            JsonObject conditionJson
    ) {
        if (configList == null || configList.isEmpty()) return null;

        // 1. 数据预清洗与过滤
        List<GroupDTO> cleanConfig = new ArrayList<>();
        for (GroupDTO group : configList) {
            GroupDTO cleanGroup = new GroupDTO();
            cleanGroup.groupName = group.groupName != null ? group.groupName : "default";
            cleanGroup.groupWeight = group.groupWeight > 0 ? group.groupWeight : 1.0;
            cleanGroup.min = group.min;
            cleanGroup.max = group.max;
            cleanGroup.damage = group.damage;
            cleanGroup.enchantChance = group.enchantChance;
            cleanGroup.enchantLevels = group.enchantLevels;
            cleanGroup.exactEnchants = group.exactEnchants;
            cleanGroup.enchantRandomly = group.enchantRandomly;
            cleanGroup.potion = group.potion;
            cleanGroup.jsonFunction = group.jsonFunction;
            cleanGroup.conditions = group.conditions != null ? new HashMap<>(group.conditions) : new HashMap<>();

            double totalRatio = 0.0;
            for (ItemDTO rawItem : group.items) {
                ItemDTO item = copyItemConfig(rawItem);

//                // 1. 尝试使用统合配置筛选解析 reference 暗号
//                if (item.reference != null) {
//                    String resolvedId = ItemUnificationRegistry.resolveReference(item.reference);
//                    if (resolvedId != null) {
//                        item.id = resolvedId;
//                        item.reference = null; // 成功匹配，转换为普通物品
//                    } else if (!item.reference.contains(":")) {
//                        // 暗号无法解析且不是原生战利品表命名空间引用，作废该项
//                        continue;
//                    }
//                }


                if (item.reference != null) {
                    String resolvedId = ItemUnificationSolver.resolveReference(item.reference);
                    if (resolvedId != null) {
                        item.id = resolvedId;
                        item.reference = null; // 成功匹配，转换为普通物品
                    } else {
//                        // 如果是以 lootjs: 开头的统合暗号，但没找到任何已安装模组的对应物品，直接丢弃该条目
//                        if (item.reference.startsWith("lootjs:") || !item.reference.contains(":")) {
//                            continue;
//                        }
                        continue;
                    }
                }

                // 模组兼容性 ID 纠正
                item.id = normalizeItemId(item.id);

//                // 远古书转换兼容：单随机附魔书转为 immersiveenchanting:ancient_book
//                if ("minecraft:book".equals(item.id) && item.enchantRandomly != null && item.enchantRandomly.size() == 1) {
//                    if (ForgeRegistries.ITEMS.containsKey(ResourceLocation.parse("immersiveenchanting:ancient_book"))) {
//                        item.id = "immersiveenchanting:ancient_book";
//                    }
//                }

                // 附魔有效性过滤
                List<String> enchantRandomly = item.enchantRandomly != null ? item.enchantRandomly : group.enchantRandomly;
                if (enchantRandomly != null && !enchantRandomly.isEmpty()) {
                    List<String> validEnchants = enchantRandomly.stream()
                            .filter(InjectFinalPools::isEnchantmentValid)
                            .toList();
                    if (validEnchants.isEmpty()) continue;
                    item.enchantRandomly = validEnchants;
                }

                // 药水有效性过滤
                String potion = item.potion != null ? item.potion : group.potion;
                if (potion != null && !potion.isEmpty()) {
                    if (!isPotionValid(potion)) {
                        continue;
                    }
                    item.potion = potion;
                }

                if (isValidItem(item)) {
                    cleanGroup.items.add(item);
                    totalRatio += (item.ratio > 0 ? item.ratio : 1.0);
                }
            }

            if (totalRatio > 0) {
                cleanConfig.add(cleanGroup);
            }
        }

        // 2. 构建原生 LootPool
        LootPool lootPool = buildLootPool(cleanConfig, minRolls, maxRolls, conditionJson);

        // 3. 直接挂载入待注入池中
        if (lootPool != null && tableId != null) {
            ResourceLocation targetLoc = ResourceLocation.parse(tableId);
            PENDING_POOLS.computeIfAbsent(targetLoc, k -> new ArrayList<>()).add(lootPool);
        }

        return lootPool;
    }

    private static LootPool buildLootPool(
            List<GroupDTO> cleanConfig,
            int minRolls,
            int maxRolls,
            JsonObject conditionJson
    ) {
        LootPool.Builder poolBuilder = LootPool.lootPool()
                .setRolls(UniformGenerator.between(minRolls, maxRolls));

        // 挂载 Pool 级全局条件 (如 matchTime 等时间轴条件)
        if (conditionJson != null) {
            applyConditionsToPool(poolBuilder, conditionJson);
        }

        // 遍历所有清洗后的组与条目
        for (GroupDTO group : cleanConfig) {
            double groupTotalRatio = group.items.stream()
                    .mapToDouble(i -> i.ratio > 0 ? i.ratio : 1.0)
                    .sum();

            for (ItemDTO item : group.items) {
                double ratio = item.ratio > 0 ? item.ratio : 1.0;
                int weight = (int) Math.max(1, (group.groupWeight * ratio * COMMON_MULTIPLIER) / groupTotalRatio);

                LootPoolSingletonContainer.Builder<?> entryBuilder;
                boolean isNormalItem = false;

                // 1. 创建 Entry 基础对象
                if (item.empty || "empty".equals(item.id) || "empty".equals(item.type)) {
                    entryBuilder = EmptyLootItem.emptyItem();
                } else if (item.reference != null || "reference".equals(item.type)) {
                    String refPath = item.reference != null ? item.reference : item.id;
                    entryBuilder = LootTableReference.lootTableReference(ResourceLocation.parse(refPath));
                } else {
                    Item itemObj = ForgeRegistries.ITEMS.getValue(ResourceLocation.parse(item.id));
                    if (itemObj == null) continue;
                    entryBuilder = LootItem.lootTableItem(itemObj);
                    isNormalItem = true;
                }

                entryBuilder.setWeight(weight);

                // 2. 挂载 Entry 级条件 (randomChance 等)
                applyItemConditions(entryBuilder, item, group);

                // 3. 计算并挂载数量修饰函数
                int maxCount = item.max != null ? item.max : (group.max != null ? group.max : 1);
                int minCount = (item.max != null && group.min != null && item.max < group.min)
                        ? 0 : (item.min != null ? item.min : (group.min != null ? group.min : 1));

                entryBuilder.apply(SetItemCountFunction.setCount(UniformGenerator.between(minCount, maxCount)));

                // 4. 挂载物品专有属性函数（NBT、药水、附魔、耐久等）
                if (isNormalItem) {
                    applyItemFunctions(entryBuilder, item, group);
                }

                poolBuilder.add(entryBuilder);
            }
        }

        return poolBuilder.build();
    }

    // ==========================================
    // 3. 条件注入
    // ==========================================

    private static void applyConditionsToPool(LootPool.Builder poolBuilder, JsonObject conditionJson) {
        if (conditionJson.has("matchTime")) {
            JsonArray timeParams = conditionJson.getAsJsonArray("matchTime");
            long period = timeParams.get(0).getAsLong();
            int minTime = timeParams.get(1).getAsInt();
            int maxTime = timeParams.get(2).getAsInt();

            poolBuilder.when(TimeCheck.time(IntRange.range(minTime, maxTime)).setPeriod(period));
        }
        if (conditionJson.has("survivesExplosion") && conditionJson.get("survivesExplosion").getAsBoolean()) {
            poolBuilder.when(ExplosionCondition.survivesExplosion());
        }
        if (conditionJson.has("killedByPlayer") && conditionJson.get("killedByPlayer").getAsBoolean()) {
            poolBuilder.when(LootItemKilledByPlayerCondition.killedByPlayer());
        }
////        if (conditionJson.has("matchBiome")) {
////            String biomePattern = conditionJson.get("matchBiome").getAsString();
////            var builder = LocationPredicate.Builder.location();
////
////            if (biomePattern.startsWith("#")) {
////                // 注：1.20.1 原生 LocationPredicate 仅支持 ResourceKey，Tag 需去掉 '#' 按具体 key 处理
////                builder.setBiome(ResourceKey.create(Registries.BIOME, new ResourceLocation(biomePattern.substring(1))));
////            } else {
////                // 单个群系匹配：只传 1 个 ResourceKey 参数
////                builder.setBiome(ResourceKey.create(Registries.BIOME, new ResourceLocation(biomePattern)));
////            }
////
////            poolBuilder.when(LocationCheck.checkLocation(builder));
////        }
//        if (conditionJson.has("matchBiome")) {
//            String biomeStr = conditionJson.get("matchBiome").getAsString();
//            LocationPredicate.Builder locBuilder = LocationPredicate.Builder.location();
//
//            if (biomeStr.startsWith("#")) {
//                // 解析 Tag，例如 "#minecraft:is_forest"
//                ResourceLocation tagId = new ResourceLocation(biomeStr.substring(1));
//                TagKey<Biome> biomeTag = TagKey.create(Registries.BIOME, tagId);
//                locBuilder.setBiome(biomeTag);
//            } else {
//                // 解析单个 Biome，例如 "minecraft:plains"
//                ResourceLocation biomeId = new ResourceLocation(biomeStr);
//                ResourceKey<Biome> biomeKey = ResourceKey.create(Registries.BIOME, biomeId);
//                locBuilder.setBiome(biomeKey);
//            }
//
//            poolBuilder.when(LocationCheck.checkLocation(locBuilder));
//        }

        if (conditionJson.has("matchBiome")) {
            JsonElement biomeElement = conditionJson.get("matchBiome");

            if (biomeElement.isJsonArray()) {
                JsonArray biomeArray = biomeElement.getAsJsonArray();
                List<LootItemCondition.Builder> conditions = new ArrayList<>();

                for (JsonElement elem : biomeArray) {
                    ResourceLocation biomeId = ResourceLocation.parse(elem.getAsString());
                    ResourceKey<Biome> biomeKey = ResourceKey.create(Registries.BIOME, biomeId);

                    conditions.add(LocationCheck.checkLocation(
                            LocationPredicate.Builder.location().setBiome(biomeKey)
                    ));
                }

                if (!conditions.isEmpty()) {
                    poolBuilder.when(AnyOfCondition.anyOf(conditions.toArray(new LootItemCondition.Builder[0])));
                }
            } else if (biomeElement.isJsonPrimitive()) {
                ResourceLocation biomeId = ResourceLocation.parse(biomeElement.getAsString());
                ResourceKey<Biome> biomeKey = ResourceKey.create(Registries.BIOME, biomeId);

                poolBuilder.when(LocationCheck.checkLocation(
                        LocationPredicate.Builder.location().setBiome(biomeKey)
                ));
            }
        }

//        if (conditionJson.has("customCondition")) {
//            // 1.18.2 ~ 1.20.4 常用方法：通过全局序列化器解析
//            LootItemCondition condition = Deserializers.createConditionSerializer()
//                    .create()
//                    .fromJson(conditionJson, LootItemCondition.class);
//
//            if (condition != null) {
//                poolBuilder.when(() -> condition); // 或直接 poolBuilder.when(condition)
//            }
//        }

        // ==========================================
        // 3. 自定义条件 / 原生 Condition 解析
        //    (删除了顶层 condition，避免与 matchTime 等参数冲突；支持单个对象或数组)
        // ==========================================
        if (conditionJson.has("customCondition")) {
            JsonElement customElem = conditionJson.get("customCondition");
            Gson conditionGson = Deserializers.createConditionSerializer().create();

            // 情况 A：如果是数组，遍历并逐个 when()，原生的多个 when 本身就是 AND 关系
            if (customElem.isJsonArray()) {
                for (JsonElement elem : customElem.getAsJsonArray()) {
                    if (elem.isJsonObject()) {
                        try {
                            LootItemCondition condition = conditionGson.fromJson(elem, LootItemCondition.class);
                            if (condition != null) {
                                poolBuilder.when(() -> condition);
                            }
                        } catch (Exception e) {
                            LOGGER.error("Failed to parse custom loot condition in array: " + elem, e);
                        }
                    }
                }
            }
            // 情况 B：如果是单个对象
            else if (customElem.isJsonObject()) {
                try {
                    LootItemCondition condition = conditionGson.fromJson(customElem.getAsJsonObject(), LootItemCondition.class);
                    if (condition != null) {
                        poolBuilder.when(() -> condition);
                    }
                } catch (Exception e) {
                    LOGGER.error("Failed to parse custom loot condition: " + customElem, e);
                }
            }
        }


    }

    private static void applyItemConditions(LootPoolSingletonContainer.Builder<?> entryBuilder, ItemDTO item, GroupDTO group) {
        if (item.randomChance != null) {
            entryBuilder.when(LootItemRandomChanceCondition.randomChance(item.randomChance.floatValue()));
        }
    }

    // ==========================================
    // 4. 函数与属性修饰注入
    // ==========================================

    private static void applyItemFunctions(LootPoolSingletonContainer.Builder<?> entryBuilder, ItemDTO item, GroupDTO group) {
        // 1. NBT 写入
        JsonObject nbtJson = item.nbt != null ? item.nbt : (group.jsonFunction != null && group.jsonFunction.isJsonObject() ? group.jsonFunction.getAsJsonObject() : null);
        if (nbtJson != null) {
            try {
                CompoundTag tag = TagParser.parseTag(nbtJson.toString());
                entryBuilder.apply(SetNbtFunction.setTag(tag));
            } catch (CommandSyntaxException ignored) {}
        }
        // 2. 耐久损伤（兼容单值 0.5 与 区间 [0.1, 0.9]）
        JsonElement damageElem = item.damage != null ? item.damage : group.damage;
        if (damageElem != null && !damageElem.isJsonNull()) {
            if (damageElem.isJsonPrimitive() && damageElem.getAsJsonPrimitive().isNumber()) {
                // 单个数值
                entryBuilder.apply(SetItemDamageFunction.setDamage(
                        ConstantValue.exactly(damageElem.getAsFloat())
                ));
            } else if (damageElem.isJsonArray()) {
                // 数组区间 [min, max]
                JsonArray dArr = damageElem.getAsJsonArray();
                if (dArr.size() >= 2) {
                    float minD = dArr.get(0).getAsFloat();
                    float maxD = dArr.get(1).getAsFloat();
                    entryBuilder.apply(SetItemDamageFunction.setDamage(
                            UniformGenerator.between(minD, maxD)
                    ));
                } else if (dArr.size() == 1) {
                    entryBuilder.apply(SetItemDamageFunction.setDamage(
                            ConstantValue.exactly(dArr.get(0).getAsFloat())
                    ));
                }
            }
        }
//        // 2. 耐久损伤
//        Double damage = item.damage != null ? item.damage : group.damage;
//        if (damage != null) {
//            entryBuilder.apply(SetItemDamageFunction.setDamage(ConstantValue.exactly(damage.floatValue())));
//        }

        // 3. 药水类型注入 (potion)
        String potionId = item.potion != null ? item.potion : group.potion;
        if (potionId != null) {
            Potion potion = ForgeRegistries.POTIONS.getValue(ResourceLocation.parse(potionId));
            if (potion != null) {
                entryBuilder.apply(SetPotionFunction.setPotion(potion));
            }
        }

        // 4. 随机附魔等级 (enchantLevels)
        List<Integer> enchantLevels = item.enchantLevels != null ? item.enchantLevels : group.enchantLevels;
        if (enchantLevels != null && enchantLevels.size() >= 2) {
            entryBuilder.apply(EnchantWithLevelsFunction.enchantWithLevels(
                    UniformGenerator.between(enchantLevels.get(0), enchantLevels.get(1))
            ));
        }

        // 5. 随机附魔列表 (enchantRandomly)
        List<String> enchantRandomly = item.enchantRandomly != null ? item.enchantRandomly : group.enchantRandomly;
        if (enchantRandomly != null && !enchantRandomly.isEmpty()) {

            // ========================================================
            // Immersive Enchanting 兼容：针对远古书直接写入 StoredEnchantments
            // ========================================================
            if ("immersiveenchanting:ancient_book".equals(item.id)) {
                CompoundTag ancientBookTag = new CompoundTag();
                net.minecraft.nbt.ListTag enchantList = new net.minecraft.nbt.ListTag();

                for (String enchId : enchantRandomly) {
                    CompoundTag singleEnch = new CompoundTag();
                    singleEnch.putString("id", enchId);
                    singleEnch.putShort("lvl", (short) 1); // 远古书固定 1 级
                    enchantList.add(singleEnch);
                }

                ancientBookTag.put("StoredEnchantments", enchantList);
                entryBuilder.apply(SetNbtFunction.setTag(ancientBookTag));
            }
            // 原生普通物品/常规附魔书随机附魔逻辑
            else {
                EnchantRandomlyFunction.Builder enchantBuilder = EnchantRandomlyFunction.randomEnchantment();
                for (String enchId : enchantRandomly) {
                    Enchantment ench = ForgeRegistries.ENCHANTMENTS.getValue(ResourceLocation.parse(enchId));
                    if (ench != null) {
                        enchantBuilder.withEnchantment(ench);
                    }
                }
                entryBuilder.apply(enchantBuilder);
            }
        }

        // 6. 指定精准附魔 (exactEnchants)
        Map<String, Integer> exactEnchants = item.exactEnchants != null ? item.exactEnchants : group.exactEnchants;
        if (exactEnchants != null && !exactEnchants.isEmpty()) {
            CompoundTag enchantTag = new CompoundTag();
            CompoundTag storedEnchantTag = new CompoundTag();
            net.minecraft.nbt.ListTag enchantList = new net.minecraft.nbt.ListTag();

            for (Map.Entry<String, Integer> entry : exactEnchants.entrySet()) {
                CompoundTag singleEnch = new CompoundTag();
                singleEnch.putString("id", entry.getKey());
                singleEnch.putShort("lvl", entry.getValue().shortValue());
                enchantList.add(singleEnch);
            }

            // 区分普通物品与附魔书
            if ("minecraft:enchanted_book".equals(item.id)) {
                storedEnchantTag.put("StoredEnchantments", enchantList);
                entryBuilder.apply(SetNbtFunction.setTag(storedEnchantTag));
            } else {
                enchantTag.put("Enchantments", enchantList);
                entryBuilder.apply(SetNbtFunction.setTag(enchantTag));
            }
        }

//        Gson lootGson = Deserializers.createFunctionSerializer().create();
//        // 如果配置字段是 JsonArray 或支持多个
//        JsonArray functionsArray = item.jsonFunction != null ? item.jsonFunction : group.jsonFunction;
//        if (functionsArray != null) {
//            for (JsonElement element : functionsArray) {
//                if (element.isJsonObject()) {
//                    try {
//                        LootItemFunction lootFunction = lootGson.fromJson(element.getAsJsonObject(), LootItemFunction.class);
//                        if (lootFunction != null) {
//                            entryBuilder.apply(() -> lootFunction);
//                        }
//                    } catch (Exception e) {
//                        LOGGER.error("Failed to parse loot function in array: " + element, e);
//                    }
//                }
//            }
//        }

        // ========================================================
        // 7. 原生 Function 解析注入（兼容 JsonObject 单对象 与 JsonArray 数组）
        // ========================================================
        Gson lootGson = Deserializers.createFunctionSerializer().create();
        JsonElement funcElem = item.jsonFunction != null ? item.jsonFunction : group.jsonFunction;

        if (funcElem != null && !funcElem.isJsonNull()) {
            // 情况 A：如果是数组 [ { ... }, { ... } ]
            if (funcElem.isJsonArray()) {
                for (JsonElement element : funcElem.getAsJsonArray()) {
                    if (element.isJsonObject()) {
                        try {
                            LootItemFunction lootFunction = lootGson.fromJson(element.getAsJsonObject(), LootItemFunction.class);
                            if (lootFunction != null) {
                                entryBuilder.apply(() -> lootFunction);
                            }
                        } catch (Exception e) {
                            LOGGER.error("Failed to parse loot function in array: " + element, e);
                        }
                    }
                }
            }
            // 情况 B：如果是单个对象 { "function": "..." }
            else if (funcElem.isJsonObject()) {
                try {
                    LootItemFunction lootFunction = lootGson.fromJson(funcElem.getAsJsonObject(), LootItemFunction.class);
                    if (lootFunction != null) {
                        entryBuilder.apply(() -> lootFunction);
                    }
                } catch (Exception e) {
                    LOGGER.error("Failed to parse single loot function: " + funcElem, e);
                }
            }
        }
    }

    // ==========================================
    // 5. 校验与辅助方法
    // ==========================================

    private static boolean isEnchantmentValid(String enchantId) {
        ResourceLocation rl = ResourceLocation.tryParse(enchantId);
        return rl != null && ForgeRegistries.ENCHANTMENTS.containsKey(rl);
    }

    private static boolean isMobEffectValid(String effectId) {
        ResourceLocation rl = ResourceLocation.tryParse(effectId);
        return rl != null && ForgeRegistries.MOB_EFFECTS.containsKey(rl);
    }

    private static boolean isPotionValid(String potionId) {
        ResourceLocation rl = ResourceLocation.tryParse(potionId);
        return rl != null && ForgeRegistries.POTIONS.containsKey(rl);
    }

    private static String normalizeItemId(String id) {
        if (id == null) return null;

        // 1. 如果已加载 youkaishomecoming，将 youkaisfeasts:xxx 转为 youkaishomecoming:xxx
        if (ModList.get().isLoaded("youkaishomecoming") && id.startsWith("youkaisfeasts:")) {
            return "youkaishomecoming:" + id.substring("youkaisfeasts:".length());
        }

        // 2. 如果已加载 youkaisfeasts，将 youkaishomecoming:xxx 转为 youkaisfeasts:xxx
        if (ModList.get().isLoaded("youkaisfeasts") && id.startsWith("youkaishomecoming:")) {
            return "youkaisfeasts:" + id.substring("youkaishomecoming:".length());
        }

        return id;
    }

    private static boolean isValidItem(ItemDTO item) {
        if (item.empty || "empty".equals(item.id) || "empty".equals(item.type)) return true;
        if (item.reference != null || "reference".equals(item.type)) return true;
        return item.id != null && ForgeRegistries.ITEMS.containsKey(ResourceLocation.parse(item.id));
    }

    private static ItemDTO copyItemConfig(ItemDTO src) {
        ItemDTO copy = new ItemDTO();
        copy.id = src.id;
        copy.reference = src.reference;
        copy.type = src.type;
        copy.empty = src.empty;
        copy.ratio = src.ratio;
        copy.min = src.min;
        copy.max = src.max;
        copy.damage = src.damage;
        copy.randomChance = src.randomChance;
        copy.enchantLevels = src.enchantLevels;
        copy.exactEnchants = src.exactEnchants;
        copy.enchantRandomly = src.enchantRandomly;
        copy.jsonFunction = src.jsonFunction;
        copy.potion = src.potion;
        copy.nbt = src.nbt;
        copy.conditions = src.conditions != null ? new HashMap<>(src.conditions) : new HashMap<>();
        return copy;
    }
}

