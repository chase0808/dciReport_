package com.dci.report.daoimpl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;

import javax.sql.DataSource;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.dci.report.dao.UserDAO;

public class UserDAOImpl implements UserDAO {

	private DataSource dataSource;

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	@RequestMapping(value = "/setTitle", method = RequestMethod.POST)
	public void generate() throws ClassNotFoundException, SQLException,
			JRException {
		// TODO Auto-generated method stub
		// the db2 driver string
		Class.forName("com.ibm.as400.access.AS400JDBCDriver");

		// the db2 url string
		String url = "jdbc:as400://zeus/zdbxdci005;";

		// get a db2 database connection
		Connection db2 = DriverManager.getConnection(url, "DCIINTERN",
				"qixin808");
		JasperPrint jasperPrint = JasperFillManager.fillReport("test.jasper",
				new HashMap<String, Object>(), db2);
		JasperExportManager.exportReportToPdfFile(jasperPrint, "test.pdf");
	}

}
