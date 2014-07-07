package com.dci.report.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.dci.report.bean.Reportoutput;

public class TransactionOpMapper implements RowMapper<Reportoutput> {

	@Override
	public Reportoutput mapRow(ResultSet rs, int rowNum) throws SQLException {
		Reportoutput output = new Reportoutput();
		output.setOutputid(rs.getInt("outputid"));
		output.setStatus(rs.getString("status"));
		output.setFilename(rs.getString("filename"));
		output.setType(rs.getString("type"));
		return output;
	}

}
