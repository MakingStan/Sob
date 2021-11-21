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
                    "$calc \"number\"\"operator(/,*,-,+)\"\"number\"\n" +
                    "$meme\n" +
                    "$RPS (Rock paper scissors)\n" +
                    "$join & $leave (join or leave a call)\n" +
                    "$play (argument)\n" +
                    "$skip\n" +
                    "$stop\n" +
                    "$resume");

            event.getChannel().sendMessage(embedBuilder.build()).queue();
        }
    }
}
