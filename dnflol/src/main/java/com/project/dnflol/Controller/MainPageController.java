package com.project.dnflol.Controller;


import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.project.dnflol.DTO.DApplyDTO;
import com.project.dnflol.DTO.DCharDTO;
import com.project.dnflol.DTO.LApplyDTO;
import com.project.dnflol.DTO.LCharDTO;
import com.project.dnflol.DTO.UserDTO;
import com.project.dnflol.Service.DApplyService;
import com.project.dnflol.Service.DCharService;
import com.project.dnflol.Service.LApplyService;
import com.project.dnflol.Service.LCharService;
import com.project.dnflol.Service.UserService;
import com.project.dnflol.util.AuthInfo;

@Controller
public class MainPageController {
	@Autowired
	private UserService uServ;
	
	@Autowired
	private LCharService lcServ;
	
	@Autowired
	private LApplyService laServ;
	
	@Autowired
	private DCharService dcServ;
	
	@Autowired
	private DApplyService daServ;
	
	@ModelAttribute("authInfo")
	public AuthInfo authInfo(Authentication auth) {
		if (auth == null)
			return null;
		else {
			UserDTO user = uServ.readById(auth.getName());
			return new AuthInfo(user.getUid(), user.getUname());
		}
	}
	//test
	@ModelAttribute("applyForm")
	public LApplyDTO applyForm() {
		return new LApplyDTO();
	}
	
	@RequestMapping("/")
	public ModelAndView mainPage(HttpSession session) {
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
		
		List<DCharDTO> mydnfChars = dcServ.readAllByUid(auth.getUid());	// LoL 연동 계정 정보
		model.addAttribute("mydnfChars", mydnfChars);
		
		mv.setViewName("/user/myPage");
		return mv;
	} 
	
	@RequestMapping("/myLoLNotice")
	@ResponseBody
	public String myLoLNotice(Model model) {
		String json = null;
		Gson gson = new Gson();
		AuthInfo authInfo = (AuthInfo)model.getAttribute("authInfo");
		
		List<LApplyDTO> applyList = null;
		if (authInfo != null) 
			applyList = laServ.readAllMyApply(authInfo.getUid());
			
		json = gson.toJson(applyList);
		return json;
	}
	
	@RequestMapping("/myDnFNotice")
	@ResponseBody
	public String myDnFNotice(Model model) {
		String json = null;
		Gson gson = new Gson();
		AuthInfo authInfo = (AuthInfo)model.getAttribute("authInfo");
		
		List<DApplyDTO> applyList = null;
		if (authInfo != null) 
			applyList = daServ.readAllMyApply(authInfo.getUid());
			
		json = gson.toJson(applyList);
		return json;
	}
}
