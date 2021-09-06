package com.poseidon.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.poseidon.web.login.LoginDTO;
import com.poseidon.web.login.LoginService;

@Controller
public class LoginController {
	@Autowired
	private LoginService loginService;
	/*
	 * 1.화면 만들기
	 * 2.데이터 받기
	 * 3.데이터베이스에 질의하기
	 * 4.결과 판별하기
	 * 5.세션만들기
	 * 6.페이지 이동
	 * 
	 */
	@GetMapping("/login")
	public String login() {
		
		return "login";
		//http://localhost:8080/web/login
	}
	
	@PostMapping("/login")
	public String loginAction(HttpServletRequest request) {
		//@RequestParam("네임") 타입 변수명
		//@RequestParam("id") String id, @RequestParam("pw") String pw 
		
		//데이터 베이스 보내고
		//올바른 로그인이면 세션 만들어서 -> board
		//잘못된 로그인이면 로그인 페이지로  -> login
		
		String id = request.getParameter("id");
		String pw = request.getParameter("pw");
		//System.out.println("들어온 id :" + id + " pw : " + pw);
		
		//LoginDTO를 만들어주세요. 거기에 넣어주세요.
		LoginDTO dto = new LoginDTO();
		dto.setSm_id(id);
		dto.setSm_pw(pw);
		
		//service -> DAO -> mapper.xml
		//리턴타입 변수명 = 값;
		LoginDTO result = loginService.login(dto);
		//System.out.println(result);//객체
		if(result != null) {//돌아오는 객체가 있다면
			//System.out.println(result.getSm_no());
			//System.out.println(result.getSm_name());//값들
			//세션은 여기에...
			HttpSession session = request.getSession();
			session.setAttribute("sm_name", result.getSm_name());
			session.setAttribute("sm_id", result.getSm_id());
			
			return "redirect:/board";//여긴 정상 로그인
		}else{			
			//오류 수정 redirect 수정했습니다.
			return "redirect:/?error=loginError";//비정상 로그인
		}
	}
	
	
	@GetMapping("/logout")
	public String logout(HttpServletRequest request) {
		HttpSession session = request.getSession();
		
		//session.invalidate();
		if(session.getAttribute("sm_id") != null) {
			session.removeAttribute("sm_id");			
		}
		if(session.getAttribute("sm_name") != null) {
			session.removeAttribute("sm_name");			
		}
		// index로 가도록 수정했습니다.
		return "redirect:/";
	}
	
	//가입하기 join
	@GetMapping("/join")
	public String join() {
		return "join";
	}
	
	//가입하기 post
	@PostMapping("/join")
	public String joinAction(HttpServletRequest request) {
		//오는 데이터 잡아주세요.
		String id = request.getParameter("id");
		String pw = request.getParameter("pw1");
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		
		//오는 값을 다 확인하셨으면 데이터베이스에 저장해주세요.
		//데이터베이스로 보낼 떄 객체에 담기
		Map<String, Object> login = new HashMap<String, Object>();
		login.put("id", id);
		login.put("pw", pw);
		login.put("name", name);
		login.put("email", email);
		int result = loginService.join(login);//보낼 데이터는 파라미터에...
		System.out.println("저장결과 : " + result);
		return "redirect:/";
	}
	
	//ajax id check
	@PostMapping("/checkID")
	public @ResponseBody String checkID(HttpServletRequest request) {
		String id = request.getParameter("id");
		//System.out.println("들어온 ID는 : "+ id);
		String check = "1";
		//데이터베이스에게 질의하기
		check = loginService.checkID(id);
		return check;
	}
	
	
}
