package com.dci.report.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.dci.report.bean.Reportoutput;

public class ReportOutputExtractor implements ResultSetExtractor {

	@Override
	public Map<String, List<Reportoutput>> extractData(ResultSet resultset)
			throws SQLException, DataAccessException {
		Map<String, List<Reportoutput>> reportToOutput = new HashMap<String, List<Reportoutput>>();
		int count = 0;
		String reportType;
		while (resultset.next()) {
			Reportoutput reportoutput = new Reportoutput();
			reportType = resultset.getString("name");
			if (count == 0) {
				reportToOutput.put(reportType, new ArrayList<Reportoutput>());

			} else if (!reportToOutput.containsKey(reportType)) {
				reportToOutput.put(reportType, new ArrayList<Reportoutput>());
			}
			reportoutput.setOutputid(resultset.getInt("outputid"));
			reportoutput.setType(resultset.getString("type"));
			reportToOutput.get(reportType).add(reportoutput);
			count++;

		}
		return reportToOutput;
	}
}
