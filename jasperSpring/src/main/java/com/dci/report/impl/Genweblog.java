package com.dci.report.impl;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;

import com.dci.report.bean.Transaction;
import com.dci.report.services.Reportdataservice;
import com.dci.report.services.Reportgenerateservice;

public class Genweblog implements Reportgenerateservice {
	
	@Autowired
	Reportdataservice reportdataservice;
	private DataSource dataSource;
	private String path;
	private String reportTypeName;
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	private String templatepath;
	
	@Override
	public String generatereport(Transaction transaction) {
		
		return null;
	}

}
