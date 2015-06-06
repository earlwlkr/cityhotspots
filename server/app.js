var app = require('express')(),
    mongoose = require('mongoose');

mongoose.connect('mongodb://earlwlkr:test123@proximus.modulusmongo.net:27017/Er8ovana/cityhotspots');
var db = mongoose.connection;
db.on('error', console.error.bind(console, 'connection error:'));

db.once('open', function (callback) {
  // GET: /malls
  app.get('/malls', function(req, res) {
    var docs = [
      {
        name: 'Sài Gòn Square',
        address: {
          city: 'TP.HCM',
          country: 'Vietnam',
          district: 'Quận 1',
          street_address: '7 Tôn Đức Thắng'
        }
      },
      {
        name: 'Diamond Plaza',
        address: {
          city: 'TP.HCM',
          country: 'Vietnam',
          district: 'Quận 1',
          street_address: '34 Lê Duẩn'
        }
      }
    ];
    res.json(docs);
  });

  // GET: /cinemaoptions
  app.get('/cinemaoptions', function(req, res) {
    var collection = db.collection('cinemas');

    collection.distinct('trademark', function(err, result) {
      var ret = {};
      ret['trademarks'] = ['Tất cả'];
      for (var i = 0; i < result.length; i++) {
        // If not null, push to return object
        if (result[i]) {
          ret['trademarks'].push(result[i]);
        }
      }
      res.send(ret);
    });
  });

  // GET: /cinemas
  app.get('/cinemas', function(req, res) {
    var collection = db.collection('cinemas');
    var obj = {};
    if (req.query.trademark && req.query.trademark != 'Tất cả') {
      obj['trademark'] = req.query.trademark;
    }
    collection.find(obj).toArray(function(err, docs) {
      res.json(docs);
    });      
  });

  // GET: /dineroptions
  app.get('/dineroptions', function(req, res) {
    var collection = db.collection('dineroptions');
    collection.findOne(function(err, docs) {
      res.json(docs);
    });
  });

  // GET: /diners
  // Params: cuisine, district, category
  app.get('/diners', function(req, res) {
    var collection = db.collection('diners');
    
    var obj = {};
    if (req.query.cuisine && req.query.cuisine != 'Tất cả') {
      obj['cuisine'] = req.query.cuisine;
    }
    
    if (req.query.district && req.query.district != 'Tất cả') {
      obj['address.district'] = req.query.district;
    }
    
    if (req.query.category && req.query.category != 'Tất cả') {
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

