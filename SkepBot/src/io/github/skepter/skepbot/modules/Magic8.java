package io.github.skepter.skepbot.modules;

import java.util.concurrent.ThreadLocalRandom;

public class Magic8 extends Module {

	String[] responses = { "It is certain", "It is decidedly so", "Without a doubt", "Yes definitely",
			"You may rely on it", "As I see it, yes", "Most likely", "Outlook good", "Yes", "Signs point to yes",
			"Reply hazy try again", "Ask again later", "Better not tell you now", "Cannot predict now",
			"Concentrate and ask again", "Don't count on it", "My reply is no", "My sources say no",
			"Outlook not so good", "Very doubtful" };
	
	public Magic8() {
		super(PatternType.STARTS_WITH, "magic8");
	}
	
	@Override
	public boolean extraConditions() {
		return input.endsWith("?");
	}
	
	@Override
	public String output() {
		return responses[ThreadLocalRandom.current().nextInt(0, responses.length)];
	}

}
