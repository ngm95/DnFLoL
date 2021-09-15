package com.project.dnflol.Controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.project.dnflol.DTO.UserDTO;
import com.project.dnflol.Service.UserService;
import com.project.dnflol.util.AuthInfo;

@RestController
public class MainPageController {
	@Autowired
	private UserService uServ;
	
	@RequestMapping("/")
	public ModelAndView mainPage(HttpSession session, Authentication auth) {
		ModelAndView mv = new ModelAndView();
		/*
		 * 만약 로그인되어 있는데 로그인 정보가 세션에 존재하지 않는다면
		 * 아이디와 이름을 뽑아서 세션에 저장해 둔다.
		 */
		if (auth != null && session.getAttribute("authInfo") == null) {				
			UserDTO dto = uServ.readById(auth.getName());
			session.setAttribute("authInfo", new AuthInfo(dto.getUid(), dto.getUname()));
		}
		mv.setViewName("/mainPage");
		return mv;
	}
	
	@RequestMapping("/myPage")
	public ModelAndView myPage() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/myPage");
		return mv;
	}
}
