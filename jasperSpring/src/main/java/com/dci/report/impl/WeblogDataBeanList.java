package com.dci.report.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.dci.report.bean.WeblogDataBean;

public class WeblogDataBeanList {
	private ArrayList<WeblogDataBean> dataBeanList = new ArrayList<WeblogDataBean>();
	
	public void produce(ResultSet rs) throws SQLException {
		WeblogDataBean db = new WeblogDataBean();
		while( rs.next() ) {
			db.setCLIENTIPADDRESS(rs.getString("CLIENTIPADDRESS"));
			db.setREQUESTTIMESTAMP(rs.getString("TIMESTAMP"));
			dataBeanList.add(db);
			db = new WeblogDataBean();
		}
		
	}
	
	public ArrayList<WeblogDataBean> getDataBeanList() {
		return dataBeanList;
	}
}
