package com.zibura.better_loot_zibura.loot.util;

import com.google.gson.*;
import java.util.*;
import java.util.function.Function;

/** Pure Gson equivalent of better_loot_linker_v2.link(). */
public final class LootReferenceResolver {
    private final JsonObject registry;
    private final Deque<String> stack = new ArrayDeque<>();
    public LootReferenceResolver(JsonObject registry) { this.registry = registry.deepCopy(); }
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
    /** Expand a template's array references without treating objects as finalized items. */
    private JsonArray templateParts(JsonElement value) {
        if (string(value)) return ref(value.getAsString(), this::templateParts);
        JsonArray out = new JsonArray();
        if (value != null && value.isJsonArray()) {
            for (JsonElement child : value.getAsJsonArray())
                for (JsonElement part : templateParts(child)) out.add(part);
        } else if (value != null && value.isJsonObject()) {
            if (value.getAsJsonObject().has("template"))
                throw error("Nested template calls are not supported: " + value);
            out.add(value.deepCopy());
        } else throw error("Expected template item/list/reference: " + value);
        return out;
    }
    /** Replace placeholders in original string values once, without regex or cascading substitutions. */
    private static boolean isPlaceholderName(String name) {
        if (name.isEmpty()) return false;
        char first = name.charAt(0);
        if (!(Character.isLetter(first) || first == '_')) return false;
        for (int i = 1; i < name.length(); i++) {
            char c = name.charAt(i);
            if (!(Character.isLetterOrDigit(c) || c == '_')) return false;
        }
        return true;
    }
    private String substitute(String input, Map<String, String> params) {
        StringBuilder result = new StringBuilder();
        int offset = 0;
        while (offset < input.length()) {
            int begin = input.indexOf('{', offset);
            if (begin < 0) { result.append(input, offset, input.length()); break; }
            int end = input.indexOf('}', begin + 1);
            if (end < 0) { result.append(input, offset, input.length()); break; }
            result.append(input, offset, begin);
            String name = input.substring(begin + 1, end);
            if (params.containsKey(name)) result.append(params.get(name));
            else if (isPlaceholderName(name))
                throw error("Missing template parameter: " + name);
            else result.append(input, begin, end + 1);
            offset = end + 1;
        }
        return result.toString();
    }
    private JsonElement substituteValues(JsonElement value, Map<String, String> params) {
        if (string(value)) return new JsonPrimitive(substitute(value.getAsString(), params));
        if (value.isJsonArray()) {
            JsonArray out = new JsonArray();
            for (JsonElement child : value.getAsJsonArray()) out.add(substituteValues(child, params));
            return out;
        }
        if (value.isJsonObject()) {
            JsonObject out = new JsonObject();
            for (Map.Entry<String, JsonElement> entry : value.getAsJsonObject().entrySet())
                out.add(entry.getKey(), substituteValues(entry.getValue(), params));
            return out;
        }
        return value.deepCopy();
    }
    private JsonArray templateCall(JsonObject call) {
        if (!string(call.get("template"))) throw error("Template name must be a string: " + call);
        String name = call.get("template").getAsString();
        JsonElement rawParams = call.get("param");
        if (rawParams != null && !rawParams.isJsonObject()) throw error("Template param must be an object: " + call);
        Map<String, String> fixed = new LinkedHashMap<>();
        String arrayKey = null;
        List<String> arrayValues = null;
        if (rawParams != null) for (Map.Entry<String, JsonElement> e : rawParams.getAsJsonObject().entrySet()) {
            JsonElement value = e.getValue();
            if (string(value)) {
                fixed.put(e.getKey(), value.getAsString());
            } else if (value != null && value.isJsonArray()) {
                if (arrayKey != null) throw error("Only one array template parameter is supported: " + call);
                arrayKey = e.getKey();
                arrayValues = new ArrayList<>();
                for (JsonElement element : value.getAsJsonArray()) {
                    if (!string(element)) throw error("Template array parameter must contain only strings: " + e.getKey());
                    arrayValues.add(element.getAsString());
                }
            } else throw error("Template parameter must be a string or string array: " + e.getKey());
        }
        final String varyingKey = arrayKey;
        final List<String> varyingValues = arrayValues;
        return ref(name, template -> {
            JsonArray parts = templateParts(template);
            JsonArray out = new JsonArray();
            List<String> values = varyingValues == null ? Collections.singletonList(null) : varyingValues;
            for (String value : values) {
                Map<String, String> params = new LinkedHashMap<>(fixed);
                if (varyingKey != null) params.put(varyingKey, value);
                for (JsonElement part : parts) out.add(metadata(substituteValues(part, params)));
            }
            return out;
        });
    }

    private JsonArray items(JsonElement value) {
        if (string(value)) return ref(value.getAsString(), this::items);
        JsonArray out = new JsonArray();
        if (value != null && value.isJsonArray()) {
            for (JsonElement child : value.getAsJsonArray()) for (JsonElement e : items(child)) out.add(e);
        } else if (value != null && value.isJsonObject()) {
            JsonObject object = value.getAsJsonObject();
            if (object.has("template")) {
                for (JsonElement item : templateCall(object)) out.add(item);
            } else out.add(metadata(value));
        }
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
