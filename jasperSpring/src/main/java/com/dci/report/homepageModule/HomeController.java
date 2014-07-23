package com.dci.report.homepageModule;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.dci.report.bean.Client;
import com.dci.report.bean.Department;
import com.dci.report.bean.Reportoutput;
import com.dci.report.bean.Reportpara;
import com.dci.report.bean.Transaction;
import com.dci.report.impl.TransactionComparator;
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
		Collections.sort(transactionList, new TransactionComparator());
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
		Transaction transaction = new Transaction();
		modelandview.addObject("command", transaction);

		return modelandview;
	}

	@RequestMapping(value = "/regenerate", method = RequestMethod.GET)
	@ResponseBody
	public String getTransactionID(
			@RequestParam("transactionID") int transactionID, ModelAndView model) {
		System.out.println("In regenerate");
		Transaction transaction = new Transaction();
		List<Client> clientList = (reporthandleservice.getClientMap());
		// get all the para for this type of report
		Map<String, List<Reportpara>> reportToPara = reportdataservice
				.reportParaMap();
		String reportTypeName = reportdataservice.getTransaction(transactionID)
				.getName();
		// get all output types of this report type
		Map<String, List<Reportoutput>> reportToOutput = reportdataservice
				.reportOutputMap();
		List<Reportoutput> outputs = reportToOutput.get(reportTypeName);
		List<Reportpara> reportpara = reportToPara.get(reportTypeName);
		transaction.setPara((ArrayList<Reportpara>) reportpara);
		int reportID = reportdataservice.getReportID(reportTypeName);
		transaction.setReportid(reportID);
		model.addObject("command", transaction);
		// get the para value of this report
		ArrayList<Reportpara> reportParaValue = reportdataservice
				.getTransaction(transactionID).getPara();
		// get all output type of that transaction

		List<Integer> transactionOuputIDs = new ArrayList<Integer>();
		List<Reportoutput> transactionOutput = reportdataservice
				.getTransaction(transactionID).getArroutput();
		for (Reportoutput reportoutput : transactionOutput) {
			transactionOuputIDs.add(reportoutput.getOutputid());
		}
		// get a list of selected departments
		List<Department> selectedDepartment = new ArrayList<Department>();
		String html = "";
		html += "<div class=\"modal-header\">\n";
		html += "<button type=\"button\" class=\"close\" data-dismiss=\"modal\" aria-hidden=\"true\">&times;</button>\n";
		html += "<h4 class=\"modal-title\" id=\"myModalLabel\">"
				+ reportTypeName + "</h4>\n";
		html += "</div>\n" + "<div class=\"modal-body\">";

		html += "<form id=\"command\" role=\"form\" commandname=\"departments\" action=\"/report/generate\" method=\"POST\" onsubmit=\"return validate();\">\n";
		html += "<div class=\"form-group\">";
		html += "<input id=\"reportid\" name=\"reportid\"  type=\"hidden\" value=\"" + reportID + "\"/>\n";
		int count = 0;
		for (Reportpara para : reportParaValue) {
			int paraID = para.getId();
			ArrayList<String> paraValues = para.getValue();
			switch (paraID) {
			case 1:
				html += " <label for=\"start_date\">Start Date</label>\n";
				html += "<input id=\"para" + count + ".id\" name=\"para["
						+ count + "].id\" value=\"" + (count + 1)
						+ "\" type=\"hidden\" value=\"" + (count + 1)
						+ "\"/>\n";
				html += "<input id=\"start_date\" name=\"para["
						+ count
						+ "].value\" class=\"form-control\" required=\"required\" type=\"date\" value=\""
						+ paraValues.get(0) + "\"/>\n";
				break;
			case 2:
				html += " <label for=\"end_date\">End Date</label>\n";
				html += "<input id=\"para" + count + ".id\" name=\"para["
						+ count + "].id\" value=\"" + (count + 1)
						+ "\" type=\"hidden\" value=\"" + (count + 1)
						+ "\"/>\n";
				html += "<input id=\"end_date\" name=\"para["
						+ count
						+ "].value\" class=\"form-control\" required=\"required\" type=\"date\" value=\""
						+ paraValues.get(0) + "\"/>\n";
				break;

			case 3:
				html += "<div class=\"container\">\n"
						+ "<div class=\"row\">\n"
						+ "<div class=\"col-md-3 panel1\">\n"
						+ "  <div class=\"panel panel-default panel-primary \">\n"
						+ "    <div class=\"panel-heading\">Select Department</div>\n"
						+ "<div class=\"panel-body checkboxes\">\n"
						+ " <ul class=\"mktree\" id=\"tree1\">\n";
				int departmentcount = 1;
				for (Client client : clientList) {
					html += "<li>" + "<p class=\"text-primary\">"
							+ client.getClientname() + "</p>";
					for (Department department : client.getDepartments()) {
						html += "<ul>\n" + "<li>\n" + "<label>\n"
								+ department.getDepartmentName();
						html += "<input id=\"para"
								+ count
								+ ".value"
								+ departmentcount
								+ "\" name=\"para["
								+ count
								+ "].value\" name=\"dept\" type=\"checkbox\" value=\""
								+ department.getDepartmentID() + "\" ";
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
						html += "/>\n";
						html += "<input id=\"para" + count
								+ ".id\" name=\"para[" + count
								+ "].id\" value=\"" + (count + 1)
								+ "\" type=\"hidden\" value=\"" + (count + 1)
								+ "\"/>\n";
						html += "</label>\n</li>\n</ul>\n";
						departmentcount++;
					}
					html += "</li>";
				}
				html += "</ul>\n</div>\n</div>\n</div>\n";
				html += "<div class=\"col-md-3 panel2\">\n"
						+ "<div class=\"panel panel-default panel-primary\">\n"
						+ "<div class=\"panel-heading\">Selected Department</div>\n"
						+ "<div class=\"panel-body\"  id=\"selectedClient\">\n";
				System.out.println(selectedDepartment.size());
				for (Department dept : selectedDepartment) {
					html += "<p class=\"" + dept.getDepartmentID() + "\">"
							+ dept.getDepartmentName() + "</p>\n";
				}
				html += "</div>\n</div>\n</div>\n</div>\n</div>\n";
				break;
			case 4:
				html += "<label for=\"deadline\" path=\"reportpara.value\">Deadline</label>\n";
				html += "<input type=\"date\" class=\"form-control\" id=\"deadline\" value=\"2014-10-23\"/>\n";
				break;
			}
			count++;
		}

		html += "<h4>Output Type</h4>\n";

		for (Reportoutput reportoutput : outputs) {
			boolean flag = transactionOuputIDs.contains(reportoutput
					.getOutputid());
			html += "<label class=\"checkbox-inline\">\n";
			html += "<input id=\"_output\" name=\"output\" type=\"checkbox\" value=\""
					+ reportoutput.getOutputid() + "\"";
			if (flag) {
				html += "checked";
			}
			html += "/><input type=\"hidden\" name=\"_output\" value=\"on\"/>"
					+ reportoutput.getType() + "\n";
			html += "</label>\n";
		}
		html += "<div class=\"modal-footer\">\n";
		html += "<button type=\"button\" class=\"btn btn-default\" data-dismiss=\"modal\"> Close </button>"
				+ "<button type=\"submit\" class=\"submit btn btn-primary\" >Generate</button>"
				+ "</div>\n";
		html += "</div>\n";
		html += "</form:form>\n";
		html += "</div>";

		return html;

	}

	@RequestMapping(value = "/getReportType", method = RequestMethod.GET)
	@ResponseBody
	public String getReportType(
			@RequestParam("reportTypeName") String reportTypename,
			ModelAndView model) {
		List<Client> clientList = (reporthandleservice.getClientMap());
		Transaction transaction = new Transaction();
		model.addObject("command", transaction);
		Map<String, List<Reportpara>> reportToPara = reportdataservice
				.reportParaMap();
		Map<String, List<Reportoutput>> reportToOutput = reportdataservice
				.reportOutputMap();
		int reportID = reportdataservice.getReportID(reportTypename);
		List<Reportoutput> outputs = reportToOutput.get(reportTypename);
		List<Reportpara> reportpara = reportToPara.get(reportTypename);

		transaction.setPara((ArrayList<Reportpara>) reportpara);
		transaction.setReportid(reportID);
		String html = "";
		html += "<div class=\"modal-header\">\n";
		html += "<button type=\"button\" class=\"close\" data-dismiss=\"modal\" aria-hidden=\"true\">&times;</button>\n";
		html += "<h4 class=\"modal-title\" id=\"myModalLabel\">"
				+ reportTypename + "</h4>\n";
		html += "</div>\n" + "<div class=\"modal-body\">";

		html += "<form id=\"command\" role=\"form\" commandname=\"departments\" action=\"/report/generate\" method=\"POST\" onsubmit=\"return validate();\">\n";
		html += "<div class=\"form-group\">";
		html += "<input id=\"reportid\" name=\"reportid\"  type=\"hidden\" value=\"" + reportID + "\"/>\n";
		int count = 0;
		for (Reportpara para : reportpara) {
			int paraId = para.getId();
			switch (paraId) {
			case 1:
				html += "<input id=\"para" + count + ".id\" name=\"para["
						+ count + "].id\" value=\"" + (count + 1)
						+ "\" type=\"hidden\" value=\"" + (count + 1)
						+ "\"/>\n";
				html += "<label for=\"start_date\">Start Date</label>\n";
				html += "<input id=\"start_date\" name=\"para["
						+ count
						+ "].value\" class=\"form-control\" required=\"required\" type=\"date\" value=\"\"/>\n";
				break;
			case 2:
				html += "<input id=\"para" + count + ".id\" name=\"para["
						+ count + "].id\" value=\"" + (count + 1)
						+ "\" type=\"hidden\" value=\"" + (count + 1)
						+ "\"/>\n";
				html += "<label for=\"end_date\">End Date</label>\n";
				html += "<input id=\"end_date\" name=\"para["
						+ count
						+ "].value\" class=\"form-control\" required=\"required\" type=\"date\" value=\"\"/>\n";
				break;
			case 3:
				html += "<div class=\"container\">\n"
						+ "<div class=\"row\">\n"
						+ "<div class=\"col-md-3 panel1\">\n"
						+ "  <div class=\"panel panel-default panel-primary \">\n"
						+ "    <div class=\"panel-heading\">Select Client</div>\n"
						+ "<div class=\"panel-body checkboxes\">\n"
						+ " <ul class=\"mktree\" id=\"tree1\">\n";
				int departmentcount = 1;
				for (Client client : clientList) {
					html += "<li>" + "<p class=\"text-primary\">"
							+ client.getClientname() + "</p>\n";
					for (Department department : client.getDepartments()) {
						html += "<ul>\n<li>\n<label>\n"
								+ department.getDepartmentName() + "\n";
						html += "<input id=\"para"
								+ count
								+ ".value"
								+ departmentcount
								+ "\" name=\"para["
								+ count
								+ "].value\" name=\"dept\" type=\"checkbox\" value=\""
								+ department.getDepartmentID()
								+ "\" onchange=\"\" /><input type=\"hidden\" name=\"_para["
								+ count + "].value\" value=\"on\"/>\n";
						html += "<input id=\"para" + count
								+ ".id\" name=\"para[" + count
								+ "].id\" value=\"" + (count + 1)
								+ "\" type=\"hidden\" value=\"" + (count + 1)
								+ "\"/>\n";
						html += "</label>\n</li>\n</ul>\n";
						departmentcount++;
					}
					html += "</li>\n";
				}
				html += "</ul>\n</div>\n</div>\n</div>\n";
				html += "<div class=\"col-md-3 panel2\">\n"
						+ "<div class=\"panel panel-default panel-primary\">\n"
						+ "<div class=\"panel-heading\">Selected Department</div>\n"
						+ "<div class=\"panel-body\"  id=\"selectedClient\" >"
						+ "</div>\n</div>\n</div>\n</div>\n</div>\n";
				break;
			case 4:
				html += "<label for=\"deadline\" path=\"reportpara.value\">Deadline</label>\n";
				html += "<input type=\"date\" class=\"form-control\" id=\"deadline\"/>\n";
				break;
			}
			count++;
		}
		html += "<h4>Output Type</h4>\n";

		for (Reportoutput output : outputs) {
			html += "<label class=\"checkbox-inline\">\n";
			html += "<input id=\"_output\" name=\"output\" type=\"checkbox\" value=\""
					+ output.getOutputid()
					+ "\"/><input type=\"hidden\" name=\"_output\" value=\"on\"/>"
					+ output.getType() + "\n";
			html += "</label>\n";
		}

		html += "<div class=\"modal-footer\">\n";
		html += "<button type=\"button\" class=\"btn btn-default\" data-dismiss=\"modal\"> Close </button>"
				+ "<button type=\"submit\" class=\"submit btn btn-primary\" >Generate</button>"
				+ "</div>\n";
		html += "</div>\n";
		html += "</form:form>\n";
		html += "</div>";

		return html;
	}

	@RequestMapping(value = "/generate", method = RequestMethod.POST)
	public String generate(Transaction transaction, ModelMap model) {
		for (int i = 0; i < transaction.getPara().size(); i++) {
			System.out.println(transaction.getPara().get(i).getValue()
					.toString());

		}
		int userid = (Integer) model.get("userid");
		transaction.setUserid(userid);
		reportdataservice.create(transaction);
		int tid = reportdataservice.getMaxTid();
		Transaction t1 = reportdataservice.getTransaction(tid);
		model.addAttribute("transaction", t1);
		String genMethod = t1.getGenMethod();
		System.out.println("Report type " + t1.getName());
		System.out.println(genMethod);
		String output = "redirect:" + genMethod;
		return output;

	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	@ResponseBody
	public String delete(@RequestParam("transactionID") int transactionID,
			ModelMap model) {
		reportdataservice.delete(transactionID);
		return "success";
	}

}
