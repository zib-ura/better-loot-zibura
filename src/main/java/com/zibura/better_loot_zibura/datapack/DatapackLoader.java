package com.zibura.better_loot_zibura.datapack;

import com.zibura.better_loot_zibura.better_loot_zibura;
import com.zibura.better_loot_zibura.config.BetterLootConfig;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.minecraft.resource.DirectoryResourcePack;
import net.minecraft.resource.ResourcePackInfo;
import net.minecraft.resource.ResourcePackPosition;
import net.minecraft.resource.ResourcePackProfile;
import net.minecraft.resource.ResourcePackSource;
import net.minecraft.resource.ResourceType;
import net.minecraft.text.Text;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * Port of Forge AddPackFindersEvent registration.
 * Native Yarn TOP insertion, required=true, source=BUILTIN.
 * Resource paths inside the mod are deliberately unchanged: packs/<modid>/.
 */
public final class DatapackLoader {
    private DatapackLoader() {}

    /** Invoked only for VanillaDataPackProvider, never client asset packs. */
    public static void registerPacks(Consumer<ResourcePackProfile> profileAdder) {
        if (!BetterLootConfig.enableLootAdditionsAndPacks) return;

        Optional<ModContainer> container = FabricLoader.getInstance()
                .getModContainer(better_loot_zibura.MOD_ID);
        if (container.isEmpty()) return;

        Optional<Path> packsPath = container.get().findPath("packs");
        if (packsPath.isEmpty() || !Files.isDirectory(packsPath.get())) return;

        try (var folders = Files.list(packsPath.get())) {
            folders.filter(Files::isDirectory).forEach(root -> {
                String modId = root.getFileName().toString();
                if (("minecraft".equals(modId) || FabricLoader.getInstance().isModLoaded(modId))
                        && Files.isRegularFile(root.resolve("pack.mcmeta"))) {
                    registerPack(profileAdder, modId, root);
                }
            });
        } catch (IOException ex) {
            throw new UncheckedIOException("Failed to scan bundled datapacks", ex);
        }
    }

    private static void registerPack(Consumer<ResourcePackProfile> profileAdder,
                                     String packName, Path root) {
        String id = better_loot_zibura.MOD_ID + "/" + packName;
        ResourcePackInfo info = new ResourcePackInfo(
                id,
                Text.literal("Better Loot Zibura - " + packName),
                ResourcePackSource.BUILTIN,
                Optional.empty()
        );
        ResourcePackPosition position = new ResourcePackPosition(
                true, // required (same as Forge)
                ResourcePackProfile.InsertionPosition.TOP,
                false // fixedPosition; no extra pinning semantics
        );
        ResourcePackProfile profile = ResourcePackProfile.create(
                info,
                new DirectoryResourcePack.DirectoryBackedFactory(root),
                ResourceType.SERVER_DATA,
                position
        );
        if (profile != null) {
            profileAdder.accept(profile);
            better_loot_zibura.LOGGER.info("Registered builtin datapack {} at TOP (required)", id);
        } else {
            better_loot_zibura.LOGGER.warn("Skipping invalid bundled datapack {} at {}", id, root);
        }
    }
}
