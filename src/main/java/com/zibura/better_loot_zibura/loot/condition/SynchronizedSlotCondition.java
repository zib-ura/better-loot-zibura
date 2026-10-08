package com.zibura.better_loot_zibura.loot.condition;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;

public final class SynchronizedSlotCondition implements LootItemCondition {
    public static LootItemConditionType TYPE;

    private final long period;
    private final int minSlot;
    private final int maxSlot;

    public SynchronizedSlotCondition(long period, int minSlot, int maxSlot) {
        if (period <= 0) throw new IllegalArgumentException("period must be > 0");
        if (minSlot < 0 || maxSlot < minSlot || maxSlot >= period) {
            throw new IllegalArgumentException("invalid synchronized slot range: [" + minSlot + "," + maxSlot + "] / " + period);
        }
        this.period = period;
        this.minSlot = minSlot;
        this.maxSlot = maxSlot;
    }

    public long period() { return period; }
    public int minSlot() { return minSlot; }
    public int maxSlot() { return maxSlot; }

    @Override
    public boolean test(LootContext context) {
        long slot = Math.floorMod(context.getLevel().getGameTime(), period);
        return slot >= minSlot && slot <= maxSlot;
    }

    @Override
    public LootItemConditionType getType() { return TYPE; }

    public static LootItemCondition.Builder builder(long period, int minSlot, int maxSlot) {
        return () -> new SynchronizedSlotCondition(period, minSlot, maxSlot);
    }

    public static final class Serializer implements net.minecraft.world.level.storage.loot.Serializer<SynchronizedSlotCondition> {
        @Override
        public void serialize(JsonObject json, SynchronizedSlotCondition value, JsonSerializationContext context) {
            json.addProperty("period", value.period);
            json.addProperty("min_slot", value.minSlot);
            json.addProperty("max_slot", value.maxSlot);
        }

        @Override
        public SynchronizedSlotCondition deserialize(JsonObject json, JsonDeserializationContext context) {
            return new SynchronizedSlotCondition(
                    GsonHelper.getAsLong(json, "period"),
                    GsonHelper.getAsInt(json, "min_slot"),
                    GsonHelper.getAsInt(json, "max_slot")
            );
        }
    }
}
