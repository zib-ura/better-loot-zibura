package com.zibura.better_loot_zibura.loot.condition;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.minecraft.world.phys.Vec3;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
public class MatchBiomeRegistryCondition implements LootItemCondition {

    public static LootItemConditionType TYPE;

    private final String registryKey;

    public MatchBiomeRegistryCondition(String registryKey) {
        this.registryKey = registryKey;
    }

    @Override
    public LootItemConditionType getType() {
        return TYPE;
    }

    @Override
    public boolean test(LootContext context) {
        Vec3 origin = context.getParamOrNull(LootContextParams.ORIGIN);
        if (origin == null || this.registryKey == null || this.registryKey.isEmpty()) {
            return false;
        }

        BlockPos pos = BlockPos.containing(origin);
        Holder<Biome> biomeHolder = context.getLevel().getBiome(pos);

        // 1. 如果本身就是 Tag 写法 (如 "#minecraft:is_ocean")，直接匹配 Tag，不需要建任何数组
        if (this.registryKey.startsWith("#")) {
            return checkTag(biomeHolder, this.registryKey.substring(1));
        }

        // 直接匹配原版生物群系 ID。
        return checkSingleBiome(biomeHolder, this.registryKey);
    }

    private boolean checkTag(Holder<Biome> holder, String tagLocationStr) {
        ResourceLocation rl = ResourceLocation.tryParse(tagLocationStr);
        if (rl == null) return false;
        return holder.is(TagKey.create(Registries.BIOME, rl));
    }

    private boolean checkSingleBiome(Holder<Biome> holder, String biomeLocationStr) {
        return holder.unwrapKey()
                .map(key -> key.location().toString().equals(biomeLocationStr))
                .orElse(false);
    }




    public static LootItemCondition.Builder matchBiomeRegistry(String registryKey) {
        return () -> new MatchBiomeRegistryCondition(registryKey);
    }

    public static class Serializer implements net.minecraft.world.level.storage.loot.Serializer<MatchBiomeRegistryCondition> {
        @Override
        public void serialize(JsonObject json, MatchBiomeRegistryCondition value, JsonSerializationContext context) {
            json.addProperty("registry_key", value.registryKey);
        }

        @Override
        public MatchBiomeRegistryCondition deserialize(JsonObject json, JsonDeserializationContext context) {
            String registryKey = json.has("registry_key") ? json.get("registry_key").getAsString() : "";
            return new MatchBiomeRegistryCondition(registryKey);
        }
    }
}