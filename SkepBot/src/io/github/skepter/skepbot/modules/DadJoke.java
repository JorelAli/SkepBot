package io.github.skepter.skepbot.modules;

import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

import io.github.skepter.skepbot.WebsiteGetters;

public class DadJoke extends Module {

	public DadJoke() {
		super(PatternType.MATCHES, ".*tell me.*dad joke.*");
	}

	@Override
	public String output() {
		if (ThreadLocalRandom.current().nextInt(0, 1000000) == 0) {
			return username + " just activated the special 1 in a million joke! If you don't know SQL, just put NoSQL on your resume!";
		}
		try {
			return WebsiteGetters.getDadJoke();
		} catch (IOException e) {
			return "I tried to think up a dad joke, but I couldn't think of anything.";
		}
	}

}
