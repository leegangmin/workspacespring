package com.poseidon.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository("messageDAO")
public class MessageDAO extends AbstractDAO {

	public List<Map<String, Object>> messageList(Map<String, Object> map) {
		return selectList("message.messageList", map);
	}

	public Map<String, Object> messageDetail(Map<String, Object> map) {
		return selectOne("message.detail", map);
	}

	public void readMessage(Map<String, Object> map) {
		update("message.readMessage", map);
	}

	public String getName(Map<String, Object> map) {
		return getName("message.getName", map);
	}

	public int sendMessage(Map<String, Object> map) {
		return sendMessage("message.sendMessage", map);
	}

	public void delMsg(Map<String, Object> map) {
		delMsg("message.delMsg", map);
	}

	
}
