package com.zibura.better_loot_zibura.loot.function;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

import java.util.List;
import java.util.Optional;

public class LazyEnchantRandomlyFunction extends LootItemConditionalFunction {

    private final List<Identifier> allowedEnchants;

    public static final MapCodec<LazyEnchantRandomlyFunction> CODEC =
            RecordCodecBuilder.mapCodec(instance ->
                    commonFields(instance)
                            .and(
                                    Identifier.CODEC
                                            .listOf()
                                            .fieldOf("enchantments")
                                            .forGetter(func -> func.allowedEnchants)
                            )
                            .apply(instance, LazyEnchantRandomlyFunction::new)
            );

    protected LazyEnchantRandomlyFunction(
            Optional<Holder<LootItemCondition>> condition,
            List<Identifier> allowedEnchants
    ) {
        super(condition);
        this.allowedEnchants = allowedEnchants;
    }

    public LazyEnchantRandomlyFunction(List<Identifier> allowedEnchants) {
        this(Optional.empty(), allowedEnchants);
    }

    @Override
    public MapCodec<? extends LootItemConditionalFunction> codec() {
        return CODEC;
    }

    @Override
    protected ItemStack run(ItemStack stack, LootContext context) {
        RegistryAccess regAccess = context.getLevel().registryAccess();
        var enchantRegistry = regAccess.lookupOrThrow(Registries.ENCHANTMENT);

        List<Holder.Reference<Enchantment>> validHolders = allowedEnchants.stream()
                .map(id -> ResourceKey.create(Registries.ENCHANTMENT, id))
                .map(enchantRegistry::get)
                .flatMap(Optional::stream)
                .toList();

        if (validHolders.isEmpty()) {
            return stack;
        }

        Holder<Enchantment> chosen =
                validHolders.get(context.getRandom().nextInt(validHolders.size()));

        Enchantment enchantVal = chosen.value();

        int minLvl = enchantVal.getMinLevel();
        int maxLvl = enchantVal.getMaxLevel();

        int level = minLvl == maxLvl
                ? minLvl
                : context.getRandom().nextIntBetweenInclusive(minLvl, maxLvl);

        // 普通书 -> 附魔书
        if (stack.is(Items.BOOK)) {
            ItemStack enchantedBook =
                    stack.transmuteCopy(Items.ENCHANTED_BOOK, stack.getCount());

            ItemEnchantments stored = enchantedBook.getOrDefault(
                    DataComponents.STORED_ENCHANTMENTS,
                    ItemEnchantments.EMPTY
            );

            ItemEnchantments.Mutable mutable =
                    new ItemEnchantments.Mutable(stored);

            mutable.set(chosen, level);

            enchantedBook.set(
                    DataComponents.STORED_ENCHANTMENTS,
                    mutable.toImmutable()
            );

            return enchantedBook;
        }

        // Immersive Enchanting 的远古书兼容
        if (stack.getItem().getDescriptionId().contains("ancient_book")) {
            ItemEnchantments stored = stack.getOrDefault(
                    DataComponents.STORED_ENCHANTMENTS,
                    ItemEnchantments.EMPTY
            );

            ItemEnchantments.Mutable mutable =
                    new ItemEnchantments.Mutable(stored);

            mutable.set(chosen, 1);

            stack.set(
                    DataComponents.STORED_ENCHANTMENTS,
                    mutable.toImmutable()
            );

            return stack;
        }

        // 普通装备/工具
        stack.enchant(chosen, level);
        return stack;
    }

    public static LootItemConditionalFunction.Builder<?> builder(
            List<Identifier> enchantments
    ) {
        return simpleBuilder(
                condition -> new LazyEnchantRandomlyFunction(
                        condition,
                        enchantments
                )
        );
    }
}