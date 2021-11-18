package com.project.dnflol.Controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.project.dnflol.Exception.AlreadyExistedUIdException;
import com.project.dnflol.Exception.AlreadyExistedUNameException;
import com.project.dnflol.Service.UserService;
import com.project.dnflol.util.RegisterRequest;

@Controller
@RequestMapping("/register")
public class RegisterController {
	@Autowired
	private UserService uServ;
	
	/**
	 * step1으로 이동
	 * step1에서는 약관을 보여주고 동의를 받음
	 */
	@RequestMapping("/step1")
	public String registerStep1() throws Exception {
		return "/register/step1";
	}
	
	/**
	 * step1에서 확인을 눌렀는지 여부를 체크하는 단계
	 * 확인을 눌렀으면 step3으로 넘어간다.
	 * @param agree step1에서 확인 버튼을 눌렀는지 여부
	 */
	@RequestMapping("/step2")
	public String registerStep2(@RequestParam(value="agree", defaultValue="false") boolean agree, Model model) throws Exception {
		if (!agree) {							// 확인 버튼이 눌리지 않았으면 step1으로 되돌아감
			return "/register/step1";
		}
		else {									// 확인 버튼이 눌렸으면 RegisterRequest 객체를 추가해 step2로 넘어감
			model.addAttribute("request", new RegisterRequest());
			return "/register/step2";
		}
	}
	
	@RequestMapping(value="/step3", method=RequestMethod.POST)
	public String registerStep3(@Valid @ModelAttribute("request") RegisterRequest regReq, BindingResult br, HttpServletRequest request, RedirectAttributes rdAttributes) throws Exception {
		
		if (br.hasErrors()) {					// 필요한 정보가 정한 폼에 맞지 않으면 이전 단계로 돌아감
			rdAttributes.addFlashAttribute("notice", new RuntimeException("정해진 형식에 맞지 않습니다."));
			return "redirect:" + request.getHeader("Referer");
		}
		
		if (!regReq.isPwEqualtoCheckPw()) {		// 입력한 비밀번호와 그 확인이 정확하지 않으면 이전 단계로 돌아감
			br.rejectValue("checkPw", "noMatch", "패스워드가 일치하지 않습니다.");
			return "/register/step2";
		}
		
		/*
		 * 회원가입을 시도해 이메일/닉네임/아이디가 이미 존재했다면 이전 단계로 돌아감
		 */
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		regReq.setPw(encoder.encode(regReq.getPw()));						// 비밀번호를 암호화한다.
		try {
			uServ.register(regReq);
		} catch(AlreadyExistedUNameException aeune) {
			br.rejectValue("uname", "duplicate", "이미 존재하는 닉네임입니다.");
			return "/register/step2";
		} catch(AlreadyExistedUIdException aeuie) {
			br.rejectValue("uid", "duplicate", "이미 존재하는 아이디입니다.");
			return "/register/step2";
		}
		
		return "redirect:/";			// 정상적으로 회원가입이 완료되면 메인페이지로 돌아간다.
	}
	
	@RequestMapping("/unregister")
	public String unregister(HttpServletRequest servletRequest, Authentication auth) {
		servletRequest.getSession().invalidate();								// 세션 정보 제거
		servletRequest.getSession(true);										// 새로운 세션 ID 발급
		try {
			servletRequest.logout();											// 현재 사용자 로그아웃
		} catch(ServletException se) {
			;
		}
		return "redirect:/";
	}
}
