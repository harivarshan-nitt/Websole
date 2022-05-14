const dbUrl = 'mongodb://localhost:27017';

const mongoose = require('mongoose');

mongoose.connect(dbUrl+'/websole',
{useNewUrlParser:true,useUnifiedTopology:true}).then(()=>{
    console.log("Connected to DB");
}).catch((error)=>{
    console.log(error);
});
  