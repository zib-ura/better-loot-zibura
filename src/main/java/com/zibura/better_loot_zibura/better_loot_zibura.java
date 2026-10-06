package com.zibura.better_loot_zibura;

import com.zibura.better_loot_zibura.config.BetterLootConfig;
import net.fabricmc.api.ModInitializer;

public class better_loot_zibura implements ModInitializer {
    public static final String MOD_ID = "better_loot_zibura";

    @Override
    public void onInitialize() {
        // 在 Fabric 初始化阶段注册配置
        BetterLootConfig.register();
    }
}