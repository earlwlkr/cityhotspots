var app = require('express')(),
    mongoose = require('mongoose');

mongoose.connect('mongodb://earlwlkr:test123@proximus.modulusmongo.net:27017/Er8ovana/cityhotspots');
var db = mongoose.connection;
db.on('error', console.error.bind(console, 'connection error:'));

db.once('open', function (callback) {
  // GET: /dineroptions
  app.get('/dineroptions', function(req, res) {
    var collection = db.collection('diners');
    var ret = {};

    collection.distinct('cuisine', function(err, result) {
      ret['cuisines'] = result;
      collection.distinct('address.district', function(err, result) {
        ret['districts'] = result;
        collection.distinct('category', function(err, result) {
          ret['categories'] = result;
          res.send(ret);
        });
      });
    });
  });

  // GET: /diners
  // Params: cuisine, district, category
  app.get('/diners', function(req, res) {
    var collection = db.collection('diners');
    
    var obj = {};
    if (req.query.cuisine) {
      obj['cuisine'] = req.query.cuisine;
    }
    
    if (req.query.district) {
      obj['address.district'] = req.query.district;
    }
    
    if (req.query.category) {
      obj['category'] = req.query.category;
    }
    
    if (req.query.price_min) {
      obj['price_min'] = {$gte: parseInt(req.query.price_min)};
    }
    
    if (req.query.price_max) {
      obj['price_max'] = {$lte: parseInt(req.query.price_max)};
    }
    
    if (req.query.time_arrival) {
      var date = new Date(Date.UTC(1900, 0, 1, req.query.time_arrival));
      obj['open_time'] = {$lt: date};
      obj['close_time'] = {$gt: date};
    }
    
    
    var options = {
      'limit': 50
    };

    collection.find(obj, options).toArray(function(err, docs) {
      console.dir(obj);
      res.json(docs);
    });      
  });

  var server = app.listen(process.env.PORT, function() {
    var port = server.address().port;
    console.log('App listening at port %s', port);
  });
});

