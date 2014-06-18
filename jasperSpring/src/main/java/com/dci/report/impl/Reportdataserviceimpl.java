package com.dci.report.impl;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import com.dci.report.bean.Report;
import com.dci.report.services.Reportdataservice;

public class Reportdataserviceimpl implements Reportdataservice {
	private DataSource reportdatasource;
	private JdbcTemplate jdbcTemplateObject;
	
	public Reportdataserviceimpl() {
		System.out.println("Reportdataserviceimpl instantiated");
	}
	@Override
	public void setReportdatasource(DataSource reportdatasource) {
		System.out.println("In the implementation setter");
		this.reportdatasource = reportdatasource;
		this.jdbcTemplateObject = new JdbcTemplate(reportdatasource);
	}

	@Override
	public void create(Report report) {
		
		System.out.println("In the create method!");
		String SQL = "insert into report (type, startdate, enddate) values (?, ? , ?)";
		
		jdbcTemplateObject.update(SQL, report.getType(), report.getStartdate(), report.getEnddate());
		
		SQL = "select last(id) from report";

		@SuppressWarnings("deprecation")
		int reportid = jdbcTemplateObject.queryForInt(SQL);
		
		SQL = "inset into para (reporid, departmentid) values (reportid, ?)";
		ArrayList<Integer> para = report.getPara();
		for( int i = 0; i < para.size(); i++ ) {
			jdbcTemplateObject.update(SQL, para.get(0));
		}
	}

	@Override
	public Report getReport(Integer reportid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Report> listReport() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		
	}

	
}
