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

import com.dci.report.bean.Summaryinput;
import com.dci.report.services.Summaryreportdaoservice;

public class Summaryreportdaoserviceimpl implements Summaryreportdaoservice {

	private DataSource dataSource;

	public Summaryreportdaoserviceimpl() {

		System.out.println("Summaryreportdaoserviceimpl Instantiated");
	}

	@Override
	public String getSummaryreport(Summaryinput summaryinput) {
		String library = summaryinput.getLibrary();
		String start_date = summaryinput.getStart_date();
		String end_date = summaryinput.getEnd_date();

		Statement statement;
		ResultSet resultSet = null;
		String query = "select count(*) as number_funds From " + library;
		// the db2 driver string

		// get a db2 database connection
		Connection db2;
		try {
			db2 = dataSource.getConnection();
			statement = db2.createStatement();
			resultSet = statement.executeQuery(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		JRResultSetDataSource resultSetDataSource = new JRResultSetDataSource(
				resultSet);
		JasperPrint jasperPrint;
		try {
			jasperPrint = JasperFillManager
					.fillReport(
							"C:\\Users\\ldong\\workspace\\jasperSpring\\test.jasper",
							new HashMap<String, Object>(), resultSetDataSource);
			JasperExportManager.exportReportToPdfFile(jasperPrint,
					"C:\\Users\\ldong\\workspace\\jasperSpring\\test.pdf");
		} catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return library;
	}

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

}
