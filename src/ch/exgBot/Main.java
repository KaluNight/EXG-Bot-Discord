package ch.exgBot;

import me.philippheuer.twitch4j.TwitchClient;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;

public class Main {

	private static JDA jda;
	
	private static TwitchClient twitchclient;
	
	private static String[] args;
	
	public static void main(String[] args) {
		try {
			jda = new JDABuilder(AccountType.BOT).setToken(args[0]).build();
		} catch (IndexOutOfBoundsException e) {
			System.err.println("You must provide a token.");
			return;
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		
		setArgs(args);
		
		jda.addEventListener(new EventListener());
		
	}
	

	public static JDA getJda() {
		return jda;
	}


	public static TwitchClient getTwitchclient() {
		return twitchclient;
	}


	public static void setTwitchclient(TwitchClient twitchclient) {
		Main.twitchclient = twitchclient;
	}


	public static String[] getArgs() {
		return args;
	}


	public static void setArgs(String[] args) {
		Main.args = args;
	}

}
