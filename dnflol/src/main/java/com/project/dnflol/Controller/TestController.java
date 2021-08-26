package com.project.dnflol.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class TestController {
	@RequestMapping("test")
	public String test() {
		return "테스트 = test";
	}
	@RequestMapping("/")
	public ModelAndView vue() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("index");
		return mv;
	}
}
