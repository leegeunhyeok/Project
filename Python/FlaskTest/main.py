from flask import Flask, render_template, redirect, json, request
import pymysql

app = Flask(__name__)

# Get db connection
conn = pymysql.connect(host="localhost", user="root", password="1234", db="python", charset="utf8")
cur = conn.cursor()

@app.route("/")
def main():
    return render_template("index.html")

@app.route("/login")
def login():
    return render_template("login.html")

@app.route("/process/login", methods=["POST"])
def process_login():
    # user id
    _id = request.form['id']

    # user password
    _password = request.form['password']

    # query , result = selected row(s) count
    result = cur.execute("SELECT * FROM flask_user WHERE _id='%s' and password='%s'" % (_id, _password))

    if result > 0:
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
            cur.execute("INSERT INTO flask_user VALUES (%s, %s)", (_id, _password))
            conn.commit()
            print "Join new user: %s %s" % (_id, _password)
            return "<script>alert('Hello new user, %s');location.href = '/login'</script>" % _id
        else:
            return "<script>alert('Check your password');location.href = '/signup'</script>"
    else:
        print "%s is already exists" % _id
        return "<script>alert('This id is alreay exists');location.href = '/signup'</script>"

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
    app.run(threaded = True)
else:
    print "Not main : %s" % __name__