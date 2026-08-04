if (Platform.isLoaded('kubeloader')) { 

    // // 1. 获取已有的共享对象（可能别人已经先运行并写入了数据）
    let sharedData = /* ContentPacks.getShared("channel") || */ {}; 

    // // 2. 避免直接覆盖！使用 Object.assign 把你的默认数据和别人的修改合并
    // // 优先保留 sharedData.allLootTables 里别人已经修改过的键
    sharedData.allLootTables = allLootTables;

    // 其他配置正常挂载
    sharedData.crateAndBag = [ DUPLICATE_ITEMS_CONFIG, crateAndBagRegistry]; 
    sharedData.getSeedBagConfig = getSeedBagConfig;
    sharedData.tableToClear = tableToClear;
    sharedData.modifiersToRemove = modifiersToRemove;
    sharedData.carpenterData = carpenterData;
    sharedData.enchantmentsWithLevels = enchantmentsWithLevels;
    sharedData.enchantmentsWithoutLevels = enchantmentsWithoutLevels;
    sharedData.registerLootTables = registerLootTables;
    // 3. 把整合后的对象放回频道
    ContentPacks.putShared("channel", sharedData); 


    // ==================== 你的其他代码逻辑 ====================


    // ==================== 尾部注册逻辑 ====================
    // 4. 重新获取最新的共享数据
    let finalSharedData = ContentPacks.getShared("channel"); 

    // 5. 此时无论别人是在你之前还是之后修改的，拿到的都是最终合并版！
    if (finalSharedData && finalSharedData.allLootTables) { 
        finalSharedData.registerLootTables(finalSharedData.allLootTables); 
    }
} else {
    // 如果 kubeloader 没有加载，直接注册 allLootTables
    registerLootTables(allLootTables);
}