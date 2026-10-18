package com.zibura.better_loot_zibura.loot.util;

import com.google.gson.*;
import java.util.*;

/** Pure Gson equivalent of better_loot_templates.py. */
public final class LootTemplateGenerator {
    private LootTemplateGenerator() {}
    private record Spec(String name, String key, int min, int max, int rollsMin, int rollsMax) {}
    private static final List<Spec> SPECS = List.of(
        new Spec("utility", "carpenterUtilityTemplates", 1, 1, 1, 2),
        new Spec("furniture", "carpenterFurnitureTemplates", 1, 1, 1, 2),
        new Spec("materials", "carpenterMaterialTemplates", 2, 3, 2, 3),
        new Spec("sapling", "carpenterSaplingTemplates", 1, 1, 1, 2),
        new Spec("axes", "carpenterAxesTemplates", 1, 1, 1, 2),
        new Spec("sawmills", "carpenterSawmillTemplates", 1, 1, 1, 1)
    );
    private static JsonArray array(JsonObject registry, String key) {
        JsonElement value = registry.get(key);
        if (value == null || !value.isJsonArray()) throw new IllegalArgumentException(key + ": expected array");
        return value.getAsJsonArray();
    }
    private static JsonObject object(JsonObject registry, String key) {
        JsonElement value = registry.get(key);
        if (value == null || !value.isJsonObject()) throw new IllegalArgumentException(key + ": expected object");
        return value.getAsJsonObject();
    }
    private static String string(JsonElement element, String context) {
        if (element == null || !element.isJsonPrimitive() || !element.getAsJsonPrimitive().isString())
            throw new IllegalArgumentException(context + ": expected string");
        return element.getAsString();
    }
    private static String woodId(String id, String wood) {
        String result = id.replace("[WOOD]", wood);
        if (wood.equals("bamboo")) return result.replace("bamboo_log", "bamboo_block").replace("bamboo_sapling", "bamboo");
        if (wood.equals("mangrove")) return result.replace("mangrove_sapling", "mangrove_propagule");
        return result;
    }
    private static JsonArray group(JsonArray templates, JsonArray substitutions, String placeholder,
                                   String name, int weight, int min, int max, boolean plainsWool) {
        JsonArray items = new JsonArray();
        for (JsonElement sub : substitutions) {
            String replacement = string(sub, "substitution");
            for (JsonElement template : templates) {
                if (!template.isJsonObject()) continue;
                JsonObject item = template.getAsJsonObject().deepCopy();
                JsonElement id = item.get("id");
                if (id != null && id.isJsonPrimitive() && id.getAsJsonPrimitive().isString()) {
                    String original = id.getAsString();
                    item.addProperty("id", placeholder.equals("[WOOD]") ? woodId(original, replacement) : original.replace(placeholder, replacement));
                }
                items.add(item);
            }
        }
        if (plainsWool) {
            JsonObject wool = new JsonObject(); wool.addProperty("id", "brewery:patterned_wool"); wool.addProperty("ratio", 10); items.add(wool);
            JsonObject carpet = new JsonObject(); carpet.addProperty("id", "brewery:patterned_carpet"); carpet.addProperty("ratio", 10); items.add(carpet);
        }
        JsonObject g = new JsonObject();
        g.addProperty("groupName", name); g.addProperty("groupWeight", weight);
        g.addProperty("min", min); g.addProperty("max", max); g.add("items", items);
        JsonArray result = new JsonArray(); result.add(g); return result;
    }
    private static JsonArray row(JsonArray group, int min, int max, JsonElement condition) {
        JsonArray row = new JsonArray(); row.add(group); row.add(min); row.add(max);
        if (condition != null) row.add(condition.deepCopy());
        return row;
    }
    public static JsonObject generateAll(JsonObject registry) {
        JsonObject result = new JsonObject();
        JsonArray biomes = array(registry, "carpenterBiomes");
        JsonObject woodsByBiome = object(registry, "carpenterBiomeWoods");
        Map<String, JsonArray> templates = new LinkedHashMap<>();
        for (Spec spec : SPECS) templates.put(spec.name(), array(registry, spec.key()));
        for (JsonElement biomeValue : biomes) {
            String biome = string(biomeValue, "carpenterBiomes");
            JsonElement woodsValue = woodsByBiome.get(biome);
            if (woodsValue != null && !woodsValue.isJsonArray()) throw new IllegalArgumentException("carpenterBiomeWoods." + biome + " must be an array");
            JsonArray woods = woodsValue == null ? new JsonArray() : woodsValue.getAsJsonArray();
            JsonArray content = new JsonArray();
            for (Spec spec : SPECS)
                content.add(row(group(templates.get(spec.name()), woods, "[WOOD]", spec.name(), 100, spec.min(), spec.max(), false), spec.rollsMin(), spec.rollsMax(), null));
            result.add("carpenter_" + biome, content);
        }
        JsonArray woolTemplates = array(registry, "shepherdWoolTemplates");
        JsonArray dyeTemplates = array(registry, "shepherdDyeTemplates");
        JsonObject colorsByBiome = object(registry, "shepherdBiomeColors");
        JsonArray biomeList = array(registry, "biomeList");
        JsonArray content = new JsonArray();
        for (JsonElement entry : biomeList) {
            if (!entry.isJsonArray() || entry.getAsJsonArray().size() < 2) continue;
            JsonArray pair = entry.getAsJsonArray();
            String biome = string(pair.get(0), "shepherd biome");
            if (!pair.get(1).isJsonObject()) throw new IllegalArgumentException("Invalid shepherd condition: " + pair);
            JsonElement colorsValue = colorsByBiome.get(biome);
            if (colorsValue != null && !colorsValue.isJsonArray()) throw new IllegalArgumentException("shepherdBiomeColors." + biome + " must be an array");
            JsonArray colors = new JsonArray();
            if (colorsValue == null) colors.add("white"); else colors = colorsValue.getAsJsonArray();
            content.add(row(group(woolTemplates, colors, "[COLOR]", "wool", 100, 1, 2, biome.equals("plains")), 1, 1, pair.get(1)));
            content.add(row(group(dyeTemplates, colors, "[COLOR]", "dye", 100, 1, 2, false), 2, 2, pair.get(1)));
        }
        JsonArray equipment = new JsonArray(); equipment.add("shepherdEquipmentGroup"); equipment.add(0); equipment.add(1); content.add(equipment);
        JsonArray materials = new JsonArray(); materials.add("shepherdMaterialGroup"); materials.add(1); materials.add(2); content.add(materials);
        result.add("village_shepherd_content", content);
        for (String key : result.keySet()) if (registry.has(key)) throw new IllegalArgumentException("Generated key collides with registry: " + key);
        return result;
    }
}
