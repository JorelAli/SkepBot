package io.github.skepter.skepbot.modules;

import java.util.concurrent.ThreadLocalRandom;

public class RockPaperScissors extends Module {
	
	public RockPaperScissors() {
		super("play rock, paper, scissors", PatternType.CONTAINS, "rock", "paper", "scissors");
	}

	@Override
	public String output() {
		
		if(ThreadLocalRandom.current().nextInt(0, 1000) == 0) {
			return "I choose you, Pikachu!";
		}
		
		int rock = getOccurances(input.toLowerCase(), "rock");
		int paper = getOccurances(input.toLowerCase(), "paper");
		int scissors = getOccurances(input.toLowerCase(), "scissors");
		
		String response = "";
		if(rock != 1) {
			response = "rock";
		} else if(paper != 1) {
			response = "paper";
		} else if(scissors != 1) {
			response = "scissors";
		} else {
			return "I couldn't interpret your choice of rock paper scissors, " + username;
		}
		
		String choice = getChoice();
		
		if(response.equals(choice)) {
			return "I chose " + choice + ", it's a draw!";
		} else if((response.equals("rock") && choice.equals("scissors")) || (response.equals("scissors") && choice.equals("paper")) || (response.equals("paper") && choice.equals("rock"))) {
			return "I chose " + choice + ", you win!";
		} else {
			return "I chose " + choice + ", you lose!";
		}
	}
	
	private int getOccurances(String mainString, String thingToSearchFor) {
		int count = 0;
		while(mainString.contains(thingToSearchFor)) {
			mainString = mainString.replaceFirst(thingToSearchFor, "");
			count++;
		}
		return count;
	}

	private String getChoice() {
		switch(ThreadLocalRandom.current().nextInt(0, 3)) {
			case 0:
				return "rock";
			case 1:
				return "paper";
			case 2:
				return "scissors";
		}
		return null;
	}
	
}
