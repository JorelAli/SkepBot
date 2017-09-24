package io.github.skepter.skepbot.modules;

import java.io.IOException;

import io.github.skepter.skepbot.WebsiteGetters;

public class RandomFact extends Module {

	public RandomFact() {
		super(PatternType.MATCHES, ".*tell me.*random fact.*");
	}

	@Override
	public String output() {
		try {
			return WebsiteGetters.getRandomFact();
		} catch (IOException e) {
			return "I tried to think up a random fact, but I couldn't think of anything.";
		}
	}

}
