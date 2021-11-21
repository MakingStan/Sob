package org.stan.commands;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.stan.Prefix;

public class CalculatorCommand extends ListenerAdapter {
    public static final char prefix = Prefix.prefix;

    public void onGuildMessageReceived(GuildMessageReceivedEvent event)
    {
        String args[] = event.getMessage().getContentRaw().split(" ");

        if(args[0].equalsIgnoreCase(prefix+"calc"))
        {
                if(args[2] != null)
                {
                        int num1 = Integer.parseInt(args[1]);
                        int num2 = Integer.parseInt(args[3]);
                        int result = 0;
                        String operator = args[2];

                        switch(operator)
                        {
                            case "-": result = num1 - num2;
                            break;
                            case "+": result = num1 + num2;
                            break;
                            case "/": result = num1 / num2;
                            break;
                            case "*": result = num1 * num2;
                            break;
                        }

                        event.getChannel().sendMessage("The result:```"+result+"```").queue();
            }
        }
    }
}
