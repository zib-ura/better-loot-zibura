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
import net.minecraft.component.type.ItemEnchantmentsComponent;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.condition.*;
import net.minecraft.loot.entry.EmptyEntry;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.entry.LeafEntry;
import net.minecraft.loot.entry.LootTableEntry;
import net.minecraft.loot.function.*;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.StringNbtReader;
import net.minecraft.potion.Potion;
import net.minecraft.predicate.entity.LocationPredicate;
import net.minecraft.registry.*;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 战利品构建与 Fabric 事件挂载类 (适配 1.21.1 Yarn)
 */
public final class InjectFinalPools {

    private InjectFinalPools() {}

    private static final Logger LOGGER = LoggerFactory.getLogger("better_loot_zibura");
    private static final double COMMON_MULTIPLIER = 100000.0;

// ==========================================
// 战利品构建与注册入口
// ==========================================

    public static LootPool buildLootPool(
            List<GroupDTO> configList,
            int minRolls,
            int maxRolls,
            JsonObject conditionJson,
            RegistryWrapper.WrapperLookup registries
    ) {
        if (configList == null || configList.isEmpty()) {
            return null;
        }

        RegistryEntryLookup<Enchantment> enchantmentLookup =
                registries.createRegistryLookup()
                        .getOrThrow(RegistryKeys.ENCHANTMENT);

        // 下面继续使用你现有的 cleanConfig 逻辑
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

                List<String> enchantRandomly = item.enchantRandomly != null ? item.enchantRandomly : group.enchantRandomly;
                if (enchantRandomly != null && !enchantRandomly.isEmpty()) {
                    List<String> validEnchants = enchantRandomly.stream()
                            .filter(enchId -> isEnchantmentValid(enchantmentLookup, enchId))
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

        return buildPreparedPool(
                registries,
                cleanConfig,
                minRolls,
                maxRolls,
                conditionJson
        );
    }

    private static LootPool buildPreparedPool(
            RegistryWrapper.WrapperLookup registries,
            List<GroupDTO> cleanConfig,
            int minRolls,
            int maxRolls,
            JsonObject conditionJson
    ) {
        LootPool.Builder poolBuilder = LootPool.builder()
                .rolls(UniformLootNumberProvider.create(minRolls, maxRolls));

        if (conditionJson != null) {
            applyConditionsToPool(registries, poolBuilder, conditionJson);
        }

        for (GroupDTO group : cleanConfig) {
            double groupTotalRatio = group.items.stream()
                    .mapToDouble(i -> i.ratio > 0 ? i.ratio : 1.0)
                    .sum();

            for (ItemDTO item : group.items) {
                double ratio = item.ratio > 0 ? item.ratio : 1.0;
                int weight = (int) Math.max(1, (group.groupWeight * ratio * COMMON_MULTIPLIER) / groupTotalRatio);

                LeafEntry.Builder<?> entryBuilder;
                boolean isNormalItem = false;

                if (item.empty || "empty".equals(item.id) || "empty".equals(item.type)) {
                    entryBuilder = EmptyEntry.builder();
                } else if (item.reference != null || "reference".equals(item.type)) {
                    String refPath = item.reference != null ? item.reference : item.id;
                    RegistryKey<LootTable> lootTableKey = RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.of(refPath));
                    entryBuilder = LootTableEntry.builder(lootTableKey);
                } else {
                    Item itemObj = Registries.ITEM.get(Identifier.of(item.id));
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
                    applyItemFunctions(registries, entryBuilder, item, group);
                }

                poolBuilder.with(entryBuilder);
            }
        }

        return poolBuilder.build();
    }

