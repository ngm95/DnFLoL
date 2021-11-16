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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.dnflol.DTO.InfoDTO;
import com.project.dnflol.DTO.LApplyDTO;
import com.project.dnflol.DTO.LCharDTO;
import com.project.dnflol.DTO.LGroupDTO;
import com.project.dnflol.DTO.LeagueDTO;
import com.project.dnflol.DTO.MatchDTO;
import com.project.dnflol.DTO.ParticipantDTO;
import com.project.dnflol.DTO.SummonerDTO;
import com.project.dnflol.DTO.UserDTO;
import com.project.dnflol.Exception.AlreadyExistedApplyException;
import com.project.dnflol.Exception.AlreadyExistedLCharNameException;
import com.project.dnflol.Exception.NoSuchApplyException;
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
	public String lolBoard() {
		bmm = new BoardMinMax(lgServ.readMaxCount());		// 새로운 bmm 객체를 만들어 둠
		lgroupList = lgServ.readLimitList(bmm);				// 최신 글 리스트(100개)를 불러옴 
		
		return "redirect:/lol/board/" + bmm.getPaging();	// 첫 번째 페이지로 리다이렉트
	}
	
	/**
	 * 게시판 페이지에서 다음 버튼을 눌렀을 때 이벤트 처리
	 * - bmm을 이용해 새로 가져올 글의 범위와 prev, next 버튼의 활성화 여부를 계산해서 세션에 넣는다.
	 *   이후 게시판 페이지로 리다이렉트한다.
	 */
	@RequestMapping("/board/next")
	public String lolNextBoard() {
		bmm.next();											// bmm을 다음으로 넘김
		
		return "redirect:/lol/board/" + bmm.getPaging();	// 첫 번째 페이지로 리다이렉트
	}

	/**
	 * 게시판 페이지에서 이전 버튼을 눌렀을 때 이벤트 처리
	 * - bmm을 이용해 새로 가져올 글의 범위와 prev, next 버튼의 활성화 여부를 계산해서 세션에 넣는다.
	 *   이후 게시판 페이지로 리다이렉트한다.
	 */
	@RequestMapping("/board/prev")
	public String lolPrevBoard() {
		bmm.prev();											// bmm을 이전으로 넘김
		
		return "redirect:/lol/board/" + bmm.getPaging();	// 첫 번째 페이지로 리다이렉트
	}
	
	@RequestMapping("/board/{page}")
	public String lolBoardPaging(@PathVariable("page") int page, RedirectAttributes rdAttributes, Model model) {
		if (bmm.getLimit() - page*10 < -10)	{											// URL로 비활성화된 페이지로 접근하면 다시 첫 페이지로 이동하도록 함
			rdAttributes.addFlashAttribute("error", new Exception("존재하지 않는 게시글 페이지입니다."));
			return "redirect:/lol/board/" + bmm.getPaging();	
		}

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
	public String lolFindBoard(@Valid @ModelAttribute("searchForm") LSearchForm form, BindingResult br, Model model) {
		if (form.getCheckRadio().equals("detail"))							// 어떤 옵션을 선택했는지 체크해서 적절한 Service, mapper를 통해 DB 접근을 통해 검색된 글 리스트를 받아 옴
			lgroupList = lgServ.readAllByDetail(form.getFindDetail());
		else if (form.getCheckRadio().equals("groupName"))
			lgroupList = lgServ.readAllByGroupName(form.getFindDetail());
		else
			lgroupList = lgServ.readAllByOwnerName(form.getFindDetail());
		
		bmm = new BoardMinMax(lgroupList.size());							// 받아 온 글 리스트를 가지고 새로운 bmm객체를 생성
		
		model.addAttribute("lgroupList", lgroupList);			
		model.addAttribute("bmm", bmm);

		return "redirect:/lol/board/" + bmm.getPaging();	// 첫 번째 페이지로 리다이렉트
	}

	@PostMapping(value="/board/newPost")
	public String newPost(@Valid @ModelAttribute("post") LGroupDTO lgroupDto, BindingResult br, HttpServletRequest request, RedirectAttributes rdAttributes) {
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

	@PostMapping("/board/delete")
	public String deletePost(@RequestParam("lgroupId") int lgroupId, @RequestParam("ownerUid") String ownerUid, Model model, HttpServletRequest request, RedirectAttributes rdAttributes) {
		if (!ownerUid.equals(((AuthInfo)model.getAttribute("authInfo")).getUid())) {
			rdAttributes.addFlashAttribute("error", new Exception("현재 계정과 작성한 계정이 달라 삭제할 수 없습니다."));
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
	public String lolBoardDetail(@PathVariable(value="lgroupId") int lgroupId, HttpServletRequest request, RedirectAttributes rdAttributes, Model model) {
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
		
		List<List<String>> matches = new ArrayList<>();
		for (LApplyDTO accepted : acceptedList) {
			String summonerURL = "https://kr.api.riotgames.com/lol/summoner/v4/summoners/by-name/" + accepted.getLcharName().replaceAll(" ", "") + "?api_key=" + api.getLOL_API_KEY();

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

					/*
					 * 계정의 전적을 받아옴
					 */
					String matchURL = "https://asia.api.riotgames.com/lol/match/v5/matches/by-puuid/" + summonerDto.getPuuid() + "/ids?start=0&count=15&api_key=" + api.getLOL_API_KEY();
					getRequest = new HttpGet(matchURL);
					response = client.execute(getRequest);
					if (response.getStatusLine().getStatusCode() == 200) {
						handler = new BasicResponseHandler();
						body = handler.handleResponse(response);
						matches.add(Arrays.asList(body.substring(2, body.length()-2).split("\",\"")));		// match_id를 리스트 형태로 변환해 다시 리스트에 넣는다.
					}
					else {
						rdAttributes.addFlashAttribute("error", new Exception("API 접속 오류(Match) : " + response.getStatusLine().getStatusCode()));
						return "redirect:" + request.getHeader("Referer");
					}
				} else {
					rdAttributes.addFlashAttribute("error", new Exception("API 접속 오류(Summoner) : " + response.getStatusLine().getStatusCode()));
					return "redirect:" + request.getHeader("Referer");
				}
			} catch (Exception e) {
				model.addAttribute("error", new RuntimeException("오류가 발생했습니다. 지속적으로 발생할 경우 관리자에게 문의해 주세요."));
			}
		}
		model.addAttribute("matches", matches);
		
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
	public String lolSubmitToGroup(@Valid @ModelAttribute("applyForm") LApplyDTO lapplyDto, BindingResult br, HttpServletRequest request, RedirectAttributes rdAttributes, Model model, HttpSession session) {
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
	public String findSummoner(Model model) {
		model.addAttribute("summoner", new SummonerDTO());	// LOL계정 정보를 담을 폼을 모델에 저장
		return "/lol/findSummoner";
	}

	@PostMapping("/findSummoner")
	public String findSummoner(@Valid @ModelAttribute("summoner") SummonerDTO summoner, BindingResult br, HttpServletRequest request, RedirectAttributes rdAttributes, Model model) {
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
	public String addSummoner(@Valid @ModelAttribute("summoner") SummonerDTO summonerDto, BindingResult br, Model model, HttpServletRequest request, RedirectAttributes rdAttributes) {
		LCharDTO lcharDto = new LCharDTO(((AuthInfo)model.getAttribute("authInfo")).getUid(), summonerDto.getName());	// 새로운 LOL 계정 정보 생성
		try {
			lcServ.create(lcharDto);					// 아이디를 계정에 연동
		} catch(AlreadyExistedLCharNameException aelne) {
			rdAttributes.addFlashAttribute("error", aelne);
		}
		return "redirect:/user/myPage";				// 마이페이지로 리다이렉트
	}
	
	@PostMapping("/deleteSummoner")
	public String deleteSummoner(@RequestParam(value="lcharName") String lcharName, HttpServletRequest request, RedirectAttributes rdAttributes) {
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
	@PostMapping("/charDetail")
	public String lolCharDetail(@RequestParam("lcharName") String lcharName, @RequestParam("matches") List<String> matches, HttpServletRequest request, RedirectAttributes rdAttributes, Model model) {
		matches.add(0, matches.remove(0).substring(1));
		matches.add(matches.size()-1, matches.remove(matches.size()-1).substring(0, 13));
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
				
				/*
				 * 계정의 티어를 받아옴
				 */
				String leagueURL = "https://kr.api.riotgames.com/lol/league/v4/entries/by-summoner/" + summonerDto.getId() + "?api_key=" + api.getLOL_API_KEY();
				getRequest = new HttpGet(leagueURL);
				response = client.execute(getRequest);
				if (response.getStatusLine().getStatusCode() == 200) {
					handler = new BasicResponseHandler();
					body = handler.handleResponse(response);
					
					if (!body.equals("[]")) {
						LeagueDTO leagueDto = objectMapper.readValue(body.substring(1, body.length()-1), LeagueDTO.class);	// JSON 응답을 DTO로 바꾸는 작업
						model.addAttribute("leagueDto", leagueDto);
					}
					else {
						model.addAttribute("leagueDto", null);
					}
					
					List<MatchDTO> matchList = new ArrayList<>();	// 모든 소환사의 협곡 게임 정보
					for (int i = 0; i < matches.size(); i++) {
						String matchURL = "https://asia.api.riotgames.com/lol/match/v5/matches/" + matches.get(i) + "?api_key=" + api.getLOL_API_KEY();
						getRequest = new HttpGet(matchURL);
						response = client.execute(getRequest);
						if (response.getStatusLine().getStatusCode() == 200) {
							handler = new BasicResponseHandler();
							body = handler.handleResponse(response);
							
							/*
							 * 많고 많은 JSON 응답 중 관심있게 볼 것은 ParticipantDTO의 리스트 중 검색 계정의 정보이므로
							 * InfoDTO와 그 내부의 ParticipantDTO만 따로 구해서 계정 ID와 일치하는 정보만 뽑아낸다.
							 * 뽑아낸 정보는 랭크/일반게임 여부에 따라 위에서 정의한 리스트에 넣는다.
							 */
							MatchDTO matchDto = objectMapper.readValue(body, MatchDTO.class);
							InfoDTO infoDto = matchDto.getInfo();
							infoDto.setMyInfoByLcharName(lcharDto.getLcharName());
							
							if (infoDto.getQueueId() == 430) {
								infoDto.getMyInfo().setGameType("노말");
								matchList.add(matchDto);
							}
							else if (infoDto.getQueueId() == 420) {
								infoDto.getMyInfo().setGameType("솔랭");
								matchList.add(matchDto);
							}
						}
						else {
							rdAttributes.addFlashAttribute("error", new Exception("API 접속 오류(Match) : " + response.getStatusLine().getStatusCode()));
							return "redirect:" + request.getHeader("Referer");
						}
					}
					model.addAttribute("matchList", matchList);
					
					SummonerMatchDetails matchDetail = new SummonerMatchDetails();
					matchDetail.setTotalGames(matchList.size());
					for (MatchDTO matchDto : matchList) {
						ParticipantDTO myInfo = matchDto.getInfo().getMyInfo();
						matchDetail.addTotalKills(myInfo.getKills());
						matchDetail.addTotalAssists(myInfo.getAssists());
						matchDetail.addTotalDeaths(myInfo.getDeaths());
						matchDetail.addTotalDamageToChampion(myInfo.getTotalDamageDealtToChampions());
						matchDetail.addTotalDamageTaken(myInfo.getTotalDamageTaken());
					}
					
					model.addAttribute("matchDetail", matchDetail);
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
			model.addAttribute("error", new RuntimeException("오류가 발생했습니다. 지속적으로 발생할 경우 관리자에게 문의해 주세요."));
		}

		return "/lol/charDetail";		
	}
	
	@GetMapping("/matchDetail/{matchId}")
	public String matchDetail(@PathVariable("matchId") String matchId, HttpServletRequest request, RedirectAttributes rdAttributes, Model model) {
		String matchURL = "https://asia.api.riotgames.com/lol/match/v5/matches/" + matchId + "?api_key=" + api.getLOL_API_KEY();
		try {
			HttpClient client = HttpClientBuilder.create().build();
			HttpGet getRequest = new HttpGet(matchURL);
			HttpResponse response = client.execute(getRequest);
			
			if (response.getStatusLine().getStatusCode() == 200) {
				ResponseHandler<String> handler = new BasicResponseHandler();
				String body = handler.handleResponse(response);
				
				MatchDTO match = objectMapper.readValue(body, MatchDTO.class);
				
				model.addAttribute("match", match);
				
				DateFormat format = new SimpleDateFormat("mm분 ss초");
				String gameDuration = format.format(match.getInfo().getGameEndTimestamp()-match.getInfo().getGameStartTimestamp());
				model.addAttribute("gameDuration", gameDuration);
			}
			else {
				rdAttributes.addFlashAttribute("error", new Exception("API 접속 오류(Match) : " + response.getStatusLine().getStatusCode()));
				return "redirect:" + request.getHeader("Referer");
			}
		} catch(Exception e) {
			model.addAttribute("error", new RuntimeException("오류가 발생했습니다. 지속적으로 발생할 경우 관리자에게 문의해 주세요."));
		}
		return "/lol/matchDetail";
	}
	
	@PostMapping("/acceptApply")
	public String acceptApply(@RequestParam("lapplyId") int lapplyId, @RequestParam("lgroupId") int lgroupId, HttpServletRequest request, RedirectAttributes rdAttributes) {
		LApplyDTO applyForm = new LApplyDTO(lapplyId, lgroupId, "ACCEPTED");
		try {
			laServ.updateResult(applyForm);
		} catch(TooManyApplyException tmae) {
			rdAttributes.addFlashAttribute("error", tmae);
		}
		return "redirect:" + request.getHeader("Referer");
	}
	
	@PostMapping("/denyApply")
	public String denyApply(@RequestParam("lapplyId") int lapplyId, @RequestParam("lgroupId") int lgroupId, HttpServletRequest request, RedirectAttributes rdAttributes) {
		LApplyDTO applyForm = new LApplyDTO(lapplyId, lgroupId, "DENIED");
		try {
			laServ.delete(applyForm);
		} catch(NoSuchApplyException nsae) {
			rdAttributes.addFlashAttribute("error", nsae);
		}
		return "redirect:" + request.getHeader("Referer");
	}
}