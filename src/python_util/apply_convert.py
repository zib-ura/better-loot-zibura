import json
import os
from pathlib import Path
import re

SRC_DIR = Path(__file__).resolve().parent.parent
DEFAULT_INPUT_DIR = SRC_DIR / "2apply"
DEFAULT_OUTPUT_DIR = (
        SRC_DIR / "output" / "data" / "better_loot_zibura" / "better_loot_zibura" / "loot_bindings"
)

# 在这里指定排除的文件名
EXCLUDE_FILES = {
    "0emptyobject.js",
    "zzz_register_loot_table",
}


def strip_comments_and_normalize(code: str) -> str:
    """清理多行注释与单行注释"""
    code = re.sub(r'/\*[\s\S]*?\*/', '', code)
    code = re.sub(r'//[^\n]*', '', code)
    return code


def parse_apply_js(code: str) -> list[dict]:
    """提取 key: value 对并转换为 target / config 映射列表"""
    code = strip_comments_and_normalize(code)
    pattern = r'["\'](?P<target>[^"\']+)["\']\s*:\s*(?P<config>[a-zA-Z0-9_$]+)'

    entries = []
    for match in re.finditer(pattern, code):
        target = match.group("target").strip()
        config = match.group("config").strip()
        entries.append({
            "target": target,
            "config": config
        })
    return entries


def process_apply_folder(input_folder=None, output_folder=None):
    in_dir = Path(input_folder) if input_folder else DEFAULT_INPUT_DIR
    out_dir = Path(output_folder) if output_folder else DEFAULT_OUTPUT_DIR

    if not in_dir.exists():
        print(f"[!] 找不到输入目录: {in_dir}")
        return

    out_dir.mkdir(parents=True, exist_ok=True)
    converted_count = 0

    for root, _, files in os.walk(in_dir):
        for file in files:
            if not file.endswith(".js"):
                continue

            if file in EXCLUDE_FILES:
                print(f"[-] 跳过排除文件: {file}")
                continue

            src_path = Path(root) / file
            rel_path = src_path.relative_to(in_dir)
            dest_path = (out_dir / rel_path).with_suffix(".json")

            dest_path.parent.mkdir(parents=True, exist_ok=True)

            try:
                with open(src_path, "r", encoding="utf-8") as f:
                    content = f.read()

                result_list = parse_apply_js(content)

                with open(dest_path, "w", encoding="utf-8") as f:
                    json.dump(result_list, f, ensure_ascii=False, indent=2)

                print(f"[✓ 已转换 2apply]: {rel_path} -> {dest_path.name}")
                converted_count += 1
            except Exception as e:
                print(f"[✗ 失败]: {rel_path}, 错误: {e}")

    print(f"✅ 2apply 转 JSON 完成，共转换 {converted_count} 个文件 -> {out_dir}")


if __name__ == "__main__":
    process_apply_folder()