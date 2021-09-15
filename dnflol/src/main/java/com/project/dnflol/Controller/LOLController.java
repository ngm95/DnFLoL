package com.project.dnflol.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/lol")
public class LOLController {
	
	@RequestMapping("/mainPage")
	public ModelAndView lolMainPage() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/lol/mainPage");
		return mv;
	}
	
	@RequestMapping("/board")
	public ModelAndView lolBoard() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/lol/board");
		return mv;
	}
	
	@RequestMapping("/find")
	public ModelAndView lolFind() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/lol/find");
		return mv;
	}
}
