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
import java.util.Map;

import javax.sql.DataSource;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import com.dci.report.bean.Reportoutput;
import com.dci.report.bean.Reportpara;
import com.dci.report.bean.SummaryDataBean;
import com.dci.report.bean.Transaction;
import com.dci.report.services.Reportdataservice;
import com.dci.report.services.Reportgenerateservice;

@SuppressWarnings("deprecation")
public class Genbillingsummary implements Reportgenerateservice {

	Reportdataservice reportdataservice;
	private DataSource dataSource;
	private String path;
	private String reportTypeName;
	private String templatepath;
	
	

	public String getTemplatepath() {
		return templatepath;
	}

	public void setTemplatepath(String templatepath) {
		this.templatepath = templatepath;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	@Override
	public String generatereport(Transaction transaction) {
		int tid =  transaction.getId();
		reportTypeName = reportdataservice.getTransaction(tid).getName();
		String jasperFilelocation = templatepath + "summary.jasper";
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date startdate = null;
		Date enddate = null;
		ArrayList<String> departmentid = null;
		for (int i = 0; i < transaction.getPara().size(); i++) {
			Reportpara reportpara = transaction.getPara().get(i);
			switch (reportpara.getId()) {
			case 1:
				try {
					startdate = format.parse(reportpara.getValue().get(0));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				break;
			case 2:
				try {
					enddate = format.parse(reportpara.getValue().get(0));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				break;
			case 3:
				departmentid = reportpara.getValue();
				break;
			}
		}
		java.sql.Date sdate = new java.sql.Date(startdate.getTime());
		java.sql.Date edate = new java.sql.Date(enddate.getTime());
		ResultSet resultSet = null;
		CallableStatement stmt = null;
		Connection c = null;
		
		System.out.println("I want the size of departmentid " + departmentid.size());
		ArrayList<Reportoutput> arroutput = transaction.getArroutput();
		String SQL = "CALL ZDBXUTIL01.SPR1_GETSUMMARYREPORT(?,?,?,?)";
		SummaryDataBeanList databeanlist = new SummaryDataBeanList();
		for( int i = 0; i < departmentid.size(); i++ ) {
			try {
				c = dataSource.getConnection();
				stmt = c.prepareCall(SQL);
				stmt.setString(1, "");
				stmt.setDate(2, sdate);
				stmt.setDate(3, edate);
				stmt.setString(4, departmentid.get(i));
				System.out.println("I am in the loop, I am the departmentid! " + departmentid.get(i));
				resultSet = stmt.executeQuery();
				databeanlist.produce(resultSet);
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				if( resultSet != null ) {
					try {
						resultSet.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				
				if( stmt != null ) {
					try {
						stmt.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				if( c != null ) {
					try {
						c.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		}
		System.out.println("I am out of the loop and I am going to render the report!");
		ArrayList<SummaryDataBean> datalist = databeanlist.getDataBeanList();
		JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(datalist, false);

		try {
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("logoimage", templatepath + "dci.png");
			JasperPrint jasperPrint = JasperFillManager.fillReport(
					jasperFilelocation, parameters, beanColDataSource);
			addPropertiesToJasperPrintForExcel(jasperPrint);
			for (int i = 0; i < arroutput.size(); i++) {
				switch (arroutput.get(i).getOutputid()) {
				case 1:
					createPdfReport(jasperPrint, arroutput.get(i));
					reportdataservice.updateStatus(tid);
					break;
				case 2:
					createXlsReport(jasperPrint, arroutput.get(i));
					reportdataservice.updateStatus(tid);
					break;
				}
			}

		} catch (JRException e) {
			e.printStackTrace();
		}
		return path;
	}

	private void createPdfReport(JasperPrint jasperPrint, Reportoutput output) {
		String outputname = output.getFilename();
		String destination = path + "\\" + reportTypeName + "\\" + outputname + ".pdf";
		try {
			JasperExportManager.exportReportToPdfFile(jasperPrint, destination);
		} catch (JRException e) {
			e.printStackTrace();
		}
		System.out.println("PDF Report generated");

	}

	private void createXlsReport(JasperPrint jasperPrint, Reportoutput output) {
		String outputname = output.getFilename();
		String destination = path + "\\" + reportTypeName + "\\" + outputname + ".xls";
		try {
			JRXlsExporter exporter = new JRXlsExporter();
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
			exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME,
					destination);
			exporter.setParameter(JRXlsExporterParameter.IS_IGNORE_CELL_BORDER,
					Boolean.TRUE);
			exporter.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE,
					Boolean.TRUE);
			exporter.exportReport();
		} catch (JRException e) {
			e.printStackTrace();
		}
		System.out.println("Xls Report generated");
	}

	private void addPropertiesToJasperPrintForExcel(JasperPrint jasperPrint) {
		jasperPrint.setProperty(
				"net.sf.jasperreports.export.xls.create.custom.palette",
				"false");
		jasperPrint.setProperty(
				"net.sf.jasperreports.export.xls.one.page.per.sheet", "true");
		jasperPrint
				.setProperty(
						"net.sf.jasperreports.export.xls.remove.empty.space.between.rows",
						"true");
		jasperPrint
				.setProperty(
						"net.sf.jasperreports.export.xls.remove.empty.space.between.columns",
						"true");
		jasperPrint.setProperty(
				"net.sf.jasperreports.export.xls.white.page.background",
				"false");
		jasperPrint.setProperty(
				"net.sf.jasperreports.export.xls.detect.cell.type", "true");
		jasperPrint.setProperty(
				"net.sf.jasperreports.export.xls.size.fix.enabled", "false");
		jasperPrint.setProperty(
				"net.sf.jasperreports.export.xls.ignore.graphics", "true");
		jasperPrint.setProperty(
				"net.sf.jasperreports.export.xls.collapse.row.span", "true");
		jasperPrint.setProperty(
				"net.sf.jasperreports.export.xls.ignore.cell.border", "true");
		jasperPrint.setProperty(
				"net.sf.jasperreports.export.xls.ignore.cell.background",
				"false");
		// jasperPrint.setProperty("net.sf.jasperreports.export.xls.max.rows.per.sheet",
		// "0");
		jasperPrint.setProperty("net.sf.jasperreports.export.xls.wrap.text",
				"true");
		jasperPrint.setProperty("net.sf.jasperreports.export.xls.use.timezone",
				"false");
		jasperPrint.setProperty("net.sf.jasperreports.print.keep.full.text",
				"true");
		jasperPrint
				.setProperty(
						"net.sf.jasperreports.export.xls.exclude.origin.keep.first.band.1",
						"columnHeader");
		
//		jasperPrint.setProperty("net.sf.jasperreports.export.xls.sheet.names.sheet1", "Eddie");
//		jasperPrint.setProperty("net.sf.jasperreports.export.xls.sheet.names.sheet2", "Eddddie");
		jasperPrint.setProperty("net.sf.jasperreports.export.xls.font.size.fix.enabled", "true");

	}

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public Reportdataservice getReportdataservice() {
		return reportdataservice;
	}

	public void setReportdataservice(Reportdataservice reportdataservice) {
		this.reportdataservice = reportdataservice;
	}
}
