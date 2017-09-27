package io.github.skepter.skepbot.modules;

import java.io.IOException;

import io.github.skepter.skepbot.WebsiteGetters;

public class PoshInsult extends Module {

	public PoshInsult() {
		super("insult you in a fancy way", PatternType.CONTAINS, "roast me");
	}

	@Override
	public String output() {
		try {
			return username + ", " + WebsiteGetters.getPoshInsult();
		} catch (IOException e) {
			e.printStackTrace();
			return username + ", Thou art a Goatish, Beetle-headed Barnacle.";
		}
	}

}
