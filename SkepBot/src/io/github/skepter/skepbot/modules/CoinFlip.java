package io.github.skepter.skepbot.modules;

import java.util.concurrent.ThreadLocalRandom;

public class CoinFlip extends Module {
	
	public CoinFlip() {
		super(PatternType.CONTAINS, "flip a coin");
	}

	@Override
	public String output() {
		return ThreadLocalRandom.current().nextBoolean() ? "It's heads!" : "It's tails!";
	}
	

}
