
var net = require('net');

var server = net.createServer(
	function(socket) {
		socket.write("Echo from server\r\n");
		socket.pipe(socket);
	}
);

server.listen(1338, "127.0.0.1");

console.log('Server running at http://127.0.0.1:1338');
