package com.dci.report.services;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import com.dci.report.bean.Client;
import com.dci.report.bean.Report;
import com.dci.report.bean.Reportpara;
import com.dci.report.bean.Transaction;

public interface Reportdataservice {

	public void setReportdatasource(DataSource dataSource);

	public void create(Transaction transaction);

	public Transaction getTransaction(Integer transactionid);

	public List<Transaction> listTransaction();

	public void delete(Integer id);

	public List<Client> getClientMap();

	public List<String> listReportType();

	public Map<String, List<Reportpara>> reportParaMap();
}
