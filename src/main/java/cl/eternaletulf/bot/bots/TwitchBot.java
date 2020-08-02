package cl.eternaletulf.bot.bots;

import cl.eternaletulf.bot.TwitchEvents.WriteOnChannel;
import cl.eternaletulf.bot.TwitchEvents.WriteToDiscordMessage;
import com.github.philippheuer.events4j.simple.SimpleEventHandler;
import com.github.twitch4j.TwitchClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TwitchBot {

    final TwitchClient twitchClient;

    final
    DiscordBot discordBot;

    public TwitchBot(TwitchClient twitchClient, DiscordBot discordBot) {
        this.twitchClient = twitchClient;
        this.discordBot = discordBot;

        registerFeatures();
    }

    public void registerFeatures() {
        SimpleEventHandler eventHandler = twitchClient.getEventManager()
            .getEventHandler(SimpleEventHandler.class);

        // Register Event-based features
        WriteOnChannel writeOnChannel = new WriteOnChannel(eventHandler, twitchClient);
        WriteToDiscordMessage writeToDiscordMessage = new WriteToDiscordMessage(eventHandler,
            discordBot);
    }
}
