package com.zibura.better_loot_zibura.datapack;

import com.zibura.better_loot_zibura.better_loot_zibura;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackLocationInfo;
import net.minecraft.server.packs.PackSelectionConfig;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.PathPackResources;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.AddPackFindersEvent;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

@EventBusSubscriber(modid = better_loot_zibura.MOD_ID)
public final class DatapackLoader {
    private DatapackLoader() {}

    @SubscribeEvent
    public static void onAddPackFinders(AddPackFindersEvent event) {
        if (event.getPackType() != PackType.SERVER_DATA) return;

        Path packsRoot = ModList.get()
                .getModFileById(better_loot_zibura.MOD_ID)
                .getFile().findResource("packs");
        if (!Files.isDirectory(packsRoot)) return;

        try (var folders = Files.list(packsRoot)) {
            folders.filter(Files::isDirectory)
                    .sorted()
                    .forEach(root -> {
                        String modId = root.getFileName().toString();
                        if (("minecraft".equals(modId) || ModList.get().isLoaded(modId))
                                && Files.isRegularFile(root.resolve("pack.mcmeta"))) {
                            registerPack(event, modId, root);
                        }
                    });
        } catch (IOException e) {
            throw new UncheckedIOException("Failed to scan bundled datapacks", e);
        }
    }

    private static void registerPack(AddPackFindersEvent event, String packName, Path root) {
        String id = better_loot_zibura.MOD_ID + "/" + packName;
        Pack pack = Pack.readMetaAndCreate(
                new PackLocationInfo(
                        id,
                        Component.literal("Better Loot Zibura - " + packName),
                        PackSource.BUILT_IN,
                        Optional.empty()
                ),
                new PathPackResources.PathResourcesSupplier(root),
                PackType.SERVER_DATA,
                new PackSelectionConfig(true, Pack.Position.TOP, false)
        );
        if (pack != null) {
            event.addRepositorySource(consumer -> consumer.accept(pack));
        }
    }
}
