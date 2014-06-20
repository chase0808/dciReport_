package com.dci.report.impl;

import java.util.List;

import com.dci.report.bean.Client;
import com.dci.report.bean.Summaryinput;
import com.dci.report.services.Summaryreportdaoservice;
import com.dci.report.services.Summaryreportservice;

public class Summaryreportserviceimpl implements Summaryreportservice {

	Summaryreportdaoservice summaryreportdaoservice;

	public Summaryreportserviceimpl() {

		System.out.println("Summaryreportserviceimpl Instantiated");
	}

	@Override
	public String getSummaryreport(Summaryinput summaryinput) {
		// TODO Auto-generated method stub

		return summaryreportdaoservice.getSummaryreport(summaryinput);
	}

	public Summaryreportdaoservice getSummaryreportdaoservice() {
		return summaryreportdaoservice;
	}

	public void setSummaryreportdaoservice(
			Summaryreportdaoservice summaryreportdaoservice) {
		this.summaryreportdaoservice = summaryreportdaoservice;
	}

	@Override
	public List<Client> getClientMap() {
		// TODO Auto-generated method stub
		return summaryreportdaoservice.getClientMap();
	}

}
