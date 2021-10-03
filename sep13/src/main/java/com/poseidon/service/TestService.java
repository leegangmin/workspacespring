package com.poseidon.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public interface TestService {
	public List<Map<String, Object>> boardList(Map<String, Object> map);

	public void write(Map<String, Object> map);

	public Map<String, Object> detail(Map<String, Object> map);

	public int delete(Map<String, Object> map);

	public int update(Map<String, Object> map);

	public String getCategory(Map<String, Object> map);

	public List<HashMap<String, Object>> categoryList(Map<String, Object> map);

	public int totalList(Map<String, Object> map);

	public void viewCount(Map<String, Object> map);

	public void like(Map<String, Object> map);
}