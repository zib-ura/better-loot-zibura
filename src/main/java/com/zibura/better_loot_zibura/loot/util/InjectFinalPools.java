package com.zibura.better_loot_zibura.loot.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.JsonOps;
import com.zibura.better_loot_zibura.loot.condition.SynchronizedSlotCondition;
import com.zibura.better_loot_zibura.loot.model.AllLevelModel.GroupDTO;
import com.zibura.better_loot_zibura.loot.model.AllLevelModel.ItemDTO;
import com.zibura.better_loot_zibura.loot.unification.ItemUnificationSolver;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.advancements.predicates.LocationPredicate;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderSet;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.TagParser;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.*;
import net.minecraft.world.level.storage.loot.functions.*;
import net.minecraft.world.level.storage.loot.predicates.*;
import net.minecraft.world.level.storage.loot.providers.number.ints.UniformGenerator;
import net.minecraft.world.level.storage.loot.providers.number.floats.ContextFloatProviders;
import net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProviders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

import static net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProviders.between;

/**
 * 战利品构建与 Fabric 事件挂载类 (适配当前开发环境映射)
 */
public final class InjectFinalPools {

    private InjectFinalPools() {}

    private static final Logger LOGGER = LoggerFactory.getLogger("better_loot_zibura");
    private static final double COMMON_MULTIPLIER = 100000.0;

    // LootTable 修改事件由 CommonEvents 注册。
    // 本类只负责在事件提供的动态 Registry 环境中构建 LootPool。

    // ==========================================
    // 战利品构建与注册入口
    // ==========================================

