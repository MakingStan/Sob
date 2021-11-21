package org.stan.commands;

import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;
import java.util.List;


public class JoinMessageCommand extends ListenerAdapter{
    @Override
    public void onGuildMemberJoin(@Nonnull GuildMemberJoinEvent event)
    {
        //happends when a user joins a guild.
        final List<TextChannel> channels = event.getGuild().getTextChannelsByName("general", true);

        if(channels.isEmpty())
        {
            return;
        }

        final TextChannel channel = channels.get(0);
        final String message = String.format("Welcome "+event.getMember().getAsMention());
        channel.sendMessage(message).queue();
    }
}
