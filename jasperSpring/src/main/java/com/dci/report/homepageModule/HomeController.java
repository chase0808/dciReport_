package com.dci.report.homepageModule;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.dci.report.bean.Client;
import com.dci.report.bean.Department;
import com.dci.report.bean.FormLayout;
import com.dci.report.bean.Report;
import com.dci.report.bean.Reportoutput;
import com.dci.report.bean.Reportpara;
import com.dci.report.bean.Transaction;
import com.dci.report.services.Reportdataservice;
import com.dci.report.services.Reporthandleservice;

@Controller
@SessionAttributes(value = { "transaction", "userid" })
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
		ArrayList<Transaction> transactionList = (ArrayList<Transaction>) reportdataservice
				.listTransaction();
		List<Client> clientList = (reporthandleservice.getClientMap());
		ModelAndView modelandview = new ModelAndView("dashboard");
		List<String> reportTypeList = reportdataservice.listReportType();
		modelandview.addObject("reportTypeList", reportTypeList);
		modelandview.addObject("clientlist", clientList);
		modelandview.addObject("transactionList", transactionList);
		return modelandview;
	}

	@RequestMapping(value = "/uitest2", method = RequestMethod.GET)
	public ModelAndView homepageWithPopup(
			@RequestParam("reportTypeName") String reportTypename, Model model) {
		ArrayList<Transaction> transactionList = (ArrayList<Transaction>) reportdataservice
				.listTransaction();
		List<Client> clientList = (reporthandleservice.getClientMap());
		List<String> reportTypeList = reportdataservice.listReportType();
		Map<String, List<Reportpara>> reportToPara = reportdataservice
				.reportParaMap();
		Map<String, List<Integer>> reportToOutputID = reportdataservice
				.reportOutputIDMap();
		int reportID = reportdataservice.getReportID(reportTypename);

		List<Integer> outputIDs = reportToOutputID.get(reportTypename);
		List<Reportpara> reportpara = reportToPara.get(reportTypename);

		Transaction transaction = new Transaction(
				(ArrayList<Reportpara>) reportpara);
		transaction.setReportid(reportID);
		System.out.println("ID: " + transaction.getReportid());
		ModelAndView modelandview = new ModelAndView("dashboard_generate",
				"command", transaction);
		modelandview.addObject("clientlist", clientList);
		modelandview.addObject("reportTypeList", reportTypeList);
		modelandview.addObject("transactionList", transactionList);
		modelandview.addObject("outputIDs", outputIDs);
		return modelandview;
	}

	@RequestMapping(value = "/uitest3", method = RequestMethod.GET)
	public ModelAndView homepageWithFilledPopup(

	@RequestParam("transactionID") int transactionID, Model model) {
		ArrayList<Transaction> transactionList = (ArrayList<Transaction>) reportdataservice
				.listTransaction();
		List<Client> clientList = (reporthandleservice.getClientMap());
		Map<String, List<Reportpara>> reportToPara = reportdataservice
				.reportParaMap();
		String reportTypeName = reportdataservice.getTransaction(transactionID)
				.getName();
		List<Reportpara> reportpara = reportToPara.get(reportTypeName);

		Transaction transaction = new Transaction(
				(ArrayList<Reportpara>) reportpara);
		ModelAndView modelandview = new ModelAndView("dashboard_regenerate",
				"command", transaction);
		List<String> reportTypeList = reportdataservice.listReportType();
		List<Department> selectedDepartment = new ArrayList<Department>();
		String html = "";
		ArrayList<Reportpara> reportParaValue = reportdataservice
				.getTransaction(transactionID).getPara();
		Map<String, List<Integer>> reportToOutputID = reportdataservice
				.reportOutputIDMap();
		List<Integer> outputIDs = reportToOutputID.get(reportdataservice
				.getTransaction(transactionID).getName());
		List<Integer> transactionOuputIDs = new ArrayList<Integer>();
		List<Reportoutput> transactionOutput = reportdataservice
				.getTransaction(transactionID).getArroutput();
		for (Reportoutput reportoutput : transactionOutput) {
			transactionOuputIDs.add(reportoutput.getOutputid());
		}
		for (Reportpara para : reportParaValue) {
			int paraID = para.getId();
			ArrayList<String> paraValues = para.getValue();
			switch (paraID) {
			case 1:
				html += " <label for=\"start_date\">Start Date</label>"
						+ " <input id=\"start_date\" name=\"startdate\" class=\"form-control\" type=\"date\" value=\""
						+ paraValues.get(0) + "\"/>";
				break;
			case 2:
				html += " <label for=\"end_date\">End Date</label>"
						+ " <input id=\"end_date\" name=\"enddate\" class=\"form-control\" type=\"date\" value=\""
						+ paraValues.get(0) + "\"/>";
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
				for (int i = 0; i < para.getValue().size(); i++) {
					System.out.println("para value" + para.getValue().get(i));
				}
				for (Client client : clientList) {
					html += "<li>" + client.getClientname();
					for (Department department : client.getDepartments()) {
						html += "<ul>" + "<li>" + "<label>"
								+ department.getDepartmentName()
								+ "<input id=\"para" + count + "\""
								+ " name=\"para\" type=\"checkbox\" value=\""
								+ department.getDepartmentID() + "\"";
						boolean flag = false;

						for (int i = 0; i < para.getValue().size(); i++) {
							if (Integer.toString(department.getDepartmentID())
									.equals(para.getValue().get(i))) {
								flag = true;
								selectedDepartment.add(department);

							}
						}
						if (flag) {
							html += "checked";
						}
						html += "/><input type=\"hidden\" name=\"_para\" value=\"on\"/>"
								+ "</label>" + "</li>" + "</ul>";
						count++;
					}
					html += "</li>";
				}
				break;
			}
		}
		System.out.println("selectedDepartment size:"
				+ selectedDepartment.size());
		FormLayout formlayout = new FormLayout();
		formlayout.setLayoutHtml(html);
		modelandview.addObject("reportParaValue", reportParaValue);
		modelandview.addObject("formlayout", formlayout);
		modelandview.addObject("clientlist", clientList);
		modelandview.addObject("reportTypeList", reportTypeList);
		modelandview.addObject("transactionList", transactionList);
		modelandview.addObject("selectedDepartment", selectedDepartment);
		modelandview.addObject("outputIDs", outputIDs);
		modelandview.addObject("transactionOuputIDs", transactionOuputIDs);
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
	public ModelAndView showTestResult(Transaction transaction, Model model) {
		System.out.println(transaction.getPara().size());
		System.out.println(transaction.getId());
		for (int i = 0; i < transaction.getPara().size(); i++) {
			System.out.println(transaction.getPara().get(i).getId());
			System.out.print(transaction.getPara().get(i).getType());
		}
		System.out.println(transaction.getOutput().toString());
		ModelAndView modelandview = new ModelAndView("result");
		modelandview.addObject("transaction", transaction);
		return modelandview;
	}

	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public String test(ModelMap model) {
		int userid = (Integer) model.get("userid");
		System.out.println(userid);
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
		// reportdataservice.delete(10);
		System.out.println("Successful");
		model.addAttribute("transaction", t.get(0));
		return "redirect:genbillingdeital";

	}

	@RequestMapping(value = "/generate", method = RequestMethod.POST)
	public String generate(Transaction transaction, ModelMap model) {

		int userid = (Integer) model.get("userid");
		transaction.setUserid(userid);
		reportdataservice.create(transaction);
		int tid = reportdataservice.getMaxTid();
		System.out.println("In generate para size"
				+ transaction.getPara().get(2).getValue().size());
		Transaction t1 = reportdataservice.getTransaction(tid);
		System.out.println("In generate para size"
				+ t1.getPara().get(2).getValue().size());
		System.out.println("reportID:" + transaction.getReportid());
		model.addAttribute("transaction", t1);
		String genMethod = t1.getGenMethod();

		String output = "redirect:" + genMethod;
		return output;

	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String delete(@RequestParam("transactionID") int transactionID,
			ModelMap model) {
		reportdataservice.delete(transactionID);
		return "redirect: uitest";
	}

}
