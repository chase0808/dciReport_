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

@Controller
@SessionAttributes(value = { "transaction", "path" })
public class GenController {

	@Autowired
	Genbillingsummary genbillingsummary;

	@Autowired
	Genbillingdetail genbillingdetail;

	@RequestMapping(value = "/genbillingdeital", method = RequestMethod.GET)
	public String genbillingdetail(ModelMap model) {
		Transaction transaction = (Transaction) model.get("transaction");
		String path = genbillingdetail.generatereport(transaction);
		return "redirect:homepage";
	}

	@RequestMapping(value = "/genbillingsummary", method = RequestMethod.GET)
	public String genbillingsummary(ModelMap model) {
		Transaction transaction = (Transaction) model.get("transaction");
		String path = genbillingsummary.generatereport(transaction);
		return "redirect:homepage";
	}
}
