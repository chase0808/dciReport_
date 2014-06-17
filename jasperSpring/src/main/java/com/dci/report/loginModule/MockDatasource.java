package com.dci.report.loginModule;

import java.util.HashMap;

public class MockDatasource {
	private HashMap<String, String> map;
	
	public MockDatasource() {
		map = new HashMap<String, String>();
		map.put("Eddie", "Eddie");
		map.put("Leo", "Leo");
		map.put("Chase", "Chase");
	}
	
	public String returnpassword(String username) {
		return map.get(username);
	}
}
