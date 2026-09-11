package com.zibura.better_loot_zibura;

import com.zibura.better_loot_zibura.config.BetterLootConfig;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;

@Mod(better_loot_zibura.MOD_ID)
public class better_loot_zibura {
    public static final String MOD_ID = "better_loot_zibura";

    // NeoForge 会自动注入 IEventBus 与 ModContainer
    public better_loot_zibura(IEventBus modEventBus, ModContainer modContainer) {
        // 注册配置
        BetterLootConfig.register(modContainer);
    }
}