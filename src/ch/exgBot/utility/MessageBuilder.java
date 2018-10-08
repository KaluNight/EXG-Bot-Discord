package ch.exgBot.utility;

import java.awt.Color;

import me.philippheuer.twitch4j.endpoints.StreamEndpoint;
import me.philippheuer.twitch4j.model.Channel;
import me.philippheuer.twitch4j.model.Stream;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.entities.MessageEmbed.Field;

public class MessageBuilder {
	
	public static MessageEmbed createInfoStreamMessage(Channel channel, StreamEndpoint stream) {
    	Stream actualStream = stream.getByChannel(channel);
		
		EmbedBuilder message = new EmbedBuilder();
		message.setAuthor(channel.getDisplayName(), null, channel.getLogo());
		message.setTitle(channel.getStatus(), "https://www.twitch.tv/terrasomnia");
		message.setImage(actualStream.getPreview().getMedium());
		
		Field field = new Field("Jeu", channel.getGame(), true);
		message.addField(field);
		
		field = new Field("Viewers", Integer.toString(actualStream.getViewers()), true);
		message.addField(field);
		
		message.setThumbnail(channel.getLogo());
		
		message.setColor(Color.GREEN);
		return message.build();
	}

}
