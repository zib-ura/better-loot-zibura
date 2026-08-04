const trial_chambers_reward_ominous_heavy_core = [
    {
        groupName: "heavy_core", 
        groupWeight: 70,
        min: 1, max: 1, 
        items: [
            { id: 'minecraft:heavy_core', ratio: 10 },        
            { id: "minecraft:book", ratio: 10, enchantRandomly: ["minecraft:wind_burst"] },
            { id: 'minecraft:enchanted_golden_apple', ratio: 2 },

        ]
    },
];

const trial_chambers_reward_ominous_explore = [
    {
        groupName: "structure_navigation", 
        groupWeight: 40,
        min: 1, max: 1, 
        items: [
            { id: 'minecraft:compass', ratio: 10 },
            { id: 'minecraft:spyglass', ratio: 10 },
            { id: 'naturescompass:naturescompass', ratio: 10 },
            { id: 'explorerscompass:explorerscompass', ratio: 10 },        
            { id: 'minecraft:ender_pearl', ratio: 10 },
            { id: 'minecraft:brush', ratio: 10 },
        ]
    },
    {
        groupName: "expedition_survival", 
        groupWeight: 30,
        min: 1, max: 1, 
        items: [
            // 抗火 (延长)
            { id: 'minecraft:potion', ratio: 10, potion: "minecraft:long_fire_resistance" },
            { id: 'minecraft:splash_potion', ratio: 10, potion: "minecraft:long_fire_resistance" },
            { id: 'minecraft:lingering_potion', ratio: 10, potion: "minecraft:long_fire_resistance" },
            // 神龟药水 II
            { id: 'minecraft:potion', ratio: 10, potion: "minecraft:strong_turtle_master" },
            { id: 'minecraft:splash_potion', ratio: 10, potion: "minecraft:strong_turtle_master" },
            { id: 'minecraft:lingering_potion', ratio: 10, potion: "minecraft:strong_turtle_master" },
            // 瞬间治疗 II
            { id: 'minecraft:potion', ratio: 10, potion: "minecraft:strong_healing" },
            { id: 'minecraft:splash_potion', ratio: 10, potion: "minecraft:strong_healing" },
            { id: 'minecraft:lingering_potion', ratio: 10, potion: "minecraft:strong_healing" },
            // 再生 II
            { id: 'minecraft:potion', ratio: 10, potion: "minecraft:strong_regeneration" },
            { id: 'minecraft:splash_potion', ratio: 10, potion: "minecraft:strong_regeneration" },
            { id: 'minecraft:lingering_potion', ratio: 10, potion: "minecraft:strong_regeneration" },
            // 力量 II
            { id: 'minecraft:potion', ratio: 10, potion: "minecraft:strong_strength" },
            { id: 'minecraft:splash_potion', ratio: 10, potion: "minecraft:strong_strength" },
            { id: 'minecraft:lingering_potion', ratio: 10, potion: "minecraft:strong_strength" },

        ]
    },

]

