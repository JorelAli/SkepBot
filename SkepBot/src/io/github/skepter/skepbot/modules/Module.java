package io.github.skepter.skepbot.modules;

import io.github.skepter.skepbot.SkepBot;

public abstract class Module {

	enum PatternType {
		STARTS_WITH, CONTAINS, MATCHES;
	}
	
	protected String input = null;
	protected String username = null;
	
	private final PatternType type;
	private final String[] patterns;
	
	public Module(String function, PatternType type, String... patterns) {
		SkepBot.functions.add(function);
		this.type = type;
		this.patterns = patterns;
	}
	
	private final boolean startsWith() {
		for(String string : patterns) {
			if(input.toLowerCase().startsWith(string.toLowerCase())) {
				return true;
			}
		}
		return false;
	}
	
	private final boolean contains() {
		for(String string : patterns) {
			if(input.toLowerCase().contains(string.toLowerCase())) {
				return true;
			}
		}
		return false;
	}
	
	private final boolean matches() {
		for(String string : patterns) {
			if(input.toLowerCase().matches(string)) {
				return true;
			}
		}
		return false;
	}
	
	public final void init(String username, String input) {
		this.username = username;
		this.input = input;
	}
	
	public final boolean isReady() {
		if(username == null || input == null) {
			System.out.println("Module not initialised!");
			return false;
		}

		if(!extraConditions()) {
			return false;
		}
		switch(type) {
			case CONTAINS:
				return contains();
			case MATCHES:
				return matches();
			case STARTS_WITH:
				return startsWith();
			default:
				return false;
		}
	}

	public boolean extraConditions() {
		return true;
	}
	
	public abstract String output();
	
	public String[] extraOutputs() {
		return null;
	}
	
}
