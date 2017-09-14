var app = require('express')(); // import 개념
var http = require('http');
//var app = express(); // express 모듈의 express()를 호출하면 application 객체를 반환해줌

// 세팅 
app.set('view engine', 'jade'); // jade 사용
app.set('views', './views');


// 동작 구현
app.get('/', function(req, res){ // root(메인)로 접속했을 경우
	var code = '<a href="test">Event</a><br><a href="login">Login</a><br><br>';
	res.send(code);
});

app.get('/test', function(req, res){
	var data = ['Java', 'JavaScript', 'Node.js'];
	var code = '<p><a href="?id=0">Java</a><br><a href="?id=1">JavaScript</a><br><a href="?id=2">Node.js</a></p><br><br><a href="../">Back</a><br>';
	res.send(code + data[req.query.id]);
});

app.get('/login', function(req, res){ // /login 으로 접속했을 경우
	res.render('index', {time:Date()});
});

app.get('/hello', function(req, res){
	res.send('<p>Hello, ' + req.query.name + '</p><br><a href="../">Back</a>');
});

app.get('*', function(req, res){ // 없는 페이지로 이동했을 경우
	  res.render('error', {error:404});
});


http.Server(app).listen(3000, function(){
  console.log('Express server listening on port 3000');
});
