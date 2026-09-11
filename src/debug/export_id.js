// ServerEvents.recipes(event => {
//     // --- 配置部分 ---
//     const MOD_ID = "immersive_armors"; // 将此处替换为你想要导出的模组 ID (例如 "ironchest")
//     const FILE_PATH = "kubejs/exported_items.txt";
//     // ----------------

//     let itemList = Item.getList().filter(item => item.mod === MOD_ID);
//     let lines = [];

//     lines.push(`--- ${MOD_ID} 物品导出报告 ---`);
//     lines.push(`总计数量: ${itemList.length}`);
//     lines.push("");

//     itemList.forEach(item => {
//         lines.push(item.id);
//     });

//     // 写入文件
//     JsonIO.write(FILE_PATH, { items: lines }); 
//     // 或者使用更直接的文本输出方式
//     //event.server.tell(`已导出 ${itemList.length} 个来自 ${MOD_ID} 的物品到 ${FILE_PATH}`);
// })

ServerEvents.recipes(event => {
    // --- 配置部分 ---
    const MOD_ID = null; // 设置为 null 则搜索所有模组；设置为 "ironchest" 则只搜该模组
    const ID_FILTER = /alexsmobsdelight/; // 正则表达式：匹配包含这些关键词的 ID
    const FILE_PATH = "kubejs/server_scripts/src/debug/exported_items.txt";
    // ----------------

    // 逻辑：如果 MOD_ID 存在则匹配 mod，否则只看正则
    let itemList = Item.getList().filter(item => {
        let modMatch = MOD_ID ? item.mod === MOD_ID : true;
        let idMatch = ID_FILTER.test(item.id);
        return modMatch && idMatch;
    });

    let lines = [];
    lines.push(`--- 全局物品导出报告 ---`);
    lines.push(`筛选条件: [模组: ${MOD_ID || "全部"}] [正则: ${ID_FILTER.toString()}]`);
    lines.push(`总计数量: ${itemList.length}`);
    lines.push("---------------------------");

    // itemList.forEach(item => {
    //     // 在全局搜索时，建议把 [模组ID] 也导出来，方便分辨
    //     lines.push(`${item.id}  [来自: ${item.mod}]`);
    // });


    // itemList.forEach(item => {

    //     lines.push(`${item.id}`);
    // });
    itemList.forEach(item => {
        try {
            let itemStack = Item.of(item.id);
            let displayName = itemStack.getDisplayName().getString();  // 获取本地化名称（需服务器语言为中文）
            lines.push(`${item.id}  ->  ${displayName}`);
        } catch(e) {
            lines.push(`${item.id}  ->  [名称获取失败]`);
        }
    });


    // 写入文件
    JsonIO.write(FILE_PATH, { items: lines });

    console.log(`[KubeJS] 扫描完成！共找到 ${itemList.length} 个匹配项，已保存至 ${FILE_PATH}`);
})