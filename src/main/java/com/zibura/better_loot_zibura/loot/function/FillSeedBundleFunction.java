package com.zibura.better_loot_zibura.loot.function;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.zibura.better_loot_zibura.event.CommonEvents;
import com.zibura.better_loot_zibura.loot.unification.ItemUnificationSolver;
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
                            Codec.STRING.listOf().fieldOf("seed_types").forGetter(fn -> fn.seedTypes),
                            Codec.DOUBLE.optionalFieldOf("pool_divisor", 1.0).forGetter(fn -> fn.pooldivisor),
                            Codec.INT.optionalFieldOf("max_distinct_types", 3).forGetter(fn -> fn.maxDistinctTypes),
                            Codec.INT.optionalFieldOf("min_count", 1).forGetter(fn -> fn.minCount),
                            Codec.INT.optionalFieldOf("max_count", 2).forGetter(fn -> fn.maxCount)
                    )
            ).apply(instance, FillSeedBundleFunction::new)
    );

    private static final float EXTRA_KIND_CHANCE = 0.5F;

    private final List<String> seedTypes;
    private final double pooldivisor;
    private final int maxDistinctTypes;
    private final int minCount;
    private final int maxCount;

    protected FillSeedBundleFunction(List<LootItemCondition> conditions, List<String> seedTypes, double pooldivisor, int maxDistinctTypes, int minCount, int maxCount) {
        super(conditions);
        this.seedTypes = List.copyOf(seedTypes);
        this.pooldivisor = pooldivisor;
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

        int calculatedMax = (int) Math.floor((double) validSeeds.size() / this.pooldivisor);
        int maxKinds = Math.min(this.maxDistinctTypes, calculatedMax);
        maxKinds = Math.max(1, maxKinds);

        // 二项分布：targetKinds = 1 + Binomial(maxKinds - 1, 0.35)
        int targetKinds = 1;
        for (int i = 1; i < maxKinds; i++) {
            if (random.nextFloat() < EXTRA_KIND_CHANCE) {
                targetKinds++;
            }
        }
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

}
