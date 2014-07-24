package com.dci.report.homepageModule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.dci.report.bean.Transaction;
import com.dci.report.impl.Genbillingdetail;
import com.dci.report.impl.Genbillingsummary;
import com.dci.report.services.Reportdataservice;

@Controller
@SessionAttributes(value = { "transaction", "path" })
public class GenController {

	@Autowired
	Reportdataservice reportdataservice;

	@Autowired
	Genbillingsummary genbillingsummary;

	@Autowired
	Genbillingdetail genbillingdetail;

	@RequestMapping(value = "/genbillingdeital", method = RequestMethod.GET)
	public String genbillingdetail(ModelMap model) {
		Transaction transaction = (Transaction) model.get("transaction");
		String path = genbillingdetail.generatereport(transaction);
		// System.out.println(transaction.getName());
		// System.out.println(transaction.getDate());
		// for (int i = 0; i < transaction.getArroutput().size(); i++) {
		// System.out.print(transaction.getArroutput().get(i).getStatus());
		// System.out.print(transaction.getArroutput().get(i).getType());
		// System.out.print(transaction.getArroutput().get(i).getFilename());
		// }
		// System.out.println(transaction.getId());
		// String html = "";
		// html += "<tr id=\"" + transaction.getId() + "\">\n";
		// html += "<td>" + transaction.getName() + "</td>\n";
		// html += "<td>" + transaction.getDate() + "</td>\n";
		// html += "<td>\n";
		// for (int i = 0; i < transaction.getArroutput().size(); i++) {
		// if (transaction.getArroutput().get(i).getStatus() == "In progress") {
		// html += "<span class=\"glyphicon glyphicon-refresh\"></span>\n";
		// } else if (transaction.getArroutput().get(i).getStatus() == "Fail") {
		// html += "<span class=\"glyphicon glyphicon-remove\"></span>\n";
		// } else {
		// html += "<span class=\"glyphicon glyphicon-ok\"></span>\n";
		// }
		// }
		// html += "</td>\n";
		// html += "<td>\n";
		// html += "<div class=\"btn-group\">\n";
		// for (int i = 0; i < transaction.getArroutput().size(); i++) {
		// html += "<a href=\"file:///"
		// + path
		// + transaction.getArroutput().get(i).getType()
		// + "/"
		// + transaction.getArroutput().get(i).getFilename()
		// + "."
		// + transaction.getArroutput().get(i).getType()
		// +
		// "\" class=\"btn btn-default btn-xs\" role=\"button\" target=\"_blank\"><span class=\"glyphicon glyphicon-search\"></span>"
		// + transaction.getArroutput().get(i).getType() + "</a>\n";
		// }
		// html += "</div>\n";
		// html += "</td>\n";
		// html += "<td>\n";
		// html += "<a  id=\"/report/regenerate?transactionID="
		// + transaction.getId()
		// +
		// "\" class=\"btn btn-default btn-xs regenerateLink\" role=\"button\">Generate</a>\n";
		// html += "</td>\n";
		// html += "<td>\n";
		// html +=
		// "<a  data-target=\"#delete-dialog\" class=\"btn btn-danger btn-xs deletedialog\" role=\"button\" data-toggle=\"modal\" id=\"/report/delete?transactionID="
		// + transaction.getId()
		// + "\" name =\""
		// + transaction.getId()
		// + "\"  >delete</a>";
		// html += "</td>\n";
		// html += "</tr>";
		// System.out.println(html);
		return "redirect: homepage";
	}

	@RequestMapping(value = "/genbillingsummary", method = RequestMethod.GET)
	public String genbillingsummary(ModelMap model) {
		Transaction transaction = (Transaction) model.get("transaction");
		String path = genbillingsummary.generatereport(transaction);
		// String html = "";
		// html += "<tr id=\"" + transaction.getId() + "\">\n";
		// html += "<td>" + transaction.getName() + "</td>\n";
		// html += "<td>" + transaction.getDate() + "</td>\n";
		// html += "<td>\n";
		// for (int i = 0; i < transaction.getArroutput().size(); i++) {
		// if (transaction.getArroutput().get(i).getStatus() == "In progress") {
		// html += "<span class=\"glyphicon glyphicon-refresh\"></span>\n";
		// } else if (transaction.getArroutput().get(i).getStatus() == "Fail") {
		// html += "<span class=\"glyphicon glyphicon-remove\"></span>\n";
		// } else {
		// html += "<span class=\"glyphicon glyphicon-ok\"></span>\n";
		// }
		// }
		// html += "</td>\n";
		// html += "<td>\n";
		// html += "<div class=\"btn-group\">\n";
		// for (int i = 0; i < transaction.getArroutput().size(); i++) {
		// html += "<a href=\"file:///"
		// + path
		// + transaction.getArroutput().get(i).getType()
		// + "/"
		// + transaction.getArroutput().get(i).getFilename()
		// + "."
		// + transaction.getArroutput().get(i).getType()
		// +
		// "\" class=\"btn btn-default btn-xs\" role=\"button\" target=\"_blank\"><span class=\"glyphicon glyphicon-search\"></span>"
		// + transaction.getArroutput().get(i).getType() + "</a>\n";
		// }
		// html += "</div>\n";
		// html += "</td>\n";
		// html += "<td>\n";
		// html += "<a  id=\"/report/regenerate?transactionID="
		// + transaction.getId()
		// +
		// "\" class=\"btn btn-default btn-xs regenerateLink\" role=\"button\">Generate</a>\n";
		// html += "</td>\n";
		// html += "<td>\n";
		// html +=
		// "<a  data-target=\"#delete-dialog\" class=\"btn btn-danger btn-xs deletedialog\" role=\"button\" data-toggle=\"modal\" id=\"/report/delete?transactionID="
		// + transaction.getId()
		// + "\" name =\""
		// + transaction.getId()
		// + "\"  >delete</a>";
		// html += "</td>\n";
		// html += "</tr>";
		// System.out.println(html);
		return "redirect: homepage";
	}
}
