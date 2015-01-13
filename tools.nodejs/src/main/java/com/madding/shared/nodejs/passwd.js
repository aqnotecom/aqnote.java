
//var stat = require("posix").stat;
var stat = require("fs").stat,
 	puts = require("sys").puts;

var promise = stat("/etc/passwd");

promise.addCallback(
	function(s) {
		puts("modified:" + s.mtime);
	}
);

console.log("running");
