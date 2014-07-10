package com.dci.report.homepageModule;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
import com.dci.report.bean.Reportoutput;
import com.dci.report.bean.Reportpara;
import com.dci.report.bean.Transaction;
import com.dci.report.services.Reportdataservice;
import com.dci.report.services.Reporthandleservice;

@Controller
@SessionAttributes(value = { "transaction", "userid", "username" })
public class HomeController {

	@Autowired
	private String path;

	@Autowired
	Reportdataservice reportdataservice;

	@Autowired
	Reporthandleservice reporthandleservice;

	@RequestMapping(value = "/homepage", method = RequestMethod.GET)
	public ModelAndView homepage(
			@RequestParam(value = "reportTypeName", required = false) String reportTypename,
			@RequestParam(value = "transactionID", required = false) Integer transactionID,
			ModelMap model) {
		// Get a list of all clients
		List<Client> clientList = (reporthandleservice.getClientMap());
		// Get a list of all transactions to populate the history table
		ArrayList<Transaction> transactionList = (ArrayList<Transaction>) reportdataservice
				.listTransaction();
		// Get a list of all reportTypes to populate the dropdown list
		List<String> reportTypeList = reportdataservice.listReportType();
		ModelAndView modelandview = new ModelAndView("homepage");
		// Get username in the session
		String username = (String) model.get("username");
		modelandview.addObject("username", username);
		// add defaul path to view
		modelandview.addObject("path", path);
		modelandview.addObject("clientlist", clientList);
		modelandview.addObject("transactionList", transactionList);
		modelandview.addObject("reportTypeList", reportTypeList);

		if (reportTypename != null) {
			// change modal here
		}

		if (transactionID != null) {
			// change modal and auto fill the request form
		}

		return modelandview;
	}

	@RequestMapping(value = "/uitest", method = RequestMethod.GET)
	public ModelAndView initFormLayout(ModelMap model) {
		String username = (String) model.get("username");
		ArrayList<Transaction> transactionList = (ArrayList<Transaction>) reportdataservice
				.listTransaction();
		List<Client> clientList = (reporthandleservice.getClientMap());
		ModelAndView modelandview = new ModelAndView("dashboard");
		List<String> reportTypeList = reportdataservice.listReportType();
		modelandview.addObject("reportTypeList", reportTypeList);
		modelandview.addObject("clientlist", clientList);
		modelandview.addObject("transactionList", transactionList);
		modelandview.addObject("username", username);
		modelandview.addObject("path", path);
		return modelandview;
	}

	@RequestMapping(value = "/uitest2", method = RequestMethod.GET)
	public ModelAndView homepageWithPopup(
			@RequestParam("reportTypeName") String reportTypename,
			ModelMap model) {
		String username = (String) model.get("username");
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
		System.out.println(reportpara.size());
		Transaction transaction = new Transaction(
				(ArrayList<Reportpara>) reportpara);
		transaction.setReportid(reportID);
		ModelAndView modelandview = new ModelAndView("dashboard_generate",
				"command", transaction);
		modelandview.addObject("clientlist", clientList);
		modelandview.addObject("reportTypeList", reportTypeList);
		modelandview.addObject("transactionList", transactionList);
		modelandview.addObject("outputIDs", outputIDs);
		modelandview.addObject("username", username);
		modelandview.addObject("path", path);
		return modelandview;
	}

	@RequestMapping(value = "/uitest3", method = RequestMethod.GET)
	public ModelAndView homepageWithFilledPopup(

	@RequestParam("transactionID") int transactionID, ModelMap model) {
		String username = (String) model.get("username");
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
		int reportID = reportdataservice.getReportID(reportTypeName);
		transaction.setReportid(reportID);
		ModelAndView modelandview = new ModelAndView("dashboard_regenerate",
				"command", transaction);

		List<String> reportTypeList = reportdataservice.listReportType();
		List<Department> selectedDepartment = new ArrayList<Department>();

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
			if (paraID == 3) {

				for (Client client : clientList) {

					for (Department department : client.getDepartments()) {

						for (int i = 0; i < para.getValue().size(); i++) {
							if (Integer.toString(department.getDepartmentID())
									.equals(para.getValue().get(i))) {
								selectedDepartment.add(department);

							}
						}

					}

				}

			}
		}

		modelandview.addObject("reportParaValue", reportParaValue);

		modelandview.addObject("clientlist", clientList);
		modelandview.addObject("reportTypeList", reportTypeList);
		modelandview.addObject("transactionList", transactionList);
		modelandview.addObject("selectedDepartment", selectedDepartment);
		modelandview.addObject("outputIDs", outputIDs);
		modelandview.addObject("transactionOuputIDs", transactionOuputIDs);
		modelandview.addObject("username", username);
		modelandview.addObject("path", path);
		return modelandview;
	}

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
	public String test(ModelMap model, HttpServletRequest request) {

		return "redirect:login";

	}

	@RequestMapping(value = "/generate", method = RequestMethod.POST)
	public String generate(Transaction transaction, ModelMap model) {

		int userid = (Integer) model.get("userid");
		transaction.setUserid(userid);
		reportdataservice.create(transaction);
		int tid = reportdataservice.getMaxTid();
		Transaction t1 = reportdataservice.getTransaction(tid);
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
