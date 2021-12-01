package org.stan.commands;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.stan.Prefix;

public class UnBanCommand extends ListenerAdapter {
    public static final char prefix = Prefix.prefix;

    public void onGuildMessageReceived(GuildMessageReceivedEvent event)
    {
        final TextChannel channel = event.getChannel();
        final Message message = event.getMessage();
        final Member member = event.getMember();
        final String[] args = event.getMessage().getContentRaw().split(" ");

        if(args[0].equalsIgnoreCase(prefix + "unban"))
        {
            if(args.length < 2 || message.getMentionedMembers().isEmpty())
            {
                channel.sendMessage("Tag iemand om die te unbannen").queue();
                return;
            }
            else
            {
                final Member target = message.getMentionedMembers().get(0);
                if(!member.hasPermission(Permission.BAN_MEMBERS))
                {
                    channel.sendMessage("je moet admin zijn om deze command uit te kunnen voeren").queue();;
                }
                else
                {

                    event.getGuild().unban(target.getUser()).queue(
                            (__) -> channel.sendMessage("Unbanned succesfully").queue(),
                            (error) -> channel.sendMessageFormat("Kon niet Unbannen", error.getMessage())
                    );
                }
            }
        }
    }
}
