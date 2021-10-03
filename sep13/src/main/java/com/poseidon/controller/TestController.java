package com.poseidon.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.poseidon.common.CommandMap;
import com.poseidon.service.TestServiceImpl;

import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

@Controller
public class TestController {
	@Resource(name = "testService")
	private TestServiceImpl testService;

	@RequestMapping(value = "/main.do")
	public ModelAndView main(CommandMap commandMap) {
		ModelAndView mv = new ModelAndView("main");
		//System.out.println("::::::::::::::::::::" + commandMap.getMap());
		int listScale = 10;
		int pageScale = 10;
		int sb_cate = 1;
		if (!commandMap.containsKey("sb_cate")) {
			commandMap.put("sb_cate", sb_cate);
		}
		int pageNo = 1;
		if (commandMap.containsKey("pageNo")) {
			pageNo = Integer.parseInt(String.valueOf(commandMap.getMap().get("pageNo")));
		}

		int totalCount = testService.totalCount(commandMap.getMap());

		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(pageNo);
		paginationInfo.setRecordCountPerPage(listScale);
		paginationInfo.setPageSize(pageScale);
		int startPage = paginationInfo.getFirstRecordIndex();
		int lastPage = paginationInfo.getRecordCountPerPage();
		//commandMap.put("startPage", startPage);
		//commandMap.put("lastPage", lastPage);
		paginationInfo.setTotalRecordCount(totalCount);

		//List<Map<String, Object>> list = testService.boardList(commandMap.getMap());
		//mv.addObject("list", list);
		//System.out.println("뽑힌 리스트 : " + list);
		mv.addObject("paginationInfo", paginationInfo);
		mv.addObject("pageNo", pageNo);
		mv.addObject("totalCount", totalCount);// ?
		mv.addObject("sb_cate", sb_cate);// ?

		return mv;
	}
	//연결하겠습니다.
	@PostMapping(value="/mainAJAX2.do", produces = "text/plain;charset=utf-8")
	public @ResponseBody String mainAJAX2(CommandMap commandMap) {
		//System.out.println("ajax로 들어온 값 : " + commandMap.getMap());
		
		int sb_cate = 1;
		if (!commandMap.containsKey("sb_cate")) {
			commandMap.put("sb_cate", sb_cate);
		}
		int pageNo = 1;
		if (commandMap.containsKey("pageNo")) {
			pageNo = Integer.parseInt(String.valueOf(commandMap.getMap().get("pageNo")));
		}
		
		int listScale = 10;
		int pageScale = 10;
		
		//int totalCount = testService.totalCount(commandMap.getMap());

		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(pageNo);
		paginationInfo.setRecordCountPerPage(listScale);
		paginationInfo.setPageSize(pageScale);
		int startPage = paginationInfo.getFirstRecordIndex();
		int lastPage = paginationInfo.getRecordCountPerPage();
		commandMap.put("startPage", startPage);
		commandMap.put("lastPage", lastPage);
		//paginationInfo.setTotalRecordCount(totalCount);

		List<Map<String, Object>> list = testService.boardList(commandMap.getMap());
		
		// JSON으로 변환하기
		JSONObject jsonList = new JSONObject();// 제이슨 객체
		jsonList.put("sb_cate", sb_cate);
		jsonList.put("pageNo", pageNo);
		//jsonList.put("totalCount", totalCount);
		jsonList.put("list", list);
		
		//System.out.println(jsonList.toJSONString());

		return jsonList.toJSONString();
	}
	
