// ============================================================
// 物品与配方统一管理脚本
// 功能：将不同模组的相似物品映射到统一标签，并批量替换配方中的输入/输出
// 版本：1.0
// ============================================================

//#region 1. 把物品或tag加入新tag并替换输入
// 定义需要统一的映射表
// item: 可以是具体物品 ID (如 'mod:item')，也可以是带 # 的标签 (如 '#mod:tag')
// toTag: 统一归类到的目标标签名（可以带 #，内部会自动处理）
const UNIFICATION_MAP = [
    // // 示例：生鱼片（已注释）
    // { item: 'kaleidoscope_cookery:sashimi', toTag: '#c:sashimi' },
    // { item: 'kitchenkarrot:sashimi', toTag: '#c:sashimi' },

    // 盐
    { item: 'voidlessframework:salt', toTag: '#c:salt' },
    { item: 'kaleidoscope_chinesefood:salt', toTag: '#c:salt' },
    { item: 'bakeries:salt', toTag: '#c:salt' },


    // 烹饪油
    { item: 'kaleidoscope_cookery:oil', toTag: '#c:cooking_oil' },
    { item: 'rusticdelight:cooking_oil', toTag: '#c:cooking_oil' },
    { item: 'youkaishomecoming:butter', toTag: '#c:cooking_oil' },
    { item: 'farm_and_charm:butter', toTag: '#c:cooking_oil' },
    { item: 'bakeries:olive_oil', toTag: '#c:cooking_oil' },

    // 奶酪
    { item: 'vanillacookbook:cheese', toTag: '#c:cheese' },
    { item: 'vanillacookbook:sculky_cheese', toTag: '#c:cheese' },
    { item: 'brewinandchewin:flaxen_cheese_wedge', toTag: '#c:cheese' },
    { item: 'brewinandchewin:scarlet_cheese_wedge', toTag: '#c:cheese' },
    { item: 'kitchenkarrot:cheese_slice', toTag: '#c:cheese' },
    { item: 'kitchenkarrot:golden_cheese', toTag: '#c:cheese' },
    { item: 'trailandtales_delight:cheese_slice', toTag: '#c:cheese' },
    { item: 'trailandtales_delight:cherry_cheese_slice', toTag: '#c:cheese' },
    { item: 'casualnessdelight:cheese_wheel_slice', toTag: '#c:cheese' },

    // // 蛋（已注释）
    // { item: 'farmersdelight:fried_egg', toTag: '#c:foods/cooked_eggs' },
    // { item: 'vanillacookbook:cooked_egg', toTag: '#c:foods/cooked_eggs' },

    // 水
    { item: 'minecraft:water_bucket', toTag: '#c:water' },
    { item: 'kitchenkarrot:water', toTag: '#c:water' },

    // // 熟米饭（已注释）
    // { item: 'farmersdelight:cooked_rice', toTag: '#c:foods/cooked_rice' },

    // 牛奶桶
    { item: 'minecraft:milk_bucket', toTag: '#c:milk' },

    // 巧克力
    { item: 'create:bar_of_chocolate', toTag: '#c:chocolate' },
    { item: 'vanillacookbook:chocolate', toTag: '#c:chocolate' },
    { item: 'bakery:chocolate_truffle', toTag: '#c:chocolate' },

];

// 2. 需要强制扫描的配方类型列表
// 如果某个模组的厨具没有自动替换，将其配方 type 添加到此数组
const RECIPE_TYPES = [];

// 物品标签统一（ServerEvents.tags）
// 将映射表中的每个物品添加到对应的目标标签中
ServerEvents.tags('item', event => {
    UNIFICATION_MAP.forEach(pair => {
        // 移除 toTag 开头的 '#' 符号（如果有）
        const cleanTag = pair.toTag.startsWith('#') ? pair.toTag.slice(1) : pair.toTag;
        event.add(cleanTag, pair.item);
    });
});

// 将所有配方中出现的原物品（pair.item）替换为统一标签（pair.toTag）
ServerEvents.recipes(event => {
    UNIFICATION_MAP.forEach(pair => {
        event.replaceInput({}, pair.item, pair.toTag);
    });
});
//#endregion

