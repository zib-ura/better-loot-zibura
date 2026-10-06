package com.zibura.better_loot_zibura.loot.condition;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.zibura.better_loot_zibura.event.CommonEvents;
import com.zibura.better_loot_zibura.loot.util.LootEvaluationContext;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.condition.LootConditionType;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.biome.Biome;

import java.util.List;

public class MatchBiomeRegistryCondition implements LootCondition {

    /*
     * 兼容：
     *
     * "registry_key": "minecraft:plains"
     *
     * 以及旧数据：
     *
     * "registry_key": ["minecraft:plains"]
     */
    private static final Codec<String> COMPAT_KEY_CODEC = Codec.either(
            Codec.STRING,
            Codec.STRING.listOf()
    ).xmap(
            either -> either.map(
                    str -> str,
                    list -> list.isEmpty() ? "" : list.get(0)
            ),
            Either::left
    );

    public static final MapCodec<MatchBiomeRegistryCondition> CODEC =
            RecordCodecBuilder.mapCodec(instance -> instance.group(
                    COMPAT_KEY_CODEC
                            .fieldOf("registry_key")
                            .forGetter(condition -> condition.registryKey)
            ).apply(instance, MatchBiomeRegistryCondition::new));

    private final String registryKey;

    public MatchBiomeRegistryCondition(String registryKey) {
        this.registryKey = registryKey;
    }

    @Override
    public LootConditionType getType() {
        return CommonEvents.MATCH_BIOME_REGISTRY;
    }

    @Override
    public boolean test(LootContext context) {
        Vec3d origin = context.get(LootContextParameters.ORIGIN);

        if (origin == null || registryKey == null || registryKey.isEmpty()) {
            return false;
        }

        BlockPos pos = BlockPos.ofFloored(origin);

        RegistryEntry<Biome> biomeEntry =
                context.getWorld().getBiome(pos);

        // 1. 直接写 biome tag
        // 例如：
        // "#minecraft:is_ocean"
        if (registryKey.startsWith("#")) {
            return checkTag(
                    biomeEntry,
                    registryKey.substring(1)
            );
        }

        // 2. 尝试把 registryKey 当作自定义列表名称解析
        List<String> allowedEntries =
                LootEvaluationContext.resolveStringList(registryKey);

        // 3. 没有对应的自定义列表：
        // 把 registryKey 本身当作 biome ID
        if (allowedEntries.isEmpty()) {
            return checkSingleBiome(
                    biomeEntry,
                    registryKey
            );
        }

        // 4. 检查列表里的 biome / tag
        for (String entry : allowedEntries) {

            if (entry == null || entry.isEmpty()) {
                continue;
            }

            if (entry.startsWith("#")) {
                if (checkTag(
                        biomeEntry,
                        entry.substring(1)
                )) {
                    return true;
                }
            } else {
                if (checkSingleBiome(
                        biomeEntry,
                        entry
                )) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean checkTag(
            RegistryEntry<Biome> biomeEntry,
            String tagLocation
    ) {
        Identifier id = Identifier.tryParse(tagLocation);

        if (id == null) {
            return false;
        }

        TagKey<Biome> tagKey =
                TagKey.of(
                        RegistryKeys.BIOME,
                        id
                );

        return biomeEntry.isIn(tagKey);
    }

    private boolean checkSingleBiome(
            RegistryEntry<Biome> biomeEntry,
            String biomeLocation
    ) {
        Identifier id = Identifier.tryParse(biomeLocation);

        if (id == null) {
            return false;
        }

        return biomeEntry.getKey()
                .map(RegistryKey::getValue)
                .map(id::equals)
                .orElse(false);
    }

    public static LootCondition.Builder matchBiomeRegistry(
            String registryKey
    ) {
        return () -> new MatchBiomeRegistryCondition(registryKey);
    }
}