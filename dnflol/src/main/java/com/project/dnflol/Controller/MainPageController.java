package com.project.dnflol.Controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.project.dnflol.DTO.LCharDTO;
import com.project.dnflol.DTO.LGroupDTO;
import com.project.dnflol.DTO.UserDTO;
import com.project.dnflol.Service.LCharService;
import com.project.dnflol.Service.LGroupService;
import com.project.dnflol.Service.UserService;
import com.project.dnflol.util.AuthInfo;

@Controller
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
		 * 로그인 정보 저장
		 */
		if (auth != null && session.getAttribute("authInfo") == null) {				
			UserDTO dto = uServ.readById(auth.getName());
			session.setAttribute("authInfo", new AuthInfo(dto.getUid(), dto.getUname()));
		}
		mv.setViewName("/mainPage");
		return mv;
	}
	
	@RequestMapping("user/myPage")
	public ModelAndView myPage(HttpSession session) {
		ModelAndView mv = new ModelAndView();
		AuthInfo auth = (AuthInfo)session.getAttribute("authInfo");
		List<LCharDTO> mylolChars = lcServ.readAllByUid(auth.getUid());	// LoL 연동 계정 정보
		session.setAttribute("mylolChars", mylolChars);
		List<LGroupDTO> mylolGroups = lgServ.readAllByUId(auth.getUid()); // LoL에서 내가 작성한 글 정보 
		session.setAttribute("mylolGroups", mylolGroups);
		
		/*
		 * DnF 연동 계정 정보와 작성한 글 정보를 session에 담는 작업 필요
		 */
		
		mv.setViewName("/user/myPage");
		return mv;
	} 
	
	@RequestMapping("user/myNotice")
	public ModelAndView myNotice() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/user/myNotice");
		return mv;
	}
}
