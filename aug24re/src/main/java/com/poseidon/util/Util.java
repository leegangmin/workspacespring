package com.poseidon.util;

import org.springframework.stereotype.Component;

@Component
public class Util {
	public boolean str2Int(String str) {//참거짓
		try {
			Integer.parseInt(str);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public int str2Int2(String str) {//숫자변환 가능하면 변환, 아니면 0
		try {
			return Integer.parseInt(str);
		} catch (Exception e) {
			return 0;
		}
	}
}
