package cl.eternaletulf.bot.TwitchEvents;

import cl.eternaletulf.bot.bots.DiscordBot;
import com.github.philippheuer.events4j.simple.SimpleEventHandler;
import com.github.twitch4j.TwitchClient;
import com.github.twitch4j.events.ChannelGoLiveEvent;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.MessageBuilder;
import org.springframework.stereotype.Service;

//TODO esta wea se va a la chucha
@Service
public class WriteToDiscordMessage {

    TwitchClient twitchClient;

    private String channelId = "738194356682489868";

    public WriteToDiscordMessage() {
        SimpleEventHandler eventHandler = twitchClient.getEventManager()
            .getEventHandler(SimpleEventHandler.class);
        eventHandler.onEvent(ChannelGoLiveEvent.class, this::showOnDiscord);
    }

    public void showOnDiscord(ChannelGoLiveEvent event) {
        TextChannel channel = DiscordBot.getDiscordApi().getChannelById(channelId).get()
            .asTextChannel()
            .get();

        new MessageBuilder()
            .append(event.getStream().getUserName())
            .send(channel);
    }
}
