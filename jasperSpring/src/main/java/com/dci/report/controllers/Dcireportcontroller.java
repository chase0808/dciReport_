package com.dci.report.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.dci.report.bean.Summaryinput;
import com.dci.report.services.Summaryreportservice;

@Controller
public class Dcireportcontroller {

	@Autowired
	Summaryreportservice summaryreportservice;

	@RequestMapping(value = "/summary", method = RequestMethod.GET)
	public ModelAndView initSummryReport(Model model) {

		// process info and generate report
		return new ModelAndView("form", "command", new Summaryinput());
	}

	@RequestMapping(value = "/generateSummary", method = RequestMethod.POST)
	public String GetSummryReport(
			@ModelAttribute("SpringWeb") Summaryinput summaryinput,
			ModelMap model) {
		model.addAttribute("library", summaryinput.getLibrary());
		model.addAttribute("start_date", summaryinput.getStart_date());
		// model.addAttribute("end_date", summaryinput.getEnd_date());
		// process info and generate report

		Summaryinput summaryinput2 = new Summaryinput();
		summaryinput2.setEnd_date("ksksks");
		summaryinput2.setLibrary("zdbxjhf004.tcontent");
		summaryinput2.setStart_date("20-20-20");
		String returnVal = summaryreportservice.getSummaryreport(summaryinput2);

		model.addAttribute("returnvalue", returnVal);
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
