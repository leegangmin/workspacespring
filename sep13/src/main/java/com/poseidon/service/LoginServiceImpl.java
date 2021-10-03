package com.poseidon.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poseidon.dao.LoginDAO;
/*
 * 객체 생성
 * @controller  객체 생성, 컨트롤러 역할 
 * @service		객체 생성, 서비스 역할
 * @repository	객체 생성, DAO역할
 * @component	객체 생성, 그외
 * 
 * 객체 주입
 * @Inject				데이터 타입으로 찾아요. java에서 제공
 * @Autowired			데이터 타입으로 찾아요. Spring에서 제공
 * @Resource(name="")	name으로 찾아요. xml에서는 id
 * 
 * 
 * 
 * 생성순서
 * 컨트롤러 -> 서비스Impl -> 서비스 -> DAO -> mapper ->
 * 
 * 
 * DAO -> 서비스 -> 서비스Impl -> 컨트롤러 -> mapper -> 
 * */

@Service("loginService")
public class LoginServiceImpl implements LoginService {
	@Autowired
	private LoginDAO loginDAO;

	@Override
	public Map<String, Object> login(Map<String, Object> map) {
		return loginDAO.login(map);
	}


}
