package com.zibura.better_loot_zibura.loot.unification;

import com.mojang.logging.LogUtils;
import com.zibura.better_loot_zibura.better_loot_zibura;
import com.zibura.better_loot_zibura.config.BetterLootConfig;
import net.minecraft.core.Holder;
import net.minecraft.core.MappedRegistry;
import net.minecraft.core.RegistrationInfo;
import net.minecraft.core.WritableRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import org.slf4j.Logger;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public final class ConvertibleLootTableGenerator {

    private static final Logger LOGGER = LogUtils.getLogger();
    private static final Map<ResourceLocation, LootTable> GENERATED_TABLES = new ConcurrentHashMap<>();

    private ConvertibleLootTableGenerator() {}

    public static void rebuildConvertibleLootTables() {
        GENERATED_TABLES.clear();
        if (!BetterLootConfig.ENABLE_CONVERTIBLE_LOOT_TABLES.get()) {
            return;
        }

        LOGGER.info("[ConvertibleLoot] 开始构建 1.21.1 虚拟战利品表, CONVERTIBLE_MAP 大小: {}", ItemUnificationSolver.CONVERTIBLE_MAP.size());

        for (Map.Entry<String, List<String>> entry : ItemUnificationSolver.CONVERTIBLE_MAP.entrySet()) {
            String groupKey = entry.getKey();
            List<String> itemIds = entry.getValue();
            if (itemIds == null || itemIds.isEmpty()) continue;

            // 1.21.1: LootPool.Builder 保留链式调用
            LootPool.Builder poolBuilder = LootPool.lootPool()
                    .name("convertible_pool")
                    .setRolls(ConstantValue.exactly(1.0F));

            int validItemCount = 0;
            for (String itemId : itemIds) {
                ResourceLocation itemRl = ResourceLocation.tryParse(itemId);
                if (itemRl != null) {
                    // 1.21.1 推荐从 BuiltInRegistries.ITEM 查找
                    Optional<Holder.Reference<Item>> itemHolder = BuiltInRegistries.ITEM.getHolder(itemRl);
                    if (itemHolder.isPresent()) {
                        poolBuilder.add(LootItem.lootTableItem(itemHolder.get().value()).setWeight(1));
                        validItemCount++;
                    }
                }
            }

            if (validItemCount > 0) {
                // 1.21.1 中构造 ResourceLocation 必须使用 fromNamespaceAndPath
                ResourceLocation tableId = ResourceLocation.fromNamespaceAndPath(better_loot_zibura.MOD_ID, "convertible/" + groupKey);

                LootTable lootTable = LootTable.lootTable()
                        .setParamSet(LootContextParamSets.ALL_PARAMS)
                        .withPool(poolBuilder)
                        .build();

                GENERATED_TABLES.put(tableId, lootTable);
            }
        }
        LOGGER.info("[ConvertibleLoot] 虚拟表构建完成，共生成 {} 张表", GENERATED_TABLES.size());
    }

    @SuppressWarnings("unchecked")
    public static void injectIntoServer(MinecraftServer server) {
        if (!BetterLootConfig.ENABLE_CONVERTIBLE_LOOT_TABLES.get()) {
            return;
        }

        if (GENERATED_TABLES.isEmpty()) {
            rebuildConvertibleLootTables();
        }

        try {
            // 1.21.1: LootTable 统一位于 server.reloadableRegistries() 容器中
            var registryLookup = server.reloadableRegistries().get();
            var lootRegistry = registryLookup.lookupOrThrow(Registries.LOOT_TABLE);

            if (lootRegistry instanceof MappedRegistry<?> mappedRegistry) {
                // 解除 MappedRegistry 的 frozen 冻结状态
                Field frozenField = MappedRegistry.class.getDeclaredField("frozen");
                frozenField.setAccessible(true);
                boolean wasFrozen = (boolean) frozenField.get(mappedRegistry);
                frozenField.set(mappedRegistry, false);

                WritableRegistry<LootTable> writableRegistry = (WritableRegistry<LootTable>) mappedRegistry;

                GENERATED_TABLES.forEach((id, table) -> {
                    ResourceKey<LootTable> key = ResourceKey.create(Registries.LOOT_TABLE, id);
                    ProblemReporter.Collector collector = new ProblemReporter.Collector();
                    var registryAccess = server.reloadableRegistries().get();

                    ValidationContext validationContext = new ValidationContext(
                            collector,
                            LootContextParamSets.ALL_PARAMS,
                            registryAccess.asGetterLookup() // 转为 HolderGetter.Provider
                    );
                    table.validate(validationContext);

                    // 注册虚拟战利品表
                    writableRegistry.register(key, table, RegistrationInfo.BUILT_IN);
                    LOGGER.info("[ConvertibleLoot] 成功注入 1.21.1 战利品表: {}", id);
                });

                // 注入完成后还原冻结状态
                frozenField.set(mappedRegistry, wasFrozen);
            } else {
                LOGGER.error("[ConvertibleLoot] LOOT_TABLE 注册表不是可修改的 MappedRegistry 实例");
            }
        } catch (Exception e) {
            LOGGER.error("[ConvertibleLoot] 反射注入虚拟表失败:", e);
        }
    }

    public static Map<ResourceLocation, LootTable> getGeneratedTables() {
        return Collections.unmodifiableMap(GENERATED_TABLES);
    }
}