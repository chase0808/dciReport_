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
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsReportConfiguration;

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
		String printFileName = null;
		try {
			JasperPrint jasperPrint = JasperFillManager.fillReport(

			"C:\\Users\\ldong\\workspace\\jasperSpring\\summary.jasper",
					new HashMap<String, Object>(), ds);
			FileOutputStream oStream = new FileOutputStream(
					"C:\\Users\\ldong\\Desktop\\sample.xls");
			jasperPrint.setProperty("net.sf.jasperreports.export.xls.create.custom.palette", "false");
		    jasperPrint.setProperty("net.sf.jasperreports.export.xls.one.page.per.sheet", "false");
		    jasperPrint.setProperty("net.sf.jasperreports.export.xls.remove.empty.space.between.rows", "true");
		    jasperPrint.setProperty("net.sf.jasperreports.export.xls.remove.empty.space.between.columns", "true");
		    jasperPrint.setProperty("net.sf.jasperreports.export.xls.white.page.background", "false");
		    jasperPrint.setProperty("net.sf.jasperreports.export.xls.detect.cell.type", "true");
		    jasperPrint.setProperty("net.sf.jasperreports.export.xls.size.fix.enabled", "false");
		    jasperPrint.setProperty("net.sf.jasperreports.export.xls.ignore.graphics", "true");
		    jasperPrint.setProperty("net.sf.jasperreports.export.xls.collapse.row.span", "true");
		    jasperPrint.setProperty("net.sf.jasperreports.export.xls.ignore.cell.border", "true");
		    jasperPrint.setProperty("net.sf.jasperreports.export.xls.ignore.cell.background", "false");
		    //jasperPrint.setProperty("net.sf.jasperreports.export.xls.max.rows.per.sheet", "0");
		    jasperPrint.setProperty("net.sf.jasperreports.export.xls.wrap.text", "true");
		    jasperPrint.setProperty("net.sf.jasperreports.export.xls.use.timezone", "false");
		    jasperPrint.setProperty("net.sf.jasperreports.print.keep.full.text", "true");
		    jasperPrint.setProperty("net.sf.jasperreports.export.xls.exclude.origin.keep.first.band.1","columnHeader");
			JRXlsExporter exporter = new JRXlsExporter();
			exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
			exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(oStream));
			SimpleXlsReportConfiguration configuration = new SimpleXlsReportConfiguration();
			configuration.setOnePagePerSheet(true);
			configuration.setDetectCellType(true);
			configuration.setCollapseRowSpan(false);
			exporter.setConfiguration(configuration);
			exporter.exportReport();
			
			
			/*
			exporter
					.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
			
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, oStream);
			exporter.setParameter(
					JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS,
					Boolean.TRUE);
			exporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET,
					Boolean.FALSE);
			exporter.setParameter(
					JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND,
					Boolean.FALSE);
			exporter.exportReport();
			


			JasperExportManager
					.exportReportToPdfFile(jasperPrint,
							"C:\\Users\\ldong\\Desktop\\summary.pdf");
			*/


		} catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
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
