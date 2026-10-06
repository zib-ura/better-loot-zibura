package com.zibura.better_loot_zibura.loot.util;

import com.zibura.better_loot_zibura.loot.model.AllLevelModel.GroupDTO;
import com.zibura.better_loot_zibura.loot.model.AllLevelModel.ItemDTO;
import com.google.gson.*;

import java.util.*;

public class LootEvaluationContext {
    private static final Gson GSON = new Gson();
    private static final Map<String, JsonElement> REGISTRY = new HashMap<>();

    public static void clear() {
        REGISTRY.clear();
    }

    public static Set<String> getRegisteredKeys() {
        return Collections.unmodifiableSet(REGISTRY.keySet());
    }
    public static void registerAll(JsonObject root) {
        root.entrySet().forEach(entry -> REGISTRY.put(entry.getKey(), entry.getValue()));
    }

    /**
     * 获取对应名称的 JsonElement，带循环/未定义安全检查
     */
    public static JsonElement resolveElement(JsonElement element) {
        if (element != null && element.isJsonPrimitive() && element.getAsJsonPrimitive().isString()) {
            String key = element.getAsString();
            if (REGISTRY.containsKey(key)) {
                return resolveElement(REGISTRY.get(key));
            }
            throw new IllegalArgumentException("未找到配置引用: " + key);
        }
        return element;
    }

    // ==========================================
    // 递归解析各层级 (支持直接展开 或 混合字符串引用展开)
    // ==========================================

    // LootEvaluationContext.java 中添加
    public static List<String> resolveStringList(JsonElement element) {
        JsonElement resolved = resolveElement(element);
        List<String> list = new ArrayList<>();
        if (resolved != null && resolved.isJsonArray()) {
            for (JsonElement e : resolved.getAsJsonArray()) {
                if (e.isJsonPrimitive() && e.getAsJsonPrimitive().isString()) {
                    String str = e.getAsString();
                    // 如果数组项引用了另一个已注册的键，则递归展开；否则作为值加入
                    if (REGISTRY.containsKey(str)) {
                        list.addAll(resolveStringList(REGISTRY.get(str)));
                    } else {
                        list.add(str);
                    }
                }
            }
        }
        return list;
    }

    public static List<String> resolveStringList(String key) {
        if (REGISTRY.containsKey(key)) {
            return resolveStringList(REGISTRY.get(key));
        }
        return Collections.emptyList();
    }

    // Level 1: 解析 Item 列表 (支持嵌套字符串引用如 "materials_items" 混合排布)
    public static List<ItemDTO> resolveItemList(JsonElement element) {
        JsonElement resolved = resolveElement(element);
        List<ItemDTO> items = new ArrayList<>();
        if (resolved != null && resolved.isJsonArray()) {
            for (JsonElement itemElem : resolved.getAsJsonArray()) {
                if (itemElem.isJsonPrimitive() && itemElem.getAsJsonPrimitive().isString()) {
                    // 遇到字符串 key，递归解析并展平成列表加入
                    items.addAll(resolveItemList(itemElem));
                } else if (itemElem.isJsonObject()) {
                    items.add(GSON.fromJson(itemElem, ItemDTO.class));
                }
            }
        }
        return items;
    }

    // Level 2: 解析 Group 列表 (支持嵌套引用展开)
    public static List<GroupDTO> resolveGroupList(JsonElement element) {
        JsonElement resolved = resolveElement(element);
        List<GroupDTO> groups = new ArrayList<>();
        if (resolved != null && resolved.isJsonArray()) {
            for (JsonElement grpElem : resolved.getAsJsonArray()) {
                if (grpElem.isJsonPrimitive() && grpElem.getAsJsonPrimitive().isString()) {
                    groups.addAll(resolveGroupList(grpElem));
                    continue;
                }
                if (!grpElem.isJsonObject()) continue;

                JsonObject obj = grpElem.getAsJsonObject();
                GroupDTO group = new GroupDTO();
                group.groupName = obj.has("groupName") ? obj.get("groupName").getAsString() : "default";
                group.groupWeight = obj.has("groupWeight") ? obj.get("groupWeight").getAsDouble() : 1.0;
                if (obj.has("min")) group.min = obj.get("min").getAsInt();
                if (obj.has("max")) group.max = obj.get("max").getAsInt();

                if (obj.has("damage")) group.damage = obj.get("damage");
                if (obj.has("enchantChance")) group.enchantChance = obj.get("enchantChance").getAsDouble();
                if (obj.has("enchantLevels")) {
                    group.enchantLevels = GSON.fromJson(obj.get("enchantLevels"), new com.google.gson.reflect.TypeToken<List<Integer>>(){}.getType());
                }
                if (obj.has("enchantRandomly")) {
                    group.enchantRandomly = GSON.fromJson(obj.get("enchantRandomly"), new com.google.gson.reflect.TypeToken<List<String>>(){}.getType());
                }
                if (obj.has("potion")) group.potion = obj.get("potion").getAsString();
                if (obj.has("jsonFunction")) group.jsonFunction = obj.get("jsonFunction").getAsJsonArray();
                if (obj.has("exactEnchants")) {
                    group.exactEnchants = GSON.fromJson(obj.get("exactEnchants"), new com.google.gson.reflect.TypeToken<Map<String, Integer>>(){}.getType());
                }
                if (obj.has("nbt") && obj.get("nbt").isJsonObject()) {
                    group.nbt = obj.getAsJsonObject("nbt");
                }
                if (obj.has("conditions")) {
                    group.conditions = GSON.fromJson(obj.get("conditions"), new com.google.gson.reflect.TypeToken<Map<String, Object>>(){}.getType());
                }


                // items 字段传递给 resolveItemList 处理
                if (obj.has("items")) {
                    group.items = resolveItemList(obj.get("items"));
                }
                groups.add(group);
            }
        }
        return groups;
    }

    // Level 3 & 4: 解析 Content / Loot 级结构
    public static JsonArray resolveContent(JsonElement element) {
        JsonElement resolved = resolveElement(element);
        if (resolved != null && resolved.isJsonArray()) {
            JsonArray sourceArray = resolved.getAsJsonArray();
            JsonArray flatArray = new JsonArray();

            for (JsonElement entry : sourceArray) {
                // 如果是单纯的字符串引用（未带Roll参数），展开并合并
                if (entry.isJsonPrimitive() && entry.getAsJsonPrimitive().isString()) {
                    JsonArray subArr = resolveContent(entry);
                    for (JsonElement sub : subArr) {
                        flatArray.add(sub);
                    }
                } else {
                    flatArray.add(entry);
                }
            }
            return flatArray;
        }
        return new JsonArray();
    }
}