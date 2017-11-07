/*--------------------------*
 * --- NodeJS WebServer --- *
 * -		            *
 * -Dev: Leegeunhyeok       *
 * -Start day : 2017-11-02  *
 * 			    *
 * ---- Raspberry PI 3 ---- *
 *--------------------------*/

var express = require('express'),
	http = require('http');

var path = require('path'),
	fs = require('fs'),
	static = require('serve-static'),
	bodyParser = require('body-parser');

var cookieParser = require('cookie-parser'),
	session = require('express-session');

//var mongoose = require('mongoose');

var app = express();
/* var db = mongoose.connection;
db.on('error', console.error);
db.once('open', function(){
	console.log('Connecting MongoDB');
});
*/

app.set('port', 8080);
app.set('views', __dirname + '/views');
app.set('view engine', 'ejs');

app.use('/js', static(path.join(__dirname, 'js')));
app.use('/css', static(path.join(__dirname, 'css')));
app.use('/image', static(path.join(__dirname, 'image')));
app.use('/public', static(path.join(__dirname, 'public')));

app.use(cookieParser());
app.use(bodyParser.urlencoded({extended: false}));
app.use(bodyParser.json());
app.use(session({
	secret:'Raspberry@Web0509',
	resave: false,
	saveUninitialized: true
}));

var router = express.Router();

router.route('/').get(function(req, res){
	var sess = req.session.user;
	var userName = sess != null ? sess.name : null;
	res.render('index', {name: userName});
});

router.route('/login').post(function(req, res){
    var email = req.body.email;
    var password = req.body.password;
    
    if(req.session.user){
        res.send('<script type="text/javascript">alert("이미 로그인 상태입니다.");location.href="/"</script>');
    } else {
        console.log('Try login: ' + email + ' / ' + password);
        req.session.user = {
            id: email,
            name: email,
            authorized: true
        };
        res.redirect('/');
    }
});

router.route('/logout').get(function(req, res){
	if(req.session.user){
		req.session.destroy();
	}
	res.redirect('/');
});

router.route('/account').get(function(req, res){
	if(req.session.user){
		res.redirect('/');
	} else {
		res.send('<h1>New account</h1>');
	}
});

router.route('/myinfo').get(function(req, res){
    if(req.session.user){
        res.redirect('/public/myinfo.html');
    } else {
        res.redirect('/');
    }
});

router.route('/session').get(function(req, res){
	if(req.session.user){
		res.writeHead(200, {'Content-Type':'text/html'});
		res.write('User: ' + req.session.user.name);
		res.end();
	} else {
		res.writeHead(200, {'Content-Type':'text/html'});
		res.write('No Session');
		res.end();
	}
});

app.use(router);

http.createServer(app).listen(app.get('port'), function(){
	console.log('NodeJS Server start.');
});
