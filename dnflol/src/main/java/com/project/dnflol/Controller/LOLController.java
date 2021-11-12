package com.project.dnflol.Controller;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.dnflol.DTO.InfoDTO;
import com.project.dnflol.DTO.LApplyDTO;
import com.project.dnflol.DTO.LCharDTO;
import com.project.dnflol.DTO.LGroupDTO;
import com.project.dnflol.DTO.LeagueDTO;
import com.project.dnflol.DTO.ParticipantDTO;
import com.project.dnflol.DTO.SummonerDTO;
import com.project.dnflol.DTO.UserDTO;
import com.project.dnflol.Exception.AlreadyExistedApplyException;
import com.project.dnflol.Exception.AlreadyExistedLCharNameException;
import com.project.dnflol.Exception.NoSuchCharException;
import com.project.dnflol.Exception.NoSuchGroupException;
import com.project.dnflol.Exception.TooManyApplyException;
import com.project.dnflol.Service.LApplyService;
import com.project.dnflol.Service.LCharService;
import com.project.dnflol.Service.LGroupService;
import com.project.dnflol.Service.UserService;
import com.project.dnflol.util.APIKey;
import com.project.dnflol.util.AuthInfo;
import com.project.dnflol.util.BoardMinMax;
import com.project.dnflol.util.LSearchForm;
import com.project.dnflol.util.SummonerMatchDetails;

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

	APIKey api = new APIKey();									// API Key를 가지고 있는 객체
	List<LGroupDTO> lgroupList;									// 게시판에 노출될 글 리스트
	BoardMinMax bmm;											// 게시판에 노출되는 글을 컨트롤할 객체
	ObjectMapper objectMapper = new ObjectMapper();				// JSON 형태로 반환되는 response를 DTO형태로 바꿔주는 Jackson 라이브러리를 사용하기 위한 객체
	
	/**
	 * LOLController 내의 메소드를 실행하기 전에 실행되어
	 * "authInfo" 이름으로 Model에 객체를 넣어 둠
	 */
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
	
	@ModelAttribute("post")
	public LGroupDTO post() {
		return new LGroupDTO();
	}
	
	/**
	 * 메인 게시판 페이지
	 * - 글을 최근 순서로 10개 단위 10페이지를 게시하고 이전/다음 버튼으로 다른 범위에 있는 글을 가져올 수 있음.
	 * - 글을 일일히 찾기 싫은 사람을 위해 페이지 하단에는 게시판 검색을 위한 검색종류 선택 체크박스, 입력 폼, 검색버튼이 존재함.
	 *   검색 버튼을 누르면 입력 폼에 입력된 정보와 체크박스에서 선택된 정보를 가지고 비슷한 글을 찾아서 그 글들을 보여줌.
	 */
	@RequestMapping("/board")
	public ModelAndView lolBoard() {
		ModelAndView mv = new ModelAndView();
		bmm = new BoardMinMax(lgServ.readMaxCount());		// 새로운 bmm 객체를 만들어 둠
		lgroupList = lgServ.readLimitList(bmm);				// 최신 글 리스트(100개)를 불러옴 
		
		mv.setViewName("redirect:/lol/board/" + bmm.getPaging());	// 첫 번째 페이지로 리다이렉트
		return mv;
	}
	
	/**
	 * 게시판 페이지에서 다음 버튼을 눌렀을 때 이벤트 처리
	 * - bmm을 이용해 새로 가져올 글의 범위와 prev, next 버튼의 활성화 여부를 계산해서 세션에 넣는다.
	 *   이후 게시판 페이지로 리다이렉트한다.
	 */
	@RequestMapping("/board/next")
	public ModelAndView lolNextBoard() {
		ModelAndView mv = new ModelAndView();
		bmm.next();											// bmm을 다음으로 넘김
		
		mv.setViewName("redirect:/lol/board/" + bmm.getPaging());	// 첫 번째 페이지로 리다이렉트
		return mv;
	}

	/**
	 * 게시판 페이지에서 이전 버튼을 눌렀을 때 이벤트 처리
	 * - bmm을 이용해 새로 가져올 글의 범위와 prev, next 버튼의 활성화 여부를 계산해서 세션에 넣는다.
	 *   이후 게시판 페이지로 리다이렉트한다.
	 */
	@RequestMapping("/board/prev")
	public ModelAndView lolPrevBoard() {
		ModelAndView mv = new ModelAndView();
		bmm.prev();											// bmm을 이전으로 넘김
		
		mv.setViewName("redirect:/lol/board/" + bmm.getPaging());	// 첫 번째 페이지로 리다이렉트
		return mv;
	}
	
	@RequestMapping("/board/{page}")
	public String lolBoardPaging(Model model, @PathVariable("page") int page) {
		if (bmm.getLimit() - page*10 < -10)												// URL로 비활성화된 페이지로 접근하면 다시 첫 페이지로 이동하도록 함
			return "redirect:/lol/board/" + bmm.getPaging();	

		int startIdx = bmm.getMin()+(page-1)*10;										// 현재 페이지에서 볼 수 있는 글의 시작, 끝 인덱스 계산
		int endIdx = Math.min(bmm.getLimit(), startIdx+10);
		
		model.addAttribute("lgroupList", lgroupList.subList(startIdx, endIdx));			// 현재 페이지에서 볼 수 있는 글만 담아서 모델에 담음
		model.addAttribute("bmm", bmm);
		
		List<LCharDTO> mylolChars = lcServ.readAllByUid(((AuthInfo)model.getAttribute("authInfo")).getUid());
		model.addAttribute("mylolChars", mylolChars);
		
		return "/lol/board";
	}

	/**
	 * 게시판 페이지에서 상세정보로 찾기 버튼을 눌렀을 때 이벤트 처리
	 * 이후 게시판 페이지로 리다이렉트한다.
	 */
	@PostMapping("/findBoard")
	public ModelAndView lolFindBoard(Model model, @Valid @ModelAttribute("searchForm") LSearchForm form, BindingResult br) {
		if (form.getCheckRadio().equals("detail"))							// 어떤 옵션을 선택했는지 체크해서 적절한 Service, mapper를 통해 DB 접근을 통해 검색된 글 리스트를 받아 옴
			lgroupList = lgServ.readAllByDetail(form.getFindDetail());
		else if (form.getCheckRadio().equals("groupName"))
			lgroupList = lgServ.readAllByGroupName(form.getFindDetail());
		else
			lgroupList = lgServ.readAllByOwnerName(form.getFindDetail());
		
		bmm = new BoardMinMax(lgroupList.size());							// 받아 온 글 리스트를 가지고 새로운 bmm객체를 생성
		
		model.addAttribute("lgroupList", lgroupList);			
		model.addAttribute("bmm", bmm);

		ModelAndView mv = new ModelAndView();	
		mv.setViewName("redirect:/lol/board/" + bmm.getPaging());		//첫 번째 페이지로 리다이렉트
		return mv;
	}

	@PostMapping(value="/board/newPost")
	public String newPost(HttpServletRequest request, RedirectAttributes rdAttributes, @Valid @ModelAttribute("post") LGroupDTO lgroupDto, BindingResult br) throws Exception {
		if (br.hasErrors())													// 필요한 정보가 정한 폼에 맞지 않으면 이전 단계로 돌아감
			return "redirect:/lol/board/newPostGET";

		if (lgroupDto.getLgroupOwner() == null)								
			return "redirect:/lol/board/newPostGET";
		
		if (lgroupDto.getLgroupType().equals("듀오랭크"))						// 선택한 게임 타입에 따라 최대 인원수를 정함
			lgroupDto.setLgroupMax(2);
		else
			lgroupDto.setLgroupMax(5);

		DateFormat format = new SimpleDateFormat("yy.MM.dd kk:mm:ss");		// '연.월.일 시간:분:초' 형식으로 시간을 구함
		String dateStr = format.format(Calendar.getInstance().getTime());
		lgroupDto.setLgroupDate(dateStr);
		
		try {
			lgServ.create(lgroupDto); 											// DB접근을 통해 게시글 생성 
			int lgroupId = lgServ.readlgroupId(lgroupDto);						// 생성한 게시글의 고유 번호를 받아 옴
			
			LApplyDTO ownerApply = new LApplyDTO(lgroupDto.getLgroupOwner(), lgroupId, lgroupDto.getLgroupName());
			laServ.createToAccepted(ownerApply);								// 게시물을 생성한 사람을 수락 상태로 초대 추가함
			
			return "redirect:/lol/boardDetail/" + lgroupId;						// 생성한 게시글로 리다이렉트
		} catch(Exception e) {
			rdAttributes.addFlashAttribute("error", e);
			return "redirect:" + request.getHeader("Referer");
		}
	}

	@GetMapping("/board/delete/{lgroupId}")
	public String deletePost(HttpServletRequest request, RedirectAttributes rdAttributes, @PathVariable("lgroupId") int lgroupId, Model model) {
		if (((String)model.getAttribute("ownerUid")).equals(((AuthInfo)model.getAttribute("authInfo")).getUid())) {
			rdAttributes.addFlashAttribute("error", new Exception("작성한 계정과 달라 삭제할 수 없습니다."));
			return "redirect:/lol/board";
		}
		
		try {
			lgServ.deleteById(lgroupId);		
		} catch(NoSuchGroupException nsge) {
			rdAttributes.addFlashAttribute("error", nsge);
			return "redirect:/lol/board";
		}
		rdAttributes.addFlashAttribute("error", new Exception("삭제에 성공했습니다"));
		return "redirect:/lol/board";
	}
	
	/**
	 * 그룹의 세부 정보를 보여주는 페이지
	 * - 그룹 생성자와 현재까지의 멤버 목록을 보여줌
	 * - 그룹 멤버를 클릭하면 그 멤버의 세부 전적/티어를 볼 수 있는 페이지로 이동
	 * - 페이지 하단에는 메인 게시판으로 되돌아가는 버튼과 신청 페이지로 이동할 수 있는 버튼이 존재
	 */
	@RequestMapping("/boardDetail/{lgroupId}")
	public String lolBoardDetail(HttpServletRequest request, RedirectAttributes rdAttributes, Model model, @PathVariable(value="lgroupId") int lgroupId) {
		LGroupDTO lgroupDto = null;
		try {
			lgroupDto = lgServ.readById(lgroupId);										// 게시글 세부 정보
		} catch(NoSuchGroupException nsge) {
			rdAttributes.addFlashAttribute("error", nsge);
			return "redirect:/lol/board";
		}
		List<LApplyDTO> acceptedList = laServ.readAllAcceptedByGroupId(lgroupId);				// 수락된 멤버 목록
		model.addAttribute("lgroupDto", lgroupDto);
		model.addAttribute("acceptedList", acceptedList);
		
		List<LCharDTO> myAppliedChars = lcServ.readAllAppliedByUid(((AuthInfo)model.getAttribute("authInfo")).getUid(), lgroupId);			// 내 LOL 계정 중 해당 게시글에 이미 신청한 계정
		List<LCharDTO> myNotAppliedChars = lcServ.readAllNotAppliedByUid(((AuthInfo)model.getAttribute("authInfo")).getUid(), lgroupId);	// 내 LOL 계정 중 해당 게시글에 아직 신청하지 않은 계정
		model.addAttribute("myAppliedChars", myAppliedChars);
		model.addAttribute("myNotAppliedChars", myNotAppliedChars);

		try {
			LCharDTO lcharDto = lcServ.readByName(lgroupDto.getLgroupOwner());
			model.addAttribute("ownerUid", lcharDto.getUid());
			return "/lol/boardDetail";
		} catch(NoSuchCharException nsce) {
			rdAttributes.addFlashAttribute("error", nsce);
			return "redirect:" + request.getHeader("Referer");
		}
	}
	
	/**
	 * 그룹 신청 페이지에서 아이디를 클릭하면 신청을 넣고 게시판 페이지로 돌아감
	 */
	@PostMapping("/submit")
	public String lolSubmitToGroup(HttpServletRequest request, RedirectAttributes rdAttributes, Model model, HttpSession session, @Valid @ModelAttribute("applyForm") LApplyDTO lapplyDto, BindingResult br) {
		if (br.hasErrors()) {
			return "redirect:/lol/board";
		}
		try {
			laServ.create(lapplyDto);						// DB접근을 통해 신청 정보 생성
		} catch(AlreadyExistedApplyException leae) {
			rdAttributes.addFlashAttribute("error", leae);
			return "redirect:" + request.getHeader("Referer");
		}
		
		return "redirect:/lol/boardDetail/" + lapplyDto.getLgroupId();
	}

	@GetMapping("/findSummoner")
	public ModelAndView findSummoner(Model model) {
		ModelAndView mv = new ModelAndView();
		model.addAttribute("summoner", new SummonerDTO());	// LOL계정 정보를 담을 폼을 모델에 저장
		mv.setViewName("/lol/findSummoner");
		return mv;
	}

	@PostMapping("/findSummoner")
	public String findSummoner(HttpServletRequest request, RedirectAttributes rdAttributes, Model model, @Valid @ModelAttribute("summoner") SummonerDTO summoner, BindingResult br) {
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		String requestURL = "https://kr.api.riotgames.com/lol/summoner/v4/summoners/by-name/" + summoner.getName().replaceAll(" ", "") + "?api_key=" + api.getLOL_API_KEY();
		SummonerDTO summonerDto = new SummonerDTO();
		try {
			HttpClient client = HttpClientBuilder.create().build();
			HttpGet getRequest = new HttpGet(requestURL);
			HttpResponse response = client.execute(getRequest);
			
			/*
			 * 기본적인 계정 정보를 받아옴
			 */
			if (response.getStatusLine().getStatusCode() == 200) {
				ResponseHandler<String> handler = new BasicResponseHandler();
				String body = handler.handleResponse(response);
				summonerDto = objectMapper.readValue(body, SummonerDTO.class);		// JSON 응답을 DTO로 바꾸는 작업
				model.addAttribute("summoner", summonerDto);		
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
				if (body.equals("[]")) {
					rdAttributes.addFlashAttribute("error", new Exception(""));
					return "redirect:" + request.getHeader("Referer");
				}
				else {
					LeagueDTO leagueDto = objectMapper.readValue(body.substring(1, body.length()-1), LeagueDTO.class);	// JSON 응답을 DTO로 바꾸는 작업
					model.addAttribute("leagueDto", leagueDto);
				}
			}
		} catch(Exception e) {
			model.addAttribute("Exception", e);
			return "/security/denied";
		}
		
		return "/lol/findSummoner";		// 다시 /lol/findSummoner 페이지로 이동해서 검색 결과를 확인하도록 함
	}
	
	@PostMapping("/addSummoner") 
	public String addSummoner(Model model, HttpServletRequest request, RedirectAttributes rdAttributes, @Valid @ModelAttribute("summoner") SummonerDTO summonerDto, BindingResult br) {
		LCharDTO lcharDto = new LCharDTO(((AuthInfo)model.getAttribute("authInfo")).getUid(), summonerDto.getName());	// 새로운 LOL 계정 정보 생성
		try {
			lcServ.create(lcharDto);					// 아이디를 계정에 연동
		} catch(AlreadyExistedLCharNameException aelne) {
			rdAttributes.addFlashAttribute("error", aelne);
		}
		return "redirect:/user/myPage";				// 마이페이지로 리다이렉트
	}
	
	@GetMapping("/deleteSummoner/{lcharName}")
	public String deleteSummoner(HttpServletRequest request, RedirectAttributes rdAttributes, @PathVariable(value="lcharName") String lcharName) {
		try {
			lcServ.deleteByName(lcharName);				// DB에서 해당 LOL 계정 삭제
		} catch(NoSuchCharException nsce) {
			rdAttributes.addFlashAttribute("error", nsce);
		}
		
		return "redirect:/user/myPage";				// 마이페이지로 리다이렉트
	}

	/**
	 * 계정 전적 정보를 볼 수 있는 페이지
	 * - 계정 전적을 랭크 / 일반 / 합계로 구분해서 각각 최근 10게임의 전적, 승률, 주요 포지션 등을 보여줌
	 */
	@RequestMapping("/charDetail/{lcharName}")
	public String lolCharDetail(HttpServletRequest request, RedirectAttributes rdAttributes, Model model, @PathVariable("lcharName") String lcharName) {
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		
		LCharDTO lcharDto = null;

		try {
			lcharDto = lcServ.readByName(lcharName);
			model.addAttribute("lcharDto", lcharDto);
		} catch(NoSuchCharException nsce) {
			rdAttributes.addFlashAttribute("error", nsce);
			return "redirect:" + request.getHeader("Referer");
		}
		
		String summonerURL = "https://kr.api.riotgames.com/lol/summoner/v4/summoners/by-name/" + lcharName.replaceAll(" ", "") + "?api_key=" + api.getLOL_API_KEY();

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
				summonerDto = objectMapper.readValue(body, SummonerDTO.class);		// JSON 응답을 DTO로 바꾸는 작업
				System.out.println(summonerDto);
				
				/*
				 * 계정의 티어를 받아옴
				 */
				String leagueURL = "https://kr.api.riotgames.com/lol/league/v4/entries/by-summoner/" + summonerDto.getId() + "?api_key=" + api.getLOL_API_KEY();
				getRequest = new HttpGet(leagueURL);
				response = client.execute(getRequest);
				if (response.getStatusLine().getStatusCode() == 200) {
					handler = new BasicResponseHandler();
					body = handler.handleResponse(response);
					
					if (body.equals("[]")) {
						LeagueDTO leagueDto = objectMapper.readValue(body.substring(1, body.length()-1), LeagueDTO.class);	// JSON 응답을 DTO로 바꾸는 작업
						model.addAttribute("leagueDto", leagueDto);
					}
					else {
						model.addAttribute("leagueDto", null);
					}
					
					/*
					 * 계정의 전적을 받아옴
					 */
					String matchURL = "https://asia.api.riotgames.com/lol/match/v5/matches/by-puuid/" + summonerDto.getPuuid() + "/ids?start=0&count=10&api_key=" + api.getLOL_API_KEY();
					getRequest = new HttpGet(matchURL);
					response = client.execute(getRequest);
					if (response.getStatusLine().getStatusCode() == 200) {
						handler = new BasicResponseHandler();
						body = handler.handleResponse(response);
						List<String> matches = Arrays.asList(body.substring(2, body.length()-2).split("\",\""));	// match_id의 배열을 리스트 형태로 변환
						System.out.println("matches " + body);
						
						List<ParticipantDTO> rankQ = new ArrayList<>();		// 랭크 게임 정보
						List<ParticipantDTO> normalQ = new ArrayList<>();	// 일반 게임 정보
						for (int i = 0; i < matches.size(); i++) {
							matchURL = "https://asia.api.riotgames.com/lol/match/v5/matches/" + matches.get(i) + "?api_key=" + api.getLOL_API_KEY();
							getRequest = new HttpGet(matchURL);
							response = client.execute(getRequest);
							if (response.getStatusLine().getStatusCode() == 200) {
								handler = new BasicResponseHandler();
								body = handler.handleResponse(response);
								System.out.println("match" + i + " " + body);
								/*
								 * 많고 많은 JSON 응답 중 관심있게 볼 것은 ParticipantDTO의 리스트 중 검색 계정의 정보이므로
								 * InfoDTO와 그 내부의 ParticipantDTO만 따로 구해서 계정 ID와 일치하는 정보만 뽑아낸다.
								 * 뽑아낸 정보는 랭크/일반게임 여부에 따라 위에서 정의한 리스트에 넣는다.
								 */
								String info = body.substring(body.indexOf("info")+6, body.length()-1);
								InfoDTO infoDto = objectMapper.readValue(info, InfoDTO.class);
								String participants = info.substring(info.indexOf("participants")+14, info.indexOf("platformId")-2);
								List<ParticipantDTO> participantList = objectMapper.readValue(participants, new TypeReference<List<ParticipantDTO>>() {});	// JSO응답을 DTO의 List로 바꾸는 작업
								for (int j = 0; j < participantList.size(); j++) {
									ParticipantDTO person = participantList.get(j);
									if (person.getSummonerName().equals(lcharDto.getLcharName())) {		// 계정 ID가 같을 때
										if (infoDto.getQueueId() == 430)								// 게임 큐 ID를 보고 어떤 종류의 게임을 했는 지 판단해서 리스트에 넣음 
											normalQ.add(person);
										else if (infoDto.getQueueId() == 420)
											rankQ.add(person);
										break;
									}
								}
							}
							else {
								rdAttributes.addFlashAttribute("error", new Exception("API 접속 오류(Match) : " + response.getStatusLine().getStatusCode()));
								return "redirect:" + request.getHeader("Referer");
							}
						}
						
						/*
						 * 구한 ParticipantsDTO를 그대로 View에서 사용하기엔 View에서 해야 할 작업량이 너무 많으므로
						 * 관심있게 볼 정보만 뽑아서 모델에 넣는다.
						 * 관심있게 볼 정보는 총 게임 수, 라인별 게임 수, KDA, 라인별 KDA, 총 준 데미지, 라인별 준 데미지, 총 받은 데미지, 라인별 받은 데미지다.
						 */
						SummonerMatchDetails rankDetail = new SummonerMatchDetails();
						rankDetail.setTotalGames(rankQ.size());
						for (int i = 0; i < rankQ.size(); i++) {
							ParticipantDTO myInfo = rankQ.get(i);

							rankDetail.addTotalKDA(myInfo.getKills(), myInfo.getDeaths(), myInfo.getAssists());
							rankDetail.addTotalDamage(myInfo.getTotalDamageDealtToChampions(), myInfo.getTotalDamageTaken());

							switch(myInfo.getTeamPosition()) {
							case "TOP" : {
								rankDetail.increaseTopCount();
								rankDetail.addTopKDA(myInfo.getKills(), myInfo.getDeaths(), myInfo.getAssists());
								rankDetail.addTopDamage(myInfo.getTotalDamageDealtToChampions(), myInfo.getTotalDamageTaken());
								break;
							}
							case "JUNGLE" : {
								rankDetail.increaseJugCount();
								rankDetail.addJugKDA(myInfo.getKills(), myInfo.getDeaths(), myInfo.getAssists());
								rankDetail.addJugDamage(myInfo.getTotalDamageDealtToChampions(), myInfo.getTotalDamageTaken());
								break;
							}
							case "MIDDLE" : {
								rankDetail.increaseMidCount();
								rankDetail.addMidKDA(myInfo.getKills(), myInfo.getDeaths(), myInfo.getAssists());
								rankDetail.addMidDamage(myInfo.getTotalDamageDealtToChampions(), myInfo.getTotalDamageTaken());
								break;
							}
							case "BOTTOM" : {
								rankDetail.increaseBotCount();
								rankDetail.addBotKDA(myInfo.getKills(), myInfo.getDeaths(), myInfo.getAssists());
								rankDetail.addBotDamage(myInfo.getTotalDamageDealtToChampions(), myInfo.getTotalDamageTaken());
								break;
							}
							case "UTILITY" : {
								rankDetail.increaseSupCount();
								rankDetail.addSupKDA(myInfo.getKills(), myInfo.getDeaths(), myInfo.getAssists());
								rankDetail.addSupDamage(myInfo.getTotalDamageDealtToChampions(), myInfo.getTotalDamageTaken());
								break;
							}
							}
						}
						model.addAttribute("rankDetail", rankDetail);
						
						SummonerMatchDetails normalDetail = new SummonerMatchDetails();
						normalDetail.setTotalGames(normalQ.size());
						for (int i = 0; i < normalQ.size(); i++) {
							ParticipantDTO myInfo = normalQ.get(i);

							normalDetail.addTotalKDA(myInfo.getKills(), myInfo.getDeaths(), myInfo.getAssists());
							normalDetail.addTotalDamage(myInfo.getTotalDamageDealtToChampions(), myInfo.getTotalDamageTaken());

							switch(myInfo.getTeamPosition()) {
							case "TOP" : {
								normalDetail.increaseTopCount();
								normalDetail.addTopKDA(myInfo.getKills(), myInfo.getDeaths(), myInfo.getAssists());
								normalDetail.addTopDamage(myInfo.getTotalDamageDealtToChampions(), myInfo.getTotalDamageTaken());
								break;
							}
							case "JUNGLE" : {
								normalDetail.increaseJugCount();
								normalDetail.addJugKDA(myInfo.getKills(), myInfo.getDeaths(), myInfo.getAssists());
								normalDetail.addJugDamage(myInfo.getTotalDamageDealtToChampions(), myInfo.getTotalDamageTaken());
								break;
							}
							case "MIDDLE" : {
								normalDetail.increaseMidCount();
								normalDetail.addMidKDA(myInfo.getKills(), myInfo.getDeaths(), myInfo.getAssists());
								normalDetail.addMidDamage(myInfo.getTotalDamageDealtToChampions(), myInfo.getTotalDamageTaken());
								break;
							}
							case "BOTTOM" : {
								normalDetail.increaseBotCount();
								normalDetail.addBotKDA(myInfo.getKills(), myInfo.getDeaths(), myInfo.getAssists());
								normalDetail.addBotDamage(myInfo.getTotalDamageDealtToChampions(), myInfo.getTotalDamageTaken());
								break;
							}
							case "UTILITY" : {
								normalDetail.increaseSupCount();
								normalDetail.addSupKDA(myInfo.getKills(), myInfo.getDeaths(), myInfo.getAssists());
								normalDetail.addSupDamage(myInfo.getTotalDamageDealtToChampions(), myInfo.getTotalDamageTaken());
								break;
							}
							}
						}
						model.addAttribute("normalDetail", normalDetail);
					}
					else {
						rdAttributes.addFlashAttribute("error", new Exception("API 접속 오류(Matches) : " + response.getStatusLine().getStatusCode()));
						return "redirect:" + request.getHeader("Referer");
					}
				}
				else {
					rdAttributes.addFlashAttribute("error", new Exception("API 접속 오류(League) : " + response.getStatusLine().getStatusCode()));
					return "redirect:" + request.getHeader("Referer");
				}
			}
			else {
				rdAttributes.addFlashAttribute("error", new Exception("API 접속 오류(Summoner) : " + response.getStatusLine().getStatusCode()));
				return "redirect:" + request.getHeader("Referer");
			}
			
		} catch(Exception e) {
			model.addAttribute("error", e);
		}

		return "/lol/charDetail";		
	}
	
	@RequestMapping("/acceptApply/{lapplyId}&{lgroupId}")
	public String acceptApply(HttpServletRequest request, RedirectAttributes rdAttributes, @PathVariable("lapplyId") int lapplyId, @PathVariable("lgroupId") int lgroupId) {
		LApplyDTO applyForm = new LApplyDTO(lapplyId, lgroupId, "ACCEPTED");
		try {
			laServ.updateResult(applyForm);
		} catch(TooManyApplyException tmae) {
			rdAttributes.addFlashAttribute("error", tmae);
		}
		return "redirect:" + request.getHeader("Referer");
	}
	
	@RequestMapping("/denyApply/{lapplyId}&{lgroupId}")
	public String denyApply(HttpServletRequest request, RedirectAttributes rdAttributes, @PathVariable("lapplyId") int lapplyId, @PathVariable("lgroupId") int lgroupId) {
		LApplyDTO applyForm = new LApplyDTO(lapplyId, lgroupId, "DENIED");
		laServ.updateResult(applyForm);
		return "redirect:" + request.getHeader("Referer");
	}
}