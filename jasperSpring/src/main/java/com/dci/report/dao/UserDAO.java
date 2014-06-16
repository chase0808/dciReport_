package com.dci.report.dao;

import java.sql.SQLException;

import net.sf.jasperreports.engine.JRException;

public interface UserDAO {
	public void generate() throws ClassNotFoundException, SQLException,
			JRException;
}
