package io.github.skepter.skepbot.modules;

import java.util.concurrent.ThreadLocalRandom;

public class DiceRoll extends Module {
	
	public DiceRoll() {
		super(PatternType.MATCHES, ".*roll.*die.*", ".*roll.*dice.*");
	}

	@Override
	public String output() {
		
		String number = input.toLowerCase().replaceAll("\\D", "");
		try {
			long i = Long.parseLong(number);
			return "I rolled a " + ThreadLocalRandom.current().nextLong(1, i + 1) + "!";
		} catch(NumberFormatException e) {
			return "I rolled a " + ThreadLocalRandom.current().nextInt(1, 7) + "!";
		}
		
	}

}
