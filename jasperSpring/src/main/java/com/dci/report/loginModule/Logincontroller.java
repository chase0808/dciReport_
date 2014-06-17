package com.dci.report.loginModule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class Logincontroller {
	
	@Autowired
	LoginService loginservice;
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView loginPage(Model model) {
		return new ModelAndView("login", "command", new User());
	}
	
	@RequestMapping(value = "/logining", method = RequestMethod.POST)
	public String login(@ModelAttribute("SpringWeb")User user, ModelMap model) {
		
		System.out.println(user.getPassword());
		String returnVal = loginservice.validate(user);
		model.addAttribute("returnvalue", returnVal);
		
		if( returnVal.equals("welcome") ) {
			return "successview";
		}
		else
			return "home";
	}
}
