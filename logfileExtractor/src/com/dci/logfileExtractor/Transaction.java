package com.dci.logfileExtractor;

public class Transaction {
	private String ipAddress;
	private String numberI;
	private String timeStamp;
	private String methodURL;
	private String numberII;
	private String browser;
	private String configureInfo;

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getNumberI() {
		return numberI;
	}

	public void setNumberI(String numberI) {
		this.numberI = numberI;
	}

	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getMethodURL() {
		return methodURL;
	}

	public void setMethodURL(String methodURL) {
		this.methodURL = methodURL;
	}

	public String getNumberII() {
		return numberII;
	}

	public void setNumberII(String numberII) {
		this.numberII = numberII;
	}

	public String getBrowser() {
		return browser;
	}

	public void setBrowser(String browser) {
		this.browser = browser;
	}

	public String getConfigureInfo() {
		return configureInfo;
	}

	public void setConfigureInfo(String configureInfo) {
		this.configureInfo = configureInfo;
	}
}
