
var puts = require("sys").puts;

setInterval(
	function() {
		puts("hello");
	},
	500
);

process.addListener(
	"SIGINT",
	function() {
		puts("good bye");	
		process.exit(0);
	}
);

console.log("process.pid:" + process.pid);
console.log("process.ARGV:" + process.ARGV);
console.log("process.ENV:" + process.ENV);
console.log("process.cwd():" + process.cwd());
console.log("process.memoryUsage():" + process.memoryUsage());
