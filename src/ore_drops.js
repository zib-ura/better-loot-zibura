LootJS.modifiers(event => {
    // stone
    event.addBlockModifier(
    [
        "minecraft:coal_ore",
        "minecraft:iron_ore",
        "minecraft:copper_ore",
        "minecraft:gold_ore",
        "minecraft:redstone_ore",
        "minecraft:lapis_ore",
        "minecraft:diamond_ore",
        "minecraft:emerald_ore",
        "create:zinc_ore"
    ]
        )
        .addLoot(LootEntry.of("minecraft:cobblestone")
            .matchMainHand(ItemFilter.not(
                ItemFilter.hasEnchantment("minecraft:silk_touch"))));
    })

LootJS.modifiers(event => {    
    // deepslate
    event.addBlockModifier(
    [
        "minecraft:deepslate_coal_ore",
        "minecraft:deepslate_iron_ore",
        "minecraft:deepslate_copper_ore",
        "minecraft:deepslate_gold_ore",
        "minecraft:deepslate_redstone_ore",
        "minecraft:deepslate_lapis_ore",
        "minecraft:deepslate_diamond_ore",
        "minecraft:deepslate_emerald_ore",
        "create:deepslate_zinc_ore"
    ]
        )
        .addLoot(LootEntry.of("minecraft:cobbled_deepslate")
            .matchMainHand(ItemFilter.not(
                ItemFilter.hasEnchantment("minecraft:silk_touch"))));
            })

LootJS.modifiers(event => {            
    // nether
    event.addBlockModifier(
    [
        "minecraft:nether_gold_ore",
        "minecraft:nether_quartz_ore"
    ]
        )
        .addLoot(LootEntry.of("minecraft:netherrack")
            .matchMainHand(ItemFilter.not(
                ItemFilter.hasEnchantment("minecraft:silk_touch"))));
        });