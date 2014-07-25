package com.dci.report.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.sql.DataSource;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import com.dci.report.bean.Client;
import com.dci.report.bean.Report;
import com.dci.report.services.Reportdataservice;
import com.dci.report.services.Reporthandleservice;

public class Reporthandleserviceimpl implements Reporthandleservice {
	private DataSource dataSource;
	private Reportdataservice reportdataservice;

	public Reportdataservice getReportdataservice() {
		return reportdataservice;
	}

	public void setReportdataservice(Reportdataservice reportdataservice) {
		this.reportdataservice = reportdataservice;
	}

	@Override
	public String generatereport(Report report) {

		System.out.println(report.getId());
		System.out.println(report.getStartdate());
		System.out.println(report.getEnddate());
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date startdate = null;
		Date enddate = null;
		try {
			startdate = format.parse(report.getStartdate());
			enddate = format.parse(report.getEnddate());
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		java.sql.Date sdate = new java.sql.Date(startdate.getTime());
		System.out.println(sdate.toString());
		java.sql.Date edate = new java.sql.Date(enddate.getTime());
		ResultSet resultSet = null;
		CallableStatement stmt = null;
		Connection c = null;

		String SQL = "CALL ZDBXUTIL01.SPR1_GETSUMMARYREPORT(?,?,?,?)";

		try {

			String idString = ",";

			for (int i = 0; i < report.getPara().size(); i++) {

				idString = idString + "," + report.getPara().get(i);
			}

			idString = idString.replace(",,", "");

			c = dataSource.getConnection();
			stmt = c.prepareCall(SQL);
			stmt.setString(1, "");
			stmt.setDate(2, sdate);
			stmt.setDate(3, edate);
			stmt.setString(4, idString);
			resultSet = stmt.executeQuery();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		JRResultSetDataSource ds = new JRResultSetDataSource(resultSet);
		try {
			JasperPrint jasperPrint = JasperFillManager.fillReport(

			"C:\\Users\\xqi\\eclipesworkspace\\jasperSpring\\summary1.jasper",

			new HashMap<String, Object>(), ds);

			JasperExportManager
					.exportReportToPdfFile(jasperPrint,
							"C:\\Users\\xqi\\eclipesworkspace\\jasperSpring\\jasperSpringsummary.pdf");
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

	@Override
	public List<Client> getClientMap() {
		// TODO Auto-generated method stub
		return reportdataservice.getClientMap();
	}

}