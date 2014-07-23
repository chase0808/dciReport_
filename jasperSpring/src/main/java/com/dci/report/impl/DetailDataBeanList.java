package com.dci.report.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.dci.report.bean.DetailDataBean;

public class DetailDataBeanList {
	private ArrayList<DetailDataBean> dataBeanList = new ArrayList<DetailDataBean>();
	
	public void produce(ResultSet rs) throws SQLException {
		DetailDataBean db = new DetailDataBean();
		while( rs.next() ) {
			db.setDOCUMENT_NAME(rs.getString("DOCUMENT_NAME"));
			db.setFBOOK_TYPE(rs.getString("FBOOK_TYPE"));
			db.setFBOOKSTATUSID(rs.getInt("FBOOKSTATUSID"));
			db.setFCLIENTID(rs.getInt("FCLIENTID"));
			db.setFLASTCHANGEDBY(rs.getString("FLASTCHANGEDBY"));
			db.setFPAGECOUNT(rs.getInt("FPAGECOUNT"));
			db.setFTIMELASTCHANGED(rs.getString("FTIMELASTCHANGED"));
			dataBeanList.add(db);
			db = new DetailDataBean();
		}
		
	}
	
	public ArrayList<DetailDataBean> getDataBeanList() {
		return dataBeanList;
	}
}
