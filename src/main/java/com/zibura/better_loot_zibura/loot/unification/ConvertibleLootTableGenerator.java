//package com.zibura.better_loot_zibura.loot.util;
//
//import com.mojang.logging.LogUtils;
//import com.zibura.better_loot_zibura.better_loot_zibura;
//import net.minecraft.resources.ResourceLocation;
//import net.minecraft.server.MinecraftServer;
//import net.minecraft.world.item.Item;
//import net.minecraft.world.level.storage.loot.LootDataId;
//import net.minecraft.world.level.storage.loot.LootDataManager;
//import net.minecraft.world.level.storage.loot.LootDataType;
//import net.minecraft.world.level.storage.loot.LootPool;
//import net.minecraft.world.level.storage.loot.LootTable;
//import net.minecraft.world.level.storage.loot.ValidationContext;
//import net.minecraft.world.level.storage.loot.entries.LootItem;
//import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
//import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
//import net.minecraftforge.registries.ForgeRegistries;
//import org.slf4j.Logger;
//
//import java.lang.reflect.Field;
//import java.util.Collections;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.concurrent.ConcurrentHashMap;
//
//public final class ConvertibleLootTableGenerator {
//
//    private static final Logger LOGGER = LogUtils.getLogger();
//    private static final Map<ResourceLocation, LootTable> GENERATED_TABLES = new ConcurrentHashMap<>();
//
//    private ConvertibleLootTableGenerator() {}
//
//    public static void rebuildConvertibleLootTables() {
//        GENERATED_TABLES.clear();
//        LOGGER.info("[ConvertibleLoot] 开始构建虚拟战利品表, CONVERTIBLE_MAP 大小: {}", ItemUnificationSolver.CONVERTIBLE_MAP.size());
//
//        for (Map.Entry<String, List<String>> entry : ItemUnificationSolver.CONVERTIBLE_MAP.entrySet()) {
//            String groupKey = entry.getKey();
//            List<String> itemIds = entry.getValue();
//            if (itemIds == null || itemIds.isEmpty()) continue;
//
//            LootPool.Builder poolBuilder = LootPool.lootPool()
//                    .name("convertible_pool")
//                    .setRolls(ConstantValue.exactly(1.0F));
//
//            int validItemCount = 0;
//            for (String itemId : itemIds) {
//                ResourceLocation itemRl = ResourceLocation.tryParse(itemId);
//                if (itemRl != null && ForgeRegistries.ITEMS.containsKey(itemRl)) {
//                    Item item = ForgeRegistries.ITEMS.getValue(itemRl);
//                    if (item != null) {
//                        poolBuilder.add(LootItem.lootTableItem(item).setWeight(1));
//                        validItemCount++;
//                    }
//                }
//            }
//
//            if (validItemCount > 0) {
//                ResourceLocation tableId = new ResourceLocation(better_loot_zibura.MOD_ID, "convertible/" + groupKey);
//                // 绑定通用的 paramSet (ALL_PARAMS 或 CHEST)
//                LootTable lootTable = LootTable.lootTable()
//                        .setParamSet(LootContextParamSets.ALL_PARAMS)
//                        .withPool(poolBuilder)
//                        .build();
//
//                GENERATED_TABLES.put(tableId, lootTable);
//            }
//        }
//        LOGGER.info("[ConvertibleLoot] 虚拟表构建完成，共生成 {} 张表", GENERATED_TABLES.size());
//    }
//
//    public static void injectIntoServer(MinecraftServer server) {
//        if (GENERATED_TABLES.isEmpty()) {
//            rebuildConvertibleLootTables();
//        }
//
//        LootDataManager lootDataManager = server.getLootData();
//        try {
//            Field elementsField = null;
//            for (Field field : LootDataManager.class.getDeclaredFields()) {
//                if (Map.class.isAssignableFrom(field.getType())) {
//                    elementsField = field;
//                    break;
//                }
//            }
//
//            if (elementsField != null) {
//                elementsField.setAccessible(true);
//                @SuppressWarnings("unchecked")
//                Map<LootDataId<?>, Object> originalMap = (Map<LootDataId<?>, Object>) elementsField.get(lootDataManager);
//
//                Map<LootDataId<?>, Object> mutableMap = new HashMap<>(originalMap);
//
//                GENERATED_TABLES.forEach((id, table) -> {
//                    // 1.20+ 正确 Key 构造：使用 LootDataType.TABLE 与 ResourceLocation 组装 LootDataId
//                    LootDataId<LootTable> dataId = new LootDataId<>(LootDataType.TABLE, id);
//
//                    // 触发验证，防止未初始化/冻结属性导致原版抽空
//                    ValidationContext validationContext = new ValidationContext(LootContextParamSets.ALL_PARAMS, lootDataManager);
//                    table.validate(validationContext);
//
//                    mutableMap.put(dataId, table);
//                    LOGGER.info("[ConvertibleLoot] 成功以 LootDataId 形式注入表: {}", id);
//                });
//
//                elementsField.set(lootDataManager, Map.copyOf(mutableMap));
//            } else {
//                LOGGER.error("[ConvertibleLoot] 未找到 LootDataManager 的 elements 字段");
//            }
//        } catch (Exception e) {
//            LOGGER.error("[ConvertibleLoot] 反射注入虚拟表失败:", e);
//        }
//    }
//
//    public static Map<ResourceLocation, LootTable> getGeneratedTables() {
//        return Collections.unmodifiableMap(GENERATED_TABLES);
//    }
//}



