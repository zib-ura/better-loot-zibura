from python_util import create_mcmeta
from python_util import crateandbag
from python_util import tableandglm
from python_util import loot_convert
from python_util import copy_data  # 1. 引入新模块
from python_util import apply_convert

def main():
    print("========================================")
    print("           开始执行自动化处理任务          ")
    print("========================================\n")

    # 执行复制与子文件夹重命名
    print(">>> [步骤] 正在同步并处理 data 文件夹...")
    copy_data.copy_and_rename_subfolders()
    print()

    # 1. 生成数据包根目录标识文件 pack.mcmeta
    print(">>> [1/4] 正在生成 pack.mcmeta...")
    create_mcmeta.generate_mcmeta()
    print()

    # 2. 执行清空 Loot Table 与 Modifier 生成
    print(">>> [2/4] 正在生成空战利品表数据包...")
    tableandglm.create_datapack_clear_entries()
    print()

    # 3. 执行物品统一配置导出
    print(">>> [3/4] 正在导出物品/箱包统一 JSON 配置...")
    crateandbag.run()
    print()

    # 4. 执行 loot_convert 转换
    print(">>> [4/4] 执行 loot_convert 转换...")
    loot_convert.process_folder()

    # 在 main() 流程中添加执行调用：
    print(">>> [5/5] 执行 2apply 映射转换...")
    apply_convert.process_apply_folder()

    print("========================================")
    print("           所有任务执行完毕！            ")
    print("========================================")


if __name__ == "__main__":
    main()