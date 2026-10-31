package com.zibura.better_loot_zibura.loot.function;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.zibura.better_loot_zibura.event.CommonEvents;
import com.zibura.better_loot_zibura.loot.unification.ItemUnificationSolver;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.BundleContentsComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.function.ConditionalLootFunction;
import net.minecraft.loot.function.LootFunctionType;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.random.Random;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class FillSeedBundleFunction extends ConditionalLootFunction {

    private static final float EXTRA_KIND_CHANCE = 0.5F;

    // Only the modern JSON keys are supported; omitted values use the defaults below.
    public static final MapCodec<FillSeedBundleFunction> CODEC = RecordCodecBuilder.mapCodec(instance ->
            addConditionsField(instance).and(
                    instance.group(
                            Codec.STRING.listOf().fieldOf("seed_types").forGetter(f -> f.seedTypes),
                            Codec.DOUBLE.optionalFieldOf("pool_divisor")
                                    .forGetter(f -> Optional.of(f.pooldivisor)),
                            Codec.INT.optionalFieldOf("max_distinct_types")
                                    .forGetter(f -> Optional.of(f.maxDistinctTypes)),
                            Codec.INT.optionalFieldOf("min_count", 1).forGetter(f -> f.minCount),
                            Codec.INT.optionalFieldOf("max_count", 2).forGetter(f -> f.maxCount)
                    )
            ).apply(instance, (conditions, seeds, divisor, kinds, min, max) ->
                    new FillSeedBundleFunction(
                            conditions,
                            seeds,
                            divisor.orElse(1.0),
                            kinds.orElse(3),
                            min,
                            max
                    )
            )
    );

    private final List<String> seedTypes;
    private final double pooldivisor;
    private final int maxDistinctTypes;
    private final int minCount;
    private final int maxCount;

    protected FillSeedBundleFunction(List<LootCondition> conditions, List<String> seedTypes,
                                     double pooldivisor, int maxDistinctTypes, int minCount, int maxCount) {
        super(conditions);
        // The Forge linker has already resolved seed_type_key to seed_types.
        this.seedTypes = List.copyOf(seedTypes);
        this.pooldivisor = pooldivisor;
        this.maxDistinctTypes = maxDistinctTypes;
        this.minCount = minCount;
        this.maxCount = maxCount;
    }

    @Override
    public LootFunctionType<FillSeedBundleFunction> getType() {
        return CommonEvents.FILL_SEED_BUNDLE;
    }

    @Override
    protected ItemStack process(ItemStack stack, LootContext context) {
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

        Random random = context.getRandom();

        int calculatedMax = (int) Math.floor((double) validSeeds.size() / this.pooldivisor);
        int maxKinds = Math.min(this.maxDistinctTypes, calculatedMax);
        maxKinds = Math.max(1, maxKinds);

        // Match Forge 1.20.1: 1 + Binomial(maxKinds - 1, 0.5), not uniform [1, maxKinds].
        int targetKinds = 1;
        for (int i = 1; i < maxKinds; i++) {
            if (random.nextFloat() < EXTRA_KIND_CHANCE) {
                targetKinds++;
            }
        }
        targetKinds = Math.min(targetKinds, validSeeds.size());

        Collections.shuffle(validSeeds, new java.util.Random(random.nextLong()));
        List<String> selectedSeeds = validSeeds.subList(0, targetKinds);

        // 1.21+ 使用组件替换原有 NBT 处理
        List<ItemStack> bundleItems = new ArrayList<>();
        for (String seedId : selectedSeeds) {
            Identifier itemId = Identifier.tryParse(seedId);
            if (itemId == null) continue;

            Item item = Registries.ITEM.get(itemId);
            int countRange = Math.max(1, this.maxCount - this.minCount + 1);
            int count = this.minCount + random.nextInt(countRange);

            bundleItems.add(new ItemStack(item, count));
        }

        // 写入收纳袋组件数据
        stack.set(DataComponentTypes.BUNDLE_CONTENTS, new BundleContentsComponent(bundleItems));
        return stack;
    }

}
