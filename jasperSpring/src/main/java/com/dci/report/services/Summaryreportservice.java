package com.dci.report.services;

import java.util.List;

import com.dci.report.bean.Client;
import com.dci.report.bean.Summaryinput;

public interface Summaryreportservice {

	public String getSummaryreport(Summaryinput summaryinput);

	public List<Client> getClientMap();

}
