package com.zibura.better_loot_zibura.mixin;

import com.zibura.better_loot_zibura.loot.util.AllDataLoader;
//import com.zibura.better_loot_zibura.loot.util.BetterLootResourceScanner;
import net.minecraft.core.LayeredRegistryAccess;
import net.minecraft.server.RegistryLayer;
import net.minecraft.server.ReloadableServerRegistries;
import net.minecraft.server.packs.resources.ResourceManager;

import org.slf4j.Logger;
import com.mojang.logging.LogUtils;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@Mixin(ReloadableServerRegistries.class)
public abstract class ReloadableServerRegistriesMixin {

    private static final Logger LOGGER = LogUtils.getLogger();

    @Inject(method = "reload", at = @At("HEAD"))
    private static void betterLoot$beforeRegistryReload(
            LayeredRegistryAccess<RegistryLayer> registries,
            ResourceManager resourceManager,
            Executor backgroundExecutor,
            CallbackInfoReturnable<
                    CompletableFuture<LayeredRegistryAccess<RegistryLayer>>
                    > cir
    ) {
        LOGGER.info("[BetterLoot] Registry reload started");

        AllDataLoader.loadAllData(resourceManager);
    }


}
