package com.project.dnflol.Controller;


import java.sql.Date;
import java.util.ArrayList;
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
import com.project.dnflol.DTO.DApplyDTO;
import com.project.dnflol.DTO.DCharDTO;
import com.project.dnflol.DTO.DGroupDTO;
import com.project.dnflol.DTO.TimeLineDTO;
import com.project.dnflol.DTO.DAdventureDTO;
import com.project.dnflol.Exception.AlreadyExistedApplyException;
import com.project.dnflol.Service.DApplyService;
import com.project.dnflol.Service.DCharService;
import com.project.dnflol.Service.DGroupService;
import com.project.dnflol.Service.UserService;
import com.project.dnflol.util.APIKey;
import com.project.dnflol.util.AuthInfo;
import com.project.dnflol.util.BoardMinMax;
import com.project.dnflol.util.LSearchForm;

@Controller
@RequestMapping("/dnf")
public class DNFController {
	@Autowired
	DGroupService dgServ;

	@Autowired
	DApplyService daServ;

	@Autowired
	DCharService dcServ;

	@Autowired
	UserService uServ;

	APIKey api = new APIKey();
	BoardMinMax bmm;											// 게시판에 노출되는 글을 컨트롤할 객체
	ObjectMapper objectMapper = new ObjectMapper();				// JSON 형태로 반환되는 response를 DTO형태로 바꿔주는 Jackson 라이브러리를 사용하기 위한 객체
	
	/**
	 * 메인 게시판 페이지
	 * - 글을 최근 순서로 10개 단위 10페이지를 게시하고 이전/다음 버튼으로 다른 범위에 있는 글을 가져올 수 있음.
	 * - 글을 일일히 찾기 싫은 사람을 위해 페이지 하단에는 게시판 검색을 위한 검색종류 선택 체크박스, 입력 폼, 검색버튼이 존재함.
	 *   검색 버튼을 누르면 입력 폼에 입력된 정보와 체크박스에서 선택된 정보를 가지고 비슷한 글을 찾아서 그 글들을 보여줌.
	 */
	
	@GetMapping("/dnf/timeline")
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
				session.setAttribute("result", timeline);
				for (TimeLineDTO ele : timeline) {
					 ele.getData();
				}
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

	@RequestMapping("/board")
	public ModelAndView dnfBoard(Model model, HttpSession session) {
		model.addAttribute("searchForm", new LSearchForm());
		bmm = new BoardMinMax(dgServ.readMaxCount());	
		
		List<DGroupDTO> dgroupList = dgServ.readLimitList(bmm);
		
		session.setAttribute("dgroupList", dgroupList);			// 최근 100개의 글
		session.setAttribute("bmm", bmm);	// 이전 100개 글을 불러올 수 있는가?

		ModelAndView mv = new ModelAndView();
		mv.setViewName("/dnf/board");
		return mv;
	}

	/**
	 * 게시판 페이지에서 다음 버튼을 눌렀을 때 이벤트 처리
	 * - bmm을 이용해 새로 가져올 글의 범위와 prev, next 버튼의 활성화 여부를 계산해서 세션에 넣는다.
	 *   이후 게시판 페이지로 리다이렉트한다.
	 */
	@RequestMapping("/board/next")
	public ModelAndView lolNextBoard(Model model, HttpSession session) {
		model.addAttribute("searchForm", new LSearchForm());
		bmm.next();
		List<DGroupDTO> dgroupList = dgServ.readLimitList(bmm);
		session.setAttribute("dgroupList", dgroupList);
		session.setAttribute("bmm", bmm);
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/dnf/board");
		return mv;
	}

	/**
	 * 게시판 페이지에서 이전 버튼을 눌렀을 때 이벤트 처리
	 * - bmm을 이용해 새로 가져올 글의 범위와 prev, next 버튼의 활성화 여부를 계산해서 세션에 넣는다.
	 *   이후 게시판 페이지로 리다이렉트한다.
	 */
	@RequestMapping("/board/prev")
	public ModelAndView dnfPrevBoard(Model model, HttpSession session) {
		model.addAttribute("searchForm", new LSearchForm());
		bmm.prev();
		List<DGroupDTO> dgroupList = dgServ.readLimitList(bmm);
		session.setAttribute("dgroupList", dgroupList);
		session.setAttribute("bmm", bmm);

		ModelAndView mv = new ModelAndView();
		mv.setViewName("/dnf/board");
		return mv;
	}

	/**
	 * 게시판 페이지에서 찾기 버튼을 눌렀을 때 이벤트 처리
	 * - 설명, 그룹이름, 생성자이름중 하나를 선택해서 검색할 수 있으며 prev, next는 비활성화된다.
	 *   이후 게시판 페이지로 리다이렉트한다.
	 */
	@PostMapping("/findBoard")
	public ModelAndView lolFindBoard(HttpSession session, @Valid @ModelAttribute("searchForm") LSearchForm form, BindingResult br) {
		List<DGroupDTO> dgroupList;								// 체크박스 결과를 받아와 적절한 방법으로 글을 검색한다.
		if (form.getCheckRadio().equals("detail"))
			dgroupList = dgServ.readAllByDetail(form.getFindDetail());
		else if (form.getCheckRadio().equals("groupName"))
			dgroupList = dgServ.readAllByGroupName(form.getFindDetail());
		else
			dgroupList = dgServ.readAllByOwnerName(form.getFindDetail());
		session.setAttribute("dgroupList", dgroupList);			
		session.setAttribute("bmm", bmm);

		ModelAndView mv = new ModelAndView();
		mv.setViewName("/dnf/board");
		return mv;
	}

