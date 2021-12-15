package org.stan.commands;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.stan.Prefix;

import java.util.Random;

public class MemeCommand extends ListenerAdapter {
    public static char prefix = Prefix.prefix;
    Random random = new Random();

    public void onGuildMessageReceived(GuildMessageReceivedEvent event)
    {

        String args[] = event.getMessage().getContentRaw().split(" ");
        String memeUrls[] = {
                "https://tenor.com/bFGiW.gif","https://tenor.com/bFh2J.gif",
                "https://tenor.com/bFh2J.gif","https://tenor.com/bHOeL.gif",
                "https://tenor.com/6a5j.gif","https://tenor.com/bAbtu.gif",
                "https://tenor.com/bbJ0i.gif","https://tenor.com/bjqLV.gif",
                "https://tenor.com/xBlx.gif","https://tenor.com/boqgZ.gif",
                "https://tenor.com/bhQaU.gif","https://tenor.com/bwtgw.gif",
                "https://tenor.com/bayfx.gif","https://tenor.com/bvr6p.gif",
                "https://tenor.com/bLoJk.gif","https://tenor.com/bmWFy.gif",
                "https://tenor.com/bowJa.gif","https://tenor.com/2AfR.gif",
                "https://tenor.com/bciDn.gif","https://tenor.com/9bE6.gif",
                "https://tenor.com/bgNB7.gif","https://tenor.com/Es0z.gif",
                "https://tenor.com/bHWe1.gif","https://tenor.com/bFhw6.gif",
                "https://tenor.com/bKSzd.gif","https://tenor.com/biLhh.gif",
        };
        if(args[0].equalsIgnoreCase(prefix+"meme"))
        {
            /*Member member = event.getMember();
            event.getGuild().addRoleToMember(member, event.getGuild().getRolesByName("eerste hoofdcommissaris", true).get(0)).queue();*/

            event.getChannel().sendMessage(memeUrls[random.nextInt(memeUrls.length)]).queue();

        }
    }
}
