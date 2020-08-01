package cl.eternaletulf.bot.bots;

import cl.eternaletulf.bot.TwitchEvents.WriteToDiscordMessage;
import com.github.philippheuer.events4j.simple.SimpleEventHandler;
import com.github.twitch4j.TwitchClient;
import com.github.twitch4j.chat.events.channel.ChannelMessageEvent;
import com.github.twitch4j.events.ChannelGoLiveEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TwitchBot {

    final TwitchClient twitchClient;

    final WriteToDiscordMessage writeToDiscordMessage;

    public TwitchBot(TwitchClient twitchClient,
        WriteToDiscordMessage writeToDiscordMessage) {
        this.twitchClient = twitchClient;
        this.writeToDiscordMessage = writeToDiscordMessage;
        SimpleEventHandler eventHandler = twitchClient.getEventManager()
            .getEventHandler(SimpleEventHandler.class);

        eventHandler.onEvent(ChannelMessageEvent.class, event -> onChannelMessage(event));
        eventHandler.onEvent(ChannelGoLiveEvent.class, live -> onLiveEvent(live));
    }

    private void onChannelMessage(ChannelMessageEvent event) {
        String message = event.getMessage();
        event.getChannel();

        switch (message) {
            case "!Pog":
                twitchClient.getChat().sendMessage(event.getChannel().getName(), "Pog !");
                break;
            case "!totodile":
                twitchClient.getChat().sendMessage(event.getChannel().getName(), "TOOOOOOOOOTODILE");
                break;
            case "!deer":
                twitchClient.getChat().sendMessage(event.getChannel().getName(), "D e e R F o r C e");
                break;
        }

    }

    //TODO aqui me muero
    private void onLiveEvent(ChannelGoLiveEvent live) {
        if (!live.getStream().getUptime().isZero() || !live.getStream().getUptime().isNegative()) {
        } else {
            writeToDiscordMessage.showOnDiscord(live);
        }
    }
}