	//새로 만들기 기존의 것은 mainAJAX2.do로 변경
	@RequestMapping(value = "/mainAJAX.do", method=RequestMethod.POST, 
					produces = "text/plain;charset=utf-8")
	@ResponseBody
	public String mainAJAX(CommandMap map) {
		//페이지 번호 받기 = 전자정부페이징이 아니라서 페이지 번호만 받음.
		int pageNo = 1;
		if(map.containsKey("pageNo")) {
			pageNo = Integer.parseInt((String)map.get("pageNo"));
		}
		map.put("pageNo", (pageNo - 1) * 10);//20, 10
		
		//카테고리
		int sb_cate = 1;
		if(!map.containsKey("sb_cate")) {			
			map.put("sb_cate", sb_cate);
		}
		
		//서버에 질의 = sql문을 수정해야 합니다.
		List<Map<String, Object>> list = testService.boardList(map.getMap());
		//totalCount
		int totalCount = testService.totalCount(map.getMap());
				
		//JSON형식으로 변환
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("sb_cate", sb_cate);//지금은 없어도 될 값
		jsonObj.put("pageNo", pageNo);
		jsonObj.put("list", list);
		jsonObj.put("totalCount", totalCount);//전체 글 수 
		
		System.out.println(jsonObj.toJSONString());//출력
		return jsonObj.toJSONString();
	}
	
	

	@RequestMapping("/detail.do")
	public ModelAndView detail(CommandMap map) {// int sb_no
		// Mv객체 만들어주세요
		ModelAndView mv = new ModelAndView("detail");
		// DB에게 질의해주세요. - ~에 담아주세요.
		// 서비스에게 질의를 던질 때 commandMap을 던지는 것이 아니라
		// 그 속에 map만 뽑아서 던집니다.
		Map<String, Object> detail = testService.detail(map.getMap());

		System.out.println("map : " + map);
		System.out.println("map.getMap() : " + map.getMap());
		System.out.println("detail : " + detail);
		// mv붙여주세요.
		mv.addObject("detail", detail);
		// Spring 생명주기, 커낵션 풀

		return mv;
	}

	@RequestMapping("/delete.do")
	public String delete(CommandMap map) {
		// 해야 할 일?
		// System.out.println(map.getMap());//출력해보기
		int result = testService.delete(map.getMap());
		System.out.println("처리결과는 : " + result);// 0 1
		String url = "redirect:main.do";// 이럴바엔 아래다가 적어서 보내지...

		return url;
	}

}

/*
 * 출석 해주세요. 로그인 컨트롤러 새로 만드시고 로그인 시켜주세요. 세션 만들어주세요. 오늘은 쪽지로 넘어가겠습니다.
 * 
 * 
 * 
 */

/*
 * 
 * 
 * 뽑힌 리스트 : [ { sb_title=asdf, sb_count=6, sb_no=144, sm_id=poseidon,
 * sm_name=poseidon, sb_date=2021-09-10 }, { sb_title=asdf, sb_count=1,
 * sb_no=143, sm_id=poseidon, sm_name=poseidon, sb_date=2021-09-10 }, {
 * sb_title=asdfasdf, sb_count=5, sb_no=142, sm_id=poseidon, sm_name=poseidon,
 * sb_date=2021-09-10}, {sb_title=1111, sb_count=0, sb_no=141, sm_id=poseidon,
 * sm_name=poseidon, sb_date=2021-09-10}, {sb_title=ttttt, sb_count=0,
 * sb_no=140, sm_id=poseidon, sm_name=poseidon, sb_date=2021-09-10},
 * {sb_title=tttttt, sb_count=0, sb_no=139, sm_id=poseidon, sm_name=poseidon,
 * sb_date=2021-09-10}, {sb_title=앞으로 할 일, sb_count=57, sb_no=137,
 * sm_id=poseidon, sm_name=poseidon, sb_date=2021-09-08}, {sb_title=다 하셨나요?,
 * sb_count=12, sb_no=136, sm_id=poseidon, sm_name=poseidon,
 * sb_date=2021-09-07}, {sb_title=글쓰기, sb_count=10, sb_no=135, sm_id=testa,
 * sm_name=testa, sb_date=2021-09-07}, {sb_title=글쓰기, sb_count=32, sb_no=134,
 * sm_id=poseidon, sm_name=poseidon, sb_date=2021-09-06}]
 */
