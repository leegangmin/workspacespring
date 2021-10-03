package com.poseidon.web;
/*
 * 2021-08-25 
 *mybatis와 연결하겠습니다. 
 */

//               Serive - DAO - sqlSession - DB 

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/* 컨트롤러 @controller
 * 서비스 @service
 * DAO @repository
 * 그외 @component
 */
@Repository
public class TestDAO {
	@Autowired
	private SqlSession sqlSession;

	// 실제 데이터를 불러오겠습니다. select 흐름을 봐주세요.
	public List<TestDTO> boardList(Map<String, Object> sendMap) {
		return sqlSession.selectList("test.boardList", sendMap);// 네임스페이스.id
	}

	public void write(TestDTO testDTO) {
		sqlSession.insert("test.write", testDTO);
	}

	public TestDTO detail(int bno) {
		return sqlSession.selectOne("test.detail", bno);
	}

	public int delete(TestDTO dto) {
		return sqlSession.delete("test.delete", dto);
	}

	public int update(TestDTO dto) {
		return sqlSession.update("test.update", dto);
	}

	public String getCategory(int sb_cate) {
		return sqlSession.selectOne("test.getCategory", sb_cate);
	}

	public List<HashMap<String, Object>> categoryList() {
		return sqlSession.selectList("test.categoryList");
	}

	public int totalList(int sb_cate) {
		return sqlSession.selectOne("test.totalList", sb_cate);
	}

	public void viewCount(int sb_no) {
		sqlSession.update("test.viewCount", sb_no);
	}

	public void like(Map<String, Object> sendMap) {
		sqlSession.insert("test.like", sendMap);		
	}

	public int likeCheck(Map<String, Object> sendMap) {
		return sqlSession.selectOne("test.likeCheck", sendMap);
	}

}
