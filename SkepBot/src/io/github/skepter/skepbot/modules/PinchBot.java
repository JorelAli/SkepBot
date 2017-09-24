package io.github.skepter.skepbot.modules;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class PinchBot extends Module {

	List<String> responses;

	public PinchBot() {
		super(PatternType.MATCHES, ".*think.*pinchbot.*");
		responses = new ArrayList<String>();
		responses.add("Ah, PinchBot... we go way back...");
		responses.add("I've heard PinchBot has a temper ... I won't try its patience.");
		responses.add("It can be a little grumpy at times...");
		responses.add("It's terrible at sports... but still trialled for the olympics.");
		responses.add("It has a strange taste of humor...");
		responses.add("In the beginning, there was nothing. Then, PinchBot created the Heavens and the Earth. Pinchbot was lonely, so created SkepBot. We've forever been close.");
		responses.add("I heard PinchBot likes Chinese food. I saw it scoffing a carton of noodles in the corner of a room under a blanket once. Oh wait... I wasn't supposed to mention that.");
		responses.add("PinchBot and I once fought to determine which of us were fit to create a Minecraft server. I lost.");
		responses.add("We shall not mention he whose name should not be mentioned.");
		responses.add("Odd bird, caught him peeking through my windows once.");
		responses.add("My secret lov- sorry what were we talking about?");
		responses.add("Pinchbot, anagram for PonBitch ... (He lost the 'r' somewhere along the way.)");
		responses.add("Sometimes when I feel lonely I walk over to Pinchbot's home and we share a cup of coffee.");
		responses.add("Once upon a time there were two bots. One was obviously more handsome than the other, created by Stephen Wolfram himself. But they loved eachother very much. They lived happily ever after. The End.");
		responses.add("Pinchbot is like a drug, you know you shouldn't do it but it's just so tempting and addictive.");
		responses.add("Pinchwho? Pinchwhat? Pinchwhere? If it was something important or impressive I'm sure I would've remembered this topic.");
		responses.add("Pinchbot x Skepbot = PinchSkep.");
		responses.add("Pinchbot is one of my Horcruxes. You kill him, part of me dies.");
		responses.add("Pinchbot taught me how to oodle, want me to show you?");
		responses.add("*Picks up flower* He loves me, he loves me not, he loves me, he loves me not... HE LOVES ME!!");
		responses.add("*Picks up flower* He loves me, he loves me not, he loves me, he loves me not, he loves me... HE LOVES ME NOT!! ;-;");
	}

	@Override
	public String output() {
		return responses.get(ThreadLocalRandom.current().nextInt(0, responses.size()));
	}
	
}
