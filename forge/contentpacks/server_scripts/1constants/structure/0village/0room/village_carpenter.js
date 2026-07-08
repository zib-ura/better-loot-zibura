// ==========================================
// 木匠配置区域 (Carpenter)
// ==========================================

// 1. 定义每个群系对应的木材类型映射表
const carpenterBiomeWoods = {
    plains: ["oak"],                                     
    savanna: ["acacia"],                               
    snowy: ["spruce"],                                   
    taiga: ["spruce"],                                  
    swamp: ["oak"],                          
    mangrove: ["mangrove"],                           
    jungle: ["jungle", "bamboo"],                       
    dark_forest: ["dark_oak"],                           
    cherry: ["cherry"],                                 
};

/**
 * 自动化生成木匠群系专属物品组的工厂函数（通用完全体）
 * @param {string} biomeName - 群系名称
 * @param {string} groupName - 组名
 * @param {Array<Object>} productTemplates - 产物模版列表
 * @param {Object} config - 配置项控制，包含 { groupWeight, min, max }
 */
function createCarpenterBiomeGroup(biomeName, groupName, productTemplates, config) {
    const woods = carpenterBiomeWoods[biomeName] || [];
    const items = [];

    // 默认配置兜底
    const groupWeight = config.groupWeight !== undefined ? config.groupWeight : 20;
    const min = config.min !== undefined ? config.min : 2;
    const max = config.max !== undefined ? config.max : 3;

    // 遍历该群系拥有的每种木材类型
    woods.forEach(function(woodType) {
        productTemplates.forEach(function(template) {
            if (template.id.indexOf('[WOOD]') !== -1) {
                let itemId = template.id.replace('[WOOD]', woodType);
                
                if (woodType === "bamboo") {
                    itemId = itemId.replace('bamboo_log', 'bamboo_block'); 
                }
                
                const itemObject = {
                    id: itemId,
                    ratio: template.ratio
                };
                if (template.max !== undefined) {
                    itemObject.max = template.max;
                }
                items.push(itemObject);
            }
        });
    });

    // 处理不需要木材前缀的统一固定产物
    productTemplates.forEach(function(template) {
        if (template.id.indexOf('[WOOD]') === -1) {
            if (woods.length > 0) {
                const copy = Object.assign({}, template);
                Array.prototype.push.apply(items, [copy]);
            }
        }
    });
    

    return [
        {
            groupName: groupName,
            groupWeight: groupWeight,
            min: min, max: max,
            items: items
        }
    ];
}

// ==========================================
// 木匠核心配置：定义统一的模版
// ==========================================

// 1.1 功能方块模版
const carpenterUtilityTemplates = [
    { id: 'minecraft:chest', ratio: 10 },
    { id: 'minecraft:crafting_table', ratio: 10 },
    { id: 'lolmcv:[WOOD]_chest', ratio: 10 },
    { id: 'minecraft:crafting_table', ratio: 10 },
    { id: 'minecraft:barrel', ratio: 10 },
    { id: 'minecraft:loom', ratio: 10 },
    { id: 'minecraft:fletching_table', ratio: 10 },
    { id: 'minecraft:cartography_table', ratio: 10 },
    { id: 'minecraft:composter', ratio: 10 },
    // { id: 'minecraft:bookshelf', ratio: 10 },
    { id: 'minecraft:chiseled_bookshelf', ratio: 10 },
    { id: 'minecraft:lectern', ratio: 10 },
    { id: 'minecraft:bowl', ratio: 10 },
    { id: 'minecraft:ladder', ratio: 10 },
    { id: 'farm_and_charm:sturdy_ladder', ratio: 10 },
    { id: 'woodenshears:wooden_shears', ratio: 10 },
    { id: 'farmersdelight:wooden_basket', ratio: 10 },
    { id: 'kaleidoscope_cookery:fruit_basket', ratio: 10 },
];

