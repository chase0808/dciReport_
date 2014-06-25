package com.dci.report.bean;

import java.sql.Timestamp;

public class Transaction {
	private int id;
	private int userid;
	private Timestamp date;
	//private ArrayList<Reportpara> para;
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

	
}
