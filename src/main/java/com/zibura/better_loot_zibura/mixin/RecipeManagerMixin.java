package com.zibura.better_loot_zibura.mixin;

import com.zibura.better_loot_zibura.config.BetterLootConfig;
import com.zibura.better_loot_zibura.loot.unification.ConvertibleRecipeHandler;
import net.minecraft.server.ReloadableServerRegistries;
import net.minecraft.server.packs.resources.ResourceManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

/**
 * Minecraft 26.3: recipes are reloadable dynamic-registry entries.
 * The former RecipeManager#prepare and its registries field no longer exist.
 * Keep the custom Mixin class name, but inject generated recipe resources
 * BEFORE RegistryDataLoader starts decoding the RECIPE registry.
 */
@Mixin(ReloadableServerRegistries.class)
public abstract class RecipeManagerMixin {
    @ModifyArg(
            method = "reload",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/resources/RegistryDataLoader;load(Lnet/minecraft/server/packs/resources/ResourceManager;Ljava/util/List;Ljava/util/List;Ljava/util/concurrent/Executor;)Ljava/util/concurrent/CompletableFuture;"
            ),
            index = 0,
            require = 1
    )
    private static ResourceManager betterLoot$injectConvertibleRecipes(ResourceManager resources) {
        if (!BetterLootConfig.enableRecipes) return resources;
        // ReloadableServerRegistriesMixin#beforeReload has already compiled
        // the active unification config at HEAD.
        return ConvertibleRecipeHandler.addGeneratedRecipes(resources);
    }
}
