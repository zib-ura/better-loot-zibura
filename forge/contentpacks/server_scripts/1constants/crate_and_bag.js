// ==================== 1. 配置数组 ====================
let DUPLICATE_ITEMS_CONFIG = [

    { result: "lootjs:black_grape",       candidates: ["youkaishomecoming:black_grape", "youkaisfeasts:black_grape"] },
    { result: "lootjs:black_grape_seeds", candidates: ["youkaishomecoming:black_grape_seeds", "youkaisfeasts:black_grape_seeds"] },
    { result: "lootjs:cooked_lamb_chops", candidates: ["kaleidoscope_cookery:cooked_lamb_chops", "farmersdelight:cooked_mutton_chops"] },
    { result: "lootjs:cooked_rice",       candidates: ["kaleidoscope_cookery:cooked_rice", "farmersdelight:cooked_rice"] },
    { result: "lootjs:corn",              candidates: ["farm_and_charm:corn", "corn_delight:corn"] },
    { result: "lootjs:corn_seeds",        candidates: ["farm_and_charm:kernels", "corn_delight:corn_seeds"] },    
    { result: "lootjs:cucumber",          candidates: ["youkaisfeasts:cucumber", "youkaishomecoming:cucumber", "vintagedelight:cucumber"] },
    { result: "lootjs:cucumber_seeds",    candidates: ["youkaisfeasts:cucumber_seeds", "youkaishomecoming:cucumber_seeds", "vintagedelight:cucumber_seeds"] },
    { result: "lootjs:lettuce",           candidates: ["kaleidoscope_cookery:lettuce", "farm_and_charm:lettuce"] },
    { result: "lootjs:lettuce_seeds",     candidates: ["kaleidoscope_cookery:lettuce_seed", "farm_and_charm:lettuce_seeds"] },
    { result: "lootjs:oat",               candidates: ["farm_and_charm:oat", "vintagedelight:oat"] },
    { result: "lootjs:oat_seeds",         candidates: ["farm_and_charm:oat_seeds", "vintagedelight:oat_seeds"] },
    { result: "lootjs:onion",             candidates: ["farmersdelight:onion", "farm_and_charm:onion"] },
    { result: "lootjs:papaya",            candidates: ["muffins_thaidelight:papaya"] },
    { result: "lootjs:pepper",            candidates: ["kaleidoscope_cookery:red_chili", "kaleidoscope_cookery:green_chili", "muffins_thaidelight:pepper", "vintagedelight:ghost_pepper"] },
    { result: "lootjs:pepper_seeds",      candidates: ["kaleidoscope_cookery:chili_seed", "muffins_thaidelight:pepper_seeds", "vintagedelight:ghost_pepper_seeds"] },
    { result: "lootjs:pods",              candidates: ["youkaishomecoming:pods", "youkaisfeasts:pods"] },
    { result: "lootjs:raw_lamb_chops",    candidates: ["kaleidoscope_cookery:raw_lamb_chops", "farmersdelight:mutton_chops"] },
    { result: "lootjs:red_grape",         candidates: ["youkaishomecoming:red_grape", "youkaisfeasts:red_grape"] },
    { result: "lootjs:red_grape_seeds",   candidates: ["youkaishomecoming:red_grape_seeds", "youkaisfeasts:red_grape_seeds"] },
    { result: "lootjs:redbean",           candidates: ["youkaishomecoming:redbean", "youkaisfeasts:redbean"] },
    // { result: "lootjs:redbean_seeds",     candidates: ["youkaishomecoming:redbean_seeds", "youkaisfeasts:redbean_seeds"] },
    { result: "lootjs:rice",              candidates: ["kaleidoscope_cookery:rice", "farmersdelight:rice"] },
    { result: "lootjs:rotten_tomato",     candidates: ["farmersdelight:rotten_tomato", "farm_and_charm:rotten_tomato"] },
    { result: "lootjs:soybean",           candidates: ["youkaishomecoming:soybean", "youkaisfeasts:soybean"] },
    { result: "lootjs:pods",              candidates: ["youkaishomecoming:soybean_seeds", "youkaisfeasts:soybean_seeds"] },
    { result: "lootjs:tomato",            candidates: ["kaleidoscope_cookery:tomato", "farmersdelight:tomato", "farm_and_charm:tomato", "bakeries:tomato"] },
    { result: "lootjs:tomato_seeds",      candidates: ["kaleidoscope_cookery:tomato_seed", "farmersdelight:tomato_seeds", "farm_and_charm:tomato_seeds"] },
    { result: "lootjs:white_grape",       candidates: ["youkaishomecoming:white_grape", "youkaisfeasts:white_grape"] },
    { result: "lootjs:white_grape_seeds", candidates: ["youkaishomecoming:white_grape_seeds", "youkaisfeasts:white_grape_seeds"] }

];

