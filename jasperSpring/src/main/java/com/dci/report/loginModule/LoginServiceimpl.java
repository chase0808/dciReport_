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
	public String validate(User user) {
		String password = user.getPassword();
		if( password.equals(logindaoservice.getUserInfo(user.getUsername()))) 
			return "welcome";
		else
			return "Incorrect username or password!";
	}

}
