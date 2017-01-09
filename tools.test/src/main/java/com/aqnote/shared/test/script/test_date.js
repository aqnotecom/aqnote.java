
//日期
var Date = Java.type('java.util.Date');
var date = new Date();
print('year:' + date.year);
date.year += 1900;
print('new year:' + date.year);