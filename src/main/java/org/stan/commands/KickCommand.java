package org.stan.commands;

import com.sedmelluq.discord.lavaplayer.container.playlists.ExtendedM3uParser;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.stan.Prefix;

public class KickCommand extends ListenerAdapter {

    public static final char prefix = Prefix.prefix;

    public void onGuildMessageReceived(GuildMessageReceivedEvent event)
    {
        final TextChannel channel = event.getChannel();
        final Message message = event.getMessage();
        final Member member = event.getMember();
        final String[] args = event.getMessage().getContentRaw().split(" ");

        if (args[0].equalsIgnoreCase(prefix + "kick"))
        {
            if (args.length < 2 || message.getMentionedMembers().isEmpty())
            {
                channel.sendMessage("Tag iemand om die te kicken").queue();
                return;
            } else {
                final Member target = message.getMentionedMembers().get(0);
                if (!member.hasPermission(Permission.KICK_MEMBERS))
                {
                    channel.sendMessage("je moet admin zijn om deze command uit te kunnen voeren").queue();
                }
                else
                {

                    event.getGuild().kick(target).queue(
                            (__) -> channel.sendMessage("Bye your kicked").queue(),
                            (error) -> channel.sendMessageFormat("ik kon die niet kicken", error.getMessage())
                    );
                }
            }
        }
    }
}