// ==================== 2. 逻辑处理 ====================
mergeDuplicateItems (DUPLICATE_ITEMS_CONFIG) 
duplicateItemConversion(DUPLICATE_ITEMS_CONFIG);

let crateAndBagRegistry = [
    { name: "acorn_bag",               ids: ["seeddelight:acorn_bag"] },
    { name: "apple_crate",             ids: ["cratedelight:apple_crate"] },
    { name: "beetroot_crate",          ids: ["farmersdelight:beetroot_crate", "farm_and_charm:beetroot_bag"] },
    { name: "bell_pepper_green_crate", ids: ["rusticdelight:bell_pepper_green_crate"] },
    { name: "bell_pepper_red_crate",   ids: ["rusticdelight:bell_pepper_red_crate"] },
    { name: "bell_pepper_yellow_crate",ids: ["rusticdelight:bell_pepper_yellow_crate"] },
    { name: "berry_crate",             ids: ["cratedelight:berry_crate"] },
    { name: "black_grape_crate",       ids: ["youkaisfeasts:black_grape_crate", "youkaishomecoming:black_grape_crate"] },
    { name: "bread_bag",               ids: ["cratedelight:bread_bag"] },
    { name: "cabbage_crate",           ids: ["farmersdelight:cabbage_crate"] },
    { name: "carrot_crate",            ids: ["farmersdelight:carrot_crate", "farm_and_charm:carrot_bag"] },
    { name: "cherry_crate",            ids: ["seeddelight:cherry_crate"] },
    { name: "chinese_cabbage_crate",   ids: ["dumplings_delight:chinese_cabbage_crate"] },
    { name: "cocoabeans_bag",          ids: ["cratedelight:cocoabeans_bag"] },
    { name: "cod_crate",               ids: ["cratedelight:cod_crate"] },
    { name: "coffee_bag",              ids: ["rusticdelight:coffee_beans_bag"] },
    { name: "cookie_bag",              ids: ["cratedelight:cookie_bag"] },
    { name: "corn_crate",              ids: ["farm_and_charm:corn_bag", "corn_delight:corn_crate"] },
    { name: "cotton_crate",            ids: ["rusticdelight:cotton_boll_crate"] },
    { name: "cucumber_crate",          ids: ["youkaisfeasts:cucumber_crate", "vintagedelight:cucumber_crate"] },
    { name: "egg_crate",               ids: ["cratedelight:egg_crate"] },
    { name: "eggplant_crate",          ids: ["dumplings_delight:eggplant_crate"] },
    { name: "fennel_crate",            ids: ["dumplings_delight:fennel_crate"] },
    { name: "flour_bag",               ids: ["farm_and_charm:flour_bag"] },
    { name: "garlic_chive_crate",      ids: ["dumplings_delight:garlic_chive_crate"] },
    { name: "garlic_crate",            ids: ["dumplings_delight:garlic_crate"] },
    { name: "glowberry_crate",         ids: ["cratedelight:glowberry_crate"] },
    { name: "greenonion_crate",        ids: ["dumplings_delight:greenonion_crate"] },
    { name: "hay_block",               ids: ["minecraft:hay_block"] },
    { name: "kernel_crate",            ids: ["corn_delight:corn_kernel_bag"] },
    { name: "lettuce_bag",             ids: ["farm_and_charm:lettuce_bag"] },
    { name: "lime_crate",              ids: ["muffins_thaidelight:lime_crate"] },
    { name: "melon_crate",             ids: ["minecraft:melon", "cratedelight:melon_crate"] },
    { name: "mushroom_brown_crate",    ids: ["cratedelight:brown_mushroom_crate"] },
    { name: "mushroom_red_crate",      ids: ["cratedelight:red_mushroom_crate"] },
    { name: "oat_bale",                ids: ["farm_and_charm:oat_ball", "vintagedelight:oat_bale"] },
    { name: "onion_crate",             ids: ["farmersdelight:onion_crate", "farm_and_charm:onion_bag"] },
    { name: "papaya_crate",            ids: ["muffins_thaidelight:papaya_crate"] },
    { name: "peanut_crate",            ids: ["vintagedelight:peanut_crate"] },
    { name: "pepper_crate",            ids: ["muffins_thaidelight:pepper_crate", "vintagedelight:ghost_pepper_crate"] },
    { name: "pineapple_crate",         ids: ["pineapple_delight:pineapple_crate"] },
    { name: "pinecone_bag",            ids: ["seeddelight:pinecone_bag"] },
    { name: "poisonous_potato_crate",  ids: ["cratedelight:poisonous_potato_crate"] },
    { name: "potato_crate",            ids: ["farmersdelight:potato_crate", "farm_and_charm:potato_bag"] },
    { name: "pumpkin_crate",           ids: ["minecraft:pumpkin"] },
    { name: "pumpkin_slice_crate",     ids: ["cratedelight:pumpkin_slice_crate"] },

    { name: "red_grape_crate",         ids: ["youkaisfeasts:red_grape_crate", "youkaishomecoming:red_grape_crate"] },
    { name: "redbean_bag",             ids: ["youkaisfeasts:redbean_bag", "youkaishomecoming:redbean_bag"] },
    { name: "pod_crate",               ids: ["youkaisfeasts:pod_crate", "youkaishomecoming:pod_crate"] },
    { name: "rice_bag",                ids: ["farmersdelight:rice_bag"] },
    { name: "rice_panicle_bag",        ids: ["farmersdelight:rice_panicle_bag", "farmersdelight:rice_bale"] },
    { name: "roasted_coffee_bag",      ids: ["rusticdelight:roasted_coffee_beans_bag"] },
    { name: "rosehip_crate",           ids: ["seeddelight:rosehip_crate"] },
    { name: "salmon_crate",            ids: ["cratedelight:salmon_crate"] },
    { name: "soybean_bag",             ids: ["youkaisfeasts:soybean_bag", "youkaishomecoming:soybean_bag"] },
    { name: "strawberry_bag",          ids: ["farm_and_charm:strawberry_bag"] },
    { name: "sugar_bag",               ids: ["cratedelight:sugar_bag"] },
    
    // { name: "tea_bag", ids: [
    //     "youkaisfeasts:tea_leaf_bag", "youkaisfeasts:black_tea_bag", "youkaisfeasts:dark_tea_bag", 
    //     "youkaisfeasts:green_tea_bag", "youkaisfeasts:oolong_tea_bag", "youkaisfeasts:white_tea_bag", 
    //     "youkaisfeasts:yellow_tea_bag"
    // ]},

    { name: "tomato_crate",            ids: ["farmersdelight:tomato_crate", "farm_and_charm:tomato_bag"] },
    { name: "white_grape_crate",       ids: ["youkaisfeasts:white_grape_crate", "youkaishomecoming:white_grape_crate"] },

];

