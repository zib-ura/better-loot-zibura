package com.zibura.better_loot_zibura.loot.function;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.zibura.better_loot_zibura.event.CommonEvents;
import com.zibura.better_loot_zibura.loot.unification.ItemUnificationSolver;
import com.zibura.better_loot_zibura.loot.util.LootEvaluationContext;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.component.BundleContents;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class FillSeedBundleFunction extends LootItemConditionalFunction {

    public static final MapCodec<FillSeedBundleFunction> CODEC = RecordCodecBuilder.mapCodec(instance ->
            commonFields(instance).and(
                    instance.group(
                            Codec.STRING.fieldOf("seed_type_key").forGetter(f -> f.seedTypeKey),
                            Codec.DOUBLE.optionalFieldOf("pool_divisor", 1.0).forGetter(f -> f.pooldivisor),
                            Codec.INT.optionalFieldOf("max_distinct_types", 3).forGetter(f -> f.maxDistinctTypes),
                            Codec.INT.optionalFieldOf("min_count", 1).forGetter(f -> f.minCount),
                            Codec.INT.optionalFieldOf("max_count", 2).forGetter(f -> f.maxCount)
                    )
            ).apply(instance, FillSeedBundleFunction::new)
    );

    private final String seedTypeKey;
    private final double pooldivisor;
    private final int maxDistinctTypes;
    private final int minCount;
    private final int maxCount;

    protected FillSeedBundleFunction(Optional<Holder<LootItemCondition>> condition, String seedTypeKey, double pooldivisor, int maxDistinctTypes, int minCount, int maxCount) {
        super(condition);
        this.seedTypeKey = seedTypeKey;
        this.pooldivisor = pooldivisor;
        this.maxDistinctTypes = maxDistinctTypes;
        this.minCount = minCount;
        this.maxCount = maxCount;
    }

    @Override
    public MapCodec<FillSeedBundleFunction> codec() {
        return CommonEvents.FILL_SEED_BUNDLE;
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

        int calculatedMax = (int) Math.floor((double) validSeeds.size() / this.pooldivisor);
        int maxKinds = Math.min(this.maxDistinctTypes, calculatedMax);
        maxKinds = Math.max(1, maxKinds);

        int targetKinds = 1 + random.nextInt(maxKinds);
        targetKinds = Math.min(targetKinds, validSeeds.size());

        Collections.shuffle(validSeeds, new java.util.Random(random.nextLong()));
        List<String> selectedSeeds = validSeeds.subList(0, targetKinds);

        List<ItemStack> bundleItems = new ArrayList<>();
        for (String seedId : selectedSeeds) {
            Identifier itemId = Identifier.tryParse(seedId);
            if (itemId == null) continue;

            Item item = BuiltInRegistries.ITEM.getValue(itemId);
            int countRange = Math.max(1, this.maxCount - this.minCount + 1);
            int count = this.minCount + random.nextInt(countRange);

            bundleItems.add(new ItemStack(item, count));
        }

        // 写入收纳袋组件数据
        List<ItemStackTemplate> templates = bundleItems.stream()
                .filter(stack2 -> !stack2.isEmpty())
                .map(ItemStackTemplate::fromNonEmptyStack)
                .toList();

        stack.set(DataComponents.BUNDLE_CONTENTS, new BundleContents(templates));
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