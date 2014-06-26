package com.dci.report.impl;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import com.dci.report.bean.Client;
import com.dci.report.bean.Report;
import com.dci.report.bean.Reportpara;
import com.dci.report.bean.Transaction;
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
	public void create(Transaction transaction) {

		String SQL = "insert into jasreport.ttransaction (userid, reportid) values (?, ?)";

		jdbcTemplateObject.update(SQL, transaction.getUserid(), transaction.getReportid());

		SQL = "select max(id) from jasreport.ttransaction";

		@SuppressWarnings("deprecation")
		int tid = jdbcTemplateObject.queryForInt(SQL);

		SQL = "insert into jasreport.ttransactionpara(transactionid, paraid, paravalue) values (?, ?, ?)";
		
		Reportpara rpara = null;
		for( int i = 0; i < transaction.getPara().size(); i++ ) {
			rpara = transaction.getPara().get(i);
			for( int j = 0; j < rpara.getValue().size(); j++ ) {
				jdbcTemplateObject.update(SQL, tid, rpara.getId(), rpara.getValue().get(j));
			}
		}
		
		SQL = "insert into jasreport.ttransactionoutput(transactionid, outputid, status, filename) values (?, ?, ?, ?)";
		for( int i = 0; i < transaction.getOutput().size(); i++ ) {
			java.util.Date date= new java.util.Date();
			int outputid = transaction.getOutput().get(i);
			String query = "select type from jasreport.toutput where id = ?";
			String type = jdbcTemplateObject.queryForObject(query, String.class, outputid);
			query = "select name from jasreport.treport where id = ?";
			String rname = jdbcTemplateObject.queryForObject(query, String.class, transaction.getReportid());
			String fname = Integer.toString(transaction.getUserid()) + rname + date.toString() + type;
			String status = "In progress";
			jdbcTemplateObject.update(SQL, tid, outputid, status, fname);
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
