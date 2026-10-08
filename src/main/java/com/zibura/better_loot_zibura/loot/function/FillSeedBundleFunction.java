package com.zibura.better_loot_zibura.loot.function;

import com.google.gson.*;
import com.zibura.better_loot_zibura.event.CommonEvents;
import com.zibura.better_loot_zibura.loot.unification.ItemUnificationSolver;
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
import org.jetbrains.annotations.NotNull;

public class FillSeedBundleFunction extends LootItemConditionalFunction {

    private final List<String> seedTypes; // 直接种子列表
    private final double pooldivisor; // 原 x: 种子池大小的折算比例因子
    private final int maxDistinctTypes; // 原 y: 允许抽取的最大种子种类上限
    private final int minCount;
    private final int maxCount;

    protected FillSeedBundleFunction(LootItemCondition[] conditions, List<String> seedTypes, double pooldivisor, int maxDistinctTypes, int minCount, int maxCount) {
        super(conditions);
        this.seedTypes = List.copyOf(seedTypes);
        this.pooldivisor = pooldivisor;
        this.maxDistinctTypes = maxDistinctTypes;
        this.minCount = minCount;
        this.maxCount = maxCount;
    }

    @Override
    public @NotNull LootItemFunctionType getType() {
        return CommonEvents.ModBusEvents.FILL_SEED_BUNDLE;
    }

    @Override
    protected @NotNull ItemStack run(@NotNull ItemStack stack, @NotNull LootContext context) {
        List<String> rawSeeds = this.seedTypes;
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

        // 计算最大种类数：min(maxDistinctTypes, floor(size / pooldivisor))
        int calculatedMax = (int) Math.floor((double) validSeeds.size() / this.pooldivisor);
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

    public static class Serializer extends LootItemConditionalFunction.Serializer<FillSeedBundleFunction> {
        @Override
        public void serialize(@NotNull JsonObject json, @NotNull FillSeedBundleFunction func, @NotNull JsonSerializationContext context) {
            super.serialize(json, func, context);
            JsonArray array = new JsonArray();
            for (String seed : func.seedTypes) array.add(seed);
            json.add("seed_types", array);
            json.addProperty("pool_divisor", func.pooldivisor);
            json.addProperty("max_distinct_types", func.maxDistinctTypes);
            json.addProperty("min_count", func.minCount);
            json.addProperty("max_count", func.maxCount);
        }

        @Override
        public @NotNull FillSeedBundleFunction deserialize(@NotNull JsonObject json, @NotNull JsonDeserializationContext context, @NotNull LootItemCondition[] conditions) {
            List<String> seeds = new ArrayList<>();
            if (json.has("seed_types")) {
                JsonElement element = json.get("seed_types");
                if (!element.isJsonArray()) {
                    throw new JsonSyntaxException("seed_types must be an array of strings");
                }
                for (JsonElement item : element.getAsJsonArray()) {
                    if (!item.isJsonPrimitive() || !item.getAsJsonPrimitive().isString()) {
                        throw new JsonSyntaxException("seed_types entries must be strings");
                    }
                    seeds.add(item.getAsString());
                }
            } else {
                throw new JsonSyntaxException("seed_types is required; seed_type_key is no longer supported");
            }
            // 兼容旧键名 "x" 与新键名 "pool_divisor"
            double pooldivisor = json.has("pool_divisor")
                    ? GsonHelper.getAsDouble(json, "pool_divisor")
                    : GsonHelper.getAsDouble(json, "x", 1.0);
            // 兼容旧键名 "y" 与新键名 "max_distinct_types"
            int maxDistinctTypes = json.has("max_distinct_types")
                    ? GsonHelper.getAsInt(json, "max_distinct_types")
                    : GsonHelper.getAsInt(json, "y", 3);
            int minCount = GsonHelper.getAsInt(json, "min_count", 1);
            int maxCount = GsonHelper.getAsInt(json, "max_count", 2);
            return new FillSeedBundleFunction(conditions, seeds, pooldivisor, maxDistinctTypes, minCount, maxCount);
        }
    }
}