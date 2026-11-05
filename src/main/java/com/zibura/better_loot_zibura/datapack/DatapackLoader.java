package com.zibura.better_loot_zibura.datapack;

import com.zibura.better_loot_zibura.better_loot_zibura;
import com.zibura.better_loot_zibura.config.BetterLootConfig;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackLocationInfo;
import net.minecraft.server.packs.PackSelectionConfig;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.PathPackResources;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * Register bundled SERVER_DATA packs using the 1211 provider-hook semantics:
 * discover packs/<modid>/ when the pack repository is refreshed, and install
 * them as required, TOP-priority, built-in packs (not optional Fabric packs).
 */
public final class DatapackLoader {
    private DatapackLoader() {}

    /** Called by VanillaResourcePackProviderMixin on BuiltInPackSource.loadPacks for ServerPacksSource only. */
    public static void registerPacks(Consumer<Pack> packAdder) {
        if (!BetterLootConfig.enableLootAdditionsAndPacks) return;

        Optional<ModContainer> container = FabricLoader.getInstance()
                .getModContainer(better_loot_zibura.MOD_ID);
        if (container.isEmpty()) return;

        Optional<Path> packsPath = container.get().findPath("packs");
        if (packsPath.isEmpty() || !Files.isDirectory(packsPath.get())) return;

        try (var folders = Files.list(packsPath.get())) {
            folders.filter(Files::isDirectory).sorted().forEach(root -> {
                String modId = root.getFileName().toString();
                if (("minecraft".equals(modId) || FabricLoader.getInstance().isModLoaded(modId))
                        && Files.isRegularFile(root.resolve("pack.mcmeta"))) {
                    registerPack(packAdder, modId, root);
                }
            });
        } catch (IOException ex) {
            throw new UncheckedIOException("Failed to scan bundled datapacks", ex);
        }
    }

    private static void registerPack(Consumer<Pack> packAdder, String packName, Path root) {
        String id = better_loot_zibura.MOD_ID + "/" + packName;
        PackLocationInfo info = new PackLocationInfo(
                id,
                Component.literal("Better Loot Zibura - " + packName),
                PackSource.BUILT_IN,
                Optional.empty()
        );
        PackSelectionConfig selection = new PackSelectionConfig(
                true, // required (same as 1211)
                Pack.Position.TOP,
                false // do not fix the position beyond the required/TOP semantics
        );
        Pack pack = Pack.readMetaAndCreate(
                info,
                new PathPackResources.PathResourcesSupplier(root),
                PackType.SERVER_DATA,
                selection
        );
        if (pack != null) {
            packAdder.accept(pack);
            better_loot_zibura.LOGGER.info("Registered builtin datapack {} at TOP (required)", id);
        } else {
            better_loot_zibura.LOGGER.warn("Skipping invalid bundled datapack {} at {}", id, root);
        }
    }
}
