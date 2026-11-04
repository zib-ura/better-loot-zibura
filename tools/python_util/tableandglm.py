import json
import re
from pathlib import Path
from python_util.version_profile import get_version_profile

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


def create_datapack_clear_entries(
        constants_dir,
        output_data_dir,
        version="1.21.1",
        loader=None,
        output_resolver=None,
):
    constants_dir = Path(constants_dir)
    output_data_dir = Path(output_data_dir)

    tables_js_path = (
            constants_dir / "0tableToClear.js"
    )

    modifiers_js_path = (
            constants_dir / "modifiersToRemove.js"
    )

    recipes_js_path = (
            constants_dir / "recipesToRemove.js"
    )

    tables_to_clear = extract_js_string_list(
        tables_js_path,
        "tableToClear"
    )

    modifiers_to_remove = extract_js_string_list(
        modifiers_js_path,
        "modifiersToRemove"
    )

    recipes_to_remove = extract_js_string_list(
        recipes_js_path,
        "recipesToRemove"
    )
    profile = get_version_profile(version)
    table_dir = profile.loot_table_dir
    modifier_dir = profile.loot_modifier_dir
    recipe_dir = profile.recipe_dir

    # Loot Tables
    for entry in tables_to_clear:
        namespace, path = entry.split(":", 1)
        target_path = (
            (output_resolver(namespace) if output_resolver else output_data_dir)
            / namespace
            / table_dir
            / f"{path}.json"
        )
        target_path.parent.mkdir(parents=True, exist_ok=True)
        with open(target_path, "w", encoding="utf-8") as f:
            f.write(EMPTY_JSON_CONTENT)

    # Loot Modifiers
    for entry in modifiers_to_remove:
        namespace, path = entry.split(":", 1)
        target_path = (
            (output_resolver(namespace) if output_resolver else output_data_dir)
            / namespace
            / modifier_dir
            / f"{path}.json"
        )
        target_path.parent.mkdir(parents=True, exist_ok=True)
        with open(target_path, "w", encoding="utf-8") as f:
            f.write(EMPTY_JSON_CONTENT)

    # Recipes
    for entry in recipes_to_remove:
        namespace, path = entry.split(":", 1)

        target_path = (
                (output_resolver(namespace) if output_resolver else output_data_dir)
                / namespace
                / recipe_dir
                / f"{path}.json"
        )

        target_path.parent.mkdir(parents=True, exist_ok=True)

        # 统一配方覆盖策略：所有加载器（Fabric / Forge / NeoForge）都用基岩 -> 基岩。
        # 保留目标配方 ID 的有效注册值，避免 26.3 动态配方注册表出现未绑定引用。
        # 这不是从注册表删除配方；如果玩家可以获得基岩，配方仍可被使用。
        # 1.21.2 起原料从 {"item": "..."} 改为物品 ID 字符串。
        ingredient = (
            "minecraft:bedrock"
            if profile.parsed.at_least("1.21.2")
            else {"item": "minecraft:bedrock"}
        )
        # 1.20.5 起产物字段由 item 改为 id。
        result_key = "id" if profile.parsed.at_least("1.20.5") else "item"
        recipe_content = {
            "type": "minecraft:crafting_shapeless",
            "category": "misc",
            "ingredients": [ingredient],
            "result": {result_key: "minecraft:bedrock", "count": 1},
        }
        with open(target_path, "w", encoding="utf-8") as f:
            json.dump(recipe_content, f, ensure_ascii=False, indent=2)
            f.write("\n")

    print(f"✅ Clear entries generated: {output_data_dir}")
    print(f"   - Version: {version}")
    if loader:
        print(f"   - Loader: {loader}")
    print(f"   - Loot Tables cleared: {len(tables_to_clear)} entries")
    print(f"   - Loot Modifiers cleared: {len(modifiers_to_remove)} entries")
    print(f"   - Recipes replaced (bedrock -> bedrock): {len(recipes_to_remove)} entries")
