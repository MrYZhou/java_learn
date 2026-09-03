

#!/usr/bin/env python3
"""
jnpf-assistant.py - JNPF 助手命令行工具
用法：
  python  ./jnpf-assistant.py netpost <URL> data
  例:python  ./jnpf-assistant.py netpost /api/visualdev/Base/codegen {fields:[]}

  python  ./jnpf-assistant.py sqlexecute <SQL语句>
  例:python  ./jnpf-assistant.py sqlexecute select * from book where name = 'java'
"""

import sys
import os
import json
import time
import zipfile
import requests
from urllib.parse import urlparse, parse_qs

# ================== 数据库配置（如需 sqlexecute 功能，请修改） ==================
DB_CONFIG = {

    "host": os.getenv("MYSQL_HOST", "192.168.0.68"),
    "port": int(os.getenv("MYSQL_PORT", 5432)),
    "user": os.getenv("MYSQL_USER", "root"),
    "password": os.getenv("MYSQL_PASSWORD", "123456"),
    "database": os.getenv("MYSQL_DATABASE", "v6.1.x")

}
# 后端服务地址
APIHOST = 'http://127.0.0.1:30000'
# 后端apigenkey,默认请求都要带上。get是放在地址上，post放在body
APIGENKEY= 'FASJUWABD'
# 数据库类型(可选值 MySQL、SQLServer、Oracle、DM、KingbaseES、PostgreSQL
# DBTYPE= 'mysql'
DBTYPE= 'PostgreSQL'

def build_url(url):
    url =APIHOST + url if url.startswith('/api') else url  
    return url
# =============================================================================
def download(path: str, output_dir: str = "./downloads") -> None:
    full_url = build_url(path)  
    resp = requests.get(full_url, timeout=60, stream=True)
    resp.raise_for_status()
    try:
        filename = path.split('name=')[-1].split('&')[0]
        # 防止路径穿越，只取文件名部分
        filename = os.path.basename(filename)
    except IndexError:
        # 如果没有 name=，则从路径末尾取或使用默认名
        filename = full_url.split('/')[-1].split('?')[0] or "download.bin"

    os.makedirs(output_dir, exist_ok=True)
    filepath = os.path.join(output_dir, filename)

    with open(filepath, 'wb') as f:
        for chunk in resp.iter_content(chunk_size=8192):
            if chunk:
                f.write(chunk)

    print(f"文件已保存到：{filepath}")

    if filename.lower().endswith('.zip'):
        extract_dir = output_dir  # 以文件名（无后缀）建目录
        os.makedirs(extract_dir, exist_ok=True)
        print(f"📦 正在解压到：{extract_dir}")
        try:
            with zipfile.ZipFile(filepath, 'r') as zip_ref:
                zip_ref.extractall(extract_dir)
            print(f"解压完成，文件位于：{extract_dir}")
            # 若想删除压缩包，取消下一行注释
            os.remove(filepath)
        except zipfile.BadZipFile:
            print(f"⚠️ 文件不是有效的 ZIP 压缩包，跳过解压。")
        except Exception as e:
            print(f"❌ 解压失败：{e}")
    else:
        print(f"文件非 .zip 格式，未进行解压。")

def netget(path: str) -> None:
    """GET 请求"""
    full_url = build_url(path)
    try:
        resp = requests.get(full_url, timeout=60)
        resp.raise_for_status()
        print_response(resp)
    except Exception as e:
        print(f"[ERROR] GET 请求出错：{e}")
        sys.exit(1)

def netpost(path: str, data) -> None:
    """
    POST 请求
    data: JSON 字符串，如果为 None 则从标准输入读取
    """
    full_url = build_url(path)

    # 如果命令行未提供数据，尝试从 stdin 读取
    if data is None:
        if not sys.stdin.isatty():
            data = sys.stdin.read().strip()
        else:
            print("[ERROR]POST 需要提供 JSON 数据（命令行参数或管道输入）")
            sys.exit(1)

    # 解析 JSON 确保格式合法
    try:
        json_data = parse_json_input(data)
    except Exception as e:
        print(f"[ERROR]数据解析失败：{e}")
        sys.exit(1)

    try:
        headers = {"Authorization": f"Bearer {APIGENKEY}"}
        resp = requests.post(full_url, json=json_data, timeout=60, headers=headers)
        resp.raise_for_status()
        print_response(resp)
    except Exception as e:
        print(f"[ERROR]POST 请求出错：{e}")
        sys.exit(1)

    
def print_response(resp: requests.Response):
    """格式化输出响应"""
    print(f"[OK] 请求成功 [{resp.status_code}]  {resp.url}")
    content_type = resp.headers.get("Content-Type", "")
    if "application/json" in content_type:
        try:
            data = resp.json()
            print(json.dumps(data, indent=2, ensure_ascii=False))
        except Exception:
            print(resp.text)
    else:
        text = resp.text
        if len(text) > 2000:
            text = text[:2000] + "\n... (内容过长，已截断)"
        print(text)
