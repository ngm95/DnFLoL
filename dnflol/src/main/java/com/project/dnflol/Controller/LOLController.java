package com.project.dnflol.Controller;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.dnflol.DTO.LApplyDTO;
import com.project.dnflol.DTO.LCharDTO;
import com.project.dnflol.DTO.LGroupDTO;
import com.project.dnflol.DTO.LeagueDTO;
import com.project.dnflol.DTO.SummonerDTO;
import com.project.dnflol.DTO.UserDTO;
import com.project.dnflol.Exception.AlreadyExistedApplyException;
import com.project.dnflol.Service.LApplyService;
import com.project.dnflol.Service.LCharService;
import com.project.dnflol.Service.LGroupService;
import com.project.dnflol.Service.UserService;
import com.project.dnflol.util.APIKey;
import com.project.dnflol.util.AuthInfo;
import com.project.dnflol.util.BoardMinMax;
import com.project.dnflol.util.LSearchForm;

@Controller
@RequestMapping("/lol")
public class LOLController {
	@Autowired
	LGroupService lgServ;

	@Autowired
	LApplyService laServ;

	@Autowired
	LCharService lcServ;

	@Autowired
	UserService uServ;

	APIKey api = new APIKey();
	BoardMinMax bmm;											// 게시판에 노출되는 글을 컨트롤할 객체
	ObjectMapper objectMapper = new ObjectMapper();				// JSON 형태로 반환되는 response를 DTO형태로 바꿔주는 Jackson 라이브러리를 사용하기 위한 객체
	
	@ModelAttribute("authInfo")
	public AuthInfo authInfo(Authentication auth) {
		if (auth == null)
			return null;
		else {
			UserDTO user = uServ.readById(auth.getName());
			return new AuthInfo(user.getUid(), user.getUname());
		}
	}
	
	@ModelAttribute("searchForm")
	public LSearchForm searchForm() {
		return new LSearchForm();
	}
	
	@ModelAttribute("applyForm")
	public LApplyDTO applyForm() {
		return new LApplyDTO();
	}
	
	/**
	 * 메인 게시판 페이지
	 * - 글을 최근 순서로 10개 단위 10페이지를 게시하고 이전/다음 버튼으로 다른 범위에 있는 글을 가져올 수 있음.
	 * - 글을 일일히 찾기 싫은 사람을 위해 페이지 하단에는 게시판 검색을 위한 검색종류 선택 체크박스, 입력 폼, 검색버튼이 존재함.
	 *   검색 버튼을 누르면 입력 폼에 입력된 정보와 체크박스에서 선택된 정보를 가지고 비슷한 글을 찾아서 그 글들을 보여줌.
	 */
	@RequestMapping("/board")
	public ModelAndView lolBoard(Model model, HttpSession session) {
		bmm = new BoardMinMax(lgServ.readMaxCount());	
		
		List<LGroupDTO> lgroupList = lgServ.readLimitList(bmm);
		model.addAttribute("lgroupList", lgroupList);			// 최근 15개의 글
		model.addAttribute("bmm", bmm);

		ModelAndView mv = new ModelAndView();
		mv.setViewName("/lol/board");
		return mv;
	}

	/**
	 * 게시판 페이지에서 다음 버튼을 눌렀을 때 이벤트 처리
	 * - bmm을 이용해 새로 가져올 글의 범위와 prev, next 버튼의 활성화 여부를 계산해서 세션에 넣는다.
	 *   이후 게시판 페이지로 리다이렉트한다.
	 */
	@RequestMapping("/board/next")
	public ModelAndView lolNextBoard(Model model, HttpSession session) {
		bmm.next();
		
		List<LGroupDTO> lgroupList = lgServ.readLimitList(bmm);
		model.addAttribute("lgroupList", lgroupList);
		model.addAttribute("bmm", bmm);
		
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/lol/board");
		return mv;
	}

