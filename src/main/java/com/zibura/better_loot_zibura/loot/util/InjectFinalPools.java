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
//import net.minecraft.advancements.predicates.LocationPredicate;
//import net.minecraft.core.BlockPos;
//import net.minecraft.core.Holder;
//import net.minecraft.core.HolderSet;
//import net.minecraft.core.RegistryAccess;
//import net.minecraft.core.registries.BuiltInRegistries;
//import net.minecraft.core.registries.Registries;
//import net.minecraft.resources.ResourceKey;
//import net.minecraft.resources.Identifier;
//import net.minecraft.world.clock.WorldClocks;
//import net.minecraft.world.item.Item;
//import net.minecraft.world.level.biome.Biome;
//import net.minecraft.world.level.storage.loot.IntRangePredicate;
//import net.minecraft.world.level.storage.loot.LootPool;
//import net.minecraft.world.level.storage.loot.entries.*;
//import net.minecraft.world.level.storage.loot.functions.*;
//import net.minecraft.world.level.storage.loot.predicates.*;
//import net.minecraft.world.level.storage.loot.providers.number.floats.ContextFloatProviders;
//import net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProvider;
//import net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProviders;
//import net.neoforged.fml.ModList;
//import net.neoforged.neoforge.event.LootTableLoadEvent;
//import net.neoforged.neoforge.server.ServerLifecycleHooks;
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
//    // 不在配置加载阶段构建 LootPool。此时 Server/动态 Registry 可能尚未就绪，
//    // 会导致 matchTime 等依赖动态 Registry 的条件被静默跳过。
//    private record PendingPoolSpec(List<GroupDTO> config, int minRolls, int maxRolls, JsonObject conditions) {}
//
//    private static final Map<Identifier, List<PendingPoolSpec>> PENDING_POOL_SPECS = new ConcurrentHashMap<>();
//    private static final Map<Identifier, AtomicInteger> EXPECTED_POOL_COUNTS = new ConcurrentHashMap<>();
//
//    private InjectFinalPools() {}
//
//    public static void recordExpectedPool(String tableId) {
//        if (tableId == null) return;
//        Identifier targetLoc = Identifier.tryParse(tableId);
//        if (targetLoc != null) {
//            EXPECTED_POOL_COUNTS.computeIfAbsent(targetLoc, k -> new AtomicInteger(0)).incrementAndGet();
//        }
//    }
//
//    public static void injectLootPools(LootTableLoadEvent event) {
//        Identifier tableName = event.getName();
//        List<PendingPoolSpec> specs = PENDING_POOL_SPECS.get(tableName);
//
//        AtomicInteger expectedCounter = EXPECTED_POOL_COUNTS.get(tableName);
//        int expectedCount = (expectedCounter != null) ? expectedCounter.get() : 0;
//        int actualCount = 0;
//
//        // 关键修复：延迟到 LootTableLoadEvent 再构建。此时 Server 与动态 Registry 已可用，
//        // matchTime / biome / 自定义 codec 条件不会因为启动阶段 registryAccess == null 而消失。
//        if (specs != null && !specs.isEmpty()) {
//            for (PendingPoolSpec spec : specs) {
//                LootPool pool = buildLootPool(spec.config(), spec.minRolls(), spec.maxRolls(), spec.conditions());
//                if (pool != null) {
//                    event.getTable().addPool(pool);
//                    actualCount++;
//                }
//            }
//        }
//
//        if (actualCount != expectedCount) {
//            LOGGER.warn("[BetterLoot] 战利品表 {} 的池数量不匹配！预期配置了 {} 个池，但实际只构建成功了 {} 个池（丢失/过滤了 {} 个）！",
//                    tableName, expectedCount, actualCount, (expectedCount - actualCount));
//        }
//    }
//
//    public static void clearRegisteredPools() {
//        PENDING_POOL_SPECS.clear();
//        EXPECTED_POOL_COUNTS.clear();
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
////                if ("minecraft:book".equals(item.id) && item.enchantRandomly != null && item.enchantRandomly.size() == 1) {
////                    if (BuiltInRegistries.ITEM.containsKey(Identifier.parse("immersiveenchanting:ancient_book"))) {
////                        item.id = "immersiveenchanting:ancient_book";
////                    }
////                }
//
//                // 修复点 2：附魔合法性过滤校验
//                List<String> enchantRandomly = item.enchantRandomly != null ? item.enchantRandomly : cleanGroup.enchantRandomly;
//                if (enchantRandomly != null && !enchantRandomly.isEmpty()) {
//                    List<String> validEnchants = enchantRandomly.stream()
//                            .filter(InjectFinalPools::isEnchantmentValid)
//                            .toList();
//                    if (validEnchants.isEmpty()) continue;
//                    item.enchantRandomly = validEnchants;
//                }
//
//                String potion = item.potion != null ? item.potion : cleanGroup.potion;
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
//        if (tableId != null && !cleanConfig.isEmpty()) {
//            Identifier targetLoc = Identifier.parse(tableId);
//            JsonObject conditionsCopy = conditionJson != null ? conditionJson.deepCopy() : null;
//            PENDING_POOL_SPECS.computeIfAbsent(targetLoc, k -> new ArrayList<>())
//                    .add(new PendingPoolSpec(cleanConfig, minRolls, maxRolls, conditionsCopy));
//        }
//
//        // 池现在延迟到 LootTableLoadEvent 构建；当前调用方不依赖返回值。
//        return null;
//    }
//
//    private static LootPool buildLootPool(
//            List<GroupDTO> cleanConfig,
//            int minRolls,
//            int maxRolls,
//            JsonObject conditionJson
//    ) {
//        LootPool.Builder poolBuilder = LootPool.lootPool()
//                .setRolls(ContextIntProviders.between(minRolls, maxRolls));
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
//                UniformContainerBase.Builder<?> entryBuilder;
//                boolean isNormalItem = false;
//                boolean isEmptyItem = false;
//
//                if (item.empty || "empty".equals(item.id) || "empty".equals(item.type)) {
//                    entryBuilder = EmptyLootItem.emptyItem();
//                    isEmptyItem = true;
//                } else if (item.reference != null || "reference".equals(item.type)) {
//                    String refPath = item.reference != null ? item.reference : item.id;
//
//                    RegistryAccess registryAccess = getCurrentRegistryAccess();
//                    if (registryAccess == null) {
//                        continue;
//                    }
//
//                    var lootTableRegistry = registryAccess.lookupOrThrow(Registries.LOOT_TABLE);
//                    var lootTableHolder = lootTableRegistry.get(Identifier.parse(refPath));
//
//                    if (lootTableHolder.isEmpty()) {
//                        continue;
//                    }
//
//                    entryBuilder = NestedLootTable.lootTableReference(lootTableHolder.get());
//                } else {
//                    Identifier itemLoc = Identifier.parse(item.id);
//                    Item itemObj = BuiltInRegistries.ITEM.getValue(itemLoc);
//                    if (itemObj == null || itemObj == BuiltInRegistries.ITEM.getValue(BuiltInRegistries.ITEM.getDefaultKey())) {
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
//                if (!isEmptyItem) {
//                    entryBuilder.apply(
//                            SetItemCountFunction.setCount(
//                                    ContextIntProviders.between(minCount, maxCount)
//                            )
//                    );
//                }
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
//    private static void applyConditionsToPool(LootPool.Builder poolBuilder, JsonObject conditionJson) {
//        if (conditionJson.has("matchTime")) {
//            JsonArray timeParams = conditionJson.getAsJsonArray("matchTime");
//            long period = timeParams.get(0).getAsLong();
//            int minTime = timeParams.get(1).getAsInt();
//            int maxTime = timeParams.get(2).getAsInt();
//
//            RegistryAccess registryAccess = getCurrentRegistryAccess();
//            if (registryAccess != null) {
//                var clockRegistry = registryAccess.lookupOrThrow(Registries.WORLD_CLOCK);
//                var clockHolder = clockRegistry.get(WorldClocks.OVERWORLD);
//
//                if (clockHolder.isPresent()) {
//                    poolBuilder.when(
//                            TimeCheck.time(
//                                    clockHolder.get(),
//                                    IntRangePredicate.range(minTime, maxTime)
//                            ).setPeriod(period)
//                    );
//                } else {
//                    LOGGER.error("[BetterLoot] 无法获取 minecraft:overworld WorldClock，matchTime 未应用: period={}, range=[{},{}]",
//                            period, minTime, maxTime);
//                }
//            } else {
//                LOGGER.error("[BetterLoot] 构建 LootPool 时 Server RegistryAccess 仍不可用，matchTime 未应用: period={}, range=[{},{}]",
//                        period, minTime, maxTime);
//            }
//        }
//        if (conditionJson.has("survivesExplosion") && conditionJson.get("survivesExplosion").getAsBoolean()) {
//            poolBuilder.when(ExplosionCondition.survivesExplosion());
//        }
//        if (conditionJson.has("killedByPlayer") && conditionJson.get("killedByPlayer").getAsBoolean()) {
//            poolBuilder.when(LootItemKilledByPlayerCondition.killedByPlayer());
//        }
//
//        if (conditionJson.has("matchBiome")) {
////            JsonElement biomeElement = conditionJson.get("matchBiome");
////
////            if (biomeElement.isJsonArray()) {
////                JsonArray biomeArray = biomeElement.getAsJsonArray();
////                List<LootItemCondition.Builder> conditions = new ArrayList<>();
////
////                for (JsonElement elem : biomeArray) {
////                    Identifier biomeId = Identifier.parse(elem.getAsString());
////                    ResourceKey<Biome> biomeKey = ResourceKey.create(Registries.BIOME, biomeId);
////
////                    conditions.add(LocationCheck.checkLocation(
////                            LocationPredicate.Builder.location().setBiomes(
////                                    HolderSet.direct(Holder.Reference.createStandAlone(null, biomeKey))
////                            ),
////                            BlockPos.ZERO
////                    ));
////                }
////
////                if (!conditions.isEmpty()) {
////                    poolBuilder.when(AnyOfCondition.anyOf(conditions.toArray(new LootItemCondition.Builder[0])));
////                }
////            } else if (biomeElement.isJsonPrimitive()) {
////                Identifier biomeId = Identifier.parse(biomeElement.getAsString());
////                ResourceKey<Biome> biomeKey = ResourceKey.create(Registries.BIOME, biomeId);
////
////                poolBuilder.when(LocationCheck.checkLocation(
////                        LocationPredicate.Builder.location().setBiomes(
////                                HolderSet.direct(Holder.Reference.createStandAlone(null, biomeKey))
////                        ),
////                        BlockPos.ZERO
////                ));
////            }
//            if (conditionJson.has("matchBiome")) {
//                JsonElement biomeElement = conditionJson.get("matchBiome");
//
//                RegistryAccess registryAccess = getCurrentRegistryAccess();
//                if (registryAccess != null) {
//                    var biomeRegistry = registryAccess.lookupOrThrow(Registries.BIOME);
//
//                    if (biomeElement.isJsonArray()) {
//                        JsonArray biomeArray = biomeElement.getAsJsonArray();
//                        List<Holder<Biome>> biomes = new ArrayList<>();
//
//                        for (JsonElement elem : biomeArray) {
//                            Identifier biomeId = Identifier.tryParse(elem.getAsString());
//                            if (biomeId == null) continue;
//
//                            biomeRegistry.get(biomeId)
//                                    .ifPresent(biomes::add);
//                        }
//
//                        if (!biomes.isEmpty()) {
//                            poolBuilder.when(
//                                    LocationCheck.checkLocation(
//                                            LocationPredicate.Builder.location()
//                                                    .setBiomes(HolderSet.direct(biomes)),
//                                            BlockPos.ZERO
//                                    )
//                            );
//                        }
//
//                    } else if (biomeElement.isJsonPrimitive()) {
//                        Identifier biomeId = Identifier.tryParse(biomeElement.getAsString());
//
//                        if (biomeId != null) {
//                            biomeRegistry.get(biomeId).ifPresent(biomeHolder ->
//                                    poolBuilder.when(
//                                            LocationCheck.checkLocation(
//                                                    LocationPredicate.Builder.location()
//                                                            .setBiomes(HolderSet.direct(biomeHolder)),
//                                                    BlockPos.ZERO
//                                            )
//                                    )
//                            );
//                        }
//                    }
//                }
//            }
//        }
//
//        if (conditionJson.has("customCondition")) {
//            JsonElement customElem = conditionJson.get("customCondition");
//
//            if (customElem.isJsonArray()) {
//                for (JsonElement elem : customElem.getAsJsonArray()) {
//                    if (elem.isJsonObject()) {
//                        LootItemCondition.DIRECT_CODEC.parse(JsonOps.INSTANCE, elem)
//                                .resultOrPartial(err -> LOGGER.error("Failed to parse custom loot condition in array: {} | Error: {}", elem, err))
//                                .ifPresent(condition -> poolBuilder.when(() -> condition));
//                    }
//                }
//            } else if (customElem.isJsonObject()) {
//                LootItemCondition.DIRECT_CODEC.parse(JsonOps.INSTANCE, customElem)
//                        .resultOrPartial(err -> LOGGER.error("Failed to parse custom loot condition: {} | Error: {}", customElem, err))
//                        .ifPresent(condition -> poolBuilder.when(() -> condition));
//            }
//        }
//    }
//
//    private static void applyItemConditions(LootPoolEntryContainer.Builder<?> entryBuilder, ItemDTO item, GroupDTO group) {
//        if (item.randomChance != null) {
//            entryBuilder.when(LootItemRandomChanceCondition.randomChance(item.randomChance.floatValue()));
//        }
//    }
//
//    private static void applyItemFunctions(LootPoolEntryContainer.Builder<?> entryBuilder, ItemDTO item, GroupDTO group) {
//        // 耐久损伤
//        JsonElement damageElem = item.damage != null ? item.damage : group.damage;
//        if (damageElem != null && !damageElem.isJsonNull()) {
//            if (damageElem.isJsonPrimitive() && damageElem.getAsJsonPrimitive().isNumber()) {
//                entryBuilder.apply(SetItemDamageFunction.setDamage(
//                        ContextFloatProviders.exactly(damageElem.getAsFloat())
//                ));
//            } else if (damageElem.isJsonArray()) {
//                JsonArray dArr = damageElem.getAsJsonArray();
//
//                if (dArr.size() >= 2) {
//                    float minD = dArr.get(0).getAsFloat();
//                    float maxD = dArr.get(1).getAsFloat();
//
//                    entryBuilder.apply(SetItemDamageFunction.setDamage(
//                            ContextFloatProviders.between(minD, maxD)
//                    ));
//                } else if (dArr.size() == 1) {
//                    entryBuilder.apply(SetItemDamageFunction.setDamage(
//                            ContextFloatProviders.exactly(dArr.get(0).getAsFloat())
//                    ));
//                }
//            }
//        }
//
//        // 药水
//        String potionId = item.potion != null ? item.potion : group.potion;
//        if (potionId != null) {
//            Identifier potionLoc = Identifier.parse(potionId);
//            BuiltInRegistries.POTION.get(potionLoc)
//                    .ifPresent(potionHolder ->
//                            entryBuilder.apply(SetPotionFunction.setPotion(potionHolder))
//                    );
//        }
//
//        // 随机附魔等级
//        List<Integer> enchantLevels = item.enchantLevels != null ? item.enchantLevels : group.enchantLevels;
//        if (enchantLevels != null && enchantLevels.size() >= 2) {
//            Holder<ContextIntProvider> levels = ContextIntProviders.between(
//                    enchantLevels.get(0),
//                    enchantLevels.get(1)
//            );
//            entryBuilder.apply(new EnchantWithLevelsFunction.Builder(levels));
//        }
//
//        // 修复点 3：Immersive Enchanting 远古书与随机附魔逻辑
//        List<String> enchantRandomly = item.enchantRandomly != null ? item.enchantRandomly : group.enchantRandomly;
//        if (enchantRandomly != null && !enchantRandomly.isEmpty()) {
//
//            List<Identifier> enchantLocs = enchantRandomly.stream()
//                    .map(Identifier::tryParse)
//                    .filter(Objects::nonNull)
//                    .toList();
//
//            if (!enchantLocs.isEmpty()) {
//                // 使用自定义的延迟加载函数，将 Identifier 延迟到 LootContext 执行期再去查找 Registry
//                entryBuilder.apply(com.zibura.better_loot_zibura.loot.function.LazyEnchantRandomlyFunction.builder(enchantLocs));
//            }
//        }
//        // 自定义 Function
//        JsonElement funcElem = item.jsonFunction != null ? item.jsonFunction : group.jsonFunction;
//        if (funcElem != null && !funcElem.isJsonNull()) {
//            if (funcElem.isJsonArray()) {
//                for (JsonElement element : funcElem.getAsJsonArray()) {
//                    if (element.isJsonObject()) {
//                        LootItemFunctions.DIRECT_CODEC.parse(JsonOps.INSTANCE, element)
//                                .resultOrPartial(err -> LOGGER.error("Failed to parse loot function in array: {} | Error: {}", element, err))
//                                .ifPresent(fn -> entryBuilder.apply(() -> fn));
//                    }
//                }
//            } else if (funcElem.isJsonObject()) {
//                LootItemFunctions.DIRECT_CODEC.parse(JsonOps.INSTANCE, funcElem)
//                        .resultOrPartial(err -> LOGGER.error("Failed to parse single loot function: {} | Error: {}", funcElem, err))
//                        .ifPresent(fn -> entryBuilder.apply(() -> fn));
//            }
//        }
//    }
//
//    private static RegistryAccess getCurrentRegistryAccess() {
//        var server = ServerLifecycleHooks.getCurrentServer();
//        return server != null ? server.registryAccess() : null;
//    }
//
//    private static boolean isEnchantmentValid(String enchantId) {
//        Identifier rl = Identifier.tryParse(enchantId);
//        if (rl == null) return false;
//        RegistryAccess regAccess = getCurrentRegistryAccess();
//        if (regAccess != null) {
//            return regAccess.lookupOrThrow(Registries.ENCHANTMENT).containsKey(rl);
//        }
//        return true;
//    }
//
//    private static boolean isPotionValid(String potionId) {
//        Identifier rl = Identifier.tryParse(potionId);
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
//        Identifier rl = Identifier.tryParse(item.id);
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
//
//
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
import com.zibura.better_loot_zibura.loot.condition.SynchronizedSlotCondition;
import net.minecraft.advancements.predicates.LocationPredicate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.*;
import net.minecraft.world.level.storage.loot.functions.*;
import net.minecraft.world.level.storage.loot.predicates.*;
import net.minecraft.world.level.storage.loot.providers.number.floats.ContextFloatProviders;
import net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProvider;
import net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProviders;
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

    private static final Map<Identifier, List<LootPool>> PENDING_POOLS = new ConcurrentHashMap<>();
    private static final Map<Identifier, AtomicInteger> EXPECTED_POOL_COUNTS = new ConcurrentHashMap<>();

    private InjectFinalPools() {}

    public static void recordExpectedPool(String tableId) {
        if (tableId == null) return;
        Identifier targetLoc = Identifier.tryParse(tableId);
        if (targetLoc != null) {
            EXPECTED_POOL_COUNTS.computeIfAbsent(targetLoc, k -> new AtomicInteger(0)).incrementAndGet();
        }
    }

    public static void injectLootPools(LootTableLoadEvent event) {
        Identifier tableName = event.getName();
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
//                    if (BuiltInRegistries.ITEM.containsKey(Identifier.parse("immersiveenchanting:ancient_book"))) {
//                        item.id = "immersiveenchanting:ancient_book";
//                    }
//                }

                // 修复点 2：附魔合法性过滤校验
                List<String> enchantRandomly = item.enchantRandomly != null ? item.enchantRandomly : cleanGroup.enchantRandomly;
                if (enchantRandomly != null && !enchantRandomly.isEmpty()) {
                    List<String> validEnchants = enchantRandomly.stream()
                            .filter(InjectFinalPools::isEnchantmentValid)
                            .toList();
                    if (validEnchants.isEmpty()) continue;
                    item.enchantRandomly = validEnchants;
                }

                String potion = item.potion != null ? item.potion : cleanGroup.potion;
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
            Identifier targetLoc = Identifier.parse(tableId);
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
                .setRolls(ContextIntProviders.between(minRolls, maxRolls));

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

                UniformContainerBase.Builder<?> entryBuilder;
                boolean isNormalItem = false;
                boolean isEmptyItem = false;

                if (item.empty || "empty".equals(item.id) || "empty".equals(item.type)) {
                    entryBuilder = EmptyLootItem.emptyItem();
                    isEmptyItem = true;
                } else if (item.reference != null || "reference".equals(item.type)) {
                    String refPath = item.reference != null ? item.reference : item.id;

                    RegistryAccess registryAccess = getCurrentRegistryAccess();
                    if (registryAccess == null) {
                        continue;
                    }

                    var lootTableRegistry = registryAccess.lookupOrThrow(Registries.LOOT_TABLE);
                    var lootTableHolder = lootTableRegistry.get(Identifier.parse(refPath));

                    if (lootTableHolder.isEmpty()) {
                        continue;
                    }

                    entryBuilder = NestedLootTable.lootTableReference(lootTableHolder.get());
                } else {
                    Identifier itemLoc = Identifier.parse(item.id);
                    Item itemObj = BuiltInRegistries.ITEM.getValue(itemLoc);
                    if (itemObj == null || itemObj == BuiltInRegistries.ITEM.getValue(BuiltInRegistries.ITEM.getDefaultKey())) {
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

                if (!isEmptyItem) {
                    entryBuilder.apply(
                            SetItemCountFunction.setCount(
                                    ContextIntProviders.between(minCount, maxCount)
                            )
                    );
                }
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
            int minSlot = timeParams.get(1).getAsInt();
            int maxSlot = timeParams.get(2).getAsInt();

            // period == 1 时唯一槽 [0,0] 恒真，无需添加任何条件。
            // period > 1 时改为运行期读取 LootContext 所在世界的 gameTime，
            // 避免数据加载/Worker 线程中 ServerLifecycleHooks.getCurrentServer() == null。
            if (period > 1) {
                poolBuilder.when(SynchronizedSlotCondition.builder(period, minSlot, maxSlot));
            }
        }
        if (conditionJson.has("survivesExplosion") && conditionJson.get("survivesExplosion").getAsBoolean()) {
            poolBuilder.when(ExplosionCondition.survivesExplosion());
        }
        if (conditionJson.has("killedByPlayer") && conditionJson.get("killedByPlayer").getAsBoolean()) {
            poolBuilder.when(LootItemKilledByPlayerCondition.killedByPlayer());
        }

        if (conditionJson.has("matchBiome")) {
            JsonElement biomeElement = conditionJson.get("matchBiome");

            RegistryAccess registryAccess = getCurrentRegistryAccess();
            if (registryAccess != null) {
                var biomeRegistry = registryAccess.lookupOrThrow(Registries.BIOME);

                if (biomeElement.isJsonArray()) {
                    JsonArray biomeArray = biomeElement.getAsJsonArray();
                    List<Holder<Biome>> biomes = new ArrayList<>();

                    for (JsonElement elem : biomeArray) {
                        Identifier biomeId = Identifier.tryParse(elem.getAsString());
                        if (biomeId == null) continue;

                        biomeRegistry.get(biomeId)
                                .ifPresent(biomes::add);
                    }

                    if (!biomes.isEmpty()) {
                        poolBuilder.when(
                                LocationCheck.checkLocation(
                                        LocationPredicate.Builder.location()
                                                .setBiomes(HolderSet.direct(biomes)),
                                        BlockPos.ZERO
                                )
                        );
                    }

                } else if (biomeElement.isJsonPrimitive()) {
                    Identifier biomeId = Identifier.tryParse(biomeElement.getAsString());

                    if (biomeId != null) {
                        biomeRegistry.get(biomeId).ifPresent(biomeHolder ->
                                poolBuilder.when(
                                        LocationCheck.checkLocation(
                                                LocationPredicate.Builder.location()
                                                        .setBiomes(HolderSet.direct(biomeHolder)),
                                                BlockPos.ZERO
                                        )
                                )
                        );
                    }
                }
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

    private static void applyItemConditions(LootPoolEntryContainer.Builder<?> entryBuilder, ItemDTO item, GroupDTO group) {
        if (item.randomChance != null) {
            entryBuilder.when(LootItemRandomChanceCondition.randomChance(item.randomChance.floatValue()));
        }
    }

    private static void applyItemFunctions(LootPoolEntryContainer.Builder<?> entryBuilder, ItemDTO item, GroupDTO group) {
        // 耐久损伤
        JsonElement damageElem = item.damage != null ? item.damage : group.damage;
        if (damageElem != null && !damageElem.isJsonNull()) {
            if (damageElem.isJsonPrimitive() && damageElem.getAsJsonPrimitive().isNumber()) {
                entryBuilder.apply(SetItemDamageFunction.setDamage(
                        ContextFloatProviders.exactly(damageElem.getAsFloat())
                ));
            } else if (damageElem.isJsonArray()) {
                JsonArray dArr = damageElem.getAsJsonArray();

                if (dArr.size() >= 2) {
                    float minD = dArr.get(0).getAsFloat();
                    float maxD = dArr.get(1).getAsFloat();

                    entryBuilder.apply(SetItemDamageFunction.setDamage(
                            ContextFloatProviders.between(minD, maxD)
                    ));
                } else if (dArr.size() == 1) {
                    entryBuilder.apply(SetItemDamageFunction.setDamage(
                            ContextFloatProviders.exactly(dArr.get(0).getAsFloat())
                    ));
                }
            }
        }

        // 药水
        String potionId = item.potion != null ? item.potion : group.potion;
        if (potionId != null) {
            Identifier potionLoc = Identifier.parse(potionId);
            BuiltInRegistries.POTION.get(potionLoc)
                    .ifPresent(potionHolder ->
                            entryBuilder.apply(SetPotionFunction.setPotion(potionHolder))
                    );
        }

        // 随机附魔等级
        List<Integer> enchantLevels = item.enchantLevels != null ? item.enchantLevels : group.enchantLevels;
        if (enchantLevels != null && enchantLevels.size() >= 2) {
            Holder<ContextIntProvider> levels = ContextIntProviders.between(
                    enchantLevels.get(0),
                    enchantLevels.get(1)
            );
            entryBuilder.apply(new EnchantWithLevelsFunction.Builder(levels));
        }

        // 修复点 3：Immersive Enchanting 远古书与随机附魔逻辑
        List<String> enchantRandomly = item.enchantRandomly != null ? item.enchantRandomly : group.enchantRandomly;
        if (enchantRandomly != null && !enchantRandomly.isEmpty()) {

            List<Identifier> enchantLocs = enchantRandomly.stream()
                    .map(Identifier::tryParse)
                    .filter(Objects::nonNull)
                    .toList();

            if (!enchantLocs.isEmpty()) {
                // 使用自定义的延迟加载函数，将 Identifier 延迟到 LootContext 执行期再去查找 Registry
                entryBuilder.apply(com.zibura.better_loot_zibura.loot.function.LazyEnchantRandomlyFunction.builder(enchantLocs));
            }
        }
        // 自定义 Function
        JsonElement funcElem = item.jsonFunction != null ? item.jsonFunction : group.jsonFunction;
        if (funcElem != null && !funcElem.isJsonNull()) {
            if (funcElem.isJsonArray()) {
                for (JsonElement element : funcElem.getAsJsonArray()) {
                    if (element.isJsonObject()) {
                        LootItemFunctions.DIRECT_CODEC.parse(JsonOps.INSTANCE, element)
                                .resultOrPartial(err -> LOGGER.error("Failed to parse loot function in array: {} | Error: {}", element, err))
                                .ifPresent(fn -> entryBuilder.apply(() -> fn));
                    }
                }
            } else if (funcElem.isJsonObject()) {
                LootItemFunctions.DIRECT_CODEC.parse(JsonOps.INSTANCE, funcElem)
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
        Identifier rl = Identifier.tryParse(enchantId);
        if (rl == null) return false;
        RegistryAccess regAccess = getCurrentRegistryAccess();
        if (regAccess != null) {
            return regAccess.lookupOrThrow(Registries.ENCHANTMENT).containsKey(rl);
        }
        return true;
    }

    private static boolean isPotionValid(String potionId) {
        Identifier rl = Identifier.tryParse(potionId);
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
        Identifier rl = Identifier.tryParse(item.id);
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