package cl.eternaletulf.bot.bots;

import cl.eternaletulf.bot.TwitchEvents.WriteToDiscordMessage;
import com.github.philippheuer.credentialmanager.CredentialManager;
import com.github.philippheuer.credentialmanager.CredentialManagerBuilder;
import com.github.philippheuer.credentialmanager.domain.OAuth2Credential;
import com.github.philippheuer.credentialmanager.identityprovider.TwitchIdentityProvider;
import com.github.philippheuer.events4j.simple.SimpleEventHandler;
import com.github.twitch4j.TwitchClient;
import com.github.twitch4j.TwitchClientBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TwitchBot {

    private static final String DEFAULT_CHANNEL = "EternalEtulf";

    @Bean
    public SimpleEventHandler getEventHandler() {
        return eventHandler;
    }

    private SimpleEventHandler eventHandler;

    public TwitchBot(@Value("${twitch.clientId}") String clientId,
        @Value("${twitch.secret}") String secret, @Value("${twitch.irc}") String irc, @Value("${discord}") String discordToken) {

        DiscordBot discordBot = new DiscordBot(discordToken);

        CredentialManager credentialManager = CredentialManagerBuilder.builder().build();
        credentialManager
            .registerIdentityProvider(new TwitchIdentityProvider(clientId, secret, ""));

        OAuth2Credential credential = new OAuth2Credential("twitch", irc);

        TwitchClient twitchClient = TwitchClientBuilder.builder()
            .withEnableChat(true)
            .withChatAccount(credential)
            .withEnableHelix(true)
            .build();

        eventHandler = twitchClient.getEventManager().getEventHandler(SimpleEventHandler.class);

        WriteToDiscordMessage writeChannelChatToConsole = new WriteToDiscordMessage(eventHandler,
            discordBot);

        twitchClient.getChat().joinChannel(DEFAULT_CHANNEL);
        twitchClient.getChat().sendMessage(DEFAULT_CHANNEL, "Hey!");
    }


}
