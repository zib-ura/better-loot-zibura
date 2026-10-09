package com.zibura.better_loot_zibura.loot.util;

import com.google.gson.*;
import java.util.*;

/** Compiles the matrix DSL into ordinary loot pool entries before reference linking. */
public final class LootMatrixExpander {
    private LootMatrixExpander() {}

    public static JsonObject expand(JsonObject source) {
        JsonObject result = source.deepCopy();
        Set<String> rowHelpers = new LinkedHashSet<>();
        for (Map.Entry<String, JsonElement> entry : source.entrySet()) {
            String name = entry.getKey();
            if (!name.endsWith("_matrix") || !entry.getValue().isJsonObject()) continue;
            JsonObject cfg = entry.getValue().getAsJsonObject();
            String type = string(cfg, "matrixType", null);
            if (!Set.of("groupToPool", "poolToContent", "contentToTable").contains(type)) continue;
            Map<String, JsonElement> generated = switch (type) {
                case "groupToPool" -> groupToPool(name, cfg, source);
                case "poolToContent" -> poolToContent(name, cfg, source);
                case "contentToTable" -> contentToTable(name, cfg, source);
                default -> throw new IllegalArgumentException("Unsupported matrix type: " + type);
            };
            for (var output : generated.entrySet()) {
                if (result.has(output.getKey()) && !output.getKey().equals(name))
                    throw new IllegalArgumentException(name + ": output key already exists: " + output.getKey());
                result.add(output.getKey(), output.getValue());
            }
            result.remove(name);
            JsonElement rows = cfg.get("rows");
            if (rows != null && rows.isJsonPrimitive() && rows.getAsJsonPrimitive().isString()
                    && source.has(rows.getAsString())) rowHelpers.add(rows.getAsString());
        }
        // Keep helper constants: other matrices or user-defined references may need them.
        return result;
    }

    private static JsonElement resolved(JsonObject cfg, String field, JsonObject source) {
        JsonElement v = cfg.get(field);
        if (v != null && v.isJsonPrimitive() && v.getAsJsonPrimitive().isString()
                && source.has(v.getAsString())) return source.get(v.getAsString());
        return v;
    }
    private static JsonArray array(JsonElement value, String context) {
        if (value == null || !value.isJsonArray()) throw new IllegalArgumentException(context + ": expected array");
        return value.getAsJsonArray();
    }
    private static String string(JsonObject obj, String key, String fallback) {
        JsonElement v = obj.get(key);
        return v == null || v.isJsonNull() ? fallback : v.getAsString();
    }
    private static String format(String pattern, JsonElement row, String prefix) {
        return pattern.replace("{row}", row.isJsonPrimitive() ? row.getAsString() : row.toString())
                .replace("{prefix}", prefix);
    }
    private static void sameSize(String name, JsonArray a, JsonArray b) {
        if (a.size() != b.size()) throw new IllegalArgumentException(name + ": rows=" + a.size() + " but data rows=" + b.size());
    }
    private static void put(Map<String, JsonElement> out, String key, JsonElement value, String name) {
        if (out.putIfAbsent(key, value) != null) throw new IllegalArgumentException(name + ": duplicate output: " + key);
    }
    private static Map<String, JsonElement> groupToPool(String name, JsonObject cfg, JsonObject source) {
        JsonArray rows = array(resolved(cfg, "rows", source), name + ".rows");
        JsonArray columns = array(resolved(cfg, "columns", source), name + ".columns");
        JsonArray weights = array(resolved(cfg, "weights", source), name + ".weights");
        String pattern = string(cfg, "outputPattern", null), prefix = string(cfg, "prefix", "");
        if (pattern == null) throw new IllegalArgumentException(name + ": missing outputPattern");
        sameSize(name, rows, weights);
        Map<String, JsonElement> out = new LinkedHashMap<>();
        for (int i = 0; i < rows.size(); i++) {
            JsonArray rowWeights = array(weights.get(i), name + ".weights[" + i + "]");
            if (rowWeights.size() != columns.size()) throw new IllegalArgumentException(name + ": column/weight mismatch at row " + i);
            JsonArray groups = new JsonArray();
            for (int j = 0; j < columns.size(); j++) {
                if (!columns.get(j).isJsonObject()) throw new IllegalArgumentException(name + ": column must be object");
                JsonObject group = columns.get(j).getAsJsonObject().deepCopy();
                group.add("groupWeight", rowWeights.get(j).deepCopy());
                groups.add(group);
            }
            put(out, format(pattern, rows.get(i), prefix), groups, name);
        }
        return out;
    }
    private static Map<String, JsonElement> poolToContent(String name, JsonObject cfg, JsonObject source) {
        JsonArray rows = array(resolved(cfg, "rows", source), name + ".rows");
        String pattern = string(cfg, "outputPattern", null), prefix = string(cfg, "prefix", "");
        if (pattern == null) throw new IllegalArgumentException(name + ": missing outputPattern");
        boolean compact = cfg.has("columns") || cfg.has("rolls");
        JsonArray columns = compact ? array(resolved(cfg, "columns", source), name + ".columns") : null;
        JsonArray data = array(resolved(cfg, compact ? "rolls" : "contents", source), name + ".data");
        sameSize(name, rows, data);
        Map<String, JsonElement> out = new LinkedHashMap<>();
        for (int i = 0; i < rows.size(); i++) {
            JsonArray entries = array(data.get(i), name + ".data[" + i + "]");
            if (compact && entries.size() != columns.size()) throw new IllegalArgumentException(name + ": roll/column mismatch at row " + i);
            JsonArray expanded = new JsonArray();
            for (int j = 0; j < entries.size(); j++) {
                JsonElement entry = entries.get(j);
                if (compact && entry.isJsonNull()) continue;
                JsonArray values = array(entry, name + ".entry[" + i + "][" + j + "]");
                if (values.size() != (compact ? 2 : 3)) throw new IllegalArgumentException(name + ": invalid content entry at " + i + "/" + j);
                JsonElement pool = compact ? columns.get(j) : values.get(0);
                if (!pool.isJsonPrimitive() || !pool.getAsJsonPrimitive().isString()) throw new IllegalArgumentException(name + ": pool must be string");
                JsonArray item = new JsonArray();
                item.add(format(pool.getAsString(), rows.get(i), prefix));
                item.add(values.get(compact ? 0 : 1).deepCopy());
                item.add(values.get(compact ? 1 : 2).deepCopy());
                expanded.add(item);
            }
            put(out, format(pattern, rows.get(i), prefix), expanded, name);
        }
        return out;
    }
    private static Map<String, JsonElement> contentToTable(String name, JsonObject cfg, JsonObject source) {
        JsonArray rows = array(resolved(cfg, "rows", source), name + ".rows");
        JsonArray weights = array(resolved(cfg, "weights", source), name + ".weights");
        sameSize(name, rows, weights);
        String output = string(cfg, "output", null), prefix = string(cfg, "prefix", "");
        String pattern = string(cfg, "contentPattern", "{prefix}_{row}_content");
        if (output == null) throw new IllegalArgumentException(name + ": missing output");
        JsonArray entries = new JsonArray();
        for (int i = 0; i < rows.size(); i++) {
            JsonArray pair = new JsonArray();
            pair.add(format(pattern, rows.get(i), prefix));
            pair.add(weights.get(i).deepCopy());
            entries.add(pair);
        }
        if (cfg.has("extra")) for (JsonElement extra : array(cfg.get("extra"), name + ".extra")) entries.add(extra.deepCopy());
        return Map.of(output, entries);
    }
}
