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
		String SQL = "select 'Number of Funds' AS title, count(*)AS rvalues, 'NUM_FUNDS' AS KEY From zdbxofi004.VtCLIENTcontent where fclientid = 22 and fcontent_name = 'VEHICLE' " +  
		" UNION (select 'Number of Documents published', count(*), 'NUM_DOC_PUB' AS KEY From zdbxofi004.tbookinstance A INNER JOIN ZDBXOFI004.TBOOK B ON A.FBOOKID = B.FBOOKID where fbookinstance_status = 7 AND B.FCLIENTID = 22 and date( A.ftimelastchanged ) between '2014-05-01' and  '2014-05-31')" + 
		" UNION (select 'Number of Documents approved', count(*), 'NUM_DOC_APPROV' AS KEY From zdbxofi004.tbookinstance A INNER JOIN ZDBXOFI004.TBOOK B ON A.FBOOKID = B.FBOOKID where fbookinstance_status = 2 AND B.FCLIENTID = 22 and date( A.ftimelastchanged ) between '2014-05-01' and  '2014-05-31')" +
		" UNION (select fbook_type, count(*), 'NUM_DOC_SYS' AS KEY  From zdbxofi004.tbookinstance a inner join zdbxofi004.tbook b on a.fbookid = b.fbookid  AND  B.FCLIENTID = 22 where  fbookinstance_status in (select fstatusid From zdbxofi004.tdocumentstatusidentity where fstatusid <> 5) group by fbook_type)" +
		" UNION (select  'Number of Pages Rendered (Every Status)', coalesce(sum(fpagecount),0), 'PAGE_RENDERED' AS KEY  From zdbxofi004.tbookstatus2  A  INNER JOIN ZDBXOFI004.TBOOKINSTANCE B ON A.FBOOKINSTANCEID = B.FBOOKINSTANCEID where  date( A.ftimelastchanged ) between '2014-05-01' and  '2014-05-31' AND FBOOKSTATUS = 2)";
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
							"C:\\Users\\ldong\\workspace\\jasperSpring\\summary.jasper",
							new HashMap<String, Object>(),ds);
			JasperExportManager.exportReportToPdfFile(jasperPrint,
					"C:\\Users\\ldong\\workspace\\jasperSpring\\summary.pdf");
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
