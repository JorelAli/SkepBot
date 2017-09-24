package io.github.skepter.skepbot.modules;

import java.math.BigInteger;
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
			if(number.equals("")) {
				return "I rolled a " + ThreadLocalRandom.current().nextInt(1, 7) + "!";
			} else {
				String num = new BigInteger(number).divide(new BigInteger(String.valueOf(ThreadLocalRandom.current().nextLong(1)))).toString();
				return "I rolled a " + num + "!";
			}
		}
		
	}

}