	@GetMapping(value="/board/newPostGET")
	public String newPostGet(HttpSession session, Model model) {
		List<DCharDTO> mylolChars = dcServ.readAllByUid(((AuthInfo)session.getAttribute("authInfo")).getUid());	// LoL 연동 계정 정보
		session.setAttribute("mylolChars", mylolChars); 
		
		model.addAttribute("post", new DGroupDTO());
		return "/dnf/newPost";
	}
/*
	@PostMapping(value="/board/newPostPOST")
	public String newPostPost(@Valid @ModelAttribute("post") DGroupDTO dgroupDto, BindingResult br) throws Exception {
		if (br.hasErrors()) {					// 필요한 정보가 정한 폼에 맞지 않으면 이전 단계로 돌아감
			return "redirect:/dnf/board/newPost";
		}

		if (dgroupDto.getDgroupOwner() == null) {
			return "redirect:/dnf/board/newPost";
		}
		
		if (dgroupDto.getDgroupType().equals("듀오랭크"))
			dgroupDto.setDgroupMax(2);
		else
			dgroupDto.setDgroupMax(5);

		Date date = new Date(System.currentTimeMillis());
		dgroupDto.setDgroupDate(date);
		
		dgServ.create(dgroupDto);
		
		Integer dgroupId = dgServ.readdgroupId(dgroupDto);
		return "redirect:/dnf/boardDetail/" + dgroupId;
	}*/

	/**
	 * 그룹의 세부 정보를 보여주는 페이지
	 * - 그룹 생성자와 현재까지의 멤버 목록을 보여줌
	 * - 그룹 멤버를 클릭하면 그 멤버의 세부 전적/티어를 볼 수 있는 페이지로 이동
	 * - 페이지 하단에는 메인 게시판으로 되돌아가는 버튼과 신청 페이지로 이동할 수 있는 버튼이 존재
	 *//*
	@RequestMapping("/boardDetail/{dgroupId}")
	public ModelAndView lolGroupBoard(Model model, HttpSession session, @PathVariable(value="dgroupId") int dgroupId) {
		model.addAttribute("applyForm", new DApplyDTO());
		
		DGroupDTO dgroupDto = dgServ.readById(dgroupId);										// 그룹 세부 정보
		List<DCharDTO> acceptedList = dcServ.readAllAcceptedByGroupId(dgroupId);				// 수락된 멤버 목록
		session.setAttribute("dgroupDto", dgroupDto);
		session.setAttribute("acceptedList", acceptedList);
		
		List<DCharDTO> mylolChars = dcServ.readAllByUid(((AuthInfo)session.getAttribute("authInfo")).getUid());	// LoL 연동 계정 정보
		session.setAttribute("mylolChars", mylolChars); 
		
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/dnf/boardDetail");
		return mv;
	}*/
	
