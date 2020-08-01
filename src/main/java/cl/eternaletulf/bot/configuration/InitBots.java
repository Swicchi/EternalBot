package cl.eternaletulf.bot.configuration;

import com.github.philippheuer.credentialmanager.CredentialManager;
import com.github.philippheuer.credentialmanager.CredentialManagerBuilder;
import com.github.philippheuer.credentialmanager.domain.OAuth2Credential;
import com.github.philippheuer.credentialmanager.identityprovider.TwitchIdentityProvider;
import com.github.philippheuer.events4j.simple.SimpleEventHandler;
import com.github.twitch4j.TwitchClient;
import com.github.twitch4j.TwitchClientBuilder;
import lombok.extern.slf4j.Slf4j;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class InitBots {

    private static final String DEFAULT_CHANNEL = "EternalEtulf";
    private static final String FIREBAT= "insanefirebat";


    @Bean
    public DiscordApi discordApi(@Value("${discord}") String token) {
        DiscordApi discordApi = new DiscordApiBuilder().setToken(token).login().join();

        log.info("Discord bot is enabled");

        log.info("You can invite the bot by using the following url: {}",
            discordApi.createBotInvite());

        return discordApi;
    }

    @Bean
    public TwitchClient twitchClient(@Value("${twitch.clientId}") String clientId,
        @Value("${twitch.secret}") String secret, @Value("${twitch.irc}") String irc) {

        log.info("Twitch bot is enabled");

        CredentialManager credentialManager = CredentialManagerBuilder.builder().build();
        credentialManager
            .registerIdentityProvider(new TwitchIdentityProvider(clientId, secret, ""));

        OAuth2Credential credential = new OAuth2Credential("twitch", irc);

        TwitchClient twitchClient = TwitchClientBuilder.builder()
            .withEnableChat(true)
            .withChatAccount(credential)
            .withEnableHelix(true)
            .build();

        twitchClient.getChat().joinChannel(DEFAULT_CHANNEL);

        this.twitchClient = twitchClient;
        return twitchClient;
    }

    TwitchClient twitchClient;

    //TODO: look for improvements
    @Bean
    public SimpleEventHandler eventHandler() {
        log.info("Twitch Event Handler is is enabled");
        return twitchClient.getEventManager().getEventHandler(SimpleEventHandler.class);
    }
}
