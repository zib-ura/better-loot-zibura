package com.zibura.better_loot_zibura.mixin;

import com.google.gson.JsonElement;
import com.zibura.better_loot_zibura.config.BetterLootConfig;
import com.zibura.better_loot_zibura.loot.unification.ConvertibleRecipeHandler;
import net.minecraft.recipe.RecipeManager;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.LinkedHashMap;
import java.util.Map;

@Mixin(RecipeManager.class)
public abstract class RecipeManagerMixin {

    /**
     * Supply the vanilla recipe decoder with the original JSON recipes plus
     * our generated conversions.  JsonDataLoader may supply an unmodifiable
     * map, so never mutate its argument directly.
     *
     * This hook runs on startup and on every server resource reload.
     */
    @ModifyVariable(
            method = "apply(Ljava/util/Map;Lnet/minecraft/resource/ResourceManager;Lnet/minecraft/util/profiler/Profiler;)V",
            at = @At("HEAD"),
            argsOnly = true,
            ordinal = 0,
            require = 1
    )
    private Map<Identifier, JsonElement> betterLootZibura$addConvertibleRecipes(
            Map<Identifier, JsonElement> loadedRecipes
    ) {
        if (!BetterLootConfig.enableRecipes || loadedRecipes == null) {
            return loadedRecipes;
        }

        // Keep the original recipe entries and their relative order intact.
        Map<Identifier, JsonElement> combined = new LinkedHashMap<>(loadedRecipes);
        ConvertibleRecipeHandler.inject(combined);
        return combined;
    }
}
