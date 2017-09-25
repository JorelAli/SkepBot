package io.github.skepter.skepbot.modules;

import java.io.IOException;

import io.github.skepter.skepbot.WebsiteGetters;

public class Dictionary extends Module {

	
	public Dictionary() {
		super("define a word", PatternType.CONTAINS, "define", "definition");
	}
	
	@Override
	public String output() {
		
		input = input.toLowerCase().replaceAll("[^A-Za-z\\s]", "");
		String[] arr = input.split(" ");
		String wordToDefine = arr[arr.length - 1];
		
		try {
			return WebsiteGetters.getDefinition(wordToDefine);
		} catch (IOException e) {
			e.printStackTrace();
			return "I have no clue what " + wordToDefine + " means. Sorry!";
		}
	}

}
