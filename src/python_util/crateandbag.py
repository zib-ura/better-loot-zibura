import ast
import json
import re
from pathlib import Path

# ==================== 路径配置 ====================
SRC_DIR = Path(__file__).resolve().parent.parent

# 兼容 crate_and_bag.js 可能在 structure 或 item 目录下的情况
candidate_js_paths = [
    SRC_DIR / "1constants" / "crate_and_bag.js",
]
JS_FILE_PATH = next((p for p in candidate_js_paths if p.exists()), candidate_js_paths[0])

# 输出目标：main同级的 output/item_unifications
OUTPUT_ROOT = SRC_DIR / "output" / "data" /"better_loot_zibura" / "better_loot_zibura" / "item_unifications"


# ==================== 解析与导出逻辑 ====================
def extract_js_array(js_text: str, var_name: str) -> str:
    pattern = rf"(?:let|const|var)\s+{var_name}\s*=\s*(\[[\s\S]*?\]);"
    match = re.search(pattern, js_text)
    if not match:
        raise ValueError(f"未在 JS 中找到变量: {var_name}")
    return match.group(1)


def parse_js_object_array(raw_js_str: str) -> list:
    # 1. 过滤注释
    clean = re.sub(r"/\*[\s\S]*?\*/", "", raw_js_str)
    clean = re.sub(r"//.*", "", clean)

    # 2. 对未加引号的 key 补全双引号
    clean = re.sub(r"(?<=[\{,\n\s])([a-zA-Z_]\w*)\s*:", r'"\1":', clean)

    # 3. 解析为 Python 数据结构
    return ast.literal_eval(clean)


def export_item_json(folder: Path, key: str, candidates: list):
    folder.mkdir(parents=True, exist_ok=True)
    file_path = folder / f"{key}.json"

    data = {"replace": False, "values": candidates}

    with open(file_path, "w", encoding="utf-8") as f:
        json.dump(data, f, indent=2, ensure_ascii=False)


def run():
    if not JS_FILE_PATH.exists():
        raise FileNotFoundError(f"找不到 JS 文件: {JS_FILE_PATH}")

    with open(JS_FILE_PATH, "r", encoding="utf-8") as f:
        js_content = f.read()

    conv_dir = OUTPUT_ROOT / "convertible"
    inconv_dir = OUTPUT_ROOT / "inconvertible"

    # 1. 导出 convertible/ (DUPLICATE_ITEMS_CONFIG)
    conv_raw = extract_js_array(js_content, "DUPLICATE_ITEMS_CONFIG")
    conv_data = parse_js_object_array(conv_raw)
    for item in conv_data:
        key = item["result"].replace("lootjs:", "")
        export_item_json(conv_dir, key, item["candidates"])

    # 2. 导出 inconvertible/ (crateAndBagRegistry)
    inconv_raw = extract_js_array(js_content, "crateAndBagRegistry")
    inconv_data = parse_js_object_array(inconv_raw)
    for item in inconv_data:
        key = item["name"]
        export_item_json(inconv_dir, key, item["ids"])

    print(
        f"✅ 导出成功: {OUTPUT_ROOT}\n"
        f"   - convertible: {len(conv_data)} 个文件\n"
        f"   - inconvertible: {len(inconv_data)} 个文件"
    )


if __name__ == "__main__":
    run()