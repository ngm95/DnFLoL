package com.project.dnflol.Controller;


import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.dnflol.DTO.LMatchDTO;
import com.project.dnflol.DTO.SummonerDTO;
import com.project.dnflol.util.APIKey;

@Controller
@RequestMapping("/lol")
public class LOLController {
	
	@Autowired
	APIKey api;
	
	ObjectMapper objectMapper = new ObjectMapper();		// json 형태로 반환되는 response를 DTO형태로 바꿔주는 jackson 라이브러리를 사용하기 위한 객체
	
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
	
	/**
	 * 라이엇 API에서 제시한 URL Form에 맞게 실행해서 response로 받은 json데이터를 DTO로 변환해 저장한다.
	 * @param summonerName 찾고자 하는 소환사명
	 */
	@GetMapping("/findSummoner/{summonerName}")
	public ModelAndView lolFindSummoner(HttpSession session, @PathVariable("summonerName") String summonerName) {
		String requestURL = "https://kr.api.riotgames.com/lol/summoner/v4/summoners/by-name/" + summonerName + "?api_key=" + api.getLOL_API_KEY();
		
		SummonerDTO summonerDto = null;
		ModelAndView mv = new ModelAndView();
		try {
			HttpClient client = HttpClientBuilder.create().build();
			HttpGet getRequest = new HttpGet(requestURL);
			HttpResponse response = client.execute(getRequest);
			
			if (response.getStatusLine().getStatusCode() == 200) {
				ResponseHandler<String> handler = new BasicResponseHandler();
				String body = handler.handleResponse(response);
				summonerDto = objectMapper.readValue(body, SummonerDTO.class);	// json을 SummonerDTO로 바꿈
			}
			
		} catch(Exception e) {
			
		}
		
		session.setAttribute("SummonerDTO", summonerDto);		// 세션에 SummonerDTO를 넣음
		mv.setViewName("/lol/board");							// 이전 상태로 되돌아감 : 수정 필요
		return mv;
	}
	
	@GetMapping("/match/{encryptedAccountId}")
	public ModelAndView lolMatch(HttpSession session, @PathVariable("encryptedAccountId") String encryptedAccountId) {
		SummonerDTO summonerDto = (SummonerDTO)session.getAttribute("SummonerDTO");
		String requestURL = "https://kr.api.riotgames.com/lol/match/v4/matchlists/by-account/" + summonerDto.getAccountId() + "?api_key=" + api.getLOL_API_KEY();
		
		List<LMatchDTO> matchList = null;
		ModelAndView mv = new ModelAndView();
		try {
			HttpClient client = HttpClientBuilder.create().build();
			HttpGet getRequest = new HttpGet(requestURL);
			HttpResponse response = client.execute(getRequest);
			
			if (response.getStatusLine().getStatusCode() == 200) {
				ResponseHandler<String> handler = new BasicResponseHandler();
				String body = handler.handleResponse(response);
				matchList = objectMapper.readValue(body, objectMapper.getTypeFactory().constructCollectionType(List.class, LMatchDTO.class));
			}
		} catch(Exception e) {
			
		}
		
		session.setAttribute("matchList", matchList);
		mv.setViewName("/lol/board");							// 이전 상태로 되돌아감 : 수정 필요
		return mv;
	}
}