	/**
	 * 게시판 페이지에서 이전 버튼을 눌렀을 때 이벤트 처리
	 * - bmm을 이용해 새로 가져올 글의 범위와 prev, next 버튼의 활성화 여부를 계산해서 세션에 넣는다.
	 *   이후 게시판 페이지로 리다이렉트한다.
	 */
	@RequestMapping("/board/prev")
	public ModelAndView lolPrevBoard(Model model, HttpSession session) {
		bmm.prev();
		
		List<LGroupDTO> lgroupList = lgServ.readLimitList(bmm);
		model.addAttribute("lgroupList", lgroupList);
		model.addAttribute("bmm", bmm);

		ModelAndView mv = new ModelAndView();
		mv.setViewName("/lol/board");
		return mv;
	}

	/**
	 * 게시판 페이지에서 찾기 버튼을 눌렀을 때 이벤트 처리
	 * - 설명, 그룹이름, 생성자이름중 하나를 선택해서 검색할 수 있으며 prev, next는 비활성화된다.
	 *   이후 게시판 페이지로 리다이렉트한다.
	 */
	@PostMapping("/findBoard")
	public ModelAndView lolFindBoard(Model model, @Valid @ModelAttribute("searchForm") LSearchForm form, BindingResult br) {
		List<LGroupDTO> lgroupList;								// 체크박스 결과를 받아와 적절한 방법으로 글을 검색한다.
		if (form.getCheckRadio().equals("detail"))
			lgroupList = lgServ.readAllByDetail(form.getFindDetail());
		else if (form.getCheckRadio().equals("groupName"))
			lgroupList = lgServ.readAllByGroupName(form.getFindDetail());
		else
			lgroupList = lgServ.readAllByOwnerName(form.getFindDetail());
		
		model.addAttribute("lgroupList", lgroupList);			
		model.addAttribute("bmm", bmm);

		ModelAndView mv = new ModelAndView();
		mv.setViewName("/lol/board");
		return mv;
	}

	@GetMapping(value="/board/newPostGET")
	public String newPostGet(HttpSession session, Model model) {
		List<LCharDTO> mylolChars = lcServ.readAllByUid(((AuthInfo)model.getAttribute("authInfo")).getUid());	// LoL 연동 계정 정보
		model.addAttribute("mylolChars", mylolChars); 
		
		model.addAttribute("post", new LGroupDTO());
		return "/lol/newPost";
	}

	@PostMapping(value="/board/newPostPOST")
	public String newPostPost(@Valid @ModelAttribute("post") LGroupDTO lgroupDto, BindingResult br) throws Exception {
		if (br.hasErrors()) {						// 필요한 정보가 정한 폼에 맞지 않으면 이전 단계로 돌아감
			return "redirect:/lol/board/newPostGET";
		}

		if (lgroupDto.getLgroupOwner() == null) {
			return "redirect:/lol/board/newPostGET";
		}
		
		if (lgroupDto.getLgroupType().equals("듀오랭크"))
			lgroupDto.setLgroupMax(2);
		else
			lgroupDto.setLgroupMax(5);

		DateFormat format = new SimpleDateFormat("yy.MM.dd kk:mm:ss");
		String dateStr = format.format(Calendar.getInstance().getTime());
		lgroupDto.setLgroupDate(dateStr);
		
		lgServ.create(lgroupDto);
		
		Integer lgroupId = lgServ.readlgroupId(lgroupDto);
		return "redirect:/lol/boardDetail/" + lgroupId;
	}

