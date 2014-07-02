package com.dci.report.impl;

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
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;

import com.dci.report.bean.Reportoutput;
import com.dci.report.bean.Reportpara;
import com.dci.report.bean.Transaction;
import com.dci.report.services.Reportgenerateservice;

@SuppressWarnings("deprecation")
public class Genbillingsummary implements Reportgenerateservice {
	private DataSource dataSource;

	@Override
	public String generatereport(Transaction transaction) {
		
		String jasperFilelocation = "C:\\Users\\ldong\\workspace\\jasperSpring\\summary.jasper";
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
					e.printStackTrace();
				}
				break;
			case 2 : try {
					enddate = format.parse(reportpara.getValue().get(0));
				} catch (ParseException e) {
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
		ArrayList<Reportoutput> arroutput = transaction.getArroutput();
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
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperFilelocation,
					new HashMap<String, Object>(), ds);
			addPropertiesToJasperPrintForExcel(jasperPrint);
			for( int i = 0; i < arroutput.size(); i++ ) {
				switch(arroutput.get(i).getOutputid()) {
				case 1 : createXlsReport(jasperPrint, arroutput.get(i));
					break;
				case 2 : createXlsxReport(jasperPrint, arroutput.get(i));
						 createPdfReport(jasperPrint, arroutput.get(i));
					break;
				}
			}
			

		} catch (JRException e) {
			e.printStackTrace();
		} 
		return null;
	}
	
	private void createPdfReport(JasperPrint jasperPrint, Reportoutput output) {
		String outputname = output.getFilename();
		String destination = "C:\\Users\\ldong\\Desktop\\" + outputname + ".pdf";
		try {
			JasperExportManager.exportReportToPdfFile(jasperPrint, destination);
		} catch (JRException e) {
			e.printStackTrace();
		}
		System.out.println("PDF Report generated");
		
	}

	private void createXlsxReport(JasperPrint jasperPrint, Reportoutput output) {
		String outputname = output.getFilename();
		String destination = "C:\\Users\\ldong\\Desktop\\" + outputname + ".xlsx";
		try{
			JRXlsxExporter xlsxexporter = new JRXlsxExporter();
			xlsxexporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
			xlsxexporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, destination );
			xlsxexporter.exportReport();
			System.out.println("Xlsx Report generated");
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

	private void createXlsReport(JasperPrint jasperPrint, Reportoutput output) {
		String outputname = output.getFilename();
		String destination = "C:\\Users\\ldong\\Desktop\\" + outputname + ".xls";
		try {
			JRXlsExporter exporter = new JRXlsExporter();
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
			exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, destination );
			exporter.setParameter(JRXlsExporterParameter.IS_IGNORE_CELL_BORDER, Boolean.TRUE);
			exporter.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
			exporter.exportReport();
		} catch (JRException e) {
			e.printStackTrace();
		}
		System.out.println("Xls Report generated");
	}

	private void addPropertiesToJasperPrintForExcel(JasperPrint jasperPrint) {
		jasperPrint.setProperty("net.sf.jasperreports.export.xls.create.custom.palette", "false");
	    jasperPrint.setProperty("net.sf.jasperreports.export.xls.one.page.per.sheet", "true");
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
		
	}

	public DataSource getDataSource() {
		return dataSource;
	}
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

}
