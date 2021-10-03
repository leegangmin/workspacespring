package com.poseidon.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poseidon.dao.TestDAO;

@Service("testService")
public class TestServiceImpl implements TestService {
	Logger log = Logger.getLogger(this.getClass());

	@Autowired
	private TestDAO testDAO;
	
	@Override
	public List<Map<String, Object>> boardList(Map<String, Object> map) {
		return testDAO.selectList(map);
	}

	@Override
	public void write(Map<String, Object> map) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Map<String, Object> detail(Map<String, Object> map) {
		return testDAO.detail(map);
	}

	@Override
	public int delete(Map<String, Object> map) {
		return testDAO.delete(map);
	}

	@Override
	public int update(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getCategory(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<HashMap<String, Object>> categoryList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int totalList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void viewCount(Map<String, Object> map) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void like(Map<String, Object> map) {
		// TODO Auto-generated method stub
		
	}

	public int totalCount(Map<String, Object> map) {
		return testDAO.totalCount(map);
	}

}







