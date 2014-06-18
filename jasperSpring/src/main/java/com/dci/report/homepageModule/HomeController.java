package com.dci.report.homepageModule;

import java.util.ArrayList;

import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

public class HomeController {

	@RequestMapping(value = "/homepage", method = RequestMethod.GET)
	public ModelAndView homepage(Model model) {
		return new ModelAndView("homepage","command", new Getinput());
	}
	
	@RequestMapping(value = "/processdata", method = RequestMethod.POST)
	public String process(Getinput input, ModelMap model) {
		ArrayList<Integer> test = input.getPara();
		System.out.print(test);
		return "successview";
	}
}
