package com.dci.report.homepageModule;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.dci.report.bean.Client;
import com.dci.report.bean.Department;
import com.dci.report.bean.FormLayout;
import com.dci.report.bean.Report;
import com.dci.report.bean.Reportpara;
import com.dci.report.bean.Transaction;
import com.dci.report.services.Reportdataservice;
import com.dci.report.services.Reporthandleservice;

@Controller
@SessionAttributes(value = { "transaction" })
public class HomeController {

	@Autowired
	Reportdataservice reportdataservice;

	@Autowired
	Reporthandleservice reporthandleservice;

	@RequestMapping(value = "/homepage", method = RequestMethod.GET)
	public ModelAndView homepage(Model model) {
		// ArrayList<Report> listreport = (ArrayList<Report>)
		// reportdataservice.listReport();
		List<Client> clientList = (reporthandleservice.getClientMap());

		ModelAndView modelandview = new ModelAndView("dashboard", "command",
				new Report());
		// modelandview.addObject("listreport", listreport);
		modelandview.addObject("clientlist", clientList);
		return modelandview;
	}

	@RequestMapping(value = "/uitest", method = RequestMethod.GET)
	public ModelAndView initFormLayout(Model model) {
		List<Client> clientList = (reporthandleservice.getClientMap());
		ModelAndView modelandview = new ModelAndView("dashboard", "command",
				new Report());
		List<String> reportTypeList = reportdataservice.listReportType();
		Map<String, List<Reportpara>> reportToPara = reportdataservice
				.reportParaMap();
		Iterator<String> KeySetIterator = reportToPara.keySet().iterator();
		while (KeySetIterator.hasNext()) {
			String reportname = KeySetIterator.next();
			modelandview.addObject(reportname, reportToPara.get(reportname));
		}
		String html = " <label for=\"start_date\">Start Date</label>"
				+ " <input id=\"start_date\" name=\"startdate\" class=\"form-control\" type=\"date\" value=\"\"/>";
		FormLayout formlayout = new FormLayout();
		formlayout.setLayoutHtml(html);
		modelandview.addObject("formlayout", formlayout);
		modelandview.addObject("reportTypeList", reportTypeList);
		modelandview.addObject("clientlist", clientList);

		return modelandview;
	}

	@RequestMapping(value = "/uitest2", method = RequestMethod.GET)
	public ModelAndView homepageWithPopup(
			@RequestParam("reportTypeName") String reportTypename, Model model) {
		List<Client> clientList = (reporthandleservice.getClientMap());
		ModelAndView modelandview = new ModelAndView("dashboard_generate",
				"command", new Report());
		List<String> reportTypeList = reportdataservice.listReportType();
		Map<String, List<Reportpara>> reportToPara = reportdataservice
				.reportParaMap();
		String html = "";
		List<Reportpara> reportpara = reportToPara.get(reportTypename);
		for (Reportpara para : reportpara) {
			int paraID = para.getId();
			switch (paraID) {
			case 1:
				html += " <label for=\"start_date\">Start Date</label>"
						+ " <input id=\"start_date\" name=\"startdate\" class=\"form-control\" type=\"date\" value=\"\"/>";
				break;
			case 2:
				html += " <label for=\"end_date\">End Date</label>"
						+ " <input id=\"end_date\" name=\"enddate\" class=\"form-control\" type=\"date\" value=\"\"/>";
				break;

			case 3:
				html += "<div class=\"container\">"
						+ "<div class=\"row\">"
						+ "<div class=\"col-md-3 panel1\">"
						+ "  <div class=\"panel panel-default panel-primary \">"
						+ "    <div class=\"panel-heading\">Select Client</div>"
						+ "<div class=\"panel-body checkboxes\">"
						+ " <ul class=\"mktree\" id=\"tree1\">";
				int count = 1;
				for (Client client : clientList) {
					html += "<li>" + client.getClientname();
					for (Department department : client.getDepartments()) {
						html += "<ul>"
								+ "<li>"
								+ "<label>"
								+ department.getDepartmentName()
								+ "<input id=\"para"
								+ count
								+ "\""
								+ " name=\"para\" type=\"checkbox\" value=\""
								+ department.getDepartmentID()
								+ "\"/><input type=\"hidden\" name=\"_para\" value=\"on\"/>"
								+ "</label>" + "</li>" + "</ul>";
						count++;
					}
					html += "</li>";
				}
				break;
			}
		}
		FormLayout formlayout = new FormLayout();
		formlayout.setLayoutHtml(html);
		modelandview.addObject("formlayout", formlayout);
		modelandview.addObject("reportTypeList", reportTypeList);

		return modelandview;
	}

	// @RequestMapping(value = "/uitest", method = RequestMethod.GET)
	// @ResponseBody
	// public Object getParmeter(String ReportId) {
	//
	// // query from database
	// // or List object
	//
	// List parameter = new ArrayList();
	// parameter.add("Date");
	//
	// return parameter;
	// }
	@RequestMapping(value = "/result", method = RequestMethod.POST)
	public ModelAndView showTestResult(Report report, Model model) {
		ModelAndView modelandview = new ModelAndView("result");
		modelandview.addObject("report", report);
		return modelandview;
	}

	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public String test(Model model) {
		// Transaction test = new Transaction();
		// test.setUserid(2);
		// test.setReportid(1);
		// ArrayList<Reportpara> arrpara = new ArrayList<Reportpara>();
		// for( int i = 0; i < 3; i++ ) {
		// Reportpara temp = new Reportpara();
		// temp.setId(i);
		// ArrayList<String> temp1 = new ArrayList<String>();
		// temp1.add("Leo");
		// temp.setValue(temp1);
		// arrpara.add(temp);
		// }
		// Reportpara temp = new Reportpara();
		// temp.setId(4);
		// ArrayList<String> temp1 = new ArrayList<String>();
		// temp1.add("Leo");
		// temp1.add("Bi");
		// temp.setValue(temp1);
		// arrpara.add(temp);
		// test.setPara(arrpara);
		// ArrayList<Integer> temp2 = new ArrayList<Integer>();
		// temp2.add(1);
		// temp2.add(2);
		// test.setOutput(temp2);
		// reportdataservice.create(test);

		ArrayList<Transaction> t = (ArrayList<Transaction>) reportdataservice
				.listTransaction();
		System.out.println("Successful!");
		System.out.println(t.get(0).getDate().toString());
		System.out.println(t.get(0).getId());
		System.out.println(t.get(0).getPara().get(2).getValue().get(0));
		System.out.println(t.get(0).getPara().get(2).getValue().get(1));
		System.out.println(t.get(0).getArroutput().get(0).getFilename());
		//reportdataservice.delete(10);
		System.out.println("Successful");
		model.addAttribute("transaction", t.get(0));
		return "redirect:genbillingdeital";

	}

	@RequestMapping(value = "/generate", method = RequestMethod.POST)
	public String generate(Transaction transaction, ModelMap model) {
		reportdataservice.create(transaction);
		String genMethod = transaction.getGenMethod();
		model.addAttribute("transaction", transaction);
		String output = "redirect:" + genMethod;
		return output;

	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public String delete(Report report, ModelMap model) {
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
