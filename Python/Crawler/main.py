#-*- coding: utf-8 -*-
from bs4 import BeautifulSoup
import urllib.request
import pymysql
import os
import re
#import time # 시간

conn = pymysql.connect(host="localhost", user="root", password="1234", db="python", charset="utf8")
cur = conn.cursor()

# 크롤링
# 최대 페이지, 현재 페이지(시작)
def crawler(max_page=1, page=1, tick=1):
    #start_time = time.time() # 시간 측정(시작)

    # 공유마당 기본 URL
    base_url = "https://gongu.copyright.or.kr"

    # 공유마당 게시글 목록 URL
    base_list_url = "https://gongu.copyright.or.kr/gongu/wrt/wrtCl/listWrt.do?menuNo=200023&wrtTy=4&depth2At=Y&pageIndex="
    count = 0 # 조회된 아이템 수(저작물 항목)
    success = 0 # 다운로드 받은 이미지 수

    while max_page >= page:
        url = base_list_url + str(page)
        print("----------[ %d 페이지 ]----------" % page)
        print(url)
        html = urllib.request.urlopen(url)
        source = html.read()

        soup = BeautifulSoup(source, "html.parser")

        list = soup.find(id="wrtList") # 아이템(저작물) 목록
        items = list.find_all("li") # 아이템(저작물)

        for item in items:
            count += 1 # 전체 아이템 수 증가
            success += get_item_data(base_url, item.find("a").get("href"))

        print("--------[ %d 페이지 끝 ]--------" % page)
        page += tick
    else:
        print("[ 전체 저작물 수: %d ]" % count)
        print("[ 다운로드 받은 저작물: %d/%d ]" % (success ,count))
        #print("[ %s 초 ]" % (round(time.time() - start_time, 3)))

# 저작물의 이미지, 라이선스, 상세정보 파싱
# 공유마당 기본 URL, 상세정보 URL
def get_item_data(base_url, href):
    error = True # 오류 여부
    url = base_url + href
    print(url)

    html = urllib.request.urlopen(url)
    source = html.read()

    soup = BeautifulSoup(source, "html.parser")
    try:
        _id = getCode(url)
        img_src = soup.find(class_="imgD").find("img").get("src") # 저작물 이미지 src
        img_name = soup.find(class_="tit_txt3").text # 이미지 명
        copy_src = soup.find(class_="copyD").find("img").get("src") # 라이선스 이미지 src
        copy_name = copy_src.split("/")[-1] # 라이선스 이미지 명

        # 라이선스 파일이 없을 때 다운로드 및 저장
        if(not duplicateCheck(copy_name, 1)):
            urllib.request.urlretrieve(base_url + copy_src, "./license/" + copy_name)
            print("새 라이선스 이미지 다운로드:", copy_name)

        # 상세정보 파싱
        table = soup.find(class_="tb_bbs").find_all("tr")

        # DB 속성 (a, b, c, ...)
        attr = "(_id,filename,path,license,"

        # DB 값 (v1, v2, v3, ...)
        attrValue = "(\"%s\",\"%s\",\"%s\",\"%s\"," % (_id, img_name, "./img/" + _id + ".png", copy_name)

        # 컬럼 수
        cols = 0
        for info in table:
            title = info.find("th").text.replace("\\s", "") # 상세정보 제목
            try:
                attr += attrQuery(title) + ","  # DB 컬럼명 추가
                value = ""
                if (title == u"저작자"):
                    authors = info.find("td").find_all("a") # 저작자가 여러명인 경우
                    for author in authors:
                        value += author.text + " "
                else:
                    value = info.find("td").text

                # DB 저장할 값 추가
                attrValue += "\"%s\"," % value.replace("\n","").replace("\r","").replace("\t","").replace("\"","")
                cols += 1
            except RuntimeError as e:
                print("Runtime Error:", e)
            
        file_name = _id + ".png" # 저장 파일명

        # 파일이 존재하지 않는 경우에만 다운로드
        if not duplicateCheck(file_name):
            urllib.request.urlretrieve(base_url + img_src, "./img/" + file_name)
            print("이미지 다운로드 완료")
            error = False
        else:
            print("이미 존재하는 이미지:", file_name)

        # 맨 뒤에 컬럼 수 데이터 추가
        attr += "col_size)"
        attrValue += "%d)" % cols

        # DB 쿼리
        query = "INSERT INTO crawler %s VALUES %s" % (attr, attrValue)
        cur.execute(query)
        conn.commit()
    except AttributeError as e: # 속성 오류
        print("Attribute Error:", e)
    except pymysql.err.IntegrityError as e: # 중복, SQL 오류
        print("SQL Error:", e)

    print("\n")
    if error: # 에러가 있으면 다운로드 된 이미지 +0
        return 0
    else: # 에러가 없으면 다운로드 된 이미지 +1
        return 1

# 게시글 URL에서 wrtSn의 값만 추출
def getCode(url):
    try:
        m = str(re.search(r"wrtSn=\d{4,12}", url).group())
        return re.sub("[^0-9]", "", m)
    except Exception as e:
        print("게시글 번호 추출 오류:", e)
        return "error"

# 중복체크, 파일 존재 유무 (0: 이미지, 1: 라이선스 이미지)
def duplicateCheck(name, type=0):
    path = type == 0 and "./img/" or "./license/"
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
        return "collect_url"
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

if __name__ == "__main__":
    # 최대 페이지, 시작페이지, 몇 페이지씩 건너뛸지
    crawler(int(input("페이지 입력: ")), 1, 1)

