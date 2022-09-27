const WebSocket = require('ws');
const ws = new WebSocket('ws://localhost:3000/ws/test');

const readline = require('readline').createInterface({
    input: process.stdin,
    output: process.stdout
});

var count = 0;
var To = "";

ws.on('open', function open() {
    console.log("Connected");

    readline.on('line',(msg) =>{
        if(count==0){
            To = msg;
            count++;
        }
        else
        {
            var packet ={}
            packet.to = To;
            packet.message = msg;
            ws.send(JSON.stringify(packet));
        }
    }); 
});

ws.on('message', function message(data) {
  console.log('Received: %s', data);
});

ws.on('error', function errorFunc(err) {
    console.log(err);
});



