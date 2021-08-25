package com.gangminlee.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class testController {

	//매핑작업
	@RequestMapping(value="/board", method = RequestMethod.GET)
	public ModelAndView board() {
		ModelAndView mv = new ModelAndView("board");
		
		return mv;
	}
	
}
