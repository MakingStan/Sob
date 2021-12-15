package org.stan.commands;

import com.sedmelluq.discord.lavaplayer.container.playlists.ExtendedM3uParser;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.stan.Prefix;

public class BanCommand extends ListenerAdapter
{
    public static final char prefix = Prefix.prefix;

    public void onGuildMessageReceived(GuildMessageReceivedEvent event)
    {
        final TextChannel channel = event.getChannel();
        final Message message = event.getMessage();
        final Member member = event.getMember();
        final String[] args = event.getMessage().getContentRaw().split(" ");

        if(args[0].equalsIgnoreCase(prefix + "ban"))
        {
            if(args.length < 2 || message.getMentionedMembers().isEmpty())
            {
                channel.sendMessage("Tag someone to ban them.").queue();
                return;
            }
            else
            {
                final Member target = message.getMentionedMembers().get(0);
                if(!member.hasPermission(Permission.BAN_MEMBERS))
                {
                    channel.sendMessage("You have to be an admin to be able to use this command.").queue();;
                }
                else
                {
                    event.getGuild().ban(target, 0, "ban command").queue(
                            (__) -> channel.sendMessage("Bye your banned").queue(),
                            (error) -> channel.sendMessageFormat("I can't ban you!", error.getMessage())
                    );
                }
            }
        }
    }
}
