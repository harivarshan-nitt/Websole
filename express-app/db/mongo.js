var dbUrl = process.env.DB_URL;

const mongoose = require('mongoose');

mongoose.connect(dbUrl+'/websole',
{useNewUrlParser:true,useUnifiedTopology:true}).then(()=>{
    console.log("Connected to DB");
}).catch((error)=>{
    console.log(error);
});
  