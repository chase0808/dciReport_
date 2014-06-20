package com.dci.report.services;

import java.util.List;

import com.dci.report.bean.Client;
import com.dci.report.bean.Report;

public interface Reporthandleservice {

	String generatereport(Report report);

	public List<Client> getClientMap();

}
