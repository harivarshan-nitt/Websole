const mg = require('mongoose');

const dbSchema=new mg.Schema({
    username: {
    type: String,
    required: true
    },
    password: {
    type: String,
    required: true
    }
});
const DBSchema=mg.model('Details',dbSchema);
module.exports=DBSchema;  