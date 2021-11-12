package com.project.dnflol.Controller;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
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

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.dnflol.DTO.DAdventureDTO;
import com.project.dnflol.DTO.DApplyDTO;
import com.project.dnflol.DTO.DCharDTO;
import com.project.dnflol.DTO.DGroupDTO;
import com.project.dnflol.DTO.UserDTO;
import com.project.dnflol.Exception.AlreadyExistedApplyException;
import com.project.dnflol.Exception.AlreadyExistedLCharNameException;
import com.project.dnflol.Exception.NoSuchCharException;
import com.project.dnflol.Exception.NoSuchGroupException;
import com.project.dnflol.Exception.TooManyApplyException;
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
	
	@ModelAttribute("applyForm")
	public DApplyDTO applyForm() {
		return new DApplyDTO();
	}
	
	@ModelAttribute("post")
	public DGroupDTO post() {
		return new DGroupDTO();
	}
	
	/**
	 * 메인 게시판 페이지
	 * - 글을 최근 순서로 10개 단위 10페이지를 게시하고 이전/다음 버튼으로 다른 범위에 있는 글을 가져올 수 있음.
	 * - 글을 일일히 찾기 싫은 사람을 위해 페이지 하단에는 게시판 검색을 위한 검색종류 선택 체크박스, 입력 폼, 검색버튼이 존재함.
	 *   검색 버튼을 누르면 입력 폼에 입력된 정보와 체크박스에서 선택된 정보를 가지고 비슷한 글을 찾아서 그 글들을 보여줌.
	 */
	@RequestMapping("/board")
	public ModelAndView dnfBoard() {
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
	public ModelAndView dnfNextBoard() {
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
	public ModelAndView dnfPrevBoard() {
		ModelAndView mv = new ModelAndView();
		bmm.prev();											// bmm을 이전으로 넘김
		
		mv.setViewName("redirect:/dnf/board/" + bmm.getPaging());	// 첫 번째 페이지로 리다이렉트
		return mv;
	}
	
	@RequestMapping("/board/{page}")
	public String dnfBoardPaging(Model model, @PathVariable("page") int page) {
		if (bmm.getLimit() - page*10 < -10)												// URL로 비활성화된 페이지로 접근하면 다시 첫 페이지로 이동하도록 함
			return "redirect:/dnf/board/" + bmm.getPaging();

		int startIdx = bmm.getMin()+(page-1)*10;										// 현재 페이지에서 볼 수 있는 글의 시작, 끝 인덱스 계산
		int endIdx = Math.min(bmm.getLimit(), startIdx+10);
		
		model.addAttribute("dgroupList", dgroupList.subList(startIdx, endIdx));			// 현재 페이지에서 볼 수 있는 글만 담아서 모델에 담음
		model.addAttribute("bmm", bmm);
		
		List<DCharDTO> mydnfChars = dcServ.readAllByUid(((AuthInfo)model.getAttribute("authInfo")).getUid());	// LoL 연동 계정 정보
		model.addAttribute("mydnfChars", mydnfChars);
		
		return "/dnf/board";
	}
	
	/**
	 * 게시판 페이지에서 상세정보로 찾기 버튼을 눌렀을 때 이벤트 처리
	 * 이후 게시판 페이지로 리다이렉트한다.
	 */
	@PostMapping("/findBoard")
	public ModelAndView dnfFindBoard(Model model, @Valid @ModelAttribute("searchForm") LSearchForm form, BindingResult br) {
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
	
	@PostMapping(value="/board/newPost")
	public String newPost(HttpServletRequest request, RedirectAttributes rdAttributes, @Valid @ModelAttribute("post") DGroupDTO dgroupDto, BindingResult br) throws Exception {
		if (br.hasErrors())													// 필요한 정보가 정한 폼에 맞지 않으면 이전 단계로 돌아감
			return "redirect:/dnf/board/newPostGET";

		if (dgroupDto.getDgroupOwner() == null)								
			return "redirect:/dnf/board/newPostGET";
		
		if (dgroupDto.getDgroupType() == 1)						// 선택한 게임 타입에 따라 최대 인원수를 정함
			dgroupDto.setDgroupMax(8);
		else if (dgroupDto.getDgroupType() == 3)
			dgroupDto.setDgroupMax(16);
		else
			dgroupDto.setDgroupMax(12);
		
		try {
			DCharDTO dcharDto = dcServ.readByNametocid(dgroupDto.getDgroupOwner());
			String OwnerName= dcharDto.getDcname();
			dgroupDto.setDgroupOwnerName(OwnerName);
			DateFormat format = new SimpleDateFormat("yy.MM.dd kk:mm:ss");		// '연.월.일 시간:분:초' 형식으로 시간을 구함
			String dateStr = format.format(Calendar.getInstance().getTime());
			dgroupDto.setDgroupDate(dateStr);

			dgServ.create(dgroupDto);											// DB접근을 통해 게시글 생성 
			
			int dgroupId = dgServ.readDgroupId(dgroupDto);					// 생성한 게시글의 고유 번호를 받아 옴
			
			DApplyDTO ownerApply = new DApplyDTO(dgroupDto.getDgroupOwner(), dgroupDto.getDgroupOwnerName(), dgroupId, dgroupDto.getDgroupName());
			daServ.createToAccepted(ownerApply);								// 게시물을 생성한 사람을 수락 상태로 초대 추가함
			
			return "redirect:/dnf/boardDetail/" + dgroupId;						// 생성한 게시글로 리다이렉트
		} catch(Exception e) {
			rdAttributes.addFlashAttribute("error", e);
			return "redirect:" + request.getHeader("Referer");
		}
	}
	
	@GetMapping("/board/delete/{dgroupId}")
	public String deletePost(HttpServletRequest request, RedirectAttributes rdAttributes, Model model, @PathVariable("dgroupId") int dgroupId) {
		if (((String)model.getAttribute("ownerUid")).equals(((AuthInfo)model.getAttribute("authInfo")).getUid())) {
			rdAttributes.addFlashAttribute("error", new Exception("작성한 계정과 달라 삭제할 수 없습니다."));
			return "redirect:/lol/board";
		}
		
		try {
			dgServ.deleteById(dgroupId);		
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
	@RequestMapping("/boardDetail/{dgroupId}")
	public String dnfBoardDetail(Model model, HttpServletRequest request, RedirectAttributes rdAttributes, @PathVariable(value="dgroupId") int dgroupId) {
		DGroupDTO dgroupDto = null;
		try {
			dgroupDto = dgServ.readById(dgroupId);										// 게시글 세부 정보
		} catch (NoSuchGroupException nsge) {
			rdAttributes.addFlashAttribute("error", nsge);
			return "redirect:/lol/board";
		}
		
		List<DApplyDTO> acceptedList = daServ.readAllAcceptedByGroupId(dgroupId);				// 수락된 멤버 목록
		model.addAttribute("dgroupDto", dgroupDto);
		model.addAttribute("acceptedList", acceptedList);

		List<DCharDTO> myAppliedChars = dcServ.readAllAppliedByUid(((AuthInfo)model.getAttribute("authInfo")).getUid(), dgroupId);			// 내 LOL 계정 중 해당 게시글에 이미 신청한 계정
		List<DCharDTO> myNotAppliedChars = dcServ.readAllNotAppliedByUid(((AuthInfo)model.getAttribute("authInfo")).getUid(), dgroupId);	// 내 LOL 계정 중 해당 게시글에 아직 신청하지 않은 계정
		model.addAttribute("myAppliedChars", myAppliedChars);
		model.addAttribute("myNotAppliedChars", myNotAppliedChars);
		
		try {
			DCharDTO dcharDto = dcServ.readByName(dgroupDto.getDgroupOwnerName());
			model.addAttribute("ownerUid", dcharDto.getUid());
			return "/dnf/boardDetail";
		} catch (NoSuchCharException nsce) {
			rdAttributes.addFlashAttribute("error", nsce);
			return "redirect:/lol/board";
		}
	}
	
	/**
	 * 그룹 신청 페이지에서 아이디를 클릭하면 신청을 넣고 게시판 페이지로 돌아감
	 */
	@PostMapping("/submit")
	public String dnfSubmitToGroup(Model model, HttpServletRequest request, RedirectAttributes rdAttributes, HttpSession session, @Valid @ModelAttribute("applyForm") DApplyDTO dapplyDto, BindingResult br) {
		if (br.hasErrors()) {
			return "redirect:/dnf/board";
		}
		try {
			daServ.create(dapplyDto);						// DB접근을 통해 신청 정보 생성
		} catch(AlreadyExistedApplyException leae) {
			rdAttributes.addFlashAttribute("error", leae);
			return "redirect:" + request.getHeader("Referer");
		}
		
		return "redirect:/dnf/boardDetail/" + dapplyDto.getDgroupId();
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
	public String addcharacter(Model model, RedirectAttributes rdAttributes, @Valid @ModelAttribute("character") DAdventureDTO dadventureDto, BindingResult br) {
		DCharDTO dcharDto = new DCharDTO(((AuthInfo)model.getAttribute("authInfo")).getUid(), dadventureDto.getCharacterName(), dadventureDto.getCharacterId(), dadventureDto.getServerId());    // 새로운 던 캐릭 정보 생성
		try {
			dcServ.create(dcharDto);					// 아이디를 계정에 연동
		} catch(AlreadyExistedLCharNameException aelne) {
			rdAttributes.addFlashAttribute("error", aelne);
		}
		return "redirect:/user/myPage";				// 마이페이지로 리다이렉트
	}
	
	@GetMapping("/deletecharacter/{characterName}")
	public String deletecharacter(HttpServletRequest request, RedirectAttributes rdAttributes, @PathVariable(value="characterName") String characterName) {
		try {
			dcServ.deleteByName(characterName);				// DB에서 해당 던 캐릭터 삭제
		} catch (NoSuchCharException nsce) {
			rdAttributes.addFlashAttribute("error", nsce);
			return "redirect:" + request.getHeader("Referer");
		}
		return "redirect:/user/myPage";				// 마이페이지로 리다이렉트
	}
	
	/**
	 * 계정 전적 정보를 볼 수 있는 페이지
	 */
	@RequestMapping("/charDetail/{dcname}")
	public String dnfCharDetail(Model model, HttpServletRequest request, RedirectAttributes rdAttributes, @PathVariable("dcname") String dcname) {
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		
		DCharDTO dcharDto = null;
		try {
			dcServ.readByName(dcname);
		} catch (NoSuchCharException nsce) {
			rdAttributes.addFlashAttribute("error", nsce);
			return "redirect:" + request.getHeader("Referer");
		}
		model.addAttribute("dcharDto", dcharDto);
		
		String url = "?api_key=" + api.getDNF_API_KEY();
		
		try {
			HttpClient client = HttpClientBuilder.create().build();
			HttpGet getRequest = new HttpGet(url);
			HttpResponse response = client.execute(getRequest);

			if (response.getStatusLine().getStatusCode() == 200) {
				ResponseHandler<String> handler = new BasicResponseHandler();
				String body = handler.handleResponse(response);
				System.out.println(body);
				
				// -- 캐릭터의 상세 정보를 담아서 model에 넣음 --
			}
			
		} catch(Exception e) {
			model.addAttribute("exception", e);
		}

		return "/dnf/charDetail";	
	}
	
	@RequestMapping("/acceptApply/{dapplyId}&{dgroupId}")
	public String acceptApply(HttpServletRequest request, RedirectAttributes rdAttributes, @PathVariable("dapplyId") int dapplyId, @PathVariable("dgroupId") int dgroupId) {
		DApplyDTO applyForm = new DApplyDTO(dapplyId, dgroupId, "ACCEPTED");
		try {
			daServ.updateResult(applyForm);
		} catch(TooManyApplyException tmae) {
			rdAttributes.addFlashAttribute("error", tmae);
		}
		return "redirect:" + request.getHeader("Referer");
	}
	
	@RequestMapping("/denyApply/{dapplyId}&{dgroupId}")
	public String denyApply(HttpServletRequest request, RedirectAttributes rdAttributes, @PathVariable("dapplyId") int dapplyId, @PathVariable("dgroupId") int dgroupId) {
		DApplyDTO applyForm = new DApplyDTO(dapplyId, dgroupId, "DENIED");
		daServ.updateResult(applyForm);
		return "redirect:" + request.getHeader("Referer");
	}
}