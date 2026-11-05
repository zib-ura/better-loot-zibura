package com.zibura.better_loot_zibura.loot.condition;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionTypes;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import com.mojang.datafixers.util.Either;

public class MatchBiomeRegistryCondition implements LootItemCondition {

    @Override
    public MapCodec<? extends LootItemCondition> codec() {
        return CODEC;
    }

    // The 1211 linker resolves registry_key to a string array (IDs and tags).
    // Single strings remain supported for direct datapack definitions.
    private static final Codec<List<String>> COMPAT_KEY_CODEC = Codec.either(
            Codec.STRING, Codec.STRING.listOf()
    ).xmap(either -> either.map(List::of, list -> list), Either::right);

    public static final MapCodec<MatchBiomeRegistryCondition> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                    COMPAT_KEY_CODEC.fieldOf("registry_key").forGetter(cond -> cond.registryKeys)
            ).apply(instance, MatchBiomeRegistryCondition::new)
    );

    private final List<String> registryKeys;

    public MatchBiomeRegistryCondition(String registryKey) {
        this(List.of(registryKey));
    }

    public MatchBiomeRegistryCondition(List<String> registryKeys) {
        this.registryKeys = List.copyOf(registryKeys);
    }

    @Override
    public boolean test(LootContext context) {
        Vec3 origin = context.getOptional(LootContextParams.ORIGIN);
        if (origin == null || registryKeys.isEmpty()) return false;

        BlockPos pos = BlockPos.containing(origin);
        Holder<Biome> biomeHolder = context.getLevel().getBiome(pos);

        for (String registryKey : registryKeys) {
            if (registryKey == null || registryKey.isEmpty()) continue;
            if (registryKey.startsWith("#")) {
                if (checkTag(biomeHolder, registryKey.substring(1))) return true;
            } else if (checkSingleBiome(biomeHolder, registryKey)) {
                return true;
            }
        }
        return false;
    }

    private boolean checkTag(Holder<Biome> holder, String tagLocationStr) {
        Identifier rl = Identifier.tryParse(tagLocationStr);
        if (rl == null) return false;
        return holder.is(TagKey.create(Registries.BIOME, rl));
    }

    private boolean checkSingleBiome(Holder<Biome> holder, String biomeLocationStr) {
        return holder.unwrapKey()
                .map(key -> key.identifier().toString().equals(biomeLocationStr))
                .orElse(false);
    }

    public static LootItemCondition.Builder matchBiomeRegistry(String registryKey) {
        return () -> new MatchBiomeRegistryCondition(registryKey);
    }
}