package org.stan.commands;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.stan.Prefix;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeCommand extends ListenerAdapter {

    private static final char prefix = Prefix.prefix;

    public void onGuildMessageReceived(GuildMessageReceivedEvent event)
    {
        String args[] = event.getMessage().getContentRaw().split(" ");
        if(args[0].equalsIgnoreCase(prefix+"time"))
        {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date time = new Date();
            event.getChannel().sendMessage("```"+formatter.format(time)+"```").queue();
        }
    }

}
