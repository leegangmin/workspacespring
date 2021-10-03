package com.poseidon.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository("testDAO")
public class TestDAO extends AbstractDAO {

	public List<Map<String, Object>> selectList(Map<String, Object> map) {
		return (List<Map<String, Object>>) selectList("test.boardList", map);
	}

	public Map<String, Object> detail(Map<String, Object> map) {
		return (Map<String, Object>) selectOne("test.detail", map);
	}

	public int delete(Map<String, Object> map) {
		return delete("test.delete", map);
	}

	public int totalCount(Map<String, Object> map) {
		// return (int) selectOne("test.totalCount", map).get("totalCount");
		return Integer.parseInt(
		String.valueOf(selectOne("test.totalCount", map).get("totalCount"))
		);

	}

	// 나중에는 AbstractDAO에 미리 제작해줍니다.
}
