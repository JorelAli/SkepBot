package io.github.skepter.skepbot.modules;

public class Oodler extends Module {

	public Oodler() {
		super("oodle phrases", PatternType.STARTS_WITH, "oodle ");
	}

	@Override
	public String output() {
		return input.substring(6).replaceAll("[aeiou]", "oodle").replaceAll("[AEIOU]", "Oodle");
	}

}
