package com.dci.report.homepageModule;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.dci.report.bean.Client;
import com.dci.report.bean.Report;
import com.dci.report.bean.Reportpara;
import com.dci.report.bean.Transaction;
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
		ArrayList<Report> listreport = (ArrayList<Report>) reportdataservice
				.listReport();
		List<Client> clientList = (reporthandleservice.getClientMap());

		ModelAndView modelandview = new ModelAndView("dashboard", "command",
				new Report());
		modelandview.addObject("listreport", listreport);
		modelandview.addObject("clientlist", clientList);
		return modelandview;
	}

	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public ModelAndView test(Model model) {
<<<<<<< HEAD
		// Report report = reportdataservice.getReport(4);
		// reporthandleservice.generatereport(report);
=======
		Transaction test = new Transaction();
		test.setUserid(2);
		test.setReportid(1);
		ArrayList<Reportpara> arrpara = new ArrayList<Reportpara>();
		for( int i = 0; i < 3; i++ ) {
			Reportpara temp = new Reportpara();
			temp.setId(i);
			ArrayList<String> temp1 = new ArrayList<String>();
			temp1.add("Leo");
			temp.setValue(temp1);
			arrpara.add(temp);
		}
		Reportpara temp = new Reportpara();
		temp.setId(4);
		ArrayList<String> temp1 = new ArrayList<String>();
		temp1.add("Leo");
		temp1.add("Bi");
		temp.setValue(temp1);
		arrpara.add(temp);
		test.setPara(arrpara);
		ArrayList<Integer> temp2 = new ArrayList<Integer>();
		temp2.add(1);
		temp2.add(2);
		test.setOutput(temp2);
		reportdataservice.create(test);
		System.out.println("Successful!");
>>>>>>> 28fdf2d1f079fdffaee881d41d8f65a4136e1ffa
		return new ModelAndView("test", "command", new Report());

	}

	@RequestMapping(value = "/generate", method = RequestMethod.POST)
	public String generate(Report report, ModelMap model) {
<<<<<<< HEAD
		// reportdataservice.create(report);
		System.out.println("Before");
=======
		//reportdataservice.create(report);
>>>>>>> 28fdf2d1f079fdffaee881d41d8f65a4136e1ffa
		reporthandleservice.generatereport(report);
		System.out.println("After");
		return "redirect:homepage";
	}

	@RequestMapping(value = "/regenerate", method = RequestMethod.POST)
	public String regenerate(Report report, ModelMap model) {
		System.out.println("I am in the regenerate!");
		// reporthandleservice.generatereport(report);
		return "successview";
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public String delete(Report report, ModelMap model) {
		System.out.println("I am in the delete!");
		// int reportid = report.getId();
		// reportdataservice.delete(reportid);
		return "successview";
	}

	@RequestMapping("/result")
	public ModelAndView processRequest(@ModelAttribute Report report) {
		ModelAndView modelAndView = new ModelAndView("result");
		modelAndView.addObject("report", report);

		return modelAndView;
	}
}
