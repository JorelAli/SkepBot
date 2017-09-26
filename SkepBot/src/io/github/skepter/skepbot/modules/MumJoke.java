package io.github.skepter.skepbot.modules;

import java.io.IOException;

import io.github.skepter.skepbot.WebsiteGetters;

public class MumJoke extends Module {
		
	public MumJoke() {
		super("tell you a yo momma joke", PatternType.MATCHES, ".*tell me.*m[uoa]m joke.*", ".*yo m[uoa]mma.*");
	}

	@Override
	public String output() {
		try {
			return WebsiteGetters.getMumJoke();
		} catch (IOException e) {
			return "I tried to think up a yo momma joke, but I couldn't think of anything.";
		}
	}

}
