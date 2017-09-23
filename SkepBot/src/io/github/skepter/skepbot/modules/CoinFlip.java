package io.github.skepter.skepbot.modules;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Consumer;

public class CoinFlip extends Module {
	
	
	public CoinFlip() {
		//TODO convert to match:
		//String number = mainMsgLC.replaceAll("\\D", "");
		//roll a 12 sided die etc.
		super(PatternType.CONTAINS, "flip a coin");
	}

	@Override
	public String output() {
		Timer t = new Timer();
		
		Consumer<String> callback = (s) -> {return;};

		t.schedule(new TimerTask() {
			
			@Override
			public void run() {
				callback.accept("Flipping a coin");
				//sendMessage(channel, "Flipping a coin...");
			}
		}, 0);
		//scheduleLater(() -> {sendMessage(channel, ThreadLocalRandom.current().nextBoolean() ? "It's heads!" : "It's tails!");}, ThreadLocalRandom.current().nextInt(1, 5));
		
		return "I rolled a " + ThreadLocalRandom.current().nextInt(1, 7) + "!";
	}
	

}