//#region 2. 移除替换特定配方
// 移除不需要的独立配方，避免重复或冲突
ServerEvents.recipes(event => {
    // 移除特定的生鱼片配方
    event.remove({ output: 'kitchenkarrot:sashimi' });

    // 移除奶酪和牛奶瓶配方（将用统一方式重新添加）
    event.remove({ output: 'vanillacookbook:cheese' });
    event.remove({ output: 'vanillacookbook:milk_bottle' });

    // // 移除煎蛋配方（已注释）
    // event.remove({ output: 'farmersdelight:fried_egg' });
    event.remove({ output: 'vanillacookbook:cooked_egg' });


    // 移除水相关的旧配方，并添加水与水桶的无序互换配方
    event.remove({ id: 'kitchenkarrot:water' });
    event.remove({ id: 'kitchenkarrot:water_bucket' });
    event.shapeless('8x kitchenkarrot:water', [
        'minecraft:water_bucket',
    ]);
    event.shapeless('minecraft:water_bucket', [
        'minecraft:bucket',
        '8x kitchenkarrot:water',
    ]);

    event.remove({ id: 'kaleidoscope_cookery:oil_block' });
    event.shapeless('kaleidoscope_cookery:oil_block', [
        '9x kaleidoscope_cookery:oil'
    ]);
});


//#endregion

//#region 3. 自定义无序合成配方
ServerEvents.recipes(event => {
    // 肥皂：用水桶 + 4个灰烬 + 烹饪油
    event.shapeless('6x supplementaries:soap', [
        'minecraft:water_bucket',
        'supplementaries:ash',
        'supplementaries:ash',
        'supplementaries:ash',
        'supplementaries:ash',
        '#c:cooking_oil'      // 替换了原配方中的猪排
    ]);

    // 巧克力牛奶：牛奶瓶 + 可可豆
    event.shapeless('vanillacookbook:chocolate_milk', [
        'farmersdelight:milk_bottle',
        'minecraft:cocoa_beans',
    ]);

    // 浆果牛奶：牛奶瓶 + 甜浆果
    event.shapeless('vanillacookbook:berry_milk', [
        'farmersdelight:milk_bottle',
        'minecraft:sweet_berries',
    ]);
    event.shapeless('farm_and_charm:kernels', [
        'corn_delight:corn_seeds',

    ]);
    event.shapeless('farm_and_charm:corn', [
        'corn_delight:corn',
    ]);
});
//#endregion

//#region 4. 配方输出替换
// 确保某些配方输出中的牛奶项被正确替换为牛奶桶
ServerEvents.recipes(event => {
    event.replaceInput({ output: 'kitchenkarrot:milk' }, '#c:milk', 'minecraft:milk_bucket');
    event.replaceInput({ output: 'farmersdelight:milk_bottle' }, '#c:milk', 'minecraft:milk_bucket');
});
//#endregion

//#region 5. tag添加物品
ServerEvents.tags('item', event => {
    const conversions = [
        { item: '#c:foods/vegetables', toTag: '#c:vegetables' },
        // { item: '#c:vegetables/corn', toTag: '#c:crops/corn' },
        { item: '#c:cooking_oil', toTag: '#rusticdelight:cooking_oil' },
        { item: '#c:cooking_oil', toTag: '#farm_and_charm:butter' },

        { item: 'beautify:rope', toTag: '#c:ropes' },

        { item: 'kitchenkarrot:raw_vegan_mutton', toTag: '#c:raw_meats' },
        { item: 'kitchenkarrot:raw_vegan_mutton', toTag: '#c:foods/raw_meat' },

        { item: 'seeddelight:cherry_jam_jar', toTag: '#bakery:jam' },
        { item: 'seeddelight:cherry_rosehip_jar', toTag: '#bakery:jam' },       
        { item: 'brewinandchewin:sweet_berry_jam', toTag: '#bakery:jam' },      
        { item: 'brewinandchewin:glow_berry_marmalade', toTag: '#bakery:jam' },      
  
        { item: '#c:water', toTag: '#c:water_bottles' },

        { item: 'corn_delight:corn_seeds', toTag: '#c:seeds' },

    ]

    conversions.forEach(pair => {
        // 将 item 标签下的所有内容添加到 toTag 标签中
        const cleanTag = pair.toTag.startsWith('#') ? pair.toTag.slice(1) : pair.toTag;
        event.add(cleanTag, pair.item);
    })
})
//#endregion


