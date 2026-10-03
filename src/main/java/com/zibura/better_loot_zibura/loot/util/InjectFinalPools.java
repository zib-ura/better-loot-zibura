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
import net.minecraft.core.*;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.*;
import net.minecraft.world.level.storage.loot.functions.*;
import net.minecraft.world.level.storage.loot.predicates.*;
import net.minecraft.world.level.storage.loot.providers.number.floats.ContextFloatProviders;
import net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProvider;
import net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProviders;
import net.neoforged.fml.ModList;
import org.slf4j.Logger;
import java.util.*;

public final class InjectFinalPools {

    private static final Logger LOGGER = LogUtils.getLogger();
    private static final double COMMON_MULTIPLIER = 100000.0;


    private InjectFinalPools() {}


    public static LootPool buildLootPool(
            List<GroupDTO> configList,
            int minRolls,
            int maxRolls,
            JsonObject conditionJson,
            HolderGetter.Provider registries
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


                // 修复点 2：附魔合法性过滤校验
                List<String> enchantRandomly = item.enchantRandomly != null ? item.enchantRandomly : cleanGroup.enchantRandomly;
                if (enchantRandomly != null && !enchantRandomly.isEmpty()) {
                    List<String> validEnchants =
                            enchantRandomly.stream()
                                    .filter(id ->
                                            isEnchantmentValid(
                                                    id,
                                                    registries
                                            )
                                    )
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

        return buildPreparedPool(
                cleanConfig,
                minRolls,
                maxRolls,
                conditionJson,
                registries
        );
    }

    private static LootPool buildPreparedPool(
            List<GroupDTO> cleanConfig,
            int minRolls,
            int maxRolls,
            JsonObject conditionJson,
            HolderGetter.Provider registries
    ) {
        LootPool.Builder poolBuilder = LootPool.lootPool()
                .setRolls(ContextIntProviders.between(minRolls, maxRolls));

        if (conditionJson != null) {
            applyConditionsToPool(poolBuilder, conditionJson, registries);
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

                    Identifier refId =
                            Identifier.tryParse(refPath);

                    if (refId == null) {
                        continue;
                    }

                    ResourceKey<LootTable> refKey =
                            ResourceKey.create(
                                    Registries.LOOT_TABLE,
                                    refId
                            );

                    var lootTableGetter =
                            registries.lookupOrThrow(
                                    Registries.LOOT_TABLE
                            );

                    var lootTableHolder =
                            lootTableGetter.get(refKey);

                    if (lootTableHolder.isEmpty()) {
                        LOGGER.warn(
                                "Unknown nested loot table: {}",
                                refId
                        );
                        continue;
                    }

                    entryBuilder =
                            NestedLootTable.lootTableReference(
                                    lootTableHolder.get()
                            );

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
                    applyItemFunctions(entryBuilder, item, group, registries);
                }

                poolBuilder.add(entryBuilder);
            }
        }

        return poolBuilder.build();
    }

    private static void applyConditionsToPool(
            LootPool.Builder poolBuilder,
            JsonObject conditionJson,
            HolderGetter.Provider registries
    ) {
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

    private static void applyItemFunctions(
            LootPoolEntryContainer.Builder<?> entryBuilder,
            ItemDTO item,
            GroupDTO group,
            HolderGetter.Provider registries
    ) {
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

        // 指定候选附魔：直接使用原版 EnchantRandomlyFunction。
        // 原版函数会在目标为 minecraft:book 时自行转换为 enchanted_book。
        List<String> enchantRandomly = item.enchantRandomly != null ? item.enchantRandomly : group.enchantRandomly;
        if (enchantRandomly != null && !enchantRandomly.isEmpty()) {
            var enchantmentGetter = registries.lookupOrThrow(Registries.ENCHANTMENT);

            List<Holder<Enchantment>> enchantments = enchantRandomly.stream()
                    .map(Identifier::tryParse)
                    .filter(Objects::nonNull)
                    .map(id -> ResourceKey.create(Registries.ENCHANTMENT, id))
                    .map(enchantmentGetter::get)
                    .flatMap(Optional::stream)
                    .map(holder -> (Holder<Enchantment>) holder)
                    .toList();

            if (!enchantments.isEmpty()) {
                entryBuilder.apply(
                        EnchantRandomlyFunction.randomEnchantment()
                                .withOneOf(HolderSet.direct(enchantments))
                );
            }
        }

        RegistryOps<JsonElement> registryOps =
                RegistryOps.create(
                        JsonOps.INSTANCE,
                        new RegistryOps.RegistryInfoLookup() {
                            @Override
                            public <T> Optional<HolderGetter<T>> lookup(
                                    ResourceKey<? extends Registry<? extends T>> registryKey
                            ) {
                                return registries.lookup(registryKey)
                                        .map(getter -> (HolderGetter<T>) getter);
                            }
                        }
                );
        // 自定义 Function
        JsonElement funcElem = item.jsonFunction != null ? item.jsonFunction : group.jsonFunction;
        if (funcElem != null && !funcElem.isJsonNull()) {
            if (funcElem.isJsonArray()) {
                for (JsonElement element : funcElem.getAsJsonArray()) {
                    if (element.isJsonObject()) {
                        LootItemFunctions.DIRECT_CODEC.parse(
                                        registryOps,
                                        element
                                )
                                .resultOrPartial(err -> LOGGER.error("Failed to parse loot function in array: {} | Error: {}", element, err))
                                .ifPresent(fn -> entryBuilder.apply(() -> fn));
                    }
                }
            } else if (funcElem.isJsonObject()) {
                LootItemFunctions.DIRECT_CODEC.parse(
                                registryOps,
                                funcElem
                        )
                        .resultOrPartial(err -> LOGGER.error("Failed to parse single loot function: {} | Error: {}", funcElem, err))
                        .ifPresent(fn -> entryBuilder.apply(() -> fn));
            }
        }
    }


    private static boolean isEnchantmentValid(
            String enchantId,
            HolderGetter.Provider registries
    ) {
        Identifier id = Identifier.tryParse(enchantId);

        if (id == null) {
            return false;
        }

        ResourceKey<Enchantment> key =
                ResourceKey.create(
                        Registries.ENCHANTMENT,
                        id
                );

        return registries.get(key).isPresent();
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
        copy.conditions = src.conditions != null ? new HashMap<>(src.conditions) : new HashMap<>();
        return copy;
    }
}