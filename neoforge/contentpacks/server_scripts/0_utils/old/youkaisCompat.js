$G.youkaisCompat = function(drinksArray) {
    // 检查是否安装了 youkaishomecoming
    if (Platform.isLoaded('youkaishomecoming')) {
        drinksArray.forEach(group => {
            if (group.items && Array.isArray(group.items)) {
                group.items.forEach(item => {
                    if (item.id && item.id.startsWith('youkaisfeasts:')) {
                        item.id = item.id.replace('youkaisfeasts:', 'youkaishomecoming:');
                    }
                });
            }
        });
    }
    return drinksArray;
}