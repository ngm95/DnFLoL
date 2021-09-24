package com.project.dnflol.Controller;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.dnflol.DTO.LGroupDTO;
import com.project.dnflol.DTO.LMatchDTO;
import com.project.dnflol.DTO.LeagueDTO;
import com.project.dnflol.DTO.SummonerDTO;
import com.project.dnflol.Service.LGroupService;
import com.project.dnflol.util.APIKey;
import com.project.dnflol.util.BoardMinMax;
import com.project.dnflol.util.LBoardCheckBox;

@Controller
@RequestMapping("/lol")
public class LOLController {
	@Autowired
	LGroupService lgServ;
	
	APIKey api = new APIKey();
	BoardMinMax bmm = new BoardMinMax(lgServ.readMaxCount());	// 게시판에 노출되는 글을 컨트롤할 객체
	ObjectMapper objectMapper = new ObjectMapper();				// JSON 형태로 반환되는 response를 DTO형태로 바꿔주는 Jackson 라이브러리를 사용하기 위한 객체
	
	/**
	 * 메인 게시판 페이지
	 * - 글을 최근 순서로 10개 단위 10페이지를 게시하고 이전/다음 버튼으로 다른 범위에 있는 글을 가져올 수 있음.
	 * - 글을 일일히 찾기 싫은 사람을 위해 페이지 하단에는 게시판 검색을 위한 검색종류 선택 체크박스, 입력 폼, 검색버튼이 존재함.
	 *   검색 버튼을 누르면 입력 폼에 입력된 정보와 체크박스에서 선택된 정보를 가지고 비슷한 글을 찾아서 그 글들을 보여줌.
	 */
	@RequestMapping("/board")
	public ModelAndView lolBoard(HttpSession session) {
		ModelAndView mv = new ModelAndView();
		List<LGroupDTO> lgroupList = lgServ.readLimitList(bmm);
		if (session.getAttribute("lgroupList") == null) {			// 검색한 리스트가 없다면 새 리스트 생성
			session.setAttribute("lgroupList", lgroupList);
			session.setAttribute("isPrevValid", bmm.isPrevValid());
			session.setAttribute("isNextValid", bmm.isNextValid());
		}
		mv.setViewName("/lol/board");
		return mv;
	}
	
	/**
	 * 게시판 페이지에서 다음 버튼을 눌렀을 때 이벤트 처리
	 * - bmm을 이용해 새로 가져올 글의 범위와 prev, next 버튼의 활성화 여부를 계산해서 세션에 넣는다.
	 *   이후 게시판 페이지로 리다이렉트한다.
	 */
	@RequestMapping("/board/next")
	public ModelAndView lolNextBoard(HttpSession session) {
		ModelAndView mv = new ModelAndView();
		bmm.next();
		List<LGroupDTO> lgroupList = lgServ.readLimitList(bmm);
		session.setAttribute("lgroupList", lgroupList);
		session.setAttribute("isPrevValid", bmm.isPrevValid());
		session.setAttribute("isNextValid", bmm.isNextValid());
		mv.setViewName("redirect:/lol/board");
		return mv;
	}
	
	/**
	 * 게시판 페이지에서 이전 버튼을 눌렀을 때 이벤트 처리
	 * - bmm을 이용해 새로 가져올 글의 범위와 prev, next 버튼의 활성화 여부를 계산해서 세션에 넣는다.
	 *   이후 게시판 페이지로 리다이렉트한다.
	 */
	@RequestMapping("/board/prev")
	public ModelAndView lolPrevBoard(HttpSession session) {
		ModelAndView mv = new ModelAndView();
		bmm.prev();
		List<LGroupDTO> lgroupList = lgServ.readLimitList(bmm);
		session.setAttribute("lgroupList", lgroupList);
		session.setAttribute("isPrevValid", bmm.isPrevValid());
		session.setAttribute("isNextValid", bmm.isNextValid());
		mv.setViewName("redirect:/lol/board");
		return mv;
	}
	
	/**
	 * 게시판 페이지에서 찾기 버튼을 눌렀을 때 이벤트 처리
	 * - 설명, 그룹이름, 생성자이름중 하나를 선택해서 검색할 수 있으며 prev, next는 비활성화된다.
	 *   이후 게시판 페이지로 리다이렉트한다.
	 */
	@GetMapping("/board/find/{findDetail}")
	public ModelAndView lolFindBoard(HttpSession session, @PathVariable(value="findDetail") String findDetail, @RequestBody LBoardCheckBox lbcb) {
		ModelAndView mv = new ModelAndView();
		List<LGroupDTO> lgroupList;
		if (lbcb.getPosition().equals("detail"))
			lgroupList = lgServ.readAllByDetail(findDetail);
		else if (lbcb.getPosition().equals("groupName"))
			lgroupList = lgServ.readAllByGroupName(findDetail);
		else
			lgroupList = lgServ.readAllByOwnerName(findDetail);
		session.setAttribute("lgroupList", lgroupList);
		session.setAttribute("isPrevValid", false);
		session.setAttribute("isNextValid", false);
		mv.setViewName("redirect:/lol/board");
		return mv;
	}
	
