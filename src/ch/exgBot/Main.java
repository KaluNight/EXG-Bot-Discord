package ch.exgBot;

import java.util.Timer;
import java.util.TimerTask;

import me.philippheuer.twitch4j.TwitchClient;
import me.philippheuer.twitch4j.TwitchClientBuilder;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;

public class Main {

	private static JDA jda;
	
	private static TwitchClient twitchclient;
	
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
		
		jda.addEventListener(new EventListener());
		
		twitchclient = TwitchClientBuilder.init()
				.withClientId(args[1])
				.withClientSecret(args[2])
				.withCredential(args[3])
				.connect();
		
		StreamChecker.setWasOnline(false);
		
		Timer timer = new Timer();
		TimerTask task = new StreamChecker();
		timer.schedule(task, 0, 60000);
	}
	

	public static JDA getJda() {
		return jda;
	}


	public static TwitchClient getTwitchclient() {
		return twitchclient;
	}

}
