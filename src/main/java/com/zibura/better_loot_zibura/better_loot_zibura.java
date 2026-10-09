package com.zibura.better_loot_zibura;

import com.zibura.better_loot_zibura.config.BetterLootConfig;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class better_loot_zibura implements ModInitializer {
    public static final String MOD_ID = "better_loot_zibura";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    @Override
    public void onInitialize() {
        // 在 Fabric 初始化阶段注册配置
        BetterLootConfig.register();
    }
}