package org.stan.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.stan.Prefix;

import javax.swing.*;
import java.awt.*;
import java.util.Random;


public class RockPaperScissorsCommand extends ListenerAdapter {
    public static final char prefix = Prefix.prefix;
    public static Timer timer;

    int count = 3;

    String paper = "✋";
    String scissors = "✂";
    String rock = "✊";
    String RPSOptions[] = {
            paper, scissors,rock
    };

    Random random = new Random();

    public void onGuildMessageReceived(GuildMessageReceivedEvent event)
    {
        String args[] = event.getMessage().getContentRaw().split(" ");


        if(args[0].equalsIgnoreCase(prefix+"RPS"))
        {
            timer = new Timer(1000, (e -> {
                event.getChannel().sendMessage(String.valueOf(count)).queue();
                count--;
                if(count == 0)
                {
                    event.getChannel().sendMessage("`paper` `rock` `scissors`").queue(message -> {
                        message.addReaction(paper).queue();
                        message.addReaction(rock).queue();
                        message.addReaction(scissors).queue();
                    });

                    count = 3;
                    timer.stop();
                }
            }));
            timer.start();
        }
    }

    public void onGuildMessageReactionAdd(GuildMessageReactionAddEvent event)
    {
        int randomInt = random.nextInt(RPSOptions.length);
        String userRPSAnswer = "";
        if(!event.getMember().getUser().equals(event.getJDA().getSelfUser()))
        {

            String reaction = event.getReactionEmote().getName();
            System.out.println(reaction);

            if(reaction.equals(scissors))
            {
                userRPSAnswer = scissors;
            }
            else if(reaction.equals(rock))
            {
                userRPSAnswer = rock;
            }
            else if(reaction.equals(paper))
            {
                userRPSAnswer = paper;
            }
            else {
                event.getChannel().sendMessage("That is not a valid emote.").queue();
                return;
            }

            EmbedBuilder embedBuilder = new EmbedBuilder();

            if(RPSOptions[randomInt].equalsIgnoreCase(userRPSAnswer))
            {
                //its a tie
                embedBuilder.setDescription("Sob rolled "+RPSOptions[randomInt]+". We tied!");
                embedBuilder.setColor(Color.CYAN);
            }

            else if(RPSOptions[randomInt].equalsIgnoreCase(scissors)&&userRPSAnswer.equalsIgnoreCase(rock)
            ||      RPSOptions[randomInt].equalsIgnoreCase(rock)&&userRPSAnswer.equalsIgnoreCase(paper)
            ||      RPSOptions[randomInt].equalsIgnoreCase(paper)&&userRPSAnswer.equalsIgnoreCase(scissors))
            {
                //the user won
                embedBuilder.setDescription(event.getMember().getUser().getAsMention()+"won! The bot rolled: "+RPSOptions[randomInt]);
                embedBuilder.setColor(Color.GREEN);
            }
            else
            {
                embedBuilder.setDescription("Sob won! he rolled: "+RPSOptions[randomInt]+". Well played!");
                embedBuilder.setColor(Color.RED);
            }
            event.getChannel().sendMessage(embedBuilder.build()).queue();

        }
    }
}
