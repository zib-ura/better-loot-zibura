const igloo_material = [

    {   
        groupName: "brewing",
        groupWeight: 50, 
        min: 2, max: 3,
        items: [
            { id: 'minecraft:redstone', ratio: 10 },
            { id: 'minecraft:sugar', ratio: 10 },
            { id: 'minecraft:glowstone_dust', ratio: 10 },
            { id: 'minecraft:magma_cream', ratio: 10 },
            { id: 'minecraft:nether_wart', ratio: 20 },
            { id: 'minecraft:blaze_rod', ratio: 10 },
            { id: 'minecraft:glistering_melon_slice', ratio: 10 },
            { id: 'minecraft:phantom_membrane', ratio: 10 },
            { id: 'minecraft:golden_carrot', ratio: 10 },
            { id: 'minecraft:turtle_scute', ratio: 10 },
            { id: 'minecraft:gunpowder', ratio: 10 },
            { id: 'minecraft:pufferfish', ratio: 10 },

            { id: 'minecraft:ender_pearl', ratio: 10 },

        ]
    },
]

const igloo_equipment = [
    {   
        groupName: "equipment",
        groupWeight: 50, 
        min: 2, max: 3,
        items: [
            { id: 'minecraft:potion', ratio: 10 , potion: "minecraft:water" , max: 3}, 
            { id: 'minecraft:book', ratio: 10 },
            { id: 'minecraft:writable_book', ratio: 10 },           
        ]
    },
]   

