const shipwreck_supply_crops = [
    // =================================================================
    // 组 1: 新大陆作物与种子 (New World Crops & Seeds)
    // 模拟从远方土地带回的奇异植物
    // =================================================================
    {
        groupName: "common",
        groupWeight: 60,
        min: 2, max: 3,
        items: [
            { reference: 'lootjs:potato_and_potato_block', ratio: 10 },
            { reference: 'lootjs:carrot_and_carrot_block', ratio: 10 },
            { reference: 'lootjs:beetroot_and_beetroot_block', ratio: 10 },
            { reference: 'lootjs:onion_and_onion_block', ratio: 10 },
            { reference: 'lootjs:cabbage_and_cabbage_block', ratio: 10 },

            { reference: 'lootjs:cod_and_cod_block', ratio: 10 },
            { reference: 'lootjs:salmon_and_salmon_block', ratio: 10 },
            { id: 'rationcraft:crate_of_hardtack', ratio: 5 },
            { id: 'rationcraft:salted_pork', ratio: 10 },
            { id: 'voidlessframework:salt_block', ratio: 10 },
            { reference: 'lootjs:sugar_and_sugar_block', ratio: 10 },

            { reference: 'lootjs:poisonous_potato_and_poisonous_potato_block', ratio: 10 },

            { id: 'kitchenkarrot:rum', ratio: 10 },
            { id: 'brewinandchewin:flaxen_cheese_wheel', ratio: 10 },

        ]
    },

    {
        groupName: "rare",
        groupWeight: 30,
        min: 2, max: 3,
        items: [
            // --- 香料与调料 ---
            { id: 'barbequesdelight:chili_powder', ratio: 3 },
            { id: 'barbequesdelight:pepper_powder', ratio: 3 },
            { id: 'barbequesdelight:cumin_powder', ratio: 3 },

            { id: 'minecraft:cocoa_beans', ratio: 10 },
            { id: 'minecraft:sugar_cane', ratio: 10 },
            { id: 'kaleidoscope_cookery:red_chili', ratio: 10 },
            { id: 'farm_and_charm:kernels', ratio: 10 },
            { id: 'rusticdelight:cotton_seeds', ratio: 10 },            
            { id: 'youkaisfeasts:tea_seeds', ratio: 10 },            
            { id: 'rusticdelight:coffee_beans', ratio: 10 },            


        ]
    },
    // // =================================================================
    // // 组 3: 压舱货与补给 (Ballast & General Supplies)
    // // 船上携带的普通补给和资源
    // // =================================================================
    // {
    //     groupName: "Ballast & General Supplies",
    //     groupWeight: 30,
    //     min: 2, max: 6,
    //     items: [
    //         { id: 'minecraft:paper', ratio: 8 },
    //         { id: 'minecraft:glass_bottle', ratio: 10 },
    //         { id: 'minecraft:string', ratio: 12 },
    //         { id: 'minecraft:lantern', ratio: 5 },
    //         { id: 'minecraft:coal', ratio: 8 },
    //         { id: 'farmersdelight:rope', ratio: 10 },
    //         { id: 'farmersdelight:canvas', ratio: 8 },      // 帆布
    //     ]
    // }
];

// =================================================================
// 事件注册区域
// =================================================================
const shipwreck_supply_content = [
    [shipwreck_supply_crops, 10, 11],
    [seagrass, 4, 5],
];

const loot_shipwreck_supply = [
    [shipwreck_supply_content, 1],
];