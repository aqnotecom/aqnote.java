
var http = require('http'), string = '', buffer='';

buffer = new Buffer(1024*1024);

for(i=0; i < buffer.length; i++) {
	buffer[i] = 97;
}

var server2 = http.createServer(
	function(req, res) {
		res.writeHead(200);
		res.end(buffer);
	}
);

server2.listen(10240, '10.19.14.2');

console.log('running');
