package com.zibura.better_loot_zibura.loot.condition;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class MatchBiomeRegistryCondition implements LootItemCondition {

    // The Python compiler writes registry_key as a literal biome/tag list.
    // A single string remains accepted for backwards-compatible direct IDs.
    private static final Codec<List<String>> BIOMES_CODEC = Codec.either(
            Codec.STRING, Codec.STRING.listOf()
    ).xmap(
            either -> either.map(List::of, list -> list),
            list -> com.mojang.datafixers.util.Either.right(list)
    );

    public static final MapCodec<MatchBiomeRegistryCondition> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                    BIOMES_CODEC.fieldOf("registry_key").forGetter(cond -> cond.registryKeys)
            ).apply(instance, MatchBiomeRegistryCondition::new)
    );

    // 1.21+ LootItemConditionType 变为泛型
    public static LootItemConditionType TYPE;

    private final List<String> registryKeys;

    public MatchBiomeRegistryCondition(String registryKey) {
        this(List.of(registryKey));
    }

    public MatchBiomeRegistryCondition(List<String> registryKeys) {
        this.registryKeys = List.copyOf(registryKeys);
    }

    @Override
    public LootItemConditionType getType() {
        return TYPE;
    }

    @Override
    public boolean test(LootContext context) {
        Vec3 origin = context.getParamOrNull(LootContextParams.ORIGIN);
        if (origin == null || registryKeys.isEmpty()) {
            return false;
        }

        BlockPos pos = BlockPos.containing(origin);
        Holder<Biome> biomeHolder = context.getLevel().getBiome(pos);

        for (String entry : registryKeys) {
            if (entry.startsWith("#")) {
                if (checkTag(biomeHolder, entry.substring(1))) {
                    return true;
                }
            } else {
                if (checkSingleBiome(biomeHolder, entry)) {
                    return true;
                }
            }
        }

        return false;
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
}