def sqlexecute(sql: str) -> None:
    """
    执行 SQL 语句（SELECT 显示结果，其他显示影响行数）
    支持数据库类型由全局 DBTYPE 变量控制（MySQL / PostgreSQL）
    """
    db_type = DBTYPE.lower()

    # ---------- 根据数据库类型导入驱动与设置参数 ----------
    if db_type == 'mysql':
        try:
            import mysql.connector # pyright: ignore[reportMissingImports]
            from mysql.connector import Error # pyright: ignore[reportMissingImports]
        except ImportError:
            print("[ERROR]请先安装 mysql-connector-python：pip install mysql-connector-python")
            sys.exit(1)
        conn_params = DB_CONFIG.copy()                # MySQL 直接使用 database 键
        cursor_kwargs = {'dictionary': True}

    elif db_type in ('postgresql', 'postgres'):
        try:
            import psycopg2
            from psycopg2 import Error
            from psycopg2.extras import RealDictCursor
        except ImportError:
            print("[ERROR]请先安装 psycopg2-binary：pip install psycopg2-binary")
            sys.exit(1)
        conn_params = DB_CONFIG.copy()
        # PostgreSQL 连接参数使用 dbname，将 database 映射为 dbname
        if 'database' in conn_params:
            conn_params['dbname'] = conn_params.pop('database')
        cursor_factory = RealDictCursor
    elif db_type == 'oracle':
        try:
            import cx_Oracle
            from cx_Oracle import Error
        except ImportError:
            print("[ERROR]请先安装 cx_Oracle：pip install cx_Oracle")
            sys.exit(1)
        conn_params = DB_CONFIG.copy()
    else:
        print(f"[ERROR]不支持的数据库类型：{DBTYPE}，目前支持 MySQL、PostgreSQL")
        sys.exit(1)

    # ---------- 连接与执行 ----------
    try:
        if db_type == 'mysql':
            conn = mysql.connector.connect(**conn_params)
            cursor = conn.cursor(**cursor_kwargs)
        elif db_type in ('postgresql', 'postgres'):
            conn = psycopg2.connect(**conn_params)
            cursor = conn.cursor(cursor_factory=cursor_factory)

        cursor.execute(sql)

        if sql.strip().upper().startswith("SELECT"):
            results = cursor.fetchall()
            if results:
                headers = list(results[0].keys())
                print(" | ".join(headers))
                print("-" * (sum(len(h) for h in headers) + 3 * (len(headers) - 1)))
                for row in results:
                    print(" | ".join(str(row[h]) for h in headers))
                print(f"共 {len(results)} 行")
            else:
                print("查询无结果")
        else:
            conn.commit()
            print(f"[OK]执行成功，影响行数：{cursor.rowcount}")

        cursor.close()
        conn.close()

    except Exception as e:
        print(f"[ERROR]数据库错误：{e}")
        sys.exit(1)

def print_usage():
    print(__doc__)
def parse_json_input(data_str: str):
    """尝试解析 JSON 输入，支持宽松格式（如 {a:123}）"""
    # 1. 尝试标准 JSON
    try:
        return json.loads(data_str)
    except json.JSONDecodeError:
        pass

    # 2. 容错处理：给未加引号的 key 加上双引号（简单正则，不处理嵌套字符串）
    try:
        import re
        # 将类似 {key: value} 或 {key: ... 中的 key 加上引号
        fixed = re.sub(r'(\w+)\s*:', r'"\1":', data_str)
        return json.loads(fixed)
    except Exception:
        raise ValueError("JSON 格式错误，正确示例：netpost /api/user '{\"name\":\"张三\"}'")
def main(argv: list[str]):
    if len(argv) < 2:
        print_usage()
        sys.exit(1)

    command = argv[1].lower()
    if command == "netget":
        if len(argv) < 3:
            print("[ERROR]用法：netget <API路径>")
            sys.exit(1)
        path = argv[2]
        netget(path)
    elif command == "download":
        if len(argv) < 3:
            print("[ERROR]用法：download <API路径>")
            sys.exit(1)
        path = argv[2]
        download(path)    
    elif command == "netpost":
        if len(argv) < 3:
            print("[ERROR]用法：netpost <API路径> [JSON数据]")
            sys.exit(1)
        path = argv[2]
        data = None
        if len(argv) >= 4:
            # 支持包含空格的 JSON，将后面所有参数合并
            data = " ".join(argv[3:])
        netpost(path, data)

    elif command == "sqlexecute":
        if len(argv) < 3:
            print("[ERROR]用法：sqlexecute <SQL语句>")
            sys.exit(1)
        sql = " ".join(argv[2:])
        sqlexecute(sql)

    else:
        print(f"[ERROR]未知命令：{command}")
        print_usage()
        sys.exit(1)

if __name__ == "__main__":
    # 当使用 python -m tool.jnpf-assistant 运行时，sys.argv[0] 是脚本路径
    main(sys.argv)