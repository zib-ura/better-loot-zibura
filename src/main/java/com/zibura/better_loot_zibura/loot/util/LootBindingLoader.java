package com.zibura.better_loot_zibura.loot.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.logging.LogUtils;
import com.zibura.better_loot_zibura.loot.model.AllLevelModel.GroupDTO;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class LootBindingLoader {

    private static final Logger LOGGER = LogUtils.getLogger();

    private static final Map<String, JsonObject> TARGET_BINDINGS =
            new LinkedHashMap<>();

    private LootBindingLoader() {}

    public static void clear() {
        TARGET_BINDINGS.clear();
    }

    public static void collectBindings(JsonElement jsonElement) {
        if (jsonElement == null || !jsonElement.isJsonArray()) {
            return;
        }

        for (JsonElement bindingElement : jsonElement.getAsJsonArray()) {
            if (!bindingElement.isJsonObject()) {
                continue;
            }

            JsonObject binding = bindingElement.getAsJsonObject();

            if (!binding.has("target")
                    || !binding.get("target").isJsonPrimitive()) {
                continue;
            }

            String target = binding.get("target").getAsString();
            Identifier id = Identifier.tryParse(target);

            if (id == null) {
                LOGGER.warn("Invalid loot table target: {}", target);
                continue;
            }

            String namespace = id.getNamespace();

            // 可选 Mod 不存在时静默跳过
            if (!"minecraft".equals(namespace)
                    && !FabricLoader.getInstance().isModLoaded(namespace)) {
                continue;
            }

            // 后加载配置覆盖前面的同 target 配置
            TARGET_BINDINGS.put(target, binding);
        }
    }

    public static void applyBinding(
            Identifier tableId,
            LootTable.Builder tableBuilder,
            RegistryWrapper.WrapperLookup registries
    ) {
        JsonObject binding = TARGET_BINDINGS.get(tableId.toString());

        if (binding == null) {
            return;
        }

        try {
            processTableWeight(
                    tableBuilder,
                    registries,
                    binding
            );
        } catch (Exception e) {
            LOGGER.error(
                    "Error processing loot binding for target {}: {}",
                    tableId,
                    binding,
                    e
            );
        }
    }

    private static void processTableWeight(
            LootTable.Builder tableBuilder,
            RegistryWrapper.WrapperLookup registries,
            JsonObject binding
    ) {
        JsonArray lootArray =
                LootEvaluationContext.resolveContent(binding.get("config"));

        if (lootArray == null || lootArray.isEmpty()) {
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
            RegistryWrapper.WrapperLookup registries,
            JsonElement contentElement,
            int totalWeight,
            int minTime,
            int maxTime
    ) {
        JsonArray contentArray =
                LootEvaluationContext.resolveContent(contentElement);

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

            List<GroupDTO> resolvedGroups =
                    LootEvaluationContext.resolveGroupList(groupElement);

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