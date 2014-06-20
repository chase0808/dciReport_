package com.dci.report.bean;

import java.util.ArrayList;

public class Report {

	private int id;
	private int userid;
	private String type;
	private String startdate;
	private String enddate;
	private ArrayList<Integer> para = new ArrayList<Integer>();

	public Report() {
	};

	public Report(int id) {
		this.id = id;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStartdate() {
		return startdate;
	}

	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}

	public String getEnddate() {
		return enddate;
	}

	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}

	public ArrayList<Integer> getPara() {
		return para;
	}

	public void setPara(ArrayList<Integer> para) {
		this.para = para;
	}

}
