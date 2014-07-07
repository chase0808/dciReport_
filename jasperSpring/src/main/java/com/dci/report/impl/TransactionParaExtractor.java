package com.dci.report.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.dci.report.bean.Reportpara;
import com.dci.report.bean.Transaction;

public class TransactionParaExtractor implements ResultSetExtractor {

	@Override
	public Object extractData(ResultSet rs) throws SQLException,
			DataAccessException {

		Map<Integer, Transaction> map = new HashMap<Integer, Transaction>();
		Map<Integer, Reportpara> paramap = null;
		Transaction transaction = null;
		ArrayList<Reportpara> arrpara = null;
		Reportpara reportpara = null;

		while (rs.next()) {
			Integer id = rs.getInt("id");
			transaction = map.get(id);

			if (transaction == null) {
				transaction = new Transaction(id);
				transaction.setUserid(rs.getInt("userid"));
				transaction.setReportid(rs.getInt("reportid"));
				transaction.setDate(rs.getTimestamp("date"));
				transaction.setGenMethod(rs.getString("description"));
				transaction.setName(rs.getString("name"));
				arrpara = transaction.getPara();
				paramap = new HashMap<Integer, Reportpara>();
				map.put(id, transaction);
			}

			Integer paraid = rs.getInt("paraid");
			reportpara = paramap.get(paraid);

			if (reportpara == null) {
				reportpara = new Reportpara();
				reportpara.setId(paraid);
				reportpara.setType(rs.getString("paratype"));
				reportpara.getValue().add(rs.getString("paravalue"));
				paramap.put(paraid, reportpara);
				arrpara.add(reportpara);
			}

			else {
				reportpara.getValue().add(rs.getString("paravalue"));
			}
		}
		return new ArrayList<Transaction>(map.values());
	}

}