	/**
	 * 그룹 신청 페이지에서 아이디를 클릭하면 신청을 넣고 게시판 페이지로 돌아감
	 *//*
	@PostMapping("/submit")
	public ModelAndView lolSummitToGroup(Model model, HttpSession session, @Valid @ModelAttribute("applyForm") DApplyDTO lapplyDto, BindingResult br) {
		ModelAndView mv = new ModelAndView();
		
		if (br.hasErrors()) {
			mv.setViewName("redirect:/dnf/board");
			return mv;
		}
		try {
			daServ.create(lapplyDto);
			session.setAttribute("applyResult", lapplyDto.getLcharName());
			session.setAttribute("leae", null);
		} catch(AlreadyExistedApplyException leae) {
			session.setAttribute("leae", leae.getMessage());
			session.setAttribute("applyResult", null);
		}
		
		mv.setViewName("redirect:/dnf/boardDetail/" + lapplyDto.getDgroupId());
		return mv;
	}

	@GetMapping("/findSummoner")
	public ModelAndView findSummoner(Model model) {
		model.addAttribute("summoner", new SummonerDTO());
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/dnf/findSummoner");
		return mv;
	}

	@PostMapping("/findSummoner")
	public ModelAndView findSummoner(HttpSession session, @Valid @ModelAttribute("summoner") SummonerDTO summoner, BindingResult br) {
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		String requestURL = "https://kr.api.riotgames.com/dnf/summoner/v4/summoners/by-name/" + summoner.getName().replaceAll(" ", "") + "?api_key=" + api.getLOL_API_KEY();
		SummonerDTO summonerDto = new SummonerDTO();
		ModelAndView mv = new ModelAndView();
		try {
			HttpClient client = HttpClientBuilder.create().build();
			HttpGet getRequest = new HttpGet(requestURL);
			HttpResponse response = client.execute(getRequest);
			
			session.setAttribute("status", response.getStatusLine().getStatusCode());*/
			/*
			 * 기본적인 계정 정보를 받아옴
			 *//*
			if (response.getStatusLine().getStatusCode() == 200) {
				ResponseHandler<String> handler = new BasicResponseHandler();
				String body = handler.handleResponse(response);
				summonerDto = objectMapper.readValue(body, SummonerDTO.class);	// json을 SummonerDTO로 바꿈
				session.setAttribute("summonerDto", summonerDto);		// 세션에 SummonerDTO를 넣음
			}
			*/
			/*
			 * 계정의 티어를 받아옴
			 *//*
			String leagueURL = "https://kr.api.riotgames.com/dnf/league/v4/entries/by-summoner/" + summonerDto.getId() + "?api_key=" + api.getLOL_API_KEY();
			getRequest = new HttpGet(leagueURL);
			response = client.execute(getRequest);
			if (response.getStatusLine().getStatusCode() == 200) {
				ResponseHandler<String> handler = new BasicResponseHandler();
				String body = handler.handleResponse(response);
				LeagueDTO leagueDto = objectMapper.readValue(body.substring(1, body.length()-1), LeagueDTO.class);
				session.setAttribute("leagueDto", leagueDto);
			}
		} catch(Exception e) {
			session.setAttribute("Exception", e);
			mv.setViewName("/security/denied");
		}
		
		mv.setViewName("/dnf/findSummoner");
		return mv;
	}
	
	@RequestMapping("/addSummoner") 
	public String addSummoner(Model model, HttpSession session) {
		SummonerDTO summonerDto = (SummonerDTO)session.getAttribute("summonerDto");
		DCharDTO lcharDto = new DCharDTO(((AuthInfo)session.getAttribute("authInfo")).getUid(), summonerDto.getName());
		dcServ.create(lcharDto);										// 아이디를 계정에 연동
		
		model.addAttribute("summonerDto", new SummonerDTO());
		
		return "redirect:/user/myPage";				// 이전 페이지로 되돌아감
	}
	
	@GetMapping("/deleteSummoner/{lcharName}")
	public String deleteSummoner(HttpSession session, @PathVariable(value="lcharName") String lcharName) {
		dcServ.deleteByName(lcharName);
		return "redirect:/user/myPage";
	}
*/
	/**
	 * 계정 전적 정보를 볼 수 있는 페이지
	 * - 계정 전적을 랭크 / 일반 / 합계로 구분해서 각각 최근 10게임의 전적, 승률, 주요 포지션 등을 보여줌
	 *//*
	@RequestMapping("/matchAndLeague/{lcharName}")
	public ModelAndView lolMatchAndLeague(HttpSession session, HttpServletRequest request, @PathVariable("lcharName") String lcharName) {
		DCharDTO lcharDto = dcServ.readByName(lcharName);
		session.setAttribute("lcharDto", lcharDto);
		
		String summonerURL = "https://kr.api.riotgames.com/dnf/summoner/v4/summoners/by-name/" + lcharName + "?api_key=" + api.getLOL_API_KEY();

		SummonerDTO summonerDto = null;
		try {
			HttpClient client = HttpClientBuilder.create().build();
			HttpGet getRequest = new HttpGet(summonerURL);
			HttpResponse response = client.execute(getRequest);*/

			/*
			 * 기본적인 계정 정보를 받아옴
			 *//*
			if (response.getStatusLine().getStatusCode() == 200) {
				ResponseHandler<String> handler = new BasicResponseHandler();
				String body = handler.handleResponse(response);
				summonerDto = objectMapper.readValue(body, SummonerDTO.class);	// json을 SummonerDTO로 바꿈
				
			}*/
			
			/*
			 * 계정의 티어를 받아옴
			 *//*
			String leagueURL = "https://kr.api.riotgames.com/dnf/league/v4/entries/by-summoner/" + summonerDto.getId() + "?api_key=" + api.getLOL_API_KEY();
			getRequest = new HttpGet(leagueURL);
			response = client.execute(getRequest);
			if (response.getStatusLine().getStatusCode() == 200) {
				ResponseHandler<String> handler = new BasicResponseHandler();
				String body = handler.handleResponse(response);
				LeagueDTO leagueDto = objectMapper.readValue(body, LeagueDTO.class);
				session.setAttribute("leagueDto", leagueDto);
			}
*/
			/*
			 * 계정의 전적을 받아옴
			 *//*
			String matchURL = "https://asia.api.riotgames.com/dnf/match/v5/matches/by-puuid/" + summonerDto.getPuuid() + "?api_key=" + api.getLOL_API_KEY();
			getRequest = new HttpGet(matchURL);
			response = client.execute(getRequest);
			if (response.getStatusLine().getStatusCode() == 200) {
				ResponseHandler<String> handler = new BasicResponseHandler();
				String body = handler.handleResponse(response);
				ArrayList<String> matches = new ArrayList<>();
				System.out.println();
			}
			
			

			
		} catch(Exception e) {

		}

		ModelAndView mv = new ModelAndView();
		mv.setViewName("/dnf/charDetail");							// 
		return mv;
	}*/
}