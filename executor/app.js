const cp = require('child_process');

var usersTerminal={};

function run(jwtToken){
    if(!usersTerminal[jwtToken])
    {
        var userTerminal = cp.spawn('./websole.sh', [], {shell: true ,
            stdio: ['ignore', 'ignore', 1, 'ipc']
        });
        usersTerminal[jwtToken] = userTerminal;
    }
    usersTerminal[jwtToken].send(req.body.data);
    usersTerminal[jwtToken].on('message', (data) => {
        usersTerminal[jwtToken].removeAllListeners();
    });
}

function clearJWT(jwt)
{   
    usersTerminal[jwt].disconnect();
    delete usersTerminal[jwt];
}
