package com.zibura.better_loot_zibura.loot.unification;

import com.zibura.better_loot_zibura.better_loot_zibura;
import com.zibura.better_loot_zibura.config.BetterLootConfig;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.ShapelessRecipe;

import java.util.*;

public final class ConvertibleRecipeHandler {

    private ConvertibleRecipeHandler() {}

    public static void injectConvertibleRecipes(RecipeManager recipeManager) {
        if (!BetterLootConfig.ENABLE_CONVERTIBLE_RECIPES.get() || recipeManager == null) {
            return;
        }

        Map<String, List<String>> convertibleMap = ItemUnificationSolver.CONVERTIBLE_MAP;
        if (convertibleMap.isEmpty()) {
            return;
        }

        List<RecipeHolder<?>> newRecipes = new ArrayList<>();

        convertibleMap.forEach((groupKey, itemList) -> {
            if (itemList == null || itemList.isEmpty()) return;

            List<Item> validItems = new ArrayList<>();
            for (String id : itemList) {
                ResourceLocation rl = ResourceLocation.tryParse(id);
                if (rl != null && BuiltInRegistries.ITEM.containsKey(rl)) {
                    Item item = BuiltInRegistries.ITEM.get(rl);
                    if (item != null && item != BuiltInRegistries.ITEM.get(BuiltInRegistries.ITEM.getDefaultKey()) && !validItems.contains(item)) {
                        validItems.add(item);
                    }
                }
            }

            if (validItems.size() < 2) return;

            for (Item sourceItem : validItems) {
                boolean sourceHasBottle = isBottledItem(sourceItem);

                for (Item targetItem : validItems) {
                    if (sourceItem == targetItem) continue;

                    boolean targetHasBottle = isBottledItem(targetItem);

                    ResourceLocation sourceId = BuiltInRegistries.ITEM.getKey(sourceItem);
                    ResourceLocation targetId = BuiltInRegistries.ITEM.getKey(targetItem);
                    if (sourceId == null || targetId == null) continue;

                    // 构造配方材料列表
                    NonNullList<Ingredient> ingredients = NonNullList.create();
                    ingredients.add(Ingredient.of(sourceItem));

                    // 只有【源物品无瓶子】且【目标物品有瓶子】时，才需要补一个空玻璃瓶，防止凭空刷瓶
                    boolean needsBottle = !sourceHasBottle && targetHasBottle;
                    if (needsBottle) {
                        ingredients.add(Ingredient.of(Items.GLASS_BOTTLE));
                    }

                    String path = String.format("convertible/%s_from_%s_%s_to_%s_%s",
                            groupKey.toLowerCase(Locale.ROOT),
                            sourceId.getNamespace(), sourceId.getPath(),
                            targetId.getNamespace(), targetId.getPath()
                    );
                    ResourceLocation recipeId = ResourceLocation.fromNamespaceAndPath(better_loot_zibura.MOD_ID, path);

                    ItemStack result = new ItemStack(targetItem, 1);

                    ShapelessRecipe recipe = new ShapelessRecipe(
                            "convertible_" + groupKey,
                            CraftingBookCategory.MISC,
                            result,
                            ingredients
                    );

                    newRecipes.add(new RecipeHolder<>(recipeId, recipe));
                }
            }
        });

        if (newRecipes.isEmpty()) {
            return;
        }

        try {
            Map<ResourceLocation, RecipeHolder<?>> combinedRecipes = new HashMap<>();
            for (RecipeHolder<?> existingRecipe : recipeManager.getRecipes()) {
                combinedRecipes.put(existingRecipe.id(), existingRecipe);
            }
            for (RecipeHolder<?> newRecipe : newRecipes) {
                combinedRecipes.put(newRecipe.id(), newRecipe);
            }

            recipeManager.replaceRecipes(combinedRecipes.values());
        } catch (Exception ignored) {}
    }

    /**
     * 判断该物品是否为瓶装/带有玻璃瓶容器
     */
    private static boolean isBottledItem(Item item) {
        // 1. 检查合成/使用残留物是否为玻璃瓶 (标准规范模组)
        ItemStack dummyStack = new ItemStack(item);
        ItemStack remainder = dummyStack.getCraftingRemainingItem();
        if (!remainder.isEmpty() && remainder.is(Items.GLASS_BOTTLE)) {
            return true;
        }

//        // 2. 启发式名称检测（针对一些没规范配置 remainder 的模组物品）
//        ResourceLocation id = BuiltInRegistries.ITEM.getKey(item);
//        if (id != null) {
//            String path = id.getPath().toLowerCase(Locale.ROOT);
//            // 排除明确不是瓶装的（如纯 butter、block、bucket 等）
//            if (path.contains("butter") || path.contains("bucket") || path.contains("block")) {
//                return false;
//            }
//            // 包含 oil 或 bottle 的常态液体多为瓶装
//            if (path.contains("bottle") || path.contains("oil")) {
//                return true;
//            }
//        }

        return false;
    }
}