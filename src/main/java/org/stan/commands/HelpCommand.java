package org.stan.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.stan.Prefix;

import java.awt.*;

public class HelpCommand extends ListenerAdapter {
    public static final char prefix = Prefix.prefix;

    public void onGuildMessageReceived(GuildMessageReceivedEvent event)
    {
        String args[] = event.getMessage().getContentRaw().split(" ");

        if(args[0].equalsIgnoreCase(prefix+"help"))
        {
            EmbedBuilder embedBuilder = new EmbedBuilder();
            embedBuilder.setColor(Color.CYAN);
            embedBuilder.setDescription("" +
                    "```" +
                    prefix+"calc \"number\"\"operator(/,*,-,+)\"\"number\"\n" +
                    prefix+"meme\n" +
                    prefix+"msges\n" +
                    prefix+"RPS (Rock paper scissors)\n" +
                    prefix+"time```\n\n```" +
                    prefix+"ban (user)\n" +
                    prefix+"kick (user)\n" +"" +
                    "```\n\n" +
                    "```$join & $leave (join or leave a call)\n" +
                    prefix+"play (argument)\n" +
                    prefix+"skip\n" +
                    prefix+"resume\n" +
                    prefix+"nowplaying\n" +
                    prefix+"repeat\n" +
                    prefix+"queue\n" +
                    prefix+"mute\n" +
                    prefix+"unmute```");

            event.getChannel().sendMessage(embedBuilder.build()).queue();
        }
    }
}
