package com.zibura.better_loot_zibura.loot.SpecificLoot;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.zibura.better_loot_zibura.loot.util.LootEvaluationContext;

import java.util.*;

public class CarpenterLootGenerator {

    /**
     * 核心执行方法：从 Context 读取模板与群系定义，动态生成 carpenter_<biome>
     */
    public static void initCarpenterTemplates() {
        // 1. 读取 6 个基础模板（键名对齐 village_carpenter.json 的驼峰命名）
        Map<String, JsonArray> templates = new HashMap<>();
        templates.put("utility", getTemplateArraySafe("carpenterUtilityTemplates"));
        templates.put("furniture", getTemplateArraySafe("carpenterFurnitureTemplates"));
        templates.put("materials", getTemplateArraySafe("carpenterMaterialTemplates"));
        templates.put("sapling", getTemplateArraySafe("carpenterSaplingTemplates"));
        templates.put("axes", getTemplateArraySafe("carpenterAxesTemplates"));
        templates.put("sawmills", getTemplateArraySafe("carpenterSawmillTemplates"));

        // 2. 从 Context 动态读取群系列表与木材映射
        List<String> biomes = LootEvaluationContext.resolveStringList("carpenterBiomes");
        Map<String, List<String>> biomeWoods = getBiomeWoodsMapSafe();

        // 3. 批量生成 carpenter_<biome> 并注册进全局 Context
        JsonObject dynamicRegistry = new JsonObject();
        for (String biome : biomes) {
            JsonArray biomeContent = generateCarpenterContent(biome, biomeWoods.getOrDefault(biome, Collections.emptyList()), templates);
            dynamicRegistry.add("carpenter_" + biome, biomeContent);
        }
        LootEvaluationContext.registerAll(dynamicRegistry);
    }

    /**
     * 解析 carpenterBiomeWoods 为 Map<群系, 木材列表>
     */
    private static Map<String, List<String>> getBiomeWoodsMapSafe() {
        Map<String, List<String>> map = new HashMap<>();
        try {
            JsonElement elem = LootEvaluationContext.resolveElement(new JsonPrimitive("carpenterBiomeWoods"));
            if (elem != null && elem.isJsonObject()) {
                JsonObject obj = elem.getAsJsonObject();
                for (Map.Entry<String, JsonElement> entry : obj.entrySet()) {
                    List<String> woods = new ArrayList<>();
                    if (entry.getValue().isJsonArray()) {
                        for (JsonElement w : entry.getValue().getAsJsonArray()) {
                            woods.add(w.getAsString());
                        }
                    }
                    map.put(entry.getKey(), woods);
                }
            }
        } catch (Exception ignored) {}
        return map;
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

    /**
     * 替换模板中的 [WOOD] 占位符，适配竹子与红树木的特殊 ID
     */
    private static String replaceWoodPlaceholder(String templateId, String woodType) {
        String itemId = templateId.replace("[WOOD]", woodType);
        if ("bamboo".equals(woodType)) {
            itemId = itemId.replace("bamboo_log", "bamboo_block")
                    .replace("bamboo_sapling", "bamboo");
        } else if ("mangrove".equals(woodType)) {
            itemId = itemId.replace("mangrove_sapling", "mangrove_propagule");
        }
        return itemId;
    }

    /**
     * 根据群系木材和模板列表组装 Group 配置
     */
    public static JsonArray createBiomeGroup(List<String> woods, String groupName, JsonArray templates, double groupWeight, int min, int max) {
        JsonArray items = new JsonArray();

        // 遍历群系对应的每种木材（如丛林包含 jungle 与 bamboo）
        for (String wood : woods) {
            for (JsonElement elem : templates) {
                if (!elem.isJsonObject()) continue;
                JsonObject itemObj = elem.getAsJsonObject().deepCopy();

                if (itemObj.has("id")) {
                    String rawId = itemObj.get("id").getAsString();
                    if (rawId.contains("[WOOD]")) {
                        itemObj.addProperty("id", replaceWoodPlaceholder(rawId, wood));
                    }
                }
                items.add(itemObj);
            }
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

    /**
     * 构建群系的完整 Content 数组
     */
    public static JsonArray generateCarpenterContent(String biome, List<String> woods, Map<String, JsonArray> templateMap) {
        JsonArray contentArray = new JsonArray();

        contentArray.add(createContentEntry(createBiomeGroup(woods, "utility",   templateMap.get("utility"),   100, 1, 1), 1, 2));
        contentArray.add(createContentEntry(createBiomeGroup(woods, "furniture", templateMap.get("furniture"), 100, 1, 1), 1, 2));
        contentArray.add(createContentEntry(createBiomeGroup(woods, "materials", templateMap.get("materials"), 100, 2, 3), 2, 3));
        contentArray.add(createContentEntry(createBiomeGroup(woods, "sapling",   templateMap.get("sapling"),   100, 1, 1), 1, 2));
        contentArray.add(createContentEntry(createBiomeGroup(woods, "axes",      templateMap.get("axes"),      100, 1, 1), 1, 2));
        contentArray.add(createContentEntry(createBiomeGroup(woods, "sawmills",  templateMap.get("sawmills"),  100, 1, 1), 1, 1));

        return contentArray;
    }

    private static JsonArray createContentEntry(JsonArray group, int minRolls, int maxRolls) {
        JsonArray entry = new JsonArray();
        entry.add(group);
        entry.add(minRolls);
        entry.add(maxRolls);
        return entry;
    }
}