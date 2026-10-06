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

    /**
     * 在数据包解析并生成原版/数据包配方之后，立即注入自定义的可转换配方
     */
    @Inject(
            method = "apply(Ljava/util/Map;Lnet/minecraft/resource/ResourceManager;Lnet/minecraft/util/profiler/Profiler;)V",
            at = @At("TAIL")
    )
    private void betterLoot$onApplyRecipes(Map<Identifier, JsonElement> map, ResourceManager resourceManager, Profiler profiler, CallbackInfo ci) {
        RecipeManager manager = (RecipeManager) (Object) this;
        ConvertibleRecipeHandler.injectConvertibleRecipes(manager);
    }
}