// const adaptedConfig = crateAndBagRegistry.map(item => ({
//     result: `lootjs:${item.name}`, // 自动加上 lootjs 命名空间前缀
//     candidates: item.ids
// }));

// mergeDuplicateItems(adaptedConfig);

// 映射转换并直接覆盖原数组名
crateAndBagRegistry = crateAndBagRegistry.map(item => ({
    result: `lootjs:${item.name}`, // 自动加上 lootjs 命名空间前缀
    candidates: item.ids
}));

mergeDuplicateItems(crateAndBagRegistry);

const mergeItem = DUPLICATE_ITEMS_CONFIG.concat(crateAndBagRegistry);

let finalFoodSupplyRegistry = [
    { name: "acorn",              singles: ["seeddelight:acorn"],                   crates: ["lootjs:acorn_bag"] },
    { name: "apple",              singles: ["minecraft:apple"],                     crates: ["lootjs:apple_crate"] },
    { name: "beetroot",           singles: ["minecraft:beetroot"],                  crates: ["lootjs:beetroot_crate"] },
    { name: "bell_pepper_green",  singles: ["rusticdelight:bell_pepper_green"],     crates: ["lootjs:bell_pepper_green_crate"] },
    { name: "bell_pepper_red",    singles: ["rusticdelight:bell_pepper_red"],       crates: ["lootjs:bell_pepper_red_crate"] },
    { name: "bell_pepper_yellow", singles: ["rusticdelight:bell_pepper_yellow"],    crates: ["lootjs:bell_pepper_yellow_crate"] },
    { name: "berry",              singles: ["minecraft:sweet_berries"],             crates: ["lootjs:berry_crate"] },
    { name: "black_grape",        singles: ["lootjs:black_grape"],                  crates: ["lootjs:black_grape_crate"] },
    { name: "cabbage",            singles: ["farmersdelight:cabbage"],              crates: ["lootjs:cabbage_crate"] },
    { name: "carrot",             singles: ["minecraft:carrot"],                    crates: ["lootjs:carrot_crate"] },
    { name: "cherry",             singles: ["seeddelight:cherry"],                  crates: ["lootjs:cherry_crate"] },
    { name: "chinese_cabbage",    singles: ["dumplings_delight:chinese_cabbage"],   crates: ["lootjs:chinese_cabbage_crate"] },
    { name: "cocoabeans",         singles: ["minecraft:cocoa_beans"],               crates: ["lootjs:cocoabeans_bag"] },
    { name: "cod",                singles: ["minecraft:cod"],                       crates: ["lootjs:cod_crate"] },
    { name: "coffee",             singles: ["rusticdelight:coffee_beans"],          crates: ["lootjs:coffee_bag"] },
    { name: "corn",               singles: ["lootjs:corn"],                         crates: ["lootjs:corn_crate"] },
    { name: "corn_kernel",        singles: ["lootjs:corn_seeds"],                   crates: ["lootjs:kernel_crate"] },
    { name: "cotton",             singles: ["rusticdelight:cotton_boll"],           crates: ["lootjs:cotton_crate"] },
    { name: "cucumber",           singles: ["lootjs:cucumber"],                     crates: ["lootjs:cucumber_crate"] },
    { name: "egg",                singles: ["minecraft:egg"],                       crates: ["lootjs:egg_crate"] },
    { name: "eggplant",           singles: ["dumplings_delight:eggplant"],          crates: ["lootjs:eggplant_crate"] },
    { name: "fennel",             singles: ["dumplings_delight:fennel"],            crates: ["lootjs:fennel_crate"] },
    { name: "garlic",             singles: ["dumplings_delight:garlic"],            crates: ["lootjs:garlic_crate"] },
    { name: "garlic_chive",       singles: ["dumplings_delight:garlic_chive"],      crates: ["lootjs:garlic_chive_crate"] },
    { name: "glowberry",          singles: ["minecraft:glow_berries"],              crates: ["lootjs:glowberry_crate"] },
    { name: "greenonion",         singles: ["dumplings_delight:greenonion"],        crates: ["lootjs:greenonion_crate"] },
    { name: "honey",              singles: ["minecraft:honey_bottle"],              crates: ["minecraft:honey_block"] },
    { name: "kelp",               singles: ["minecraft:kelp"],                      crates: ["minecraft:dried_kelp_block"] },
    { name: "lettuce",            singles: ["lootjs:lettuce"],                      crates: ["lootjs:lettuce_bag"] },
    { name: "lime",               singles: ["muffins_thaidelight:lime"],            crates: ["lootjs:lime_crate"] },
    { name: "melon",              singles: ["minecraft:melon_slice"],               crates: ["lootjs:melon_crate"] }, // 注：原版西瓜切片
    { name: "mushroom_brown",     singles: ["minecraft:brown_mushroom"],            crates: ["lootjs:mushroom_brown_crate"] },
    { name: "mushroom_red",       singles: ["minecraft:red_mushroom"],              crates: ["lootjs:mushroom_red_crate"] },
    { name: "oat",                singles: ["lootjs:oat"],                          crates: ["lootjs:oat_bale"] },
    { name: "onion",              singles: ["lootjs:onion"],                        crates: ["lootjs:onion_crate"] },
    { name: "papaya",             singles: ["muffins_thaidelight:papaya"],          crates: ["lootjs:papaya_crate"] },
    { name: "peanut",             singles: ["vintagedelight:peanut"],               crates: ["lootjs:peanut_crate"] },
    { name: "pepper",             singles: ["lootjs:pepper"],                       crates: ["lootjs:pepper_crate"] },
    { name: "pineapple",          singles: ["pineapple_delight:pineapple"],         crates: ["lootjs:pineapple_crate"] },
    { name: "pinecone",           singles: ["seeddelight:pinecone"],                crates: ["lootjs:pinecone_bag"] },
    { name: "poisonous_potato",   singles: ["minecraft:poisonous_potato"],          crates: ["lootjs:poisonous_potato_crate"] },
    { name: "potato",             singles: ["minecraft:potato"],                    crates: ["lootjs:potato_crate"] },
    { name: "pumpkin",            singles: ["farmersdelight:pumpkin_slice"],        crates: ["lootjs:pumpkin_crate"] },
    { name: "red_grape",          singles: ["lootjs:red_grape"],                    crates: ["lootjs:red_grape_crate"] },
    { name: "rice",               singles: ["lootjs:rice"],                         crates: ["lootjs:rice_bag"] },
    { name: "roasted_coffee",     singles: ["rusticdelight:roasted_coffee_beans"],  crates: ["lootjs:roasted_coffee_bag"] },
    { name: "rosehip",            singles: ["seeddelight:rosehip"],                 crates: ["lootjs:rosehip_crate"] },
    { name: "salmon",             singles: ["minecraft:salmon"],                    crates: ["lootjs:salmon_crate"] },
    { name: "strawberry",         singles: ["farm_and_charm:strawberry"],           crates: ["lootjs:strawberry_bag"] },
    { name: "sugar",              singles: ["minecraft:sugar"],                     crates: ["lootjs:sugar_bag"] },
    { name: "tomato",             singles: ["lootjs:tomato"],                       crates: ["lootjs:tomato_crate"] },
    { name: "wheat",              singles: ["minecraft:wheat"],                     crates: ["lootjs:hay_block"] },
    { name: "white_grape",        singles: ["lootjs:white_grape"],                  crates: ["lootjs:white_grape_crate"] },

    { name: "redbean",            singles: ["lootjs:redbean"],                      crates: ["lootjs:redbean_bag"] },
    { name: "soybean",            singles: ["lootjs:soybean"],                      crates: ["lootjs:soybean_bag"] },
    { name: "pods",               singles: ["lootjs:pods"],                         crates: ["lootjs:pod_crate"] },
    
    { name: "raw_iron",        singles: ["minecraft:raw_iron"],        crates: ["minecraft:raw_iron_block"] },
    { name: "raw_copper",      singles: ["minecraft:raw_copper"],      crates: ["minecraft:raw_copper_block"] },
    { name: "raw_gold",        singles: ["minecraft:raw_gold"],        crates: ["minecraft:raw_gold_block"] },
    { name: "coal",            singles: ["minecraft:coal"],            crates: ["minecraft:coal_block"] },
    { name: "iron_ingot",      singles: ["minecraft:iron_ingot"],      crates: ["minecraft:iron_block"] },
    { name: "copper_ingot",    singles: ["minecraft:copper_ingot"],    crates: ["minecraft:copper_block"] },
    { name: "gold_ingot",      singles: ["minecraft:gold_ingot"],      crates: ["minecraft:gold_block"] },
    { name: "redstone",        singles: ["minecraft:redstone"],        crates: ["minecraft:redstone_block"] },
    { name: "emerald",         singles: ["minecraft:emerald"],         crates: ["minecraft:emerald_block"] },
    { name: "lapis",           singles: ["minecraft:lapis_lazuli"],    crates: ["minecraft:lapis_block"] },
    { name: "diamond",         singles: ["minecraft:diamond"],         crates: ["minecraft:diamond_block"] },
    { name: "netherite",       singles: ["minecraft:netherite_ingot"], crates: ["minecraft:netherite_block"] },
    { name: "quartz",          singles: ["minecraft:quartz"],          crates: ["minecraft:quartz_block"] },
    { name: "amethyst",        singles: ["minecraft:amethyst_shard"],  crates: ["minecraft:amethyst_block"] },
    { name: "raw_zinc",       singles: ["create:raw_zinc"],       crates: ["create:raw_zinc_block"] },
    { name: "zinc_ingot",     singles: ["create:zinc_ingot"],     crates: ["create:zinc_block"] },


];

// =================================================================
// 第二步：将单个食物与之前打包好的箱/袋战利品表进行最终关联
// =================================================================
createLootTable2Pool(finalFoodSupplyRegistry)