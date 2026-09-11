
import json
import os
from pathlib import Path
import re

# 基于当前脚本自动定位 src 目录与默认路径
SRC_DIR = Path(__file__).resolve().parent.parent
DEFAULT_INPUT_DIR = SRC_DIR / "1constants"
DEFAULT_OUTPUT_DIR = SRC_DIR / "output" / "data" / "better_loot_zibura" / "better_loot_zibura" /  "loot_pools"


# ----------------- 模版生成函数 -----------------
def build_seed_bundle_payload(
    bundle_id: str, seed_type_key: str, divisor: float
):
  return [{
      "id": bundle_id,
      "ratio": 10,
      "jsonFunction": [{
          "function": "better_loot_zibura:fill_seed_bundle",
          "seed_type_key": seed_type_key,
          "pool_Divisor": divisor,
          "max_Distinct_Types": 8,
          "min_count": 1,
          "max_count": 3,
      }],
  }]


# ----------------- JS 代码清理与正则分词 -----------------
def strip_js_comments(text: str) -> str:
  """清理 JS 的单行注释 (//...) 与多行注释 (/*...*/)"""
  pattern = r"(//[^\n]*)|(/\*[\s\S]*?\*/)"
  return re.sub(pattern, "", text)


def tokenize_js(code: str):
  """将 JS 源码分解为 token 序列"""
  code = strip_js_comments(code)
  token_spec = [
      ("STRING", r'"(?:\\.|[^"\\])*"|\'(?:\\.|[^\'\\])*\''),
      ("NUMBER", r"-?\d+(?:\.\d+)?"),
      ("IDENT", r"[a-zA-Z_$][a-zA-Z0-9_$]*"),
      ("PUNCT", r"[{}()[\]:,=.]"),
      ("SKIP", r"\s+"),
  ]
  tok_regex = "|".join(f"(?P<{name}>{pattern})" for name, pattern in token_spec)
  tokens = []
  for mo in re.finditer(tok_regex, code):
    kind = mo.lastgroup
    val = mo.group()
    if kind != "SKIP":
      tokens.append((kind, val))
  return tokens


