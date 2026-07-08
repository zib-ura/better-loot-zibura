const vanilla_desert_pyramid_Treasure_Treasure = [
    {
        groupName: "Treasure",
        groupWeight: 30,
        min: 1, max: 2,
        items: [
            { id: 'minecraft:diamond', ratio: 10 },
            { id: 'minecraft:ender_pearl', ratio: 10 },
            { id: 'minecraft:ender_eye', ratio: 10 },
            { id: 'minecraft:gold_ingot', ratio: 10 },
            { id: 'minecraft:totem_of_undying', ratio: 5 },
        ]
    },
];
const vanilla_desert_pyramid_emerald = [
    {
        groupName: "emerald",
        groupWeight: 25,  
        min: 1,
        max: 1,
        items: [
            { id: 'minecraft:emerald_block', ratio: 10 },

            { id: 'minecraft:emerald', ratio: 10 },

        ]
    },
];

const desert_pyramid_smithing_templates = [
    {
        groupName: "Smithing Templates",
        groupWeight: 25,  
        min: 1,
        max: 1,
        items: [
            { id: 'minecraft:dune_armor_trim_smithing_template', ratio: 10 },
        ]
    },

    {
        groupName: "empty",
        groupWeight: 50,
        items: [
            { "id": "empty" },
        ]
    },
];

const vanilla_desert_pyramid_Food = [
    {
        groupName: "Food",
        groupWeight: 25,
        min: 1, max: 1,
        items: [
            { id: 'minecraft:golden_apple', ratio: 10 },
            { id: 'minecraft:enchanted_golden_apple', ratio: 5 },
            { id: 'minecraft:golden_carrot', ratio: 10 },
            { id: 'minecraft:glistering_melon_slice', ratio: 10 }, 
            { id: 'kaleidoscope_cookery:golden_salad', ratio: 10 },       
           
            { id: 'minecraft:experience_bottle', ratio: 10 },      

        ]
    }
];

// 2. 沙漠神殿：食物与药水
const vanilla_desert_pyramid_potion = [
    {
        groupName: "potion",
        groupWeight: 25,
        min: 1, max: 1,
        items: [

            // --- 法老可能会喜欢的药水效果 ---
            // 烈日抗性（抗火）
            { id: 'minecraft:potion', ratio: 3, potion: "minecraft:fire_resistance" }, 
            { id: 'minecraft:potion', ratio: 1, potion: "minecraft:long_fire_resistance" },
            
            // 统治者的力量（力量）
            { id: 'minecraft:potion', ratio: 3, potion: "minecraft:strength" },
            { id: 'minecraft:potion', ratio: 1, potion: "minecraft:strong_strength" },
            
            // 迅捷如沙暴（迅捷）
            { id: 'minecraft:potion', ratio: 3, potion: "minecraft:swiftness" },
            { id: 'minecraft:potion', ratio: 1, potion: "minecraft:long_swiftness" },
            
            // 不朽与恩赐（治疗与再生）
            { id: 'minecraft:potion', ratio: 3, potion: "minecraft:healing" },
            { id: 'minecraft:potion', ratio: 1, potion: "minecraft:strong_healing" },
            
            // // 法老的诅咒（负面药水，赐予胆敢盗墓者的惩罚）
            // { id: 'minecraft:potion', ratio: 2, potion: "minecraft:harming" },     // 瞬间伤害
            // { id: 'minecraft:potion', ratio: 2, potion: "minecraft:poison" },      // 剧毒
            // { id: 'minecraft:potion', ratio: 2, potion: "minecraft:weakness" },    // 虚弱
            // { id: 'minecraft:potion', ratio: 1, potion: "minecraft:slowness" },    // 迟缓
        ]
    }
];
const vanilla_desert_pyramid_gold_nugget = [
    {
        groupName: "gold nugget",
        groupWeight: 25,  
        min: 1,
        max: 1,
        items: [
            { id: 'minecraft:gold_nugget', ratio: 10 },
        ]
    },
];

