var amqp = require('amqplib/callback_api');

function send(name){
    amqp.connect('amqp://localhost', function(error0, connection) {
    if (error0) {
        throw error0;
    }
    connection.createChannel(function(error1, channel) {
        if (error1) {
            throw error1;
        }
        var queue = 'encode';
        var msg = name;

        channel.assertQueue(queue, {
            durable: false
        });
        channel.sendToQueue(queue, Buffer.from(msg));
        console.log(" [x] Sent %s", msg);
    });
    setTimeout(function() {
        connection.close();
        process.exit(0);
    }, 500);
});

}


var express=require('express');
var app =express();
const bodyParser = require('body-parser');
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: false }));



//设置跨域访问
app.all('*', function(req, res, next) {
    res.header("Access-Control-Allow-Origin", "*");
    res.header("Access-Control-Allow-Headers", "X-Requested-With");
    res.header("Access-Control-Allow-Methods","PUT,POST,GET,DELETE,OPTIONS");
    res.header("X-Powered-By",' 3.2.1');
    res.header("Content-Type", "application/json;charset=utf-8");
    next();
 });

var amqp = require('amqplib/callback_api');

function send(name){
    amqp.connect('amqp://localhost', function(error0, connection) {
        if (error0) {
            throw error0;
        }
        connection.createChannel(function(error1, channel) {
            if (error1) {
                throw error1;
            }
            var queue = 'encode';
            var msg = name;

            channel.assertQueue(queue, {
                durable: false
            });
            channel.sendToQueue(queue, Buffer.from(msg));
            console.log(" [x] Sent %s", msg);
        });
        setTimeout(function() {
            connection.close();
            // process.exit(0);
        }, 500);
    });
}


var Minio=require('minio');
var minioClient =new Minio.Client({
    endPoint:'127.0.0.1',
    port: 9000,
    accessKey:'admin',
    secretKey:'12345678',
    useSSL: false
});

var minioBucket = '1080p'
var metaData = {
    'X-Amz-Meta-Testing': 1234,
    'example': 5678
}


var rabbitmsg="success.mp4"
var file='./video/output.mp4'

 var status={ code: 0};

app.post('/up',function(request,response){
    console.log(request.body.name);
//    rabbitmsg=request.body.name;
    minioClient.fPutObject(minioBucket, rabbitmsg,file, metaData, function(err, etag) {
        if (err) return console.log(err)
        console.log('File uploaded successfully.')
        let msg="2021 "+rabbitmsg;
        send(msg);
    });
    response.json(status);
});



//配置服务端口
var server = app.listen(3001, function () {
    var host = server.address().address;
    var port = server.address().port;
    console.log('Example app listening at http://%s:%s', host, port);
})

