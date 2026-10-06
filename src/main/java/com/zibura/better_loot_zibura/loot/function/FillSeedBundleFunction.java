package com.zibura.better_loot_zibura.loot.function;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
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
import java.util.Random;

public class FillSeedBundleFunction extends LootItemConditionalFunction {

    public static final MapCodec<FillSeedBundleFunction> CODEC =
            RecordCodecBuilder.mapCodec(instance ->
                    commonFields(instance).and(
                            instance.group(
                                    Codec.STRING
                                            .fieldOf("seed_type_key")
                                            .forGetter((FillSeedBundleFunction fn) -> fn.seedTypeKey),

                                    Codec.DOUBLE
                                            .optionalFieldOf("pool_divisor", 1.0)
                                            .forGetter((FillSeedBundleFunction fn) -> fn.pooldivisor),

                                    Codec.INT
                                            .optionalFieldOf("max_distinct_Types", 3)
                                            .forGetter((FillSeedBundleFunction fn) -> fn.maxDistinctTypes),

                                    Codec.INT
                                            .optionalFieldOf("min_count", 1)
                                            .forGetter((FillSeedBundleFunction fn) -> fn.minCount),

                                    Codec.INT
                                            .optionalFieldOf("max_count", 2)
                                            .forGetter((FillSeedBundleFunction fn) -> fn.maxCount)
                            )
                    ).apply(instance, FillSeedBundleFunction::new)
            );

    private final String seedTypeKey;
    private final double pooldivisor;
    private final int maxDistinctTypes;
    private final int minCount;
    private final int maxCount;

    protected FillSeedBundleFunction(
            Optional<Holder<LootItemCondition>> condition,
            String seedTypeKey,
            double pooldivisor,
            int maxDistinctTypes,
            int minCount,
            int maxCount
    ) {
        super(condition);

        this.seedTypeKey = seedTypeKey;
        this.pooldivisor = pooldivisor;
        this.maxDistinctTypes = maxDistinctTypes;
        this.minCount = minCount;
        this.maxCount = maxCount;
    }

    @Override
    public MapCodec<? extends LootItemConditionalFunction> codec() {
        return CODEC;
    }

@Override
protected ItemStack run(ItemStack stack, LootContext context) {
    System.out.println("[SeedBundle] RUN stack=" + stack);

    List<String> rawSeeds = getSeedTypeListSafe(this.seedTypeKey);

    System.out.println("[SeedBundle] key=" + this.seedTypeKey);
    System.out.println("[SeedBundle] rawSeeds=" + rawSeeds);
    System.out.println("[SeedBundle] ALL_MAP size="
            + ItemUnificationSolver.ALL_MAP.size());

    List<String> validSeeds = new ArrayList<>();

    for (String type : rawSeeds) {
        String reference = type.startsWith("lootjs:")
                ? type
                : "lootjs:" + type;

        String resolvedId =
                ItemUnificationSolver.resolveReference(reference);

        System.out.println(
                "[SeedBundle] " + reference
                        + " -> " + resolvedId
        );

        if (resolvedId != null) {
            validSeeds.add(resolvedId);
        }
    }

    System.out.println("[SeedBundle] validSeeds=" + validSeeds);

    if (validSeeds.isEmpty()) {
        System.out.println("[SeedBundle] !!! VALID SEEDS EMPTY !!!");
        return stack;
    }

    RandomSource random = context.getRandom();

    double divisor = this.pooldivisor <= 0.0
            ? 1.0
            : this.pooldivisor;

    int calculatedMax =
            (int) Math.floor(validSeeds.size() / divisor);

    int maxKinds =
            Math.min(this.maxDistinctTypes, calculatedMax);

    maxKinds = Math.max(1, maxKinds);

    int targetKinds =
            1 + random.nextInt(maxKinds);

    targetKinds =
            Math.min(targetKinds, validSeeds.size());

    Collections.shuffle(
            validSeeds,
            new Random(random.nextLong())
    );

    List<String> selectedSeeds =
            validSeeds.subList(0, targetKinds);

    System.out.println("[SeedBundle] selectedSeeds=" + selectedSeeds);

    List<ItemStackTemplate> bundleItems =
            new ArrayList<>();

    for (String seedId : selectedSeeds) {
        Identifier itemLoc = Identifier.tryParse(seedId);

        System.out.println(
                "[SeedBundle] seedId=" + seedId
                        + ", parsed=" + itemLoc
        );

        if (itemLoc == null) {
            System.out.println("[SeedBundle] invalid Identifier");
            continue;
        }

        Item item =
                BuiltInRegistries.ITEM.getValue(itemLoc);

        System.out.println(
                "[SeedBundle] registry item=" + item
        );

        if (item == null) {
            continue;
        }

        Item defaultItem =
                BuiltInRegistries.ITEM.getValue(
                        BuiltInRegistries.ITEM.getDefaultKey()
                );

        if (item == defaultItem) {
            System.out.println("[SeedBundle] DEFAULT ITEM!");
            continue;
        }

        int actualMin = Math.max(1, this.minCount);
        int actualMax = Math.max(actualMin, this.maxCount);

        int count =
                actualMin
                        + random.nextInt(
                        actualMax - actualMin + 1
                );

        ItemStackTemplate template =
                new ItemStackTemplate(item, count);

        System.out.println(
                "[SeedBundle] ADD template="
                        + template
                        + ", count=" + count
        );

        bundleItems.add(template);
    }

    System.out.println(
            "[SeedBundle] bundleItems size="
                    + bundleItems.size()
    );

    BundleContents contents =
            new BundleContents(bundleItems);

    System.out.println(
            "[SeedBundle] contents=" + contents
    );

    stack.set(
            DataComponents.BUNDLE_CONTENTS,
            contents
    );

    System.out.println(
            "[SeedBundle] FINAL COMPONENT="
                    + stack.get(DataComponents.BUNDLE_CONTENTS)
    );

    return stack;
}
    private List<String> getSeedTypeListSafe(String key) {
        List<String> result = new ArrayList<>();

        try {
            JsonElement elem =
                    LootEvaluationContext.resolveElement(
                            new JsonPrimitive(key)
                    );

            if (elem != null && elem.isJsonArray()) {
                for (JsonElement item : elem.getAsJsonArray()) {
                    if (item.isJsonPrimitive()) {
                        result.add(item.getAsString());
                    }
                }
            }
        } catch (Exception ignored) {
        }

        return result;
    }
}