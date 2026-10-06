//package com.zibura.better_loot_zibura.loot.SpecificLoot;
//
//import com.zibura.better_loot_zibura.loot.unification.ItemUnificationSolver;
//import com.zibura.better_loot_zibura.loot.util.LootEvaluationContext;
//import com.zibura.better_loot_zibura.loot.model.AllLevelModel.ItemDTO;
//import com.google.gson.*;
//import net.minecraft.resources.ResourceLocation;
//import net.minecraftforge.registries.ForgeRegistries;
//
//import java.util.*;
//
//public class SeedBagLootGenerator {
//
//    private static final String CONTAINER_BAG = "minecraft:bundle";
//    private static final String SEED_TYPE_SUFFIX = "_seed_type";
//    private static final int DEFAULT_SEED_COUNT = 2;
//    private static final Gson GSON = new Gson();
//
//    /**
//     * 自动扫描所有已注册的 *_seed_type，动态生成对应的 seedbag_* Item 级条目并注册
//     */
//    public static void initSeedBagTemplates() {
//        JsonObject dynamicRegistry = new JsonObject();
//
//        // 遍历当前上下文中所有已加载的配置 key
//        for (String key : LootEvaluationContext.getRegisteredKeys()) {
//            if (!key.endsWith(SEED_TYPE_SUFFIX)) continue;
//
//            // 提取前缀 (例如 "plains_seed_type" -> "plains")
//            String typePrefix = key.substring(0, key.length() - SEED_TYPE_SUFFIX.length());
//            if (typePrefix.isEmpty()) continue;
//
//            // 读取种子列表
//            List<String> seedTypes = getSeedTypeListSafe(key);
//            if (seedTypes.isEmpty()) continue;
//
//            // 生成种子袋 Item 列表
//            List<ItemDTO> bagItems = getSeedBagConfig_1_20_1(CONTAINER_BAG, seedTypes, DEFAULT_SEED_COUNT);
//            if (bagItems.isEmpty()) continue;
//
//            // 序列化为 JsonArray
//            JsonArray itemsArray = new JsonArray();
//            for (ItemDTO item : bagItems) {
//                itemsArray.add(GSON.toJsonTree(item));
//            }
//
//            // 自动注册对应条目: seedbag_<prefix>
//            dynamicRegistry.add("seedbag_" + typePrefix, itemsArray);
//        }
//
//        // 统一合并注册至全局上下文
//        LootEvaluationContext.registerAll(dynamicRegistry);
//    }
//
//    /**
//     * 构建 1.20.1 版本的收纳袋条目列表
//     */
//    public static List<ItemDTO> getSeedBagConfig_1_20_1(String bagType, List<String> seedTypes, int count) {
//        List<ItemDTO> bundleList = new ArrayList<>();
//
//        ResourceLocation bagLoc = ResourceLocation.tryParse(bagType);
//        if (bagLoc == null || !ForgeRegistries.ITEMS.containsKey(bagLoc)) {
//            return bundleList;
//        }
//
//        // 解析有效种子
//        List<String> validSeedTypes = new ArrayList<>();
//        if (seedTypes != null) {
//            for (String type : seedTypes) {
//                String reference = type.startsWith("lootjs:") ? type : "lootjs:" + type;
//                String resolvedId = ItemUnificationSolver.resolveReference(reference);
//                if (resolvedId != null) {
//                    validSeedTypes.add(resolvedId);
//                }
//            }
//        }
//
//        if (validSeedTypes.isEmpty()) return bundleList;
//
//        // 波动切片算法
//        int groupSize = Math.min((int) Math.ceil((double) validSeedTypes.size() / 6.0), 3);
//        List<List<String>> groups = new ArrayList<>();
//        int[] offsets = {1, 0, -1};
//        int offsetIdx = 0;
//        int i = 0;
//
//        while (i < validSeedTypes.size()) {
//            int currentOffset = offsets[offsetIdx % offsets.length];
//            int currentSize = Math.max(1, groupSize + currentOffset);
//
//            int endIndex = Math.min(i + currentSize, validSeedTypes.size());
//            groups.add(new ArrayList<>(validSeedTypes.subList(i, endIndex)));
//
//            i += currentSize;
//            offsetIdx++;
//        }
//
//        // 生成最终条目
//        if (groups.size() == 1) {
//            bundleList.add(createBundleItem(bagType, groups.get(0), count));
//        } else {
//            for (int j = 0; j < groups.size(); j++) {
//                for (int k = j + 1; k < groups.size(); k++) {
//                    List<String> combined = new ArrayList<>(groups.get(j));
//                    combined.addAll(groups.get(k));
//                    bundleList.add(createBundleItem(bagType, combined, count));
//                }
//            }
//        }
//
//        return bundleList;
//    }
//
//    /**
//     * 构造单个 Item 级别的 Bundle 配置
//     */
//    private static ItemDTO createBundleItem(String bagType, List<String> seedIds, int count) {
//        ItemDTO itemConfig = new ItemDTO();
//        itemConfig.id = bagType;
//        itemConfig.ratio = 10.0;
//
//        JsonObject nbtTag = new JsonObject();
//        JsonArray itemsArray = new JsonArray();
//
//        for (String id : seedIds) {
//            JsonObject itemObj = new JsonObject();
//            itemObj.addProperty("id", id);
//            itemObj.add("Count", new JsonPrimitive(count));
//            itemsArray.add(itemObj);
//        }
//        nbtTag.add("Items", itemsArray);
//
//        JsonObject nbtFunc = new JsonObject();
//        nbtFunc.addProperty("function", "minecraft:set_nbt");
//        nbtFunc.addProperty("tag", nbtTag.toString());
//
//        JsonArray functionsArray = new JsonArray();
//        functionsArray.add(nbtFunc);
//        itemConfig.jsonFunction = functionsArray;
//
//        return itemConfig;
//    }
//
//    private static List<String> getSeedTypeListSafe(String key) {
//        List<String> result = new ArrayList<>();
//        try {
//            JsonElement elem = LootEvaluationContext.resolveElement(new JsonPrimitive(key));
//            if (elem != null && elem.isJsonArray()) {
//                for (JsonElement item : elem.getAsJsonArray()) {
//                    if (item.isJsonPrimitive()) result.add(item.getAsString());
//                }
//            }
//        } catch (Exception ignored) {}
//        return result;
//    }
//}