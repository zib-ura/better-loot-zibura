package com.zibura.better_loot_zibura.loot.function;

import com.google.gson.*;
import com.zibura.better_loot_zibura.event.CommonEvents;
import com.zibura.better_loot_zibura.loot.unification.ItemUnificationSolver;
import com.zibura.better_loot_zibura.loot.util.LootEvaluationContext;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

import java.util.*;

public class FillSeedBundleFunction extends LootItemConditionalFunction {

    private final String seedTypeKey; // 传入 context key，例如 "plains_seed_type"
    private final double poolDivisor; // 原 x: 种子池大小的折算比例因子
    private final int maxDistinctTypes; // 原 y: 允许抽取的最大种子种类上限
    private final int minCount;
    private final int maxCount;

    protected FillSeedBundleFunction(LootItemCondition[] conditions, String seedTypeKey, double poolDivisor, int maxDistinctTypes, int minCount, int maxCount) {
        super(conditions);
        this.seedTypeKey = seedTypeKey;
        this.poolDivisor = poolDivisor;
        this.maxDistinctTypes = maxDistinctTypes;
        this.minCount = minCount;
        this.maxCount = maxCount;
    }

    @Override
    public LootItemFunctionType getType() {
        return CommonEvents.ModBusEvents.FILL_SEED_BUNDLE;
    }

    @Override
    protected ItemStack run(ItemStack stack, LootContext context) {
        List<String> rawSeeds = getSeedTypeListSafe(this.seedTypeKey);
        List<String> validSeeds = new ArrayList<>();

        for (String type : rawSeeds) {
            String reference = type.startsWith("lootjs:") ? type : "lootjs:" + type;
            String resolvedId = ItemUnificationSolver.resolveReference(reference);
            if (resolvedId != null) {
                validSeeds.add(resolvedId);
            }
        }

        if (validSeeds.isEmpty()) {
            return stack;
        }

        RandomSource random = context.getRandom();

        // 计算最大种类数：min(maxDistinctTypes, floor(size / poolDivisor))
        int calculatedMax = (int) Math.floor((double) validSeeds.size() / this.poolDivisor);
        int maxKinds = Math.min(this.maxDistinctTypes, calculatedMax);
        maxKinds = Math.max(1, maxKinds); // 防止小于 1

        // 随机抽取种类数 [1, maxKinds]
        int targetKinds = 1 + random.nextInt(maxKinds);
        targetKinds = Math.min(targetKinds, validSeeds.size());

        // 打乱并选取 targetKinds 个不重复种子
        Collections.shuffle(validSeeds, new Random(random.nextLong()));
        List<String> selectedSeeds = validSeeds.subList(0, targetKinds);

        // 写入 Bundle 的 Items NBT
        CompoundTag tag = stack.getOrCreateTag();
        ListTag itemsTag = new ListTag();

        for (String seedId : selectedSeeds) {
            CompoundTag itemTag = new CompoundTag();
            itemTag.putString("id", seedId);

            // 数量在 [minCount, maxCount] 之间随机
            int countRange = Math.max(1, this.maxCount - this.minCount + 1);
            int count = this.minCount + random.nextInt(countRange);
            itemTag.putByte("Count", (byte) count);

            itemsTag.add(itemTag);
        }

        tag.put("Items", itemsTag);
        return stack;
    }

    private List<String> getSeedTypeListSafe(String key) {
        List<String> result = new ArrayList<>();
        try {
            JsonElement elem = LootEvaluationContext.resolveElement(new JsonPrimitive(key));
            if (elem != null && elem.isJsonArray()) {
                for (JsonElement item : elem.getAsJsonArray()) {
                    if (item.isJsonPrimitive()) result.add(item.getAsString());
                }
            }
        } catch (Exception ignored) {}
        return result;
    }

    public static class Serializer extends LootItemConditionalFunction.Serializer<FillSeedBundleFunction> {
        @Override
        public void serialize(JsonObject json, FillSeedBundleFunction func, JsonSerializationContext context) {
            super.serialize(json, func, context);
            json.addProperty("seed_type_key", func.seedTypeKey);
            json.addProperty("pool_divisor", func.poolDivisor);
            json.addProperty("max_distinct_types", func.maxDistinctTypes);
            json.addProperty("min_count", func.minCount);
            json.addProperty("max_count", func.maxCount);
        }

        @Override
        public FillSeedBundleFunction deserialize(JsonObject json, JsonDeserializationContext context, LootItemCondition[] conditions) {
            String key = GsonHelper.getAsString(json, "seed_type_key");
            // 兼容旧键名 "x" 与新键名 "pool_divisor"
            double poolDivisor = json.has("pool_divisor")
                    ? GsonHelper.getAsDouble(json, "pool_divisor")
                    : GsonHelper.getAsDouble(json, "x", 1.0);
            // 兼容旧键名 "y" 与新键名 "max_distinct_types"
            int maxDistinctTypes = json.has("max_distinct_types")
                    ? GsonHelper.getAsInt(json, "max_distinct_types")
                    : GsonHelper.getAsInt(json, "y", 3);
            int minCount = GsonHelper.getAsInt(json, "min_count", 1);
            int maxCount = GsonHelper.getAsInt(json, "max_count", 2);
            return new FillSeedBundleFunction(conditions, key, poolDivisor, maxDistinctTypes, minCount, maxCount);
        }
    }
}