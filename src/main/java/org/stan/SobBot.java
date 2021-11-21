package org.stan;

import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import org.stan.commands.*;
import org.stan.commands.audio.AudioHandler;

import javax.security.auth.login.LoginException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.sql.Time;

public class SobBot {

    public static JDABuilder builder;

    public static void main(String[] args) throws LoginException, UnsupportedAudioFileException, IOException {
        String token = "OTExNTUxNDE1NjE5NTU5NDQ0.YZjCaQ.jECBAGPomKQ3DlzUGsrWRi1FIu4";
        builder = JDABuilder.createDefault(token);
        builder.setActivity(Activity.playing("$help"));

        builder.enableIntents(GatewayIntent.GUILD_MEMBERS);
        builder.enableIntents(GatewayIntent.GUILD_VOICE_STATES);
        builder.enableIntents(GatewayIntent.GUILD_MESSAGES);

        builder.enableCache(CacheFlag.VOICE_STATE);

        builder.addEventListeners(new CalculatorCommand());
        builder.addEventListeners(new HelpCommand());
        builder.addEventListeners(new JoinMessageCommand());
        builder.addEventListeners(new MemeCommand());
        builder.addEventListeners(new RockPaperScissorsCommand());
        builder.addEventListeners(new AudioHandler());
        builder.addEventListeners(new TimeCommand());

        builder.build();
    }
}
