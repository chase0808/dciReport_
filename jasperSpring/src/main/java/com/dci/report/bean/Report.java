package com.dci.report.bean;

import java.util.ArrayList;

public class Report {

	private int id;
	private int userid;
	private String startdate;
	private String type;
	private String enddate;
	private ArrayList<Integer> para = new ArrayList<Integer>();
	private Test test;
	private ArrayList<Test> arrtest = new ArrayList<Test>();
	private String temp;

	public String getTemp() {
		return temp;
	}

	public void setTemp(String temp) {
		this.temp = temp;
		arrtest.add(test);
		System.out.println("I am in the setTemp!");
	}

	public Test getTest() {
		test = new Test();
		return test;
	}

	public void setTest(Test test) {
		this.test = test;
	}

	public ArrayList<Test> getArrtest() {
		return arrtest;
	}

	public void setArrtest(ArrayList<Test> arrtest) {
		this.arrtest = arrtest;
	}

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

	public String getStartdate() {
		return startdate;
	}

	public void setStartdate(String startdate) {
		arrtest.add(test);
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



}
