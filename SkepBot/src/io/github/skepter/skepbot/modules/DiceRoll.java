package io.github.skepter.skepbot.modules;

import java.math.BigInteger;
import java.util.concurrent.ThreadLocalRandom;

public class DiceRoll extends Module {
	
	final String[] expressions = {"insane", "mental", "crazy", "a psycho", "stupid", "trying to confuse me", "trying to confuse yourself", "trying to perform the impossible"};
	
	public DiceRoll() {
		super(PatternType.MATCHES, ".*roll.*die.*", ".*roll.*dice.*");
	}

	@Override
	public String output() {
		
		String number = input.toLowerCase().replaceAll("\\D", "");
		if(number.equals("0")) {
			return "Wait, you want to roll a 0 sided die? Are you " + expressions[ThreadLocalRandom.current().nextInt(0, expressions.length)] + "?";
		}
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
