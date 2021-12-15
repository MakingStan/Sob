package org.stan.commands;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Random;


public class JoinMessageCommand extends ListenerAdapter{
    @Override
    public void onGuildMemberJoin(@Nonnull GuildMemberJoinEvent event)
    {
        //happends when a user joins a guild.
        final List<TextChannel> channels = event.getGuild().getTextChannelsByName("welcome\uD83D\uDC4B", true);

        if(channels.isEmpty())
        {
            return;
        }

        Random random = new Random();


        String[] messages = {
                "Welcome "+event.getMember().getAsMention()+" please say you have pizza!",
                "Sup "+event.getMember().getAsMention()+"!",
                "Yo "+event.getMember().getAsMention()+"!"
        };

        final TextChannel channel = channels.get(0);
        final String message = String.format(messages[random.nextInt(messages.length)]);
        channel.sendMessage(message).queue();
    }
}
