package org.stan.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberLeaveEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.stan.Prefix;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ServerStatsCommand extends ListenerAdapter {

    public static final char prefix = Prefix.prefix;

    int membersJoinedToday = 0;
    int membersLeftToday = 0;

    public void onGuildMessageReceived(GuildMessageReceivedEvent event)
    {
        String args[] = event.getMessage().getContentRaw().split(" ");
        if(args[0].equalsIgnoreCase(prefix+"serverstats"))
        {
            int memberCount = event.getGuild().getMemberCount();
            int membersGained;

            membersGained = membersJoinedToday-membersLeftToday;

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HHmmss");
            LocalTime localTime = LocalTime.now();
            int hour = localTime.getHour();


            EmbedBuilder currentMembersBuilder = new EmbedBuilder();

            currentMembersBuilder.addField("Members Statistics",
                    "**Membercount**: "+memberCount+
                            "\n**Members joined today**: "+membersJoinedToday+
                            "\n**Members left today**: "+membersLeftToday+
                            "\n**Members gained today**: "+membersGained
                    , true);
            event.getChannel().sendMessage(currentMembersBuilder.build()).queue();
        }
    }

    public void onGuildMemberLeave(GuildMemberLeaveEvent event)
    {
        membersLeftToday += 1;
    }

    public void onGuildMemberJoin(GuildMemberJoinEvent event)
    {
        membersJoinedToday += 1;
    }



}
