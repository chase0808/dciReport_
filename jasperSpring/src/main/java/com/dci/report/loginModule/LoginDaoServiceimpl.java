package com.dci.report.loginModule;

import org.springframework.beans.factory.annotation.Autowired;

public class LoginDaoServiceimpl implements LoginDaoService {
	
	@Autowired
	private MockDatasource dataSource;
	
	@Override
	public String getUserInfo(String username) {
		return dataSource.returnpassword(username);
	}
	
	public MockDatasource getDataSource() {
		return dataSource;
	}

	public void setDataSource(MockDatasource dataSource) {
		this.dataSource = dataSource;
	}

}
