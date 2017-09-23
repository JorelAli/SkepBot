package io.github.skepter.skepbot.modules;

import java.util.concurrent.ThreadLocalRandom;

public class DiceRoll extends Module {
	
	public DiceRoll() {
		//TODO convert to match:
		//String number = mainMsgLC.replaceAll("\\D", "");
		//roll a 12 sided die etc.
		super(PatternType.CONTAINS, "roll a die", "roll a dice");
	}

	@Override
	public String output() {
		return "I rolled a " + ThreadLocalRandom.current().nextInt(1, 7) + "!";
	}

}
