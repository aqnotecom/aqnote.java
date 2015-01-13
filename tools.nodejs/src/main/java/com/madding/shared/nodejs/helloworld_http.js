var http = require('http');

var server = http.createServer(
	function(req, res) {
		res.writeHead(200, {'Content-Type': 'text/plain'});
		res.end('<h1>Hello World</h1>\n');
	}
);

server.listen(10240,'10.19.14.2');

console.log('Server running at http://10.19.14.2:10240/');