//#region 6. 蔬菜特殊处理
// 1. 定义配置数组：包含物品ID、对应的标签、以及需要修复配方的目标模组
const vegetable_ENTRIES = [
    { id: 'corn_delight:corn', tag: '#c:vegetables/corn', targetMod: 'corn_delight' },
    { id: 'farm_and_charm:corn', tag: '#c:vegetables/corn', targetMod: 'corn_delight' }
];

ServerEvents.tags('item', event => {
    vegetable_ENTRIES.forEach(pair => {
        // 移除 toTag 开头的 '#' 符号（如果有）
        const cleanTag = pair.tag.startsWith('#') ? pair.tag.slice(1) : pair.tag;
        event.add(cleanTag, pair.id);
    });
});

// 将所有配方中出现的原物品（pair.item）替换为统一标签（pair.toTag）
ServerEvents.recipes(event => {
    vegetable_ENTRIES.forEach(pair => {
        event.replaceInput({ mod: pair.targetMod }, pair.id, pair.tag);
    });
});
//#endregion



//#region 7. 高炉
ServerEvents.recipes(event => {
    // 高炉专用时间：100 ticks（原版熔炉的一半）
    // 经验值保持原版熔炉的值

    // ==================== 石头类 ====================
    event.blasting('minecraft:stone', 'minecraft:cobblestone', 0.1, 100);
    event.blasting('minecraft:smooth_stone', 'minecraft:stone', 0.1, 100);
    event.blasting('minecraft:cracked_stone_bricks', 'minecraft:stone_bricks', 0.1, 100);
    event.blasting('minecraft:smooth_sandstone', 'minecraft:sandstone', 0.1, 100);
    event.blasting('minecraft:smooth_red_sandstone', 'minecraft:red_sandstone', 0.1, 100);
    event.blasting('minecraft:smooth_quartz', 'minecraft:quartz_block', 0.1, 100);
    event.blasting('minecraft:cracked_nether_bricks', 'minecraft:nether_bricks', 0.1, 100);
    event.blasting('minecraft:smooth_basalt', 'minecraft:basalt', 0.1, 100);
    event.blasting('minecraft:deepslate', 'minecraft:cobbled_deepslate', 0.1, 100);
    event.blasting('minecraft:cracked_deepslate_bricks', 'minecraft:deepslate_bricks', 0.1, 100);
    event.blasting('minecraft:cracked_deepslate_tiles', 'minecraft:deepslate_tiles', 0.1, 100);
    event.blasting('minecraft:cracked_polished_blackstone_bricks', 'minecraft:polished_blackstone_bricks', 0.1, 100);
    event.blasting('minecraft:terracotta', 'minecraft:clay', 0.35, 100);

    // ==================== 染色陶瓦 → 带釉陶瓦 ====================
    const dyes = [
        'white', 'orange', 'magenta', 'light_blue', 'yellow', 'lime',
        'pink', 'gray', 'light_gray', 'cyan', 'purple', 'blue',
        'brown', 'green', 'red', 'black'
    ];
    dyes.forEach(color => {
        event.blasting(`minecraft:${color}_glazed_terracotta`, `minecraft:${color}_terracotta`, 0.1, 100);
    });

    // ==================== 木炭 ====================
    event.blasting('minecraft:charcoal', '#minecraft:logs', 0.15, 100);

    // ==================== 砂石 → 玻璃 ====================
    event.blasting('minecraft:glass', 'minecraft:sand', 0.1, 100);
    event.blasting('minecraft:glass', 'minecraft:red_sand', 0.1, 100);
    // 如果你还想支持所有带 sand 标签的方块（比如模组里的），可以用下面这行（可选）
    // event.blasting('minecraft:glass', '#minecraft:sand', 0.1, 100);

    // ==================== 黏土球 → 红砖 ====================
    event.blasting('minecraft:brick', 'minecraft:clay_ball', 0.3, 100);
});