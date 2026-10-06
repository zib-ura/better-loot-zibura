package com.zibura.better_loot_zibura.loot.unification;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.zibura.better_loot_zibura.better_loot_zibura;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public final class ConvertibleRecipeHandler {

    private ConvertibleRecipeHandler() {
    }

    public static void inject(Map<Identifier, JsonElement> recipes) {
        Map<String, List<String>> convertibleMap =
                ItemUnificationSolver.CONVERTIBLE_MAP;

        if (convertibleMap == null || convertibleMap.isEmpty()) {
            return;
        }

        int generated = 0;

        for (Map.Entry<String, List<String>> entry : convertibleMap.entrySet()) {
            String groupKey = entry.getKey();
            List<String> configuredItems = entry.getValue();

            if (configuredItems == null || configuredItems.isEmpty()) {
                continue;
            }

            List<Item> validItems = findValidItems(configuredItems);

            // 至少两个实际存在的物品才有转换意义
            if (validItems.size() < 2) {
                continue;
            }

            for (Item source : validItems) {
                boolean sourceHasBottle = isBottledItem(source);

                for (Item target : validItems) {
                    if (source == target) {
                        continue;
                    }

                    boolean targetHasBottle = isBottledItem(target);

                    /*
                     * 瓶装 -> 瓶装：
                     *
                     * 普通 shapeless recipe 会让 source 自动返还空瓶，
                     * target 本身又已经是瓶装物，因此会复制瓶子。
                     *
                     * 直接跳过。
                     */
                    if (sourceHasBottle && targetHasBottle) {
                        continue;
                    }

                    Identifier sourceId = Registries.ITEM.getId(source);
                    Identifier targetId = Registries.ITEM.getId(target);

                    Identifier recipeId = createRecipeId(
                            groupKey,
                            sourceId,
                            targetId
                    );

                    /*
                     * 如果 datapack / 其他来源已经存在相同 ID，
                     * 不覆盖。
                     */
                    if (recipes.containsKey(recipeId)) {
                        continue;
                    }

                    /*
                     * 普通 -> 瓶装：
                     * 需要额外消耗一个 glass bottle。
                     */
                    boolean needsBottle =
                            !sourceHasBottle && targetHasBottle;

                    recipes.put(
                            recipeId,
                            createRecipeJson(
                                    sourceId,
                                    targetId,
                                    needsBottle
                            )
                    );

                    generated++;
                }
            }
        }

        if (generated > 0) {
            better_loot_zibura.LOGGER.info(
                    "Injected {} convertible recipes",
                    generated
            );
        }
    }

    private static List<Item> findValidItems(List<String> configuredItems) {
        List<Item> result = new ArrayList<>();
        Set<Item> seen = new HashSet<>();

        for (String rawId : configuredItems) {
            if (rawId == null || rawId.isBlank()) {
                continue;
            }

            Identifier id = Identifier.tryParse(rawId);

            /*
             * ID 本身写错属于数据库错误，所以警告。
             */
            if (id == null) {
                better_loot_zibura.LOGGER.warn(
                        "Invalid convertible item identifier: {}",
                        rawId
                );
                continue;
            }

            /*
             * ID 合法但 Registry 不存在：
             *
             * 很可能只是对应 Mod 没安装。
             * 这是正常情况，静默跳过。
             */
            if (!Registries.ITEM.containsId(id)) {
                continue;
            }

            Item item = Registries.ITEM.get(id);

            if (item == Items.AIR) {
                continue;
            }

            if (seen.add(item)) {
                result.add(item);
            }
        }

        return result;
    }

    private static boolean isBottledItem(Item item) {
        return item.getRecipeRemainder() == Items.GLASS_BOTTLE;
    }

    private static JsonObject createRecipeJson(
            Identifier source,
            Identifier target,
            boolean needsBottle
    ) {
        JsonObject recipe = new JsonObject();

        recipe.addProperty(
                "type",
                "minecraft:crafting_shapeless"
        );

        JsonArray ingredients = new JsonArray();

        JsonObject sourceIngredient = new JsonObject();
        sourceIngredient.addProperty(
                "item",
                source.toString()
        );
        ingredients.add(sourceIngredient);

        if (needsBottle) {
            JsonObject bottleIngredient = new JsonObject();
            bottleIngredient.addProperty(
                    "item",
                    "minecraft:glass_bottle"
            );
            ingredients.add(bottleIngredient);
        }

        recipe.add(
                "ingredients",
                ingredients
        );

        JsonObject result = new JsonObject();
        result.addProperty(
                "id",
                target.toString()
        );
        result.addProperty(
                "count",
                1
        );

        recipe.add(
                "result",
                result
        );

        return recipe;
    }

    private static Identifier createRecipeId(
            String groupKey,
            Identifier source,
            Identifier target
    ) {
        String path =
                "convertible/"
                        + sanitizeGroupKey(groupKey)
                        + "/"
                        + source.getNamespace()
                        + "/"
                        + source.getPath()
                        + "/to/"
                        + target.getNamespace()
                        + "/"
                        + target.getPath();

        return Identifier.of(
                better_loot_zibura.MOD_ID,
                path
        );
    }

    private static String sanitizeGroupKey(String value) {
        if (value == null || value.isBlank()) {
            return "unknown";
        }

        return value
                .toLowerCase(Locale.ROOT)
                .replaceAll("[^a-z0-9_.-]", "_");
    }
}