function execute() {

	var QueryData = Java.type('com.aqnote.shared.test.script.QueryData');
	var user = QueryData.getUser();
	var session = QueryData.getSession();
	var device = QueryData.getDevice();
	
	print(user);
	var result = true;
	if(user.nick == "") {
		result = result && false;
	}
	
	print(session);
	if(session.isLogin == true) {
		result = result && true;
	}
	
	print(device);
	if(device.type == "android") {
		result = result && true;
	}
	return result;
}