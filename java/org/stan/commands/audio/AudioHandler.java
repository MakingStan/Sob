package org.stan.commands.audio;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.api.audio.AudioSendHandler;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TeamMember;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.managers.AudioManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.stan.Prefix;
import org.stan.commands.lavaplayer.GuildMusicManager;
import org.stan.commands.lavaplayer.PlayerManager;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class AudioHandler extends ListenerAdapter {
    public static final char prefix = Prefix.prefix;

    String link = "https://www.youtube.com/watch?v=H9sb5VOK3YI";

    private byte[] audioBytes = new byte[218982];

    private final Queue<byte[]> queue = new ConcurrentLinkedQueue<>();

    public AudioHandler()
    {

    }


    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event)
    {
        String args[] = event.getMessage().getContentRaw().split(" ");

        final Member member = event.getMember();
        final GuildVoiceState memberVoiceState = member.getVoiceState();
        final AudioManager audioManager = event.getGuild().getAudioManager();
        final VoiceChannel memberChannel = memberVoiceState.getChannel();

        if(args[0].equalsIgnoreCase(prefix+"join"))
        {
            if(memberVoiceState.inVoiceChannel())
            {
                event.getChannel().sendMessage("Connecting to "+memberChannel.getName()+" \uD83D\uDCE2").queue();
                audioManager.openAudioConnection(memberChannel);
            }
            else
            {
                event.getChannel().sendMessage("You need to be in a voice channel in order for this to work.").queue();
                return;
            }
        }
            if(args[0].equalsIgnoreCase(prefix+"play"))
            {
                link = event.getMessage().getContentRaw();
                link = link.replace(prefix+"play", "");

                if(!isUrl(link))
                {
                    link = "ytsearch:"+link;
                }
                System.out.println(link);

                PlayerManager.getInstance()
                        .loadAndPlay(event.getChannel(), link);

            }
            final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(event.getGuild());

            // stop command
            if(args[0].equalsIgnoreCase(prefix+"stop"))
            {
                musicManager.scheduler.audioPlayer.stopTrack();
                event.getChannel().sendMessage("Succesfully stopped the song.").queue();
            }

            //clear command
            if(args[0].equalsIgnoreCase(prefix+"clear"))
            {
                event.getChannel().sendMessage("Succesfully cleared!").queue();
                musicManager.scheduler.queue.clear();
            }

            //resume command
            if(args[0].equalsIgnoreCase(prefix+"resume"))
            {
                PlayerManager.getInstance()
                        .loadAndPlay(event.getChannel(), link);
            }

            //skip command
            if(args[0].equalsIgnoreCase(prefix+"skip"))
            {
                AudioPlayer audioPlayer = musicManager.scheduler.audioPlayer;

                if(audioPlayer.getPlayingTrack() == null)
                {
                    event.getChannel().sendMessage("You are not playing any tracks.").queue();
                }
                else
                {
                    event.getChannel().sendMessage("Successfully skipped the song!").queue();
                    musicManager.scheduler.nextTrack();
                }
            }


            if(args[0].equalsIgnoreCase(prefix+"leave"))
            {
                event.getChannel().sendMessage("leaving "+memberChannel.getName()+" \uD83D\uDCE2").queue();
                audioManager.closeAudioConnection();
            }
    }

    private boolean isUrl(String url)
    {
        try {
            new URI(url);
            return true;
        }
        catch (URISyntaxException e)
        {
            return false;
        }
    }
}
