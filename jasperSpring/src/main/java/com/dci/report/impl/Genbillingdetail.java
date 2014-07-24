package com.dci.report.impl;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
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
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;

import org.springframework.beans.factory.annotation.Autowired;

import com.dci.report.bean.Reportoutput;
import com.dci.report.bean.Reportpara;
import com.dci.report.bean.Transaction;
import com.dci.report.services.Reportdataservice;
import com.dci.report.services.Reportgenerateservice;

@SuppressWarnings("deprecation")
public class Genbillingdetail implements Reportgenerateservice {
	@Autowired
	Reportdataservice reportdataservice;
	private DataSource dataSource;
	private String path;

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	private String templatepath;

	@Override
	public String generatereport(Transaction transaction) {
		int tid = transaction.getId();
		String jasperFilelocation = templatepath + "billingdetail.jasper";
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
		PreparedStatement stmt = null;
		Connection c = null;
		ArrayList<Reportoutput> arroutput = transaction.getArroutput();
		String SQL = "select fbookstatusid, b.fclientid, fbook_type, a.fbookinstance_description as document_name, b.ftimelastchanged, a.flastchangedby,   fpagecount From  zdbxofi004.tbookinstance  a inner join zdbxofi004.tbookstatus2 b on a.fbookinstanceid = b.fbookinstanceid"
				+ " inner join zdbxofi004.tbook c on a.fbookid = c.fbookid"
				+ " where b.fbookinstance_status in (2,7)  and fpagecount > 0"
				+ " and date( b.ftimelastchanged ) between ? and  ? "
				+ " and b.fclientid = ?" + " order by fbookstatus, fbook_type";
		try {
			//
			// String idString =",";
			//
			// for (int i=0; i<departmentid.size(); i++) {
			//
			// idString= idString + "," + departmentid.get(i);
			// }
			//
			// idString=idString.replace(",,","");

			c = dataSource.getConnection();
			stmt = c.prepareStatement(SQL);
			stmt.setDate(1, sdate);
			stmt.setDate(2, edate);
			stmt.setString(3, "22");
			resultSet = stmt.executeQuery();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		JRResultSetDataSource ds = new JRResultSetDataSource(resultSet);
		try {
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("logoimage", templatepath + "dci.png");
			JasperPrint jasperPrint = JasperFillManager.fillReport(
					jasperFilelocation, parameters, ds);
			addPropertiesToJasperPrintForExcel(jasperPrint);
			for (int i = 0; i < arroutput.size(); i++) {
				switch (arroutput.get(i).getOutputid()) {
				case 1:
					createPdfReport(jasperPrint, arroutput.get(i), path);
					reportdataservice.updateStatus(tid);
					break;
				case 2:
					createXlsxReport(jasperPrint, arroutput.get(i), path);
					createXlsReport(jasperPrint, arroutput.get(i), path);
					reportdataservice.updateStatus(tid);
					break;
				}
			}

		} catch (JRException e) {
			e.printStackTrace();
		}
		return path;
	}

	private void createPdfReport(JasperPrint jasperPrint, Reportoutput output,
			String path) {
		String outputname = output.getFilename();
		String destination = null;
		File f = new File(path + "\\Billing Detail\\");
		if (!f.exists()) {
			f.mkdir();
		}
		destination = path + "\\Billing Detail\\" + outputname + ".pdf";
		try {
			JasperExportManager.exportReportToPdfFile(jasperPrint, destination);
		} catch (JRException e) {
			e.printStackTrace();
		}
		System.out.println("PDF Report generated");

	}

	private void createXlsxReport(JasperPrint jasperPrint, Reportoutput output,
			String path) {
		String outputname = output.getFilename();
		String destination = null;
		File f = new File(path + "\\Billing Detail\\");
		if (!f.exists()) {
			f.mkdir();
		}
		destination = path + "\\Billing Detail\\" + outputname + ".xlsx";
		try {
			JRXlsxExporter xlsxexporter = new JRXlsxExporter();
			xlsxexporter.setParameter(JRExporterParameter.JASPER_PRINT,
					jasperPrint);
			xlsxexporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME,
					destination);
			xlsxexporter.exportReport();
			System.out.println("Xlsx Report generated");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void createXlsReport(JasperPrint jasperPrint, Reportoutput output,
			String path) {
		String outputname = output.getFilename();
		String destination = null;
		File f = new File(path + "\\Billing Detail\\");
		if (!f.exists()) {
			f.mkdir();
		}
		destination = path + "\\Billing Detail\\" + outputname + ".xls";
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
				"net.sf.jasperreports.export.xls.one.page.per.sheet", "false");
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

	}

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public String getTemplatepath() {
		return templatepath;
	}

	public void setTemplatepath(String templatepath) {
		this.templatepath = templatepath;
	}

	public Reportdataservice getReportdataservice() {
		return reportdataservice;
	}

	public void setReportdataservice(Reportdataservice reportdataservice) {
		this.reportdataservice = reportdataservice;
	}
}
