package cl.eternaletulf.bot.bots;


import lombok.extern.slf4j.Slf4j;
import org.javacord.api.DiscordApi;
import org.javacord.api.event.message.MessageCreateEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DiscordBot {

    static DiscordApi discordApi;

    public DiscordBot(DiscordApi discordApi) {
        discordApi.addMessageCreateListener(this::message);
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

    @Bean
    public static DiscordApi getDiscordApi() {
        return discordApi;
    }

}
