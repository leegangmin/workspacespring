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

	// ๋งตํ์์
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

		// ip๋ถ๋ฌ์ค๊ธฐ
		String ip = util.getUserIp(request);
		
		String target = "board?sb_cate=" + sb_cate;
		String data = "๊ฒ์ํ ์ฝ์";
		LogDTO log = null;

		HttpSession session = request.getSession();
		if (session.getAttribute("sm_id") != null) {// ๋ก๊ทธ์ธ ํ๋ค๋ฉด
			String id = (String) session.getAttribute("sm_id");
			log = new LogDTO(ip, target, id, data);// sl_id๊ฐ ์์.
		} else {
			log = new LogDTO(ip, target, data);// sl_id๊ฐ ์์.
		}
		logService.writeLog(log);

		return mv;
	}

	// write ๊ธ์ฐ๊ธฐ
	// @RequestMapping(value="/write", method=RequestMethod.GET)
	@GetMapping("/write")
	public String write(HttpServletRequest request) {
		// @RequestParam("sb_cate") int sb_cate
		// ์์์ ์ก์ sb_cate๋ฅผ MV์ ๋ถ์ฌ์ write.jsp๋ก ๋ณด๋ด๊ธฐ
		// ๊ทธ๋ผ formํ๊ทธ ์์์ ๋ถ์ฌ์ ๊ธ์ฐ๊ธฐ ๋ฒํผ์ ๋๋?์๋
		// sb_cate๊ฐ๊น์ง ๊ฐ์?ธ๊ฐ ์ ์์.
		// ๋ก๊ทธ์ธ ํ ์ฌ๋์ด ํ์ธ
		HttpSession session = request.getSession();

		if (session.getAttribute("sm_name") != null && session.getAttribute("sm_id") != null) {
			return "write";// ์?์ ๋ก๊ทธ์ธ ํ ์ฌ๋์ด๋ผ๋ฉด ๊ธ์ฐ๊ธฐ ํ๋ฉด์ผ๋ก
		} else {
			return "redirect:/";
		}

	}

	@PostMapping("/write")
	public String write2(HttpServletRequest request, MultipartFile file) throws IOException {
		HttpSession session = request.getSession();

		if (session.getAttribute("sm_name") != null && session.getAttribute("sm_id") != null) {
			//๋ฐ์ดํฐ๋ฒ?์ด์ค๋ก ๋ณด๋ผ dto๋ง๋ค๊ธฐ
			TestDTO testDTO = new TestDTO();
			testDTO.setSb_title(request.getParameter("sb_title"));
			testDTO.setSb_content(request.getParameter("sb_content"));
			testDTO.setSb_cate(util.str2Int2(request.getParameter("sb_cate")));
			
			//System.out.println("title : " + testDTO.getSb_title());
			//System.out.println("content : " + testDTO.getSb_content());
			//System.out.println("cate : " + testDTO.getSb_cate());
			
			//file/
			testDTO.setSb_orifile(file.getOriginalFilename());
			
			//์ค์?๋ก ํ์ผ์ ์?์ฅ์ํค๊ธฐ  -> ์ค์? ์?์ฅ๋ ํ์ผ ์ด๋ฆ -> ์๋์
			//์ค์? ๊ฒฝ๋ก
			String realPath = servletContext.getRealPath("resources/upfile/");
			String upfile = fileSave.save2(realPath, file);
			testDTO.setSb_file(upfile);
			
			//System.out.println("file : " + testDTO.getSb_orifile());
			//System.out.println("upfile : " + testDTO.getSb_file());
			System.out.println("path : " + realPath);
			//System.out.println("file size : " + file.getSize());
			
			testDTO.setSm_id((String) session.getAttribute("sm_id"));
			testService.write(testDTO);
			// ip๋ถ๋ฌ์ค๊ธฐ
			String ip = util.getUserIp(request);
			String target = "write";
			String data = "๊ธ์ฐ๊ธฐ : " + testDTO.getSb_content();
			LogDTO log = null;
			// HttpSession session = request.getSession();
			if (session.getAttribute("sm_id") != null) {// ๋ก๊ทธ์ธ ํ๋ค๋ฉด
				String id = (String) session.getAttribute("sm_id");
				log = new LogDTO(ip, target, id, data);// sl_id๊ฐ ์์.
			} else {
				log = new LogDTO(ip, target, data);// sl_id๊ฐ ์์.
			}
			logService.writeLog(log);

			return "redirect:/board?sb_cate=" + testDTO.getSb_cate();
		} else {
			return "redirect:/";
		}
	}

	@GetMapping("/detail")
	public ModelAndView detail(@RequestParam("sb_no") int sb_no, HttpServletRequest request) {
		//์ฌ๊ธฐ์ ์๋ค๋ฉด?
		//์๊ธฐ์ ์ฝ์ ์ sb_count +1 ํด์ฃผ์ธ์.
		//service -> dao -> sqlsession -> DB
		testService.viewCount(sb_no);
		
		TestDTO dto = testService.detail(sb_no);
		ModelAndView mv = new ModelAndView("detail");
		mv.addObject("dto", dto);

		String ip = util.getUserIp(request);
		String target = "detail?sb_no=" + sb_no;
		String data = "๊ธ ์ฝ๊ธฐ : " + dto.getSb_content();
		LogDTO log = null;

		HttpSession session = request.getSession();
		if (session.getAttribute("sm_id") != null) {// ๋ก๊ทธ์ธ ํ๋ค๋ฉด
			String id = (String) session.getAttribute("sm_id");
			log = new LogDTO(ip, target, id, data);// sl_id๊ฐ ์์.
		} else {
			log = new LogDTO(ip, target, data);// sl_id๊ฐ ์์.
		}
		logService.writeLog(log);

		
		
		return mv;
	}

	@GetMapping("/delete")
	public String delete(@RequestParam("sb_no") int sb_no, HttpServletRequest request) {
		// ์ฃผ์ : ๋ก๊ทธ์ธ ์ฌ์ฉ์๋ง ์ญ์? ์?๊ทผ ๊ฐ๋ฅ + ์์?์ ๊ธ๋ง ์ญ์? ๊ฐ๋ฅ
		HttpSession session = request.getSession();
		TestDTO dto = new TestDTO();
		dto.setSb_no(sb_no);
		dto.setSm_id((String) session.getAttribute("sm_id"));
		if (session.getAttribute("sm_id") != null) {
			int result = testService.delete(dto);
			// ip๋ถ๋ฌ์ค๊ธฐ
			String ip = util.getUserIp(request);
			
			String target = "delete?sb_no=" + sb_no;
			String data = "๊ธ์ญ์? : " + result;
			LogDTO log = null;

			// HttpSession session = request.getSession();
			if (session.getAttribute("sm_id") != null) {// ๋ก๊ทธ์ธ ํ๋ค๋ฉด
				String id = (String) session.getAttribute("sm_id");
				log = new LogDTO(ip, target, id, data);// sl_id๊ฐ ์์.
			} else {
				log = new LogDTO(ip, target, data);// sl_id๊ฐ ์์.
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
			// ip๋ถ๋ฌ์ค๊ธฐ
			String ip = util.getUserIp(request);
			
			String target = "update?sb_no=" + dto.getSb_no();
			String data = "๊ธ์์? : " + result + " : " + dto.getSb_content();
			LogDTO log = null;
			String id = (String) session.getAttribute("sm_id");
			log = new LogDTO(ip, target, id, data);// sl_id๊ฐ ์์.
			logService.writeLog(log);
			
			return "redirect:/detail?sb_no=" + dto.getSb_no();
		} else {
			return "redirect:/";
		}

	}

	// ์ด๊ฑด ์ญ์?
	@RequestMapping("/temp")
	public String temp() {
		return "temp";
	}

	
	//์ข์์ ๊ธฐ๋ฅ์ ๋ฃ๋๋ค๋ฉด?
	//1. ๋ฐ์ดํฐ๋ฒ?์ด์ค์ ์ปฌ๋ผ์ ์ถ๊ฐํด์ฃผ์ธ์.
	//2. DTO์ sb_like๋ฅผ ์ถ๊ฐํด์ฃผ์๊ณ?, setter/getter๋ฅผ ๋ง๋ค์ด์ฃผ์ธ์.
	//3. jspํ๋ฉด์ ๊ทธ๋ฆผ ํ๋ ๋ฃ์ด์ฃผ์๊ณ?, 
	//   db์์ ํด๋น ๊ฐ์ ๋ฐ์์ ์ถ๋?ฅํด์ฃผ์ธ์.
	//4. ๊ทธ๋ฆผ์ ๋๋ฅด๋ฉด ํ์ด์ง ์?ํ -> sb_like +1 ์์ผ์ฃผ์ธ์. -> ํ์ด์ง ์?ํ
			//detail๋ก ๋ค์ ๋์๊ฐ์ผ ํด์ ---> sb_no๊ฐ ์์ด์ผํด์.
	@RequestMapping("/like")
	public String like(
						@RequestParam("sb_no") int sb_no, 
						HttpServletRequest request) {
		//request๋ฅผ ๋ฃ์ ์ด์? : ์ธ์ ๊ฒ์ฌ๋ฅผ ํด์ ๋ก๊ทธ์ธ ํ ์ฌ๋๋ง ๋๋ฅด๊ฒ...
		//์ผ๋จ์ ajax๋ ๋์ค์, ๋จผ์? ํ์ด์ง ์?ํํด์ ๋ค์ ์ฝ์ด์ค๊ธฐ ๊ธฐ๋ฅ์ผ๋ก
		//System.out.println("๋ค์ด์จ sb_no : " + sb_no);
		
		//๋ก์ง๋ณ๊ฒฝํฉ๋๋ค. if๋ฌธ์ผ๋ก id๊ฐ ์ค๋์ง ๊ฒ์ฌ
		HttpSession session = request.getSession();
		if(session.getAttribute("sm_id") != null) {//์?์๋ก๊ทธ์ธ
			//๋ณด๋ผ ๊ฐ์ด ๋๊ฐ~ sb_no, sm_id
			Map<String, Object> sendMap = new HashMap<String, Object>();
			String id = (String)session.getAttribute("sm_id");
			sendMap.put("sb_no", sb_no);
			sendMap.put("sm_id", id);
			
			testService.like(sendMap);
			
			return "redirect:/detail?sb_no=" + sb_no;			
		}else {//๋ก๊ทธ์ธX
			return "redirect:/";
		}
		
		
		
		
		
		
	}
	
}
