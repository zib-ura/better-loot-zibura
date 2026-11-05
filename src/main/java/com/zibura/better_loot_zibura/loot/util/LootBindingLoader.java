package com.zibura.better_loot_zibura.loot.util;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.mojang.logging.LogUtils;
import com.zibura.better_loot_zibura.loot.model.AllLevelModel.GroupDTO;
import com.zibura.better_loot_zibura.loot.model.AllLevelModel.ItemDTO;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.Identifier;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class LootBindingLoader {
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final Gson GSON = new Gson();

    // Snapshot publication: never clear the live map before a successful link.
    private static volatile Map<String, JsonObject> TARGET_BINDINGS = Map.of();

    private LootBindingLoader() {}

    public static void clear() {
        TARGET_BINDINGS = Map.of();
    }

    public static void replaceBindings(Map<String, JsonObject> compiled) {
        Map<String, JsonObject> next = new LinkedHashMap<>();
        for (Map.Entry<String, JsonObject> entry : compiled.entrySet()) {
            String target = entry.getKey();
            if (Identifier.tryParse(target) == null) {
                throw new IllegalArgumentException("Invalid loot table target: " + target);
            }
            // Do not filter by namespace / loaded Mod: original Forge did not.
            next.put(target, entry.getValue().deepCopy());
        }
        TARGET_BINDINGS = Collections.unmodifiableMap(next);
        LOGGER.info("[BetterLoot] Binding snapshot replaced: {} entries", next.size());
    }

    public static void applyBinding(
            Identifier tableId,
            LootTable.Builder tableBuilder,
            HolderLookup.Provider registries
    ) {
        JsonObject binding = TARGET_BINDINGS.get(tableId.toString());
        if (binding == null) return;

        try {
            processTableWeight(tableBuilder, registries, binding);
        } catch (Exception ex) {
            LOGGER.error("Error processing loot binding for target {}: {}", tableId, binding, ex);
        }
    }

    private static void processTableWeight(
            LootTable.Builder tableBuilder,
            HolderLookup.Provider registries,
            JsonObject binding
    ) {
        JsonElement config = binding.get("config");
        if (config == null || !config.isJsonArray()) {
            LOGGER.warn("Expected pre-expanded config array for {}", binding.get("target"));
            return;
        }
        JsonArray lootArray = config.getAsJsonArray();

        if (lootArray.isEmpty()) {
            return;
        }

        int totalWeight = 0;

        for (JsonElement lootEntry : lootArray) {
            if (!lootEntry.isJsonArray()) {
                continue;
            }

            int weight = parseWeight(lootEntry.getAsJsonArray());

            if (weight > 0) {
                totalWeight += weight;
            }
        }

        if (totalWeight <= 0) {
            return;
        }

        int currentOffset = 0;

        for (JsonElement lootEntry : lootArray) {
            if (!lootEntry.isJsonArray()) {
                continue;
            }

            JsonArray array = lootEntry.getAsJsonArray();

            if (array.isEmpty()) {
                continue;
            }

            int weight = parseWeight(array);

            if (weight <= 0) {
                continue;
            }

            int minTime = currentOffset;
            int maxTime = currentOffset + weight - 1;

            currentOffset += weight;

            finalProcessLootContent(
                    tableBuilder,
                    registries,
                    array.get(0),
                    totalWeight,
                    minTime,
                    maxTime
            );
        }
    }

    private static void finalProcessLootContent(
            LootTable.Builder tableBuilder,
            HolderLookup.Provider registries,
            JsonElement contentElement,
            int totalWeight,
            int minTime,
            int maxTime
    ) {
        if (contentElement == null || !contentElement.isJsonArray()) {
            LOGGER.warn("Expected pre-expanded content array: {}", contentElement);
            return;
        }
        JsonArray contentArray = contentElement.getAsJsonArray();

        if (contentArray == null) {
            return;
        }

        for (JsonElement subEntry : contentArray) {
            if (!subEntry.isJsonArray()) {
                continue;
            }

            JsonArray array = subEntry.getAsJsonArray();

            if (array.size() < 3) {
                continue;
            }

            JsonElement groupElement = array.get(0);
            int minRolls = array.get(1).getAsInt();
            int maxRolls = array.get(2).getAsInt();

            JsonObject mergedConditions =
                    mergeTimeConditions(
                            array,
                            totalWeight,
                            minTime,
                            maxTime
                    );

            List<GroupDTO> resolvedGroups = parseGroups(groupElement);

            LootPool pool = InjectFinalPools.buildLootPool(
                    resolvedGroups,
                    minRolls,
                    maxRolls,
                    mergedConditions,
                    registries
            );

            if (pool != null) {
                tableBuilder.pool(pool);
            }
        }
    }


    /** Convert fully expanded group objects into DTOs; no registry lookups. */
    private static List<GroupDTO> parseGroups(JsonElement element) {
        if (element == null || !element.isJsonArray()) {
            throw new IllegalArgumentException("Expected expanded group array: " + element);
        }
        List<GroupDTO> result = new ArrayList<>();
        for (JsonElement entry : element.getAsJsonArray()) {
            if (!entry.isJsonObject()) {
                throw new IllegalArgumentException("Expected expanded group object: " + entry);
            }
            JsonObject obj = entry.getAsJsonObject();
            GroupDTO group = new GroupDTO();
            group.groupName = obj.has("groupName") ? obj.get("groupName").getAsString() : "default";
            group.groupWeight = obj.has("groupWeight") ? obj.get("groupWeight").getAsDouble() : 1.0;
            if (obj.has("min")) group.min = obj.get("min").getAsInt();
            if (obj.has("max")) group.max = obj.get("max").getAsInt();
            if (obj.has("damage")) group.damage = obj.get("damage");
            if (obj.has("enchantChance")) group.enchantChance = obj.get("enchantChance").getAsDouble();
            if (obj.has("enchantLevels")) group.enchantLevels = GSON.fromJson(obj.get("enchantLevels"), new TypeToken<List<Integer>>() {}.getType());
            if (obj.has("enchantRandomly")) group.enchantRandomly = GSON.fromJson(obj.get("enchantRandomly"), new TypeToken<List<String>>() {}.getType());
            if (obj.has("potion")) group.potion = obj.get("potion").getAsString();
            if (obj.has("jsonFunction")) group.jsonFunction = obj.get("jsonFunction");
            if (obj.has("exactEnchants")) group.exactEnchants = GSON.fromJson(obj.get("exactEnchants"), new TypeToken<Map<String, Integer>>() {}.getType());
            if (obj.has("nbt") && obj.get("nbt").isJsonObject()) group.nbt = obj.getAsJsonObject("nbt");
            if (obj.has("conditions")) group.conditions = GSON.fromJson(obj.get("conditions"), new TypeToken<Map<String, Object>>() {}.getType());
            if (obj.has("items")) {
                JsonElement items = obj.get("items");
                if (!items.isJsonArray()) throw new IllegalArgumentException("Expected expanded items array: " + items);
                group.items = new ArrayList<>();
                for (JsonElement item : items.getAsJsonArray()) {
                    if (!item.isJsonObject()) throw new IllegalArgumentException("Expected item object: " + item);
                    group.items.add(GSON.fromJson(item, ItemDTO.class));
                }
            }
            result.add(group);
        }
        return result;
    }

    private static JsonObject mergeTimeConditions(
            JsonArray array,
            int totalWeight,
            int minTime,
            int maxTime
    ) {
        JsonArray timeParams = new JsonArray();

        timeParams.add(totalWeight);
        timeParams.add(minTime);
        timeParams.add(maxTime);

        JsonObject mergedConditions = new JsonObject();
        mergedConditions.add("matchTime", timeParams);

        if (array.size() > 3 && array.get(3).isJsonObject()) {
            JsonObject extraConditions = array.get(3).getAsJsonObject();

            for (Map.Entry<String, JsonElement> property
                    : extraConditions.entrySet()) {
                mergedConditions.add(
                        property.getKey(),
                        property.getValue()
                );
            }
        }

        return mergedConditions;
    }

    private static int parseWeight(JsonArray array) {
        if (array.size() > 1
                && array.get(1).isJsonPrimitive()
                && array.get(1).getAsJsonPrimitive().isNumber()) {
            return array.get(1).getAsInt();
        }

        return 1;
    }
}