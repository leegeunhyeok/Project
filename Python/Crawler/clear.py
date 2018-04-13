import pymysql
import shutil
import os

def clear_db():
    conn = pymysql.connect(host="localhost", user="root", password="1234", db="python", charset="utf8")
    cur = conn.cursor()
    cur.execute("DELETE FROM crawler")
    conn.commit()

def clear_file():
    try:
        shutil.rmtree('./thumbnail/')
        shutil.rmtree('./img/')
        shutil.rmtree('./license/')
        shutil.rmtree('./log/')
    except Exception as e:
        pass

    os.mkdir("thumbnail")
    os.mkdir("img")
    os.mkdir("license")
    os.mkdir("log")

clear_db()
clear_file()

