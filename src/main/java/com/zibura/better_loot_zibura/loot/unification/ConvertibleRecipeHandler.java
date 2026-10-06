package com.zibura.better_loot_zibura.loot.unification;

import com.zibura.better_loot_zibura.better_loot_zibura;
import com.zibura.better_loot_zibura.config.BetterLootConfig;
import com.zibura.better_loot_zibura.mixin.RecipeManagerAccessor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeManager;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.ShapelessRecipe;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;

import java.util.*;

public final class ConvertibleRecipeHandler {

    private ConvertibleRecipeHandler() {}

    public static void injectConvertibleRecipes(RecipeManager recipeManager) {
        // 适配原生布尔配置判断
        if (!BetterLootConfig.enableRecipes || recipeManager == null) {
            return;
        }

        Map<String, List<String>> convertibleMap = ItemUnificationSolver.CONVERTIBLE_MAP;
        if (convertibleMap.isEmpty()) {
            return;
        }

        List<ShapelessRecipe> newRecipes = new ArrayList<>();

        convertibleMap.forEach((groupKey, itemList) -> {
            if (itemList == null || itemList.isEmpty()) return;

            List<Item> validItems = new ArrayList<>();
            for (String id : itemList) {
                Identifier identifier = Identifier.tryParse(id);
                if (identifier != null && Registries.ITEM.containsId(identifier)) {
                    Item item = Registries.ITEM.get(identifier);
                    if (item != Items.AIR && !validItems.contains(item)) {
                        validItems.add(item);
                    }
                }
            }

            if (validItems.size() < 2) return;

            for (Item sourceItem : validItems) {
                for (Item targetItem : validItems) {
                    if (sourceItem == targetItem) continue;

                    Identifier sourceId = Registries.ITEM.getId(sourceItem);
                    Identifier targetId = Registries.ITEM.getId(targetItem);
                    if (sourceId == null || targetId == null) continue;

                    boolean sourceHasBottle = hasBottleContainer(sourceItem);
                    boolean targetHasBottle = hasBottleContainer(targetItem);

                    DefaultedList<Ingredient> ingredients = DefaultedList.of();
                    ingredients.add(Ingredient.ofItems(sourceItem));

                    if (!sourceHasBottle && targetHasBottle) {
                        ingredients.add(Ingredient.ofItems(Items.GLASS_BOTTLE));
                    }

                    Identifier recipeId = new Identifier(
                            better_loot_zibura.MOD_ID,
                            String.format("convertible/%s_from_%s_%s_to_%s_%s",
                                    groupKey.toLowerCase(Locale.ROOT),
                                    sourceId.getNamespace(), sourceId.getPath(),
                                    targetId.getNamespace(), targetId.getPath())
                    );
                    ItemStack result = new ItemStack(targetItem, 1);

                    // 1.20.1 ShapelessRecipe 构造签名:
                    // (Identifier id, String group, CraftingRecipeCategory category, ItemStack output, DefaultedList<Ingredient> input)
                    ShapelessRecipe recipe = new ShapelessRecipe(
                            recipeId,
                            "convertible_" + groupKey,
                            CraftingRecipeCategory.MISC,
                            result,
                            ingredients
                    );

                    newRecipes.add(recipe);
                }
            }
        });

        if (newRecipes.isEmpty()) {
            return;
        }

        try {
            RecipeManagerAccessor accessor = (RecipeManagerAccessor) recipeManager;

            // 1. 注入按类型分类的配方集合 (recipes)
            Map<RecipeType<?>, Map<Identifier, Recipe<?>>> mutableRecipes = new HashMap<>(accessor.getRecipes());
            Map<Identifier, Recipe<?>> craftingMap = new HashMap<>(
                    mutableRecipes.getOrDefault(RecipeType.CRAFTING, Collections.emptyMap())
            );

            for (ShapelessRecipe recipe : newRecipes) {
                craftingMap.put(recipe.getId(), recipe);
            }
            mutableRecipes.put(RecipeType.CRAFTING, craftingMap);
            accessor.setRecipes(mutableRecipes);

            // 2. 同步注入全局按 ID 查找表，避免按 ID 查询或同步客户端出现缺失
            Map<Identifier, Recipe<?>> mutableRecipesById = new HashMap<>(accessor.getRecipesById());
            for (ShapelessRecipe recipe : newRecipes) {
                mutableRecipesById.put(recipe.getId(), recipe);
            }
            accessor.setRecipesById(mutableRecipesById);

        } catch (Exception ignored) {}
    }

    private static boolean hasBottleContainer(Item item) {
        return item.getRecipeRemainder() == Items.GLASS_BOTTLE;
    }
}