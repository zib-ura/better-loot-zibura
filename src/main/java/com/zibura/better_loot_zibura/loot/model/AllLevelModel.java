package com.zibura.better_loot_zibura.loot.model;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.*;

/**
 * 战利品表注册入口与时间轴分配工具类
 */
public final class AllLevelModel {

    private AllLevelModel() {}

    // ==========================================
    // 1. 数据结构定义
    // ==========================================
    public static class FinalTable {
        public List<WeightedSubTable> data;
        public String mode = "REPLACE";

        public FinalTable(List<WeightedSubTable> data, String mode) {
            this.data = data;
            this.mode = mode != null ? mode : "REPLACE";
        }
    }

    public static class WeightedSubTable {
        public List<SubTableEntry> contentArray;
        public int weight;

        public WeightedSubTable(List<SubTableEntry> contentArray, int weight) {
            this.contentArray = contentArray;
            this.weight = weight;
        }
    }

    public static class SubTableEntry {
        public List<GroupDTO> itemArray;
        public int minRolls;
        public int maxRolls;
        public JsonObject conditionJson;

        public SubTableEntry(List<GroupDTO> pools, int minRolls, int maxRolls, JsonObject conditionJson) {
            this.itemArray = pools;
            this.minRolls = minRolls;
            this.maxRolls = maxRolls;
            this.conditionJson = conditionJson;
        }
    }

    public static class GroupDTO {
        public String groupName = "default";
        public double groupWeight = 1.0;
        public Integer min;
        public Integer max;
        public JsonElement damage;
        public double enchantChance = 0.0;
        public List<Integer> enchantLevels;
        public Map<String, Integer> exactEnchants;
        public List<String> enchantRandomly;
        public JsonElement jsonFunction;
        public String potion;

        // 兼容旧版 NBT 与 1.21.1 Data Components
        public JsonObject nbt;
        public JsonObject components;

        public Map<String, Object> conditions = new HashMap<>();
        public List<ItemDTO> items = new ArrayList<>();
    }

    public static class ItemDTO {
        public String id;
        public String reference;
        public String type;
        public boolean empty = false;
        public double ratio = 1.0;
        public Integer min;
        public Integer max;
        public JsonElement damage;
        public Double randomChance;
        public List<Integer> enchantLevels;
        public Map<String, Integer> exactEnchants;
        public List<String> enchantRandomly;
        public JsonElement jsonFunction;
        public String potion;

        // 兼容旧版 NBT 与 1.21.1 Data Components
        public JsonObject nbt;
        public JsonObject components;

        public Map<String, Object> conditions = new HashMap<>();
    }
}