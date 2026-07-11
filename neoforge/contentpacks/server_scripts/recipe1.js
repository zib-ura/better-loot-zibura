ServerEvents.recipes(event => {
    // 移除 brewinnandchewin 的两种奶酪轮（有序和无序）
    event.remove({ output: 'brewinandchewin:scarlet_cheese_wheel', type: 'minecraft:crafting_shaped' });
    event.remove({ output: 'brewinandchewin:scarlet_cheese_wheel', type: 'minecraft:crafting_shapeless' });
    event.remove({ output: 'brewinandchewin:flaxen_cheese_wheel', type: 'minecraft:crafting_shaped' });
    event.remove({ output: 'brewinandchewin:flaxen_cheese_wheel', type: 'minecraft:crafting_shapeless' });
    // 移除 trailandtales_delight 的奶酪轮
    event.remove({ output: 'trailandtales_delight:cheese_wheel', type: 'minecraft:crafting_shaped' });
    event.remove({ output: 'trailandtales_delight:cheese_wheel', type: 'minecraft:crafting_shapeless' });
});