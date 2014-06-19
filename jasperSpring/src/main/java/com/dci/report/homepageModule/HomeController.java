package com.dci.report.homepageModule;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.dci.report.bean.Report;
import com.dci.report.services.Reportdataservice;
import com.dci.report.services.Reporthandleservice;

@Controller
public class HomeController {
	
	@Autowired
	Reportdataservice reportdataservice;
	
	@Autowired
	Reporthandleservice reporthandleservice;
	
	@RequestMapping(value = "/homepage", method = RequestMethod.GET)
	public ModelAndView homepage(Model model) {
		return new ModelAndView("homepage","command", new Report());
	}
	
	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public ModelAndView test(Model model) {
		Report report = reportdataservice.getReport(4);
		return new ModelAndView("test","command", report);
	}
	
	@RequestMapping(value = "/generate", method = RequestMethod.POST)
	public String generate(Report report, ModelMap model) {
		reportdataservice.create(report);
		reporthandleservice.generatereport(report);
		return "successview";
	}
	
	@RequestMapping(value = "/regenerate", method = RequestMethod.POST)
	public String regenerate(Report report, ModelMap model) {
		System.out.println("I am in the regenerate!");
		reporthandleservice.generatereport(report);
		return "successview";
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public String delete(Report report, ModelMap model) {
		int reportid = report.getId();
		reportdataservice.delete(reportid);
		return "successview";
	}

}
