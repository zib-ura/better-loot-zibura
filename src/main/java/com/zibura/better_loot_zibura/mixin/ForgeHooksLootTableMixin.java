package com.zibura.better_loot_zibura.mixin;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.zibura.better_loot_zibura.loot.util.LootBindingLoader;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraftforge.common.ForgeHooks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/** Inject custom datapack loot tables immediately before Forge freezes them. */
@Mixin(value = ForgeHooks.class, remap = false)
public abstract class ForgeHooksLootTableMixin {

    @Redirect(
            method = "loadLootTable",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/storage/loot/LootTable;freeze()V",
                    remap = true
            ),
            remap = false
    )
    private static void betterLoot$beforeFreeze(
            LootTable table,
            Gson gson,
            ResourceLocation name,
            JsonElement data,
            boolean custom
    ) {
        try {
            if (custom) {
                LootBindingLoader.applyBinding(name, table);
            }
        } finally {
            table.freeze();
        }
    }
}