const vanilla_desert_pyramid_gold_and_diamond_products = [
    {
        groupName: "Gold Products",
        groupWeight: 50,
        min: 1,
        max: 1,        
        enchantChance: 0.7, 
        enchantLevels: [20, 30], 
        items: [
            // 金制工具与武器
            { id: 'minecraft:golden_sword', ratio: 10 },
            // { id: 'minecraft:golden_pickaxe', ratio: 10 },
            // { id: 'minecraft:golden_axe', ratio: 10 },
            // { id: 'minecraft:golden_shovel', ratio: 10 },
            // { id: 'minecraft:golden_hoe', ratio: 10 },
            // 金制盔甲
            { id: 'minecraft:golden_helmet', ratio: 10 },
            { id: 'minecraft:golden_chestplate', ratio: 10 },
            { id: 'minecraft:golden_leggings', ratio: 10 },
            { id: 'minecraft:golden_boots', ratio: 10 },
            { id: 'minecraft:golden_horse_armor', ratio: 10 }, // 金马铠
        ]
    },
    {
        groupName: "Diamond Products",
        groupWeight: 50,
        min: 1,
        max: 1,        
        enchantChance: 0.7, 
        enchantLevels: [20, 30], 
        items: [
            // 钻石制工具与武器
            { id: 'minecraft:diamond_sword', ratio: 10 },
            // { id: 'minecraft:diamond_pickaxe', ratio: 10 },
            // { id: 'minecraft:diamond_axe', ratio: 10 },
            // { id: 'minecraft:diamond_shovel', ratio: 10 },
            // { id: 'minecraft:diamond_hoe', ratio: 10 },
            // 钻石制盔甲
            { id: 'minecraft:diamond_helmet', ratio: 10 },
            { id: 'minecraft:diamond_chestplate', ratio: 10 },
            { id: 'minecraft:diamond_leggings', ratio: 10 },
            { id: 'minecraft:diamond_boots', ratio: 10 },
            { id: 'minecraft:diamond_horse_armor', ratio: 10 }, // 钻石马铠
            // 钻石原材料
            // { id: 'minecraft:diamond_block', ratio: 10 }        // 钻石块
        ]
    }
];

const vanilla_desert_pyramid_gold_and_diamond_repair = [
    {
        groupName: "Gold",
        groupWeight: 50,
        min: 1,
        max: 1,        
        items: [
            { id: 'minecraft:gold_ingot', ratio: 10 },        // 金锭
        ]
    },
    {
        groupName: "Diamond",
        groupWeight: 50,
        min: 1,
        max: 1,        
        items: [
            { id: 'minecraft:diamond', ratio: 10 },             // 钻石
        ]
    },
];

const vanilla_desert_pyramid_anvil = [
    {
        groupName: "anvil",
        groupWeight: 50,
        min: 1,
        max: 1,        
        items: [
            { id: 'minecraft:anvil', ratio: 10 },        // 金锭
        ]
    },
    {
        groupName: "empty",
        groupWeight: 50,
        items: [
            { "id": "empty" },
        ]
    },
];

const vanilla_desert_pyramid_Archaeology = [
    {
        groupName: "Archaeology",
        groupWeight: 25,
        min: 1, max: 2,
        items: [
            { id: 'minecraft:pottery_sherd_archer', ratio: 10 },   // 弓箭手陶片
            { id: 'minecraft:pottery_sherd_prize', ratio: 10 },    // 奖杯陶片
            { id: 'minecraft:pottery_sherd_skull', ratio: 10 },    // 骷髅陶片
            { id: 'minecraft:pottery_sherd_miner', ratio: 10 },    // 矿工陶片
        ]
    }
];
// =================================================================
// 事件注册区域
// =================================================================
const desert_pyramid_valuable_content = [
    [vanilla_desert_pyramid_Treasure_Treasure, 4, 4],
    [vanilla_desert_pyramid_emerald, 14, 16],
    [desert_pyramid_smithing_templates, 1, 1],
];

const desert_pyramid_supply_content = [
    [vanilla_desert_pyramid_Food, 4, 4],
    [vanilla_desert_pyramid_potion, 2, 4],
    [vanilla_desert_pyramid_gold_nugget, 14, 16],
];

const desert_pyramid_Archaeology_content = [
    [vanilla_desert_pyramid_Archaeology, 4, 4],
];

const desert_pyramid_armor_content = [
    [vanilla_desert_pyramid_gold_and_diamond_products, 4, 4],
    [vanilla_desert_pyramid_gold_and_diamond_repair, 4, 4],
    [vanilla_desert_pyramid_anvil, 1, 1],
];

const loot_desert_pyramid = [
    [desert_pyramid_valuable_content, 1],
    [desert_pyramid_supply_content, 1], 
    // [desert_pyramid_Archaeology_content, 1],
    [desert_pyramid_armor_content, 1], 
];