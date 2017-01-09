//JavaString测试
var StringCls = Java.type("java.lang.String");
var str = new StringCls("Hello");
str = str.toUpperCase();
print('Upper: ' + str);