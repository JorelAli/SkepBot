package io.github.skepter.skepbot.modules;

import java.io.IOException;

import io.github.skepter.skepbot.WebsiteGetters;

public class Insult extends Module {

	public Insult() {
		super("insult you", PatternType.CONTAINS, "insult me", "roast me");
	}

	@Override
	public String output() {
		try {
			return username + ", " + WebsiteGetters.getInsult().toLowerCase();
		} catch (IOException e) {
			e.printStackTrace();
			return "You are worse than a smelly pile of poo";
		}
	}

}
