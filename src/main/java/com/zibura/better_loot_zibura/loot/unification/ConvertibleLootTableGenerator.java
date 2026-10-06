package com.zibura.better_loot_zibura.loot.unification;

import com.zibura.better_loot_zibura.better_loot_zibura;
import com.zibura.better_loot_zibura.config.BetterLootConfig;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.item.Item;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
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
                Identifier tableId = Identifier.of(better_loot_zibura.MOD_ID, "convertible/" + groupKey);
                LootTable lootTable = LootTable.builder()
                        .type(LootContextTypes.GENERIC)
                        .pool(poolBuilder)
                        .build();

                GENERATED_TABLES.put(tableId, lootTable);
            }
        }
        LOGGER.info("Successfully built {} convertible loot tables", GENERATED_TABLES.size());
    }

    public static void init() {
        LootTableEvents.ALL_LOADED.register((resourceManager, registry) -> {
            if (!BetterLootConfig.enableLootTables) {
                GENERATED_TABLES.clear();
                return;
            }

            rebuildConvertibleLootTables();

            int injected = 0;

            for (Map.Entry<Identifier, LootTable> entry : GENERATED_TABLES.entrySet()) {
                Identifier id = entry.getKey();
                LootTable table = entry.getValue();

                if (registry.containsId(id)) {
                    LOGGER.warn(
                            "Skipping generated convertible loot table {} because it already exists",
                            id
                    );
                    continue;
                }

                RegistryKey<LootTable> key =
                        RegistryKey.of(RegistryKeys.LOOT_TABLE, id);

                Registry.register(registry, key, table);
                injected++;
            }

            LOGGER.info(
                    "Injected {} convertible loot tables",
                    injected
            );
        });
    }

    public static Map<Identifier, LootTable> getGeneratedTables() {
        return Collections.unmodifiableMap(GENERATED_TABLES);
    }
}