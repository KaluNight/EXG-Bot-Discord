package ch.exgBot;

import java.util.TimerTask;

import me.philippheuer.twitch4j.endpoints.StreamEndpoint;
import me.philippheuer.twitch4j.model.Channel;
import net.dv8tion.jda.core.entities.TextChannel;

public class StreamChecker extends TimerTask {
	
	private static boolean wasOnline;
	
	private static TextChannel annonce_channel;

    @Override
    public void run() {
        Channel channel = Main.getTwitchclient().getChannelEndpoint().getChannel("KaluNight");
        StreamEndpoint stream = Main.getTwitchclient().getStreamEndpoint();

        if(stream.isLive(channel) && !wasOnline) {	
        	wasOnline = true;
        	annonce_channel.sendMessage("KaluNight est en live ! Venez donc le voir !").complete();
            
        } else if (!stream.isLive(channel) && wasOnline) {
            wasOnline = false;
        }
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
}
