package com.zibura.better_loot_zibura.loot.util;

import com.google.gson.*;
import com.mojang.logging.LogUtils;
import com.zibura.better_loot_zibura.loot.model.AllLevelModel.GroupDTO;
import org.slf4j.Logger;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class LootBindingLoader {
    private static final Gson GSON = new GsonBuilder().create();
    private static final Logger LOGGER = LogUtils.getLogger();

    // 暂存所有 target 对应的最新绑定配置，按 target 覆盖
    private static final Map<String, JsonObject> LOOT_BINDINGS = new LinkedHashMap<>();


    public static void clear() {
        LOOT_BINDINGS.clear();
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
                // 核心：后加载的相同 target 直接覆盖之前的值
                LOOT_BINDINGS.put(target, bObj);
            }
        }
    }

    /**
     * 提交阶段：将最终去重覆盖后的 bindings 统一提交给 InjectFinalPools
     */
    public static void commitAllBindings() {
        for (Map.Entry<String, JsonObject> entry : LOOT_BINDINGS.entrySet()) {
            try {
                ProcessTableWeight(entry.getKey(), entry.getValue());
            } catch (Exception e) {
                LOGGER.error("Error processing loot binding for target {}: {}", entry.getKey(), entry.getValue(), e);
            }
        }
    }

    private static void ProcessTableWeight(String target, JsonObject bObj) {
        JsonArray lootArray = MixedDataResolver.resolveContent(bObj.get("config"));
        if (lootArray == null || lootArray.isEmpty()) return;

        // 1. 计算总权重
        int totalWeight = 0;
        for (JsonElement lootEntry : lootArray) {
            if (lootEntry.isJsonArray()) {
                int weight = parseWeight(lootEntry.getAsJsonArray());
                if (weight > 0) totalWeight += weight;
            }
        }

        if (totalWeight <= 0) {
            LOGGER.warn("No positive loot binding weights for target {}", target);
            return;
        }

        // 2. 遍历时间片区间
        int currentOffset = 0;
        for (JsonElement lootEntry : lootArray) {
            if (!lootEntry.isJsonArray()) continue;
            JsonArray lArr = lootEntry.getAsJsonArray();
            if (lArr.isEmpty()) continue;

            int weight = parseWeight(lArr);
            if (weight <= 0) {
                LOGGER.warn("Ignoring loot binding with non-positive weight {} for target {}: {}", weight, target, lArr);
                continue;
            }
            int minTime = currentOffset;
            int maxTime = currentOffset + weight - 1;
            currentOffset += weight;

            FinalProcessLootContent(target, lArr.get(0), totalWeight, minTime, maxTime);
        }
    }

    private static void FinalProcessLootContent(String target, JsonElement contentElem, int totalWeight, int minTime, int maxTime) {
        JsonArray contentArray = MixedDataResolver.resolveContent(contentElem);
        if (contentArray == null) return;

        for (JsonElement subEntry : contentArray) {
            if (!subEntry.isJsonArray()) continue;
            JsonArray cArr = subEntry.getAsJsonArray();
            if (cArr.size() < 3) continue;

            InjectFinalPools.recordExpectedPool(target);

            JsonElement groupElem = cArr.get(0);
            int minRolls = cArr.get(1).getAsInt();
            int maxRolls = cArr.get(2).getAsInt();

            JsonObject mergedConditions = MergeTimeConditions(cArr, totalWeight, minTime, maxTime);
            List<GroupDTO> resolvedGroups = MixedDataResolver.resolveGroupList(groupElem);

            InjectFinalPools.addCustomLoot(target, resolvedGroups, minRolls, maxRolls, mergedConditions);
        }
    }

    private static JsonObject MergeTimeConditions(JsonArray cArr, int totalWeight, int minTime, int maxTime) {
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