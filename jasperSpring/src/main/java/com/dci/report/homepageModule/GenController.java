package com.dci.report.homepageModule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.dci.report.bean.Transaction;
import com.dci.report.impl.Genbillingsummary;

@Controller
@SessionAttributes(value = { "transaction" })
public class GenController {

	@Autowired
	Genbillingsummary genbillingsummary;

	@RequestMapping(value = "/genbillingdeital", method = RequestMethod.GET)
	public String genbillingdetail(ModelMap model) {
		Transaction transaction2 = (Transaction) model.get("transaction");
		System.out.println("I am in the regenerate!");
		System.out.println(transaction2.getGenMethod());
		System.out.println(transaction2.getDate().toString());
		System.out.println(transaction2.getId());
		System.out.println(transaction2.getPara().get(2).getValue().get(0));
		System.out.println(transaction2.getPara().get(2).getValue().get(1));
		System.out.println(transaction2.getArroutput().get(0).getFilename());
		System.out.println("Successful!");
		// reporthandleservice.generatereport(report);
		return "successview";
	}

	@RequestMapping(value = "/genbillingsummary", method = RequestMethod.GET)
	public String genbillingsummary(ModelMap model) {
		Transaction transaction = (Transaction) model.get("transaction");
		System.out.println(transaction.getPara().get(0).getValue().toString());
		System.out.println(transaction.getId());
		genbillingsummary.generatereport(transaction);
		// reporthandleservice.generatereport(report);
		return "dashboard";
	}
}
