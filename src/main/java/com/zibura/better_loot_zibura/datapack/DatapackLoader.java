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

        registerIfLoaded(event, "brewery", "brewery");
        registerIfLoaded(event, "brewinandchewin", "brewinandchewin");
        registerIfLoaded(event, "chalk", "chalk");
        registerIfLoaded(event, "corn_delight", "corn_delight");
        registerIfLoaded(event, "ctov", "ctov");
        registerIfLoaded(event, "dumplings_delight", "dumplings_delight");
        registerIfLoaded(event, "explorify", "explorify");
        registerIfLoaded(event, "farmersdelight", "farmersdelight");
        registerIfLoaded(event, "immersive_armors", "immersive_armors");
        registerIfLoaded(event, "immersiveenchanting", "immersiveenchanting");
        registerIfLoaded(event, "kaisyn", "t_and_t");
        registerIfLoaded(event, "kattersstructures", "mr_katters_structures");
        registerIfLoaded(event, "kitchenkarrot", "kitchenkarrot");
        registerIfLoaded(event, "miners_delight", "miners_delight");
        registerIfLoaded(event, "muffins_thaidelight", "muffins_thaidelight");
        // These two mappings are inferred from mods.toml; verify against the actual mod jars.
        registerIfLoaded(event, "nova_cherry_village", "mr_cherry_villages");
        registerIfLoaded(event, "nova_structures", "mr_dungeons_andtaverns");

        registerIfLoaded(event, "ramadandelight", "ramadandelight");
        registerIfLoaded(event, "rationcraft", "rationcraft");
        registerIfLoaded(event, "repurposed_structures", "repurposed_structures");
        registerIfLoaded(event, "revampedvillages", "mr_lukis_grandcapitals");
        registerIfLoaded(event, "roadweaver", "roadweaver");
        registerIfLoaded(event, "rusticdelight", "rusticdelight");
        registerIfLoaded(event, "supplementaries", "supplementaries");
        registerIfLoaded(event, "touhou_little_maid", "touhou_little_maid");
        registerIfLoaded(event, "trailandtales_delight", "trailandtales_delight");
        registerIfLoaded(event, "travelersbackpack", "travelersbackpack");
        registerIfLoaded(event, "trek", "mr_trek");
        registerIfLoaded(event, "vanillacookbook", "vanillacookbook");
        registerIfLoaded(event, "veggiesdelight", "veggiesdelight");
        registerIfLoaded(event, "vintagedelight", "vintagedelight");
        registerPack(event, "minecraft");
    }

    private static void registerIfLoaded(AddPackFindersEvent event, String packName, String modId) {
        if (ModList.get().isLoaded(modId)) registerPack(event, packName);
    }

    private static void registerPack(AddPackFindersEvent event, String packName) {
        Path root = ModList.get().getModFileById(better_loot_zibura.MOD_ID)
                .getFile().findResource("packs", packName);
        if (!Files.isRegularFile(root.resolve("pack.mcmeta"))) return;

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
