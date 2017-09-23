package io.github.skepter.skepbot.modules;

import java.util.concurrent.ThreadLocalRandom;

public class PinchBot extends Module {

	String[] responses = { "Ah, PinchBot... we go way back...",
			"I've heard PinchBot has a temper ... I won't try its patience", "It can be a little grumpy at times...",
			"It's terrible at sports... but was still trialled for the olympics", "It has a strange taste of humor...",
			"In the beginning, there was nothing. Then, PinchBot created the Heavens and the Earth. Pinchbot was lonely, so created SkepBot. We've forever been close.",
			"I heard PinchBot likes Chinese food. I saw it scoffing a carton of noodles in the corner of a room under a blanket once. Oh wait... I wasn't supposed to mention that.",
			"PinchBot and I once fought to determine which of us were fit to create a Minecraft server. I lost."};

	public PinchBot() {
		super(PatternType.MATCHES, ".*think.*pinchbot.*");
	}

	@Override
	public String output() {
		return responses[ThreadLocalRandom.current().nextInt(0, responses.length)];
	}

}
