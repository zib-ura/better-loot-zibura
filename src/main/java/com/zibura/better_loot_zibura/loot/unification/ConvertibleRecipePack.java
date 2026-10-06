package com.zibura.better_loot_zibura.loot.unification;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.zibura.better_loot_zibura.config.BetterLootConfig;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.PackLocationInfo;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.metadata.MetadataSectionType;
import net.minecraft.server.packs.metadata.pack.PackFormat;
import net.minecraft.server.packs.metadata.pack.PackMetadataSection;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.server.packs.resources.IoSupplier;
import net.minecraft.util.InclusiveRange;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Items;

import org.jspecify.annotations.Nullable;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class ConvertibleRecipePack implements PackResources {

    private static final String NAMESPACE = "better_loot_zibura";

    private final PackLocationInfo location = new PackLocationInfo(
            "better_loot_zibura:convertible_recipes",
            Component.literal("Better Loot Convertible Recipes"),
            PackSource.BUILT_IN,
            Optional.empty()
    );

    @Override
    public PackLocationInfo location() {
        return location;
    }

    @Override
    public @Nullable IoSupplier<InputStream> getRootResource(String... path) {
        return null;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> @Nullable T getMetadataSection(
            MetadataSectionType<T> metadataSectionType
    ) throws IOException {

        if (metadataSectionType == PackMetadataSection.SERVER_TYPE) {
            PackMetadataSection metadata = new PackMetadataSection(
                    Component.literal("Better Loot Convertible Recipes"),
                    new InclusiveRange<>(
                            PackFormat.of(Integer.MAX_VALUE)
                    )
            );

            return (T) metadata;
        }

        return null;
    }

    @Override
    public @Nullable IoSupplier<InputStream> getResource(
            PackType packType,
            Identifier identifier
    ) {
        if (packType != PackType.SERVER_DATA) {
            return null;
        }

        byte[] data = buildRecipeResources().get(identifier);

        if (data == null) {
            return null;
        }

        return () -> new ByteArrayInputStream(data);
    }

    @Override
    public void listResources(
            PackType packType,
            String namespace,
            String path,
            ResourceOutput output
    ) {
        if (packType != PackType.SERVER_DATA) {
            return;
        }

        if (!NAMESPACE.equals(namespace)) {
            return;
        }

        Map<Identifier, byte[]> recipes = buildRecipeResources();

        recipes.forEach((id, data) -> {
            if (id.getPath().startsWith(path)) {
                output.accept(
                        id,
                        () -> new ByteArrayInputStream(data)
                );
            }
        });
    }

    @Override
    public Set<String> getNamespaces(PackType packType) {
        if (packType == PackType.SERVER_DATA) {
            return Set.of(NAMESPACE);
        }

        return Set.of();
    }

    @Override
    public void close() {
    }

    /**
     * 根据 CONVERTIBLE_MAP 动态构建全部转换配方资源。
     * Pack 中的资源路径：
     * better_loot_zibura:
     * recipe/convertible/<group>_from_<source>_to_<target>.json
     * Minecraft 最终得到的配方 ID：
     * better_loot_zibura:
     * convertible/<group>_from_<source>_to_<target>
     */
    private static Map<Identifier, byte[]> buildRecipeResources() {
        Map<Identifier, byte[]> recipes = new LinkedHashMap<>();

        if (!BetterLootConfig.ENABLE_CONVERTIBLE_RECIPES.get()) {
            return recipes;
        }

        Map<String, List<String>> convertibleMap =
                ItemUnificationSolver.CONVERTIBLE_MAP;

        if (convertibleMap.isEmpty()) {
            return recipes;
        }

        convertibleMap.forEach((groupKey, itemList) -> {
            if (itemList == null || itemList.isEmpty()) {
                return;
            }

            /*
             * 先把配置中的字符串 ID 转换成当前游戏中
             * 实际存在的 Item。
             *
             * 不存在的模组物品直接忽略。
             */
            List<Item> validItems = new ArrayList<>();

            Item defaultItem = BuiltInRegistries.ITEM.getValue(
                    BuiltInRegistries.ITEM.getDefaultKey()
            );

            for (String itemId : itemList) {
                Identifier id = Identifier.tryParse(itemId);

                if (id == null) {
                    continue;
                }

                if (!BuiltInRegistries.ITEM.containsKey(id)) {
                    continue;
                }

                Item item = BuiltInRegistries.ITEM.getValue(id);

                if (item == null || item == defaultItem) {
                    continue;
                }

                if (!validItems.contains(item)) {
                    validItems.add(item);
                }
            }

            /*
             * 至少需要两个实际存在、且不同的物品。
             *
             * 只有一个有效物品的组不会生成任何转换配方。
             */
            if (validItems.size() < 2) {
                return;
            }

            /*
             * 完整有向转换：
             *
             * A -> B
             * A -> C
             * B -> A
             * B -> C
             * C -> A
             * C -> B
             */
            for (Item sourceItem : validItems) {
                boolean sourceHasBottle =
                        isBottledItem(sourceItem);

                for (Item targetItem : validItems) {
                    if (sourceItem == targetItem) {
                        continue;
                    }

                    boolean targetHasBottle =
                            isBottledItem(targetItem);

                    // 瓶装 -> 瓶装使用普通 shapeless recipe
                    // 会返还 source 的玻璃瓶，导致复制空瓶。
                    // 因此不生成这种转换。
                    if (sourceHasBottle && targetHasBottle) {
                        continue;
                    }

                    boolean needsBottle =
                            !sourceHasBottle && targetHasBottle;

                    Identifier sourceId =
                            BuiltInRegistries.ITEM.getKey(sourceItem);

                    Identifier targetId =
                            BuiltInRegistries.ITEM.getKey(targetItem);

                    if (sourceId == null || targetId == null) {
                        continue;
                    }

                    String recipePath = String.format(
                            Locale.ROOT,
                            "recipe/convertible/%s_from_%s_%s_to_%s_%s.json",
                            groupKey.toLowerCase(Locale.ROOT),
                            sourceId.getNamespace(),
                            sourceId.getPath(),
                            targetId.getNamespace(),
                            targetId.getPath()
                    );

                    Identifier resourceId =
                            Identifier.fromNamespaceAndPath(
                                    NAMESPACE,
                                    recipePath
                            );

                    byte[] json = createRecipeJson(
                            groupKey,
                            sourceId,
                            targetId,
                            needsBottle
                    );

                    recipes.put(resourceId, json);
                }
            }
        });

        return recipes;
    }

    /**
     * 生成 Minecraft 26.3 crafting_shapeless JSON。
     */
    private static byte[] createRecipeJson(
            String groupKey,
            Identifier sourceId,
            Identifier targetId,
            boolean needsBottle
    ) {
        JsonObject root = new JsonObject();

        root.addProperty(
                "type",
                "minecraft:crafting_shapeless"
        );

        root.addProperty(
                "group",
                "convertible_" + groupKey
        );

        JsonArray ingredients = new JsonArray();

        ingredients.add(sourceId.toString());

        if (needsBottle) {
            ingredients.add("minecraft:glass_bottle");
        }

        root.add(
                "ingredients",
                ingredients
        );

        JsonObject result = new JsonObject();

        result.addProperty(
                "id",
                targetId.toString()
        );

        root.add(
                "result",
                result
        );

        return root.toString()
                .getBytes(StandardCharsets.UTF_8);
    }

    /**
     * 判断该物品在合成后是否返还玻璃瓶。
     * Minecraft 26.3:
     * Item#getCraftingRemainder()
     *     -> @Nullable ItemStackTemplate
     * ItemStackTemplate#item()
     *     -> Holder<Item>
     * 如果 crafting remainder 是玻璃瓶，
     * 就把该物品视为瓶装物品。
     */
    @SuppressWarnings("deprecation")
    private static boolean isBottledItem(Item item) {
        ItemStackTemplate remainder =
                item.getCraftingRemainder();

        if (remainder == null) {
            return false;
        }

        return remainder.item().is(
                Items.GLASS_BOTTLE.builtInRegistryHolder()
        );
    }
}