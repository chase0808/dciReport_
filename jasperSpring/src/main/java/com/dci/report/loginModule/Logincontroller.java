package com.dci.report.loginModule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

@Controller
@SessionAttributes(value = { "transaction", "userid", "username" })
public class Logincontroller {

	@Autowired
	LoginService loginservice;

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView loginPage(
			@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout,
			Model model) {
		ModelAndView modelandview = new ModelAndView("loginpage", "command",
				new User());
		if (error != null) {
			modelandview.addObject("error", "Invalid user name or password!");
		}

		if (logout != null) {
			modelandview.addObject("logout",
					"You have been successfully logged out!");
		}
		return modelandview;
	}

	@RequestMapping(value = "/logining", method = RequestMethod.POST)
	public String login(@ModelAttribute("SpringWeb") User user, ModelMap model) {

		int userid = loginservice.validate(user);

		if (userid != 0) {
			model.addAttribute("userid", userid);
			String username = user.getUsername();
			model.addAttribute("username", username);
			return "redirect:homepage";
		} else {
			return "redirect:login?error=invalidate";
		}

	}
}
