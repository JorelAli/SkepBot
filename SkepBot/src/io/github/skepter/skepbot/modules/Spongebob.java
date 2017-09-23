package io.github.skepter.skepbot.modules;

import java.util.concurrent.ThreadLocalRandom;

public class Spongebob extends Module {

	public Spongebob() {
		super(PatternType.STARTS_WITH, "spongebob ");
	}

	@Override
	public String output() {
		StringBuilder builder = new StringBuilder();
		for (char c : input.toLowerCase().substring(10).toCharArray()) {
			builder.append(ThreadLocalRandom.current().nextBoolean() ? Character.toUpperCase(c) : c);
		}
		return builder.toString();
	}

}
