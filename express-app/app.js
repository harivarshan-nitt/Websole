const express = require('express');
const bodyParser = require('body-parser');
const cp = require('child_process');

const console = cp.spawn('./websole.sh', [], {shell: true ,
    stdio: ['ignore', 'ignore', 1, 'ipc']
});

const app = express();

app.use(bodyParser.urlencoded({ extended: false }));
app.use(bodyParser.json());
app.listen(2000);

app.post('/websole', (req, res) =>{
    res.setHeader('Content-Type', 'application/json');
    console.send(req.body.data);
    var responseData={
        data:""
    }
    console.on('message', (data) => {
        responseData.data= data;
        res.end(JSON.stringify(responseData));
    });
})
