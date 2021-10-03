package com.poseidon.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.poseidon.util.FileSave;
import com.poseidon.util.Util;
import com.poseidon.web.log.LogDTO;
import com.poseidon.web.log.LogService;

import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

@Controller
public class TestController {
	@Autowired
	private TestService testService;
	@Autowired
	private Util util;
	@Autowired
	private LogService logService;
	@Autowired
	private FileSave fileSave; 
	@Autowired
	private ServletContext servletContext;
	

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
		ModelAndView mv = new ModelAndView("board");// jsp

		int sb_cate = 1;
		if (request.getParameter("sb_cate") != null && util.str2Int(request.getParameter("sb_cate"))) {
			sb_cate = util.str2Int2(request.getParameter("sb_cate"));
		}
		Map<String, Object> sendMap = new HashMap<String, Object>();
		sendMap.put("sb_cate", sb_cate);

		PaginationInfo paginationInfo = new PaginationInfo();
		int pageNo = 1;
		int listScale = 10;
		int pageScale = 10;

		if (request.getParameter("pageNo") != null) {
			pageNo = util.str2Int2(request.getParameter("pageNo"));
		}

		paginationInfo.setCurrentPageNo(pageNo);
		paginationInfo.setRecordCountPerPage(listScale);
		paginationInfo.setPageSize(pageScale);

		int startPage = paginationInfo.getFirstRecordIndex();
		int lastPage = paginationInfo.getRecordCountPerPage();

		sendMap.put("startPage", startPage);
		sendMap.put("lastPage", lastPage);

		List<TestDTO> boardList = testService.boardList(sendMap);
		int totalList = testService.totalList(sb_cate);
		paginationInfo.setTotalRecordCount(totalList);
		mv.addObject("paginationInfo", paginationInfo);
		mv.addObject("pageNo", pageNo);
		mv.addObject("totalList", totalList);

		mv.addObject("list", boardList);
		if (boardList.size() > 0) {
			mv.addObject("category", boardList.get(0).getSc_category());
		}
		mv.addObject("sb_cate", sb_cate);

		// ip불러오기
		String ip = util.getUserIp(request);
		
		String target = "board?sb_cate=" + sb_cate;
		String data = "게시판 읽음";
		LogDTO log = null;

		HttpSession session = request.getSession();
		if (session.getAttribute("sm_id") != null) {// 로그인 했다면
			String id = (String) session.getAttribute("sm_id");
			log = new LogDTO(ip, target, id, data);// sl_id가 있음.
		} else {
			log = new LogDTO(ip, target, data);// sl_id가 없음.
		}
		logService.writeLog(log);