# ----------------- 语法树解析器 -----------------
class JSObjectParser:

  def __init__(self, tokens):
    self.tokens = tokens
    self.pos = 0

  def peek(self):
    if self.pos < len(self.tokens):
      return self.tokens[self.pos]
    return None

  def next_token(self):
    tok = self.peek()
    self.pos += 1
    return tok

  def parse_all(self):
    declarations = {}
    while self.pos < len(self.tokens):
      tok = self.peek()
      if tok and tok[1] in ("const", "let", "var"):
        self.next_token()
        var_name = self.next_token()[1]
        if self.peek() and self.peek()[1] == "=":
          self.next_token()  # 跳过 '='
          val = self.parse_value()
          declarations[var_name] = val
      else:
        self.pos += 1
    return declarations

  def parse_value(self):
    tok = self.peek()
    if not tok:
      return None

    kind, val = tok

    # # 数组: [ ... ]
    # if val == "[":
    #   self.next_token()
    #   arr = []
    #   while self.peek() and self.peek()[1] != "]":
    #     arr.append(self.parse_value())
    #     if self.peek() and self.peek()[1] == ",":
    #       self.next_token()
    #   if self.peek() and self.peek()[1] == "]":
    #     self.next_token()
    #   return arr
    # 数组: [ ... ]
    if val == '[':
        self.next_token()
        arr = []
        while self.peek() and self.peek()[1] != ']':
            arr.append(self.parse_value())
            if self.peek() and self.peek()[1] == ',':
                self.next_token()
        if self.peek() and self.peek()[1] == ']':
            self.next_token()

        # 处理链式调用: [].concat(...)
        while self.peek() and self.peek()[1] == '.':
            self.next_token()  # 跳过 '.'
            method_token = self.next_token()
            method_name = method_token[1] if method_token else None

            if method_name == 'concat' and self.peek() and self.peek()[1] == '(':
                self.next_token()  # 跳过 '('
                while self.peek() and self.peek()[1] != ')':
                    arg_val = self.parse_value()
                    if arg_val is not None:
                        # 将 concat 传入的列表项追加到 arr 中
                        arr.append(arg_val)
                    if self.peek() and self.peek()[1] == ',':
                        self.next_token()
                if self.peek() and self.peek()[1] == ')':
                    self.next_token()  # 跳过 ')'
        return arr

    # 对象: { ... }
    elif val == "{":
      self.next_token()
      obj = {}
      while self.peek() and self.peek()[1] != "}":
        key_tok = self.next_token()
        key = key_tok[1].strip("\"'")
        if self.peek() and self.peek()[1] == ":":
          self.next_token()
        prop_val = self.parse_value()

        if key == "matchBiome":
          obj["customCondition"] = {
              "condition": "better_loot_zibura:match_biome_registry",
              "registry_key": prop_val,
          }
        else:
          obj[key] = prop_val

        if self.peek() and self.peek()[1] == ",":
          self.next_token()
      if self.peek() and self.peek()[1] == "}":
        self.next_token()
      return obj

    # 字符串
    elif kind == "STRING":
      self.next_token()
      return val[1:-1]

    # 数字
    elif kind == "NUMBER":
      self.next_token()
      return float(val) if "." in val else int(val)

    # 标识符 / 引用 / 函数调用
    elif kind == "IDENT":
      name = self.next_token()[1]

      # 属性访问
      while self.peek() and self.peek()[1] == ".":
        self.next_token()
        attr = self.next_token()[1]
        name = f"{name}_{attr}".replace("carpenterData_", "carpenter_")

      # 函数调用: getSeedBagConfig(...)
      if self.peek() and self.peek()[1] == "(":
        self.next_token()
        args = []
        while self.peek() and self.peek()[1] != ")":
          args.append(self.parse_value())
          if self.peek() and self.peek()[1] == ",":
            self.next_token()
        if self.peek() and self.peek()[1] == ")":
          self.next_token()

        if name == "getSeedBagConfig":
          bundle_id = args[0]
          seed_key = args[1]
          divisor = 3.0
          return build_seed_bundle_payload(bundle_id, seed_key, divisor)
        return None

      # 特殊关键字
      if name == "true":
        return True
      if name == "false":
        return False
      if name == "null":
        return None

      return name

    else:
      self.next_token()
      return None


# ----------------- 主流程处理 -----------------
def convert_js_to_dict(js_code: str) -> dict:
  tokens = tokenize_js(js_code)
  parser = JSObjectParser(tokens)
  all_vars = parser.parse_all()

  filter_keys = {
      "carpenterData",
      "utility",
      "furniture",
      "materials",
      "sapling",
      "axes",
      "sawmills",
      "woods",
      "items",
      "groupWeight",
      "min",
      "max",
      "itemId",
      "colors",
      "biome",
      "condition",
      "village_shepherd_content",
      "POTIONS_1_20_1",
      "POTIONS_1_21_1",
      "currentMCVersion",

  }
  return {k: v for k, v in all_vars.items() if k not in filter_keys}


def process_folder(input_folder=None, output_folder=None):
  """如果不传参，默认读取 src/1constants/ 并输出到 src/output/loot_configs/"""
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

      # 跳过已经单独处理的几个特殊配置 js 文件
      if file in (
          "0tableToClear.js",
          "modifiersToRemove.js",
          "crate_and_bag.js",
          "recipesToRemove.js"
      ):
        continue

      src_path = Path(root) / file
      rel_path = src_path.relative_to(in_dir)
      dest_path = (out_dir / rel_path).with_suffix(".json")

      dest_path.parent.mkdir(parents=True, exist_ok=True)

      try:
        with open(src_path, "r", encoding="utf-8") as f:
          content = f.read()

        result_dict = convert_js_to_dict(content)

        with open(dest_path, "w", encoding="utf-8") as f:
          json.dump(result_dict, f, ensure_ascii=False, indent=2)

        print(f"[✓ 已转换]: {rel_path} -> {dest_path.name}")
        converted_count += 1
      except Exception as e:
        print(f"[✗ 失败]: {rel_path}, 错误: {e}")

  print(f"✅ Loot 转 JSON 完成，共转换 {converted_count} 个文件 -> {out_dir}")


if __name__ == "__main__":
  process_folder()