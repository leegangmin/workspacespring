package com.springbook.biz.board.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
//DAO는 repository
@Repository
public class BoardDAOSpring {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private final String BOARD_INSERT = "INSERT INTO board (seq, title, writer, content) VALUES (?, ?, ?, ?)";

	public void insertBoard(/*DTO를 안만들어서 뺌 */ ) {
//		jdbcTemplate.update(쿼리, 값1, 값2, 값3, 값4);
		jdbcTemplate.update(BOARD_INSERT, 2, "title", "gangminlee", "blabla");
	}
}
