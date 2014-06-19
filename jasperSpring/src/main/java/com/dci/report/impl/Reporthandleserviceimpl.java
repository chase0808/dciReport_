package com.dci.report.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import javax.sql.DataSource;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import com.dci.report.bean.Report;
import com.dci.report.services.Reporthandleservice;

public class Reporthandleserviceimpl implements Reporthandleservice {
	private DataSource dataSource;

	@Override
	public String generatereport(Report report) {
		
		System.out.println(report.getId());
		System.out.println(report.getStartdate());
		System.out.println(report.getEnddate());
		ResultSet resultSet = null;
		Statement stmt = null;
		Connection c = null;
		String SQL = "select count(*) as number_funds from zdbxjhf004.tcontent";
		try {
			c = dataSource.getConnection();
			stmt = c.createStatement();
			resultSet = stmt.executeQuery(SQL);			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		JRResultSetDataSource ds = new JRResultSetDataSource(resultSet);
		try {
			JasperPrint jasperPrint = JasperFillManager.fillReport(
							"C:\\Users\\ldong\\workspace\\jasperSpring\\test.jasper",
							new HashMap<String, Object>(),ds);
			JasperExportManager.exportReportToPdfFile(jasperPrint,
					"C:\\Users\\ldong\\workspace\\jasperSpring\\test.pdf");
		} catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

}
