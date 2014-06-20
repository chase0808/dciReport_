package com.dci.report.services;

import java.util.List;

import javax.sql.DataSource;

import com.dci.report.bean.Client;
import com.dci.report.bean.Report;

public interface Reportdataservice {

	public void setReportdatasource(DataSource dataSource);

	public void create(Report report);

	public Report getReport(Integer reportid);

	public List<Report> listReport();

	public void delete(Integer id);

	public List<Client> getClientMap();
}