	/**
	 * 그룹의 세부 정보를 보여주는 페이지
	 * - 그룹 생성자와 현재까지의 멤버 목록을 보여줌
	 * - 그룹 멤버를 클릭하면 그 멤버의 세부 전적/티어를 볼 수 있는 페이지로 이동
	 * - 페이지 하단에는 메인 게시판으로 되돌아가는 버튼과 신청 페이지로 이동할 수 있는 버튼이 존재
	 */
	@RequestMapping("/boardDetail/{lgroupId}")
	public ModelAndView lolGroupBoardDetail(Model model, HttpSession session, @PathVariable(value="lgroupId") int lgroupId) {
		LGroupDTO lgroupDto = lgServ.readById(lgroupId);										// 그룹 세부 정보
		List<LCharDTO> acceptedList = lcServ.readAllAcceptedByGroupId(lgroupId);				// 수락된 멤버 목록
		model.addAttribute("lgroupDto", lgroupDto);
		model.addAttribute("acceptedList", acceptedList);
		
		List<LCharDTO> myAppliedChars = lcServ.readAllAppliedByUid(((AuthInfo)model.getAttribute("authInfo")).getUid(), lgroupId);
		List<LCharDTO> myNotAppliedChars = lcServ.readAllNotAppliedByUid(((AuthInfo)model.getAttribute("authInfo")).getUid(), lgroupId);
		model.addAttribute("myAppliedChars", myAppliedChars);
		model.addAttribute("myNotAppliedChars", myNotAppliedChars);
		
		List<LCharDTO> allAppliedChars = lcServ.readAllAppliedByGroupId(lgroupId);
		model.addAttribute("allAppliedChars", allAppliedChars);
		
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/lol/boardDetail");
		return mv;
	}
	
	/**
	 * 그룹 신청 페이지에서 아이디를 클릭하면 신청을 넣고 게시판 페이지로 돌아감
	 */
	@PostMapping("/submit")
	public ModelAndView lolSubmitToGroup(Model model, HttpSession session, @Valid @ModelAttribute("applyForm") LApplyDTO lapplyDto, BindingResult br) {
		ModelAndView mv = new ModelAndView();
		
		if (br.hasErrors()) {
			mv.setViewName("redirect:/lol/board");
			return mv;
		}
		try {
			laServ.create(lapplyDto);
		} catch(AlreadyExistedApplyException leae) {
			
		}
		
		mv.setViewName("redirect:/lol/boardDetail/" + lapplyDto.getLgroupId());
		return mv;
	}

	@GetMapping("/findSummoner")
	public ModelAndView findSummoner(Model model) {
		ModelAndView mv = new ModelAndView();
		model.addAttribute("summoner", new SummonerDTO());
		mv.setViewName("/lol/findSummoner");
		return mv;
	}

	@PostMapping("/findSummoner")
	public ModelAndView findSummoner(Model model, @Valid @ModelAttribute("summoner") SummonerDTO summoner, BindingResult br) {
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		String requestURL = "https://kr.api.riotgames.com/lol/summoner/v4/summoners/by-name/" + summoner.getName().replaceAll(" ", "") + "?api_key=" + api.getLOL_API_KEY();
		SummonerDTO summonerDto = new SummonerDTO();
		ModelAndView mv = new ModelAndView();
		try {
			HttpClient client = HttpClientBuilder.create().build();
			HttpGet getRequest = new HttpGet(requestURL);
			HttpResponse response = client.execute(getRequest);
			
			model.addAttribute("status", response.getStatusLine().getStatusCode());
			/*
			 * 기본적인 계정 정보를 받아옴
			 */
			if (response.getStatusLine().getStatusCode() == 200) {
				ResponseHandler<String> handler = new BasicResponseHandler();
				String body = handler.handleResponse(response);
				summonerDto = objectMapper.readValue(body, SummonerDTO.class);	// json을 SummonerDTO로 바꿈
				model.addAttribute("summoner", summonerDto);		// 세션에 SummonerDTO를 넣음
			}
			
			/*
			 * 계정의 티어를 받아옴
			 */
			String leagueURL = "https://kr.api.riotgames.com/lol/league/v4/entries/by-summoner/" + summonerDto.getId() + "?api_key=" + api.getLOL_API_KEY();
			getRequest = new HttpGet(leagueURL);
			response = client.execute(getRequest);
			if (response.getStatusLine().getStatusCode() == 200) {
				ResponseHandler<String> handler = new BasicResponseHandler();
				String body = handler.handleResponse(response);
				LeagueDTO leagueDto = objectMapper.readValue(body.substring(1, body.length()-1), LeagueDTO.class);
				model.addAttribute("leagueDto", leagueDto);
			}
		} catch(Exception e) {
			model.addAttribute("Exception", e);
			mv.setViewName("/security/denied");
		}
		
		mv.setViewName("/lol/findSummoner");
		return mv;
	}
	
