import re
from pathlib import Path

# ==================== 路径配置 ====================
SRC_DIR = Path(__file__).resolve().parent.parent

TABLES_JS_PATH = SRC_DIR / "1constants" / "0tableToClear.js"
MODIFIERS_JS_PATH = SRC_DIR / "1constants" / "modifiersToRemove.js"
RECIPES_JS_PATH = SRC_DIR / "1constants" / "recipesToRemove.js"  # 👈 新增配方列表路径

# 输出目标：src/output/data/
OUTPUT_DATA_DIR = SRC_DIR / "output" / "data"

EMPTY_JSON_CONTENT = "{}\n"


def extract_js_string_list(file_path: Path, var_name: str) -> list[str]:
    """读取 JS 文件并提取有效字符串项（自动忽略 // 和 /* */ 注释）"""
    if not file_path.exists():
        return []

    with open(file_path, "r", encoding="utf-8") as f:
        content = f.read()

    match = re.search(rf"(?:const|let|var)\s+{var_name}\s*=\s*\[([\s\S]*?)\];", content)
    if not match:
        return []

    array_body = match.group(1)
    array_body = re.sub(r"/\*[\s\S]*?\*/", "", array_body)
    array_body = re.sub(r"//.*", "", array_body)

    items = re.findall(r'["\']([^"\']+)["\']', array_body)
    return items


def create_datapack_clear_entries():
    """生成清空的 Loot Tables、Modifiers 与 Recipes（兼容 1.20.1 与 1.21.1）"""
    tables_to_clear = extract_js_string_list(TABLES_JS_PATH, "tableToClear")
    modifiers_to_remove = extract_js_string_list(MODIFIERS_JS_PATH, "modifiersToRemove")
    recipes_to_remove = extract_js_string_list(RECIPES_JS_PATH, "recipesToRemove")

    # 1. 清空 Loot Tables (1.20.1 loot_tables / 1.21.1 loot_table)
    table_dirs = ["loot_tables", "loot_table"]
    for entry in tables_to_clear:
        namespace, path = entry.split(":", 1)
        for folder in table_dirs:
            target_path = OUTPUT_DATA_DIR / namespace / folder / f"{path}.json"
            target_path.parent.mkdir(parents=True, exist_ok=True)
            with open(target_path, "w", encoding="utf-8") as f:
                f.write(EMPTY_JSON_CONTENT)

    # 2. 清空 Loot Modifiers (1.20.1 loot_modifiers / 1.21.1 loot_modifier)
    modifier_dirs = ["loot_modifiers", "loot_modifier"]
    for entry in modifiers_to_remove:
        namespace, path = entry.split(":", 1)
        for folder in modifier_dirs:
            target_path = OUTPUT_DATA_DIR / namespace / folder / f"{path}.json"
            target_path.parent.mkdir(parents=True, exist_ok=True)
            with open(target_path, "w", encoding="utf-8") as f:
                f.write(EMPTY_JSON_CONTENT)

    # 3. 清空 Recipes (1.20.1 recipes / 1.21.1 recipe) 👈 新增生成逻辑
    recipe_dirs = ["recipes", "recipe"]
    for entry in recipes_to_remove:
        namespace, path = entry.split(":", 1)
        for folder in recipe_dirs:
            target_path = OUTPUT_DATA_DIR / namespace / folder / f"{path}.json"
            target_path.parent.mkdir(parents=True, exist_ok=True)
            with open(target_path, "w", encoding="utf-8") as f:
                f.write(EMPTY_JSON_CONTENT)

    print(f"✅ 清理项生成完成: {OUTPUT_DATA_DIR}")
    print(f"   - 清理 Loot Tables: {len(tables_to_clear)} 条目")
    print(f"   - 清理 Loot Modifiers: {len(modifiers_to_remove)} 条目")
    print(f"   - 清理 Recipes: {len(recipes_to_remove)} 条目")


if __name__ == "__main__":
    create_datapack_clear_entries()