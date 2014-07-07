package com.dci.report.bean;

public class Reportoutput {

	private int outputid;
	private String status;
	private String filename;
	private String type;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getOutputid() {
		return outputid;
	}
	public void setOutputid(int outputid) {
		this.outputid = outputid;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	
	
}
