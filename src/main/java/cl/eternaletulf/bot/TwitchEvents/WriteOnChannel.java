package cl.eternaletulf.bot.TwitchEvents;

import com.github.philippheuer.events4j.simple.SimpleEventHandler;
import com.github.twitch4j.TwitchClient;
import com.github.twitch4j.chat.events.channel.ChannelMessageEvent;

public class WriteOnChannel {

    public WriteOnChannel(SimpleEventHandler eventHandler, TwitchClient twitchClient) {
        eventHandler.onEvent(ChannelMessageEvent.class, event -> {
            onChannelMessage(event, twitchClient);
        });
    }

    public void onChannelMessage(ChannelMessageEvent event, TwitchClient twitchClient) {
        String message = event.getMessage();
        event.getChannel();

        switch (message) {
            case "!Pog":
                twitchClient.getChat().sendMessage(event.getChannel().getName(), "Pog !");
                break;
            case "!totodile":
                twitchClient.getChat()
                    .sendMessage(event.getChannel().getName(), "TOOOOOOOOOTODILE");
                break;
            case "!deer":
                twitchClient.getChat()
                    .sendMessage(event.getChannel().getName(), "D e e R F o r C e");
                break;
        }
    }
}
