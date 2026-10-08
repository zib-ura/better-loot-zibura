package com.zibura.better_loot_zibura.loot.util;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.zibura.better_loot_zibura.loot.model.AllLevelModel.GroupDTO;
import com.zibura.better_loot_zibura.loot.model.AllLevelModel.ItemDTO;
import com.zibura.better_loot_zibura.loot.unification.ItemUnificationSolver;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.loot.LootDataType;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.condition.*;
import net.minecraft.loot.entry.EmptyEntry;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.entry.LeafEntry;
import net.minecraft.loot.entry.LootTableEntry;
import net.minecraft.loot.function.*;
import net.minecraft.loot.operator.BoundedIntUnaryOperator;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.StringNbtReader;
import net.minecraft.potion.Potion;
import net.minecraft.predicate.entity.LocationPredicate;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 战利品构建与 Fabric 事件挂载类 (1.20.1 Yarn)
 */
public final class InjectFinalPools {

    private InjectFinalPools() {}

    private static final Logger LOGGER = LoggerFactory.getLogger("better_loot_zibura");
    private static final double COMMON_MULTIPLIER = 100000.0;

    /**
     * 存储各个战利品表 ID 对应要注入的 LootPool 列表
     */
    private static final Map<Identifier, List<LootPool>> PENDING_POOLS = new ConcurrentHashMap<>();

    /**
     * 存储各个战利品表 ID 理论预期配置的池总数量
     */
    private static final Map<Identifier, AtomicInteger> EXPECTED_POOL_COUNTS = new ConcurrentHashMap<>();