	/**
	 * 그룹의 세부 정보를 보여주는 페이지
	 * - 그룹 생성자와 현재까지의 멤버 목록을 보여줌
	 * - 그룹 멤버를 클릭하면 그 멤버의 세부 전적/티어를 볼 수 있는 페이지로 이동
	 * - 페이지 하단에는 메인 게시판으로 되돌아가는 버튼과 신청 페이지로 이동할 수 있는 버튼이 존재
	 */
	@GetMapping("/board/{lgroupId}")
	public ModelAndView lolGroupBoard(HttpSession session, @PathVariable(value="lgroupId") int lgroupId) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/lol/board/detail");
		return mv;
	}
	
	/**
	 * 그룹에 신청할 계정을 찾는 페이지
	 * - 현재 아이디와 연동되어 있는 계정 목록이 나타남
	 * - 하단에는 다른 계정을 연동할 수 있는 입력 폼과 버튼이 존재
	 */
	@RequestMapping("lol/findSummoner")
	public ModelAndView lolFindSummoner(HttpSession session) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/lol/findSummoner");
		return mv;
	}
	
	/**
	 * 그룹 신청 페이지에서 아이디를 클릭하면 신청을 넣고 게시판 페이지로 돌아감
	 */
	@RequestMapping("/lol/summit/{lgroupId}?{summonerName}")
	public ModelAndView lolSummitToGroup(HttpSession session) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("redirect:/lol/board");
		return mv;
	}
	
	/**
	 * 라이엇 API에서 제시한 URL Form에 맞게 실행해서 계정을 찾아 아이디와 연동한다.
	 * 연동이 정상적으로 완료되었으면 해당 그룹에 신청을 넣는다. == 미구현
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
	
	/**
	 * 계정 전적 정보를 볼 수 있는 페이지
	 * - 계정 전적을 랭크 / 일반 / 합계로 구분해서 각각 최근 10게임의 전적, 승률, 주요 포지션 등을 보여줌
	 */
	@RequestMapping("/matchAndLeague/{summonerName}")
	public ModelAndView lolMatchAndLeague(HttpSession session, HttpServletRequest request, @PathVariable("summonerName") String summonerName) {
		String summonerURL = "https://kr.api.riotgames.com/lol/summoner/v4/summoners/by-name/" + summonerName + "?api_key=" + api.getLOL_API_KEY();
		
		SummonerDTO summonerDto = null;
		ModelAndView mv = new ModelAndView();
		try {
			HttpClient client = HttpClientBuilder.create().build();
			HttpGet getRequest = new HttpGet(summonerURL);
			HttpResponse response = client.execute(getRequest);
			
			if (response.getStatusLine().getStatusCode() == 200) {
				ResponseHandler<String> handler = new BasicResponseHandler();
				String body = handler.handleResponse(response);
				summonerDto = objectMapper.readValue(body, SummonerDTO.class);	// json을 SummonerDTO로 바꿈
			}
			
			String matchURL = "https://kr.api.riotgames.com/lol/match/v4/matchlists/by-account/" + summonerDto.getAccountId() + "?api_key=" + api.getLOL_API_KEY();
			getRequest = new HttpGet(matchURL);
			response = client.execute(getRequest);
			if (response.getStatusLine().getStatusCode() == 200) {
				ResponseHandler<String> handler = new BasicResponseHandler();
				String body = handler.handleResponse(response);
				List<LMatchDTO> matchList = objectMapper.readValue(body, objectMapper.getTypeFactory().constructCollectionType(List.class, LMatchDTO.class));
				session.setAttribute("matchList", matchList);
			}
			
			String leagueURL = "https://kr.api.riotgames.com/lol/league/v4/entries/by-summoner/" + summonerDto.getId() + "?api_key=" + api.getLOL_API_KEY();
			getRequest = new HttpGet(leagueURL);
			response = client.execute(getRequest);
			if (response.getStatusLine().getStatusCode() == 200) {
				ResponseHandler<String> handler = new BasicResponseHandler();
				String body = handler.handleResponse(response);
				LeagueDTO leagueDto = objectMapper.readValue(body, LeagueDTO.class);
				session.setAttribute("leagueDto", leagueDto);
			}
		} catch(Exception e) {
			
		}
		
		mv.setViewName("/lol/matchAndLeague");							// 이전 상태로 되돌아감 : 수정 필요
		return mv;
	}
}
