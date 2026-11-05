package com.zibura.better_loot_zibura.mixin;

import com.zibura.better_loot_zibura.datapack.DatapackLoader;
import net.minecraft.server.packs.repository.BuiltInPackSource;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.ServerPacksSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Consumer;

/**
 * 1211's VanillaResourcePackProvider.register(TAIL) equivalent on 26.3.
 * Only register SERVER_DATA packs, never the client asset pack provider.
 */
@Mixin(BuiltInPackSource.class)
public abstract class VanillaResourcePackProviderMixin {

    @Inject(method = "loadPacks", at = @At("TAIL"), require = 1)
    private void betterLootZibura$registerBundledDataPacks(Consumer<Pack> packAdder, CallbackInfo ci) {
        if ((Object) this instanceof ServerPacksSource) {
            DatapackLoader.registerPacks(packAdder);
        }
    }
}
