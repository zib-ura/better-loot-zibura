package com.zibura.better_loot_zibura.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class BetterLootConfig {
    private static final Logger LOGGER = LoggerFactory.getLogger("BetterLootConfig");
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final File CONFIG_FILE = FabricLoader.getInstance().getConfigDir().resolve("better_loot_zibura.json").toFile();

    public static boolean enableRecipes = true;
    public static boolean enableLootTables = true;
    public static boolean enableLootAdditionsAndPacks = true;

    public static void register() {
        loadConfig();
    }

    public static void loadConfig() {
        if (!CONFIG_FILE.exists()) {
            saveConfig();
            return;
        }

        try (FileReader reader = new FileReader(CONFIG_FILE)) {
            JsonObject root = JsonParser.parseReader(reader).getAsJsonObject();
            if (root.has("convertible_unification")) {
                JsonObject section = root.getAsJsonObject("convertible_unification");
                if (section.has("enableRecipes")) {
                    enableRecipes = section.get("enableRecipes").getAsBoolean();
                }
                if (section.has("enableLootTables")) {
                    enableLootTables = section.get("enableLootTables").getAsBoolean();
                }
            }
            if (root.has("loot_injection") && root.get("loot_injection").isJsonObject()) {
                JsonObject injection = root.getAsJsonObject("loot_injection");
                if (injection.has("enableLootAdditionsAndPacks")) {
                    enableLootAdditionsAndPacks = injection.get("enableLootAdditionsAndPacks").getAsBoolean();
                }
            }
        } catch (Exception e) {
            LOGGER.error("[BetterLoot] Failed to load config, restoring defaults: ", e);
            saveConfig();
        }
    }

    public static void saveConfig() {
        JsonObject root = new JsonObject();
        JsonObject section = new JsonObject();

        section.addProperty("_comment_recipes", "Whether to register 1-to-1 shapeless conversion recipes for items in 'convertible' / 是否注册1对1配方");
        section.addProperty("enableRecipes", enableRecipes);

        section.addProperty("_comment_lootTables", "Whether to register a unified loot table for items in 'convertible' / 是否注册统合战利品表");
        section.addProperty("enableLootTables", enableLootTables);

        root.add("convertible_unification", section);

        JsonObject injection = new JsonObject();
        injection.addProperty("_comment", "Enable custom loot additions and bundled packs; restart to apply pack changes / 同时控制自定义战利品添加及 packs 数据包导入；数据包开关需重启");
        injection.addProperty("enableLootAdditionsAndPacks", enableLootAdditionsAndPacks);
        root.add("loot_injection", injection);

        try (FileWriter writer = new FileWriter(CONFIG_FILE)) {
            GSON.toJson(root, writer);
        } catch (IOException e) {
            LOGGER.error("[BetterLoot] Failed to save config: ", e);
        }
    }
}