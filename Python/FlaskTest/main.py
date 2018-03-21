from flask import Flask, render_template, redirect, json, request, session
import pymysql
import datetime

app = Flask(__name__)

# Get db connection
conn = pymysql.connect(host="localhost", user="root", password="1234", db="python", charset="utf8")
cur = conn.cursor()

@app.route("/")
def main():
    return render_template("index.html")

@app.route("/logout")
def logout():
    try:
        session.pop('user', None)
        return '<script>alert("Logout success");location.href = "/"</script>'
    except:
        return '<script>location.href = "/"</script>'

@app.route("/login")
def login():
    return render_template("login.html")

@app.route("/process/loginCheck", methods=["POST"])
def process_login_check():
    try:
        user = session['user']
        return json.dumps({"result": user})
    except:
        return json.dumps({"result": False})

@app.route("/process/getContent", methods=["POST"])
def get_content():
    cur.execute("SELECT * FROM content")
    rows = cur.fetchall()
    data = []
    for row in rows:
        data.append([row[0], row[1], row[3], row[4]])

    return json.dumps({"data": data})

@app.route("/process/getPost", methods=["POST"])
def get_post():
    _cid = request.form["cid"]
    cur.execute("SELECT title, content, date FROM content WHERE _cid = %s", (_cid))
    data = cur.fetchall()[0]
    return json.dumps({"title":data[0], "content":data[1], "date":data[2]})

@app.route("/process/deletePost", methods=["POST"])
def delete_post():
    _cid = request.form["cid"]
    _id = request.form["id"]
    print _cid, _id
    try:
        permission = session["user"]["permission"]
        id = session["user"]["_id"]
        print id
        if permission or id == _id:
            cur.execute("DELETE FROM content WHERE _cid = %s", (_cid))
            conn.commit()
            return json.dumps({"result": True, "message":"Done!"})
        else:
            return json.dumps({"result": False, "message":"Admin or %s can delete" % _id})
    except:
        return json.dumps({"result": False, "message":"Access denied."})

@app.route("/process/login", methods=["POST"])
def process_login():
    # user id
    _id = request.form['id']

    # user password
    _password = request.form['password']

    # query , result = selected row(s) count
    result = cur.execute("SELECT * FROM flask_user WHERE _id='%s' and password='%s'" % (_id, _password))

    if result > 0:
        user = cur.fetchall()[0] # first user data
        session['user'] = {"_id": user[0], "permission":user[2]} # Add user data at session
        return '<script>alert("Hello, %s");location.href = "/"</script>' % _id
    else:
        return '<script>alert("Access denied.");location.href = "/"</script>'

@app.route("/signup")
def signup():
    return render_template("signup.html")

@app.route("/process/signup", methods=["POST"])
def process_signup():
    _id = request.form['id']
    _password = request.form['password']
    _password_re = request.form['password_re']
    try:
        _permission = request.form['permission'] and 1 or 0
    except:
        _permission = 0

    check_id = check(_id)
    check_password = check(_password)

    if not _id or not _password or not _password_re:
        return "<script>alert('You must fill data');location.href = '/signup'</script>"

    if check_id or check_password:
        if check_id:
            return "<script>alert('Check id');location.href = '/signup'</script>"
        else:
            return "<script>alert('Check password');location.href = '/signup'</script>"


    result = cur.execute("SELECT * FROM flask_user WHERE _id='%s'" % _id)
    if result == 0:
        if _password == _password_re:
            cur.execute("INSERT INTO flask_user VALUES (%s, %s, %s)", (_id, _password, _permission))
            conn.commit()
            print "Join new user: %s %s" % (_id, _password)
            return "<script>alert('Hello new user, %s');location.href = '/login'</script>" % _id
        else:
            return "<script>alert('Check your password');location.href = '/signup'</script>"
    else:
        print "%s is already exists" % _id
        return "<script>alert('This id is alreay exists');location.href = '/signup'</script>"

# Write post
@app.route("/process/write", methods=["POST"])
def write():
    try:
        n = datetime.datetime.now()
        _cid = n.strftime("%Y%M%S%f")
        _id = session['user']['_id']
        _title = request.form['title']
        _content = request.form['content']
        _date = n.strftime("%Y-%m-%d")
        cur.execute("INSERT INTO content VALUES(%s, %s, %s, %s, %s)", (_cid, _title, _content, _date, _id))
        conn.commit()
        return "<script>alert('Done!');location.href = '/'</script>"
    except:
        return "<script>alert('Please login');location.href = '/login'</script>"

# Edit post
@app.route("/process/modifyPost", methods=["POST"])
def modify():
    try:
        user = session['user']['_id']
        title = request.form['title']
        print title
        content = request.form['content']
        print content
        id = request.form['id']
        cid = request.form['cid']
        print id, cid

        if user == id:
            cur.execute("UPDATE content SET title = %s, content = %s WHERE _cid = %s", (title, content, cid))
            conn.commit()
            return "<script>alert('Done!');location.href = '/'</script>"
        else:
            return "<script>alert('Only %s can edit');location.href = '/'</script>" % id
    except:
        return "<script>alert('Please login');location.href = '/login'</script>"

@app.route("/user")
def user():
    try:
        user = session['user']['permission']
        if user == 1:
            return render_template("user.html")
        else:
            return "<script>alert('Only admin!'); location.href='/'</script>"
    except:
        return "<script>alert('Please login'); location.href='/'</script>"

@app.route("/connect")
def connect():
    return render_template("connect.html")

def check(str):
    loop = 0
    for c in str:
        count = 0
        for temp in str[loop:]: # List slice
            if count >= 2:
                return True
            elif c == temp:
                count+=1
            else:
                break
        loop+=1
    else:
        return False



if __name__ == "__main__":
    print "Starting at %s" % __name__
    app.secret_key = "key_value_0509"
    app.run(threaded = True)
else:
    print "Not main : %s" % __name__