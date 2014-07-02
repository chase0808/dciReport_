package com.dci.report.bean;

import java.sql.Timestamp;
import java.util.ArrayList;

public class Transaction {
	private int id;
	private int userid;
	private int reportid;
	private Timestamp date;
	private String genMethod;
	private Reportpara reportpara = new Reportpara();
	private ArrayList<Reportpara> para = new ArrayList<Reportpara>();
	private ArrayList<Integer> output = new ArrayList<Integer>();
	private ArrayList<Reportoutput> arroutput = new ArrayList<Reportoutput>();
	
	public Transaction(){};
	
	public Transaction(int id){
		this.id = id;
	}
	public ArrayList<Reportoutput> getArroutput() {
		return arroutput;
	}
	public void setArroutput(ArrayList<Reportoutput> arroutput) {
		this.arroutput = arroutput;
	}
	public ArrayList<Integer> getOutput() {
		return output;
	}
	public void setOutput(ArrayList<Integer> output) {
		this.output = output;
	}
	public int getReportid() {
		return reportid;
	}
	public void setReportid(int reportid) {
		this.reportid = reportid;
	}

	public Reportpara getReportpara() {
		return reportpara;
	}
	public void setReportpara(Reportpara reportpara) {
		this.reportpara = reportpara;
	}
	public ArrayList<Reportpara> getPara() {
		return para;
	}
	public void setPara(ArrayList<Reportpara> para) {
		this.para = para;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public Timestamp getDate() {
		return date;
	}
	public void setDate(Timestamp date) {
		this.date = date;
	}
	
	public String getGenMethod() {
		return genMethod;
	}

	public void setGenMethod(String genMethod) {
		this.genMethod = genMethod;
	}
	
}