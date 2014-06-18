package com.dci.report.homepageModule;

import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.dci.report.bean.Report;
import com.dci.report.services.Reportdataservice;

@Controller
public class HomeController {
	Reportdataservice reportdataservice;
	
	@RequestMapping(value = "/homepage", method = RequestMethod.GET)
	public ModelAndView homepage(Model model) {
		return new ModelAndView("homepage","command", new Report());
	}
	
	@RequestMapping(value = "/generate", method = RequestMethod.POST)
	public String generate(Report report, ModelMap model) {
		System.out.println(report.getType());
		reportdataservice.create(report);
		return "successview";
	}
	
	@RequestMapping(value = "/regenerate", method = RequestMethod.POST)
	public String regenerate() {
		return null;
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public String delete() {
		return null;
	}

}
