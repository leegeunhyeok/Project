# -*- coding: utf-8 -*-
from bs4 import BeautifulSoup
from PIL import Image
import urllib.request
import pymysql
import os
import re
import time  # 시간
import multiprocessing
# import

conn = pymysql.connect(host="localhost", user="root", password="1234", db="python", charset="utf8")
cur = conn.cursor()

# 저작물 링크 추출
def get_links(url):
    html = urllib.request.urlopen(url)
    source = html.read()

    soup = BeautifulSoup(source, "html.parser")

    list = soup.find(id="wrtList")  # 아이템(저작물) 목록
    items = list.find_all("li")  # 아이템(저작물)
    url_list = []
    for item in items:
        url_list.append(item.find("a").get("href"))
    return url_list

# 공유마당의 마지막 페이지 추출
def get_max_page(url):
    html = urllib.request.urlopen(url)
    source = html.read()
    soup = BeautifulSoup(source, "html.parser")
    last_btn = soup.find(class_="end")
    href = last_btn.find("a").get("href")
    max = re.search("[0-9]{1,10}$", href).group()
    print("게시글 마지막 페이지:", max)
    return int(max)


# 저작물의 이미지, 라이선스, 상세정보 파싱
# 공유마당 기본 URL, 상세정보 URL
def get_item_data(href, thumbnail=False):
    base_url = "https://gongu.copyright.or.kr"
    url = base_url + href

    html = urllib.request.urlopen(url)
    source = html.read()

    soup = BeautifulSoup(source, "html.parser")

    # 에러 핸들링, (SQL, HTML 속성, 기타 오류)
    try:
        _id = getCode(url)
        img_src = soup.find(class_="imgD").find("img").get("src")  # 저작물 이미지 src
        img_name = soup.find(class_="tit_txt3").text  # 이미지 명
        copy = soup.find(class_="copyD")
        copy_src = copy.find("img").get("src")  # 라이선스 이미지 src
        copy_text = copy.text.replace("\n", "").replace("\r", "").replace("\t", "").strip()
        copy_name = copy_src.split("/")[-1]  # 라이선스 이미지 명

        # 라이선스 파일이 없을 때 다운로드 및 저장
        if (not duplicateCheck(copy_name, 1)):
            urllib.request.urlretrieve(base_url + copy_src, "./license/" + copy_name)
            # print("새 라이선스 이미지 다운로드:", copy_name)

        # 상세정보 파싱
        table = soup.find(class_="tb_bbs").find_all("tr")

        # DB 속성 (a, b, c, ...)
        attr = "(_id,filename,path,license,license_name,"

        # DB 값 (v1, v2, v3, ...)
        attrValue = "(\"%s\",\"%s\",\"%s\",\"%s\",\"%s\"," % (_id, img_name, "./img/" + _id + ".png", copy_name, copy_text)

        # 컬럼 수
        cols = 0
        for info in table:
            title = info.find("th").text.replace("\\s", "")  # 상세정보 제목
            try:
                attr += attrQuery(title) + ","  # DB 컬럼명 추가
                value = ""
                if (title == u"저작자"):
                    authors = info.find("td").find_all("a")  # 저작자가 여러명인 경우
                    for author in authors:
                        value += author.text + ","  # 저작자가 여러명인 경우 , 문자로 구분
                else:
                    value = info.find("td").text

                value = value.strip()  # 양쪽 불필요한 공백 제거

                # DB 저장할 값 추가
                attrValue += "\"%s\"," % value.replace("\n", "").replace("\r", "").replace("\t", "").replace("\"", "")
                cols += 1
            except RuntimeError as e:
                print("Runtime Error:", e)
                save_log(_id, str(e))  # 로그에 저장

        file_name = _id + ".png"  # 저장 파일명

        # 파일이 존재하지 않는 경우에만 다운로드
        if not duplicateCheck(file_name):
            urllib.request.urlretrieve(base_url + img_src, "./img/" + file_name)
        else:
            print("이미 존재하는 이미지:", file_name)

        if thumbnail:
            # 썸네일 이미지가 없는 경우에만 생성
            if not duplicateCheck(file_name, 2):
                gen_thumbnail(file_name)
            else:
                print("이미 존재하는 썸네일 이미지:", file_name)

        # 맨 뒤에 컬럼 수 데이터 추가
        attr += "col_size)"
        attrValue += "%d)" % cols

        # DB 쿼리
        query = "INSERT INTO crawler %s VALUES %s" % (attr, attrValue)
        cur.execute(query)
        conn.commit()
    except AttributeError as e:  # 속성 오류
        print("Attribute Error:", e)
    except pymysql.err.IntegrityError as e:  # 중복, SQL 오류
        print("SQL Error:", e)
    except Exception as e:
        print("Error:", e)
        save_log(_id, str(e))  # 기타 예외사항은 로그에 기록
    print(_id)

