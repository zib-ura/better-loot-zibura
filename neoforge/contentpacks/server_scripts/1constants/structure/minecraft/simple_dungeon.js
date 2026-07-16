// =================================================================
// 战利品表定义区域
// =================================================================

const simple_dungeon_equipment = [

    {
        groupName: "common_armors",
        groupWeight: 80,
        min: 1, max: 1, enchantChance: 1, enchantLevels: [8, 10], damage: [0.7, 0.8],
        items: [

            { id: 'immersive_armors:bone_helmet', ratio: 6 },
            { id: 'immersive_armors:bone_chestplate', ratio: 6 },
            { id: 'immersive_armors:bone_leggings', ratio: 6 },
            { id: 'immersive_armors:bone_boots', ratio: 6 },

            { id: 'immersive_armors:warrior_helmet', ratio: 2 },
            { id: 'immersive_armors:warrior_chestplate', ratio: 2 },
            { id: 'immersive_armors:warrior_leggings', ratio: 2 },
            { id: 'immersive_armors:warrior_boots', ratio: 2 },

            { id: 'minecraft:iron_helmet', ratio: 2 },
            { id: 'minecraft:iron_chestplate', ratio: 2 },
            { id: 'minecraft:iron_leggings', ratio: 2 },
            { id: 'minecraft:iron_boots', ratio: 2 },

            { id: 'minecraft:chainmail_helmet', ratio: 2 },
            { id: 'minecraft:chainmail_chestplate', ratio: 2 },
            { id: 'minecraft:chainmail_leggings', ratio: 2 },
            { id: 'minecraft:chainmail_boots', ratio: 2 },

            { id: 'immersive_armors:heavy_helmet', ratio: 2 },
            { id: 'immersive_armors:heavy_chestplate', ratio: 2 },
            { id: 'immersive_armors:heavy_leggings', ratio: 2 },
            { id: 'immersive_armors:heavy_boots', ratio: 2 },


        ]
    },

    {
        groupName: "rare_armors",
        groupWeight: 20,
        min: 1, 
        max: 1, 
        enchantChance: 0.7, 
        enchantLevels: [20, 30], 
        damage: [0.2, 0.3],
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

    {
        groupName: "common_weapons",
        groupWeight: 80,
        min: 1, 
        max: 1, 
        enchantChance: 0.5, 
        enchantLevels: [10, 15], 
        damage: [0.7, 0.8],
        items: [
            { id: 'minecraft:iron_sword', ratio: 2 },
            { id: 'minecraft:iron_axe', ratio: 2 },
            { id: 'minecraft:stone_sword', ratio: 2 },
            { id: 'minecraft:stone_axe', ratio: 2 }
        ]
    },

    {
        groupName: "rare_weapons", 
        groupWeight: 20,
        min: 1, 
        max: 1, 
        enchantChance: 0.7, 
        enchantLevels: [20, 30], 
        damage: [0.2, 0.3],
        items: [
            { id: 'minecraft:diamond_sword', ratio: 1.5 },
            { id: 'minecraft:diamond_axe', ratio: 1.5 }
        ]
    }
];

const simple_dungeon_resources_treasure = [
    // {
    //     groupName: "ore",
    //     groupWeight: 40,
    //     min: 2, max: 4, 
    //     items: [
    //         { id: 'minecraft:iron_ingot', ratio: 10 },
    //         { id: 'minecraft:coal', ratio: 15 },
    //         { id: 'minecraft:copper_ingot', ratio: 10 },
    //         { id: 'minecraft:lapis_lazuli', ratio: 10 },
    //         { id: 'minecraft:redstone', ratio: 10 },
    //         { id: 'create:zinc_ingot', ratio: 10 },

    //     ]
    // },

    {
        groupName: "drops",
        groupWeight: 90,
        min: 3, max: 4,
        items: [
            { id: 'minecraft:bone', ratio: 10 },
            { id: 'minecraft:gunpowder', ratio: 10 },
            { id: 'minecraft:rotten_flesh', ratio: 10 },
            { id: 'minecraft:string', ratio: 10 },
            { id: 'minecraft:spider_eye', ratio: 10 },
            // { id: 'minecraft:ender_pearl', ratio: 10 },


        ]
    },

    {
        groupName: "treasure",
        groupWeight: 10,
        min: 1, max: 2,
        items: [
            { id: 'minecraft:diamond', ratio: 5 },
            // { id: 'minecraft:emerald', ratio: 20 },
            { id: 'minecraft:gold_ingot', ratio: 10 },
            
            { id: 'minecraft:golden_apple', ratio: 5 },
            { id: 'trailandtales_delight:golden_lantern_fruit', ratio: 5 },
            { id: 'minecraft:enchanted_golden_apple', ratio: 5 },
            { id: 'mynethersdelight:enchanted_golden_egg', ratio: 5 },


        ]
    },
];

const simple_dungeon_supply = [
    // {
    //     groupName: "ore",
    //     groupWeight: 40,
    //     min: 2, max: 4, 
    //     items: [
    //         { id: 'minecraft:iron_ingot', ratio: 10 },
    //         { id: 'minecraft:coal', ratio: 15 },
    //         { id: 'minecraft:copper_ingot', ratio: 10 },
    //         { id: 'minecraft:lapis_lazuli', ratio: 10 },
    //         { id: 'minecraft:redstone', ratio: 10 },
    //         { id: 'create:zinc_ingot', ratio: 10 },

    //     ]
    // },

    {
        groupName: "supply",
        groupWeight: 50,
        min: 3, max: 4,
        items: [
            { id: 'minecraft:torch', ratio: 10 },
            { id: 'minecraft:stick', ratio: 10 },
        ]
    },
];

const simple_dungeon_potion = [

    {   
        groupName: "potion_positive",
        groupWeight: 50, 
        min: 1, max: 1,
        items: [
            // 瞬间治疗
            { id: 'minecraft:potion', ratio: 10, potion: "minecraft:healing" },
            { id: 'minecraft:splash_potion', ratio: 10, potion: "minecraft:healing" },
            { id: 'minecraft:lingering_potion', ratio: 10, potion: "minecraft:healing" },
            // 瞬间治疗 II
            { id: 'minecraft:potion', ratio: 10, potion: "minecraft:strong_healing" },
            { id: 'minecraft:splash_potion', ratio: 10, potion: "minecraft:strong_healing" },
            { id: 'minecraft:lingering_potion', ratio: 10, potion: "minecraft:strong_healing" },
            // // 再生
            // { id: 'minecraft:potion', ratio: 10, potion: "minecraft:regeneration" },
            // { id: 'minecraft:splash_potion', ratio: 10, potion: "minecraft:regeneration" },
            // { id: 'minecraft:lingering_potion', ratio: 10, potion: "minecraft:regeneration" },
            // // 再生 (延长)
            // { id: 'minecraft:potion', ratio: 10, potion: "minecraft:long_regeneration" },
            // { id: 'minecraft:splash_potion', ratio: 10, potion: "minecraft:long_regeneration" },
            // { id: 'minecraft:lingering_potion', ratio: 10, potion: "minecraft:long_regeneration" },
            // // 再生 II
            // { id: 'minecraft:potion', ratio: 10, potion: "minecraft:strong_regeneration" },
            // { id: 'minecraft:splash_potion', ratio: 10, potion: "minecraft:strong_regeneration" },
            // { id: 'minecraft:lingering_potion', ratio: 10, potion: "minecraft:strong_regeneration" },
            // 力量
            { id: 'minecraft:potion', ratio: 10, potion: "minecraft:strength" },
            { id: 'minecraft:splash_potion', ratio: 10, potion: "minecraft:strength" },
            { id: 'minecraft:lingering_potion', ratio: 10, potion: "minecraft:strength" },
            // 力量 (延长)
            { id: 'minecraft:potion', ratio: 10, potion: "minecraft:long_strength" },
            { id: 'minecraft:splash_potion', ratio: 10, potion: "minecraft:long_strength" },
            { id: 'minecraft:lingering_potion', ratio: 10, potion: "minecraft:long_strength" },
            // 力量 II
            { id: 'minecraft:potion', ratio: 10, potion: "minecraft:strong_strength" },
            { id: 'minecraft:splash_potion', ratio: 10, potion: "minecraft:strong_strength" },
            { id: 'minecraft:lingering_potion', ratio: 10, potion: "minecraft:strong_strength" },
            // 迅捷
            { id: 'minecraft:potion', ratio: 10, potion: "minecraft:swiftness" },
            { id: 'minecraft:splash_potion', ratio: 10, potion: "minecraft:swiftness" },
            { id: 'minecraft:lingering_potion', ratio: 10, potion: "minecraft:swiftness" },
            // 迅捷 (延长)
            { id: 'minecraft:potion', ratio: 10, potion: "minecraft:long_swiftness" },
            { id: 'minecraft:splash_potion', ratio: 10, potion: "minecraft:long_swiftness" },
            { id: 'minecraft:lingering_potion', ratio: 10, potion: "minecraft:long_swiftness" },
            // 迅捷 II
            { id: 'minecraft:potion', ratio: 10, potion: "minecraft:strong_swiftness" },
            { id: 'minecraft:splash_potion', ratio: 10, potion: "minecraft:strong_swiftness" },
            { id: 'minecraft:lingering_potion', ratio: 10, potion: "minecraft:strong_swiftness" },
            // 抗火
            { id: 'minecraft:potion', ratio: 10, potion: "minecraft:fire_resistance" },
            { id: 'minecraft:splash_potion', ratio: 10, potion: "minecraft:fire_resistance" },
            { id: 'minecraft:lingering_potion', ratio: 10, potion: "minecraft:fire_resistance" },
            // 抗火 (延长)
            { id: 'minecraft:potion', ratio: 10, potion: "minecraft:long_fire_resistance" },
            { id: 'minecraft:splash_potion', ratio: 10, potion: "minecraft:long_fire_resistance" },
            { id: 'minecraft:lingering_potion', ratio: 10, potion: "minecraft:long_fire_resistance" },
            // 夜视
            { id: 'minecraft:potion', ratio: 10, potion: "minecraft:night_vision" },
            { id: 'minecraft:splash_potion', ratio: 10, potion: "minecraft:night_vision" },
            { id: 'minecraft:lingering_potion', ratio: 10, potion: "minecraft:night_vision" },
            // 夜视 (延长)
            { id: 'minecraft:potion', ratio: 10, potion: "minecraft:long_night_vision" },
            { id: 'minecraft:splash_potion', ratio: 10, potion: "minecraft:long_night_vision" },
            { id: 'minecraft:lingering_potion', ratio: 10, potion: "minecraft:long_night_vision" },
            // 隐形
            { id: 'minecraft:potion', ratio: 10, potion: "minecraft:invisibility" },
            { id: 'minecraft:splash_potion', ratio: 10, potion: "minecraft:invisibility" },
            { id: 'minecraft:lingering_potion', ratio: 10, potion: "minecraft:invisibility" },
            // 隐形 (延长)
            { id: 'minecraft:potion', ratio: 10, potion: "minecraft:long_invisibility" },
            { id: 'minecraft:splash_potion', ratio: 10, potion: "minecraft:long_invisibility" },
            { id: 'minecraft:lingering_potion', ratio: 10, potion: "minecraft:long_invisibility" },
            // 水下呼吸
            { id: 'minecraft:potion', ratio: 10, potion: "minecraft:water_breathing" },
            { id: 'minecraft:splash_potion', ratio: 10, potion: "minecraft:water_breathing" },
            { id: 'minecraft:lingering_potion', ratio: 10, potion: "minecraft:water_breathing" },
            // 水下呼吸 (延长)
            { id: 'minecraft:potion', ratio: 10, potion: "minecraft:long_water_breathing" },
            { id: 'minecraft:splash_potion', ratio: 10, potion: "minecraft:long_water_breathing" },
            { id: 'minecraft:lingering_potion', ratio: 10, potion: "minecraft:long_water_breathing" },
            // 跳跃提升
            { id: 'minecraft:potion', ratio: 10, potion: "minecraft:leaping" },
            { id: 'minecraft:splash_potion', ratio: 10, potion: "minecraft:leaping" },
            { id: 'minecraft:lingering_potion', ratio: 10, potion: "minecraft:leaping" },
            // 跳跃提升 (延长)
            { id: 'minecraft:potion', ratio: 10, potion: "minecraft:long_leaping" },
            { id: 'minecraft:splash_potion', ratio: 10, potion: "minecraft:long_leaping" },
            { id: 'minecraft:lingering_potion', ratio: 10, potion: "minecraft:long_leaping" },
            // 跳跃提升 II
            { id: 'minecraft:potion', ratio: 10, potion: "minecraft:strong_leaping" },
            { id: 'minecraft:splash_potion', ratio: 10, potion: "minecraft:strong_leaping" },
            { id: 'minecraft:lingering_potion', ratio: 10, potion: "minecraft:strong_leaping" },
            // 缓降
            { id: 'minecraft:potion', ratio: 10, potion: "minecraft:slow_falling" },
            { id: 'minecraft:splash_potion', ratio: 10, potion: "minecraft:slow_falling" },
            { id: 'minecraft:lingering_potion', ratio: 10, potion: "minecraft:slow_falling" },
            // 缓降 (延长)
            { id: 'minecraft:potion', ratio: 10, potion: "minecraft:long_slow_falling" },
            { id: 'minecraft:splash_potion', ratio: 10, potion: "minecraft:long_slow_falling" },
            { id: 'minecraft:lingering_potion', ratio: 10, potion: "minecraft:long_slow_falling" },
            // 幸运
            { id: 'minecraft:potion', ratio: 10, potion: "minecraft:luck" },
            { id: 'minecraft:splash_potion', ratio: 10, potion: "minecraft:luck" },
            { id: 'minecraft:lingering_potion', ratio: 10, potion: "minecraft:luck" }
        ]
    },


    {   
        groupName: "potion_negative",
        groupWeight: 50, 
        min: 1, max: 1,
        items: [
            // 瞬间伤害
            { id: 'minecraft:potion', ratio: 10, potion: "minecraft:harming" },
            { id: 'minecraft:splash_potion', ratio: 10, potion: "minecraft:harming" },
            { id: 'minecraft:lingering_potion', ratio: 10, potion: "minecraft:harming" },
            // 瞬间伤害 II
            { id: 'minecraft:potion', ratio: 10, potion: "minecraft:strong_harming" },
            { id: 'minecraft:splash_potion', ratio: 10, potion: "minecraft:strong_harming" },
            { id: 'minecraft:lingering_potion', ratio: 10, potion: "minecraft:strong_harming" },
            // 中毒
            { id: 'minecraft:potion', ratio: 10, potion: "minecraft:poison" },
            { id: 'minecraft:splash_potion', ratio: 10, potion: "minecraft:poison" },
            { id: 'minecraft:lingering_potion', ratio: 10, potion: "minecraft:poison" },
            // 中毒 (延长)
            { id: 'minecraft:potion', ratio: 10, potion: "minecraft:long_poison" },
            { id: 'minecraft:splash_potion', ratio: 10, potion: "minecraft:long_poison" },
            { id: 'minecraft:lingering_potion', ratio: 10, potion: "minecraft:long_poison" },
            // 中毒 II
            { id: 'minecraft:potion', ratio: 10, potion: "minecraft:strong_poison" },
            { id: 'minecraft:splash_potion', ratio: 10, potion: "minecraft:strong_poison" },
            { id: 'minecraft:lingering_potion', ratio: 10, potion: "minecraft:strong_poison" },
            // 缓慢
            { id: 'minecraft:potion', ratio: 10, potion: "minecraft:slowness" },
            { id: 'minecraft:splash_potion', ratio: 10, potion: "minecraft:slowness" },
            { id: 'minecraft:lingering_potion', ratio: 10, potion: "minecraft:slowness" },
            // 缓慢 (延长)
            { id: 'minecraft:potion', ratio: 10, potion: "minecraft:long_slowness" },
            { id: 'minecraft:splash_potion', ratio: 10, potion: "minecraft:long_slowness" },
            { id: 'minecraft:lingering_potion', ratio: 10, potion: "minecraft:long_slowness" },
            // 缓慢 II
            { id: 'minecraft:potion', ratio: 10, potion: "minecraft:strong_slowness" },
            { id: 'minecraft:splash_potion', ratio: 10, potion: "minecraft:strong_slowness" },
            { id: 'minecraft:lingering_potion', ratio: 10, potion: "minecraft:strong_slowness" },
            // 虚弱
            { id: 'minecraft:potion', ratio: 10, potion: "minecraft:weakness" },
            { id: 'minecraft:splash_potion', ratio: 10, potion: "minecraft:weakness" },
            { id: 'minecraft:lingering_potion', ratio: 10, potion: "minecraft:weakness" },
            // 虚弱 (延长)
            { id: 'minecraft:potion', ratio: 10, potion: "minecraft:long_weakness" },
            { id: 'minecraft:splash_potion', ratio: 10, potion: "minecraft:long_weakness" },
            { id: 'minecraft:lingering_potion', ratio: 10, potion: "minecraft:long_weakness" },
            // 神龟药水
            { id: 'minecraft:potion', ratio: 10, potion: "minecraft:turtle_master" },
            { id: 'minecraft:splash_potion', ratio: 10, potion: "minecraft:turtle_master" },
            { id: 'minecraft:lingering_potion', ratio: 10, potion: "minecraft:turtle_master" },
            // 神龟药水 (延长)
            { id: 'minecraft:potion', ratio: 10, potion: "minecraft:long_turtle_master" },
            { id: 'minecraft:splash_potion', ratio: 10, potion: "minecraft:long_turtle_master" },
            { id: 'minecraft:lingering_potion', ratio: 10, potion: "minecraft:long_turtle_master" },
            // 神龟药水 II
            { id: 'minecraft:potion', ratio: 10, potion: "minecraft:strong_turtle_master" },
            { id: 'minecraft:splash_potion', ratio: 10, potion: "minecraft:strong_turtle_master" },
            { id: 'minecraft:lingering_potion', ratio: 10, potion: "minecraft:strong_turtle_master" },
            // // 渗粘 (1.21+)
            // { id: 'minecraft:potion', ratio: 10, potion: "minecraft:oozing" },
            // { id: 'minecraft:splash_potion', ratio: 10, potion: "minecraft:oozing" },
            // { id: 'minecraft:lingering_potion', ratio: 10, potion: "minecraft:oozing" },
            // // 异虫 (1.21+)
            // { id: 'minecraft:potion', ratio: 10, potion: "minecraft:infested" },
            // { id: 'minecraft:splash_potion', ratio: 10, potion: "minecraft:infested" },
            // { id: 'minecraft:lingering_potion', ratio: 10, potion: "minecraft:infested" },
            // // 织网 (1.21+)
            // { id: 'minecraft:potion', ratio: 10, potion: "minecraft:weaving" },
            // { id: 'minecraft:splash_potion', ratio: 10, potion: "minecraft:weaving" },
            // { id: 'minecraft:lingering_potion', ratio: 10, potion: "minecraft:weaving" },
            // // 风袭 (1.21+)
            // { id: 'minecraft:potion', ratio: 10, potion: "minecraft:wind_charged" },
            // { id: 'minecraft:splash_potion', ratio: 10, potion: "minecraft:wind_charged" },
            // { id: 'minecraft:lingering_potion', ratio: 10, potion: "minecraft:wind_charged" },

            // // 📥 模组负面药水组
            // // crabbersdelight:inky_potion
            // { id: 'minecraft:potion', ratio: 10, potion: "crabbersdelight:inky_potion" },
            // { id: 'minecraft:splash_potion', ratio: 10, potion: "crabbersdelight:inky_potion" },
            // { id: 'minecraft:lingering_potion', ratio: 10, potion: "crabbersdelight:inky_potion" },
            // // rationcraft:im_very_hungry
            // { id: 'minecraft:potion', ratio: 10, potion: "rationcraft:im_very_hungry" },
            // { id: 'minecraft:splash_potion', ratio: 10, potion: "rationcraft:im_very_hungry" },
            // { id: 'minecraft:lingering_potion', ratio: 10, potion: "rationcraft:im_very_hungry" },
            // // youkaishomecoming:aphrodisiac
            // { id: 'minecraft:potion', ratio: 10, potion: "youkaishomecoming:aphrodisiac" },
            // { id: 'minecraft:splash_potion', ratio: 10, potion: "youkaishomecoming:aphrodisiac" },
            // { id: 'minecraft:lingering_potion', ratio: 10, potion: "youkaishomecoming:aphrodisiac" },
            // // vintagedelight:dehydrated
            // { id: 'minecraft:potion', ratio: 10, potion: "vintagedelight:dehydrated" },
            // { id: 'minecraft:splash_potion', ratio: 10, potion: "vintagedelight:dehydrated" },
            // { id: 'minecraft:lingering_potion', ratio: 10, potion: "vintagedelight:dehydrated" },
            // // youkaishomecoming:hypnosis
            // { id: 'minecraft:potion', ratio: 10, potion: "youkaishomecoming:hypnosis" },
            // { id: 'minecraft:splash_potion', ratio: 10, potion: "youkaishomecoming:hypnosis" },
            // { id: 'minecraft:lingering_potion', ratio: 10, potion: "youkaishomecoming:hypnosis" },
            // // youkaishomecoming:long_hypnosis
            // { id: 'minecraft:potion', ratio: 10, potion: "youkaishomecoming:long_hypnosis" },
            // { id: 'minecraft:splash_potion', ratio: 10, potion: "youkaishomecoming:long_hypnosis" },
            // { id: 'minecraft:lingering_potion', ratio: 10, potion: "youkaishomecoming:long_hypnosis" },
            // // youkaishomecoming:long_aphrodisiac
            // { id: 'minecraft:potion', ratio: 10, potion: "youkaishomecoming:long_aphrodisiac" },
            // { id: 'minecraft:splash_potion', ratio: 10, potion: "youkaishomecoming:long_aphrodisiac" },
            // { id: 'minecraft:lingering_potion', ratio: 10, potion: "youkaishomecoming:long_aphrodisiac" },
            // // vintagedelight:long_dehydrated
            // { id: 'minecraft:potion', ratio: 10, potion: "vintagedelight:long_dehydrated" },
            // { id: 'minecraft:splash_potion', ratio: 10, potion: "vintagedelight:long_dehydrated" },
            // { id: 'minecraft:lingering_potion', ratio: 10, potion: "vintagedelight:long_dehydrated" },
            // // vintagedelight:strong_dehydrated
            // { id: 'minecraft:potion', ratio: 10, potion: "vintagedelight:strong_dehydrated" },
            // { id: 'minecraft:splash_potion', ratio: 10, potion: "vintagedelight:strong_dehydrated" },
            // { id: 'minecraft:lingering_potion', ratio: 10, potion: "vintagedelight:strong_dehydrated" }
        ]
    },
    {   
        groupName: "empty",
        groupWeight: 50, 
        items: [
            { id: 'empty'},
        ]
    },
]

const simple_dungeon_enchanted_book = [
    {
        groupName: "simple_dungeon",
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
        groupName: "empty",
        groupWeight: 50,
        items: [
            { "id": "empty" },
        ]
    },
];
// =================================================================
// 事件注册区域
// =================================================================
const simple_dungeon_content = [
    [simple_dungeon_equipment, 1, 2],
    [simple_dungeon_resources_treasure, 3, 4],
    [shipwreck_treasure_music_discs, 1, 1],
    [shipwreck_treasure_smithing_templates_common, 1, 1],
    [simple_dungeon_supply, 3, 4],
    [simple_dungeon_potion, 1, 1],
    [simple_dungeon_enchanted_book, 1, 1],
];

const loot_simple_dungeon = [
    [simple_dungeon_content, 1],
];