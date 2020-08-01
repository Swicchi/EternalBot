package cl.eternaletulf.bot.bots;


import lombok.extern.slf4j.Slf4j;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DiscordBot {

    private final DiscordApi api;

    public DiscordApi getApi() {
        return api;
    }

    public DiscordBot(@Value("${discord}") String token) {
        api = new DiscordApiBuilder().setToken(token).login().join();

        api.addMessageCreateListener(this::message);

        log.info("You can invite the bot by using the following url: {}", api.createBotInvite());
    }

    private void message(MessageCreateEvent event) {
        String message = event.getMessageContent();

        switch (message) {
            case "!ping":
                event.getChannel().sendMessage("Pong!");
                break;
            case "!totodile":
                event.getChannel().sendMessage("TOOOOOOOOOTODILE");
                break;
            case "!stream":
                event.getChannel().sendMessage("https://www.twitch.tv/eternaletulf");
        }
    }

}
