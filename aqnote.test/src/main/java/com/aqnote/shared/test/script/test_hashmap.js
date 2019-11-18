//创建Java对象
var HashMap = Java.type("java.util.HashMap");
var mapDef = new HashMap();
var map100 = new HashMap(100);
print(Java.type("java.util.Map").Entry);
print('内部类：' + Java.type("java.util.Map$Entry")); //存取内部类

