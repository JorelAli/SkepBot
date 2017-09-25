package io.github.skepter.skepbot;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Logger;

import javax.security.auth.login.LoginException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;

import io.github.skepter.skepbot.modules.ChuckNorris;
import io.github.skepter.skepbot.modules.CoinFlip;
import io.github.skepter.skepbot.modules.DadJoke;
import io.github.skepter.skepbot.modules.DiceRoll;
import io.github.skepter.skepbot.modules.Dictionary;
import io.github.skepter.skepbot.modules.Leet;
import io.github.skepter.skepbot.modules.Magic8;
import io.github.skepter.skepbot.modules.Module;
import io.github.skepter.skepbot.modules.NumberFact;
import io.github.skepter.skepbot.modules.Oodler;
import io.github.skepter.skepbot.modules.PinchBot;
import io.github.skepter.skepbot.modules.RandomFact;
import io.github.skepter.skepbot.modules.RockPaperScissors;
import io.github.skepter.skepbot.modules.Spongebob;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.events.ReconnectedEvent;
import net.dv8tion.jda.core.events.ResumedEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.exceptions.RateLimitedException;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

/*
 * Basically, my Skype bot, except the discord version to play around with on the pinch discord server
 */
public class SkepBot extends ListenerAdapter {

	static String WOLFRAM_ALPHA_ID = ""; 
	private static String TOKEN = ""; 
	
	static String[] guilds = {"Pinchcliffe SMP", "Skepter's Server", "Blazers"};
	//String[] channels = {"pinchcliffesmp", "staff", "general"};
	
	static String[] users = {"Bluey3004"};
	
	// Hangman
	private boolean playingHangman = false;
	private String hangmanWord = "";
	private Set<Character> guessedCharacters;
	private int guessesRemaining; 
	private Set<String> hints;
	
	//Online status
	private static final boolean DISPLAY_ONLINE_STATUS = false;
	private static final String ONLINE_MESSAGE = ">> SkepBot is now online <<";
	
	//Cooldowns
	Map<String, Long> cooldownsPerPerson = new WeakHashMap<String, Long>();
	private static final int COOLDOWN = 5;

	//Modules (Using a list preserves insertion order!
	static List<Module> modules;
	
	//Channels
	static MessageChannel pinchcliffesmpChannel;
	
	//List of stuff it can do 
	public static List<String> functions;
	
