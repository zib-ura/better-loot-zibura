package com.zibura.better_loot_zibura.mixin;

import com.zibura.better_loot_zibura.loot.util.AllDataLoader;
import net.minecraft.registry.CombinedDynamicRegistries;
import net.minecraft.registry.ReloadableRegistries;
import net.minecraft.registry.ServerDynamicRegistryType;
import net.minecraft.resource.ResourceManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

/**
 * Called before vanilla loot registries are prepared, both at initial load
 * and on /reload. This avoids stale bindings in LootTableEvents.MODIFY.
 */
@Mixin(ReloadableRegistries.class)
public abstract class ReloadableRegistriesMixin {

    @Inject(method = "reload", at = @At("HEAD"))
    private static void betterLootZibura$beforeLootRegistriesReload(
            CombinedDynamicRegistries<ServerDynamicRegistryType> registries,
            ResourceManager resourceManager,
            Executor prepareExecutor,
            CallbackInfoReturnable<CompletableFuture<CombinedDynamicRegistries<ServerDynamicRegistryType>>> cir
    ) {
        AllDataLoader.loadAllData(resourceManager);
    }
}
