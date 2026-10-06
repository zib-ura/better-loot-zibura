package com.zibura.better_loot_zibura.loot.condition;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;

public final class SynchronizedSlotCondition implements LootItemCondition {

    public static final MapCodec<SynchronizedSlotCondition> CODEC =
            RecordCodecBuilder.mapCodec(instance -> instance.group(
                    Codec.LONG.fieldOf("period")
                            .forGetter(SynchronizedSlotCondition::period),
                    Codec.INT.fieldOf("min_slot")
                            .forGetter(SynchronizedSlotCondition::minSlot),
                    Codec.INT.fieldOf("max_slot")
                            .forGetter(SynchronizedSlotCondition::maxSlot)
            ).apply(instance, SynchronizedSlotCondition::new));

    public static LootItemConditionType TYPE;

    private final long period;
    private final int minSlot;
    private final int maxSlot;

    public SynchronizedSlotCondition(
            long period,
            int minSlot,
            int maxSlot
    ) {
        if (period <= 0) {
            throw new IllegalArgumentException(
                    "period must be > 0"
            );
        }

        if (minSlot < 0
                || maxSlot < minSlot
                || maxSlot >= period) {
            throw new IllegalArgumentException(
                    "invalid synchronized slot range: ["
                            + minSlot
                            + ","
                            + maxSlot
                            + "] / "
                            + period
            );
        }

        this.period = period;
        this.minSlot = minSlot;
        this.maxSlot = maxSlot;
    }

    public long period() {
        return period;
    }

    public int minSlot() {
        return minSlot;
    }

    public int maxSlot() {
        return maxSlot;
    }

    @Override
    public boolean test(LootContext context) {
        long gameTime = context.getLevel().getGameTime();
        long slot = Math.floorMod(gameTime, period);

        return slot >= minSlot && slot <= maxSlot;
    }

    @Override
    public LootItemConditionType getType() {
        return TYPE;
    }

    public static LootItemCondition.Builder builder(
            long period,
            int minSlot,
            int maxSlot
    ) {
        return () ->
                new SynchronizedSlotCondition(
                        period,
                        minSlot,
                        maxSlot
                );
    }
}