		return mv;
	}

	// write 글쓰기
	// @RequestMapping(value="/write", method=RequestMethod.GET)
	@GetMapping("/write")
	public String write(HttpServletRequest request) {
		// @RequestParam("sb_cate") int sb_cate
		// 위에서 잡은 sb_cate를 MV에 붙여서 write.jsp로 보내기
		// 그럼 form태그 안에서 붙여서 글쓰기 버튼을 눌렀을때
		// sb_cate값까지 가져갈 수 있음.
		// 로그인 한 사람이 확인
		HttpSession session = request.getSession();

		if (session.getAttribute("sm_name") != null && session.getAttribute("sm_id") != null) {
			return "write";// 정상 로그인 한 사람이라면 글쓰기 화면으로
		} else {
			return "redirect:/";
		}

	}

	@PostMapping("/write")
	public String write2(HttpServletRequest request, MultipartFile file) throws IOException {
		HttpSession session = request.getSession();

		if (session.getAttribute("sm_name") != null && session.getAttribute("sm_id") != null) {
			//데이터베이스로 보낼 dto만들기
			TestDTO testDTO = new TestDTO();
			testDTO.setSb_title(request.getParameter("sb_title"));
			testDTO.setSb_content(request.getParameter("sb_content"));
			testDTO.setSb_cate(util.str2Int2(request.getParameter("sb_cate")));
			
			//System.out.println("title : " + testDTO.getSb_title());
			//System.out.println("content : " + testDTO.getSb_content());
			//System.out.println("cate : " + testDTO.getSb_cate());
			
			//file/
			testDTO.setSb_orifile(file.getOriginalFilename());
			
			//실제로 파일을 저장시키기  -> 실제 저장된 파일 이름 -> 아래에
			//실제 경로
			String realPath = servletContext.getRealPath("resources/upfile/");
			String upfile = fileSave.save2(realPath, file);
			testDTO.setSb_file(upfile);
			
			//System.out.println("file : " + testDTO.getSb_orifile());
			//System.out.println("upfile : " + testDTO.getSb_file());
			System.out.println("path : " + realPath);
			//System.out.println("file size : " + file.getSize());
			
			testDTO.setSm_id((String) session.getAttribute("sm_id"));
			testService.write(testDTO);
			// ip불러오기
			String ip = util.getUserIp(request);
			String target = "write";
			String data = "글쓰기 : " + testDTO.getSb_content();
			LogDTO log = null;
			// HttpSession session = request.getSession();
			if (session.getAttribute("sm_id") != null) {// 로그인 했다면
				String id = (String) session.getAttribute("sm_id");
				log = new LogDTO(ip, target, id, data);// sl_id가 있음.
			} else {
				log = new LogDTO(ip, target, data);// sl_id가 없음.
			}
			logService.writeLog(log);

			return "redirect:/board?sb_cate=" + testDTO.getSb_cate();
		} else {
			return "redirect:/";
		}
	}

	@GetMapping("/detail")
	public ModelAndView detail(@RequestParam("sb_no") int sb_no, HttpServletRequest request) {
		//여기에 있다면?
		//요기에 읽음 수 sb_count +1 해주세요.
		//service -> dao -> sqlsession -> DB
		testService.viewCount(sb_no);
		
		TestDTO dto = testService.detail(sb_no);
		ModelAndView mv = new ModelAndView("detail");
		mv.addObject("dto", dto);

		String ip = util.getUserIp(request);
		String target = "detail?sb_no=" + sb_no;
		String data = "글 읽기 : " + dto.getSb_content();
		LogDTO log = null;

		HttpSession session = request.getSession();
		if (session.getAttribute("sm_id") != null) {// 로그인 했다면
			String id = (String) session.getAttribute("sm_id");
			log = new LogDTO(ip, target, id, data);// sl_id가 있음.
		} else {
			log = new LogDTO(ip, target, data);// sl_id가 없음.
		}
		logService.writeLog(log);

		
		
		return mv;
	}

	@GetMapping("/delete")
	public String delete(@RequestParam("sb_no") int sb_no, HttpServletRequest request) {
		// 주의 : 로그인 사용자만 삭제 접근 가능 + 자신의 글만 삭제 가능
		HttpSession session = request.getSession();
		TestDTO dto = new TestDTO();
		dto.setSb_no(sb_no);
		dto.setSm_id((String) session.getAttribute("sm_id"));
		if (session.getAttribute("sm_id") != null) {
			int result = testService.delete(dto);
			// ip불러오기
			String ip = util.getUserIp(request);
			
			String target = "delete?sb_no=" + sb_no;
			String data = "글삭제 : " + result;
			LogDTO log = null;

			// HttpSession session = request.getSession();
			if (session.getAttribute("sm_id") != null) {// 로그인 했다면
				String id = (String) session.getAttribute("sm_id");
				log = new LogDTO(ip, target, id, data);// sl_id가 있음.
			} else {
				log = new LogDTO(ip, target, data);// sl_id가 없음.
			}
			logService.writeLog(log);
			return "redirect:/board";
		} else {
			return "redirect:/";
		}

	}

	@GetMapping("/update")
	public ModelAndView update(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("update");
		int sb_no = Integer.parseInt(request.getParameter("sb_no"));
		TestDTO dto = testService.detail(sb_no);
		mv.addObject("dto", dto);
		return mv;
	}

	@PostMapping("/update")
	public String update(TestDTO dto, HttpServletRequest request) {
		HttpSession session = request.getSession();
		if (session.getAttribute("sm_id") != null) {
			dto.setSm_id((String)session.getAttribute("sm_id"));
			int result = testService.update(dto);
			// ip불러오기
			String ip = util.getUserIp(request);
			
			String target = "update?sb_no=" + dto.getSb_no();
			String data = "글수정 : " + result + " : " + dto.getSb_content();
			LogDTO log = null;
			String id = (String) session.getAttribute("sm_id");
			log = new LogDTO(ip, target, id, data);// sl_id가 있음.
			logService.writeLog(log);
			
			return "redirect:/detail?sb_no=" + dto.getSb_no();
		} else {
			return "redirect:/";
		}

	}

	// 이건 삭제
	@RequestMapping("/temp")
	public String temp() {
		return "temp";
	}

	
	//좋아요 기능을 넣는다면?
	//1. 데이터베이스에 컬럼을 추가해주세요.
	//2. DTO에 sb_like를 추가해주시고, setter/getter를 만들어주세요.
	//3. jsp화면에 그림 하나 넣어주시고, 
	//   db에서 해당 값을 받아와 출력해주세요.
	//4. 그림을 누르면 페이지 전환 -> sb_like +1 시켜주세요. -> 페이지 전환
			//detail로 다시 돌아가야 해요 ---> sb_no가 있어야해요.
	@RequestMapping("/like")
	public String like(
						@RequestParam("sb_no") int sb_no, 
						HttpServletRequest request) {
		//request를 넣은 이유 : 세션 검사를 해서 로그인 한 사람만 누르게...
		//일단은 ajax는 나중에, 먼저 페이지 전환해서 다시 읽어오기 기능으로
		//System.out.println("들어온 sb_no : " + sb_no);
		
		//로직변경합니다. if문으로 id가 오는지 검사
		HttpSession session = request.getSession();
		if(session.getAttribute("sm_id") != null) {//정상로그인
			//보낼 값이 두개~ sb_no, sm_id
			Map<String, Object> sendMap = new HashMap<String, Object>();
			String id = (String)session.getAttribute("sm_id");
			sendMap.put("sb_no", sb_no);
			sendMap.put("sm_id", id);
			
			testService.like(sendMap);
			
			return "redirect:/detail?sb_no=" + sb_no;			
		}else {//로그인X
			return "redirect:/";
		}
		
		
		
		
		
		
	}
	
}
