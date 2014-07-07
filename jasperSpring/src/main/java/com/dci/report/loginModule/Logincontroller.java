package com.dci.report.loginModule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

@Controller
@SessionAttributes(value = { "transaction", "userid" })
public class Logincontroller {

	@Autowired
	LoginService loginservice;

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView loginPage(Model model) {

		return new ModelAndView("loginpage", "command", new User());
	}

	@RequestMapping(value = "/logining", method = RequestMethod.POST)
	public String login(@ModelAttribute("SpringWeb") User user, ModelMap model) {

		int userid = loginservice.validate(user);
		
		if (userid != 0) {
			model.addAttribute("userid", userid);
			return "redirect:test";
		} else
			return "redirect:login";
	}
}
