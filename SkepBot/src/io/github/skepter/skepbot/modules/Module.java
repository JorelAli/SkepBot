package io.github.skepter.skepbot.modules;

public abstract class Module {

	enum PatternType {
		STARTS_WITH, CONTAINS, MATCHES;
	}
	
	protected String input = null;
	protected String username = null;
	
	private final PatternType type;
	private final String[] patterns;
	
	public Module(PatternType type, String... patterns) {
		this.type = type;
		this.patterns = patterns;
	}
	
	private boolean startsWith() {
		for(String string : patterns) {
			if(input.toLowerCase().startsWith(string.toLowerCase())) {
				return true;
			}
		}
		return false;
	}
	
	private boolean contains() {
		for(String string : patterns) {
			if(input.toLowerCase().contains(string.toLowerCase())) {
				return true;
			}
		}
		return false;
	}
	
	private boolean matches() {
		for(String string : patterns) {
			if(input.toLowerCase().matches(string)) {
				return true;
			}
		}
		return false;
	}
	
	public void init(String username, String input) {
		this.username = username;
		this.input = input;
	}
	
	public boolean extraConditions() {
		return true;
	}
	
	public boolean isReady() {
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
	
	public abstract String output();
	
}
