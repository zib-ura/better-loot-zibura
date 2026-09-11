package com.zibura.better_loot_zibura.loot.condition;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.zibura.better_loot_zibura.loot.util.LootEvaluationContext;
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

    // 1.21+ 使用 MapCodec 定义数据序列化与反序列化
    // 同时兼容 String 与 List<String>
    private static final Codec<String> COMPAT_KEY_CODEC = Codec.either(
            Codec.STRING,
            Codec.STRING.listOf()
    ).xmap(
            either -> either.map(
                    str -> str,
                    list -> list.isEmpty() ? "" : list.get(0) // 如果是数组，取第 1 个元素
            ),
            str -> com.mojang.datafixers.util.Either.left(str)
    );

    public static final MapCodec<MatchBiomeRegistryCondition> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                    COMPAT_KEY_CODEC.fieldOf("registry_key").forGetter(cond -> cond.registryKey)
            ).apply(instance, MatchBiomeRegistryCondition::new)
    );

    // 1.21+ LootItemConditionType 变为泛型
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

        // 1. 如果本身是 Tag 写法 (如 "#minecraft:is_ocean")
        if (this.registryKey.startsWith("#")) {
            return checkTag(biomeHolder, this.registryKey.substring(1));
        }

        // 2. 尝试从自定义列表解析展开
        List<String> allowedEntries = LootEvaluationContext.resolveStringList(this.registryKey);

        // 3. 上下文中未定义该 key，当作单个群系 ID 处理
        if (allowedEntries.isEmpty()) {
            return checkSingleBiome(biomeHolder, this.registryKey);
        }

        // 4. 遍历解析到的群系列表
        for (String entry : allowedEntries) {
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