	@PostMapping("/addSummoner") 
	public String addSummoner(Model model, @Valid @ModelAttribute("summoner") SummonerDTO summonerDto, BindingResult br) {
		LCharDTO lcharDto = new LCharDTO(((AuthInfo)model.getAttribute("authInfo")).getUid(), summonerDto.getName());
		lcServ.create(lcharDto);										// 아이디를 계정에 연동
		
		return "redirect:/user/myPage";				// 이전 페이지로 되돌아감
	}
	
	@GetMapping("/deleteSummoner/{lcharName}")
	public String deleteSummoner(@PathVariable(value="lcharName") String lcharName) {
		lcServ.deleteByName(lcharName);
		return "redirect:/user/myPage";
	}

	/**
	 * 계정 전적 정보를 볼 수 있는 페이지
	 * - 계정 전적을 랭크 / 일반 / 합계로 구분해서 각각 최근 10게임의 전적, 승률, 주요 포지션 등을 보여줌
	 */
	@RequestMapping("/charDetail/{lcharName}")
	public ModelAndView lolMatchAndLeague(Model model, HttpServletRequest request, @PathVariable("lcharName") String lcharName) {
		LCharDTO lcharDto = lcServ.readByName(lcharName);
		model.addAttribute("lcharDto", lcharDto);
		
		String summonerURL = "https://kr.api.riotgames.com/lol/summoner/v4/summoners/by-name/" + lcharName + "?api_key=" + api.getLOL_API_KEY();

		SummonerDTO summonerDto = null;
		try {
			HttpClient client = HttpClientBuilder.create().build();
			HttpGet getRequest = new HttpGet(summonerURL);
			HttpResponse response = client.execute(getRequest);

			/*
			 * 기본적인 계정 정보를 받아옴
			 */
			if (response.getStatusLine().getStatusCode() == 200) {
				ResponseHandler<String> handler = new BasicResponseHandler();
				String body = handler.handleResponse(response);
				summonerDto = objectMapper.readValue(body, SummonerDTO.class);	// json을 SummonerDTO로 바꿈
				
			}
			
			/*
			 * 계정의 티어를 받아옴
			 */
			String leagueURL = "https://kr.api.riotgames.com/lol/league/v4/entries/by-summoner/" + summonerDto.getId() + "?api_key=" + api.getLOL_API_KEY();
			getRequest = new HttpGet(leagueURL);
			response = client.execute(getRequest);
			if (response.getStatusLine().getStatusCode() == 200) {
				ResponseHandler<String> handler = new BasicResponseHandler();
				String body = handler.handleResponse(response);
				LeagueDTO leagueDto = objectMapper.readValue(body, LeagueDTO.class);
				model.addAttribute("leagueDto", leagueDto);
			}

			/*
			 * 계정의 전적을 받아옴
			 */
			String matchURL = "https://asia.api.riotgames.com/lol/match/v5/matches/by-puuid/" + summonerDto.getPuuid() + "?api_key=" + api.getLOL_API_KEY();
			getRequest = new HttpGet(matchURL);
			response = client.execute(getRequest);
			if (response.getStatusLine().getStatusCode() == 200) {
				ResponseHandler<String> handler = new BasicResponseHandler();
				String body = handler.handleResponse(response);
				ArrayList<String> matches = new ArrayList<>();

				// 테스팅 및 전적 파싱 작업
			}

			
		} catch(Exception e) {

		}

		ModelAndView mv = new ModelAndView();
		mv.setViewName("/lol/charDetail");							
		return mv;
	}
}