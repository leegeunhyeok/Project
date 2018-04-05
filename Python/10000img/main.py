from bs4 import BeautifulSoup
import urllib.request
import time
import os

def get(max_count = 1):
    start = time.time() # 크롤링 시작 시간
    base_url = "http://10000img.com/" # 이미지 src와 조합하여 다운받을 주소
    url = "http://10000img.com/ran.php" # 접속할 URL 

    count = 1
    while count <= max_count:
        print("+---------[ %d번 째 이미지 ]---------+" % count)

        html = urllib.request.urlopen(url)
        source = html.read()

        soup = BeautifulSoup(source, "html.parser")

        img = soup.find("img")  # 이미지 태그
        img_src = img.get("src") # 이미지 경로
        img_url = base_url + img_src # 다운로드를 위해 base_url과 합침
        img_name = img_src.replace("/", "") # 이미지 src에서 / 없애기

        if not duplicate(img_name):
            urllib.request.urlretrieve(img_url, "./img/" + img_name)
        else:
            print("중복된 이미지!")

        print("이미지 src:", img_src)
        print("이미지 url:", img_url)
        print("이미지 명:", img_name)
        print("\n")
        count += 1 # 갯수 1 증가
    else:
        print("크롤링 종료")
        print("크롤링 소요 시간:", round(time.time() - start, 6)) # 소수점 아래 6자리까지

def duplicate(img):
    return os.path.exists("./img/" + img) # 존재하면 True, 없으면 False

num = int(input("이미지 수: "))
get(num)

