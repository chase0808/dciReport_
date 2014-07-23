package com.dci.report.bean;

public class DetailDataBean {

	private int FBOOKSTATUSID;
	private int FCLIENTID;
	private String FBOOK_TYPE;
	private String FTIMELASTCHANGED;
	private String FLASTCHANGEDBY;
	private int FPAGECOUNT;
	private String DOCUMENT_NAME;
	
	public int getFBOOKSTATUSID() {
		return FBOOKSTATUSID;
	}
	public void setFBOOKSTATUSID(int fBOOKSTATUSID) {
		FBOOKSTATUSID = fBOOKSTATUSID;
	}
	public int getFCLIENTID() {
		return FCLIENTID;
	}
	public void setFCLIENTID(int fCLIENTID) {
		FCLIENTID = fCLIENTID;
	}
	public String getFBOOK_TYPE() {
		return FBOOK_TYPE;
	}
	public void setFBOOK_TYPE(String fBOOK_TYPE) {
		FBOOK_TYPE = fBOOK_TYPE;
	}
	public String getFTIMELASTCHANGED() {
		return FTIMELASTCHANGED;
	}
	public void setFTIMELASTCHANGED(String fTIMELASTCHANGED) {
		FTIMELASTCHANGED = fTIMELASTCHANGED;
	}
	public String getFLASTCHANGEDBY() {
		return FLASTCHANGEDBY;
	}
	public void setFLASTCHANGEDBY(String fLASTCHANGEDBY) {
		FLASTCHANGEDBY = fLASTCHANGEDBY;
	}
	public int getFPAGECOUNT() {
		return FPAGECOUNT;
	}
	public void setFPAGECOUNT(int fPAGECOUNT) {
		FPAGECOUNT = fPAGECOUNT;
	}
	public String getDOCUMENT_NAME() {
		return DOCUMENT_NAME;
	}
	public void setDOCUMENT_NAME(String dOCUMENT_NAME) {
		DOCUMENT_NAME = dOCUMENT_NAME;
	}
}