    // ==========================================
    // 条件注入
    // ==========================================
    private static void applyConditionsToPool(RegistryWrapper.WrapperLookup registries, LootPool.Builder poolBuilder, JsonObject conditionJson) {
        if (conditionJson.has("matchTime")) {
            JsonArray timeParams =
                    conditionJson.getAsJsonArray("matchTime");

            long period = timeParams.get(0).getAsLong();
            int minSlot = timeParams.get(1).getAsInt();
            int maxSlot = timeParams.get(2).getAsInt();

            if (period > 1) {
                poolBuilder.conditionally(
                        SynchronizedSlotCondition.builder(
                                period,
                                minSlot,
                                maxSlot
                        )
                );
            }
        }
        if (conditionJson.has("survivesExplosion") && conditionJson.get("survivesExplosion").getAsBoolean()) {
            poolBuilder.conditionally(SurvivesExplosionLootCondition.builder());
        }
        if (conditionJson.has("killedByPlayer") && conditionJson.get("killedByPlayer").getAsBoolean()) {
            poolBuilder.conditionally(KilledByPlayerLootCondition.builder());
        }

        if (conditionJson.has("matchBiome")) {
            JsonElement biomeElement = conditionJson.get("matchBiome");
            RegistryWrapper.Impl<Biome> biomeLookup = registries.getWrapperOrThrow(RegistryKeys.BIOME);

            if (biomeElement.isJsonArray()) {
                JsonArray biomeArray = biomeElement.getAsJsonArray();
                List<LootCondition.Builder> conditions = new ArrayList<>();

                for (JsonElement elem : biomeArray) {
                    Identifier biomeId = Identifier.of(elem.getAsString());
                    biomeLookup.getOptional(RegistryKey.of(RegistryKeys.BIOME, biomeId)).ifPresent(biomeEntry -> {
                        // 修改此处：使用 RegistryEntryList.of 包装
                        conditions.add(LocationCheckLootCondition.builder(
                                LocationPredicate.Builder.create().biome(net.minecraft.registry.entry.RegistryEntryList.of(biomeEntry))
                        ));
                    });
                }

                if (!conditions.isEmpty()) {
                    poolBuilder.conditionally(AnyOfLootCondition.builder(conditions.toArray(new LootCondition.Builder[0])));
                }
            } else if (biomeElement.isJsonPrimitive()) {
                Identifier biomeId = Identifier.of(biomeElement.getAsString());
                biomeLookup.getOptional(RegistryKey.of(RegistryKeys.BIOME, biomeId)).ifPresent(biomeEntry -> {
                    // 修改此处：使用 RegistryEntryList.of 包装
                    poolBuilder.conditionally(LocationCheckLootCondition.builder(
                            LocationPredicate.Builder.create().biome(net.minecraft.registry.entry.RegistryEntryList.of(biomeEntry))
                    ));
                });
            }
        }

        if (conditionJson.has("customCondition")) {
            JsonElement customElem = conditionJson.get("customCondition");

            if (customElem.isJsonArray()) {
                for (JsonElement elem : customElem.getAsJsonArray()) {
                    if (elem.isJsonObject()) {
                        // 改用 LootCondition.CODEC
                        LootCondition.CODEC.parse(registries.getOps(JsonOps.INSTANCE), elem)
                                .resultOrPartial((String err) -> LOGGER.error("Failed to parse custom loot condition in array: {}", err))
                                .ifPresent(condition -> poolBuilder.conditionally(() -> condition));
                    }
                }
            } else if (customElem.isJsonObject()) {
                // 改用 LootCondition.CODEC
                LootCondition.CODEC.parse(registries.getOps(JsonOps.INSTANCE), customElem)
                        .resultOrPartial((String err) -> LOGGER.error("Failed to parse custom loot condition: {}", err))
                        .ifPresent(condition -> poolBuilder.conditionally(() -> condition));
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

    private static void applyItemFunctions(RegistryWrapper.WrapperLookup registries, LeafEntry.Builder<?> entryBuilder, ItemDTO item, GroupDTO group) {
        // 1. 自定义 NBT (1.21 改用 SetCustomDataLootFunction)
        JsonObject nbtJson = item.nbt != null ? item.nbt : (group.jsonFunction != null && group.jsonFunction.isJsonObject() ? group.jsonFunction.getAsJsonObject() : null);
        if (nbtJson != null) {
            try {
                NbtCompound tag = StringNbtReader.parse(nbtJson.toString());
                entryBuilder.apply(SetCustomDataLootFunction.builder(tag));
            } catch (CommandSyntaxException ignored) {
            }
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
            RegistryEntry<Potion> potionEntry = Registries.POTION.getEntry(Identifier.of(potionId)).orElse(null);
            if (potionEntry != null) {
                entryBuilder.apply(SetPotionLootFunction.builder(potionEntry));
            }
        }

        // 4. 随机附魔等级 (1.21 需要传入 Lookup)
        List<Integer> enchantLevels = item.enchantLevels != null ? item.enchantLevels : group.enchantLevels;
        if (enchantLevels != null && enchantLevels.size() >= 2) {
            entryBuilder.apply(EnchantWithLevelsLootFunction.builder(
                    registries,
                    UniformLootNumberProvider.create(enchantLevels.get(0), enchantLevels.get(1))
            ));
        }

// 5. 随机附魔列表
        List<String> enchantRandomly = item.enchantRandomly != null ? item.enchantRandomly : group.enchantRandomly;
        if (enchantRandomly != null && !enchantRandomly.isEmpty()) {
            if ("immersiveenchanting:ancient_book".equals(item.id)) {
                try {
                    NbtCompound customData = StringNbtReader.parse("{StoredEnchantments:[]}");
                    entryBuilder.apply(SetCustomDataLootFunction.builder(customData));
                } catch (CommandSyntaxException ignored) {
                }
            } else {
                RegistryWrapper.Impl<Enchantment> enchantLookup = registries.getWrapperOrThrow(RegistryKeys.ENCHANTMENT);
                List<RegistryEntry<Enchantment>> enchantEntries = new ArrayList<>();

                for (String enchId : enchantRandomly) {
                    Identifier id = Identifier.tryParse(enchId);
                    if (id != null) {
                        enchantLookup.getOptional(RegistryKey.of(RegistryKeys.ENCHANTMENT, id))
                                .ifPresent(enchantEntries::add);
                    }
                }

                if (!enchantEntries.isEmpty()) {
                    EnchantRandomlyLootFunction.Builder enchantBuilder = EnchantRandomlyLootFunction.create()
                            .options(net.minecraft.registry.entry.RegistryEntryList.of(enchantEntries));
                    entryBuilder.apply(enchantBuilder);
                }
            }
        }

// 6. 指定精准附魔
        Map<String, Integer> exactEnchants = item.exactEnchants != null ? item.exactEnchants : group.exactEnchants;
        if (exactEnchants != null && !exactEnchants.isEmpty()) {
            RegistryWrapper.Impl<Enchantment> enchantLookup = registries.getWrapperOrThrow(RegistryKeys.ENCHANTMENT);
            ItemEnchantmentsComponent.Builder enchCompBuilder = new ItemEnchantmentsComponent.Builder(ItemEnchantmentsComponent.DEFAULT);

            for (Map.Entry<String, Integer> entry : exactEnchants.entrySet()) {
                Identifier id = Identifier.tryParse(entry.getKey());
                if (id != null) {
                    enchantLookup.getOptional(RegistryKey.of(RegistryKeys.ENCHANTMENT, id))
                            .ifPresent(enchEntry -> enchCompBuilder.set(enchEntry, entry.getValue()));
                }
            }

            ItemEnchantmentsComponent component = enchCompBuilder.build();
            if ("minecraft:enchanted_book".equals(item.id)) {
                entryBuilder.apply(SetComponentsLootFunction.builder(net.minecraft.component.DataComponentTypes.STORED_ENCHANTMENTS, component));
            } else {
                entryBuilder.apply(SetComponentsLootFunction.builder(net.minecraft.component.DataComponentTypes.ENCHANTMENTS, component));
            }
        }

// 7. 原生 Function 解析注入
        JsonElement funcElem = item.jsonFunction != null ? item.jsonFunction : group.jsonFunction;
        if (funcElem != null && !funcElem.isJsonNull()) {
            if (funcElem.isJsonArray()) {
                for (JsonElement element : funcElem.getAsJsonArray()) {
                    if (element.isJsonObject()) {
                        LootFunctionTypes.CODEC.parse(registries.getOps(JsonOps.INSTANCE), element)
                                .resultOrPartial(err -> LOGGER.error("Failed to parse loot function in array: {}", err))
                                .ifPresent(func -> entryBuilder.apply(new LootFunction.Builder() {
                                    @Override
                                    public LootFunction build() {
                                        return func;
                                    }
                                }));
                    }
                }
            } else if (funcElem.isJsonObject()) {
                LootFunctionTypes.CODEC.parse(registries.getOps(JsonOps.INSTANCE), funcElem)
                        .resultOrPartial(err -> LOGGER.error("Failed to parse single loot function: {}", err))
                        .ifPresent(func -> entryBuilder.apply(new LootFunction.Builder() {
                            @Override
                            public LootFunction build() {
                                return func;
                            }
                        }));
            }
        }
    }
    // ==========================================
    // 校验与辅助方法
    // ==========================================

    private static boolean isEnchantmentValid(RegistryEntryLookup<Enchantment> enchantmentLookup, String enchantId) {
        Identifier id = Identifier.tryParse(enchantId);
        if (id == null) return false;
        return enchantmentLookup.getOptional(RegistryKey.of(RegistryKeys.ENCHANTMENT, id)).isPresent();
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