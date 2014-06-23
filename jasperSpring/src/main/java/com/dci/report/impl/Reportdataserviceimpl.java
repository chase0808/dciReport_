package com.dci.report.impl;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import com.dci.report.bean.Client;
import com.dci.report.bean.Report;
import com.dci.report.jdbc.DepartmentExtractor;
import com.dci.report.services.Reportdataservice;

public class Reportdataserviceimpl implements Reportdataservice {
	private DataSource reportdatasource;
	private DataSource dataSource;

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public DataSource getReportdatasource() {
		return reportdatasource;
	}

	private JdbcTemplate jdbcTemplateObject;

	public Reportdataserviceimpl() {
		System.out.println("Reportdataserviceimpl instantiated");
	}

	@Override
	public void setReportdatasource(DataSource reportdatasource) {
		this.reportdatasource = reportdatasource;
		this.jdbcTemplateObject = new JdbcTemplate(reportdatasource);
	}

	@Override
	public void create(Report report) {

		String SQL = "insert into report(id) value (null)";

		jdbcTemplateObject.update(SQL);

		SQL = "select max(id) from report";

		@SuppressWarnings("deprecation")
		int rid = jdbcTemplateObject.queryForInt(SQL);

		SQL = "insert into para(reportid, paraid, paravalue) values (?, ?, ?)";

		jdbcTemplateObject.update(SQL, rid, 1, 1);
		jdbcTemplateObject.update(SQL, rid, 2, report.getStartdate());
		jdbcTemplateObject.update(SQL, rid, 3, report.getEnddate());
		jdbcTemplateObject.update(SQL, rid, 4, "summary");
		ArrayList<Integer> para = report.getPara();
		for (int i = 0; i < para.size(); i++) {
			jdbcTemplateObject.update(SQL, rid, 5, para.get(i));
		}
	}

	@Override
	public Report getReport(Integer reportid) {
		String SQL = "select * from report a inner join para b on a.id = b.reportid inner join lookup c on b.paraid = c.paraid where a.id = ?";
		ArrayList<Report> reports = jdbcTemplateObject.query(SQL,
				new ReportExtractor(), reportid);
		return reports.get(0);
	}

	@Override
	public List<Report> listReport() {
		String SQL = "select * from report a inner join para b on a.id = b.reportid inner join lookup c on b.paraid = c.paraid";
		ArrayList<Report> reports = jdbcTemplateObject.query(SQL,
				new ReportExtractor());
		return reports;
	}

	@Override
	public void delete(Integer id) {
		String SQL = "delete report, para from report inner join para on id = reportid where id = ?";
		jdbcTemplateObject.update(SQL, id);
	}

	@Override
	public List<Client> getClientMap() {
		// TODO Auto-generated method stub
		String sql = "select * From  zdbxofi004.tclienttablelookup";
		List<Client> listofclient = new JdbcTemplate(dataSource).query(sql,
				new DepartmentExtractor());
		return listofclient;
	}

}
