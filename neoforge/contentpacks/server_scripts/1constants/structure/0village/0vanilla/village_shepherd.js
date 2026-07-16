const biomeColors = {
    // === 独立保留的群系 ===
    desert: ["white", "green", "cyan", "blue", "lime"],          
    snowy: ["white", "red", "blue"],                            
    taiga: ["white", "red", "blue", "purple"],             
    swamp: ["light_blue", "white"],                                 
    cherry_grove: ["pink", "white"],                          
    jungle: ["brown", "white"],                                 

    // === 大组 A：红、黄、蓝 ===
    // 包含：平原、向日葵平原、繁花森林、草甸
    red_yellow_blue: ["red", "yellow", "blue", "white"],
    plains: ["red", "yellow", "blue", "white"],
    
    // === 大组 B：红、黄、橙 ===
    // 包含：稀树草原、普通森林（针叶林已独立出去）
    red_yellow_orange: ["red", "yellow", "orange", "white"],

    // === 兜底配置 ===
    other: ["white"]                                          
};
/**
 * 自动化生成群系专属物品组的工厂函数
 * @param {string} biomeName - 群系名称（用于变量名或标识）
 * @param {string} groupName - 组名，例如 "wool" 或 "dye"
 * @param {Array<Object>} productTemplates - 产物模版列表
 */
function createBiomeProductGroup(biomeName, groupName, productTemplates) {
    const colors = biomeColors[biomeName] || [];
    const items = [];

    // 遍历该群系拥有的每种颜色
    colors.forEach(function(color) {
        productTemplates.forEach(function(template) {
            // 如果模版是纯颜色相关的（带有 [COLOR] 占位符）
            if (template.id.indexOf('[COLOR]') !== -1) {
                const itemObject = {
                    id: template.id.replace('[COLOR]', color),
                    ratio: template.ratio
                };
                // 如果模版有数量限制（如床），则保留
                if (template.max !== undefined) {
                    itemObject.max = template.max;
                }
                items.push(itemObject);
            }
        });
    });

    // 处理不需要颜色前缀的统一固定产物（例如画）
    productTemplates.forEach(function(template) {
        if (template.id.indexOf('[COLOR]') === -1) {
            // 只有当群系不是“无”时才添加
            if (colors.length > 0) {
                // 使用 Object.assign 复制对象，避免直接修改模版
                const copy = Object.assign({}, template);
                // 使用 apply 将单项或多项安全地推入 items 数组
                Array.prototype.push.apply(items, [copy]);
            }
        }
    });
    if (biomeName === "plains" && groupName === "wool") { // 举例：如果是平原群系
        items.push(
            { id: "brewery:patterned_wool", ratio: 10 },
            { id: "brewery:patterned_carpet", ratio: 10 },

        );
    }
    // 返回最终的结构
    return [
        {
            groupName: groupName,
            groupWeight: 20,
            min: 2, max: 4,
            items: items
        }
    ];
}

// ==========================================
// 核心配置：定义统一的产物模版
// 使用 [COLOR] 作为颜色占位符
// ==========================================

// 羊毛/织物类统一模版
const woolTemplates = [
    { id: 'minecraft:[COLOR]_wool', ratio: 10 },
    { id: 'minecraft:[COLOR]_banner', ratio: 10 },
    { id: 'minecraft:[COLOR]_carpet', ratio: 10 },
    { id: 'supplementaries:bunting_[COLOR]', ratio: 10 },
    { id: 'supplementaries:flag_[COLOR]', ratio: 10 },
    { id: 'minecraft:[COLOR]_bed', ratio: 10, max: 2 },
    { id: 'minecraft:painting', ratio: 10 }, 
    { id: 'touhou_little_maid:maid_bed', ratio: 10, max: 1 },
];

// 染料类统一模版 (顺便附赠染料的自动化配置)
const dyeTemplates = [
    { id: 'minecraft:[COLOR]_dye', ratio: 10 }
];


// ==========================================
// 自动生成各个群系的羊毛版本
// ==========================================
const desert_shepherd_products_wool       = createBiomeProductGroup("desert", "wool", woolTemplates);
const desert_shepherd_products_dye        = createBiomeProductGroup("desert", "dye", dyeTemplates);

const snowy_shepherd_products_wool        = createBiomeProductGroup("snowy", "wool", woolTemplates);
const snowy_shepherd_products_dye         = createBiomeProductGroup("snowy", "dye", dyeTemplates);

