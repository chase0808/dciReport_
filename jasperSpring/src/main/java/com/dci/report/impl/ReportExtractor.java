package com.dci.report.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.dci.report.bean.Report;

public class ReportExtractor implements ResultSetExtractor {

	@Override
	public Object extractData(ResultSet rs) throws SQLException,
			DataAccessException {
		Map<Integer, Report> map = new HashMap<Integer,Report>();
		Report report = null;

		ArrayList<Integer> para = null;
		while(rs.next()) {
			Integer id = rs.getInt("id");
			report = map.get(id);
			if(report == null){
				report = new Report(id);
				para = report.getPara();
				map.put(id, report);
			}
			switch(rs.getInt("paraid")) {
				case 1:	report.setUserid(Integer.parseInt(rs.getString("paravalue")));
					break;
				case 2: report.setStartdate(rs.getString("paravalue"));
					break;
				case 3: report.setEnddate(rs.getString("paravalue"));
				 	break;
				case 4: report.setType(rs.getString("paravalue"));
					break;
				case 5:	para.add(Integer.parseInt(rs.getString("paravalue")));
					break;
			}

		}
		return new ArrayList<Report>(map.values());
	}
}
