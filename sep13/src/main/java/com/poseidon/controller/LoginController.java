package com.poseidon.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.poseidon.common.CommandMap;
import com.poseidon.service.LoginServiceImpl;

@Controller
public class LoginController {
	@Resource(name = "loginService")
	private LoginServiceImpl loginService;

	@GetMapping("/login.do")
	public String login() {
		return "login";
	}

	@PostMapping("/login.do")
	public String login(CommandMap commandMap, HttpServletRequest request) {
		// System.out.println(commandMap.getMap());

		Map<String, Object> login = loginService.login(commandMap.getMap());
		System.out.println("나온 값 : " + login);
		// 나온 값 : {sm_no=4, sm_id=poseidon, sm_name=poseidon}
		// 세션만들어주기
		//ServletWebRequest servletRequest =  (ServletWebRequest) RequestContextHolder.getRequestAttributes();
		//HttpServletRequest request = servletRequest.getRequest();
		
		HttpSession session = request.getSession();
		session.setAttribute("sm_name", login.get("sm_name"));
		session.setAttribute("sm_id", login.get("sm_id"));
		// sm_name
		// sm_id
		return "redirect:/main.do";
	}

}