import json
from pathlib import Path

# ==================== 路径与常量配置 ====================
# 定位项目根目录 (src/)
SRC_DIR = Path(__file__).resolve().parent.parent

# 数据包根目录：与 data 文件夹平级 (src/output/)
PACK_ROOT_DIR = SRC_DIR / "output"

# pack_format 说明：
# 1.20.1: 15
# 1.20.4: 26
# 1.21: 48
PACK_FORMAT = 48
PACK_DESCRIPTION = "Empty loot tables, modifiers and custom loot configuration"


def generate_mcmeta(
    output_dir: Path = PACK_ROOT_DIR,
    pack_format: int = PACK_FORMAT,
    description: str = PACK_DESCRIPTION
):
    """
    生成符合 Minecraft 数据包规范的 pack.mcmeta 文件。
    位置应该在数据包根目录下（即 data 文件夹的同级目录）。
    """
    output_dir.mkdir(parents=True, exist_ok=True)
    mcmeta_path = output_dir / "pack.mcmeta"

    mcmeta_content = {
        "pack": {
            "pack_format": pack_format,
            "description": description
        }
    }

    with open(mcmeta_path, "w", encoding="utf-8") as f:
        json.dump(mcmeta_content, f, indent=4, ensure_ascii=False)

    print(f"✅ 已生成 pack.mcmeta -> {mcmeta_path}")


if __name__ == "__main__":
    generate_mcmeta()