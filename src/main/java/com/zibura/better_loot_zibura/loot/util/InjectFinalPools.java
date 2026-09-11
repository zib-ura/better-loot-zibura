//package com.zibura.better_loot_zibura.loot.util;
//
//import com.google.gson.JsonArray;
//import com.google.gson.JsonElement;
//import com.google.gson.JsonObject;
//import com.mojang.logging.LogUtils;
//import com.mojang.serialization.JsonOps;
//import com.zibura.better_loot_zibura.loot.model.AllLevelModel.GroupDTO;
//import com.zibura.better_loot_zibura.loot.model.AllLevelModel.ItemDTO;
//import com.zibura.better_loot_zibura.loot.unification.ItemUnificationSolver;
//import net.minecraft.advancements.critereon.LocationPredicate;
//import net.minecraft.core.BlockPos;
//import net.minecraft.core.Holder;
//import net.minecraft.core.HolderSet;
//import net.minecraft.core.registries.BuiltInRegistries;
//import net.minecraft.core.registries.Registries;
//import net.minecraft.resources.ResourceKey;
//import net.minecraft.resources.ResourceLocation;
//import net.minecraft.world.item.Item;
//import net.minecraft.world.item.alchemy.Potion;
//import net.minecraft.world.level.biome.Biome;
//import net.minecraft.world.level.storage.loot.IntRange;
//import net.minecraft.world.level.storage.loot.LootPool;
//import net.minecraft.world.level.storage.loot.entries.EmptyLootItem;
//import net.minecraft.world.level.storage.loot.entries.LootItem;
//import net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer;
//import net.minecraft.world.level.storage.loot.entries.NestedLootTable; // 1.21+ 替代 LootTableReference
//import net.minecraft.world.level.storage.loot.functions.*;
//import net.minecraft.world.level.storage.loot.predicates.*;
//import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
//import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
//import net.neoforged.fml.ModList;
//import net.neoforged.neoforge.event.LootTableLoadEvent;
//import org.slf4j.Logger;
//
//import java.util.*;
//import java.util.concurrent.ConcurrentHashMap;
//import java.util.concurrent.atomic.AtomicInteger;
//
//public final class InjectFinalPools {
//
//    private static final Logger LOGGER = LogUtils.getLogger();
//    private static final double COMMON_MULTIPLIER = 100000.0;
//
//    /**
//     * 存储各个战利品表 ID 对应要注入的 LootPool 列表
//     */
//    private static final Map<ResourceLocation, List<LootPool>> PENDING_POOLS = new ConcurrentHashMap<>();
//
//
//    /**
//     * 存储各个战利品表 ID 理论预期配置的池总数量
//     */
//    private static final Map<ResourceLocation, AtomicInteger> EXPECTED_POOL_COUNTS = new ConcurrentHashMap<>();
//
//    /**
//     * 记录某个 target 理论上应该生成的池数量（+1）
//     */
//    public static void recordExpectedPool(String tableId) {
//        if (tableId == null) return;
//        ResourceLocation targetLoc = ResourceLocation.tryParse(tableId);
//        if (targetLoc != null) {
//            EXPECTED_POOL_COUNTS.computeIfAbsent(targetLoc, k -> new AtomicInteger(0)).incrementAndGet();
//        }
//    }
//
//    private InjectFinalPools() {}
//
//    public static void injectLootPools(LootTableLoadEvent event) {
//        ResourceLocation tableName = event.getName();
//        List<LootPool> pools = PENDING_POOLS.get(tableName);
//        if (pools != null && !pools.isEmpty()) {
//            for (LootPool pool : pools) {
//                event.getTable().addPool(pool);
//            }
//            LOGGER.info("[BetterLoot] Successfully injected {} pools into {}", pools.size(), tableName);
//        }
//    }
//
//    public static void clearRegisteredPools() {
//        PENDING_POOLS.clear();
//    }
//
//    public static LootPool addCustomLoot(
//            String tableId,
//            List<GroupDTO> configList,
//            int minRolls,
//            int maxRolls,
//            JsonObject conditionJson
//    ) {
//        if (configList == null || configList.isEmpty()) return null;
//
//        List<GroupDTO> cleanConfig = new ArrayList<>();
//        for (GroupDTO group : configList) {
//            GroupDTO cleanGroup = new GroupDTO();
//            cleanGroup.groupName = group.groupName != null ? group.groupName : "default";
//            cleanGroup.groupWeight = group.groupWeight > 0 ? group.groupWeight : 1.0;
//            cleanGroup.min = group.min;
//            cleanGroup.max = group.max;
//            cleanGroup.damage = group.damage;
//            cleanGroup.enchantChance = group.enchantChance;
//            cleanGroup.enchantLevels = group.enchantLevels;
//            cleanGroup.exactEnchants = group.exactEnchants;
//            cleanGroup.enchantRandomly = group.enchantRandomly;
//            cleanGroup.potion = group.potion;
//            cleanGroup.jsonFunction = group.jsonFunction;
//            cleanGroup.conditions = group.conditions != null ? new HashMap<>(group.conditions) : new HashMap<>();
//            cleanGroup.components = group.components;
//            cleanGroup.nbt = group.nbt;
//
//            double totalRatio = 0.0;
//            for (ItemDTO rawItem : group.items) {
//                ItemDTO item = copyItemConfig(rawItem);
//
//                if (item.reference != null) {
//                    String resolvedId = ItemUnificationSolver.resolveReference(item.reference);
//                    if (resolvedId != null) {
//                        item.id = resolvedId;
//                        item.reference = null;
//                    } else {
//                        continue;
//                    }
//                }
//
//                item.id = normalizeItemId(item.id);
//
//                if ("minecraft:book".equals(item.id) && item.enchantRandomly != null && item.enchantRandomly.size() == 1) {
//                    if (BuiltInRegistries.ITEM.containsKey(ResourceLocation.parse("immersiveenchanting:ancient_book"))) {
//                        item.id = "immersiveenchanting:ancient_book";
//                    }
//                }
//
//                String potion = item.potion != null ? item.potion : group.potion;
//                if (potion != null && !potion.isEmpty()) {
//                    if (!isPotionValid(potion)) {
//                        continue;
//                    }
//                    item.potion = potion;
//                }
//
//                if (isValidItem(item)) {
//                    cleanGroup.items.add(item);
//                    totalRatio += (item.ratio > 0 ? item.ratio : 1.0);
//                }
//            }
//
//            if (totalRatio > 0) {
//                cleanConfig.add(cleanGroup);
//            }
//        }
//
//        LootPool lootPool = buildLootPool(cleanConfig, minRolls, maxRolls, conditionJson);
//
//        if (lootPool != null && tableId != null) {
//            ResourceLocation targetLoc = ResourceLocation.parse(tableId);
//            PENDING_POOLS.computeIfAbsent(targetLoc, k -> new ArrayList<>()).add(lootPool);
//        }
//
//        return lootPool;
//    }
//
//    private static LootPool buildLootPool(
//            List<GroupDTO> cleanConfig,
//            int minRolls,
//            int maxRolls,
//            JsonObject conditionJson
//    ) {
//        LootPool.Builder poolBuilder = LootPool.lootPool()
//                .setRolls(UniformGenerator.between(minRolls, maxRolls));
//
//        if (conditionJson != null) {
//            applyConditionsToPool(poolBuilder, conditionJson);
//        }
//
//        for (GroupDTO group : cleanConfig) {
//            double groupTotalRatio = group.items.stream()
//                    .mapToDouble(i -> i.ratio > 0 ? i.ratio : 1.0)
//                    .sum();
//
//            for (ItemDTO item : group.items) {
//                double ratio = item.ratio > 0 ? item.ratio : 1.0;
//                int weight = (int) Math.max(1, (group.groupWeight * ratio * COMMON_MULTIPLIER) / groupTotalRatio);
//
//                LootPoolSingletonContainer.Builder<?> entryBuilder;
//                boolean isNormalItem = false;
//
//                if (item.empty || "empty".equals(item.id) || "empty".equals(item.type)) {
//                    entryBuilder = EmptyLootItem.emptyItem();
//                } else if (item.reference != null || "reference".equals(item.type)) {
//                    String refPath = item.reference != null ? item.reference : item.id;
//                    // 1.21+ 使用 NestedLootTable 代替 LootTableReference
//                    entryBuilder = NestedLootTable.lootTableReference(
//                            ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.parse(refPath))
//                    );
//                } else {
//                    ResourceLocation itemLoc = ResourceLocation.parse(item.id);
//                    Item itemObj = BuiltInRegistries.ITEM.get(itemLoc);
//                    if (itemObj == null || itemObj == BuiltInRegistries.ITEM.get(BuiltInRegistries.ITEM.getDefaultKey())) {
//                        continue;
//                    }
//                    entryBuilder = LootItem.lootTableItem(itemObj);
//                    isNormalItem = true;
//                }
//
//                entryBuilder.setWeight(weight);
//                applyItemConditions(entryBuilder, item, group);
//
//                int maxCount = item.max != null ? item.max : (group.max != null ? group.max : 1);
//                int minCount = (item.max != null && group.min != null && item.max < group.min)
//                        ? 0 : (item.min != null ? item.min : (group.min != null ? group.min : 1));
//
//                entryBuilder.apply(SetItemCountFunction.setCount(UniformGenerator.between(minCount, maxCount)));
//
//                if (isNormalItem) {
//                    applyItemFunctions(entryBuilder, item, group);
//                }
//
//                poolBuilder.add(entryBuilder);
//            }
//        }
//
//        return poolBuilder.build();
//    }
//
//    // ==========================================
//    // 3. 条件注入
//    // ==========================================
//
//    private static void applyConditionsToPool(LootPool.Builder poolBuilder, JsonObject conditionJson) {
//        if (conditionJson.has("matchTime")) {
//            JsonArray timeParams = conditionJson.getAsJsonArray("matchTime");
//            long period = timeParams.get(0).getAsLong();
//            int minTime = timeParams.get(1).getAsInt();
//            int maxTime = timeParams.get(2).getAsInt();
//
//            poolBuilder.when(TimeCheck.time(IntRange.range(minTime, maxTime)).setPeriod(period));
//        }
//        if (conditionJson.has("survivesExplosion") && conditionJson.get("survivesExplosion").getAsBoolean()) {
//            poolBuilder.when(ExplosionCondition.survivesExplosion());
//        }
//        if (conditionJson.has("killedByPlayer") && conditionJson.get("killedByPlayer").getAsBoolean()) {
//            poolBuilder.when(LootItemKilledByPlayerCondition.killedByPlayer());
//        }
//
//        if (conditionJson.has("matchBiome")) {
//            JsonElement biomeElement = conditionJson.get("matchBiome");
//
//            if (biomeElement.isJsonArray()) {
//                JsonArray biomeArray = biomeElement.getAsJsonArray();
//                List<LootItemCondition.Builder> conditions = new ArrayList<>();
//
//                for (JsonElement elem : biomeArray) {
//                    ResourceLocation biomeId = ResourceLocation.parse(elem.getAsString());
//                    ResourceKey<Biome> biomeKey = ResourceKey.create(Registries.BIOME, biomeId);
//
//                    // 1.21.1 利用 Holder.Reference 虚拟占位或直接构造 LocationPredicate
//                    conditions.add(LocationCheck.checkLocation(
//                            LocationPredicate.Builder.location().setBiomes(
//                                    HolderSet.direct(Holder.Reference.createStandAlone(null, biomeKey))
//                            ),
//                            BlockPos.ZERO
//                    ));
//                }
//
//                if (!conditions.isEmpty()) {
//                    poolBuilder.when(AnyOfCondition.anyOf(conditions.toArray(new LootItemCondition.Builder[0])));
//                }
//            } else if (biomeElement.isJsonPrimitive()) {
//                ResourceLocation biomeId = ResourceLocation.parse(biomeElement.getAsString());
//                ResourceKey<Biome> biomeKey = ResourceKey.create(Registries.BIOME, biomeId);
//
//                poolBuilder.when(LocationCheck.checkLocation(
//                        LocationPredicate.Builder.location().setBiomes(
//                                HolderSet.direct(Holder.Reference.createStandAlone(null, biomeKey))
//                        ),
//                        BlockPos.ZERO
//                ));
//            }
//        }
//
////        if (conditionJson.has("customCondition")) {
////            LootItemCondition.DIRECT_CODEC.parse(JsonOps.INSTANCE, conditionJson.get("customCondition"))
////                    .resultOrPartial(LOGGER::error)
////                    .ifPresent(condition -> poolBuilder.when(() -> condition));
////        }
//
//        // ==========================================
//        // 3. 自定义条件 / 原生 Condition 解析 (1.21.1 Codec 实现)
//        // ==========================================
//        if (conditionJson.has("customCondition")) {
//            JsonElement customElem = conditionJson.get("customCondition");
//
//            // 情况 A：如果是数组，遍历并逐个 Codec 解码
//            if (customElem.isJsonArray()) {
//                for (JsonElement elem : customElem.getAsJsonArray()) {
//                    if (elem.isJsonObject()) {
//                        LootItemCondition.DIRECT_CODEC.parse(JsonOps.INSTANCE, elem)
//                                .resultOrPartial(err -> LOGGER.error("Failed to parse custom loot condition in array: {} | Error: {}", elem, err))
//                                .ifPresent(condition -> poolBuilder.when(() -> condition));
//                    }
//                }
//            }
//            // 情况 B：如果是单个对象
//            else if (customElem.isJsonObject()) {
//                LootItemCondition.DIRECT_CODEC.parse(JsonOps.INSTANCE, customElem)
//                        .resultOrPartial(err -> LOGGER.error("Failed to parse custom loot condition: {} | Error: {}", customElem, err))
//                        .ifPresent(condition -> poolBuilder.when(() -> condition));
//            }
//        }
//    }
//
//    private static void applyItemConditions(LootPoolSingletonContainer.Builder<?> entryBuilder, ItemDTO item, GroupDTO group) {
//        if (item.randomChance != null) {
//            entryBuilder.when(LootItemRandomChanceCondition.randomChance(item.randomChance.floatValue()));
//        }
//    }
//
//    // ==========================================
//    // 4. 函数与属性修饰注入
//    // ==========================================
//
//    private static void applyItemFunctions(LootPoolSingletonContainer.Builder<?> entryBuilder, ItemDTO item, GroupDTO group) {
////// 1. Data Components 注入
////        JsonObject compJson = item.components != null ? item.components : group.components;
////        if (compJson != null) {
////            JsonObject funcJson = new JsonObject();
////            funcJson.addProperty("function", "minecraft:set_components");
////            funcJson.add("components", compJson);
////
////            SetComponentsFunction.CODEC.parse(JsonOps.INSTANCE, funcJson)
////                    .resultOrPartial(LOGGER::error)
////                    .ifPresent(fn -> entryBuilder.apply(new LootItemFunction.Builder() {
////                        @Override
////                        public LootItemFunction build() {
////                            return fn;
////                        }
////                    }));
////        }
//
//        // 2. 耐久损伤
//        JsonElement damageElem = item.damage != null ? item.damage : group.damage;
//        if (damageElem != null && !damageElem.isJsonNull()) {
//            if (damageElem.isJsonPrimitive() && damageElem.getAsJsonPrimitive().isNumber()) {
//                entryBuilder.apply(SetItemDamageFunction.setDamage(
//                        ConstantValue.exactly(damageElem.getAsFloat())
//                ));
//            } else if (damageElem.isJsonArray()) {
//                JsonArray dArr = damageElem.getAsJsonArray();
//                if (dArr.size() >= 2) {
//                    float minD = dArr.get(0).getAsFloat();
//                    float maxD = dArr.get(1).getAsFloat();
//                    entryBuilder.apply(SetItemDamageFunction.setDamage(
//                            UniformGenerator.between(minD, maxD)
//                    ));
//                } else if (dArr.size() == 1) {
//                    entryBuilder.apply(SetItemDamageFunction.setDamage(
//                            ConstantValue.exactly(dArr.get(0).getAsFloat())
//                    ));
//                }
//            }
//        }
//
//        // 3. 药水类型注入
//        String potionId = item.potion != null ? item.potion : group.potion;
//        if (potionId != null) {
//            ResourceLocation potionLoc = ResourceLocation.parse(potionId);
//            if (BuiltInRegistries.POTION.containsKey(potionLoc)) {
//                Holder<Potion> potionHolder = BuiltInRegistries.POTION.wrapAsHolder(BuiltInRegistries.POTION.get(potionLoc));
//                entryBuilder.apply(SetPotionFunction.setPotion(potionHolder));
//            }
//        }
//
//        // 4. 附魔等级修饰 (适配 2 参数签名)
//        List<Integer> enchantLevels = item.enchantLevels != null ? item.enchantLevels : group.enchantLevels;
//        if (enchantLevels != null && enchantLevels.size() >= 2) {
//            UniformGenerator levels = UniformGenerator.between(enchantLevels.get(0), enchantLevels.get(1));
//            // 传入 levels 并设置默认附魔范围
//            entryBuilder.apply(new EnchantWithLevelsFunction.Builder(levels));
//        }
//
//        // 5. 随机附魔
//        List<String> enchantRandomly = item.enchantRandomly != null ? item.enchantRandomly : group.enchantRandomly;
//        if (enchantRandomly != null && !enchantRandomly.isEmpty()) {
//            entryBuilder.apply(EnchantRandomlyFunction.randomEnchantment());
//        }
//
//        // 6. 指定附魔
//        Map<String, Integer> exactEnchants = item.exactEnchants != null ? item.exactEnchants : group.exactEnchants;
//        if (exactEnchants != null && !exactEnchants.isEmpty()) {
//            SetEnchantmentsFunction.Builder enchBuilder = new SetEnchantmentsFunction.Builder();
//            entryBuilder.apply(enchBuilder);
//        }
//
////        // 7. 自定义 JSON Functions (使用 LootItemFunctions.ROOT_CODEC)
////        JsonArray functionsArray = item.jsonFunction != null ? item.jsonFunction : group.jsonFunction;
////        if (functionsArray != null) {
////            for (JsonElement element : functionsArray) {
////                if (element.isJsonObject()) {
////                    LootItemFunctions.ROOT_CODEC.parse(JsonOps.INSTANCE, element)
////                            .resultOrPartial(LOGGER::error)
////                            .ifPresent(fn -> entryBuilder.apply(new LootItemFunction.Builder() {
////                                @Override
////                                public LootItemFunction build() {
////                                    return fn;
////                                }
////                            }));
////                }
////            }
////        }
//
//        // 7. 自定义 JSON Functions (使用 LootItemFunctions.ROOT_CODEC 兼容对象与数组)
//        JsonElement funcElem = item.jsonFunction != null ? item.jsonFunction : group.jsonFunction;
//        if (funcElem != null && !funcElem.isJsonNull()) {
//            // 情况 A：数组格式 [ { ... }, { ... } ]
//            if (funcElem.isJsonArray()) {
//                for (JsonElement element : funcElem.getAsJsonArray()) {
//                    if (element.isJsonObject()) {
//                        LootItemFunctions.ROOT_CODEC.parse(JsonOps.INSTANCE, element)
//                                .resultOrPartial(err -> LOGGER.error("Failed to parse loot function in array: {} | Error: {}", element, err))
//                                .ifPresent(fn -> entryBuilder.apply(() -> fn));
//                    }
//                }
//            }
//            // 情况 B：单对象格式 { "function": "..." }
//            else if (funcElem.isJsonObject()) {
//                LootItemFunctions.ROOT_CODEC.parse(JsonOps.INSTANCE, funcElem)
//                        .resultOrPartial(err -> LOGGER.error("Failed to parse single loot function: {} | Error: {}", funcElem, err))
//                        .ifPresent(fn -> entryBuilder.apply(() -> fn));
//            }
//        }
//    }
//
//    // ==========================================
//    // 5. 校验与辅助方法
//    // ==========================================
//
//    private static boolean isPotionValid(String potionId) {
//        ResourceLocation rl = ResourceLocation.tryParse(potionId);
//        return rl != null && BuiltInRegistries.POTION.containsKey(rl);
//    }
//
//    private static String normalizeItemId(String id) {
//        if (id == null) return null;
//
//        if (ModList.get().isLoaded("youkaishomecoming") && id.startsWith("youkaisfeasts:")) {
//            return "youkaishomecoming:" + id.substring("youkaisfeasts:".length());
//        }
//
//        if (ModList.get().isLoaded("youkaisfeasts") && id.startsWith("youkaishomecoming:")) {
//            return "youkaisfeasts:" + id.substring("youkaishomecoming:".length());
//        }
//
//        return id;
//    }
//
//    private static boolean isValidItem(ItemDTO item) {
//        if (item.empty || "empty".equals(item.id) || "empty".equals(item.type)) return true;
//        if (item.reference != null || "reference".equals(item.type)) return true;
//        ResourceLocation rl = ResourceLocation.tryParse(item.id);
//        return rl != null && BuiltInRegistries.ITEM.containsKey(rl);
//    }
//
//    private static ItemDTO copyItemConfig(ItemDTO src) {
//        ItemDTO copy = new ItemDTO();
//        copy.id = src.id;
//        copy.reference = src.reference;
//        copy.type = src.type;
//        copy.empty = src.empty;
//        copy.ratio = src.ratio;
//        copy.min = src.min;
//        copy.max = src.max;
//        copy.damage = src.damage;
//        copy.randomChance = src.randomChance;
//        copy.enchantLevels = src.enchantLevels;
//        copy.exactEnchants = src.exactEnchants;
//        copy.enchantRandomly = src.enchantRandomly;
//        copy.jsonFunction = src.jsonFunction;
//        copy.potion = src.potion;
//        copy.components = src.components;
//        copy.nbt = src.nbt;
//        copy.conditions = src.conditions != null ? new HashMap<>(src.conditions) : new HashMap<>();
//        return copy;
//    }
//}


