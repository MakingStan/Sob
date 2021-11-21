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
        final List<TextChannel> channels = event.getGuild().getTextChannelsByName("algemeen", true);

        if(channels.isEmpty())
        {
            return;
        }

        final TextChannel pleaseDontDoThisAtAll = channels.get(0);
        final String useGuildSpecificSettingsInstead = String.format("Welcome "+event.getMember().getAsMention());
        pleaseDontDoThisAtAll.sendMessage(useGuildSpecificSettingsInstead).queue();
    }
}
