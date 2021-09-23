package com.project.dnflol.Controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.project.dnflol.DTO.LCharDTO;
import com.project.dnflol.DTO.LGroupDTO;
import com.project.dnflol.DTO.UserDTO;
import com.project.dnflol.Service.LCharService;
import com.project.dnflol.Service.LGroupService;
import com.project.dnflol.Service.UserService;
import com.project.dnflol.util.AuthInfo;

@RestController
public class MainPageController {
	@Autowired
	private UserService uServ;
	
	@Autowired
	private LCharService lcServ;
	
	@Autowired
	private LGroupService lgServ;
	
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
	public ModelAndView myPage(HttpSession session) {
		ModelAndView mv = new ModelAndView();
		List<LCharDTO> myChars = lcServ.readAllByUid(((UserDTO)session.getAttribute("authInfo")).getUid());	// LoL 연동 계정 정보
		session.setAttribute("myChars", myChars);
		List<LGroupDTO> myGroups = lgServ.readAllByUId(((UserDTO)session.getAttribute("authInfo")).getUid()); // LoL에서 내가 작성한 글 정보 
		session.setAttribute("myGroups", myGroups);
		
		/*
		 * DnF 연동 계정 정보와 작성한 글 정보를 session에 담는 작업 필요
		 */
		
		mv.setViewName("/myPage");
		return mv;
	} 
}
