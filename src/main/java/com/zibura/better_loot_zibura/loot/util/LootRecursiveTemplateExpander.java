package com.zibura.better_loot_zibura.loot.util;

import com.google.gson.*;
import java.util.*;

/**
 * Compiles opt-in recursive template/output calls into ordinary registry arrays.
 *
 * <p>Rules:
 * <ul>
 *   <li>param contains at most one variable, and its value is a string array or a
 *       registry name pointing to a string array.</li>
 *   <li>Nested calls without param inherit the current variable binding.</li>
 *   <li>output is legal at any nesting depth. It registers the expanded array and
 *       replaces the call with its registry key (or keys).</li>
 *   <li>Without output, a call inserts its expanded elements at the current list level.</li>
 * </ul>
 * Only roots containing an output call are eagerly compiled; existing legacy
 * item templates and matrix definitions remain available to the normal linker.
 */
public final class LootRecursiveTemplateExpander {
    private final JsonObject source;
    private final JsonObject result;
    private final Deque<String> chain = new ArrayDeque<>();
    private final Set<String> produced = new HashSet<>();

    private LootRecursiveTemplateExpander(JsonObject source) {
        this.source = source;
        this.result = source.deepCopy();
    }

    public static JsonObject expand(JsonObject source) {
        LootRecursiveTemplateExpander compiler = new LootRecursiveTemplateExpander(source);
        for (Map.Entry<String, JsonElement> entry : source.entrySet()) {
            // Only explicitly requested output roots trigger generation. Template
            // definitions containing inherited placeholders must not run by themselves.
            if (entry.getValue().isJsonArray() && compiler.hasRootOutput(entry.getValue())) {
                compiler.result.add(entry.getKey(), compiler.expandNode(entry.getValue(), Map.of(), false));
            }
        }
        return compiler.result;
    }

    private boolean hasRootOutput(JsonElement value) {
        if (!value.isJsonArray()) return false;
        for (JsonElement e : value.getAsJsonArray())
            if (e.isJsonObject() && e.getAsJsonObject().has("template") && e.getAsJsonObject().has("output")) return true;
        return false;
    }

    private IllegalArgumentException fail(String message) {
        return new IllegalArgumentException(message + " (template chain: " + chain + ")");
    }

    private static boolean str(JsonElement e) {
        return e != null && e.isJsonPrimitive() && e.getAsJsonPrimitive().isString();
    }

    /** Substitute string VALUES, including dynamic registry references, not object keys. */
    private String replace(String text, Map<String, String> context) {
        StringBuilder out = new StringBuilder();
        int pos = 0;
        while (pos < text.length()) {
            int start = text.indexOf('{', pos);
            if (start < 0) { out.append(text, pos, text.length()); break; }
            int end = text.indexOf('}', start + 1);
            if (end < 0) { out.append(text, pos, text.length()); break; }
            out.append(text, pos, start);
            String key = text.substring(start + 1, end);
            if (!context.containsKey(key)) throw fail("Missing template parameter: " + key);
            out.append(context.get(key));
            pos = end + 1;
        }
        return out.toString();
    }

    /** A parameter string is ALWAYS a registry array reference, never a scalar. */
    private List<String> parameterValues(JsonElement raw) {
        JsonElement value = raw;
        if (str(raw)) {
            String key = raw.getAsString();
            if (!source.has(key)) throw fail("Undefined parameter array: " + key);
            value = source.get(key);
        }
        if (!value.isJsonArray()) throw fail("param must be a string array or array registry reference: " + raw);
        List<String> values = new ArrayList<>();
        for (JsonElement e : value.getAsJsonArray()) {
            if (!str(e)) throw fail("Parameter array entries must be strings: " + e);
            values.add(e.getAsString());
        }
        return values;
    }

