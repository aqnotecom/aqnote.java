function execute() {
	var MyObject = Java.type('com.aqnote.shared.test.script.MyObject');
	var obj = new MyObject();
	obj.var1 = "hello";
	obj.var2 = 101;
	return obj.var2 > 102;
}