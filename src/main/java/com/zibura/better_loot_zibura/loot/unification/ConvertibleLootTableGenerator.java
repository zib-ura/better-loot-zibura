package com.zibura.better_loot_zibura.loot.unification;

import com.zibura.better_loot_zibura.better_loot_zibura;
import com.zibura.better_loot_zibura.config.BetterLootConfig;
import net.minecraft.item.Item;
import net.minecraft.loot.LootDataKey;
import net.minecraft.loot.LootDataType;
import net.minecraft.loot.LootManager;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.registry.Registries;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class ConvertibleLootTableGenerator {
    private static final Logger LOGGER = LoggerFactory.getLogger("ConvertibleLoot");
    private static final Map<Identifier, LootTable> GENERATED_TABLES = new ConcurrentHashMap<>();

    private ConvertibleLootTableGenerator() {}

    public static void rebuildConvertibleLootTables() {
        GENERATED_TABLES.clear();
        if (!BetterLootConfig.enableLootTables) {
            return;
        }

        // 假定 ItemUnificationSolver 也是 Map<String, List<String>> 结构
        for (Map.Entry<String, List<String>> entry : ItemUnificationSolver.CONVERTIBLE_MAP.entrySet()) {
            String groupKey = entry.getKey();
            List<String> itemIds = entry.getValue();
            if (itemIds == null || itemIds.isEmpty()) continue;

            LootPool.Builder poolBuilder = LootPool.builder()
                    .rolls(ConstantLootNumberProvider.create(1.0F));

            int validItemCount = 0;
            for (String itemId : itemIds) {
                Identifier itemIdentifier = Identifier.tryParse(itemId);
                if (itemIdentifier != null && Registries.ITEM.containsId(itemIdentifier)) {
                    Item item = Registries.ITEM.get(itemIdentifier);
                    if (item != null) {
                        poolBuilder.with(ItemEntry.builder(item).weight(1));
                        validItemCount++;
                    }
                }
            }

            if (validItemCount > 0) {
                Identifier tableId = new Identifier(better_loot_zibura.MOD_ID, "convertible/" + groupKey);
                LootTable lootTable = LootTable.builder()
                        .type(LootContextTypes.GENERIC) // 或使用 LootContextTypes.CHEST / ALL 等
                        .pool(poolBuilder)
                        .build();

                GENERATED_TABLES.put(tableId, lootTable);
            }
        }
        LOGGER.info("Successfully built {} convertible loot tables", GENERATED_TABLES.size());
    }

    @SuppressWarnings("unchecked")
    public static void injectIntoServer(MinecraftServer server) {
        if (!BetterLootConfig.enableLootTables) {
            return;
        }

        if (GENERATED_TABLES.isEmpty()) {
            rebuildConvertibleLootTables();
        }

        // 1.20.1 Yarn 下是 getLootManager() 返回 LootManager
        LootManager lootManager = server.getLootManager();
        try {
            Field elementsField = null;
            // 查找存储 Map<LootDataKey<?>, Object> 的内部字段 (在 Yarn 中通常叫 keyToValue)
            for (Field field : LootManager.class.getDeclaredFields()) {
                if (Map.class.isAssignableFrom(field.getType())) {
                    elementsField = field;
                    break;
                }
            }

            if (elementsField != null) {
                elementsField.setAccessible(true);
                Map<LootDataKey<?>, Object> originalMap = (Map<LootDataKey<?>, Object>) elementsField.get(lootManager);
                Map<LootDataKey<?>, Object> mutableMap = new HashMap<>(originalMap);

                GENERATED_TABLES.forEach((id, table) -> {
                    // 使用 LootDataKey 和 LootDataType.LOOT_TABLES
                    LootDataKey<LootTable> key = new LootDataKey<>(LootDataType.LOOT_TABLES, id);
                    mutableMap.put(key, table);
                });

                elementsField.set(lootManager, Map.copyOf(mutableMap));
                LOGGER.info("Successfully injected {} loot tables into LootManager", GENERATED_TABLES.size());
            } else {
                LOGGER.warn("Failed to find 'keyToValue' map in LootManager");
            }
        } catch (Exception e) {
            LOGGER.error("Failed to reflectively inject loot tables: ", e);
        }
    }

    public static Map<Identifier, LootTable> getGeneratedTables() {
        return Collections.unmodifiableMap(GENERATED_TABLES);
    }
}