
package com.zibura.better_loot_zibura.mixin;

import com.mojang.logging.LogUtils;
import com.zibura.better_loot_zibura.loot.util.AllDataLoader;
import net.minecraft.commands.Commands;
import net.minecraft.core.RegistryAccess;
import net.minecraft.server.ReloadableServerResources;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.flag.FeatureFlagSet;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@Mixin(ReloadableServerResources.class)
public abstract class ReloadableServerRegistriesMixin {

    private static final Logger LOGGER = LogUtils.getLogger();

    @Inject(method = "loadResources", at = @At("HEAD"))
    private static void betterLoot$beforeResourcesLoad(
            ResourceManager resourceManager,
            RegistryAccess.Frozen registries,
            FeatureFlagSet enabledFeatures,
            Commands.CommandSelection commandSelection,
            int functionCompilationLevel,
            Executor backgroundExecutor,
            Executor gameExecutor,
            CallbackInfoReturnable<CompletableFuture<ReloadableServerResources>> cir
    ) {
        LOGGER.info("[BetterLoot] Server resources load started");

        AllDataLoader.setRegistryAccess(registries);
        AllDataLoader.loadAllData(resourceManager);
    }
}