const trial_chambers_reward_ominous_enchanted = [
    {
        groupName: "book",
        groupWeight: 50,
        min: 1, max: 1,
        items: [

            // ---- 装备通用 (工具/武器/防具) ----
            { id: "minecraft:book", ratio: 10, enchantRandomly: ["minecraft:unbreaking"] }, 
            { id: "minecraft:book", ratio: 10, enchantRandomly: ["minecraft:mending"] },
            { id: "minecraft:book", ratio: 10, enchantRandomly: ["nova_structures:photosynthesis"] },

            // ---- 防具通用 ----
            { id: "minecraft:book", ratio: 10, enchantRandomly: ["minecraft:protection"] }, 
            { id: "minecraft:book", ratio: 10, enchantRandomly: ["minecraft:projectile_protection"] }, 
            { id: "minecraft:book", ratio: 10, enchantRandomly: ["minecraft:fire_protection"] }, 
            { id: "minecraft:book", ratio: 10, enchantRandomly: ["minecraft:blast_protection"] }, 

            // ---- 躯干 (胸甲) ----
            { id: "minecraft:book", ratio: 10, enchantRandomly: ["kattersstructures:light"] },
            { id: "minecraft:book", ratio: 10, enchantRandomly: ["nova_structures:outreach"] },
            { id: "minecraft:book", ratio: 10, enchantRandomly: ["nova_structures:antidote"] },

            // ---- 腿部 (护腿) ----
            { id: "minecraft:book", ratio: 10, enchantRandomly: ["minecraft:swift_sneak"] }, 

            // ---- 脚部 (靴子) ----
            { id: "minecraft:book", ratio: 10, enchantRandomly: ["minecraft:feather_falling"] }, 
            { id: "minecraft:book", ratio: 10, enchantRandomly: ["kattersstructures:web_walker"] },
            { id: "minecraft:book", ratio: 10, enchantRandomly: ["nova_structures:traveler"] },

            // ---- 近战武器 (剑/通用斧) ----
            { id: "minecraft:book", ratio: 10, enchantRandomly: ["minecraft:sharpness"] },
            { id: "minecraft:book", ratio: 10, enchantRandomly: ["minecraft:smite"] }, 
            { id: "minecraft:book", ratio: 10, enchantRandomly: ["minecraft:bane_of_arthropods"] }, 
            { id: "minecraft:book", ratio: 10, enchantRandomly: ["minecraft:knockback"] }, 
            { id: "minecraft:book", ratio: 10, enchantRandomly: ["minecraft:fire_aspect"] }, 
            { id: "minecraft:book", ratio: 10, enchantRandomly: ["minecraft:looting"] }, 
            { id: "minecraft:book", ratio: 10, enchantRandomly: ["minecraft:sweeping_edge"] }, 
            { id: "minecraft:book", ratio: 10, enchantRandomly: ["enchantencore:weaving_aspect"] },
            { id: "minecraft:book", ratio: 10, enchantRandomly: ["farmersdelight:backstabbing"] },
            { id: "minecraft:book", ratio: 10, enchantRandomly: ["kattersstructures:heavy"] },
            { id: "minecraft:book", ratio: 10, enchantRandomly: ["kattersstructures:blunt"] },

            // ---- 远程武器：弓 (Bow) ----
            { id: "minecraft:book", ratio: 10, enchantRandomly: ["minecraft:power"] }, 
            { id: "minecraft:book", ratio: 10, enchantRandomly: ["minecraft:punch"] }, 
            { id: "minecraft:book", ratio: 10, enchantRandomly: ["minecraft:flame"] },
            { id: "minecraft:book", ratio: 10, enchantRandomly: ["minecraft:infinity"] },
            { id: "minecraft:book", ratio: 10, enchantRandomly: ["nova_structures:piercing"] },
            { id: "minecraft:book", ratio: 10, enchantRandomly: ["nova_structures:multishot"] },
        ],
    },
    {
        groupName: "weapons", 
        groupWeight: 10,
        min: 1, max: 1, enchantChance: 0.7, enchantLevels: [20, 30],
        items: [
            { id: 'minecraft:diamond_sword', ratio: 10 },
            { id: 'minecraft:bow', ratio: 10 },

        ]
    },
    {
        groupName: "armors",
        groupWeight: 10,
        min: 1, max: 1, enchantChance: 0.7, enchantLevels: [20, 30],
        items: [
            { id: 'immersive_armors:divine_helmet', ratio: 1.5 },
            { id: 'immersive_armors:divine_chestplate', ratio: 1.5 },
            { id: 'immersive_armors:divine_leggings', ratio: 1.5 },
            { id: 'immersive_armors:divine_boots', ratio: 1.5 },

            { id: 'minecraft:diamond_helmet', ratio: 1.5 },
            { id: 'minecraft:diamond_chestplate', ratio: 1.5 },
            { id: 'minecraft:diamond_leggings', ratio: 1.5 },
            { id: 'minecraft:diamond_boots', ratio: 1.5 }
        ]
    },
    // {
    //     groupName: "empty",
    //     groupWeight: 30,
    //     items: [
    //         { id: "empty" },
    //     ]
    // },
];

const trial_chambers_reward_ominous_ornament = [
    {
        groupName: "ornament", 
        groupWeight: 90,
        min: 1, max: 1, 
        items: [
            { id: 'minecraft:flow_armor_trim_smithing_template', ratio: 10 },
            { id: 'minecraft:bolt_armor_trim_smithing_template', ratio: 10 },
            { id: 'minecraft:flow_banner_pattern', ratio: 10 },
            { id: 'minecraft:guster_banner_pattern', ratio: 10 },

            // // --- 3. 陶罐碎片（纯展示/建筑） ---
            // { id: 'minecraft:flow_pottery_sherd', ratio: 10 },
            // { id: 'minecraft:guster_pottery_sherd', ratio: 10 },
            // { id: 'minecraft:scrape_pottery_sherd', ratio: 10 },

            // --- 4. 试炼主题音乐唱片（收藏向） ---
            { id: 'minecraft:music_disc_precipice', ratio: 10 },
            { id: 'minecraft:music_disc_creator', ratio: 10 },
            { id: 'minecraft:music_disc_creator_music_box', ratio: 10 }
        ]
    },

    {
        groupName: "empty",
        groupWeight: 10,
        items: [
            { id: "empty" },
        ]
    },
];
// =================================================================
// 事件注册区域
// =================================================================
const trial_chambers_reward_ominous_content = [
    [trial_chambers_reward_ominous_heavy_core, 1, 1],
    [trial_chambers_reward_ominous_explore, 2, 3],
    [trial_chambers_reward_ominous_enchanted, 1, 1],
    [trial_chambers_reward_ominous_ornament, 1, 1],
];

const loot_trial_chambers_reward_ominous = [
    [trial_chambers_reward_ominous_content, 1],
];