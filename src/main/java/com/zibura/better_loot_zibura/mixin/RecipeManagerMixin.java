package com.zibura.better_loot_zibura.mixin;

import com.zibura.better_loot_zibura.loot.unification.ConvertibleRecipeHandler;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.SortedMap;

@Mixin(RecipeManager.class)
public abstract class RecipeManagerMixin {

    @Shadow
    @Final
    private HolderLookup.Provider registries;

    @ModifyVariable(
            method = "prepare",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/util/SortedMap;forEach(Ljava/util/function/BiConsumer;)V"
            ),
            ordinal = 0
    )
    private SortedMap<Identifier, Recipe<?>> betterLoot$injectConvertibleRecipes(
            SortedMap<Identifier, Recipe<?>> recipes
    ) {
        ConvertibleRecipeHandler.inject(
                recipes,
                this.registries
        );

        return recipes;
    }
}