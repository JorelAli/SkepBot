package io.github.skepter.skepbot.modules;

import java.util.HashMap;
import java.util.Map;

//I prefer my own leet generator to the one by Wolfram (it's less spammy)
public class Leet extends Module {
	
	private char english[] = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
	private char leet[] =    {'4', '8', 'C', 'D', '3', 'F', '6', 'H', '!', 'J', 'K', '1', 'M', 'N', '0', '9', 'Q', 'R', '5', '7', 'U', 'V', 'W', 'X', 'Y', 'Z'};
	
	public Leet() {
		super(PatternType.STARTS_WITH, "1337", "leet");
	}

	@Override
	public String output() {
		
		String message = input.substring(5).toUpperCase();
		Map<Character, Character> map = new HashMap<Character, Character>();
		
		for (int i = 0; i < english.length; i++) {
			char c = english[i];
			map.put(c, leet[i]);
		}
		
		StringBuilder builder = new StringBuilder("");
		for(char c : message.toCharArray()) {
			builder.append(map.getOrDefault(c, c));
		}
		
		return builder.toString();
	}

}
