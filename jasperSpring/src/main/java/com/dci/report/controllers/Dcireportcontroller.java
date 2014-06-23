package com.dci.report.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.dci.report.bean.Client;
import com.dci.report.bean.Departments;
import com.dci.report.bean.Summaryinput;
import com.dci.report.services.Summaryreportservice;

@Controller
public class Dcireportcontroller {

	@Autowired
	Summaryreportservice summaryreportservice;

	@RequestMapping("/homepage1")
	public ModelAndView initHomepage() {

		// process info and generate report

		List<Client> clientList = (summaryreportservice.getClientMap());

		ModelAndView modelandview = new ModelAndView("dashboard", "command",
				new Departments());

		modelandview.addObject("clientlist", clientList);

		// modelandview.addObject("list", clientList);

		return modelandview;
	}

	@RequestMapping("/generateSummary")
	public String GetSummryReport(@ModelAttribute Summaryinput summaryinput) {
		// model.addAttribute("library", summaryinput.getLibrary());
		// model.addAttribute("start_date", summaryinput.getStart_date());
		// model.addAttribute("end_date", summaryinput.getEnd_date());
		// process info and generate report

		// Summaryinput summaryinput2 = new Summaryinput();
		// summaryinput2.setEnd_date("ksksks");
		// summaryinput2.setLibrary("zdbxjhf004.tcontent");
		// summaryinput2.setStart_date("20-20-20");
		String returnVal = summaryreportservice.getSummaryreport(summaryinput);

		// model.addAttribute("returnvalue", returnVal);
		// respond back to the UI
		return "successview";
	}

	public Summaryreportservice getSummaryreportservice() {
		return summaryreportservice;
	}

	public void setSummaryreportservice(
			Summaryreportservice summaryreportservice) {
		this.summaryreportservice = summaryreportservice;
	}

}
