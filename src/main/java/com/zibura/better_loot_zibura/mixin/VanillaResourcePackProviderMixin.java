package com.zibura.better_loot_zibura.mixin;

import com.zibura.better_loot_zibura.datapack.DatapackLoader;
import net.minecraft.resource.ResourcePackProfile;
import net.minecraft.resource.VanillaDataPackProvider;
import net.minecraft.resource.VanillaResourcePackProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Consumer;

/** Attach to the existing vanilla SERVER_DATA pack provider, not the asset provider. */
@Mixin(VanillaResourcePackProvider.class)
public abstract class VanillaResourcePackProviderMixin {

    @Inject(method = "register", at = @At("TAIL"))
    private void betterLootZibura$registerBundledDataPacks(
            Consumer<ResourcePackProfile> profileAdder, CallbackInfo ci) {
        if ((Object) this instanceof VanillaDataPackProvider) {
            DatapackLoader.registerPacks(profileAdder);
        }
    }
}
