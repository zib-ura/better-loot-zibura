package com.zibura.better_loot_zibura.loot.condition;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.zibura.better_loot_zibura.loot.util.LootEvaluationContext;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.condition.LootConditionType;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonSerializer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.biome.Biome;

import java.util.List;

public class MatchBiomeRegistryCondition implements LootCondition {

    public static LootConditionType TYPE;

    private final String registryKey;

    public MatchBiomeRegistryCondition(String registryKey) {
        this.registryKey = registryKey;
    }

    @Override
    public LootConditionType getType() {
        return TYPE;
    }

    @Override
    public boolean test(LootContext context) {
        Vec3d origin = context.get(LootContextParameters.ORIGIN);
        if (origin == null || this.registryKey == null || this.registryKey.isEmpty()) {
            return false;
        }

        BlockPos pos = BlockPos.ofFloored(origin);
        RegistryEntry<Biome> biomeEntry = context.getWorld().getBiome(pos);

        if (this.registryKey.startsWith("#")) {
            return checkTag(biomeEntry, this.registryKey.substring(1));
        }

        List<String> allowedEntries = LootEvaluationContext.resolveStringList(this.registryKey);

        if (allowedEntries.isEmpty()) {
            return checkSingleBiome(biomeEntry, this.registryKey);
        }

        for (String entry : allowedEntries) {
            if (entry.startsWith("#")) {
                if (checkTag(biomeEntry, entry.substring(1))) {
                    return true;
                }
            } else {
                if (checkSingleBiome(biomeEntry, entry)) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean checkTag(RegistryEntry<Biome> entry, String tagLocationStr) {
        Identifier id = Identifier.tryParse(tagLocationStr);
        if (id == null) return false;
        return entry.isIn(TagKey.of(RegistryKeys.BIOME, id));
    }

    private boolean checkSingleBiome(RegistryEntry<Biome> entry, String biomeLocationStr) {
        return entry.getKey()
                .map(key -> key.getValue().toString().equals(biomeLocationStr))
                .orElse(false);
    }

    public static LootCondition.Builder matchBiomeRegistry(String registryKey) {
        return () -> new MatchBiomeRegistryCondition(registryKey);
    }

    public static class Serializer implements JsonSerializer<MatchBiomeRegistryCondition> {
        @Override
        public void toJson(JsonObject json, MatchBiomeRegistryCondition value, JsonSerializationContext context) {
            json.addProperty("registry_key", value.registryKey);
        }

        @Override
        public MatchBiomeRegistryCondition fromJson(JsonObject json, JsonDeserializationContext context) {
            String registryKey = json.has("registry_key") ? json.get("registry_key").getAsString() : "";
            return new MatchBiomeRegistryCondition(registryKey);
        }
    }
}