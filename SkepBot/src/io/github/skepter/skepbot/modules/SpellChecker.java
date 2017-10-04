package io.github.skepter.skepbot.modules;

import java.io.IOException;

import io.github.skepter.skepbot.WebsiteGetters;

public class SpellChecker extends Module {

	public SpellChecker() {
		super("spellcheck words", PatternType.STARTS_WITH, "spellcheck ");
	}

	@Override
	public String output() {
		String query = input.substring(11);
		try {
			return "The correct spelling of " + query + " is " + WebsiteGetters.spellingCorrection(query);
		} catch (IOException e) {
			return "I couldn't spell check that for you :/";
		}
	}

}
