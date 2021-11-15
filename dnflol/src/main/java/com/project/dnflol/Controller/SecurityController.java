package com.project.dnflol.Controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/security")
@Controller
public class SecurityController {
	
	@GetMapping(value="/login")
	public String loginPage(Authentication auth) {
		if (auth != null)
			return "redirect:/";
		return "/security/loginForm";
	}
	
	@RequestMapping("/denied")
	public String deniedPage() {
		return "/security/deniedPage";
	}
}
