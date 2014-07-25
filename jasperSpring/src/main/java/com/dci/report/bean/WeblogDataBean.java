package com.dci.report.bean;

public class WeblogDataBean {
	
	private String CLIENTIPADDRESS;
	private String REQUESTTIMESTAMP;
	
	public String getCLIENTIPADDRESS() {
		return CLIENTIPADDRESS;
	}
	public void setCLIENTIPADDRESS(String cLIENTIPADDRESS) {
		CLIENTIPADDRESS = cLIENTIPADDRESS;
	}
	public String getREQUESTTIMESTAMP() {
		return REQUESTTIMESTAMP;
	}
	public void setREQUESTTIMESTAMP(String rEQUESTTIMESTAMP) {
		REQUESTTIMESTAMP = rEQUESTTIMESTAMP;
	}
}
