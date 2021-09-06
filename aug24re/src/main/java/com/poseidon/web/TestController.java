package com.poseidon.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.poseidon.util.Util;

import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

/*
 * 2021-08-25 testController
 * 
 * user -> RD -> Controller -> Service -> DAO -> mybatis -> DB
 * 						DTO(VO)
 * user <---------	View(jsp)			
 * 
 * 
 * 선생님 오늘 배운 내용을 정리해보려고 하는데
데이터 통신 user => Controller -> Service -> DAO -> DB
1. pom.xml에 관련 maven을 다 입력한다.
2. src/main/resources에 spring, mybatis(mappers, config)을 생성한다
3. spring에 data-context.xml을 생성하고 sqlSession 설정 및 dataSource 
	객체를 생성한다.
4. mybatisConfig에 내가 만든 클래스를 mybatis가 사용할 수 있도록 
	등록해준다.(TestDTO)
5. testMapper에 sql문을 생성한다. id는 boardList로 resultType은 
	TestDTO로 만든다. 그리고 해당 sql문을 갖고 있는 mapper이름을 
	test라고 지정한다.
6. TestDTO를 생성한다. getter와 setter를 설정한다.
7. TestDAO를 생성한다. 실제 데이터를 불러오도록 한다. 
	sqlSession을 이용하여 test속 boardList라고 생성된 
	sql문을 통해 데이터베이스와 통신한다.
8. TestService.java를 생성하고 데이터베이스와 통신한 후 받아온 
	값을 담아준다.
9. TestController.java에서  맵핑작업(어느 페이지에 대한 컨트롤러인지)을 
	해주고, TestService.java에 있는 boardList를 잡아 ModelAndView에 
	데이터를 리스트로 담아서 반환값으로 지정해줍니다.
10. board.jsp에서 컨트롤러에서 받은 값을 표현해줍니다.

이런식으로 이해하는게 맞나요?

 * 
 * 
 * 
 * 								
 * 
 */
@Controller
public class TestController {
	@Autowired
	private TestService testService;
	@Autowired
	private Util util;

	@RequestMapping("/menu")
	public ModelAndView menu() {
		ModelAndView mv = new ModelAndView("menu");
		List<HashMap<String, Object>> categoryList = testService.categoryList();
		mv.addObject("categoryList", categoryList);
		return mv;
	}
	
	// 맵핑작업
	@RequestMapping(value = "/board", method = RequestMethod.GET)
	public ModelAndView board(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("board");//jsp
		
		int sb_cate = 1;
		if(request.getParameter("sb_cate") != null &&
				util.str2Int(request.getParameter("sb_cate"))) {
			sb_cate =  util.str2Int2(request.getParameter("sb_cate"));
		}
		//데이터베이스로 보낼 map만들기
		Map<String, Object> sendMap = new HashMap<String, Object>();
		sendMap.put("sb_cate", sb_cate);
		
		///////////////////////////////////
		//페이지네이션인포 만들어주겠습니다.
		PaginationInfo paginationInfo = new PaginationInfo();
		int pageNo = 1;//현재 페이지 번호
		int listScale = 10;//한 페이지에 나올 글 수 
		int pageScale = 10;// 페이지 개수 
		
		if(request.getParameter("pageNo") != null) {
			pageNo = util.str2Int2(request.getParameter("pageNo"));
		}
		
		paginationInfo.setCurrentPageNo(pageNo);
		paginationInfo.setRecordCountPerPage(listScale);
		paginationInfo.setPageSize(pageScale);
		
		int startPage = paginationInfo.getFirstRecordIndex();//시작페이지
		int lastPage = paginationInfo.getRecordCountPerPage();//마지막페이지
		
		sendMap.put("startPage", startPage);
		sendMap.put("lastPage", lastPage);
		
		//////////////////////////////////////////////////////
		
		//보드 리스트 가져오기
		List<TestDTO> boardList = testService.boardList(sendMap);
		//카테고리 리스트 가져오기
		//List<HashMap<String, Object>> categoryList = testService.categoryList();
		//카테고리 찍기
		//String category = testService.getCategory(sb_cate);
		//String category = (String) categoryList.get(0).get("sc_category");
		//for (HashMap<String, Object> list : categoryList) {
		//	if((int)(list.get("sc_no")) == sb_cate) {//이부분 수정해야합니다.
		//		category = (String) list.get("sc_category");
		//	}
		//}
		
		/////////////////////////////////////
		int totalList = testService.totalList(sb_cate);//전체 글의 수
		//이거 저 위에 testService.boardList(sendMap);에서 가져오게 하면
		//데이터베이스에 한 번만 갔다 와도 됩니다.
		System.out.println(boardList.get(0).getSc_category());
		paginationInfo.setTotalRecordCount(totalList);//전체 글 수 저장
		mv.addObject("paginationInfo", paginationInfo);//페이징도 보내기
		mv.addObject("pageNo", pageNo);//현 페이지 번호
		mv.addObject("totalList", totalList);//전체 글 수 
		////////////////////////////////////
		
		
		mv.addObject("list", boardList);//보드 내용
		mv.addObject("category", boardList.get(0).getSc_category());//if문 추가해주세요.
		//mv.addObject("categoryList", categoryList);//새로 추가된 카테고리 리스트
		mv.addObject("sb_cate", sb_cate);
		return mv;
	}
	
