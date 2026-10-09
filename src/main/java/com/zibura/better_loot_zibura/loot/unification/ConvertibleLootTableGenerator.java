package com.zibura.better_loot_zibura.loot.unification;

import com.zibura.better_loot_zibura.better_loot_zibura;
import com.zibura.better_loot_zibura.config.BetterLootConfig;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.providers.number.ints.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProviders;

import java.util.List;

/**
 * Minecraft 26.2 + Fabric API + Mojang mappings.
 *
 * This class modifies convertible loot tables while they are loading.
 * It deliberately does not mutate the loot-table registry from ALL_LOADED:
 * Fabric API documents ALL_LOADED as post-processing/inspection after
 * REPLACE and MODIFY have completed.
 *
 * Expected table id:
 *   better_loot_zibura:convertible/<groupKey>
 *
 * The corresponding loot-table resource must exist so that MODIFY is fired
 * for that key.
 */
public final class ConvertibleLootTableGenerator {
    private static boolean initialized;

    private ConvertibleLootTableGenerator() {
    }

    public static void init() {
        if (initialized) {
            return;
        }
        initialized = true;

        LootTableEvents.MODIFY.register((key, tableBuilder, source, registries) -> {
            if (!BetterLootConfig.enableLootTables) {
                return;
            }

            Identifier tableId = key.identifier();
            if (!better_loot_zibura.MOD_ID.equals(tableId.getNamespace())) {
                return;
            }

            String prefix = "convertible/";
            String path = tableId.getPath();
            if (!path.startsWith(prefix)) {
                return;
            }

            String groupKey = path.substring(prefix.length());
            List<String> itemIds = ItemUnificationSolver.CONVERTIBLE_MAP.get(groupKey);
            if (itemIds == null || itemIds.isEmpty()) {
                return;
            }

            LootPool.Builder pool = LootPool.lootPool()
                    .setRolls(ContextIntProviders.exactly(1));

            int validItems = 0;

            for (String rawId : itemIds) {
                Identifier itemId = Identifier.tryParse(rawId);
                if (itemId == null || !BuiltInRegistries.ITEM.containsKey(itemId)) {
                    continue;
                }

                Item item = BuiltInRegistries.ITEM.getValue(itemId);
                if (item == null) {
                    continue;
                }

                pool.add(LootItem.lootTableItem(item).setWeight(1));
                validItems++;
            }

            if (validItems > 0) {
                tableBuilder.withPool(pool);
            }
        });
    }
}
