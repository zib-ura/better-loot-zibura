package com.zibura.better_loot_zibura;

import com.zibura.better_loot_zibura.config.BetterLootConfig;
import net.minecraftforge.fml.common.Mod;

@Mod(better_loot_zibura.MOD_ID)
@Mod.EventBusSubscriber(modid = better_loot_zibura.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class better_loot_zibura {
    public static final String MOD_ID = "better_loot_zibura";

    public better_loot_zibura() {
        // ✅ 必须在此处注册配置
        BetterLootConfig.register();
    }

}