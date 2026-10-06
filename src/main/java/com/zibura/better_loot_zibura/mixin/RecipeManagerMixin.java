package com.zibura.better_loot_zibura.mixin;

import com.google.gson.JsonElement;
import com.zibura.better_loot_zibura.loot.unification.ConvertibleRecipeHandler;
import net.minecraft.recipe.RecipeManager;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(RecipeManager.class)
public abstract class RecipeManagerMixin {

    @Inject(
            method = "apply",
            at = @At("HEAD")
    )
    private void betterLootZibura$injectConvertibleRecipes(
            Map<Identifier, JsonElement> recipes,
            ResourceManager resourceManager,
            Profiler profiler,
            CallbackInfo ci
    ) {
        ConvertibleRecipeHandler.inject(recipes);
    }
}