	//write 글쓰기
	//@RequestMapping(value="/write", method=RequestMethod.GET)
	@GetMapping("/write")
	public String write(HttpServletRequest request) {
		//@RequestParam("sb_cate") int sb_cate
		//위에서 잡은 sb_cate를 MV에 붙여서 write.jsp로 보내기
		//그럼 form태그 안에서 붙여서 글쓰기 버튼을 눌렀을때
		//sb_cate값까지 가져갈 수 있음.		
		//로그인 한 사람이 확인
		HttpSession session = request.getSession();
		
		if(session.getAttribute("sm_name") != null 
				&& session.getAttribute("sm_id") != null ) {
			return "write";//정상 로그인 한 사람이라면 글쓰기 화면으로	
		}else {
			return "redirect:/";
		}
		
		
	}
	//HttpServletRequest request
	//@RequestParam("title") String title
	//DTO방식 두가지 다 시연하기
	@PostMapping("/write")
	public String write2(TestDTO testDTO, HttpServletRequest request) {
		//session에 있는 id값 넣어주기
		HttpSession session = request.getSession();
		
		if(session.getAttribute("sm_name") != null 
				&& session.getAttribute("sm_id") != null) {
			//정상 로그인 한 사람
			testDTO.setSm_id((String) session.getAttribute("sm_id"));
			//category도
			//testDTO.setSb_cate(2); 
			//1은 자유게시판
			//출력 찍어보기			
			//System.out.println(testDTO.getSb_cate());			
			//System.out.println(testDTO.getSb_title());
			//System.out.println(testDTO.getSb_content());
			//service -> dao -> mybatis -> DB
			testService.write(testDTO);
			//board 메소드 다시 실행시켜
			return "redirect:/board?sb_cate="+testDTO.getSb_cate();
		}else {
			//로그인 안 한 사람
			return "redirect:/";
		}
	}

	
	@GetMapping("/detail")
	public ModelAndView detail(@RequestParam("sb_no") int sb_no) {
		System.out.println("sb_ : " + sb_no);
		//데이터베이스에서 값 가져오기
		//SELECT * FROM boardview WHERE bno=bno
		//TestDTO
		TestDTO dto = testService.detail(sb_no);
		//MV
		ModelAndView mv = new ModelAndView("detail");
		//값 붙이기
		mv.addObject("dto", dto);
		
		return mv;
	}
	
	@GetMapping("/delete")
	public String delete(@RequestParam("sb_no") int sb_no) {
		//여기 수정 =  bno -> sb_no로 수정
		//데이터베이스로 bno보내기
		int result = testService.delete(sb_no);
		System.out.println("결과 : "+ result);
		return "redirect:/board";	//sb_cate받아서 해당 경로로 이동하게			
	}
	
	@GetMapping("/update")
	public ModelAndView update(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("update");
		//글 번호 가져오기
		int sb_no = Integer.parseInt(request.getParameter("sb_no"));
		//System.out.println("sb_no : " + sb_no);
		
		TestDTO dto = testService.detail(sb_no);
		mv.addObject("dto", dto);
		
		return mv;
	}
	
	@PostMapping("/update")
	public String update(TestDTO dto) {//name이 똑같다면 거기에 저장
		//System.out.println(dto.getBtitle());
		//System.out.println(dto.getBcontent());
		//System.out.println(dto.getBno());
		
		//데이터 베이스로 보내서 저장시켜주세요.
		int result = testService.update(dto);
		System.out.println("수정결과 : " + result);
		return "redirect:/detail?sb_no="+dto.getSb_no();
	}
	
	
	
	
}
