package com.project.dnflol.Controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.project.dnflol.Security.CustomUserDetailsService;

@RequestMapping("/security")
@Controller
public class SecurityController {
	
	@Autowired
	private CustomUserDetailsService mServ;
	
	@RequestMapping("/login")
	public ModelAndView loginPage(HttpSession session) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/security/login");
		return mv;
	}
	
	@RequestMapping("/loginProcess")
	public ModelAndView loginProcess(HttpSession session) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/");
		return mv;
	}
	
	@RequestMapping("/logout")
	public ModelAndView logout() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/");
		return mv;
	}
	
	@RequestMapping("/denied")
	public ModelAndView deniedPage(HttpSession session) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/security/denied");
		return mv;
	}
}
