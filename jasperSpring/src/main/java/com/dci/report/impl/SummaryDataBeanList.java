package com.dci.report.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.dci.report.bean.SummaryDataBean;

public class SummaryDataBeanList {

	private ArrayList<SummaryDataBean> dataBeanList = new ArrayList<SummaryDataBean>();
	
	public void produce(ResultSet rs) throws SQLException {
		SummaryDataBean db = new SummaryDataBean();
		while( rs.next() ) {
			db.setCLIENTNAME(rs.getString("clientname"));
			db.setDEPTNAME(rs.getString("deptname"));
			db.setTitle(rs.getString("title"));
			db.setRvalues(rs.getInt("rvalues"));
			dataBeanList.add(db);
			db = new SummaryDataBean();
		}
		
	}
	
	public ArrayList<SummaryDataBean> getDataBeanList() {
		return dataBeanList;
	}
}