const igloo_product = [
    {   
        groupName: "bauble",
        groupWeight: 20, 
        min: 1, max: 1,
        items: [

            { id: 'touhou_little_maid:nimble_fabric', ratio: 10 },
            { id: 'touhou_little_maid:magic_protect_bauble', ratio: 10 },
            { id: 'touhou_little_maid:wireless_io', ratio: 10 },
            { id: 'touhou_little_maid:shrine', ratio: 1 },

        ]
    },

    {   
        groupName: "potion_positive",
        groupWeight: 10, 
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
            // 再生
            { id: 'minecraft:potion', ratio: 10, potion: "minecraft:regeneration" },
            { id: 'minecraft:splash_potion', ratio: 10, potion: "minecraft:regeneration" },
            { id: 'minecraft:lingering_potion', ratio: 10, potion: "minecraft:regeneration" },
            // 再生 (延长)
            { id: 'minecraft:potion', ratio: 10, potion: "minecraft:long_regeneration" },
            { id: 'minecraft:splash_potion', ratio: 10, potion: "minecraft:long_regeneration" },
            { id: 'minecraft:lingering_potion', ratio: 10, potion: "minecraft:long_regeneration" },
            // 再生 II
            { id: 'minecraft:potion', ratio: 10, potion: "minecraft:strong_regeneration" },
            { id: 'minecraft:splash_potion', ratio: 10, potion: "minecraft:strong_regeneration" },
            { id: 'minecraft:lingering_potion', ratio: 10, potion: "minecraft:strong_regeneration" },
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
        groupWeight: 70, 
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
            // 渗粘 (1.21+)
            { id: 'minecraft:potion', ratio: 10, potion: "minecraft:oozing" },
            { id: 'minecraft:splash_potion', ratio: 10, potion: "minecraft:oozing" },
            { id: 'minecraft:lingering_potion', ratio: 10, potion: "minecraft:oozing" },
            // 异虫 (1.21+)
            { id: 'minecraft:potion', ratio: 10, potion: "minecraft:infested" },
            { id: 'minecraft:splash_potion', ratio: 10, potion: "minecraft:infested" },
            { id: 'minecraft:lingering_potion', ratio: 10, potion: "minecraft:infested" },
            // 织网 (1.21+)
            { id: 'minecraft:potion', ratio: 10, potion: "minecraft:weaving" },
            { id: 'minecraft:splash_potion', ratio: 10, potion: "minecraft:weaving" },
            { id: 'minecraft:lingering_potion', ratio: 10, potion: "minecraft:weaving" },
            // 风袭 (1.21+)
            { id: 'minecraft:potion', ratio: 10, potion: "minecraft:wind_charged" },
            { id: 'minecraft:splash_potion', ratio: 10, potion: "minecraft:wind_charged" },
            { id: 'minecraft:lingering_potion', ratio: 10, potion: "minecraft:wind_charged" },

            // 📥 模组负面药水组
            // crabbersdelight:inky_potion
            { id: 'minecraft:potion', ratio: 10, potion: "crabbersdelight:inky_potion" },
            { id: 'minecraft:splash_potion', ratio: 10, potion: "crabbersdelight:inky_potion" },
            { id: 'minecraft:lingering_potion', ratio: 10, potion: "crabbersdelight:inky_potion" },
            // rationcraft:im_very_hungry
            { id: 'minecraft:potion', ratio: 10, potion: "rationcraft:im_very_hungry" },
            { id: 'minecraft:splash_potion', ratio: 10, potion: "rationcraft:im_very_hungry" },
            { id: 'minecraft:lingering_potion', ratio: 10, potion: "rationcraft:im_very_hungry" },
            // youkaishomecoming:aphrodisiac
            { id: 'minecraft:potion', ratio: 10, potion: "youkaishomecoming:aphrodisiac" },
            { id: 'minecraft:splash_potion', ratio: 10, potion: "youkaishomecoming:aphrodisiac" },
            { id: 'minecraft:lingering_potion', ratio: 10, potion: "youkaishomecoming:aphrodisiac" },
            // vintagedelight:dehydrated
            { id: 'minecraft:potion', ratio: 10, potion: "vintagedelight:dehydrated" },
            { id: 'minecraft:splash_potion', ratio: 10, potion: "vintagedelight:dehydrated" },
            { id: 'minecraft:lingering_potion', ratio: 10, potion: "vintagedelight:dehydrated" },
            // youkaishomecoming:hypnosis
            { id: 'minecraft:potion', ratio: 10, potion: "youkaishomecoming:hypnosis" },
            { id: 'minecraft:splash_potion', ratio: 10, potion: "youkaishomecoming:hypnosis" },
            { id: 'minecraft:lingering_potion', ratio: 10, potion: "youkaishomecoming:hypnosis" },
            // youkaishomecoming:long_hypnosis
            { id: 'minecraft:potion', ratio: 10, potion: "youkaishomecoming:long_hypnosis" },
            { id: 'minecraft:splash_potion', ratio: 10, potion: "youkaishomecoming:long_hypnosis" },
            { id: 'minecraft:lingering_potion', ratio: 10, potion: "youkaishomecoming:long_hypnosis" },
            // youkaishomecoming:long_aphrodisiac
            { id: 'minecraft:potion', ratio: 10, potion: "youkaishomecoming:long_aphrodisiac" },
            { id: 'minecraft:splash_potion', ratio: 10, potion: "youkaishomecoming:long_aphrodisiac" },
            { id: 'minecraft:lingering_potion', ratio: 10, potion: "youkaishomecoming:long_aphrodisiac" },
            // vintagedelight:long_dehydrated
            { id: 'minecraft:potion', ratio: 10, potion: "vintagedelight:long_dehydrated" },
            { id: 'minecraft:splash_potion', ratio: 10, potion: "vintagedelight:long_dehydrated" },
            { id: 'minecraft:lingering_potion', ratio: 10, potion: "vintagedelight:long_dehydrated" },
            // vintagedelight:strong_dehydrated
            { id: 'minecraft:potion', ratio: 10, potion: "vintagedelight:strong_dehydrated" },
            { id: 'minecraft:splash_potion', ratio: 10, potion: "vintagedelight:strong_dehydrated" },
            { id: 'minecraft:lingering_potion', ratio: 10, potion: "vintagedelight:strong_dehydrated" }
        ]
    }
]

const igloo_golden_apple = [
    {
        groupName: "golden_apple",
        groupWeight: 15,
        min: 1, max: 1,
        items: [
            { id: 'minecraft:golden_apple', ratio: 10 }
        ]
    },
]
// =================================================================
// 事件注册区域
// =================================================================
const igloo_content = [
    [igloo_equipment, 1, 1],
    [igloo_material, 3, 4],
    [igloo_product, 2, 2],
    [igloo_golden_apple, 1, 1],
];

const loot_igloo = [
    [igloo_content, 1],
];