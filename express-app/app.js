const express = require('express');
const bodyParser = require('body-parser');
const cp = require('child_process');
const cors = require('cors');
const bcrypt = require("bcrypt");
const { compare } = require('bcrypt');
const jwt = require("jsonwebtoken");
var rand = require("random-key");
require('dotenv').config({ path: './.env' });

const PORT = process.env.PORT;

const timeOut = "10m";
const timeOutMs = 10*60*1000;

require('./db/mongo');
const dbSchema = require('./db/user');

var usersTerminal={};

var secretKey = rand.generate(100);

const app = express();

app.use(cors({origin: '*'}));
app.use(bodyParser.urlencoded({ extended: false }));
app.use(bodyParser.json());
app.use(express.static('public'));
app.listen(PORT);

app.post('/websole', async(req, res) =>{
    
    var jwtToken = req.body.jwt;
    var responseData = {};
    if(!jwtToken)
    {
        responseData.msg = "Login to access Websole !";
        res.end(JSON.stringify(responseData));
    }
    else
    {
        try
        {
            const decodedToken = jwt.verify(jwtToken,secretKey );
            try
            {
                const user = await dbSchema.findOne({username:decodedToken.username});
                if(user)
                {
                    if(!usersTerminal[jwtToken])
                    {
                        var userTerminal = cp.spawn('./websole.sh', [], {shell: true ,
                            stdio: ['ignore', 'ignore', 1, 'ipc']
                        });
                        usersTerminal[jwtToken] = userTerminal;
                    }
                    
                    usersTerminal[jwtToken].send(req.body.data);
                    usersTerminal[jwtToken].on('message', (data) => {
                        responseData.data= data;
                        responseData.msg = "success";
                        usersTerminal[jwtToken].removeAllListeners();
                        res.end(JSON.stringify(responseData));
                    });
                }
                else
                {
                    responseData.msg = "Login to access Websole !";
                    res.end(JSON.stringify(responseData));
                }
            }
            catch(error){
                responseData.msg = "UnExpected Error";
                res.end(JSON.stringify(responseData));
            }
        }
        catch(error){
            responseData.msg = "Login to access Websole !";
            res.end(JSON.stringify(responseData));
        }
        
    } 
});

app.post('/login',async(req,res)=>{
    try
    {
        const user =await dbSchema.findOne({username:req.body.username});
        if(user)
        {
            const EncryptPassword = await bcrypt.compare(req.body.password, user.password);
            if(EncryptPassword)
            {
                let token;
                try 
                {
                    token = jwt.sign(
                    { username: user.username },
                    secretKey,
                    { expiresIn: timeOut }
                    );
                    setTimeout(clearJWT,timeOutMs,token);
                } catch (err) 
                {
                    res.end(JSON.stringify({response:"UnExpected Error"}));
                }
                res.end(JSON.stringify({response:"success",jwt:token}));
            }
            else res.end(JSON.stringify({response:"Invalid Credentials"}));
        }
        else res.end(JSON.stringify({response:"Invalid Credentials"}));
    }
    catch(error){
        res.end(JSON.stringify({response:"UnExpected Error"}));
    }
});

app.post('/sigin',async(req,res)=>{
    try
    {
        const userDb =await dbSchema.findOne({username:req.body.username});
        if(userDb) res.end(JSON.stringify({response:"Username Exists"}));
        else
        {
            user = {}
            user.username = req.body.username;
            const salt = await bcrypt.genSalt(10);
            user.password = await bcrypt.hash(req.body.password, salt);
            dbSchema.create(user).then((data)=>{
                let token;
                    try 
                    {
                        token = jwt.sign(
                        { username: user.username },
                        secretKey,
                        { expiresIn: timeOut }
                        );
                        setTimeout(clearJWT,timeOutMs,jwt);
                    } catch (err) 
                    {
                        res.end(JSON.stringify({response:"UnExpected Error"}));
                    }
                    res.end(JSON.stringify({response:"success",jwt:token}));
            }).catch((error)=>{
                res.end(JSON.stringify({error}));
            })
        }
    }
    catch(error){
        res.end(JSON.stringify({response:"UnExpected Error"}));
    }
});

app.get("*", function (req, res) {
    res.redirect('/');  
});

function clearJWT(jwt)
{   
    usersTerminal[jwt].disconnect();
    delete usersTerminal[jwt];
}
