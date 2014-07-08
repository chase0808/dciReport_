package com.dci.report.loginModule;

import java.util.List;

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
		List<String> password = jdbcTemplateObject.queryForList(sql, String.class, username);
		if( password.size() == 0 ) {
			System.out.println("null");
			return null;
		} else {
			return password.get(0);
		}
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
