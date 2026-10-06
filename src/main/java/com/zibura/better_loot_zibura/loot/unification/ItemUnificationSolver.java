package com.zibura.better_loot_zibura.loot.unification;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class ItemUnificationSolver {

    public static final Map<String, List<String>> CONVERTIBLE_MAP = new ConcurrentHashMap<>();
    public static final Map<String, List<String>> INCONVERTIBLE_MAP = new ConcurrentHashMap<>();
    public static final Map<String, List<String>> ALL_MAP = new ConcurrentHashMap<>();

    private static final Map<String, String> RESOLVED_CACHE = new ConcurrentHashMap<>();
    private static final Pattern AND_BLOCK_PATTERN = Pattern.compile("^lootjs:([a-zA-Z0-9_]+)_and_\\1_block$");

    public static void clear() {
        CONVERTIBLE_MAP.clear();
        INCONVERTIBLE_MAP.clear();
        ALL_MAP.clear();
        RESOLVED_CACHE.clear();
    }

    public static void parseAndPut(String key, JsonObject json, Map<String, List<String>> targetMap) {
        if (!json.has("values") || !json.get("values").isJsonArray()) return;

        boolean replace = json.has("replace") && json.get("replace").getAsBoolean();
        List<String> list = targetMap.computeIfAbsent(key, k -> new ArrayList<>());
        if (replace) list.clear();

        JsonArray values = json.getAsJsonArray("values");
        for (JsonElement elem : values) {
            if (elem.isJsonPrimitive()) {
                String val = elem.getAsString();
                if (!list.contains(val)) {
                    list.add(val);
                }
            }
        }
    }

    public static void rebuildAllMap() {
        ALL_MAP.clear();
        CONVERTIBLE_MAP.forEach((k, v) -> ALL_MAP.put(k, new ArrayList<>(v)));
        INCONVERTIBLE_MAP.forEach((k, v) -> {
            List<String> list = ALL_MAP.computeIfAbsent(k, key -> new ArrayList<>());
            for (String val : v) {
                if (!list.contains(val)) {
                    list.add(val);
                }
            }
        });
        RESOLVED_CACHE.clear();
    }

    public static String resolveReference(String reference) {
        if (reference == null || reference.isEmpty()) return null;
        if (RESOLVED_CACHE.containsKey(reference)) {
            return RESOLVED_CACHE.get(reference);
        }

        Matcher matcher = AND_BLOCK_PATTERN.matcher(reference);
        if (matcher.matches()) {
            String baseName = matcher.group(1);
            String crateRef = baseName + "_block";
            String singleRef = baseName;

            List<String> candidates = ALL_MAP.get(crateRef);
            if (candidates == null || candidates.isEmpty()) {
                candidates = ALL_MAP.get(singleRef);
            }

            String validId = findFirstValidItem(candidates);
            if (validId != null) {
                RESOLVED_CACHE.put(reference, validId);
                return validId;
            }
            return null;
        }

        String lookupKey = reference.startsWith("lootjs:") ? reference.substring("lootjs:".length()) : reference;
        List<String> candidates = ALL_MAP.get(lookupKey);
        if (candidates != null && !candidates.isEmpty()) {
            String validId = findFirstValidItem(candidates);
            if (validId != null) {
                RESOLVED_CACHE.put(reference, validId);
                return validId;
            }
        }

        return null;
    }

    private static String findFirstValidItem(List<String> candidates) {
        if (candidates == null) return null;
        for (String id : candidates) {
            Identifier identifier = Identifier.tryParse(id);
            if (identifier != null && Registries.ITEM.containsId(identifier)) {
                return id;
            }
        }
        return null;
    }
}