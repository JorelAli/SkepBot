package io.github.skepter.skepbot.modules;

public abstract class Module {

	enum PatternType {
		STARTS_WITH, CONTAINS, MATCHES;
	}
	
	protected final String input;
	protected final String username;
	
	private final PatternType type;
	private final String[] patterns;
	
	public Module(String username, String input, PatternType type, String... patterns) {
		this.username = username;
		this.input = input;
		this.type = type;
		this.patterns = patterns;
	}
	
	private boolean startsWith() {
		for(String string : patterns) {
			if(!input.toLowerCase().startsWith(string.toLowerCase())) {
				return false;
			}
		}
		return true;
	}
	
	private boolean contains() {
		for(String string : patterns) {
			if(!input.toLowerCase().contains(string.toLowerCase())) {
				return false;
			}
		}
		return true;
	}
	
	private boolean matches() {
		for(String string : patterns) {
			if(!input.toLowerCase().matches(string)) {
				return false;
			}
		}
		return true;
	}
	
	public boolean isReady() {
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
	
	public abstract String output();
	
}
