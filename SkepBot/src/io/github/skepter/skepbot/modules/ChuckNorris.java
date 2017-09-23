package io.github.skepter.skepbot.modules;

import java.io.IOException;

import io.github.skepter.skepbot.WebsiteGetters;

public class ChuckNorris extends Module {

	public ChuckNorris() {
		super(PatternType.MATCHES, ".*tell me.*chuck norris joke.*");
	}
	
	@Override
	public String output() {
		try {
			return WebsiteGetters.getChuckNorrisJoke();
		} catch (IOException e) {
			return "I tried to think up a Chuck Norris joke, but I couldn't think of anything :/";
		}
	}

}
