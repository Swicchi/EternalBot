package cl.eternaletulf.bot.TwitchEvents;

import cl.eternaletulf.bot.bots.DiscordBot;
import com.github.philippheuer.events4j.simple.SimpleEventHandler;
import com.github.twitch4j.chat.events.channel.ChannelMessageEvent;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.MessageBuilder;


public class WriteToDiscordMessage {

    private String channelId = "738194356682489868";

    DiscordBot discordBot;

    public WriteToDiscordMessage(SimpleEventHandler eventHandler,
        DiscordBot discordBot) {
        this.discordBot = discordBot;
        eventHandler.onEvent(ChannelMessageEvent.class, event -> {
            showOnDiscord(event);
        });
    }

    public void showOnDiscord(ChannelMessageEvent event) {
        TextChannel channel = discordBot.getDiscordApi().getChannelById(channelId).get()
            .asTextChannel()
            .get();

        new MessageBuilder()
            .append(event.getMessage())
            .send(channel);
    }
}
