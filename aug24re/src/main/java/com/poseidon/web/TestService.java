package com.poseidon.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/*
 *  2021-08-25 TestService 
 *  
 */
@Service
public class TestService {
	@Autowired
	private TestDAO testDAO;
	
	public List<TestDTO> boardList(Map<String, Object> sendMap){
		return testDAO.boardList(sendMap);
	}

	public void write(TestDTO testDTO) {
		testDAO.write(testDTO);
	}

	public TestDTO detail(int bno) {
		return testDAO.detail(bno);
	}

	public int delete(int bno) {
		return testDAO.delete(bno);
	}

	public int update(TestDTO dto) {
		return testDAO.update(dto);
	}

	public String getCategory(int sb_cate) {
		return testDAO.getCategory(sb_cate);
	}

	public List<HashMap<String, Object>> categoryList() {
		return testDAO.categoryList();
	}

	public int totalList(int sb_cate) {
		return testDAO.totalList(sb_cate);
	}
}
