package io.github.skepter.skepbot;

import java.util.HashMap;
import java.util.Map;

public class LeetGenerator {

	private char english[] = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
	private char leet[] =    {'4', '8', 'C', 'D', '3', 'F', '6', 'H', '!', 'J', 'K', '1', 'M', 'N', '0', '9', 'Q', 'R', '5', '7', 'U', 'V', 'W', 'X', 'Y', 'Z'};
	
	private String str;
	
	public LeetGenerator(String message) {
		message = message.toUpperCase();
		Map<Character, Character> map = new HashMap<Character, Character>();
		
		for (int i = 0; i < english.length; i++) {
			char c = english[i];
			map.put(c, leet[i]);		}
		
		StringBuilder builder = new StringBuilder("");
		for(char c : message.toCharArray()) {
			builder.append(map.getOrDefault(c, c));
		}
		str = builder.toString();
	}
	
	public String leet() {
		return str;
	}
	
}
