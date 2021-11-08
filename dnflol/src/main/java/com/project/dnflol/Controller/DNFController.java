package com.project.dnflol.Controller;


import java.util.List;

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
import com.project.dnflol.DTO.DAdventureDTO;
import com.project.dnflol.DTO.DCharDTO;
import com.project.dnflol.DTO.DGroupDTO;
import com.project.dnflol.DTO.TimeLineDTO;
import com.project.dnflol.DTO.UserDTO;
import com.project.dnflol.Service.DApplyService;
import com.project.dnflol.Service.DCharService;
import com.project.dnflol.Service.DGroupService;
import com.project.dnflol.Service.UserService;
import com.project.dnflol.util.APIKey;
import com.project.dnflol.util.AuthInfo;
import com.project.dnflol.util.BoardMinMax;
import com.project.dnflol.util.DSearchForm;
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
	List<DGroupDTO> dgroupList;
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
	public DSearchForm searchForm() {
		return new DSearchForm();
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
		bmm = new BoardMinMax(dgServ.readMaxCount());		// 새로운 bmm 객체를 만들어 둠
		dgroupList = dgServ.readLimitList(bmm);				// 최신 글 리스트(100개)를 불러옴 
		
		mv.setViewName("redirect:/dnf/board/" + bmm.getPaging());	// 첫 번째 페이지로 리다이렉트
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
		
		mv.setViewName("redirect:/dnf/board/" + bmm.getPaging());	// 첫 번째 페이지로 리다이렉트
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
		
		mv.setViewName("redirect:/dnf/board/" + bmm.getPaging());	// 첫 번째 페이지로 리다이렉트
		return mv;
	}
	
	@RequestMapping("/board/{page}")
	public String lolBoardPaging(Model model, @PathVariable("page") int page) {
		if (bmm.getLimit() - page*10 < -10)												// URL로 비활성화된 페이지로 접근하면 다시 첫 페이지로 이동하도록 함
			return "redirect:/dnf/board/" + bmm.getPaging();

		int startIdx = bmm.getMin()+(page-1)*10;										// 현재 페이지에서 볼 수 있는 글의 시작, 끝 인덱스 계산
		int endIdx = Math.min(bmm.getLimit(), startIdx+10);
		
		model.addAttribute("dgroupList", dgroupList.subList(startIdx, endIdx));			// 현재 페이지에서 볼 수 있는 글만 담아서 모델에 담음
		model.addAttribute("bmm", bmm);
		
		return "/dnf/board";
	}
	
	@GetMapping("/findcharacter")
	public ModelAndView findcharacter(Model model) {
		ModelAndView mv = new ModelAndView();
		model.addAttribute("character", new DAdventureDTO());	
		mv.setViewName("/dnf/findcharacter");
		return mv;
	}

	@PostMapping("/findcharacter") 
	public ModelAndView findcharacter(Model model, @ModelAttribute("character") DAdventureDTO dadventureDTO) {
		String requestURL = "https://api.neople.co.kr/df/servers/"+dadventureDTO.getServerId()+"/characters?characterName="+ dadventureDTO.getCharacterName() + "&limit=30&wordType=full&apikey=" + api.getDNF_API_KEY();
		model.addAttribute("character", new DAdventureDTO());
		
		ModelAndView mv = new ModelAndView();
		try {
			HttpClient client = HttpClientBuilder.create().build();
			HttpGet getRequest = new HttpGet(requestURL);
			HttpResponse response = client.execute(getRequest);
			
			if (response.getStatusLine().getStatusCode() == 200) {
				ResponseHandler<String> handler = new BasicResponseHandler();
				String body = handler.handleResponse(response);
				
				body = body.substring(body.indexOf("rows")+6,body.length()-1);
				objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
				List<DAdventureDTO> characters = objectMapper.readValue(body, objectMapper.getTypeFactory().constructCollectionType(List.class, DAdventureDTO.class));
				model.addAttribute("characters", characters);
			}
		} catch(Exception e) {
			mv.setViewName("redirect:/secrutiry/denied");
			return mv;
		}
		
		mv.setViewName("/dnf/findcharacter");					// 이전 상태로 되돌아감
		return mv;
	}
	
	@PostMapping("/addcharacter") 
	public String addcharacter(Model model, @Valid @ModelAttribute("character") DAdventureDTO dadventureDto, BindingResult br) {
		DCharDTO dcharDto = new DCharDTO(((AuthInfo)model.getAttribute("authInfo")).getUid(), dadventureDto.getCharacterName(), dadventureDto.getCharacterId(), dadventureDto.getServerId());    // 새로운 던 캐릭 정보 생성
		dcServ.create(dcharDto);					// 아이디를 계정에 연동
		
		return "redirect:/user/myPage";				// 마이페이지로 리다이렉트
	}
	
	@GetMapping("/deletecharacter/{characterName}")
	public String deletecharacter(@PathVariable(value="characterName") String characterName) {
		dcServ.deleteByName(characterName);				// DB에서 해당 던 캐릭터 삭제
		return "redirect:/user/myPage";				// 마이페이지로 리다이렉트
	}
	
	/**
	 * 게시판 페이지에서 상세정보로 찾기 버튼을 눌렀을 때 이벤트 처리
	 * 이후 게시판 페이지로 리다이렉트한다.
	 */
	@PostMapping("/findBoard")
	public ModelAndView lolFindBoard(Model model, @Valid @ModelAttribute("searchForm") LSearchForm form, BindingResult br) {
		if (form.getCheckRadio().equals("detail"))							// 어떤 옵션을 선택했는지 체크해서 적절한 Service, mapper를 통해 DB 접근을 통해 검색된 글 리스트를 받아 옴
			dgroupList = dgServ.readAllByDetail(form.getFindDetail());
		else if (form.getCheckRadio().equals("groupName"))
			dgroupList = dgServ.readAllByGroupName(form.getFindDetail());
		else
			dgroupList = dgServ.readAllByOwnerName(form.getFindDetail());
		
		bmm = new BoardMinMax(dgroupList.size());							// 받아 온 글 리스트를 가지고 새로운 bmm객체를 생성
		
		model.addAttribute("dgroupList", dgroupList);			
		model.addAttribute("bmm", bmm);

		ModelAndView mv = new ModelAndView();	
		mv.setViewName("redirect:/dnf/board/" + bmm.getPaging());		//첫 번째 페이지로 리다이렉트
		return mv;
	}
	
	@RequestMapping("/timeline")
	public ModelAndView test(Model model, @PathVariable("dcharName") String dcharName) {
		String requestURL = "https://api.neople.co.kr/df/servers/siroco/characters/"+ dcharName + "timeline?limit=100&code=201,507&apikey=" + api.getDNF_API_KEY();
		
		ModelAndView mv = new ModelAndView();
		try {
			HttpClient client = HttpClientBuilder.create().build();
			HttpGet getRequest = new HttpGet(requestURL);
			HttpResponse response = client.execute(getRequest);
			
			/*
			 * 
			 */
			if (response.getStatusLine().getStatusCode() == 200) {
				ResponseHandler<String> handler = new BasicResponseHandler();
				String body = handler.handleResponse(response);
				
				body = body.substring(body.indexOf("rows")+6,body.length()-2);
				objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
				List<TimeLineDTO> timeline = objectMapper.readValue(body, objectMapper.getTypeFactory().constructCollectionType(List.class, TimeLineDTO.class));
				// 
			}
		} catch(Exception e) {
			mv.setViewName("redirect:/secrity/denied");
			return mv;
		}
		
		mv.setViewName("/dnf/test");					
		return mv;
	}
}