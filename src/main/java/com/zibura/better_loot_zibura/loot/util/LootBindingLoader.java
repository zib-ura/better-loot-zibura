package com.zibura.better_loot_zibura.loot.util;

import com.google.gson.*;
import com.mojang.logging.LogUtils;
import com.zibura.better_loot_zibura.loot.model.AllLevelModel.GroupDTO;
import com.zibura.better_loot_zibura.loot.model.AllLevelModel.ItemDTO;
import com.google.gson.reflect.TypeToken;
import java.util.ArrayList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.core.RegistryAccess;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.event.LootTableLoadEvent;
import org.slf4j.Logger;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class LootBindingLoader {
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final Gson GSON = new Gson();

    // 暂存所有 target 对应的最新绑定配置，按 target 覆盖
    private static final Map<String, JsonObject> TARGET_BINDINGS = new LinkedHashMap<>();


    public static void clear() {
        TARGET_BINDINGS.clear();
    }

    /**
     * 收集阶段：同名 target 会直接 put 覆盖前面的配置
     */
    public static void collectBindings(JsonElement jsonElement) {
        if (jsonElement == null || jsonElement.isJsonNull()) return;
        if (jsonElement.isJsonObject()) {
            collectSingleBinding(jsonElement.getAsJsonObject());
        } else if (jsonElement.isJsonArray()) {
            for (JsonElement element : jsonElement.getAsJsonArray()) {
                if (element.isJsonObject()) collectSingleBinding(element.getAsJsonObject());
            }
        } else {
            LOGGER.warn("Expected loot binding object or array, got {}", jsonElement);
        }
    }

    private static void collectSingleBinding(JsonObject binding) {
        if (!binding.has("target") || !binding.get("target").isJsonPrimitive()
                || !binding.get("target").getAsJsonPrimitive().isString()) return;
        String target = binding.get("target").getAsString();
        ResourceLocation id = ResourceLocation.tryParse(target);
        if (id == null) {
            LOGGER.warn("Invalid loot table target: {}", target);
            return;
        }
        String namespace = id.getNamespace();
//        if (!"minecraft".equals(namespace) && !ModList.get().isLoaded(namespace)) {
////            LOGGER.debug("Skipping loot binding {} because mod '{}' is not loaded", target, namespace);
//            return;
//        }
        TARGET_BINDINGS.put(target, binding);
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
        JsonElement config = bObj.get("config");
        if (config == null || !config.isJsonArray()) {
            LOGGER.warn("Expected pre-expanded config array for {}", bObj.get("target"));
            return;
        }
        JsonArray lootArray = config.getAsJsonArray();

        if (lootArray == null || lootArray.isEmpty()) {
            return;
        }

        int totalWeight = 0;

        for (JsonElement lootEntry : lootArray) {
            if (lootEntry.isJsonArray()) {
                int weight = parseWeight(lootEntry.getAsJsonArray());

                if (weight > 0) {
                    totalWeight += weight;
                }
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

            JsonArray lArr = lootEntry.getAsJsonArray();

            if (lArr.isEmpty()) {
                continue;
            }

            int weight = parseWeight(lArr);

            if (weight <= 0) {
                continue;
            }

            int minTime = currentOffset;
            int maxTime = currentOffset + weight - 1;

            currentOffset += weight;

            finalProcessLootContent(
                    event,
                    lArr.get(0),
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
        if (contentElem == null || !contentElem.isJsonArray()) {
            LOGGER.warn("Expected pre-expanded content array");
            return;
        }
        JsonArray contentArray = contentElem.getAsJsonArray();

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

            List<GroupDTO> resolvedGroups = parseGroups(groupElem);

            RegistryAccess.Frozen registries = AllDataLoader.getRegistryAccess();
            if (registries == null) {
                LOGGER.warn("Cannot inject loot pool: server registry access is unavailable for {}", event.getName());
                continue;
            }

            LootPool pool = InjectFinalPools.buildLootPool(
                    resolvedGroups,
                    minRolls,
                    maxRolls,
                    mergedConditions,
                    registries
            );

            if (pool != null) {
                event.getTable().addPool(pool);
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
            if (obj.has("jsonFunction")) group.jsonFunction = obj.getAsJsonArray("jsonFunction");
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