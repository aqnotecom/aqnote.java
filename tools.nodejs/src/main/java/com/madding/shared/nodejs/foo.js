
var circle = require('./circle.js');

var PI = Math.PI;

area = function(r) {
	return PI * r * r;
};

console.log('The area of a circle of radius 4 is ' + circle.area(4));
console.log('The area of a circle of radius 4 is ' + area(4));