	private static void storeDefaultProperties(File file) {
		Properties prop = new Properties();
		OutputStream output = null;
		
		try {

			output = new FileOutputStream(file);

			// set the properties value
			prop.setProperty("discord_token", "");
			prop.setProperty("wolfram_alpha_id", "");
			prop.setProperty("servers", "Pinchcliffe SMP,Skepter's Server,Blazers");
			prop.setProperty("users", "Bluey3004");

			// save properties to project root folder
			prop.store(output, null);

		} catch (IOException io) {
			io.printStackTrace();
		} finally {
			if (output != null) {
				try {
					output.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
	}
	
	private static void loadProperties(File file) {
		Properties prop = new Properties();
		InputStream input = null;

		try {

			input = new FileInputStream(file);

			// load a properties file
			prop.load(input);

			WOLFRAM_ALPHA_ID = prop.getProperty("wolfram_alpha_id");
			TOKEN = prop.getProperty("discord_token");
			guilds = prop.getProperty("servers").split(",");
			users = prop.getProperty("users").split(",");

		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void onClose() {
		if(DISPLAY_ONLINE_STATUS) {
			pinchcliffesmpChannel.sendMessage(">> SkepBot is now offline <<").queue();
		}
    	System.exit(0);
	}
	
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws LoginException, IllegalArgumentException, InterruptedException, RateLimitedException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		
		System.out.println("Loading properties...");
		/*
		 * Load properties
		 */
		File file = new File(System.getProperty("user.dir") + File.separator + "skepbot.properties");
		if(!file.exists()) {
			storeDefaultProperties(file);
			JOptionPane.showMessageDialog(null, "Properties doesn't exist. Please fill out properties file before use", "SkepBot", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		} else {
			loadProperties(file);
		}
		System.out.println("Properties loaded!");
		
		//Create bot
		System.out.println("Building bot...");
		SkepBot bot = new SkepBot();
		JDA jda = new JDABuilder(AccountType.CLIENT).setToken(TOKEN).buildBlocking();
        jda.addEventListener(bot);
		System.out.println("Bot created!");

		//Register channels
		pinchcliffesmpChannel = jda.getTextChannelById(337772474894909450L);
        if(DISPLAY_ONLINE_STATUS)
        	pinchcliffesmpChannel.sendMessage(ONLINE_MESSAGE).queue();
		
		//Create interface
        System.out.println("Loading interface...");
        
        JFrame frame = new JFrame("SkepBot");
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frame.setBounds(0, 0, 250, 100);
        frame.setLocationRelativeTo(null);
        JButton button = new JButton("Click to turn off SkepBot");
        button.addActionListener((e) -> {onClose();});
        frame.getContentPane().add(button);
        frame.setVisible(true);
        System.out.println("Interface loaded.");
        
        //Loads modules dynamically from the modules package
        System.out.println("Loading modules...");
        functions = new ArrayList<String>();
        modules = new ArrayList<Module>();
        
        //https://stackoverflow.com/questions/1810614/getting-all-classes-from-a-package
        // Prepare.
        String packageName = "io.github.skepter.skepbot.modules";
        List<Class<Module>> moduleList = new ArrayList<Class<Module>>();
        URL root = SkepBot.class.getClassLoader().getResource(packageName.replace(".", "/"));

        // Filter .class files.
        File[] files = new File(root.getFile()).listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.endsWith(".class");
            }	
        });
        
        if(files != null) {
        	 // Find classes implementing Module.
            for (File f : files) {
                String className = f.getName().replaceAll(".class$", "");
                Class<?> cls = Class.forName(packageName + "." + className);
                if (Module.class.isAssignableFrom(cls)) {
                    moduleList.add((Class<Module>) cls);
                }
            }
            for(Class<Module> c : moduleList) {
            	if(c.getSimpleName().equals("Module"))
            		continue;
            	System.out.println("Adding " + c.getSimpleName());
            	modules.add(c.newInstance());
            }
            
		} else {
			// Guess we're gonna have to do it manually :(
			modules.add(new ChuckNorris());
			modules.add(new CoinFlip());
			modules.add(new DadJoke());
			modules.add(new DiceRoll());
			modules.add(new Dictionary());
			modules.add(new Leet());
			modules.add(new Magic8());
			modules.add(new NumberFact());
			modules.add(new Oodler());
			modules.add(new PinchBot());
			modules.add(new RandomFact());
			modules.add(new RockPaperScissors());
			modules.add(new Spongebob());

		}
        System.out.println("All set!");
        
	}
	
	@Override
	public void onResume(ResumedEvent event) {
		if(DISPLAY_ONLINE_STATUS)
        	pinchcliffesmpChannel.sendMessage(ONLINE_MESSAGE).queue();
	}
	
	@Override
	public void onReconnect(ReconnectedEvent event) {
		if(DISPLAY_ONLINE_STATUS)
        	pinchcliffesmpChannel.sendMessage(ONLINE_MESSAGE).queue();
	}
	
	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		//System.out.println(event.getMessage().toString());
		MessageChannel channel = event.getChannel();
		String message = event.getMessage().getContent();
		
		//Takes into account the : from pinchbot
		//This returns the main message they send, for example "hello"
		String mainMsg = "";
		if(event.getAuthor().getName().equals("SkepBot") && event.getMessage().getContent().startsWith("**")) {
			mainMsg = event.getAuthor().getName().equals("SkepBot") ? (message.contains(":") ? message.substring(message.indexOf(":") + 2) : message) : message;
		} else {
			mainMsg = event.getAuthor().getName().equals("PinchBot") ? (message.contains(":") ? message.substring(message.indexOf(":") + 2) : message) : message;
		}
			
		
		//Messages MUST now start with "SkepBot, <query>"
		
		if(mainMsg.toLowerCase().startsWith("skepbot, ")) {
			mainMsg = mainMsg.substring(9);
		} else if(mainMsg.toLowerCase().startsWith("skepbot ")) {
			mainMsg = mainMsg.substring(8);
		} else {
			return;
		}
		
		String mainMsgLC = mainMsg.toLowerCase();
				
		//Prevent NPEs - This bot is only designed to run with the pinchcliffesmp chat, not PMs
		if((event.getGuild() == null || event.getTextChannel() == null) && channel.getType() != ChannelType.PRIVATE) {
			return;
		}
		
		boolean checkPublicChannels = false;
		try {
			checkPublicChannels = (Arrays.stream(guilds).anyMatch(event.getGuild().getName()::equals)/* && Arrays.stream(channels).anyMatch(event.getTextChannel().getName()::equals)*/);
		} catch(NullPointerException e) {}
		
		//Only available for pinch, lets me communicate as normal elsewhere without random NPEs
		if(checkPublicChannels || (Arrays.stream(users).anyMatch(event.getChannel().getName()::equals))) {
			Logger.getGlobal().info(message);
			String username = event.getAuthor().getName();
			if(message.contains(":") && (event.getAuthor().getName().equals("PinchBot") || (event.getAuthor().getName().equals("SkepBot")))) {
				username = message.split(":")[0];
				//Remove the annoying ** before and after the player's name
				username = username.replace("*", "");
				//System.out.println("Minecraft username: " + username);
			}
			
			long cooldownTime = cooldownsPerPerson.getOrDefault(username, 0L);
			//Cooldown system where you can't request things for ~10 seconds
			
			long averageCooldown = ((cooldownTime - System.currentTimeMillis()) / 1000); //Gives a "human readable" estimate of how long they have to go
			if(System.currentTimeMillis() > cooldownTime || isSkepter(event) || averageCooldown == 0 || playingHangman) {
				cooldownsPerPerson.put(username, System.currentTimeMillis() + (COOLDOWN * 1000));
								
				for(Module module : modules) {
					module.init(username, mainMsg);
					if(module.isReady()) {
						if(module.output() != null) {
							sendMessage(channel, module.output());
						}
						if(module.extraOutputs() != null) {
							for(String output : module.extraOutputs()) {
								sendMessage(channel, output);
							}
						}
						return;
					}
				}
								
				if(playingHangman) {
					if(mainMsgLC.length() == 1 || mainMsgLC.equalsIgnoreCase(hangmanWord)) {
						
						//They guess the word correctly in one go
						if(mainMsgLC.equalsIgnoreCase(hangmanWord)) {
							sendMessage(channel, "You're right " + username + ", the word was " + hangmanWord.toLowerCase());
							playingHangman = false;
							return;
						}
						
						//Get the character
						char character = mainMsgLC.toUpperCase().toCharArray()[0];
						
						//They've already guessed this letter
						if(guessedCharacters.contains(character)) {
							sendMessage(channel, "You've already guessed this letter! (" + character + ")");
							sendMessage(channel, "You have " + guessesRemaining + " attempts remaining: " + Arrays.deepToString(guessedCharacters.toArray()));
							return;
						} else {
							//Their guess
							guessedCharacters.add(character);
							
							//The resultant string (e.g. he--o)
							StringBuilder builder = new StringBuilder("[^");
							guessedCharacters.forEach(builder::append);
							builder.append("]");
							String resultant = hangmanWord.replaceAll(builder.toString(), "-");
							
							//If they now guessed it correctly from that last letter
							if(resultant.equalsIgnoreCase(hangmanWord)) {
								sendMessage(channel, "You're right " + username + "! The word was " + hangmanWord.toLowerCase() + "!");
								playingHangman = false;
								return;
							}
							
							//If the letter was correct
							if(hangmanWord.contains(Character.toString(character))) {
								sendMessage(channel, "You're right: " + resultant);
								sendMessage(channel, "You have " + guessesRemaining + " attempts remaining: " + Arrays.deepToString(guessedCharacters.toArray()));
								return;
							} else {
								//They're wrong
								sendMessage(channel, "You're wrong: " + resultant);
								guessesRemaining--;
								if(guessesRemaining == 0) {
									sendMessage(channel, "You've lost! The word was " + hangmanWord.toLowerCase() + "!");
									playingHangman = false;
								} else {
									sendMessage(channel, "You have " + guessesRemaining + " attempts remaining: " + Arrays.deepToString(guessedCharacters.toArray()));
								}
								return;
							}
						}
					} else {
						//HINTS
						if(mainMsg.contains(" ")) {
							if(mainMsgLC.contains("help") || mainMsgLC.contains("hint")) {
								//Prevent them hinting more than once
								if(hints.contains(username)) {
									sendMessage(channel, username + ", you have already used a hint!");
									return;
								}
								
								//Don't let them lose the game
								if(guessesRemaining == 1) {
									sendMessage(channel, "You only have one guess left! You can't use a hint without losing!");
									return;
								}
								
								//Hint stuff
								hints.add(username);
								guessesRemaining--;
								
								//Find the difference in characters between what they've guessed and the actual word
								Set<Character> hangmanWordChars = new HashSet<Character>();
								for(char c : hangmanWord.toCharArray()) {
									hangmanWordChars.add(Character.toUpperCase(c));
								}
								Set<Character> guessedChars = new HashSet<Character>();
								guessedChars.addAll(guessedCharacters);
								
								hangmanWordChars.removeAll(guessedChars);
								
								//Generate a random guess from characters they haven't used yet
								char guess = (char) hangmanWordChars.toArray()[ThreadLocalRandom.current().nextInt(0, hangmanWordChars.size())];
								guessedCharacters.add(guess);
								
								//Check if that guess was the result
								StringBuilder builder = new StringBuilder("[^");
								guessedCharacters.forEach(builder::append);
								builder.append("]");
								String resultant = hangmanWord.replaceAll(builder.toString(), "-");
								
								sendMessage(channel, username + " used a hint (costing one guess): [" + guess + "]: " + resultant);
								
								if(resultant.equalsIgnoreCase(hangmanWord)) {
									sendMessage(channel, "You're right " + username + "! The word was " + hangmanWord.toLowerCase() + "!");
									playingHangman = false;
									return;
								}
								
								sendMessage(channel, "You have " + guessesRemaining + " attempts remaining: " + Arrays.deepToString(guessedCharacters.toArray()));
							}
							return;
						}
						//INCORRECT GUESS
						sendMessage(channel, "You're wrong, the word wasn't " + mainMsg);
						guessesRemaining--;
						if(guessesRemaining == 0) {
							sendMessage(channel, "You've lost! The word was " + hangmanWord.toLowerCase() + "!");
							playingHangman = false;
						} else {
							sendMessage(channel, "You have " + guessesRemaining + " attempts remaining: " + Arrays.deepToString(guessedCharacters.toArray()));
						}
						return;
					}
				}
				
				if(mainMsgLC.contains("hangman")) {
					if(!playingHangman) {
						playingHangman = true;
						try {
							guessedCharacters = new HashSet<Character>();
							hints = new HashSet<String>();
							guessesRemaining = 10;
							hangmanWord = WebsiteGetters.randomNoun().toUpperCase();
							System.out.println(hangmanWord);
							sendMessage(channel, "I've thought up a word " + hangmanWord.length() + " letters long");
							sendMessage(channel, "You have 10 attempts remaining");
						} catch (IOException e) {
							sendMessage(channel, "I tried to think up a word, but couldn't think of anything *Cries*");
							playingHangman = false;
						}
					}
					
				
				} else if(mainMsgLC.contains("would you rather")) {
					try {
						sendMessage(channel, WebsiteGetters.getWouldYouRather());
					} catch (IOException e) {
						sendMessage(channel, "I tried to think of a \"would you rather\" question, but I couldn't think of anything :(");
					}
					return;
				} else if(mainMsgLC.endsWith("?")) {
					try {
						sendMessage(channel, WebsiteGetters.shortAnswersWolframAlpha(mainMsgLC));
					} catch(IOException e) {
						sendMessage(channel, "I couldn't understand that, sorry");
						e.printStackTrace();
					}
				} else {
					try {
						sendMessage(channel, WebsiteGetters.shortAnswersWolframAlpha(mainMsgLC));
					} catch(IOException e) {
						sendMessage(channel, "I couldn't understand that, sorry");
						e.printStackTrace();
					}
				} 
				
			} else {
				sendMessage(channel, username + ", you're still on cooldown for around " + averageCooldown + " seconds");
			}
			
		}
	}
	
	private void sendMessage(MessageChannel channel, String str) {
		if(str.length() > 2000) {
			return;
		}
		if(str.length() > 256) {
			channel.sendMessage(str.substring(0, 256)).queue();
			channel.sendMessage(str.substring(256, str.length())).queue();
		} else {
			channel.sendMessage(str).queue();
		}
	}
	
	private boolean isSkepter(MessageReceivedEvent event) {
		return event.getMessage().getAuthor().getId().equals("125375393334034433");
	}
}