const taiga_shepherd_products_wool        = createBiomeProductGroup("taiga", "wool", woolTemplates);
const taiga_shepherd_products_dye         = createBiomeProductGroup("taiga", "dye", dyeTemplates);

const swamp_shepherd_products_wool        = createBiomeProductGroup("swamp", "wool", woolTemplates);
const swamp_shepherd_products_dye         = createBiomeProductGroup("swamp", "dye", dyeTemplates);

const cherry_grove_shepherd_products_wool = createBiomeProductGroup("cherry_grove", "wool", woolTemplates);
const cherry_grove_shepherd_products_dye  = createBiomeProductGroup("cherry_grove", "dye", dyeTemplates);

const plains_shepherd_products_wool       = createBiomeProductGroup("plains", "wool", woolTemplates);
const plains_shepherd_products_dye        = createBiomeProductGroup("plains", "dye", dyeTemplates);

const jungle_shepherd_products_wool       = createBiomeProductGroup("jungle", "wool", woolTemplates);
const jungle_shepherd_products_dye        = createBiomeProductGroup("jungle", "dye", dyeTemplates);

// 2. 两大合并色组
const ryb_shepherd_products_wool          = createBiomeProductGroup("red_yellow_blue", "wool", woolTemplates);
const ryb_shepherd_products_dye           = createBiomeProductGroup("red_yellow_blue", "dye", dyeTemplates);

const ryo_shepherd_products_wool          = createBiomeProductGroup("red_yellow_orange", "wool", woolTemplates);
const ryo_shepherd_products_dye           = createBiomeProductGroup("red_yellow_orange", "dye", dyeTemplates);

// 3. 兜底
const other_shepherd_products_wool        = createBiomeProductGroup("other", "wool", woolTemplates);
const other_shepherd_products_dye         = createBiomeProductGroup("other", "dye", dyeTemplates);

const village_shepherd_equipment = [
    {        
        groupName: "equipment",
        groupWeight: 20,
        min: 1, max: 1,
        damage: [0.2, 0.8],
        items: [
            { id: 'minecraft:shears', ratio: 10 },
            { id: 'minecraft:lead', ratio: 10 },

            { id: 'woodenshears:wooden_shears', ratio: 10 },

        ]
    },
]


const village_shepherd_material = [
    {        
        groupName: "materials",
        groupWeight: 20,
        min: 3, max: 4,
        items: [
            { id: 'minecraft:white_wool', ratio: 10 },
            { id: 'minecraft:string', ratio: 10 },
        ]
    },
]

// =================================================================
// 事件注册区域
// =================================================================
const village_shepherd_content = [];

// 1. 定义群系映射表 [ 前缀, 抽取最小次数, 抽取最大次数, 匹配条件 ]
const shepherd_biome_mapping = [
    ['plains',       3, 3, { matchBiome: '#minecraft:has_structure/village_plains' }],
    ['desert',       3, 3, { matchBiome: '#minecraft:has_structure/village_desert' }],
    ['snowy',        3, 3, { matchBiome: '#minecraft:has_structure/village_snowy' }],
    ['taiga',        3, 3, { matchBiome: '#minecraft:is_taiga' }],
    ['swamp',        3, 3, { matchBiome: 'minecraft:swamp' }],
    ['cherry_grove', 3, 3, { matchBiome: 'minecraft:cherry_grove' }],
    ['jungle',       3, 3, { matchBiome: '#minecraft:is_jungle' }],
    ['ryb',          3, 3, { matchBiome: '#kubejs:shepherd/red_yellow_blue' }],
    ['ryo',          3, 3, { matchBiome: '#kubejs:shepherd/red_yellow_orange' }],
    ['other',        3, 3, { matchBiome: '#kubejs:shepherd/other' }]
];

// 2. 自动遍历并注入 羊毛 和 染料 的配置
shepherd_biome_mapping.forEach(([prefix, min, max, condition]) => {
    // 自动动态获取对应的全局变量，并拼入对应的 min 和 max 数量
    village_shepherd_content.push([eval(`${prefix}_shepherd_products_wool`), 1, 1, condition]);
    village_shepherd_content.push([eval(`${prefix}_shepherd_products_dye`), 2, 2, condition]);
});

// 3. 注入通用共享产物
village_shepherd_content.push(
    [village_shepherd_equipment, 1, 2],
    [village_shepherd_material, 2, 2]
);

const loot_village_shepherd = [
    [village_shepherd_content, 1],
];