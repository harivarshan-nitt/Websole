const express = require('express');
const bodyParser = require('body-parser');
const cp = require('child_process');
const cors = require('cors');
const bcrypt = require("bcrypt");
const { compare } = require('bcrypt');
const jwt = require("jsonwebtoken");
var rand = require("random-key");

require('./db/mongo');
const dbSchema = require('./db/user');

const cons = cp.spawn('./websole.sh', [], {shell: true ,
    stdio: ['ignore', 'ignore', 1, 'ipc']
});

var secretKey = rand.generate(100);

const app = express();

app.use(cors({origin: '*'}));
app.use(bodyParser.urlencoded({ extended: false }));
app.use(bodyParser.json());
app.listen(2000);

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
                    cons.send(req.body.data);
                    cons.on('message', (data) => {
                        responseData.data= data;
                        responseData.msg = "success";
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
                    { expiresIn: "10m" }
                    );
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
                        { expiresIn: "10m" }
                        );
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