package com.zibura.better_loot_zibura.loot.unification;

import com.zibura.better_loot_zibura.better_loot_zibura;
import com.zibura.better_loot_zibura.config.BetterLootConfig;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.storage.loot.LootDataId;
import net.minecraft.world.level.storage.loot.LootDataManager;
import net.minecraft.world.level.storage.loot.LootDataType;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraftforge.registries.ForgeRegistries;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class ConvertibleLootTableGenerator {

    private static final Map<ResourceLocation, LootTable> GENERATED_TABLES = new ConcurrentHashMap<>();

    private ConvertibleLootTableGenerator() {}

    public static void rebuildConvertibleLootTables() {
        GENERATED_TABLES.clear();
        if (!BetterLootConfig.ENABLE_CONVERTIBLE_LOOT_TABLES.get()) {
            return;
        }

        for (Map.Entry<String, List<String>> entry : ItemUnificationSolver.CONVERTIBLE_MAP.entrySet()) {
            String groupKey = entry.getKey();
            List<String> itemIds = entry.getValue();
            if (itemIds == null || itemIds.isEmpty()) continue;

            LootPool.Builder poolBuilder = LootPool.lootPool()
                    .name("convertible_pool")
                    .setRolls(ConstantValue.exactly(1.0F));

            int validItemCount = 0;
            for (String itemId : itemIds) {
                ResourceLocation itemRl = ResourceLocation.tryParse(itemId);
                if (itemRl != null && ForgeRegistries.ITEMS.containsKey(itemRl)) {
                    Item item = ForgeRegistries.ITEMS.getValue(itemRl);
                    if (item != null) {
                        poolBuilder.add(LootItem.lootTableItem(item).setWeight(1));
                        validItemCount++;
                    }
                }
            }

            if (validItemCount > 0) {
                ResourceLocation tableId = ResourceLocation.fromNamespaceAndPath(better_loot_zibura.MOD_ID, "convertible/" + groupKey);
                LootTable lootTable = LootTable.lootTable()
                        .setParamSet(LootContextParamSets.ALL_PARAMS)
                        .withPool(poolBuilder)
                        .build();

                GENERATED_TABLES.put(tableId, lootTable);
            }
        }
    }

    public static void injectIntoServer(MinecraftServer server) {
        if (!BetterLootConfig.ENABLE_CONVERTIBLE_LOOT_TABLES.get()) {
            return;
        }

        if (GENERATED_TABLES.isEmpty()) {
            rebuildConvertibleLootTables();
        }

        LootDataManager lootDataManager = server.getLootData();
        try {
            Field elementsField = null;
            for (Field field : LootDataManager.class.getDeclaredFields()) {
                if (Map.class.isAssignableFrom(field.getType())) {
                    elementsField = field;
                    break;
                }
            }

            if (elementsField != null) {
                elementsField.setAccessible(true);
                @SuppressWarnings("unchecked")
                Map<LootDataId<?>, Object> originalMap = (Map<LootDataId<?>, Object>) elementsField.get(lootDataManager);

                Map<LootDataId<?>, Object> mutableMap = new HashMap<>(originalMap);

                GENERATED_TABLES.forEach((id, table) -> {
                    LootDataId<LootTable> dataId = new LootDataId<>(LootDataType.TABLE, id);
                    ValidationContext validationContext = new ValidationContext(LootContextParamSets.ALL_PARAMS, lootDataManager);
                    table.validate(validationContext);
                    mutableMap.put(dataId, table);
                });

                elementsField.set(lootDataManager, Map.copyOf(mutableMap));
            }
        } catch (Exception ignored) {}
    }

    public static Map<ResourceLocation, LootTable> getGeneratedTables() {
        return Collections.unmodifiableMap(GENERATED_TABLES);
    }
}