package com.zibura.better_loot_zibura.loot.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.JsonOps;
import com.zibura.better_loot_zibura.loot.model.AllLevelModel.GroupDTO;
import com.zibura.better_loot_zibura.loot.model.AllLevelModel.ItemDTO;
import com.zibura.better_loot_zibura.loot.unification.ItemUnificationSolver;
import net.minecraft.advancements.critereon.LocationPredicate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.storage.loot.IntRange;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.EmptyLootItem;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer;
import net.minecraft.world.level.storage.loot.entries.NestedLootTable;
import net.minecraft.world.level.storage.loot.functions.*;
import net.minecraft.world.level.storage.loot.predicates.*;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.event.LootTableLoadEvent;
import net.neoforged.neoforge.server.ServerLifecycleHooks;
import org.slf4j.Logger;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public final class InjectFinalPools {

    private static final Logger LOGGER = LogUtils.getLogger();
    private static final double COMMON_MULTIPLIER = 100000.0;

    private static final Map<ResourceLocation, List<LootPool>> PENDING_POOLS = new ConcurrentHashMap<>();
    private static final Map<ResourceLocation, AtomicInteger> EXPECTED_POOL_COUNTS = new ConcurrentHashMap<>();

    private InjectFinalPools() {}

    public static void recordExpectedPool(String tableId) {
        if (tableId == null) return;
        ResourceLocation targetLoc = ResourceLocation.tryParse(tableId);
        if (targetLoc != null) {
            EXPECTED_POOL_COUNTS.computeIfAbsent(targetLoc, k -> new AtomicInteger(0)).incrementAndGet();
        }
    }

    public static void injectLootPools(LootTableLoadEvent event) {
        ResourceLocation tableName = event.getName();
        List<LootPool> pools = PENDING_POOLS.get(tableName);

        int actualCount = (pools != null) ? pools.size() : 0;
        AtomicInteger expectedCounter = EXPECTED_POOL_COUNTS.get(tableName);
        int expectedCount = (expectedCounter != null) ? expectedCounter.get() : 0;

        if (pools != null && !pools.isEmpty()) {
            for (LootPool pool : pools) {
                event.getTable().addPool(pool);
            }
        }

        // 修复点 1：预期与实际注入池数量不匹配时发出警告日志
        if (actualCount != expectedCount) {
            LOGGER.warn("[BetterLoot] 战利品表 {} 的池数量不匹配！预期配置了 {} 个池，但实际只构建成功了 {} 个池（丢失/过滤了 {} 个）！",
                    tableName, expectedCount, actualCount, (expectedCount - actualCount));
        }
//        else if (actualCount > 0) {
//            LOGGER.info("[BetterLoot] Successfully injected {} pools into {}", actualCount, tableName);
//        }
    }

    public static void clearRegisteredPools() {
        PENDING_POOLS.clear();
        EXPECTED_POOL_COUNTS.clear();
    }

    public static LootPool addCustomLoot(
            String tableId,
            List<GroupDTO> configList,
            int minRolls,
            int maxRolls,
            JsonObject conditionJson
    ) {
        if (configList == null || configList.isEmpty()) return null;

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
            cleanGroup.components = group.components;
            cleanGroup.nbt = group.nbt;

            double totalRatio = 0.0;
            for (ItemDTO rawItem : group.items) {
                ItemDTO item = copyItemConfig(rawItem);

                if (item.reference != null) {
                    String resolvedId = ItemUnificationSolver.resolveReference(item.reference);
                    if (resolvedId != null) {
                        item.id = resolvedId;
                        item.reference = null;
                    } else {
                        continue;
                    }
                }

                item.id = normalizeItemId(item.id);

//                if ("minecraft:book".equals(item.id) && item.enchantRandomly != null && item.enchantRandomly.size() == 1) {
//                    if (BuiltInRegistries.ITEM.containsKey(ResourceLocation.parse("immersiveenchanting:ancient_book"))) {
//                        item.id = "immersiveenchanting:ancient_book";
//                    }
//                }

                // 修复点 2：附魔合法性过滤校验
                List<String> enchantRandomly = item.enchantRandomly != null ? item.enchantRandomly : group.enchantRandomly;
                if (enchantRandomly != null && !enchantRandomly.isEmpty()) {
                    List<String> validEnchants = enchantRandomly.stream()
                            .filter(InjectFinalPools::isEnchantmentValid)
                            .toList();
                    if (validEnchants.isEmpty()) continue;
                    item.enchantRandomly = validEnchants;
                }

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

        LootPool lootPool = buildLootPool(cleanConfig, minRolls, maxRolls, conditionJson);

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

        if (conditionJson != null) {
            applyConditionsToPool(poolBuilder, conditionJson);
        }

        for (GroupDTO group : cleanConfig) {
            double groupTotalRatio = group.items.stream()
                    .mapToDouble(i -> i.ratio > 0 ? i.ratio : 1.0)
                    .sum();

            for (ItemDTO item : group.items) {
                double ratio = item.ratio > 0 ? item.ratio : 1.0;
                int weight = (int) Math.max(1, (group.groupWeight * ratio * COMMON_MULTIPLIER) / groupTotalRatio);

                LootPoolSingletonContainer.Builder<?> entryBuilder;
                boolean isNormalItem = false;

                if (item.empty || "empty".equals(item.id) || "empty".equals(item.type)) {
                    entryBuilder = EmptyLootItem.emptyItem();
                } else if (item.reference != null || "reference".equals(item.type)) {
                    String refPath = item.reference != null ? item.reference : item.id;
                    entryBuilder = NestedLootTable.lootTableReference(
                            ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.parse(refPath))
                    );
                } else {
                    ResourceLocation itemLoc = ResourceLocation.parse(item.id);
                    Item itemObj = BuiltInRegistries.ITEM.get(itemLoc);
                    if (itemObj == null || itemObj == BuiltInRegistries.ITEM.get(BuiltInRegistries.ITEM.getDefaultKey())) {
                        continue;
                    }
                    entryBuilder = LootItem.lootTableItem(itemObj);
                    isNormalItem = true;
                }

                entryBuilder.setWeight(weight);
                applyItemConditions(entryBuilder, item, group);

                int maxCount = item.max != null ? item.max : (group.max != null ? group.max : 1);
                int minCount = (item.max != null && group.min != null && item.max < group.min)
                        ? 0 : (item.min != null ? item.min : (group.min != null ? group.min : 1));

                entryBuilder.apply(SetItemCountFunction.setCount(UniformGenerator.between(minCount, maxCount)));

                if (isNormalItem) {
                    applyItemFunctions(entryBuilder, item, group);
                }

                poolBuilder.add(entryBuilder);
            }
        }

        return poolBuilder.build();
    }

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

        if (conditionJson.has("matchBiome")) {
            JsonElement biomeElement = conditionJson.get("matchBiome");

            if (biomeElement.isJsonArray()) {
                JsonArray biomeArray = biomeElement.getAsJsonArray();
                List<LootItemCondition.Builder> conditions = new ArrayList<>();

                for (JsonElement elem : biomeArray) {
                    ResourceLocation biomeId = ResourceLocation.parse(elem.getAsString());
                    ResourceKey<Biome> biomeKey = ResourceKey.create(Registries.BIOME, biomeId);

                    conditions.add(LocationCheck.checkLocation(
                            LocationPredicate.Builder.location().setBiomes(
                                    HolderSet.direct(Holder.Reference.createStandAlone(null, biomeKey))
                            ),
                            BlockPos.ZERO
                    ));
                }

                if (!conditions.isEmpty()) {
                    poolBuilder.when(AnyOfCondition.anyOf(conditions.toArray(new LootItemCondition.Builder[0])));
                }
            } else if (biomeElement.isJsonPrimitive()) {
                ResourceLocation biomeId = ResourceLocation.parse(biomeElement.getAsString());
                ResourceKey<Biome> biomeKey = ResourceKey.create(Registries.BIOME, biomeId);

                poolBuilder.when(LocationCheck.checkLocation(
                        LocationPredicate.Builder.location().setBiomes(
                                HolderSet.direct(Holder.Reference.createStandAlone(null, biomeKey))
                        ),
                        BlockPos.ZERO
                ));
            }
        }

        if (conditionJson.has("customCondition")) {
            JsonElement customElem = conditionJson.get("customCondition");

            if (customElem.isJsonArray()) {
                for (JsonElement elem : customElem.getAsJsonArray()) {
                    if (elem.isJsonObject()) {
                        LootItemCondition.DIRECT_CODEC.parse(JsonOps.INSTANCE, elem)
                                .resultOrPartial(err -> LOGGER.error("Failed to parse custom loot condition in array: {} | Error: {}", elem, err))
                                .ifPresent(condition -> poolBuilder.when(() -> condition));
                    }
                }
            } else if (customElem.isJsonObject()) {
                LootItemCondition.DIRECT_CODEC.parse(JsonOps.INSTANCE, customElem)
                        .resultOrPartial(err -> LOGGER.error("Failed to parse custom loot condition: {} | Error: {}", customElem, err))
                        .ifPresent(condition -> poolBuilder.when(() -> condition));
            }
        }
    }

    private static void applyItemConditions(LootPoolSingletonContainer.Builder<?> entryBuilder, ItemDTO item, GroupDTO group) {
        if (item.randomChance != null) {
            entryBuilder.when(LootItemRandomChanceCondition.randomChance(item.randomChance.floatValue()));
        }
    }

    private static void applyItemFunctions(LootPoolSingletonContainer.Builder<?> entryBuilder, ItemDTO item, GroupDTO group) {
        // 耐久损伤
        JsonElement damageElem = item.damage != null ? item.damage : group.damage;
        if (damageElem != null && !damageElem.isJsonNull()) {
            if (damageElem.isJsonPrimitive() && damageElem.getAsJsonPrimitive().isNumber()) {
                entryBuilder.apply(SetItemDamageFunction.setDamage(
                        ConstantValue.exactly(damageElem.getAsFloat())
                ));
            } else if (damageElem.isJsonArray()) {
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

        // 药水
        String potionId = item.potion != null ? item.potion : group.potion;
        if (potionId != null) {
            ResourceLocation potionLoc = ResourceLocation.parse(potionId);
            if (BuiltInRegistries.POTION.containsKey(potionLoc)) {
                Holder<Potion> potionHolder = BuiltInRegistries.POTION.wrapAsHolder(BuiltInRegistries.POTION.get(potionLoc));
                entryBuilder.apply(SetPotionFunction.setPotion(potionHolder));
            }
        }

        // 随机附魔等级
        List<Integer> enchantLevels = item.enchantLevels != null ? item.enchantLevels : group.enchantLevels;
        if (enchantLevels != null && enchantLevels.size() >= 2) {
            UniformGenerator levels = UniformGenerator.between(enchantLevels.get(0), enchantLevels.get(1));
            entryBuilder.apply(new EnchantWithLevelsFunction.Builder(levels));
        }

        // 修复点 3：Immersive Enchanting 远古书与随机附魔逻辑
        List<String> enchantRandomly = item.enchantRandomly != null ? item.enchantRandomly : group.enchantRandomly;
        if (enchantRandomly != null && !enchantRandomly.isEmpty()) {
//            if ("immersiveenchanting:ancient_book".equals(item.id)) {
//                RegistryAccess regAccess = getCurrentRegistryAccess();
//                if (regAccess != null) {
//                    var enchantRegistry = regAccess.registryOrThrow(Registries.ENCHANTMENT);
//                    ItemEnchantments.Mutable enchantBuilder = new ItemEnchantments.Mutable(ItemEnchantments.EMPTY);
//
//                    for (String enchId : enchantRandomly) {
//                        ResourceLocation rl = ResourceLocation.tryParse(enchId);
//                        if (rl != null) {
//                            enchantRegistry.getHolder(ResourceKey.create(Registries.ENCHANTMENT, rl))
//                                    .ifPresent(holder -> enchantBuilder.set(holder, 1)); // 远古书固定 1 级
//                        }
//                    }
//
//                    // 精准注入 minecraft:stored_enchantments 组件
//                    entryBuilder.apply(SetComponentsFunction.setComponent(
//                            DataComponents.STORED_ENCHANTMENTS,
//                            enchantBuilder.toImmutable()
//                    ));
//                }
//            }
//            else {
//                EnchantRandomlyFunction.Builder enchantBuilder = EnchantRandomlyFunction.randomEnchantment();
//                RegistryAccess regAccess = getCurrentRegistryAccess();
//                if (regAccess != null) {
//                    var enchantRegistry = regAccess.registryOrThrow(Registries.ENCHANTMENT);
//                    for (String enchId : enchantRandomly) {
//                        ResourceLocation rl = ResourceLocation.tryParse(enchId);
//                        if (rl != null) {
//                            enchantRegistry.getHolder(ResourceKey.create(Registries.ENCHANTMENT, rl))
//                                    .ifPresent(enchantBuilder::withEnchantment);
//                        }
//                    }
//                }
//                entryBuilder.apply(enchantBuilder);
//            }
            List<ResourceLocation> enchantLocs = enchantRandomly.stream()
                    .map(ResourceLocation::tryParse)
                    .filter(Objects::nonNull)
                    .toList();

            if (!enchantLocs.isEmpty()) {
                // 使用自定义的延迟加载函数，将 ResourceLocation 延迟到 LootContext 执行期再去查找 Registry
                entryBuilder.apply(com.zibura.better_loot_zibura.loot.function.LazyEnchantRandomlyFunction.builder(enchantLocs));
            }
        }
        // 自定义 Function
        JsonElement funcElem = item.jsonFunction != null ? item.jsonFunction : group.jsonFunction;
        if (funcElem != null && !funcElem.isJsonNull()) {
            if (funcElem.isJsonArray()) {
                for (JsonElement element : funcElem.getAsJsonArray()) {
                    if (element.isJsonObject()) {
                        LootItemFunctions.ROOT_CODEC.parse(JsonOps.INSTANCE, element)
                                .resultOrPartial(err -> LOGGER.error("Failed to parse loot function in array: {} | Error: {}", element, err))
                                .ifPresent(fn -> entryBuilder.apply(() -> fn));
                    }
                }
            } else if (funcElem.isJsonObject()) {
                LootItemFunctions.ROOT_CODEC.parse(JsonOps.INSTANCE, funcElem)
                        .resultOrPartial(err -> LOGGER.error("Failed to parse single loot function: {} | Error: {}", funcElem, err))
                        .ifPresent(fn -> entryBuilder.apply(() -> fn));
            }
        }
    }

    private static RegistryAccess getCurrentRegistryAccess() {
        var server = ServerLifecycleHooks.getCurrentServer();
        return server != null ? server.registryAccess() : null;
    }

    private static boolean isEnchantmentValid(String enchantId) {
        ResourceLocation rl = ResourceLocation.tryParse(enchantId);
        if (rl == null) return false;
        RegistryAccess regAccess = getCurrentRegistryAccess();
        if (regAccess != null) {
            return regAccess.registryOrThrow(Registries.ENCHANTMENT).containsKey(rl);
        }
        return true;
    }

    private static boolean isPotionValid(String potionId) {
        ResourceLocation rl = ResourceLocation.tryParse(potionId);
        return rl != null && BuiltInRegistries.POTION.containsKey(rl);
    }

    private static String normalizeItemId(String id) {
        if (id == null) return null;

        if (ModList.get().isLoaded("youkaishomecoming") && id.startsWith("youkaisfeasts:")) {
            return "youkaishomecoming:" + id.substring("youkaisfeasts:".length());
        }

        if (ModList.get().isLoaded("youkaisfeasts") && id.startsWith("youkaishomecoming:")) {
            return "youkaisfeasts:" + id.substring("youkaishomecoming:".length());
        }

        return id;
    }

    private static boolean isValidItem(ItemDTO item) {
        if (item.empty || "empty".equals(item.id) || "empty".equals(item.type)) return true;
        if (item.reference != null || "reference".equals(item.type)) return true;
        ResourceLocation rl = ResourceLocation.tryParse(item.id);
        return rl != null && BuiltInRegistries.ITEM.containsKey(rl);
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
        copy.components = src.components;
        copy.nbt = src.nbt;
        copy.conditions = src.conditions != null ? new HashMap<>(src.conditions) : new HashMap<>();
        return copy;
    }
}