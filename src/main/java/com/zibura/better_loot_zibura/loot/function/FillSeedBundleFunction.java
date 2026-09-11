package com.zibura.better_loot_zibura.loot.function;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.zibura.better_loot_zibura.event.CommonEvents;
import com.zibura.better_loot_zibura.loot.unification.ItemUnificationSolver;
import com.zibura.better_loot_zibura.loot.util.LootEvaluationContext;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.BundleContents;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class FillSeedBundleFunction extends LootItemConditionalFunction {

    public static final MapCodec<FillSeedBundleFunction> CODEC = RecordCodecBuilder.mapCodec(instance ->
            commonFields(instance).and(
                    instance.group(
                            Codec.STRING.fieldOf("seed_type_key").forGetter(fn -> fn.seedTypeKey),
                            Codec.DOUBLE.optionalFieldOf("pool_divisor", 1.0).forGetter(fn -> fn.poolDivisor),
                            Codec.INT.optionalFieldOf("max_distinct_types", 3).forGetter(fn -> fn.maxDistinctTypes),
                            Codec.INT.optionalFieldOf("min_count", 1).forGetter(fn -> fn.minCount),
                            Codec.INT.optionalFieldOf("max_count", 2).forGetter(fn -> fn.maxCount)
                    )
            ).apply(instance, FillSeedBundleFunction::new)
    );

    private final String seedTypeKey;
    private final double poolDivisor;
    private final int maxDistinctTypes;
    private final int minCount;
    private final int maxCount;

    protected FillSeedBundleFunction(List<LootItemCondition> conditions, String seedTypeKey, double poolDivisor, int maxDistinctTypes, int minCount, int maxCount) {
        super(conditions);
        this.seedTypeKey = seedTypeKey;
        this.poolDivisor = poolDivisor;
        this.maxDistinctTypes = maxDistinctTypes;
        this.minCount = minCount;
        this.maxCount = maxCount;
    }

    @Override
    public LootItemFunctionType<FillSeedBundleFunction> getType() {
        return CommonEvents.ModLootFunctions.FILL_SEED_BUNDLE;
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

        int calculatedMax = (int) Math.floor((double) validSeeds.size() / this.poolDivisor);
        int maxKinds = Math.min(this.maxDistinctTypes, calculatedMax);
        maxKinds = Math.max(1, maxKinds);

        int targetKinds = 1 + random.nextInt(maxKinds);
        targetKinds = Math.min(targetKinds, validSeeds.size());

        Collections.shuffle(validSeeds, new Random(random.nextLong()));
        List<String> selectedSeeds = validSeeds.subList(0, targetKinds);

        // 1.21.1 Data Components: 构建收纳袋物品列表
        List<ItemStack> bundleItems = new ArrayList<>();

        for (String seedId : selectedSeeds) {
            ResourceLocation itemLoc = ResourceLocation.tryParse(seedId);
            if (itemLoc != null) {
                Item item = BuiltInRegistries.ITEM.get(itemLoc);
                if (item != null && item != BuiltInRegistries.ITEM.get(BuiltInRegistries.ITEM.getDefaultKey())) {
                    int countRange = Math.max(1, this.maxCount - this.minCount + 1);
                    int count = this.minCount + random.nextInt(countRange);
                    bundleItems.add(new ItemStack(item, count));
                }
            }
        }

        // 写入收纳袋组件
        stack.set(DataComponents.BUNDLE_CONTENTS, new BundleContents(bundleItems));
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
}