    /**
     * 在 Mod 初始化时调用一次该方法完成事件注册
     */
    public static void init() {
        LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, source) -> {
            List<LootPool> pools = PENDING_POOLS.get(id);

            int actualCount = (pools != null) ? pools.size() : 0;
            AtomicInteger expectedCounter = EXPECTED_POOL_COUNTS.get(id);
            int expectedCount = (expectedCounter != null) ? expectedCounter.get() : 0;

            if (pools != null && !pools.isEmpty()) {
                for (LootPool pool : pools) {
                    tableBuilder.pool(pool);
                }
            }

            if (actualCount != expectedCount) {
                LOGGER.warn("[better_loot_zibura] 战利品表 {} 的池数量不匹配！预期配置了 {} 个池，但实际只构建成功了 {} 个池（丢失/过滤了 {} 个）！",
                        id, expectedCount, actualCount, (expectedCount - actualCount));
            }
        });
    }

    /**
     * 记录某个 target 理论上应该生成的池数量（+1）
     */
    public static void recordExpectedPool(String tableId) {
        if (tableId == null) return;
        Identifier targetLoc = Identifier.tryParse(tableId);
        if (targetLoc != null) {
            EXPECTED_POOL_COUNTS.computeIfAbsent(targetLoc, k -> new AtomicInteger(0)).incrementAndGet();
        }
    }

    /**
     * 清空已挂载缓存（用于重载配置时）
     */
    public static void clearRegisteredPools() {
        PENDING_POOLS.clear();
        EXPECTED_POOL_COUNTS.clear();
    }

    // ==========================================
    // 战利品构建与注册入口
    // ==========================================

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
    // Explicit zero/negative weight disables the whole group.
    if (group.groupWeight <= 0.0) continue;
            GroupDTO cleanGroup = new GroupDTO();
            cleanGroup.groupName = group.groupName != null ? group.groupName : "default";
            cleanGroup.groupWeight = group.groupWeight;
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
    // Explicit zero/negative ratio disables the item.
    if (rawItem.ratio <= 0.0) continue;
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
                    totalRatio += item.ratio;
                }
            }

            if (totalRatio > 0) {
                cleanConfig.add(cleanGroup);
            }
        }

        LootPool lootPool = buildLootPool(cleanConfig, minRolls, maxRolls, conditionJson);

        if (lootPool != null && tableId != null) {
            Identifier targetLoc = new Identifier(tableId);
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
        LootPool.Builder poolBuilder = LootPool.builder()
                .rolls(UniformLootNumberProvider.create(minRolls, maxRolls));

        if (conditionJson != null) {
            applyConditionsToPool(poolBuilder, conditionJson);
        }

        for (GroupDTO group : cleanConfig) {
            double groupTotalRatio = group.items.stream()
                    .mapToDouble(i -> i.ratio > 0 ? i.ratio : 1.0)
                    .sum();

            for (ItemDTO item : group.items) {
                double ratio = item.ratio;
                int weight = (int) Math.max(1, (group.groupWeight * ratio * COMMON_MULTIPLIER) / groupTotalRatio);

                LeafEntry.Builder<?> entryBuilder;
                boolean isNormalItem = false;

                if (item.empty || "empty".equals(item.id) || "empty".equals(item.type)) {
                    entryBuilder = EmptyEntry.builder();
                } else if (item.reference != null || "reference".equals(item.type)) {
                    String refPath = item.reference != null ? item.reference : item.id;
                    entryBuilder = LootTableEntry.builder(new Identifier(refPath));
                } else {
                    Item itemObj = Registries.ITEM.get(new Identifier(item.id));
                    if (itemObj == null) continue;
                    entryBuilder = ItemEntry.builder(itemObj);
                    isNormalItem = true;
                }

                entryBuilder.weight(weight);

                applyItemConditions(entryBuilder, item, group);

                int maxCount = item.max != null ? item.max : (group.max != null ? group.max : 1);
                int minCount = (item.max != null && group.min != null && item.max < group.min)
                        ? 0 : (item.min != null ? item.min : (group.min != null ? group.min : 1));

                entryBuilder.apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(minCount, maxCount)));

                if (isNormalItem) {
                    applyItemFunctions(entryBuilder, item, group);
                }

                poolBuilder.with(entryBuilder);
            }
        }

        return poolBuilder.build();
    }

    // ==========================================
    // 条件注入
    // ==========================================

    private static void applyConditionsToPool(LootPool.Builder poolBuilder, JsonObject conditionJson) {
        if (conditionJson.has("matchTime")) {
            JsonArray timeParams = conditionJson.getAsJsonArray("matchTime");
            long period = timeParams.get(0).getAsLong();
            int minTime = timeParams.get(1).getAsInt();
            int maxTime = timeParams.get(2).getAsInt();

            poolBuilder.conditionally(TimeCheckLootCondition.create(BoundedIntUnaryOperator.create(minTime, maxTime)).period(period));
        }
        if (conditionJson.has("survivesExplosion") && conditionJson.get("survivesExplosion").getAsBoolean()) {
            poolBuilder.conditionally(SurvivesExplosionLootCondition.builder());
        }
        if (conditionJson.has("killedByPlayer") && conditionJson.get("killedByPlayer").getAsBoolean()) {
            poolBuilder.conditionally(KilledByPlayerLootCondition.builder());
        }

        if (conditionJson.has("matchBiome")) {
            JsonElement biomeElement = conditionJson.get("matchBiome");

            if (biomeElement.isJsonArray()) {
                JsonArray biomeArray = biomeElement.getAsJsonArray();
                List<LootCondition.Builder> conditions = new ArrayList<>();

                for (JsonElement elem : biomeArray) {
                    Identifier biomeId = new Identifier(elem.getAsString());
                    RegistryKey<Biome> biomeKey = RegistryKey.of(RegistryKeys.BIOME, biomeId);

                    conditions.add(LocationCheckLootCondition.builder(
                            LocationPredicate.Builder.create().biome(biomeKey)
                    ));
                }

                if (!conditions.isEmpty()) {
                    poolBuilder.conditionally(AnyOfLootCondition.builder(conditions.toArray(new LootCondition.Builder[0])));
                }
            } else if (biomeElement.isJsonPrimitive()) {
                Identifier biomeId = new Identifier(biomeElement.getAsString());
                RegistryKey<Biome> biomeKey = RegistryKey.of(RegistryKeys.BIOME, biomeId);

                poolBuilder.conditionally(LocationCheckLootCondition.builder(
                        LocationPredicate.Builder.create().biome(biomeKey)
                ));
            }
        }

        if (conditionJson.has("customCondition")) {
            JsonElement customElem = conditionJson.get("customCondition");
            Gson conditionGson = LootDataType.PREDICATES.getGson();

            if (customElem.isJsonArray()) {
                for (JsonElement elem : customElem.getAsJsonArray()) {
                    if (elem.isJsonObject()) {
                        try {
                            LootCondition condition = conditionGson.fromJson(elem, LootCondition.class);
                            if (condition != null) {
                                poolBuilder.conditionally(() -> condition);
                            }
                        } catch (Exception e) {
                            LOGGER.error("Failed to parse custom loot condition in array: " + elem, e);
                        }
                    }
                }
            } else if (customElem.isJsonObject()) {
                try {
                    LootCondition condition = conditionGson.fromJson(customElem.getAsJsonObject(), LootCondition.class);
                    if (condition != null) {
                        poolBuilder.conditionally(() -> condition);
                    }
                } catch (Exception e) {
                    LOGGER.error("Failed to parse custom loot condition: " + customElem, e);
                }
            }
        }
    }

    private static void applyItemConditions(LeafEntry.Builder<?> entryBuilder, ItemDTO item, GroupDTO group) {
        if (item.randomChance != null) {
            entryBuilder.conditionally(RandomChanceLootCondition.builder(item.randomChance.floatValue()));
        }
    }

    // ==========================================
    // 函数与属性修饰注入
    // ==========================================

    private static void applyItemFunctions(LeafEntry.Builder<?> entryBuilder, ItemDTO item, GroupDTO group) {
        // 1. NBT 写入
        JsonObject nbtJson = item.nbt != null ? item.nbt : (group.jsonFunction != null && group.jsonFunction.isJsonObject() ? group.jsonFunction.getAsJsonObject() : null);
        if (nbtJson != null) {
            try {
                NbtCompound tag = StringNbtReader.parse(nbtJson.toString());
                entryBuilder.apply(SetNbtLootFunction.builder(tag));
            } catch (CommandSyntaxException ignored) {}
        }

        // 2. 耐久损伤
        JsonElement damageElem = item.damage != null ? item.damage : group.damage;
        if (damageElem != null && !damageElem.isJsonNull()) {
            if (damageElem.isJsonPrimitive() && damageElem.getAsJsonPrimitive().isNumber()) {
                entryBuilder.apply(SetDamageLootFunction.builder(
                        ConstantLootNumberProvider.create(damageElem.getAsFloat())
                ));
            } else if (damageElem.isJsonArray()) {
                JsonArray dArr = damageElem.getAsJsonArray();
                if (dArr.size() >= 2) {
                    float minD = dArr.get(0).getAsFloat();
                    float maxD = dArr.get(1).getAsFloat();
                    entryBuilder.apply(SetDamageLootFunction.builder(
                            UniformLootNumberProvider.create(minD, maxD)
                    ));
                } else if (dArr.size() == 1) {
                    entryBuilder.apply(SetDamageLootFunction.builder(
                            ConstantLootNumberProvider.create(dArr.get(0).getAsFloat())
                    ));
                }
            }
        }

        // 3. 药水类型注入
        String potionId = item.potion != null ? item.potion : group.potion;
        if (potionId != null) {
            Potion potion = Registries.POTION.get(new Identifier(potionId));
            if (potion != null) {
                entryBuilder.apply(SetPotionLootFunction.builder(potion));
            }
        }

        // 4. 随机附魔等级
        List<Integer> enchantLevels = item.enchantLevels != null ? item.enchantLevels : group.enchantLevels;
        if (enchantLevels != null && enchantLevels.size() >= 2) {
            entryBuilder.apply(EnchantWithLevelsLootFunction.builder(
                    UniformLootNumberProvider.create(enchantLevels.get(0), enchantLevels.get(1))
            ));
        }

        // 5. 随机附魔列表
        List<String> enchantRandomly = item.enchantRandomly != null ? item.enchantRandomly : group.enchantRandomly;
        if (enchantRandomly != null && !enchantRandomly.isEmpty()) {
            if ("immersiveenchanting:ancient_book".equals(item.id)) {
                NbtCompound ancientBookTag = new NbtCompound();
                NbtList enchantList = new NbtList();

                for (String enchId : enchantRandomly) {
                    NbtCompound singleEnch = new NbtCompound();
                    singleEnch.putString("id", enchId);
                    singleEnch.putShort("lvl", (short) 1);
                    enchantList.add(singleEnch);
                }

                ancientBookTag.put("StoredEnchantments", enchantList);
                entryBuilder.apply(SetNbtLootFunction.builder(ancientBookTag));
            } else {
                EnchantRandomlyLootFunction.Builder enchantBuilder = EnchantRandomlyLootFunction.create();
                for (String enchId : enchantRandomly) {
                    Enchantment ench = Registries.ENCHANTMENT.get(new Identifier(enchId));
                    if (ench != null) {
                        enchantBuilder.add(ench);
                    }
                }
                entryBuilder.apply(enchantBuilder);
            }
        }

        // 6. 指定精准附魔
        Map<String, Integer> exactEnchants = item.exactEnchants != null ? item.exactEnchants : group.exactEnchants;
        if (exactEnchants != null && !exactEnchants.isEmpty()) {
            NbtCompound enchantTag = new NbtCompound();
            NbtCompound storedEnchantTag = new NbtCompound();
            NbtList enchantList = new NbtList();

            for (Map.Entry<String, Integer> entry : exactEnchants.entrySet()) {
                NbtCompound singleEnch = new NbtCompound();
                singleEnch.putString("id", entry.getKey());
                singleEnch.putShort("lvl", entry.getValue().shortValue());
                enchantList.add(singleEnch);
            }

            if ("minecraft:enchanted_book".equals(item.id)) {
                storedEnchantTag.put("StoredEnchantments", enchantList);
                entryBuilder.apply(SetNbtLootFunction.builder(storedEnchantTag));
            } else {
                enchantTag.put("Enchantments", enchantList);
                entryBuilder.apply(SetNbtLootFunction.builder(enchantTag));
            }
        }

        // 7. 原生 Function 解析注入
        Gson lootGson = LootDataType.ITEM_MODIFIERS.getGson();
        JsonElement funcElem = item.jsonFunction != null ? item.jsonFunction : group.jsonFunction;

        if (funcElem != null && !funcElem.isJsonNull()) {
            if (funcElem.isJsonArray()) {
                for (JsonElement element : funcElem.getAsJsonArray()) {
                    if (element.isJsonObject()) {
                        try {
                            LootFunction lootFunction = lootGson.fromJson(element.getAsJsonObject(), LootFunction.class);
                            if (lootFunction != null) {
                                entryBuilder.apply(() -> lootFunction);
                            }
                        } catch (Exception e) {
                            LOGGER.error("Failed to parse loot function in array: " + element, e);
                        }
                    }
                }
            } else if (funcElem.isJsonObject()) {
                try {
                    LootFunction lootFunction = lootGson.fromJson(funcElem.getAsJsonObject(), LootFunction.class);
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
    // 校验与辅助方法
    // ==========================================

    private static boolean isEnchantmentValid(String enchantId) {
        Identifier id = Identifier.tryParse(enchantId);
        return id != null && Registries.ENCHANTMENT.containsId(id);
    }

    private static boolean isPotionValid(String potionId) {
        Identifier id = Identifier.tryParse(potionId);
        return id != null && Registries.POTION.containsId(id);
    }

    private static String normalizeItemId(String id) {
        if (id == null) return null;

        if (FabricLoader.getInstance().isModLoaded("youkaishomecoming") && id.startsWith("youkaisfeasts:")) {
            return "youkaishomecoming:" + id.substring("youkaisfeasts:".length());
        }

        if (FabricLoader.getInstance().isModLoaded("youkaisfeasts") && id.startsWith("youkaishomecoming:")) {
            return "youkaisfeasts:" + id.substring("youkaishomecoming:".length());
        }

        return id;
    }

    private static boolean isValidItem(ItemDTO item) {
        if (item.empty || "empty".equals(item.id) || "empty".equals(item.type)) return true;
        if (item.reference != null || "reference".equals(item.type)) return true;
        Identifier id = Identifier.tryParse(item.id);
        return id != null && Registries.ITEM.containsId(id);
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