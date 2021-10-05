package com.project.dnflol.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@RequestMapping("/security")
@Controller
public class SecurityController {
	
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public ModelAndView loginPage() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/security/loginForm");
		return mv;
	}
	
	@RequestMapping("/denied")
	public ModelAndView deniedPage() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/security/deniedPage");
		return mv;
	}
}
