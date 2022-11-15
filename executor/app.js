const process = require('node:process');
const WebSocket = require('ws');
const ws = new WebSocket('ws://localhost:5000/ws/executor');

const {addClient,removeClient,executeCommand} = require('./Shell')

ws.on('open', function open() {
    console.log("Connected to WebSocket Server");
});

ws.on('message', function message(req) {
    req = JSON.parse(req);
    if(req.topic == "CONNECT")
    {
        addClient(req.clientId,responseHandler);
    }
    if(req.topic == "DISCONNECT")
    {
        removeClient(req.clientId);
    }
    if(req.topic == "COMMAND")
    {
        executeCommand(req.clientId,req.command);
    }
});

ws.on('error', function errorFunc(err) {
    console.log(err);
});

var responseHandler = function (res,clientId)
{   
    var packet = {};
    packet.response = res;
    packet.clientId = clientId;
    ws.send(JSON.stringify(packet));    
}

process.on('SIGINT', (code) => {
    ws.close()
    process.exit()
});
