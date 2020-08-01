package cl.eternaletulf.bot.TwitchEvents;

import com.github.philippheuer.events4j.simple.SimpleEventHandler;
import com.github.twitch4j.chat.events.channel.ChannelMessageEvent;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.MessageBuilder;
import org.springframework.beans.factory.annotation.Autowired;

//@Service
public class WriteToDiscordMessage {

    SimpleEventHandler eventHandler;

    @Autowired
    DiscordApi discordApi;

    public WriteToDiscordMessage(SimpleEventHandler eventHandler) {
        eventHandler.onEvent(ChannelMessageEvent.class, this::onChannelMessage);
        eventHandler.onEvent(ChannelMessageEvent.class, this::showOnDiscord);
    }

    /**
     * Subscribe to the ChannelMessage Event and write the output to the console
     */
    private void onChannelMessage(ChannelMessageEvent event) {
        System.out.printf(
            "Channel [%s] - User[%s] - Message [%s]%n",
            event.getChannel().getName(),
            event.getUser().getName(),
            event.getMessage()
        );
    }

    public void showOnDiscord(ChannelMessageEvent event) {
        TextChannel channel = discordApi.getChannelById("738194356682489868").get().asTextChannel()
            .get();

        new MessageBuilder()
            .append(event.getMessage())
            .send(channel);
    }
}
