package com.zibura.better_loot_zibura.loot.condition;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.zibura.better_loot_zibura.loot.util.LootEvaluationContext;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.minecraft.world.phys.Vec3;

import java.util.List;
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

//    @Override
//    public boolean test(LootContext context) {
//        Vec3 origin = context.getParamOrNull(LootContextParams.ORIGIN);
//        if (origin == null) {
//            return false;
//        }
//
//        // 从 Registry 获取当前引用的群系列表
//        List<String> allowedBiomes = LootEvaluationContext.resolveStringList(this.registryKey);
//        if (allowedBiomes.isEmpty()) {
//            return false;
//        }
//
//        BlockPos pos = BlockPos.containing(origin);
//        Holder<Biome> biomeHolder = context.getLevel().getBiome(pos);
//
//        return biomeHolder.unwrapKey().map(key -> {
//            String currentBiomeId = key.location().toString();
//            return allowedBiomes.contains(currentBiomeId);
//        }).orElse(false);
//    }


//    @Override
//    public boolean test(LootContext context) {
//        Vec3 origin = context.getParamOrNull(LootContextParams.ORIGIN);
//        if (origin == null) {
//            return false;
//        }
//
//        List<String> allowedEntries = LootEvaluationContext.resolveStringList(this.registryKey);
//        if (allowedEntries.isEmpty()) {
//            return false;
//        }
//
//        BlockPos pos = BlockPos.containing(origin);
//        Holder<Biome> biomeHolder = context.getLevel().getBiome(pos);
//
//        // 逐项匹配：支持普通 ID ("minecraft:plains") 与 Tag ("#minecraft:is_forest")
//        for (String entry : allowedEntries) {
//            if (entry.startsWith("#")) {
//                TagKey<Biome> tagKey = TagKey.create(
//                        Registries.BIOME,
//                        ResourceLocation.parse(entry.substring(1))
//                );
//                if (biomeHolder.is(tagKey)) {
//                    return true;
//                }
//            } else {
//                boolean matches = biomeHolder.unwrapKey()
//                        .map(key -> key.location().toString().equals(entry))
//                        .orElse(false);
//                if (matches) {
//                    return true;
//                }
//            }
//        }
//
//        return false;
//    }

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

        // 2. 尝试从自定义列表 (LootEvaluationContext) 解析展开
        List<String> allowedEntries = LootEvaluationContext.resolveStringList(this.registryKey);

        // 3. 如果在上下文里找不到该 key 对应的数组，当成单个群系 ID 直接匹配 (如 "minecraft:desert")
        if (allowedEntries.isEmpty()) {
            return checkSingleBiome(biomeHolder, this.registryKey);
        }

        // 4. 如果找到了数组，遍历数组进行匹配（数组内元素同样支持 #tag 和 普通ID）
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