package com.poseidon.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.poseidon.web.admin.AdminService;
import com.poseidon.web.admin.MemberDTO;

@Controller
@RequestMapping("/admin")
public class AdminController {
	@Autowired
	private AdminService adminService;

	// @RequestMapping(value = {"/category", "/admin"})
	// http://localhost:8080/web/admin/category
	@RequestMapping(value = "/category", method = RequestMethod.GET)
	public ModelAndView category(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("/admin/category");

		// scotagory내용을 다 가져와 주세요. sc_no, sc_category, sc_date
		List<HashMap<String, Object>> cate = adminService.getCategory();
		// mv에 붙여주세요.
		mv.addObject("category", cate);
		// jsp에 찍어주세요.
		return mv;
	}

	// @RequestMapping(value = "/admin/category", method=RequestMethod.POST)
	@PostMapping("/category")
	public String categoryInsert(HttpServletRequest request) {
		String categoryName = request.getParameter("categoryName");
		System.out.println(categoryName);
		// 로직...
		// 서비스 -> DAO -> sqlSession -> DB
		int result = adminService.categoryInsert(categoryName);
		// result는 결과값이 뭐가 오는지 찍어보기 위함입니다.
		System.out.println("저장 결과 : " + result);

		return "redirect:/admin/category";// get다시 호출
	}

	@GetMapping("/categoryUpdate")
	public ModelAndView categoryUpdate(HttpServletRequest request) {
		int sc_no = Integer.parseInt(request.getParameter("sc_no"));
		System.out.println("들어온 sc_no : " + sc_no);
		ModelAndView mv = new ModelAndView("/admin/categoryUpdate");

		HashMap<String, Object> cate = adminService.getCategory(sc_no);

		System.out.println(cate);

		mv.addObject("cate", cate);

		return mv;
	}

	@PostMapping("/categoryUpdate")
	public String categoryUpdate2(HttpServletRequest request) {
		int sc_no = Integer.parseInt(request.getParameter("sc_no"));
		String category = request.getParameter("category");

		System.out.println("sc_no:" + sc_no);
		System.out.println("category:" + category);
		
		//DB로 보내기
		HashMap<String, Object> cate = new HashMap<String, Object>();
		cate.put("sc_no", sc_no);
		cate.put("category", category);
		int result = adminService.categoryUpdate(cate);

		System.out.println("수정 결과 : " + result);
		
		return "redirect:/admin/category";
	}
	
	//전체 회원 리스트 단, 비밀번호는 안 보이게;;;
	//	/admin/member     member.jsp
	@GetMapping("/member")
	public ModelAndView member() {//파라미터 필요하면 넣어주세요.
		ModelAndView mv = new ModelAndView();//페이지 없이 생성
		mv.setViewName("admin/member");//추후 페이지 넣어주기
		
		ArrayList<MemberDTO> list = 
				(ArrayList<MemberDTO>)adminService.list();
		mv.addObject("list", list);
		return mv;
	}
	
	//회원 등급 조정 down up   gradeDown?sm_no=6
	@GetMapping({"/gradeDown", "/gradeUp"})
	public String gradeDown(
			@RequestParam("sm_no") int sm_no,
			@RequestParam("grade") int sm_grade  
			) {
		System.out.println("sm_no : " + sm_no);
		System.out.println("sm_grade : " + sm_grade);
		//이 메소드 정상으로 동작하지 않습니다.
		//고치는 방법은?
		MemberDTO dto = new MemberDTO();
		dto.setSm_no(sm_no);
		dto.setSm_grade(sm_grade);
		
		int result = adminService.gradeUpDown(dto);
		return "redirect:/admin/member";
	}
	
	@GetMapping("/userDelete")
	public String userDelete(@RequestParam("sm_no") int sm_no) {
		//로직
		int result = adminService.userDelete(sm_no);
		return "redirect:/admin/member";
	}
	
}
