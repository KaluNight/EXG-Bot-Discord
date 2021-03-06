package ch.exgBot;

import java.io.File;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import me.philippheuer.twitch4j.TwitchClientBuilder;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class EventListener extends ListenerAdapter{

	private static final char PREFIX = '>';

	private static final String ADMIN_ROLE_ADMIN = "492057292775096321";

	private static final String ID_STREAM_GUILD = "492047910931005450";

	private static final String ANNONCE_ID_CHANNEL = "492783834992082980";

	@Override
	public void onReady(ReadyEvent event) {

		Main.setTwitchclient(TwitchClientBuilder.init()
				.withClientId(Main.getArgs()[1])
				.withClientSecret(Main.getArgs()[2])
				.withCredential(Main.getArgs()[3])
				.withConfigurationDirectory(new File("config"))
				.withAutoSaveConfiguration(true)
				.connect());

		StreamChecker.setChannelEndpoint(Main.getTwitchclient().getChannelEndpoint());
		StreamChecker.setStreamEndpoint(Main.getTwitchclient().getStreamEndpoint());
		
		StreamChecker.setWasOnline(false);

		Timer timer = new Timer();
		TimerTask task = new StreamChecker();
		timer.schedule(task, 0, 60000);

		StreamChecker.setAnnonceChannel(Main.getJda().getGuildById(EventListener.getIdStreamGuild()).getTextChannelById(ANNONCE_ID_CHANNEL));
		Main.getJda().getTextChannelsByName("feedback-bot", true).get(0).sendMessage("Je suis Up !").complete();
	}

	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		String message = event.getMessage().getContentRaw();

		if (message.length() == 0 || message.charAt(0) != PREFIX) {
			return;
		}

		message = message.substring(1);
		
		if(message.substring(0, 4).equalsIgnoreCase("reco")) {
			Main.getTwitchclient().reconnect();
			event.getTextChannel().sendMessage("Reconnexion effectué !");
			return;
		}
		
		try {
			if (message.substring(0, 4).equalsIgnoreCase("stop")){
				User user = event.getAuthor();
				List<Guild> listGuild = user.getMutualGuilds();
				long id = user.getIdLong();

				for(int i = 0; i < listGuild.size(); i++) {
					if(listGuild.get(i).getId().equals(ID_STREAM_GUILD)) {
						Guild guild = listGuild.get(i);
						Member member = guild.getMemberById(id);
						List<Role> listRole = member.getRoles();

						for(int j = 0; j < listRole.size(); j++) {
							if(listRole.get(j).getId().equals(ADMIN_ROLE_ADMIN)) {
								Main.getJda().getTextChannelsByName("feedback-bot", true).get(0).sendMessage("Je suis down !").complete();
								Main.getJda().shutdown();
								Main.getTwitchclient().disconnect();
								System.exit(0);
							}
						}
					}
				}
			}
		}catch(StringIndexOutOfBoundsException e) {
			//Not a exception
		}

		if (message.substring(0, 2).equalsIgnoreCase("up")){
			Main.getJda().getTextChannelsByName("feedback-bot", true).get(0).sendMessage("Je suis Up !").queue();
			return;
		}
	}

	public static String getIdStreamGuild() {
		return ID_STREAM_GUILD;
	}
}
