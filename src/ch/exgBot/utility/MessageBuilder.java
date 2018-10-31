package ch.exgBot.utility;

import java.awt.Color;

import me.philippheuer.twitch4j.endpoints.StreamEndpoint;
import me.philippheuer.twitch4j.model.Channel;
import me.philippheuer.twitch4j.model.Stream;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.entities.MessageEmbed.Field;

public class MessageBuilder {
	
	private static final String LINK_BANNER = "https://static-cdn.jtvnw.net/jtv_user_pictures/b93e4a34-4b7d-40bb-8ff4-74557d31542d-profile_banner-480.png";
	
	public static MessageEmbed createInfoStreamMessage(Channel channel, StreamEndpoint stream) {
    	Stream actualStream = stream.getByChannel(channel);
		
		EmbedBuilder message = new EmbedBuilder();
		message.setAuthor(channel.getDisplayName(), null, channel.getLogo());
		message.setTitle(channel.getStatus(), "https://www.twitch.tv/terrasomnia");
		
		Field field = new Field("Jeu", channel.getGame(), true);
		message.addField(field);
		
		field = new Field("Viewers", Integer.toString(actualStream.getViewers()), true);
		message.addField(field);
		
		message.setThumbnail(channel.getLogo());
		
		long inc = SaveSystem.loadActualNumberOfInc();
		
		message.setImage(LINK_BANNER);
		
		System.out.println("Message numéro " + inc + " crée, Nom du live : " + channel.getDisplayName());
		inc++;
		
		SaveSystem.saveActualNumberOfInc(inc);
		
		message.setColor(Color.GREEN);
		
		return message.build();
	}

}
