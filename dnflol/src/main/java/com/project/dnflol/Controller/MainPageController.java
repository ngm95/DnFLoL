package com.project.dnflol.Controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
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
	
	@ModelAttribute("authInfo")
	public AuthInfo authInfo(Authentication auth) {
		if (auth == null)
			return null;
		else {
			UserDTO user = uServ.readById(auth.getName());
			return new AuthInfo(user.getUid(), user.getUname());
		}
	}
	
	@RequestMapping("/")
	public ModelAndView mainPage() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/mainPage");
		return mv;
	}
	
	@RequestMapping("user/myPage")
	public ModelAndView myPage(Model model) {
		ModelAndView mv = new ModelAndView();
		AuthInfo auth = (AuthInfo)model.getAttribute("authInfo");
		List<LCharDTO> mylolChars = lcServ.readAllByUid(auth.getUid());	// LoL 연동 계정 정보
		model.addAttribute("mylolChars", mylolChars);
		List<LGroupDTO> mylolGroups = lgServ.readAllByUId(auth.getUid()); // LoL에서 내가 작성한 글 정보 
		model.addAttribute("mylolGroups", mylolGroups);
		
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
