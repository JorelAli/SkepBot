package io.github.skepter.skepbot.modules;

import java.io.IOException;

import io.github.skepter.skepbot.WebsiteGetters;

public class NumberFact extends Module {

	public NumberFact() {
		super("tell you a fact about a specific number", PatternType.MATCHES, ".*tell me.*number.*");
	}
	
	@Override
	public String output() {
		try {
			return WebsiteGetters.getNumberFact(input.toLowerCase());
		} catch (IOException e) {
			return "I tried to think up an interesting fact, but couldn't think of anything D:";
		}
	}

}
