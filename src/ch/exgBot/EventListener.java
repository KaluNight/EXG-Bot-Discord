package ch.exgBot;

import java.util.List;

import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class EventListener extends ListenerAdapter{
	
	private static final char PREFIX = '>';
	
	private static final String ID_STREAM_GUILD = "492047910931005450";
	
	private static final String ANNONCE_ID_CHANNEL = "492783834992082980";
	
	@Override
	public void onReady(ReadyEvent event) {
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
						if(listRole.get(j).getName().equals("Streamer")) {
							Main.getJda().getTextChannelsByName("feedback-bot", true).get(0).sendMessage("Je suis down !").complete();
							Main.getJda().shutdown();
							Main.getTwitchclient().disconnect();
							System.exit(0);
						}
					}
				}
			}
		}
	}

	public static String getIdStreamGuild() {
		return ID_STREAM_GUILD;
	}
}
