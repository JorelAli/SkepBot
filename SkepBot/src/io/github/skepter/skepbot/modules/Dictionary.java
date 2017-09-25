package io.github.skepter.skepbot.modules;

import java.io.IOException;

import io.github.skepter.skepbot.WebsiteGetters;

public class Dictionary extends Module {

	
	public Dictionary() {
		super("define a word", PatternType.CONTAINS, "define", "definition");
	}
	
	@Override
	public String output() {
		return null;
	}
	
	@Override
	public String[] extraOutputs() {
		input = input.toLowerCase().replaceAll("[^A-Za-z\\s]", "");
		String[] arr = input.split(" ");
		String wordToDefine = arr[arr.length - 1];
		
		try {
			String[] definitions = WebsiteGetters.getDefinition(wordToDefine);
			if(definitions.length != 1) {
				String[] temp = new String[definitions.length + 1];
				temp[0] = "I have found " + definitions.length + " different definitions:";
				for(int i = 0; i < definitions.length; i++) {
					temp[i + 1] = definitions[i];
				}
				definitions = temp;
			}
			return definitions;
		} catch (IOException e) {
			e.printStackTrace();
			return new String[] {"I have no clue what " + wordToDefine + " means. Sorry!"};
		}
	}

}
