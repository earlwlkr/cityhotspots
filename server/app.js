var MongoClient = require('mongodb').MongoClient, 
    app = require('express')(),
    assert = require('assert');

// Connection URL
var url = 'mongodb://localhost:27017/cityhotspots';
var database;

// Use connect method to connect to the Server
MongoClient.connect(url, function(err, db) {
  assert.equal(null, err);
  console.log("Successfully connected to server!");
  database = db;
});

// GET: /diners
// Params: cuisine, district, category
app.get('/diners', function(req, res) {
  var collection = database.collection('diners');
  
  var obj = {};
  if (req.query.cuisine) {
    obj['cuisine'] = req.query.cuisine;
  }
  
  if (req.query.district) {
    obj['address.district'] = req.query.district;
  }
  
  if (req.query.category) {
    obj['category'] = parseInt(req.query.category);
  }
  
  
  var options = {
    'limit': 50
  };

  collection.find(obj, options).toArray(function(err, docs) {
    console.dir(obj);
    res.json(docs);
  });      
});

var server = app.listen(3000, function() {
  var host = server.address().address;
  var port = server.address().port;

  console.log('App listening at http://%s:%s', host, port);

});