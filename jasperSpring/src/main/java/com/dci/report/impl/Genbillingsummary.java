package com.dci.report.impl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import javax.sql.DataSource;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;

import com.dci.report.bean.Reportpara;
import com.dci.report.bean.Transaction;
import com.dci.report.services.Reportgenerateservice;

public class Genbillingsummary implements Reportgenerateservice {
	private DataSource dataSource;

	@SuppressWarnings("deprecation")
	@Override
	public String generatereport(Transaction transaction) {

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date startdate = null;
		Date enddate = null;
		ArrayList<String> departmentid = null;
		for ( int i = 0; i < transaction.getPara().size(); i++ ){
			Reportpara reportpara = transaction.getPara().get(i);
			switch (reportpara.getId()) {
			case 1 : try {
					startdate = format.parse(reportpara.getValue().get(0));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case 2 : try {
					enddate = format.parse(reportpara.getValue().get(0));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case 3 : departmentid = reportpara.getValue();
				break;
			}
		}
		java.sql.Date sdate = new java.sql.Date(startdate.getTime());
		java.sql.Date edate = new java.sql.Date(enddate.getTime());
		ResultSet resultSet = null;
		CallableStatement stmt = null;
		Connection c = null;
		String SQL = "CALL ZDBXUTIL01.SPR1_GETSUMMARYREPORT(?,?,?,?)";
		try {
			
			String idString =",";
			
			for (int i=0; i<departmentid.size(); i++) {
				
				idString= idString +  "," + departmentid.get(i);
			}
			
			idString=idString.replace(",,","");
			
			c = dataSource.getConnection();
			stmt = c.prepareCall(SQL);
			stmt.setString(1,"");
			stmt.setDate(2, (java.sql.Date) sdate);
			stmt.setDate(3, (java.sql.Date) edate);
			stmt.setString(4,idString);
			resultSet = stmt.executeQuery();	
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

		JRResultSetDataSource ds = new JRResultSetDataSource(resultSet);
		try {
			JasperPrint jasperPrint = JasperFillManager.fillReport(

			"C:\\Users\\ldong\\workspace\\jasperSpring\\summary.jasper",
					new HashMap<String, Object>(), ds);

			JasperExportManager
					.exportReportToPdfFile(jasperPrint,
							"C:\\Users\\ldong\\workspace\\jasperSpring\\summary.pdf");
            JRXlsExporter exporter = new JRXlsExporter();

            exporter.setParameter(JRExporterParameter.INPUT_FILE_NAME,
                  jasperPrint);
            exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME,
                  "C://sample_report.xls");

            exporter.exportReport();
 
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
