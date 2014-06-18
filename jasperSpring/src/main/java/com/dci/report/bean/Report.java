package com.dci.report.bean;

import java.util.ArrayList;

public class Report {

		String type;
		String startdate;
		String enddate;
		ArrayList<Integer> para = new ArrayList<Integer>();
		
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
