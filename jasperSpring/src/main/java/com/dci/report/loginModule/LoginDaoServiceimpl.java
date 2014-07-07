package com.dci.report.loginModule;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public class LoginDaoServiceimpl implements LoginDaoService {
	
	@Autowired
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplateObject;

	@Override
	public String getUserInfo(String username) {
		String sql = "select password from jasreport.tuser where username = ?";
		String password = jdbcTemplateObject.queryForObject(sql, String.class, username);
		return password;
	}

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.jdbcTemplateObject = new JdbcTemplate(dataSource);
	}

	@SuppressWarnings("deprecation")
	@Override
	public int getUserid(String username) {
		String sql = "select id from jasreport.tuser where username = ?";
		Integer userid = jdbcTemplateObject.queryForInt(sql, username);
		return userid;
	}

}
