package com.dci.report;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {

	private static final Logger logger = LoggerFactory
			.getLogger(HomeController.class);

	/**
	 * Simply selects the home view to render by returning its name.
	 * 
	 * @throws SQLException
	 * @throws JRException
	 */
	/*
	 * @RequestMapping(value = "/", method = RequestMethod.GET) public String
	 * home(Locale locale, Model model) {
	 * logger.info("Welcome home! The client locale is {}.", locale);
	 * 
	 * Date date = new Date(); DateFormat dateFormat =
	 * DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
	 * 
	 * String formattedDate = dateFormat.format(date);
	 * 
	 * model.addAttribute("serverTime", formattedDate );
	 * 
	 * return "home"; }
	 */

	@RequestMapping(value = "/setTitle", method = RequestMethod.GET)
	public String setForm() {

		logger.debug("In Set For method");

		return "setTitle";

	}

	@RequestMapping(value = "/setTitle", method = RequestMethod.POST)
	public String setParameter(@RequestParam("reportTitle") String title,
			@RequestParam("reportAuthor") String author) throws SQLException,
			JRException {
		logger.error(title);
		logger.error(author);
		new com.mysql.jdbc.Driver();
		java.sql.Connection c = DriverManager.getConnection(
				"jdbc:mysql://localhost:3306/test", "root", "qixin808");
		Map parameters = new HashMap();
		parameters.put("ReportTitle", title);
		parameters.put("Author", author);
		JasperPrint jasperPrint = JasperFillManager
				.fillReport(
						"C:\\Users\\xqi\\eclipesworkspace\\jasperSpring\\Blank_A4.jasper",
						parameters, c);
		JasperExportManager.exportReportToPdfFile(jasperPrint,
				"C:\\Users\\xqi\\eclipesworkspace\\jasperSpring\\sample.pdf");
		JasperExportManager.exportReportToHtmlFile(jasperPrint, "sample.html");
		// try {
		// jasperPrint = JasperFillManager.fillReport("Blank_A4.jasper",
		// parameters, c);
		// JasperExportManager
		// .exportReportToPdfFile(jasperPrint, "sample.pdf");
		// JasperExportManager.exportReportToHtmlFile(jasperPrint,
		// "/views/sample.html");
		// } catch (JRException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

		logger.error("In setParameter method");
		return "sample";
	}
}
