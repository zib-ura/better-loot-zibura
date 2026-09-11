package com.zibura.better_loot_zibura.loot.unification;

import com.zibura.better_loot_zibura.better_loot_zibura;
import com.zibura.better_loot_zibura.config.BetterLootConfig;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.ShapelessRecipe;
import net.minecraftforge.registries.ForgeRegistries;

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

        List<Recipe<?>> newRecipes = new ArrayList<>();

        convertibleMap.forEach((groupKey, itemList) -> {
            if (itemList == null || itemList.isEmpty()) return;

            List<Item> validItems = new ArrayList<>();
            for (String id : itemList) {
                ResourceLocation rl = ResourceLocation.tryParse(id);
                // 使用 ForgeRegistries.ITEMS
                if (rl != null && ForgeRegistries.ITEMS.containsKey(rl)) {
                    Item item = ForgeRegistries.ITEMS.getValue(rl);
                    if (item != null && item != Items.AIR && !validItems.contains(item)) {
                        validItems.add(item);
                    }
                }
            }

            if (validItems.size() < 2) return;

            for (Item sourceItem : validItems) {
                for (Item targetItem : validItems) {
                    if (sourceItem == targetItem) continue;

                    ResourceLocation sourceId = ForgeRegistries.ITEMS.getKey(sourceItem);
                    ResourceLocation targetId = ForgeRegistries.ITEMS.getKey(targetItem);
                    if (sourceId == null || targetId == null) continue;

                    boolean sourceHasBottle = hasBottleContainer(sourceItem);
                    boolean targetHasBottle = hasBottleContainer(targetItem);

                    NonNullList<Ingredient> ingredients = NonNullList.create();
                    ingredients.add(Ingredient.of(sourceItem));

                    // 无瓶 -> 有瓶：额外消耗一个玻璃瓶防刷
                    if (!sourceHasBottle && targetHasBottle) {
                        ingredients.add(Ingredient.of(Items.GLASS_BOTTLE));
                    }

                    // 1.20.1 中 ResourceLocation 构造函数为 new ResourceLocation(namespace, path)
                    ResourceLocation recipeId = new ResourceLocation(
                            better_loot_zibura.MOD_ID,
                            String.format("convertible/%s_from_%s_%s_to_%s_%s",
                                    groupKey.toLowerCase(Locale.ROOT),
                                    sourceId.getNamespace(), sourceId.getPath(),
                                    targetId.getNamespace(), targetId.getPath())
                    );
                    ItemStack result = new ItemStack(targetItem, 1);

                    // 1.20.1 ShapelessRecipe
                    ShapelessRecipe recipe = new ShapelessRecipe(
                            recipeId,
                            "convertible_" + groupKey,
                            CraftingBookCategory.MISC,
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
            Map<ResourceLocation, Recipe<?>> combinedRecipes = new HashMap<>();
            for (Recipe<?> existingRecipe : recipeManager.getRecipes()) {
                combinedRecipes.put(existingRecipe.getId(), existingRecipe);
            }
            for (Recipe<?> newRecipe : newRecipes) {
                combinedRecipes.put(newRecipe.getId(), newRecipe);
            }

            recipeManager.replaceRecipes(combinedRecipes.values());
        } catch (Exception ignored) {}
    }

    private static boolean hasBottleContainer(Item item) {
        ItemStack remaining = item.getCraftingRemainingItem(new ItemStack(item));
        if (!remaining.isEmpty() && remaining.is(Items.GLASS_BOTTLE)) {
            return true;
        }

        return false;
    }
}