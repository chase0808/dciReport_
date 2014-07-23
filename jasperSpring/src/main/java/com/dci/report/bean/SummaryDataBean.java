package com.dci.report.bean;

public class SummaryDataBean {

	private String CLIENTNAME;
	private String DEPTNAME;
	private String title;
	private int rvalues;
	

	public String getCLIENTNAME() {
		return CLIENTNAME;
	}
	public void setCLIENTNAME(String cLIENTNAME) {
		CLIENTNAME = cLIENTNAME;
	}
	public String getDEPTNAME() {
		return DEPTNAME;
	}
	public void setDEPTNAME(String dEPTNAME) {
		DEPTNAME = dEPTNAME;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getRvalues() {
		return rvalues;
	}
	public void setRvalues(int rvalues) {
		this.rvalues = rvalues;
	}
	
}
