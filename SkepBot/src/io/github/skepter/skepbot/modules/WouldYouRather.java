package io.github.skepter.skepbot.modules;

import java.io.IOException;

import io.github.skepter.skepbot.WebsiteGetters;

public class WouldYouRather extends Module {

	public WouldYouRather() {
		super("play would you rather", PatternType.CONTAINS, "would you rather");
	}
	
	@Override
	public String output() {
		try {
			return WebsiteGetters.getWouldYouRather();
		} catch (IOException e) {
			return "I tried to think of a \"would you rather\" question, but I couldn't think of anything :(";
		}
	}

}
