const cp = require('child_process');

var clientShells={};

function executeCommand(clientId,command){
    clientShells[clientId].send(command);
}

function removeClient(clientId)
{   
    clientShells[clientId].removeAllListeners();
    clientShells[clientId].disconnect();
    delete clientShells[clientId];
}

function addClient(clientId,callback)
{   
    var clientShell = cp.spawn('./shellProcess.sh', [], {shell: true ,
        stdio: ['ignore', 'ignore', 1, 'ipc']
    });
    clientShells[clientId] = clientShell;
    clientShells[clientId].on('message', (res) => {
        callback(res,clientId);
    });
}
module.exports = {addClient,removeClient,executeCommand}