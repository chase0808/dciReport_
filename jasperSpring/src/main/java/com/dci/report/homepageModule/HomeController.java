package com.dci.report.homepageModule;

import java.util.ArrayList;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

	@RequestMapping(value = "/homepage", method = RequestMethod.GET)
	public ModelAndView homepage(Model model) {
		return new ModelAndView("homepage","command", new Getinput());
	}
	
	@RequestMapping(value = "/processdata", method = RequestMethod.POST)
	public String process(Getinput input, ModelMap model) {
		ArrayList<Integer> test = input.getPara();
		System.out.println(test.size());
		for( int i = 0; i < test.size(); i++ )
			System.out.print(test.get(i));
		return "successview";
	}
}
