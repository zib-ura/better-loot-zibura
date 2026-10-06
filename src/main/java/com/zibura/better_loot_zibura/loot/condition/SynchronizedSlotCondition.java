package com.zibura.better_loot_zibura.loot.condition;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.zibura.better_loot_zibura.event.CommonEvents;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.condition.LootConditionType;
import net.minecraft.loot.context.LootContext;

public final class SynchronizedSlotCondition implements LootCondition {

    public static final MapCodec<SynchronizedSlotCondition> CODEC =
            RecordCodecBuilder.mapCodec(instance -> instance.group(
                    Codec.LONG.fieldOf("period")
                            .forGetter(SynchronizedSlotCondition::period),
                    Codec.INT.fieldOf("min_slot")
                            .forGetter(SynchronizedSlotCondition::minSlot),
                    Codec.INT.fieldOf("max_slot")
                            .forGetter(SynchronizedSlotCondition::maxSlot)
            ).apply(instance, SynchronizedSlotCondition::new));

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
        long gameTime = context.getWorld().getTime();
        long slot = Math.floorMod(gameTime, period);

        return slot >= minSlot && slot <= maxSlot;
    }

    @Override
    public LootConditionType getType() {
        return CommonEvents.SYNCHRONIZED_SLOT;
    }

    public static LootCondition.Builder builder(
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