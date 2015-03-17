var app = require('express')();
var http = require('http');
var server = http.Server(app);

var config = require('./config/config_D').configApp();
var io = require('socket.io')(server);

var users = {};

io.on('connection', function(socket){
  var origin = socket.handshake.headers["origin"];
  var secure = socket.handshake.secure;
  
  if (origin != config.socket.clientUrl || ( secure && config.socket.justSecure))
    console.log('Unauthorized client: ' + origin + " secure "+ secure);
  else 
    setupSocket(socket);
});

function setupSocket (socket) {
  console.log ('setupSocket');
  socket.on('disconnect', function(){
     console.log('user disconnected');
  });

  socket.on('add user', function(username){
      var user = {
          socket : socket
      };
      users [username] = user;
      console.log ('user add ' + username);
  });

  socket.on('send', function (data) {
      var msg = JSON.parse (data);
      console.log('send = ' + data);
      
      findSocketAndEmit (msg.userA, data);
      findSocketAndEmit (msg.userB, data);
      saveMessage(data);
  });
}

function findSocketAndEmit (username, data) {
  var userConf = users [username];
  var socketUser = userConf ? userConf.socket : undefined;
  if (socketUser)
      socketUser.emit ('receive', data);
}

function saveMessage (data) {
  console.log('saving message');
  var options = {
      host: config.appServer.host,
      path: '/user-profile-svc/chat/saveMessage',
      port: '9090',
      method : 'POST',
      headers: {
        'Content-Type': 'application/json', 
        'service.gateway.key': '739b7a0a-2b73-455b-87b2-dd511afabde5'
      }
  };

  callback = function(response) {
      var str = ''
      response.on('data', function (chunk) {
        str += chunk;
        console.log('response data');
      });
      response.on('end', function () {
        console.log(str);
        console.log('enddddddd');
      });
  }

  var req = http.request(options, callback);
  var strContent = JSON.stringify(data);
  console.log(strContent);
  //options.headers ['Content-Length']  =  Buffer.byteLength(strContent, 'utf8')
  req.write(data);
  req.end();
}

server.listen(3000, function(){
  console.log('listening on *:3000');
});