# 게시글 URL에서 wrtSn의 값만 추출
def getCode(url):
    try:
        m = str(re.search(r"wrtSn=\d{4,12}", url).group())
        return re.sub("[^0-9]", "", m)
    except Exception as e:
        print("게시글 번호 추출 오류:", e)
        return "error"


# 중복체크, 파일 존재 유무 (0: 이미지, 1: 라이선스 이미지, 2: 썸네일 이미지)
def duplicateCheck(name, type=0):
    if type == 0:
        path = "./img/"
    elif type == 1:
        path = "./license/"
    elif type == 2:
        path = "./thumbnail/"
    else:
        path = "./img/"
    return os.path.exists(path + name)


# 항목 타이틀로 DB 컬럼 찾기
def attrQuery(title):
    title = title.replace(" ", "")
    if title == u"UCI":
        return "uci"
    elif title == u"ICN":
        return "icn"
    elif title == u"저작자":
        return "author"
    elif title == u"공동저작자":
        return "public_author"
    elif title == u"공표일자(년도)":
        return "publicate_date"
    elif title == u"창작일자(년도)":
        return "create_date"
    elif title == u"공표국가":
        return "publicate_contry"
    elif title == u"분류(장르)":
        return "classification"
    elif title == u"원문제공":
        return "original_text"
    elif title == u"요약정보":
        return "summary_info"
    elif title == u"관련태그":
        return "relation_tag"
    elif title == u"발행일자":
        return "publish_date"
    elif title == u"발행자":
        return "publisher"
    elif title == u"기여자":
        return "contributor"
    elif title == u"저작물명대체제목":
        return "alternate_title"
    elif title == u"저작물파일유형":
        return "substitute"
    elif title == u"저작물속성":
        return "attribute"
    elif title == u"수집연계유형":
        return "collect_type"
    elif title == u"수집연계대상명":
        return "collect_target"
    elif title == u"수집연계URL":
        return "collect_url"
    elif title == u"주언어":
        return "main_language"
    elif title == u"원저작물유형":
        return "original_type"
    elif title == u"원저작물창작일":
        return "original_date"
    elif title == u"원저작물크기":
        return "original_size"
    elif title == u"원저작물소장처":
        return "original_collection"
    else:
        raise RuntimeError("알 수 없는 상세정보")


# 썸네일 생성
def gen_thumbnail(src):
    img = Image.open("./img/" + src)
    if img.mode != "RGB":
        img = img.convert("RGB")
    img.thumbnail((200, 200))
    img.save("./thumbnail/" + src)


# 로그 저장
def save_log(msg, err=""):
    f = open("./log/crawler.log", "a", encoding="utf-8")
    f.write(msg + " : " + err + "\n")
    f.close()


if __name__ == "__main__":
    start_time = time.time()  # 시간 측정(시작)
    page = 1
    base_list_url = "https://gongu.copyright.or.kr/gongu/wrt/wrtCl/listWrt.do?menuNo=200023&wrtTy=4&depth2At=Y&pageIndex="
    process = []
    max_page = get_max_page(base_list_url + "1")
    max_page = 25
    for i in range(max_page): # 1 ~ 최대 페이지까지
        page_start = time.time()
        print("----------[ %d 페이지 ]----------" % (i+1))
        for link in get_links(base_list_url + str(i+1)): # 추출된 링크 수 만큼 프로세스 생성
            p = multiprocessing.Process(target=get_item_data, args=(link, True))
            process.append(p)
            p.start() # 프로세스 시작

        for p in process:
            p.join() # 프로세스 종료 대기
        print("[ %d 페이지 크롤링 소요시간: %s 초 ] -- %s%% (%d/%d)" % (i+1, round(time.time() - page_start, 3), round((i+1)/max_page*100, 3), i+1, max_page))
    else:
        print("[ 전체 크롤링 소요시간: %s 초 ]" % (round(time.time() - start_time, 3)))

