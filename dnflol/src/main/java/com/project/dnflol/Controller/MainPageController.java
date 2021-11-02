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



import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.project.dnflol.DTO.DAdventureDTO;
import com.project.dnflol.DTO.TimeLineDTO;

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
	ObjectMapper objectMapper = new ObjectMapper();
	@GetMapping("/dnf")
	public ModelAndView lolFindSummoner(HttpSession session) {
		String requestURL = "https://api.neople.co.kr/df/servers/siroco/characters/fb2eabeab87124585a54a48356cc02c3/timeline?limit=100&code=201,507&apikey=VdItQKfKUZWekekAag0O6i7vxRhE9TMs";
		
		ModelAndView mv = new ModelAndView();
		DAdventureDTO summonerDto = null;
		try {
			HttpClient client = HttpClientBuilder.create().build();
			System.out.print(1);
			HttpGet getRequest = new HttpGet(requestURL);
			System.out.print(2);
			HttpResponse response = client.execute(getRequest);
			System.out.print(3);
			/*
			 * 기본적인 계정 정보를 받아옴
			 */
			if (response.getStatusLine().getStatusCode() == 200) {
				ResponseHandler<String> handler = new BasicResponseHandler();
				System.out.print(4);
				String body = handler.handleResponse(response);
				//body = body.substring(9,body.length());
				//body.indexOf
				
				body = body.substring(body.indexOf("rows")+6,body.length()-2);
				System.out.print(body);
				objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
				List<TimeLineDTO> timeline = objectMapper.readValue(body, objectMapper.getTypeFactory().constructCollectionType(List.class, TimeLineDTO.class));
				//summonerDto = objectMapper.readValue(body, DAdventureDTO.class);	// json을 SummonerDTO로 바꿈
				System.out.print("5");
				
				for (TimeLineDTO ele : timeline) {
					 ele.getData();
				}
				System.out.print("6");
				session.setAttribute("result", timeline);
				//LCharDTO lcharDto = new LCharDTO(((AuthInfo)session.getAttribute("authInfo")).getUid(), summonerDto.getName());
				//lcServ.create(lcharDto);										// 아이디를 계정에 연동
			}
		} catch(Exception e) {
			session.setAttribute("linked", false);
			mv.setViewName("redirect:/secrity/denied");
			return mv;
		}
		
		session.setAttribute("linked", true);
		mv.setViewName("/dnf/test");					// 이전 상태로 되돌아감
		return mv;
	}
}
