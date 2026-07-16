/**
 * 根据传入的附魔 ID 数组，生成用于 LootJS 或自定义战利品表配置的数组
 * @param {string[]} enchantments - 附魔 ID 数组 (例如 allAvailableEnchantments)
 * @param {number} ratio - 默认的权重/比例，默认设为 10
 * @returns {Array<{id: string, ratio: number, enchantRandomly: string[]}>} 
 */
function generateEnchantmentBooks(enchantments) {
    // 过滤掉可能存在的空值或非字符串，确保安全
    const validEnchants = enchantments.filter(enchant => typeof enchant === 'string' && enchant.trim() !== '');

    // 将每一个附魔 ID 映射成你需要的对象格式
    return validEnchants.map(enchant => {
        return {
            id: 'minecraft:book',
            ratio: 10,
            enchantRandomly: [enchant]
        };
    });
}
const all_Available_Enchant_books = generateEnchantmentBooks(allAvailableEnchantments)
// const all_enchanted_book = [
//     {
//         groupName: "board_state",
//         groupWeight: 50,
//         min: 1, max: 1,
//         items: generateEnchantmentBooks(allAvailableEnchantments)
//     },
// ]