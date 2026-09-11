package com.zibura.better_loot_zibura.loot.function;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.zibura.better_loot_zibura.event.CommonEvents;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

import java.util.List;
import java.util.Optional;

public class LazyEnchantRandomlyFunction extends LootItemConditionalFunction {
    private final List<ResourceLocation> allowedEnchants;

    public static final MapCodec<LazyEnchantRandomlyFunction> CODEC = RecordCodecBuilder.mapCodec(instance ->
            commonFields(instance)
                    .and(
                            ResourceLocation.CODEC.listOf().fieldOf("enchantments")
                                    .forGetter(func -> func.allowedEnchants)
                    )
                    .apply(instance, LazyEnchantRandomlyFunction::new)
    );

    protected LazyEnchantRandomlyFunction(List<LootItemCondition> conditions, List<ResourceLocation> allowedEnchants) {
        super(conditions);
        this.allowedEnchants = allowedEnchants;
    }

    public LazyEnchantRandomlyFunction(List<ResourceLocation> allowedEnchants) {
        this(List.of(), allowedEnchants);
    }

    @Override
    public LootItemFunctionType<LazyEnchantRandomlyFunction> getType() {
        return CommonEvents.ModLootFunctions.LAZY_ENCHANT_RANDOMLY;
    }

//    @Override
//    public ItemStack run(ItemStack stack, LootContext context) {
//        RegistryAccess regAccess = context.getLevel().registryAccess();
//        var enchantRegistry = regAccess.registryOrThrow(Registries.ENCHANTMENT);
//
//        List<Holder.Reference<Enchantment>> validHolders = allowedEnchants.stream()
//                .map(rl -> ResourceKey.create(Registries.ENCHANTMENT, rl))
//                .map(enchantRegistry::getHolder)
//                .flatMap(Optional::stream)
//                .toList();
//
//        if (validHolders.isEmpty()) {
//            return stack;
//        }
//
//        Holder<Enchantment> chosen = validHolders.get(context.getRandom().nextInt(validHolders.size()));
//        int level = chosen.value().getMinLevel();
//
//        // 兼容处理：远古书或普通成书转为附魔书/存储附魔
//        if (stack.is(Items.BOOK) || stack.getItem().getDescriptionId().contains("ancient_book")) {
//            ItemEnchantments stored = stack.getOrDefault(DataComponents.STORED_ENCHANTMENTS, ItemEnchantments.EMPTY);
//            ItemEnchantments.Mutable mutable = new ItemEnchantments.Mutable(stored);
//            mutable.set(chosen, level);
//            stack.set(DataComponents.STORED_ENCHANTMENTS, mutable.toImmutable());
//        } else {
//            stack.enchant(chosen, level);
//        }
//
//        return stack;
//    }
@Override
public ItemStack run(ItemStack stack, LootContext context) {
    RegistryAccess regAccess = context.getLevel().registryAccess();
    var enchantRegistry = regAccess.registryOrThrow(Registries.ENCHANTMENT);

    List<Holder.Reference<Enchantment>> validHolders = allowedEnchants.stream()
            .map(rl -> ResourceKey.create(Registries.ENCHANTMENT, rl))
            .map(enchantRegistry::getHolder)
            .flatMap(Optional::stream)
            .toList();

    if (validHolders.isEmpty()) {
        return stack;
    }

    Holder<Enchantment> chosen = validHolders.get(context.getRandom().nextInt(validHolders.size()));
    Enchantment enchantVal = chosen.value();

    // 支持附魔的最大/最小等级随机，若需要固定1级可保留 getMinLevel()
    int minLvl = enchantVal.getMinLevel();
    int maxLvl = enchantVal.getMaxLevel();
    int level = minLvl == maxLvl ? minLvl : context.getRandom().nextIntBetweenInclusive(minLvl, maxLvl);

    // 1. 普通书：转为原版附魔书，并将附魔写入 STORED_ENCHANTMENTS
    if (stack.is(Items.BOOK)) {
        ItemStack enchantedBook = stack.transmuteCopy(Items.ENCHANTED_BOOK, stack.getCount());
        ItemEnchantments stored = enchantedBook.getOrDefault(DataComponents.STORED_ENCHANTMENTS, ItemEnchantments.EMPTY);
        ItemEnchantments.Mutable mutable = new ItemEnchantments.Mutable(stored);
        mutable.set(chosen, level);
        enchantedBook.set(DataComponents.STORED_ENCHANTMENTS, mutable.toImmutable());
        return enchantedBook;
    }

    // 2. 远古书 (Immersive Enchanting 兼容)：保持原物品，写入 STORED_ENCHANTMENTS (通常固定 1 级)
    if (stack.getItem().getDescriptionId().contains("ancient_book")) {
        ItemEnchantments stored = stack.getOrDefault(DataComponents.STORED_ENCHANTMENTS, ItemEnchantments.EMPTY);
        ItemEnchantments.Mutable mutable = new ItemEnchantments.Mutable(stored);
        mutable.set(chosen, 1);
        stack.set(DataComponents.STORED_ENCHANTMENTS, mutable.toImmutable());
        return stack;
    }

    // 3. 其它常规装备或工具：直接附魔
    stack.enchant(chosen, level);
    return stack;
}

    public static LootItemConditionalFunction.Builder<?> builder(List<ResourceLocation> enchantments) {
        return simpleBuilder(conditions -> new LazyEnchantRandomlyFunction(conditions, enchantments));
    }
}