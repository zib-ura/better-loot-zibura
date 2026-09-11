////package com.zibura.better_loot_zibura.loot.SpecificLoot;
////
////import com.google.gson.JsonArray;
////import com.google.gson.JsonElement;
////import com.google.gson.JsonObject;
////import com.google.gson.JsonPrimitive;
////import com.zibura.better_loot_zibura.loot.util.LootEvaluationContext;
////
////import java.util.*;
////
////public class ShepherdLootGenerator {
////
////    // 1. 群系颜色映射表[cite: 10]
////    private static final Map<String, List<String>> BIOME_COLORS = Map.of(
////            "desert",       List.of("white", "green", "cyan", "blue", "lime"),
////            "snowy",        List.of("white", "red", "blue"),
////            "taiga",        List.of("white", "red", "blue", "purple"),
////            "swamp",        List.of("light_blue", "white"),
////            "cherry_grove", List.of("pink", "white"),
////            "jungle",       List.of("brown", "white"),
////            "ryb",          List.of("red", "yellow", "blue", "white"),
////            "plains",       List.of("red", "yellow", "blue", "white"),
////            "ryo",          List.of("red", "yellow", "orange", "white"),
////            "other",        List.of("white")
////    );
////
////    // 2. 群系匹配条件映射表
////    private static final List<BiomeConditionEntry> BIOME_ENTRIES = List.of(
////            // 平原群系 (原 #minecraft:has_structure/village_plains)
////            new BiomeConditionEntry("plains", List.of(
////                    "minecraft:plains",
////                    "minecraft:meadow"
////            )),
////
////            // 沙漠群系 (原 #minecraft:has_structure/village_desert)
////            new BiomeConditionEntry("desert", List.of(
////                    "minecraft:desert"
////            )),
////
////            // 雪原群系 (原 #minecraft:has_structure/village_snowy)
////            new BiomeConditionEntry("snowy", List.of(
////                    "minecraft:snowy_plains"
////            )),
////
////            // 针叶林群系 (原 #minecraft:is_taiga)
////            new BiomeConditionEntry("taiga", List.of(
////                    "minecraft:taiga",
////                    "minecraft:snowy_taiga",
////                    "minecraft:old_growth_pine_taiga",
////                    "minecraft:old_growth_spruce_taiga"
////            )),
////
////            // 沼泽
////            new BiomeConditionEntry("swamp", List.of(
////                    "minecraft:swamp",
////                    "minecraft:mangrove_swamp"
////            )),
////
////            // 樱花树林
////            new BiomeConditionEntry("cherry_grove", List.of(
////                    "minecraft:cherry_grove"
////            )),
////
////            // 丛林群系 (原 #minecraft:is_jungle)
////            new BiomeConditionEntry("jungle", List.of(
////                    "minecraft:jungle",
////                    "minecraft:sparse_jungle",
////                    "minecraft:bamboo_jungle"
////            ))
////
////            // 如果你的 KubeJS 自定义标签包含其他群系，按同样方式在此展开群系 ID：
////            // new BiomeConditionEntry("ryb", List.of("minecraft:xxx", "minecraft:yyy")),
////            // new BiomeConditionEntry("ryo", List.of("minecraft:xxx", "minecraft:yyy")),
////            // new BiomeConditionEntry("other", List.of("minecraft:xxx"))
////    );
////
////    // 修改 record，使用 List<String> 接收多个群系
////    private record BiomeConditionEntry(String biome, List<String> matchBiomes) {
////        // 便捷构造函数（兼容只填单个群系的情况）
////        public BiomeConditionEntry(String biome, String singleBiome) {
////            this(biome, List.of(singleBiome));
////        }
////    }
////
////    /**
////     * 核心注册入口：动态生成 village_shepherd_content 并注入 Context[cite: 10]
////     */
////    public static void initShepherdTemplates() {
////        JsonArray woolTemplates = getTemplateArraySafe("shepherd_wool_templates");
////        JsonArray dyeTemplates = getTemplateArraySafe("shepherd_dye_templates");
////
////        JsonArray shepherdContent = new JsonArray();
////
////        // 1. 遍历群系并生成羊毛组与染料组[cite: 10]
////        for (BiomeConditionEntry entry : BIOME_ENTRIES) {
////            JsonObject conditionObj = new JsonObject();
//////            conditionObj.addProperty("matchBiome", entry.matchBiome());
////// 将 List<String> 转换为 JsonArray
////            JsonArray biomeArray = new JsonArray();
////            for (String b : entry.matchBiomes()) {
////                biomeArray.add(b);
////            }
////            conditionObj.add("matchBiome", biomeArray);
////            // 羊毛组
////            JsonArray woolGroup = createBiomeGroup(entry.biome(), "wool", woolTemplates, 100, 1, 2, true);
////            shepherdContent.add(createContentEntry(woolGroup, 1, 1, conditionObj));
////
////            // 染料组
////            JsonArray dyeGroup = createBiomeGroup(entry.biome(), "dye", dyeTemplates, 100, 1, 2, false);
////            shepherdContent.add(createContentEntry(dyeGroup, 2, 2, conditionObj));
////        }
////
////        // 2. 追加共享装备组与材料组[cite: 10]
////        shepherdContent.add(createContentEntry(new JsonPrimitive("shepherd_equipment_group"), 0, 1, null));
////        shepherdContent.add(createContentEntry(new JsonPrimitive("shepherd_material_group"), 1, 2, null));
////
////        // 3. 注册进全局 Context[cite: 7, 10]
////        JsonObject registry = new JsonObject();
////        registry.add("village_shepherd_content", shepherdContent);
////        LootEvaluationContext.registerAll(registry);
////    }
////
////    /**
////     * 动态替换 [COLOR] 并处理 plains 平原专属物品[cite: 10]
////     */
////    private static JsonArray createBiomeGroup(String biome, String groupName, JsonArray templates, double groupWeight, int min, int max, boolean isWool) {
////        List<String> colors = BIOME_COLORS.getOrDefault(biome, List.of("white"));
////        JsonArray items = new JsonArray();
////
////        for (String color : colors) {
////            for (JsonElement elem : templates) {
////                if (!elem.isJsonObject()) continue;
////                JsonObject itemObj = elem.getAsJsonObject().deepCopy();
////
////                if (itemObj.has("id")) {
////                    String rawId = itemObj.get("id").getAsString();
////                    if (rawId.contains("[COLOR]")) {
////                        itemObj.addProperty("id", rawId.replace("[COLOR]", color));
////                    }
////                }
////                items.add(itemObj);
////            }
////        }
////
////        // 平原专属额外物品注入[cite: 10]
////        if ("plains".equals(biome) && isWool) {
////            JsonObject extra1 = new JsonObject();
////            extra1.addProperty("id", "brewery:patterned_wool");
////            extra1.addProperty("ratio", 10);
////            items.add(extra1);
////
////            JsonObject extra2 = new JsonObject();
////            extra2.addProperty("id", "brewery:patterned_carpet");
////            extra2.addProperty("ratio", 10);
////            items.add(extra2);
////        }
////
////        JsonObject groupObj = new JsonObject();
////        groupObj.addProperty("groupName", groupName);
////        groupObj.addProperty("groupWeight", groupWeight);
////        groupObj.addProperty("min", min);
////        groupObj.addProperty("max", max);
////        groupObj.add("items", items);
////
////        JsonArray groups = new JsonArray();
////        groups.add(groupObj);
////        return groups;
////    }
////
////    private static JsonArray createContentEntry(JsonElement group, int minRolls, int maxRolls, JsonObject condition) {
////        JsonArray entry = new JsonArray();
////        entry.add(group);
////        entry.add(minRolls);
////        entry.add(maxRolls);
////        if (condition != null) {
////            entry.add(condition);
////        }
////        return entry;
////    }
////
////    private static JsonArray getTemplateArraySafe(String key) {
////        try {
////            JsonElement elem = LootEvaluationContext.resolveElement(new JsonPrimitive(key));
////            if (elem != null && elem.isJsonArray()) {
////                return elem.getAsJsonArray();
////            }
////        } catch (Exception ignored) {}
////        return new JsonArray();
////    }
////}
//
//
//package com.zibura.better_loot_zibura.loot.SpecificLoot;
//
//import com.google.gson.JsonArray;
//import com.google.gson.JsonElement;
//import com.google.gson.JsonObject;
//import com.google.gson.JsonPrimitive;
//import com.zibura.better_loot_zibura.loot.util.LootEvaluationContext;
//
//import java.util.*;
//
//public class ShepherdLootGenerator {
//
//    // 1. 群系颜色映射表
//    private static final Map<String, List<String>> BIOME_COLORS = Map.of(
//            "desert",       List.of("white", "green", "cyan", "blue", "lime"),
//            "snowy",        List.of("white", "red", "blue"),
//            "taiga",        List.of("white", "red", "blue", "purple"),
//            "swamp",        List.of("light_blue", "white"),
//            "cherry_grove", List.of("pink", "white"),
//            "jungle",       List.of("brown", "white"),
//            "ryb",          List.of("red", "yellow", "blue", "white"),
//            "plains",       List.of("red", "yellow", "blue", "white"),
//            "ryo",          List.of("red", "yellow", "orange", "white"),
//            "other",        List.of("white")
//    );
//
//    // 2. 映射表：按照给定的 Tag 重新映射，前缀 kubejs 统一换为 better_loot_zibura
//    private static final List<BiomeConditionEntry> BIOME_ENTRIES = List.of(
//            new BiomeConditionEntry("plains",       "#minecraft:has_structure/village_plains"),
//            new BiomeConditionEntry("desert",       "#minecraft:has_structure/village_desert"),
//            new BiomeConditionEntry("snowy",        "#minecraft:has_structure/village_snowy"),
//            new BiomeConditionEntry("taiga",        "#minecraft:is_taiga"),
//            new BiomeConditionEntry("swamp",        "minecraft:swamp"),
//            new BiomeConditionEntry("cherry_grove", "minecraft:cherry_grove"),
//            new BiomeConditionEntry("jungle",       "#minecraft:is_jungle"),
//            new BiomeConditionEntry("ryb",          "#better_loot_zibura:shepherd/red_yellow_blue"),
//            new BiomeConditionEntry("ryo",          "#better_loot_zibura:shepherd/red_yellow_orange"),
//            new BiomeConditionEntry("other",        "#better_loot_zibura:shepherd/other")
//    );
//
//    private record BiomeConditionEntry(String biomeKey, String matchBiome) {}
//
//    /**
//     * 核心注册入口：动态生成 village_shepherd_content 并注入 Context
//     */
//    public static void initShepherdTemplates() {
//        JsonArray woolTemplates = getTemplateArraySafe("shepherd_wool_templates");
//        JsonArray dyeTemplates = getTemplateArraySafe("shepherd_dye_templates");
//
//        JsonArray shepherdContent = new JsonArray();
//
//        // 1. 遍历群系并生成羊毛组与染料组
//        for (BiomeConditionEntry entry : BIOME_ENTRIES) {
//            JsonObject conditionObj = new JsonObject();
//
//            // 封装自定义条件：交由 MatchBiomeRegistryCondition 处理（自动识别 #tag 和 单群系）
//            JsonObject customCond = new JsonObject();
//            customCond.addProperty("condition", "better_loot_zibura:match_biome_registry");
//            customCond.addProperty("registry_key", entry.matchBiome());
//
//            conditionObj.add("customCondition", customCond);
//
//            // 羊毛组
//            JsonArray woolGroup = createBiomeGroup(entry.biomeKey(), "wool", woolTemplates, 100, 1, 2, true);
//            shepherdContent.add(createContentEntry(woolGroup, 1, 1, conditionObj));
//
//            // 染料组
//            JsonArray dyeGroup = createBiomeGroup(entry.biomeKey(), "dye", dyeTemplates, 100, 1, 2, false);
//            shepherdContent.add(createContentEntry(dyeGroup, 2, 2, conditionObj));
//        }
//
//        // 2. 追加共享装备组与材料组
//        shepherdContent.add(createContentEntry(new JsonPrimitive("shepherd_equipment_group"), 0, 1, null));
//        shepherdContent.add(createContentEntry(new JsonPrimitive("shepherd_material_group"), 1, 2, null));
//
//        // 3. 注册进全局 Context
//        JsonObject registry = new JsonObject();
//        registry.add("village_shepherd_content", shepherdContent);
//        LootEvaluationContext.registerAll(registry);
//    }
//
//    /**
//     * 动态替换 [COLOR] 并处理 plains 平原专属物品
//     */
//    private static JsonArray createBiomeGroup(String biome, String groupName, JsonArray templates, double groupWeight, int min, int max, boolean isWool) {
//        List<String> colors = BIOME_COLORS.getOrDefault(biome, List.of("white"));
//        JsonArray items = new JsonArray();
//
//        for (String color : colors) {
//            for (JsonElement elem : templates) {
//                if (!elem.isJsonObject()) continue;
//                JsonObject itemObj = elem.getAsJsonObject().deepCopy();
//
//                if (itemObj.has("id")) {
//                    String rawId = itemObj.get("id").getAsString();
//                    if (rawId.contains("[COLOR]")) {
//                        itemObj.addProperty("id", rawId.replace("[COLOR]", color));
//                    }
//                }
//                items.add(itemObj);
//            }
//        }
//
//        // 平原专属额外物品注入
//        if ("plains".equals(biome) && isWool) {
//            JsonObject extra1 = new JsonObject();
//            extra1.addProperty("id", "brewery:patterned_wool");
//            extra1.addProperty("ratio", 10);
//            items.add(extra1);
//
//            JsonObject extra2 = new JsonObject();
//            extra2.addProperty("id", "brewery:patterned_carpet");
//            extra2.addProperty("ratio", 10);
//            items.add(extra2);
//        }
//
//        JsonObject groupObj = new JsonObject();
//        groupObj.addProperty("groupName", groupName);
//        groupObj.addProperty("groupWeight", groupWeight);
//        groupObj.addProperty("min", min);
//        groupObj.addProperty("max", max);
//        groupObj.add("items", items);
//
//        JsonArray groups = new JsonArray();
//        groups.add(groupObj);
//        return groups;
//    }
//
//    private static JsonArray createContentEntry(JsonElement group, int minRolls, int maxRolls, JsonObject condition) {
//        JsonArray entry = new JsonArray();
//        entry.add(group);
//        entry.add(minRolls);
//        entry.add(maxRolls);
//        if (condition != null) {
//            entry.add(condition);
//        }
//        return entry;
//    }
//
//    private static JsonArray getTemplateArraySafe(String key) {
//        try {
//            JsonElement elem = LootEvaluationContext.resolveElement(new JsonPrimitive(key));
//            if (elem != null && elem.isJsonArray()) {
//                return elem.getAsJsonArray();
//            }
//        } catch (Exception ignored) {}
//        return new JsonArray();
//    }
//}

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