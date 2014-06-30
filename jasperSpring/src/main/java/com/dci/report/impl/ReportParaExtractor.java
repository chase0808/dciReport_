package com.dci.report.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.dci.report.bean.Reportpara;

public class ReportParaExtractor implements ResultSetExtractor {

	@Override
	public Map<String, List<Reportpara>> extractData(ResultSet resultSet)
			throws SQLException, DataAccessException {
		Reportpara reportpara;
		Map<String, List<Reportpara>> reportParaMap = new HashMap<String, List<Reportpara>>();
		int ctr = 0;
		String reportName;
		String temp = null;
		while (resultSet.next()) {
			reportName = resultSet.getString("reportname");
			if (ctr == 0) {
				reportParaMap.put(reportName, new ArrayList<Reportpara>());

			} else if (!temp.equals(reportName)) {
				reportParaMap.put(reportName, new ArrayList<Reportpara>());
			}

			reportpara = new Reportpara();

			reportpara.setId(resultSet.getInt("paraid"));
			reportpara.setType(resultSet.getString("paraname"));
			reportParaMap.get(reportName).add(reportpara);
			temp = reportName;
			ctr++;
		}
		return reportParaMap;
	}
}
