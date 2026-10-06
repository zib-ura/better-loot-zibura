package com.zibura.better_loot_zibura.loot.util;

import com.google.gson.*;
import com.mojang.logging.LogUtils;
import com.zibura.better_loot_zibura.loot.model.AllLevelModel.GroupDTO;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.storage.loot.LootPool;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.event.LootTableLoadEvent;
import org.slf4j.Logger;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class LootBindingLoader {
    private static final Logger LOGGER = LogUtils.getLogger();

    // 暂存所有 target 对应的最新绑定配置，按 target 覆盖
    private static final Map<String, JsonObject> TARGET_BINDINGS = new LinkedHashMap<>();


    public static void clear() {
        TARGET_BINDINGS.clear();
    }

    /**
     * 收集阶段：同名 target 会直接 put 覆盖前面的配置
     */
    public static void collectBindings(JsonElement jsonElement) {
        if (jsonElement == null || !jsonElement.isJsonArray()) return;

        for (JsonElement bElem : jsonElement.getAsJsonArray()) {
            if (!bElem.isJsonObject()) continue;
            JsonObject bObj = bElem.getAsJsonObject();
            if (bObj.has("target") && bObj.get("target").isJsonPrimitive()) {
                String target = bObj.get("target").getAsString();

                Identifier id = Identifier.tryParse(target);
                if (id == null) {
                    LOGGER.warn("Invalid loot table target: {}", target);
                    continue;
                }

                String namespace = id.getNamespace();

                // 非 minecraft 的战利品表：对应模组没加载就直接忽略
                if (!"minecraft".equals(namespace)
                        && !ModList.get().isLoaded(namespace)) {
                    LOGGER.debug(
                            "Skipping loot binding {} because mod '{}' is not loaded",
                            target,
                            namespace
                    );
                    continue;
                }

                // 后加载的相同 target 覆盖之前的值
                TARGET_BINDINGS.put(target, bObj);
            }
        }
    }


    public static void applyBinding(LootTableLoadEvent event) {
        String target = event.getName().toString();

        JsonObject binding = TARGET_BINDINGS.get(target);
        if (binding == null) {
            return;
        }

        try {
            processTableWeight(event, binding);
        } catch (Exception e) {
            LOGGER.error(
                    "Error processing loot binding for target {}: {}",
                    target,
                    binding,
                    e
            );
        }
    }

    private static void processTableWeight(
            LootTableLoadEvent event,
            JsonObject bObj
    ) {
        JsonArray lootArray =
                LootEvaluationContext.resolveContent(bObj.get("config"));

        if (lootArray == null || lootArray.isEmpty()) {
            return;
        }

        int totalWeight = 0;

        for (JsonElement lootEntry : lootArray) {
            if (!lootEntry.isJsonArray()) {
                continue;
            }

            int weight =
                    parseWeight(lootEntry.getAsJsonArray());

            if (weight > 0) {
                totalWeight += weight;
            }
        }

        if (totalWeight <= 0) {
            LOGGER.warn(
                    "No positive loot binding weights for target {}",
                    event.getName()
            );
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
                    event,
                    array.get(0),
                    totalWeight,
                    minTime,
                    maxTime
            );
        }
    }

    private static void finalProcessLootContent(
            LootTableLoadEvent event,
            JsonElement contentElem,
            int totalWeight,
            int minTime,
            int maxTime
    ) {
        JsonArray contentArray =
                LootEvaluationContext.resolveContent(contentElem);

        if (contentArray == null) {
            return;
        }

        for (JsonElement subEntry : contentArray) {
            if (!subEntry.isJsonArray()) {
                continue;
            }

            JsonArray cArr = subEntry.getAsJsonArray();

            if (cArr.size() < 3) {
                continue;
            }

            JsonElement groupElem = cArr.get(0);

            int minRolls = cArr.get(1).getAsInt();
            int maxRolls = cArr.get(2).getAsInt();

            JsonObject mergedConditions =
                    mergeTimeConditions(
                            cArr,
                            totalWeight,
                            minTime,
                            maxTime
                    );

            List<GroupDTO> resolvedGroups =
                    LootEvaluationContext.resolveGroupList(groupElem);

            LootPool pool =
                    InjectFinalPools.buildLootPool(
                            resolvedGroups,
                            minRolls,
                            maxRolls,
                            mergedConditions,
                            event.getRegistries()
                    );

            if (pool != null) {
                event.getTable().addPool(pool);
            }
        }
    }

    private static JsonObject mergeTimeConditions(JsonArray cArr, int totalWeight, int minTime, int maxTime) {
        JsonArray timeParams = new JsonArray();
        timeParams.add(totalWeight);
        timeParams.add(minTime);
        timeParams.add(maxTime);

        JsonObject mergedConditions = new JsonObject();
        mergedConditions.add("matchTime", timeParams);

        if (cArr.size() > 3 && cArr.get(3).isJsonObject()) {
            JsonObject extraConditions = cArr.get(3).getAsJsonObject();
            for (Map.Entry<String, JsonElement> prop : extraConditions.entrySet()) {
                mergedConditions.add(prop.getKey(), prop.getValue());
            }
        }
        return mergedConditions;
    }

    private static int parseWeight(JsonArray array) {
        if (array.size() > 1 && array.get(1).isJsonPrimitive() && array.get(1).getAsJsonPrimitive().isNumber()) {
            return array.get(1).getAsInt();
        }
        return 1;
    }
}