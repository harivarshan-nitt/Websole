const mongoose = require('mongoose');
mongoose.connect('mongodb://localhost:27017/websole',
{useNewUrlParser:true,useUnifiedTopology:true}).then(()=>{
    console.log("Connected to DB");
}).catch((error)=>{
    console.log(error);
});
  