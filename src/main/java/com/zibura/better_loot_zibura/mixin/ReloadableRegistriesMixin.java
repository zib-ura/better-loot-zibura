package com.zibura.better_loot_zibura.mixin;

import com.zibura.better_loot_zibura.loot.util.AllDataLoader;
import net.minecraft.core.LayeredRegistryAccess;
import net.minecraft.core.Registry;
import net.minecraft.server.RegistryLayer;
import net.minecraft.server.ReloadableServerRegistries;
import net.minecraft.server.packs.resources.ResourceManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

/**
 * Minecraft 26.3 (official mappings).
 * Compile BetterLoot's data before reloadable loot registries start decoding.
 * Runs both during server startup and on /reload.
 */
@Mixin(ReloadableServerRegistries.class)
public abstract class ReloadableRegistriesMixin {
    @Inject(method = "reload", at = @At("HEAD"), require = 1)
    private static void betterLoot$beforeReload(
            LayeredRegistryAccess<RegistryLayer> context,
            List<Registry.PendingTags<?>> updatedContextTags,
            ResourceManager resourceManager,
            Executor executor,
            CallbackInfoReturnable<CompletableFuture<ReloadableServerRegistries.LoadResult>> cir
    ) {
        AllDataLoader.loadAllData(resourceManager);
    }
}
