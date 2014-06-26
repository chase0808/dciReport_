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
		/*
		 * String SQL =
		 * "select 'Number of Funds' AS title, count(*)AS rvalues, 'NUM_FUNDS' AS KEY From zdbxofi004.VtCLIENTcontent where fclientid = 22 and fcontent_name = 'VEHICLE' "
		 * +
		 * " UNION (select 'Number of Documents published', count(*), 'NUM_DOC_PUB' AS KEY From zdbxofi004.tbookinstance A INNER JOIN ZDBXOFI004.TBOOK B ON A.FBOOKID = B.FBOOKID where fbookinstance_status = 7 AND B.FCLIENTID = 22 and date( A.ftimelastchanged ) between ? and  ?)"
		 * +
		 * " UNION (select 'Number of Documents approved', count(*), 'NUM_DOC_APPROV' AS KEY From zdbxofi004.tbookinstance A INNER JOIN ZDBXOFI004.TBOOK B ON A.FBOOKID = B.FBOOKID where fbookinstance_status = 2 AND B.FCLIENTID = 22 and date( A.ftimelastchanged ) between ? and  ?)"
		 * +
		 * " UNION (select fbook_type, count(*), 'NUM_DOC_SYS' AS KEY  From zdbxofi004.tbookinstance a inner join zdbxofi004.tbook b on a.fbookid = b.fbookid  AND  B.FCLIENTID = 22 where  fbookinstance_status in (select fstatusid From zdbxofi004.tdocumentstatusidentity where fstatusid <> 5) group by fbook_type)"
		 * +
		 * " UNION (select 'Number of Pages Rendered (Every Status)', coalesce(sum(fpagecount),0), 'PAGE_RENDERED' AS KEY  From zdbxofi004.tbookstatus2  A  INNER JOIN ZDBXOFI004.TBOOKINSTANCE B ON A.FBOOKINSTANCEID = B.FBOOKINSTANCEID where  date( A.ftimelastchanged ) between ? and ? AND FBOOKSTATUS = 2)"
		 * +
		 * " UNION (select 'Number of Funds' AS title, count(*)AS rvalues, 'NUM_FUNDS' AS KEY From zdbxofi004.VtCLIENTcontent where fclientid = 22 and fcontent_name = 'VEHICLE' )"
		 * +
		 * " UNION (select 'Number of Documents published', count(*), 'NUM_DOC_PUB' AS KEY From zdbxofi004.tbookinstance A INNER JOIN ZDBXOFI004.TBOOK B ON A.FBOOKID = B.FBOOKID where fbookinstance_status = 7 AND B.FCLIENTID = 22 and date( A.ftimelastchanged ) between ? and  ?)"
		 * +
		 * " UNION (select 'Number of Documents approved', count(*), 'NUM_DOC_APPROV' AS KEY From zdbxofi004.tbookinstance A INNER JOIN ZDBXOFI004.TBOOK B ON A.FBOOKID = B.FBOOKID where fbookinstance_status = 2 AND B.FCLIENTID = 22 and date( A.ftimelastchanged ) between ? and  ?)"
		 * +
		 * " UNION (select fbook_type, count(*), 'NUM_DOC_SYS' AS KEY  From zdbxofi004.tbookinstance a inner join zdbxofi004.tbook b on a.fbookid = b.fbookid  AND  B.FCLIENTID = 22 where  fbookinstance_status in (select fstatusid From zdbxofi004.tdocumentstatusidentity where fstatusid <> 5) group by fbook_type)"
		 * +
		 * " UNION (select  'Number of Pages Rendered (Every Status)', coalesce(sum(fpagecount),0), 'PAGE_RENDERED' AS KEY  From zdbxofi004.tbookstatus2  A  INNER JOIN ZDBXOFI004.TBOOKINSTANCE B ON A.FBOOKINSTANCEID = B.FBOOKINSTANCEID where  date( A.ftimelastchanged ) between ? and ? AND FBOOKSTATUS = 2)"
		 * ;
		 */
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
