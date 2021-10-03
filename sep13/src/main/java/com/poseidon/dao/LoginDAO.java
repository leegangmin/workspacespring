package com.poseidon.dao;

import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository("loginDAO")
public class LoginDAO extends AbstractDAO{

	public Map<String, Object> login(Map<String, Object> map) {
		return selectOne("login.login", map);
	}
	//AbstractDAO가 가지고 있는 모든 것을 내것처럼 씁니다.
	//sqlSession
	
	

}
