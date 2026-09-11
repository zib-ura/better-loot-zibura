import shutil
from pathlib import Path

# 当前文件位于: .../kubejs/server_scripts/src/python_util/copy_data.py
CURRENT_DIR = Path(__file__).resolve().parent
SRC_DIR = CURRENT_DIR.parent          # .../kubejs/server_scripts/src
KUBEJS_DIR = SRC_DIR.parent
# ==================== 路径与重命名配置 ====================
# 源目录: kubejs/data
SOURCE_DIR = KUBEJS_DIR / "data"

# 目标目录: kubejs/server_scripts/src/output/data
TARGET_DIR = SRC_DIR / "output" / "data"

# 需要重命名的子文件夹名称（例如 1.21+ 常用的 loot_tables -> loot_table）
# 如果是其他文件夹，直接修改这里的名字即可
OLD_FOLDER_NAME = "kubejs"
NEW_FOLDER_NAME = "better_loot_zibura"
# =======================================================


def copy_and_rename_subfolders(
    src: Path = SOURCE_DIR,
    dst: Path = TARGET_DIR,
    old_name: str = OLD_FOLDER_NAME,
    new_name: str = NEW_FOLDER_NAME,
):
    """
    将 src 目录下的所有内容复制到 dst 目录。
    如果遇到名为 old_name 的子文件夹（包括命名空间深层子目录），统一重命名为 new_name。
    """
    if not src.exists():
        print(f"⚠️ 源目录不存在: {src}")
        return

    dst.mkdir(parents=True, exist_ok=True)

    def _copy_recursive(source_path: Path, target_path: Path):
        for item in source_path.iterdir():
            # 判断当前项目名是否需要替换
            target_item_name = new_name if item.name == old_name else item.name
            dest_item = target_path / target_item_name

            if item.is_dir():
                dest_item.mkdir(parents=True, exist_ok=True)
                # 递归复制子内容
                _copy_recursive(item, dest_item)
            else:
                # 复制文件并保留元数据
                shutil.copy2(item, dest_item)

    print(f"📂 正在从: {src}")
    print(f"   复制到: {dst}")
    _copy_recursive(src, dst)
    print(f"✅ 复制完成！所有 '{old_name}' 已重命名为 '{new_name}'")


if __name__ == "__main__":
    copy_and_rename_subfolders()