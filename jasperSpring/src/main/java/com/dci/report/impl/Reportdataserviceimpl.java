package com.dci.report.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import com.dci.report.bean.Client;
import com.dci.report.bean.Reportoutput;
import com.dci.report.bean.Reportpara;
import com.dci.report.bean.Transaction;
import com.dci.report.jdbc.DepartmentExtractor;
import com.dci.report.services.Reportdataservice;

public class Reportdataserviceimpl implements Reportdataservice {
	private DataSource reportdatasource;
	private DataSource dataSource;

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public DataSource getReportdatasource() {
		return reportdatasource;
	}

	private JdbcTemplate jdbcTemplateObject;

	public Reportdataserviceimpl() {
		System.out.println("Reportdataserviceimpl instantiated");
	}

	@Override
	public void setReportdatasource(DataSource reportdatasource) {
		this.reportdatasource = reportdatasource;
		this.jdbcTemplateObject = new JdbcTemplate(reportdatasource);
	}

	@Override
	public void create(Transaction transaction) {

		String SQL = "insert into jasreport.ttransaction (userid, reportid) values (?, ?)";

		jdbcTemplateObject.update(SQL, transaction.getUserid(),
				transaction.getReportid());

		SQL = "select max(id) from jasreport.ttransaction";

		@SuppressWarnings("deprecation")
		int tid = jdbcTemplateObject.queryForInt(SQL);

		SQL = "insert into jasreport.ttransactionpara(transactionid, paraid, paravalue) values (?, ?, ?)";

		Reportpara rpara = null;
		for (int i = 0; i < transaction.getPara().size(); i++) {
			rpara = transaction.getPara().get(i);
			for (int j = 0; j < rpara.getValue().size(); j++) {
				jdbcTemplateObject.update(SQL, tid, rpara.getId(), rpara
						.getValue().get(j));
			}
		}

		SQL = "insert into jasreport.ttransactionoutput(transactionid, outputid, status, filename) values (?, ?, ?, ?)";
		for (int i = 0; i < transaction.getOutput().size(); i++) {
			java.util.Date date = new java.util.Date();
			int outputid = transaction.getOutput().get(i);
			String query = "select type from jasreport.toutput where id = ?";
			String type = jdbcTemplateObject.queryForObject(query,
					String.class, outputid);
			query = "select name from jasreport.treport where id = ?";
			String rname = jdbcTemplateObject.queryForObject(query,
					String.class, transaction.getReportid());
			String fname = Integer.toString(transaction.getUserid()) + rname
					+ date.toString() + type;
			String status = "In progress";
			jdbcTemplateObject.update(SQL, tid, outputid, status, fname);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Transaction getTransaction(Integer transactionid) {
		String SQL = "select a.id, description, userid, a.date, reportid, paraid, paravalue, paratype from jasreport.ttransaction a inner join jasreport.ttransactionpara b on a.id = b.transactionid inner join jasreport.treportpara c on b.paraid = c.id inner join jasreport.treport e on a.reportid = e.id where a.id = ?";
		ArrayList<Transaction> transactions = jdbcTemplateObject.query(SQL,
				new TransactionParaExtractor(), transactionid);
		SQL = "select * from jasreport.ttransactionoutput where transactionid = ?";
		ArrayList<Reportoutput> reportoutput = (ArrayList<Reportoutput>) jdbcTemplateObject
				.query(SQL, new TransactionOpMapper(), transactionid);
		transactions.get(0).setArroutput(reportoutput);
		return transactions.get(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Transaction> listTransaction() {
		String SQL = "select a.id, description, userid, a.date, reportid, paraid, paravalue, paratype from jasreport.ttransaction a inner join jasreport.ttransactionpara b on a.id = b.transactionid inner join jasreport.treportpara c on b.paraid = c.id inner join jasreport.treport e on a.reportid = e.id";
		ArrayList<Transaction> transactions = jdbcTemplateObject.query(SQL,
				new TransactionParaExtractor());
		SQL = "select * from jasreport.ttransactionoutput where transactionid = ?";
		for (int i = 0; i < transactions.size(); i++) {
			int tid = transactions.get(i).getId();
			ArrayList<Reportoutput> reportoutput = (ArrayList<Reportoutput>) jdbcTemplateObject
					.query(SQL, new TransactionOpMapper(), tid);
			transactions.get(i).setArroutput(reportoutput);
		}
		return transactions;
	}

	@Override
	public void delete(Integer id) {
		String SQL = "delete from jasreport.ttransaction where id = ?";
		jdbcTemplateObject.update(SQL, id);
		SQL = "delete from jasreport.ttransactionpara where transactionid = ?";
		jdbcTemplateObject.update(SQL, id);
		SQL = "delete from jasreport.ttransactionoutput where transactionid = ?";
		jdbcTemplateObject.update(SQL, id);
	}

	@Override
	public List<Client> getClientMap() {
		// TODO Auto-generated method stub
		String sql = "select * From  zdbxofi004.tclienttablelookup";
		List<Client> listofclient = new JdbcTemplate(dataSource).query(sql,
				new DepartmentExtractor());
		return listofclient;
	}

	@Override
	public List<String> listReportType() {
		// TODO Auto-generated method stub
		String sql = "select name From  jasreport.treport";
		List<String> listofReportType = new JdbcTemplate(reportdatasource)
				.queryForList(sql, String.class);
		return listofReportType;
	}

	@Override
	public Map<String, List<Reportpara>> reportParaMap() {
		Map<String, List<Reportpara>> reportToPara = new HashMap<String, List<Reportpara>>();
		String sql = "select a.id as reportid, a.name as reportname, type as paraname, paraid, paratype  From jasreport.treport a inner join jasreport.treporttopara b on a.id = b.reportid inner join jasreport.treportpara c on b.paraid = c.id order by reportid ";
		reportToPara = new JdbcTemplate(reportdatasource).query(sql,
				new ReportParaExtractor());
		return reportToPara;
	}

}
