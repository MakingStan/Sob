package org.stan.commands;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.stan.Prefix;
import org.stan.TotalMessageHandler;

import java.io.IOException;

public class TotalMessageCommand extends ListenerAdapter {

    public static final char prefix = Prefix.prefix;
    public static int msgCount = 0;

    public TotalMessageCommand() throws IOException {
        TotalMessageHandler.start();
    }
    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event)
    {
        try
        {
            TotalMessageHandler.handle();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        String[] args = event.getMessage().getContentRaw().split(" ");

        if(args[0].equalsIgnoreCase(prefix+"msges"))
        {
            event.getChannel().sendMessage("Dit is het totaal van messages die verstuurd zijn in de server: ```"+msgCount+"```").queue();
        }
    }

}
