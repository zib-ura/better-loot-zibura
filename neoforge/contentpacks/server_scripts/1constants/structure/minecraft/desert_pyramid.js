const desert_pyramid_treasure = [
    {
        groupName: "Treasure",
        groupWeight: 30,
        min: 1, max: 1,
        items: [
            { id: 'minecraft:ender_pearl', ratio: 10 },
            { id: 'minecraft:ender_eye', ratio: 10 },
            { id: 'minecraft:totem_of_undying', ratio: 5 },
            { id: 'touhou_little_maid:fall_protect_bauble', ratio: 10 },
            { id: 'touhou_little_maid:explosion_protect_bauble', ratio: 10 },
        ]
    },
];


const desert_pyramid_ore = [
    {
        groupName: "ore",
        groupWeight: 25,  
        min: 1,
        max: 1,
        items: [
            { reference: 'lootjs:gold_ingot_and_gold_ingot_block', ratio: 10 },
            { reference: 'lootjs:emerald_and_emerald_block', ratio: 10 },
            { id: 'minecraft:diamond', ratio: 10 },

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

const desert_pyramid_Food = [
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

const desert_pyramid_potion = [
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
const desert_pyramid_gold_nugget = [
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

const desert_pyramid_gold_and_diamond_products = [
    {
        groupName: "Gold Products",
        groupWeight: 50,
        min: 1,
        max: 1,        
        enchantChance: 0.7, 
        enchantLevels: [20, 40], 
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
        enchantLevels: [20, 40], 
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

const desert_pyramid_gold_and_diamond_repair = [
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

const desert_pyramid_anvil = [
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

const desert_pyramid_Archaeology = [
    {
        groupName: "Archaeology",
        groupWeight: 25,
        min: 1, max: 1,
        items: [
            { id: 'minecraft:pottery_sherd_archer', ratio: 10 },   // 弓箭手陶片
            { id: 'minecraft:pottery_sherd_prize', ratio: 10 },    // 奖杯陶片
            { id: 'minecraft:pottery_sherd_skull', ratio: 10 },    // 骷髅陶片
            { id: 'minecraft:pottery_sherd_miner', ratio: 10 },    // 矿工陶片
        ]
    }
];

const desert_pyramid_enchanted_book = [
    {
        groupName: "desert_pyramid",
        groupWeight: 50,
        min: 1, max: 1,
        items: [
            { id: "minecraft:book", ratio: 10, enchantRandomly: ["minecraft:unbreaking"] }, 
            { id: "minecraft:book", ratio: 10, enchantRandomly: ["minecraft:mending"] },
            { id: "minecraft:book", ratio: 10, enchantRandomly: ["nova_structures:conductivity_curse"] },
            { id: "minecraft:book", ratio: 10, enchantRandomly: ["nova_structures:photosynthesis"] },
            { id: "minecraft:book", ratio: 10, enchantRandomly: ["minecraft:protection"] }, 
            { id: "minecraft:book", ratio: 10, enchantRandomly: ["minecraft:projectile_protection"] }, 
            { id: "minecraft:book", ratio: 10, enchantRandomly: ["minecraft:fire_protection"] }, 
            { id: "minecraft:book", ratio: 10, enchantRandomly: ["minecraft:blast_protection"] }, 
            { id: "minecraft:book", ratio: 10, enchantRandomly: ["kattersstructures:light"] },
            { id: "minecraft:book", ratio: 10, enchantRandomly: ["nova_structures:outreach"] },
            { id: "minecraft:book", ratio: 10, enchantRandomly: ["minecraft:feather_falling"] }, 
            { id: "minecraft:book", ratio: 10, enchantRandomly: ["nova_structures:traveler"] },
            { id: "minecraft:book", ratio: 10, enchantRandomly: ["minecraft:sharpness"] },
            { id: "minecraft:book", ratio: 10, enchantRandomly: ["minecraft:smite"] }, 
            { id: "minecraft:book", ratio: 10, enchantRandomly: ["minecraft:bane_of_arthropods"] }, 
            { id: "minecraft:book", ratio: 10, enchantRandomly: ["minecraft:knockback"] }, 
            { id: "minecraft:book", ratio: 10, enchantRandomly: ["minecraft:looting"] }, 
            { id: "minecraft:book", ratio: 10, enchantRandomly: ["minecraft:sweeping_edge"] }, 
            { id: "minecraft:book", ratio: 10, enchantRandomly: ["enchantencore:weaving_aspect"] },
            { id: "minecraft:book", ratio: 10, enchantRandomly: ["farmersdelight:backstabbing"] },
            { id: "minecraft:book", ratio: 10, enchantRandomly: ["kattersstructures:heavy"] },
            { id: "minecraft:book", ratio: 10, enchantRandomly: ["kattersstructures:blunt"] },
            { id: "minecraft:book", ratio: 10, enchantRandomly: ["kattersstructures:crystal_curse"] },
            { id: "minecraft:book", ratio: 10, enchantRandomly: ["nova_structures:illagers_bane"] },
            { id: "minecraft:book", ratio: 10, enchantRandomly: ["minecraft:power"] }, 
            { id: "minecraft:book", ratio: 10, enchantRandomly: ["minecraft:punch"] }, 
            { id: "minecraft:book", ratio: 10, enchantRandomly: ["minecraft:flame"] },
            { id: "minecraft:book", ratio: 10, enchantRandomly: ["minecraft:infinity"] },
            { id: "minecraft:book", ratio: 10, enchantRandomly: ["nova_structures:piercing"] },
            { id: "minecraft:book", ratio: 10, enchantRandomly: ["nova_structures:multishot"] },    
            { id: "minecraft:book", ratio: 10, enchantRandomly: ["minecraft:efficiency"] }, 
            { id: "minecraft:book", ratio: 10, enchantRandomly: ["minecraft:fortune"] }, 
            { id: "minecraft:book", ratio: 10, enchantRandomly: ["minecraft:silk_touch"] },
        ]
    }
];
// =================================================================
// 事件注册区域
// =================================================================
const desert_pyramid_valuable_content = [
    [desert_pyramid_treasure, 2, 3],
    [desert_pyramid_gold_and_diamond_products, 1, 2],
    [desert_pyramid_smithing_templates, 1, 1],
    [desert_pyramid_ore, 4, 6],
    [desert_pyramid_enchanted_book, 1, 1]
];

const desert_pyramid_supply_content = [
    [desert_pyramid_Food, 3, 4],
    [desert_pyramid_potion, 1, 2],
    [desert_pyramid_Archaeology, 1, 2],
    [desert_pyramid_gold_nugget, 4, 6],

];

const loot_desert_pyramid = [
    [desert_pyramid_valuable_content, 1],
    [desert_pyramid_supply_content, 1], 
];