    private JsonArray expandCall(JsonObject call, Map<String, String> parent) {
        if (!str(call.get("template"))) throw fail("template must be a registry name string");
        String name = replace(call.get("template").getAsString(), parent);
        if (!source.has(name)) throw fail("Undefined template: " + name);
        if (chain.contains(name)) throw fail("Cyclic template: " + name);
        JsonElement template = source.get(name);
        if (!template.isJsonArray()) throw fail("Template must resolve to an array: " + name);
        if (call.has("output") && !str(call.get("output"))) throw fail("output must be a string");
        List<Map<String, String>> contexts = new ArrayList<>();
        if (!call.has("param")) contexts.add(parent);
        else {
            JsonElement raw = call.get("param");
            if (!raw.isJsonObject() || raw.getAsJsonObject().size() != 1)
                throw fail("param must contain exactly one array parameter");
            Map.Entry<String, JsonElement> entry =
                    raw.getAsJsonObject().entrySet().iterator().next();

            JsonElement parameter = entry.getValue();
            // 如果参数值是字符串，先替换其中的父级变量
            if (str(parameter)) {
                parameter = new JsonPrimitive(
                        replace(parameter.getAsString(), parent)
                );
            }
            // 解析参数数组，并为每个元素创建独立上下文
            for (String value : parameterValues(parameter)) {
                Map<String, String> next = new LinkedHashMap<>(parent);
                next.put(entry.getKey(), value);
                contexts.add(next);
            }
        }
        JsonArray resultValues = new JsonArray();
        chain.addLast(name);
        try {
            for (Map<String, String> context : contexts) {
                JsonElement expanded = expandNode(template, context, false);
                if (!expanded.isJsonArray()) throw fail("Expanded template is not an array: " + name);
                if (call.has("output")) {
                    String outputName = replace(call.get("output").getAsString(), context);
                    if (source.has(outputName) || !produced.add(outputName))
                        throw fail("Duplicate output registry key: " + outputName);
                    this.result.add(outputName, expanded);
                    resultValues.add(outputName);
                } else {
                    for (JsonElement child : expanded.getAsJsonArray()) resultValues.add(child);
                }
            }
        } finally { chain.removeLast(); }
        return resultValues;
    }

    /**
     * Preserve positional tuples [groups,min,max] and [content,weight].
     * In a tuple's first slot, a call is a single value (array or output ref),
     * rather than multiple sibling rows.
     */
    private boolean tuple(JsonArray arr) {
        return arr.size() >= 2 && arr.get(1).isJsonPrimitive()
                && arr.get(1).getAsJsonPrimitive().isNumber();
    }

    private JsonElement expandNode(JsonElement node, Map<String, String> context, boolean singleSlot) {
        if (str(node)) return new JsonPrimitive(replace(node.getAsString(), context));
        if (node.isJsonArray()) {
            JsonArray original = node.getAsJsonArray();
            JsonArray expanded = new JsonArray();
            boolean positional = tuple(original);
            for (int i = 0; i < original.size(); i++) {
                JsonElement child = original.get(i);
                if (child.isJsonObject() && child.getAsJsonObject().has("template")) {
                    JsonArray generated = expandCall(child.getAsJsonObject(), context);
                    if (positional && i == 0) {
                        if (child.getAsJsonObject().has("output")) {
                            if (generated.size() != 1) throw fail("Tuple slot requires one output reference");
                            expanded.add(generated.get(0));
                        } else expanded.add(generated);
                    } else for (JsonElement value : generated) expanded.add(value);
                } else expanded.add(expandNode(child, context, positional && i == 0));
            }
            return expanded;
        }
        if (node.isJsonObject()) {
            JsonObject object = node.getAsJsonObject();
            if (object.has("template")) {
                JsonArray values = expandCall(object, context);
                if (singleSlot && values.size() == 1) return values.get(0);
                return values;
            }
            JsonObject expanded = new JsonObject();
            for (Map.Entry<String, JsonElement> entry : object.entrySet())
                expanded.add(entry.getKey(), expandNode(entry.getValue(), context, false));
            return expanded;
        }
        return node.deepCopy();
    }
}
