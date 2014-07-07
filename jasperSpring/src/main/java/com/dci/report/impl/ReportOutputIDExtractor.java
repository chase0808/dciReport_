package com.dci.report.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

public class ReportOutputIDExtractor implements ResultSetExtractor {

	@Override
	public Map<String, List<Integer>> extractData(ResultSet resultset)
			throws SQLException, DataAccessException {
		Map<String, List<Integer>> reportToOutputID = new HashMap<String, List<Integer>>();
		int count = 0;
		String reportType;
		String temp = null;
		while (resultset.next()) {
			reportType = resultset.getString("reportname");
			if (count == 0) {
				reportToOutputID.put(reportType, new ArrayList<Integer>());

			} else if (!temp.equals(reportType)) {
				reportToOutputID.put(reportType, new ArrayList<Integer>());
			}

			reportToOutputID.get(reportType).add(resultset.getInt("outputid"));
			temp = reportType;
			count++;

		}
		return reportToOutputID;
	}
}
