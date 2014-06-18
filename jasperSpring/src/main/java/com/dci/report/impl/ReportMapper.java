package com.dci.report.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.dci.report.bean.Report;

public class ReportMapper implements RowMapper<Report>{

	@Override
	public Report mapRow(ResultSet rs, int rowNum) throws SQLException {
		Report report = new Report();
		report.setType(rs.getString("type"));
		report.setStartdate(rs.getDate("startdate").toString());
		report.setEnddate(rs.getDate("enddate").toString());
		return report;
	}
	
}
