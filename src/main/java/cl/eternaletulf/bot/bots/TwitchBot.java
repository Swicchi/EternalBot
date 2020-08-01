package cl.eternaletulf.bot.bots;

import com.github.philippheuer.events4j.simple.SimpleEventHandler;
import com.github.twitch4j.TwitchClient;
import com.github.twitch4j.chat.events.channel.ChannelMessageEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TwitchBot {

    final TwitchClient twitchClient;

    public TwitchBot(TwitchClient twitchClient) {
        this.twitchClient = twitchClient;
        SimpleEventHandler eventHandler = twitchClient.getEventManager()
            .getEventHandler(SimpleEventHandler.class);

        eventHandler.onEvent(ChannelMessageEvent.class, event -> onChannelMessage(event));
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
}
