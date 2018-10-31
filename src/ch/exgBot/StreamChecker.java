package ch.exgBot;

import java.util.ArrayList;
import java.util.Random;
import java.util.TimerTask;

import ch.exgBot.utility.MessageBuilder;
import me.philippheuer.twitch4j.endpoints.ChannelEndpoint;
import me.philippheuer.twitch4j.endpoints.StreamEndpoint;
import me.philippheuer.twitch4j.model.Channel;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.entities.Game.GameType;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.TextChannel;

public class StreamChecker extends TimerTask {

	private static final Random random = new Random();
	
	private static ChannelEndpoint channelEndpoint;
	
	private static StreamEndpoint streamEndpoint;

	private static boolean wasOnline;

	private static TextChannel annonce_channel;
	
	private static Message embendedMessage;

	private static final String[] LIST_QUOTE_IN_LIVE_WATCH = {"KaluNight jouer avec moi", "Aristo faire des blagues", "Enollia entrain de chanter",
			"Sylveøn joué à Pokémon", "Panda créer des dictatures", "Konrad faire du SpeedRun"};

	private static final String[] LIST_QUOTE_IN_LIVE_PLAY = {"avec le micro de Daxyys", "conduire le vaisseau avec Ozuki", "dancer avec Typraah"};
	
	private static final String[] LIST_QUOTE_NOT_IN_LIVE_WATCH = {"des galaxies", "une civilisation déchu", "des grenouilles sur un lac",
			"Ezreal en cachette"};
			
	private static final String[] LIST_QUOTE_NOT_IN_LIVE_PLAY = {"one-shot un adc", "flash 6 fois de suite", "endormir un jungler égaré",
			"courir derrière des papillons", "chasser Lux"};

	@Override
	public void run() {
		Channel channel = channelEndpoint.getChannel("Terrasomnia");
		
		boolean streamIsLive = streamEndpoint.isLive(channel);

		if(streamIsLive && !wasOnline) {
			wasOnline = true;

			annonce_channel.sendMessage("Hey @everyone ! Nous sommes actuellement en live ! Venez donc nous dire coucou !").queue();
			embendedMessage = annonce_channel.sendMessage(MessageBuilder.createInfoStreamMessage(channel, streamEndpoint)).complete();
		} else if (!streamIsLive && wasOnline) {
			wasOnline = false;
			embendedMessage = null;
		}
		
		if(streamIsLive) {
			ArrayList<Object> list = getRandomGameTypeAndQuotesInLive();
			Main.getJda().getPresence().setGame(Game.of((GameType)list.get(0), (String)list.get(1)));
			
			embendedMessage.editMessage(MessageBuilder.createInfoStreamMessage(channel, streamEndpoint)).queue();
		}else {
			ArrayList<Object> list = getRandomGameTypeAndQuotesNotInLive();
			Main.getJda().getPresence().setGame(Game.of((GameType)list.get(0), (String)list.get(1)));
		}
	}

	private static ArrayList<Object> getRandomGameTypeAndQuotesInLive() {

		ArrayList<Object> list = new ArrayList<>();

		int randomChoicePhrase = randInt(0, 2);

		if(randomChoicePhrase == 0) {
			list.add(GameType.WATCHING);
			list.add(LIST_QUOTE_IN_LIVE_WATCH[randInt(0, LIST_QUOTE_IN_LIVE_WATCH.length)]);
			return list;
		}else {
			list.add(GameType.DEFAULT);
			list.add(LIST_QUOTE_IN_LIVE_PLAY[randInt(0, LIST_QUOTE_IN_LIVE_PLAY.length)]);
			return list;
		}
	}
	
	private static ArrayList<Object> getRandomGameTypeAndQuotesNotInLive() {

		ArrayList<Object> list = new ArrayList<>();

		int randomChoicePhrase = randInt(0, 2);

		if(randomChoicePhrase == 0) {
			list.add(GameType.WATCHING);
			list.add(LIST_QUOTE_NOT_IN_LIVE_WATCH[randInt(0, LIST_QUOTE_NOT_IN_LIVE_WATCH.length)]);
			return list;
		}else {
			list.add(GameType.DEFAULT);
			list.add(LIST_QUOTE_NOT_IN_LIVE_PLAY[randInt(0, LIST_QUOTE_NOT_IN_LIVE_PLAY.length)]);
			return list;
		}
	}

	public static int randInt(int min, int max) {
		return random.nextInt(max - min) + min;
	}

	public static boolean isWasOnline() {
		return wasOnline;
	}

	public static void setWasOnline(boolean wasOnline) {
		StreamChecker.wasOnline = wasOnline;
	}

	public static TextChannel getAnnonceChannel() {
		return annonce_channel;
	}

	public static void setAnnonceChannel(TextChannel pannonce_channel) {
		annonce_channel = pannonce_channel;
	}

	public static ChannelEndpoint getChannelEndpoint() {
		return channelEndpoint;
	}

	public static void setChannelEndpoint(ChannelEndpoint channelEndpoint) {
		StreamChecker.channelEndpoint = channelEndpoint;
	}

	public static StreamEndpoint getStreamEndpoint() {
		return streamEndpoint;
	}

	public static void setStreamEndpoint(StreamEndpoint streamEndpoint) {
		StreamChecker.streamEndpoint = streamEndpoint;
	}
}
