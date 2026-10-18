package com.zibura.better_loot_zibura.loot.util;

import com.google.gson.*;
import java.util.*;
import java.util.function.Function;

/** Pure Gson equivalent of better_loot_linker_v2.link(). */
public final class LootReferenceResolver {
    private final JsonObject registry;
    private final Deque<String> stack = new ArrayDeque<>();
    public LootReferenceResolver(JsonObject registry) { this.registry = registry.deepCopy(); }
    public static JsonObject withGeneratedTemplates(JsonObject source) {
        JsonObject merged = source.deepCopy();
        JsonObject generated = LootTemplateGenerator.generateAll(merged);
        for (Map.Entry<String, JsonElement> e : generated.entrySet()) merged.add(e.getKey(), e.getValue());
        return merged;
    }
    private static boolean string(JsonElement e) {
        return e != null && e.isJsonPrimitive() && e.getAsJsonPrimitive().isString();
    }
    private IllegalArgumentException error(String message) { return new IllegalArgumentException(message + "; path=" + stack); }
    private <T> T ref(String key, Function<JsonElement, T> expand) {
        if (!registry.has(key)) throw error("Undefined configuration reference: " + key);
        if (stack.contains(key)) throw error("Cyclic reference: " + stack + " -> " + key);
        stack.addLast(key);
        try { return expand.apply(registry.get(key)); }
        finally { stack.removeLast(); }
    }
    private JsonArray flatten(JsonElement value, Function<JsonElement, JsonElement> parse) {
        if (string(value)) return ref(value.getAsString(), v -> flatten(v, parse));
        if (value == null || !value.isJsonArray()) throw error("Expected array: " + value);
        JsonArray out = new JsonArray();
        for (JsonElement item : value.getAsJsonArray()) {
            if (string(item)) for (JsonElement e : ref(item.getAsString(), v -> flatten(v, parse))) out.add(e);
            else out.add(parse.apply(item));
        }
        return out;
    }
    private JsonArray items(JsonElement value) {
        if (string(value)) return ref(value.getAsString(), this::items);
        JsonArray out = new JsonArray();
        if (value != null && value.isJsonArray()) {
            for (JsonElement child : value.getAsJsonArray()) for (JsonElement e : items(child)) out.add(e);
        } else if (value != null && value.isJsonObject()) out.add(metadata(value));
        else throw error("Expected item object/list/reference: " + value);
        return out;
    }
    private JsonArray groups(JsonElement value) {
        return flatten(value, group -> {
            if (!group.isJsonObject()) throw error("Expected group object: " + group);
            JsonObject obj = group.getAsJsonObject().deepCopy();
            if (obj.has("items")) obj.add("items", items(obj.get("items")));
            return obj;
        });
    }
    private JsonArray content(JsonElement value) {
        return flatten(value, row -> {
            if (!row.isJsonArray() || row.getAsJsonArray().size() < 3) throw error("Expected [groups,min,max,...]: " + row);
            JsonArray out = row.getAsJsonArray().deepCopy();
            out.set(0, groups(out.get(0)));
            if (out.size() > 3) out.set(3, metadata(out.get(3)));
            return out;
        });
    }
    private JsonArray weighted(JsonElement value) {
        return flatten(value, row -> {
            if (!row.isJsonArray() || row.getAsJsonArray().size() < 2) throw error("Expected [content,weight,...]: " + row);
            JsonArray out = row.getAsJsonArray().deepCopy();
            out.set(0, content(out.get(0)));
            return out;
        });
    }
    private JsonElement metadata(JsonElement value) {
        if (value == null || value.isJsonNull()) return JsonNull.INSTANCE;
        if (value.isJsonArray()) {
            JsonArray out = new JsonArray();
            for (JsonElement e : value.getAsJsonArray()) out.add(metadata(e));
            return out;
        }
        if (!value.isJsonObject()) return value.deepCopy();
        JsonObject obj = new JsonObject();
        for (Map.Entry<String, JsonElement> e : value.getAsJsonObject().entrySet()) obj.add(e.getKey(), metadata(e.getValue()));
        if (obj.has("function") && string(obj.get("function")) && obj.get("function").getAsString().equals("better_loot_zibura:fill_seed_bundle")) {
            JsonElement key = obj.get("seed_type_key");
            if (!string(key) || !registry.has(key.getAsString())) throw error("Undefined seed_type_key: " + key);
            obj.add("seed_types", seeds(key.getAsString(), new ArrayDeque<>()));
            obj.remove("seed_type_key");
        }
        if (obj.has("registry_key")) obj.add("registry_key", biomes(obj.get("registry_key"), new ArrayDeque<>()));
        return obj;
    }
    private JsonArray seeds(String name, Deque<String> chain) {
        if (chain.contains(name)) throw error("Cyclic seed list: " + chain + " -> " + name);
        JsonElement data = registry.get(name);
        if (data == null || !data.isJsonArray()) throw error("Seed list " + name + " must be an array");
        chain.addLast(name);
        try {
            JsonArray result = new JsonArray();
            for (JsonElement entry : data.getAsJsonArray()) {
                if (!string(entry)) throw error("Invalid seed entry in " + name + ": " + entry);
                String key = entry.getAsString();
                if (registry.has(key)) for (JsonElement e : seeds(key, chain)) result.add(e);
                else result.add(key);
            }
            return result;
        } finally { chain.removeLast(); }
    }
    private JsonArray biomes(JsonElement value, Deque<String> chain) {
        JsonArray result = new JsonArray();
        if (value != null && value.isJsonArray()) {
            for (JsonElement e : value.getAsJsonArray()) for (JsonElement x : biomes(e, chain)) result.add(x);
        } else if (string(value)) {
            String key = value.getAsString();
            if (registry.has(key)) {
                if (chain.contains(key)) throw error("Cyclic biome list: " + chain + " -> " + key);
                chain.addLast(key);
                try { for (JsonElement e : biomes(registry.get(key), chain)) result.add(e); }
                finally { chain.removeLast(); }
            } else result.add(key);
        } else throw error("Invalid biome entry: " + value);
        return result;
    }
    public JsonArray link(JsonElement bindings) {
        JsonArray input = new JsonArray();
        if (bindings != null && bindings.isJsonObject()) input.add(bindings);
        else if (bindings != null && bindings.isJsonArray()) input = bindings.getAsJsonArray();
        else throw error("Expected binding object or array");
        JsonArray result = new JsonArray();
        for (JsonElement binding : input) {
            if (!binding.isJsonObject() || !binding.getAsJsonObject().has("target") || !binding.getAsJsonObject().has("config"))
                throw error("Invalid binding: " + binding);
            JsonObject obj = binding.getAsJsonObject().deepCopy();
            obj.add("config", weighted(obj.get("config")));
            result.add(obj);
        }
        return result;
    }
}