// 1.2 家具模版
const carpenterFurnitureTemplates = [
    // { id: 'minecraft:[WOOD]_stairs', ratio: 10 },
    // { id: 'minecraft:[WOOD]_slab', ratio: 10 },
    // { id: 'minecraft:[WOOD]_door', ratio: 10 },
    // { id: 'minecraft:[WOOD]_trapdoor', ratio: 10 },
    // { id: 'minecraft:[WOOD]_fence', ratio: 10 },
    // { id: 'minecraft:[WOOD]_sign', ratio: 10 },
    { id: 'kaleidoscope_cookery:table_[WOOD]', ratio: 10 },
    { id: 'kaleidoscope_cookery:chair_[WOOD]', ratio: 10 },
    { id: 'kaleidoscope_cookery:cook_stool_[WOOD]', ratio: 10 },
    { id: 'farmersdelight:[WOOD]_cabinet', ratio: 10 },
    // { id: 'brewery:sideboard', ratio: 10 },
    // { id: 'brewery:cabinet', ratio: 10 },
    // { id: 'bakery:cupcake_display', ratio: 10 },
    // { id: 'bakery:cake_display', ratio: 10 },
    // { id: 'bakery:wall_display', ratio: 10 },
    // { id: 'bakery:cabinet', ratio: 10 },
    // { id: 'brewery:table', ratio: 10 },
    // { id: 'farm_and_charm:water_trough', ratio: 10 },
    // { id: 'bakery:breadbox', ratio: 10 },
    { id: 'kaleidoscope_chinesefood:mooncake_mold', ratio: 10 },
    { id: 'kaleidoscope_tavern:stepladder', ratio: 10 },
    { id: 'kaleidoscope_chinesefood:bowl_stack', ratio: 10 },
    { id: 'kaleidoscope_cookery:kitchenware_racks', ratio: 10 },
    { id: 'youkaisfeasts:[WOOD]_dining_table', ratio: 10 },

];
// 2. 基础原料模版
const carpenterMaterialTemplates = [
    { id: 'minecraft:[WOOD]_log', ratio: 15 },
    { id: 'minecraft:[WOOD]_planks', ratio: 20 },
    { id: 'minecraft:stick', ratio: 15 }
];

// 3. 树苗模板
const carpenterSaplingTemplates = [
    { id: 'minecraft:[WOOD]_sapling', ratio: 10 }
];

// 4. 公共池模板（不再需要单独作为独立数组声明）
const carpenterAxesTemplates = [
    { id: 'minecraft:stone_axe', ratio: 30, damage: [0.3, 0.4] },
    { id: 'minecraft:iron_axe', ratio: 10, damage: [0.5, 0.6] }
];

const carpenterSawmillTemplates = [
    { id: 'sawmill:sawmill', ratio: 10 }
];

// =================================================================
// 木匠事件注册区域（自动化动态生成）
// =================================================================

const carpenterBiomes = ["plains", "savanna", "snowy", "taiga", "swamp", "jungle", "dark_forest", "cherry", "mangrove"];
const carpenterData = {};

/**
 * 动态获取组合好的群系总配置
 */
function getCarpenterConfig(biomeName) {
    // 1. 动态生成群系专属池（传入自定义的权重与数量控制）
    const utility   = createCarpenterBiomeGroup(biomeName, "utility",   carpenterUtilityTemplates, { groupWeight: 20, min: 1, max: 1 });
    const furniture = createCarpenterBiomeGroup(biomeName, "furniture", carpenterFurnitureTemplates, { groupWeight: 20, min: 1, max: 1 });
    const materials = createCarpenterBiomeGroup(biomeName, "materials", carpenterMaterialTemplates, { groupWeight: 20, min: 2, max: 3 });
    const sapling   = createCarpenterBiomeGroup(biomeName, "sapling",   carpenterSaplingTemplates,   { groupWeight: 20, min: 1, max: 2 });

    // 2. 直接用统一函数生成公共池（传入公共池特有的权重 15 和 10）
    const axes      = createCarpenterBiomeGroup(biomeName, "axes",      carpenterAxesTemplates,      { groupWeight: 15, min: 1, max: 1 });
    const sawmills  = createCarpenterBiomeGroup(biomeName, "sawmills",  carpenterSawmillTemplates,  { groupWeight: 10, min: 1, max: 1 });

    // 3. 返回组合结果
    return [
        [utility, 5, 5],
        [furniture, 5, 5],
        [materials, 2, 3],
        [sapling,   1, 2],
        [axes,      1, 1],
        [sawmills,  1, 1]
    ];
}

// 执行自动化构建
carpenterBiomes.forEach(biome => {
    carpenterData[biome] = getCarpenterConfig(biome);
});