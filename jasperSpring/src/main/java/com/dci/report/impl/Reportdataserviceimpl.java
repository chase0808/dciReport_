package com.dci.report.impl;

import java.text.SimpleDateFormat;
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
		java.util.Date date = new java.util.Date();
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyyhh-mm-ssa");
		String formattedDate = sdf.format(date);
		for (int i = 0; i < transaction.getOutput().size(); i++) {
			int outputid = transaction.getOutput().get(i);
			String query = "select name from jasreport.treport where id = ?";
			String rname = jdbcTemplateObject.queryForObject(query,
					String.class, transaction.getReportid());
			String fname = Integer.toString(transaction.getUserid()) + rname
					+ formattedDate;
			String status = "In progress";
			jdbcTemplateObject.update(SQL, tid, outputid, status, fname);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Transaction getTransaction(Integer transactionid) {
		String SQL = "select a.id, name, description, userid, a.date, reportid, paraid, paravalue, paratype from jasreport.ttransaction a inner join jasreport.ttransactionpara b on a.id = b.transactionid inner join jasreport.treportpara c on b.paraid = c.id inner join jasreport.treport e on a.reportid = e.id where a.id = ?";
		ArrayList<Transaction> transactions = jdbcTemplateObject.query(SQL,
				new TransactionParaExtractor(), transactionid);
		SQL = "select * from jasreport.ttransactionoutput a inner join jasreport.toutput b on a.outputid = b.id where transactionid = ?";
		ArrayList<Reportoutput> reportoutput = (ArrayList<Reportoutput>) jdbcTemplateObject
				.query(SQL, new TransactionOpMapper(), transactionid);
		transactions.get(0).setArroutput(reportoutput);
		System.out.println("In getTransaction"
				+ transactions.get(0).getPara().get(2).getValue().toString());
		return transactions.get(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Transaction> listTransaction() {

		String SQL = "select a.id, name, description, userid, a.date, reportid, paraid, paravalue, paratype from jasreport.ttransaction a inner join jasreport.ttransactionpara b on a.id = b.transactionid inner join jasreport.treportpara c on b.paraid = c.id inner join jasreport.treport e on a.reportid = e.id";
		ArrayList<Transaction> transactions = jdbcTemplateObject.query(SQL,
				new TransactionParaExtractor());
		SQL = "select * from jasreport.ttransactionoutput a inner join jasreport.toutput b on a.outputid = b.id where transactionid = ?";
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

	@Override
	public Map<String, List<Integer>> reportOutputIDMap() {
		Map<String, List<Integer>> reportToOutputID = new HashMap<String, List<Integer>>();
		String sql = "select a.name as reportname, c.id as outputid from jasreport.treport a inner join jasreport.treporttooutput b on a.id = b.reportid inner join jasreport.toutput c on b.outputid = c.id";
		reportToOutputID = new JdbcTemplate(reportdatasource).query(sql,
				new ReportOutputIDExtractor());
		return reportToOutputID;
	}

	@SuppressWarnings("deprecation")
	@Override
	public int getReportID(String reportTypename) {
		String sql = "select id from jasreport.treport where name=?";
		int reportid = new JdbcTemplate(reportdatasource).queryForInt(sql,
				reportTypename);
		return reportid;
	}

	@SuppressWarnings("deprecation")
	@Override
	public int getMaxTid() {
		String sql = "select max(id) from jasreport.ttransaction";
		int tid = jdbcTemplateObject.queryForInt(sql);
		return tid;
	}

}
