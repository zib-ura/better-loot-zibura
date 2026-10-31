package com.zibura.better_loot_zibura.loot.condition;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.zibura.better_loot_zibura.event.CommonEvents;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.condition.LootConditionType;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
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
     * 兼容直接 ID/Tag 和编译后的字符串列表：
     *
     * "registry_key": "minecraft:plains"
     *
     * 以及旧数据：
     *
     * "registry_key": ["minecraft:plains"]
     */
    private static final Codec<List<String>> COMPAT_KEY_CODEC = Codec.either(
            Codec.STRING,
            Codec.STRING.listOf()
    ).xmap(
            either -> either.map(List::of, list -> list),
            Either::right
    );

    public static final MapCodec<MatchBiomeRegistryCondition> CODEC =
            RecordCodecBuilder.mapCodec(instance -> instance.group(
                    COMPAT_KEY_CODEC
                            .fieldOf("registry_key")
                            .forGetter(condition -> condition.registryKeys)
            ).apply(instance, MatchBiomeRegistryCondition::new));

    private final List<String> registryKeys;

    public MatchBiomeRegistryCondition(String registryKey) {
        this(List.of(registryKey));
    }

    public MatchBiomeRegistryCondition(List<String> registryKeys) {
        this.registryKeys = List.copyOf(registryKeys);
    }

    @Override
    public LootConditionType getType() {
        return CommonEvents.MATCH_BIOME_REGISTRY;
    }

    @Override
    public boolean test(LootContext context) {
        Vec3d origin = context.get(LootContextParameters.ORIGIN);
        if (origin == null || registryKeys.isEmpty()) return false;

        BlockPos pos = BlockPos.ofFloored(origin);
        RegistryEntry<Biome> biomeEntry = context.getWorld().getBiome(pos);

        for (String registryKey : registryKeys) {
            if (registryKey == null || registryKey.isEmpty()) continue;
            if (registryKey.startsWith("#")) {
                if (checkTag(biomeEntry, registryKey.substring(1))) return true;
            } else if (checkSingleBiome(biomeEntry, registryKey)) {
                return true;
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
        // Forge compares the registered biome ID string verbatim.
        // Parsing with Identifier.tryParse() here would also accept unqualified
        // IDs such as "plains", which the original Forge condition did not match.
        return biomeEntry.getKey()
                .map(key -> key.getValue().toString().equals(biomeLocation))
                .orElse(false);
    }

    public static LootCondition.Builder matchBiomeRegistry(
            String registryKey
    ) {
        return () -> new MatchBiomeRegistryCondition(registryKey);
    }
}