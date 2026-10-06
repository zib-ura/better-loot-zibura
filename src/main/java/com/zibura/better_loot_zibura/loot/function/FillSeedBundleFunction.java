package com.zibura.better_loot_zibura.loot.function;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.zibura.better_loot_zibura.event.CommonEvents;
import com.zibura.better_loot_zibura.loot.unification.ItemUnificationSolver;
import com.zibura.better_loot_zibura.loot.util.LootEvaluationContext;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.function.ConditionalLootFunction;
import net.minecraft.loot.function.LootFunctionType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.math.random.Random;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FillSeedBundleFunction extends ConditionalLootFunction {

    private final String seedTypeKey;
    private final double pooldivisor;
    private final int maxDistinctTypes;
    private final int minCount;
    private final int maxCount;

    protected FillSeedBundleFunction(LootCondition[] conditions, String seedTypeKey, double pooldivisor, int maxDistinctTypes, int minCount, int maxCount) {
        super(conditions);
        this.seedTypeKey = seedTypeKey;
        this.pooldivisor = pooldivisor;
        this.maxDistinctTypes = maxDistinctTypes;
        this.minCount = minCount;
        this.maxCount = maxCount;
    }

    @Override
    public LootFunctionType getType() {
        return CommonEvents.FILL_SEED_BUNDLE;
    }

    @Override
    protected ItemStack process(ItemStack stack, LootContext context) {
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

        Random random = context.getRandom();

        int calculatedMax = (int) Math.floor((double) validSeeds.size() / this.pooldivisor);
        int maxKinds = Math.min(this.maxDistinctTypes, calculatedMax);
        maxKinds = Math.max(1, maxKinds);

        int targetKinds = 1 + random.nextInt(maxKinds);
        targetKinds = Math.min(targetKinds, validSeeds.size());

        Collections.shuffle(validSeeds, new java.util.Random(random.nextLong()));
        List<String> selectedSeeds = validSeeds.subList(0, targetKinds);

        NbtCompound tag = stack.getOrCreateNbt();
        NbtList itemsTag = new NbtList();

        for (String seedId : selectedSeeds) {
            NbtCompound itemTag = new NbtCompound();
            itemTag.putString("id", seedId);

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

    public static class Serializer extends ConditionalLootFunction.Serializer<FillSeedBundleFunction> {
        @Override
        public void toJson(JsonObject json, FillSeedBundleFunction func, JsonSerializationContext context) {
            super.toJson(json, func, context);
            json.addProperty("seed_type_key", func.seedTypeKey);
            json.addProperty("pool_divisor", func.pooldivisor);
            json.addProperty("max_distinct_types", func.maxDistinctTypes);
            json.addProperty("min_count", func.minCount);
            json.addProperty("max_count", func.maxCount);
        }

        @Override
        public FillSeedBundleFunction fromJson(JsonObject json, JsonDeserializationContext context, LootCondition[] conditions) {
            String key = JsonHelper.getString(json, "seed_type_key");
            double pooldivisor = json.has("pool_divisor")
                    ? JsonHelper.getDouble(json, "pool_divisor")
                    : JsonHelper.getDouble(json, "x", 1.0);
            int maxDistinctTypes = json.has("max_distinct_types")
                    ? JsonHelper.getInt(json, "max_distinct_types")
                    : JsonHelper.getInt(json, "y", 3);
            int minCount = JsonHelper.getInt(json, "min_count", 1);
            int maxCount = JsonHelper.getInt(json, "max_count", 2);
            return new FillSeedBundleFunction(conditions, key, pooldivisor, maxDistinctTypes, minCount, maxCount);
        }
    }
}