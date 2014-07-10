package com.dci.report.loginModule;

public class LoginServiceimpl implements LoginService {

	LoginDaoService logindaoservice;

	public LoginDaoService getLogindaoservice() {
		return logindaoservice;
	}

	public void setLogindaoservice(LoginDaoService logindaoservice) {
		this.logindaoservice = logindaoservice;
	}

	@Override
	public int validate(User user) {
		String password = user.getPassword();
		if (password.equals(logindaoservice.getUserInfo(user.getUsername()))) {
			int userid = logindaoservice.getUserid(user.getUsername());
			return userid;
		} else
			return 0;
	}

}
