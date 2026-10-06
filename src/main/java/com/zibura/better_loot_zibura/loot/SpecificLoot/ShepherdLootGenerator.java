package com.zibura.better_loot_zibura.loot.SpecificLoot;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.zibura.better_loot_zibura.loot.util.LootEvaluationContext;

import java.util.*;

public class ShepherdLootGenerator {

    /**
     * 核心注册入口：动态解析 JSON 中的群系与模板，生成 village_shepherd_content 并注册到 Context
     */
    public static void initShepherdTemplates() {
        // 1. 读取对应驼峰 Key 的模板数组
        JsonArray woolTemplates = getTemplateArraySafe("shepherdWoolTemplates");
        JsonArray dyeTemplates = getTemplateArraySafe("shepherdDyeTemplates");

        // 2. 读取群系颜色字典
        Map<String, List<String>> biomeColors = getBiomeColorsSafe("shepherdBiomeColors");

        // 3. 读取群系条件列表 (对应 JSON 的 biomeList)
        JsonArray biomeList = getTemplateArraySafe("biomeList");

        JsonArray shepherdContent = new JsonArray();

        // 4. 遍历群系列表，为每个群系生成羊毛组与染料组
        for (JsonElement element : biomeList) {
            if (!element.isJsonArray()) continue;
            JsonArray entry = element.getAsJsonArray();
            if (entry.size() < 2) continue;

            String biomeKey = entry.get(0).getAsString();
            JsonObject conditionObj = entry.get(1).getAsJsonObject();

            List<String> colors = biomeColors.getOrDefault(biomeKey, List.of("white"));

            // 羊毛组
            JsonArray woolGroup = createBiomeGroup(biomeKey, "wool", woolTemplates, colors, 100, 1, 2, true);
            shepherdContent.add(createContentEntry(woolGroup, 1, 1, conditionObj));

            // 染料组
            JsonArray dyeGroup = createBiomeGroup(biomeKey, "dye", dyeTemplates, colors, 100, 1, 2, false);
            shepherdContent.add(createContentEntry(dyeGroup, 2, 2, conditionObj));
        }

        // 5. 追加共享装备组与材料组 (引用驼峰命名的配置 key)
        shepherdContent.add(createContentEntry(new JsonPrimitive("shepherdEquipmentGroup"), 0, 1, null));
        shepherdContent.add(createContentEntry(new JsonPrimitive("shepherdMaterialGroup"), 1, 2, null));

        // 6. 注册进全局 Context
        JsonObject registry = new JsonObject();
        registry.add("village_shepherd_content", shepherdContent);
        LootEvaluationContext.registerAll(registry);
    }

    /**
     * 动态替换 [COLOR] 并注入特定群系（如 plains）的额外物品
     */
    private static JsonArray createBiomeGroup(String biome, String groupName, JsonArray templates, List<String> colors, double groupWeight, int min, int max, boolean isWool) {
        JsonArray items = new JsonArray();

        for (String color : colors) {
            for (JsonElement elem : templates) {
                if (!elem.isJsonObject()) continue;
                JsonObject itemObj = elem.getAsJsonObject().deepCopy();

                if (itemObj.has("id")) {
                    String rawId = itemObj.get("id").getAsString();
                    if (rawId.contains("[COLOR]")) {
                        itemObj.addProperty("id", rawId.replace("[COLOR]", color));
                    }
                }
                items.add(itemObj);
            }
        }

        // 平原专属额外物品注入
        if ("plains".equals(biome) && isWool) {
            JsonObject extra1 = new JsonObject();
            extra1.addProperty("id", "brewery:patterned_wool");
            extra1.addProperty("ratio", 10);
            items.add(extra1);

            JsonObject extra2 = new JsonObject();
            extra2.addProperty("id", "brewery:patterned_carpet");
            extra2.addProperty("ratio", 10);
            items.add(extra2);
        }

        JsonObject groupObj = new JsonObject();
        groupObj.addProperty("groupName", groupName);
        groupObj.addProperty("groupWeight", groupWeight);
        groupObj.addProperty("min", min);
        groupObj.addProperty("max", max);
        groupObj.add("items", items);

        JsonArray groups = new JsonArray();
        groups.add(groupObj);
        return groups;
    }

    private static JsonArray createContentEntry(JsonElement group, int minRolls, int maxRolls, JsonObject condition) {
        JsonArray entry = new JsonArray();
        entry.add(group);
        entry.add(minRolls);
        entry.add(maxRolls);
        if (condition != null) {
            entry.add(condition);
        }
        return entry;
    }

    private static JsonArray getTemplateArraySafe(String key) {
        try {
            JsonElement elem = LootEvaluationContext.resolveElement(new JsonPrimitive(key));
            if (elem != null && elem.isJsonArray()) {
                return elem.getAsJsonArray();
            }
        } catch (Exception ignored) {}
        return new JsonArray();
    }

    private static Map<String, List<String>> getBiomeColorsSafe(String key) {
        Map<String, List<String>> map = new HashMap<>();
        try {
            JsonElement elem = LootEvaluationContext.resolveElement(new JsonPrimitive(key));
            if (elem != null && elem.isJsonObject()) {
                JsonObject obj = elem.getAsJsonObject();
                for (Map.Entry<String, JsonElement> entry : obj.entrySet()) {
                    List<String> colorList = new ArrayList<>();
                    if (entry.getValue().isJsonArray()) {
                        for (JsonElement c : entry.getValue().getAsJsonArray()) {
                            colorList.add(c.getAsString());
                        }
                    }
                    map.put(entry.getKey(), colorList);
                }
            }
        } catch (Exception ignored) {}
        return map;
    }
}