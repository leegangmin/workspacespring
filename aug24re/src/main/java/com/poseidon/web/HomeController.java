package com.poseidon.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

	//시작하자마자 열리는 페이지
	@RequestMapping(value = "/")
	public String home() {
		return "index";
	}
	@RequestMapping(value = "/index2")
	public String home2() {
		return "index2";
	}
	
}
