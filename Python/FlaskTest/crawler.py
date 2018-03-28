from bs4 import BeautifulSoup
import urllib2
import pymysql

# DB Connection
conn = pymysql.connect(host="localhost", user="root", password="1234", db="python", charset="utf8")
cur = conn.cursor()

# Data crawling
def crawler():
    url = "https://www.rottentomatoes.com/"
    html = urllib2.urlopen(url)
    source = html.read()

    soup = BeautifulSoup(source, "html.parser")

    table = soup.find(id="Top-Box-Office")
    movies = table.find_all("tr")

    # Movie data list
    data = []

    for m in movies:
        score = m.find(class_="tMeterScore").get_text().strip()
        title = m.find(class_="middle_col").get_text().strip()
        link = m.find(class_="left_col").a.get("href")
        data.append({
            "title": title,
            "link": link,
            "score": score
        })
        print "[%s]\nScore: %s\nLink: %s\n" % (title, score, url + link)
    return data

# Data save
def save_db(list):
    for d in list:
        cur.execute("INSERT INTO movie VALUES (%s, %s, %s)", (d["title"], d["link"], d["score"]))
        conn.commit()
    else:
        print "Done!"

# Get data and save to db
data = crawler()
save_db(data)