    public static LootPool buildLootPool(
            List<GroupDTO> configList,
            int minRolls,
            int maxRolls,
            JsonObject conditionJson,
            HolderLookup.Provider registries
    ) {
        if (configList == null || configList.isEmpty()) return null;

        HolderGetter<Enchantment> enchantmentLookup = registries.lookupOrThrow(Registries.ENCHANTMENT);

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
                            .filter(enchId -> isEnchantmentValid(enchantmentLookup, enchId))
                            .toList();
                    if (validEnchants.isEmpty()) {
                        continue;
                    }
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
                } else {
                    Identifier parsedId = item.id != null ? Identifier.tryParse(item.id) : null;
                }
            }

            if (totalRatio > 0) {
                cleanConfig.add(cleanGroup);
            } else {
            }
        }

        if (cleanConfig.isEmpty()) {
            return null;
        }

        return buildPreparedPool(registries, cleanConfig, minRolls, maxRolls, conditionJson);
    }

    private static LootPool buildPreparedPool(
            HolderLookup.Provider registries,
            List<GroupDTO> cleanConfig,
            int minRolls,
            int maxRolls,
            JsonObject conditionJson
    ) {
        LootPool.Builder poolBuilder = LootPool.lootPool()
                .setRolls(between(minRolls, maxRolls));
        if (conditionJson != null) {
            applyConditionsToPool(registries, poolBuilder, conditionJson);
        }

        for (GroupDTO group : cleanConfig) {
            double groupTotalRatio = group.items.stream()
                    .mapToDouble(i -> i.ratio > 0 ? i.ratio : 1.0)
                    .sum();

            for (ItemDTO item : group.items) {
                double ratio = item.ratio;
                int weight = (int) Math.max(1, (group.groupWeight * ratio * COMMON_MULTIPLIER) / groupTotalRatio);

                UniformContainerBase.Builder<?> entryBuilder;
                boolean isNormalItem = false;
                boolean isEmptyItem = false;

                if (item.empty || "empty".equals(item.id) || "empty".equals(item.type)) {
                    entryBuilder = EmptyLootItem.emptyItem();
                    isEmptyItem = true;
                } else if (item.reference != null || "reference".equals(item.type)) {
                    String refPath = item.reference != null ? item.reference : item.id;
                    Identifier refId = Identifier.tryParse(refPath);
                    if (refId == null) {
                        LOGGER.warn("Invalid nested loot table id: {}", refPath);
                        continue;
                    }

                    ResourceKey<LootTable> lootTableKey = ResourceKey.create(Registries.LOOT_TABLE, refId);
                    var lootTableLookup = registries.lookupOrThrow(Registries.LOOT_TABLE);
                    if (lootTableLookup.get(lootTableKey).isEmpty()) {
                        LOGGER.warn("Unknown nested loot table: {}", refId);
                        continue;
                    }

                    entryBuilder = NestedLootTable.lootTableReference(lootTableLookup.get(lootTableKey).get());
                } else {
                    Identifier itemId = Identifier.tryParse(item.id);
                    if (itemId == null) continue;
                    var itemHolderOpt = BuiltInRegistries.ITEM.get(itemId);
                    if (itemHolderOpt.isEmpty()) continue;

                    // 从 Holder 中取出 Item 实例 (或直接传 itemHolderOpt.get().value())
                    Item itemObj = itemHolderOpt.get().value();
                    entryBuilder = LootItem.lootTableItem(itemObj);
                    isNormalItem = true;
                }

                entryBuilder.setWeight(weight);

                applyItemConditions(entryBuilder, item, group);

                int maxCount = item.max != null ? item.max : (group.max != null ? group.max : 1);
                int minCount = (item.max != null && group.min != null && item.max < group.min)
                        ? 0 : (item.min != null ? item.min : (group.min != null ? group.min : 1));

                if (!isEmptyItem) {
                    entryBuilder.apply(SetItemCountFunction.setCount(between(minCount, maxCount)));
                }

                if (isNormalItem) {
                    applyItemFunctions(registries, entryBuilder, item, group);
                }

                poolBuilder.add(entryBuilder);
            }
        }

        return poolBuilder.build();
    }

    // ==========================================
    // 条件注入
    // ==========================================
    private static void applyConditionsToPool(HolderLookup.Provider registries, LootPool.Builder poolBuilder, JsonObject conditionJson) {
        if (conditionJson.has("matchTime")) {
            JsonArray timeParams = conditionJson.getAsJsonArray("matchTime");
            long period = timeParams.get(0).getAsLong();
            int minSlot = timeParams.get(1).getAsInt();
            int maxSlot = timeParams.get(2).getAsInt();

            // 在 Loot 真正执行时读取 LootContext 所在世界的 gameTime。
            // period == 1 时唯一槽 [0,0] 恒真，无需添加条件。
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
            HolderLookup.RegistryLookup<Biome> biomeLookup = registries.lookupOrThrow(Registries.BIOME);

            if (biomeElement.isJsonArray()) {
                JsonArray biomeArray = biomeElement.getAsJsonArray();
                List<LootItemCondition.Builder> conditions = new ArrayList<>();

                for (JsonElement elem : biomeArray) {
                    Identifier biomeId = Identifier.parse(elem.getAsString());
                    biomeLookup.get(ResourceKey.create(Registries.BIOME, biomeId)).ifPresent(biomeEntry -> {
                        conditions.add(LocationCheck.checkLocation(
                                LocationPredicate.Builder.location().setBiomes(HolderSet.direct(biomeEntry))
                        ));
                    });
                }

                if (!conditions.isEmpty()) {
                    poolBuilder.when(AnyOfCondition.anyOf(conditions.toArray(new LootItemCondition.Builder[0])));
                }
            } else if (biomeElement.isJsonPrimitive()) {
                Identifier biomeId = Identifier.parse(biomeElement.getAsString());
                biomeLookup.get(ResourceKey.create(Registries.BIOME, biomeId)).ifPresent(biomeEntry -> {
                    poolBuilder.when(LocationCheck.checkLocation(
                            LocationPredicate.Builder.location().setBiomes(HolderSet.direct(biomeEntry))
                    ));
                });
            }
        }

        if (conditionJson.has("customCondition")) {
            JsonElement customElem = conditionJson.get("customCondition");

            if (customElem.isJsonArray()) {
                for (JsonElement elem : customElem.getAsJsonArray()) {
                    if (elem.isJsonObject()) {
                        LootItemCondition.DIRECT_CODEC.parse(registries.createSerializationContext(JsonOps.INSTANCE), elem)
                                .resultOrPartial((String err) -> LOGGER.error("Failed to parse custom loot condition in array: {}", err))
                                .ifPresent(condition -> poolBuilder.when(() -> condition));
                    }
                }
            } else if (customElem.isJsonObject()) {
                LootItemCondition.DIRECT_CODEC.parse(registries.createSerializationContext(JsonOps.INSTANCE), customElem)
                        .resultOrPartial((String err) -> LOGGER.error("Failed to parse custom loot condition: {}", err))
                        .ifPresent(condition -> poolBuilder.when(() -> condition));
            }
        }
    }

    private static void applyItemConditions(LootPoolEntryContainer.Builder<?> entryBuilder, ItemDTO item, GroupDTO group) {
        if (item.randomChance != null) {
            entryBuilder.when(LootItemRandomChanceCondition.randomChance(item.randomChance.floatValue()));
        }
    }

    // ==========================================
    // 函数与属性修饰注入
    // ==========================================

    private static void applyItemFunctions(HolderLookup.Provider registries, LootPoolEntryContainer.Builder<?> entryBuilder, ItemDTO item, GroupDTO group) {
        // 1. 自定义 NBT
        JsonObject nbtJson = item.nbt != null ? item.nbt : (group.jsonFunction != null && group.jsonFunction.isJsonObject() ? group.jsonFunction.getAsJsonObject() : null);
        if (nbtJson != null) {
            try {
                CompoundTag tag = TagParser.parseCompoundFully(nbtJson.toString());
                entryBuilder.apply(SetCustomDataFunction.setCustomData(tag));
            } catch (CommandSyntaxException ignored) {
            }
        }

        // 2. 耐久损伤
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

        // 3. 药水类型注入
        String potionId = item.potion != null ? item.potion : group.potion;
        if (potionId != null) {
            Holder<Potion> potionEntry = BuiltInRegistries.POTION
                    .get(ResourceKey.create(Registries.POTION, Identifier.parse(potionId)))
                    .orElse(null);
            if (potionEntry != null) {
                entryBuilder.apply(SetPotionFunction.setPotion(potionEntry));
            }
        }

        // 4. 随机附魔等级
        List<Integer> enchantLevels = item.enchantLevels != null ? item.enchantLevels : group.enchantLevels;
        if (enchantLevels != null && enchantLevels.size() >= 2) {
            HolderGetter<Enchantment> enchantments =
                    registries.lookupOrThrow(Registries.ENCHANTMENT);
            entryBuilder.apply(EnchantWithLevelsFunction.enchantWithLevels(
                    enchantments,
                    ContextIntProviders.between(enchantLevels.get(0), enchantLevels.get(1))
            ));
        }

        // 5. 随机附魔列表
        List<String> enchantRandomly = item.enchantRandomly != null ? item.enchantRandomly : group.enchantRandomly;
        if (enchantRandomly != null && !enchantRandomly.isEmpty()) {
            if ("immersiveenchanting:ancient_book".equals(item.id)) {
                try {
                    CompoundTag customData = TagParser.parseCompoundFully("{StoredEnchantments:[]}");
                    entryBuilder.apply(SetCustomDataFunction.setCustomData(customData));
                } catch (CommandSyntaxException ignored) {
                }
            } else {
                HolderLookup.RegistryLookup<Enchantment> enchantLookup = registries.lookupOrThrow(Registries.ENCHANTMENT);
                List<Holder<Enchantment>> enchantEntries = new ArrayList<>();

                for (String enchId : enchantRandomly) {
                    Identifier id = Identifier.tryParse(enchId);
                    if (id != null) {
                        enchantLookup.get(ResourceKey.create(Registries.ENCHANTMENT, id))
                                .ifPresent(enchantEntries::add);
                    }
                }

                if (!enchantEntries.isEmpty()) {
                    EnchantRandomlyFunction.Builder enchantBuilder = EnchantRandomlyFunction.randomEnchantment()
                            .withOneOf(HolderSet.direct(enchantEntries));
                    entryBuilder.apply(enchantBuilder);
                }
            }
        }

        // 6. 指定精准附魔
        Map<String, Integer> exactEnchants = item.exactEnchants != null ? item.exactEnchants : group.exactEnchants;
        if (exactEnchants != null && !exactEnchants.isEmpty()) {
            HolderLookup.RegistryLookup<Enchantment> enchantLookup = registries.lookupOrThrow(Registries.ENCHANTMENT);
            ItemEnchantments.Mutable enchCompBuilder = new ItemEnchantments.Mutable(ItemEnchantments.EMPTY);

            for (Map.Entry<String, Integer> entry : exactEnchants.entrySet()) {
                Identifier id = Identifier.tryParse(entry.getKey());
                if (id != null) {
                    enchantLookup.get(ResourceKey.create(Registries.ENCHANTMENT, id))
                            .ifPresent(enchEntry -> enchCompBuilder.set(enchEntry, entry.getValue()));
                }
            }

            ItemEnchantments component = enchCompBuilder.toImmutable();
            if ("minecraft:enchanted_book".equals(item.id)) {
                entryBuilder.apply(SetComponentsFunction.setComponent(DataComponents.STORED_ENCHANTMENTS, component));
            } else {
                entryBuilder.apply(SetComponentsFunction.setComponent(DataComponents.ENCHANTMENTS, component));
            }
        }

        // 7. 原生 Function 解析注入
        JsonElement funcElem = item.jsonFunction != null ? item.jsonFunction : group.jsonFunction;
        if (funcElem != null && !funcElem.isJsonNull()) {
            if (funcElem.isJsonArray()) {
                for (JsonElement element : funcElem.getAsJsonArray()) {
                    if (element.isJsonObject()) {
                        LootItemFunctions.CODEC.parse(registries.createSerializationContext(JsonOps.INSTANCE), element)
                                .resultOrPartial(err -> LOGGER.error("Failed to parse loot function in array: {}", err))
                                .ifPresent(func -> entryBuilder.apply(new LootItemFunction.Builder() {
                                    @Override
                                    public LootItemFunction build() {
                                        return func.value();
                                    }
                                }));
                    }
                }
            } else if (funcElem.isJsonObject()) {
                LootItemFunctions.CODEC.parse(registries.createSerializationContext(JsonOps.INSTANCE), funcElem)
                        .resultOrPartial(err -> LOGGER.error("Failed to parse single loot function: {}", err))
                        .ifPresent(func -> entryBuilder.apply(new LootItemFunction.Builder() {
                            @Override
                            public LootItemFunction build() {
                                return func.value();
                            }
                        }));
            }
        }
    }

    // ==========================================
    // 校验与辅助方法
    // ==========================================

    private static boolean isEnchantmentValid(HolderGetter<Enchantment> enchantmentLookup, String enchantId) {
        Identifier id = Identifier.tryParse(enchantId);
        if (id == null) return false;
        return enchantmentLookup.get(ResourceKey.create(Registries.ENCHANTMENT, id)).isPresent();
    }

    private static boolean isPotionValid(String potionId) {
        Identifier id = Identifier.tryParse(potionId);
        return id != null && BuiltInRegistries.POTION.containsKey(id);
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
        return id != null && BuiltInRegistries.ITEM.containsKey(id);
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
        copy.conditions = src.conditions != null ? new HashMap<>(groupConditions(src)) : new HashMap<>();
        return copy;
    }

    private static Map<String, Object> groupConditions(ItemDTO src) {
        return src.conditions != null ? src.conditions : Collections.emptyMap();
    }
}