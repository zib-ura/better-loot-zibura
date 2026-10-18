package com.zibura.better_loot_zibura.datapack;

import com.zibura.better_loot_zibura.better_loot_zibura;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraftforge.event.AddPackFindersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.resource.PathPackResources;

import java.nio.file.Files;
import java.nio.file.Path;

@Mod.EventBusSubscriber(modid = better_loot_zibura.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class DatapackLoader {
    private DatapackLoader() {}

    @SubscribeEvent
    public static void onAddPackFinders(AddPackFindersEvent event) {
        if (event.getPackType() != PackType.SERVER_DATA) return;

        Path packsRoot = ModList.get().getModFileById(better_loot_zibura.MOD_ID)
                .getFile().findResource("packs");
        if (!Files.isDirectory(packsRoot)) return;

        try (var folders = Files.list(packsRoot)) {
            folders.filter(Files::isDirectory).forEach(root -> {
                String modId = root.getFileName().toString();
                if (("minecraft".equals(modId) || ModList.get().isLoaded(modId))
                        && Files.isRegularFile(root.resolve("pack.mcmeta"))) {
                    registerPack(event, modId, root);
                }
            });
        } catch (java.io.IOException e) {
            throw new java.io.UncheckedIOException("Failed to scan bundled datapacks", e);
        }
    }

    private static void registerPack(AddPackFindersEvent event, String packName, Path root) {

        String id = better_loot_zibura.MOD_ID + "/" + packName;
        Pack pack = Pack.readMetaAndCreate(
                id,
                Component.literal("Better Loot Zibura - " + packName),
                true,
                packId -> new PathPackResources(packId, false, root),
                PackType.SERVER_DATA,
                Pack.Position.TOP,
                PackSource.BUILT_IN
        );
        if (pack != null) event.addRepositorySource(consumer -> consumer.accept(pack));
    }
}
