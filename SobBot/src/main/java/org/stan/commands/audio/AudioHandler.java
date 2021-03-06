package org.stan.commands.audio;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.managers.AudioManager;
import net.dv8tion.jda.api.requests.restaction.MessageAction;
import org.jetbrains.annotations.NotNull;
import org.stan.Prefix;
import org.stan.commands.lavaplayer.GuildMusicManager;
import org.stan.commands.lavaplayer.PlayerManager;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
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

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
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
        //play commnd
        if(args[0].equalsIgnoreCase(prefix+"play"))
        {
            link = event.getMessage().getContentRaw();
            link = link.replace(prefix+"play", "");

            if(!isUrl(link))
            {
                link = "ytsearch:"+link;
            }

            PlayerManager.getInstance()
                    .loadAndPlay(event.getTextChannel(), link);

        }
        final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(event.getGuild());

        // pause command
        if(args[0].equalsIgnoreCase(prefix+"pause"))
        {
            musicManager.scheduler.audioPlayer.setPaused(true);
            event.getChannel().sendMessage("Succesfully stopped the song.").queue();
        }

        if(args[0].equalsIgnoreCase(prefix+"unmute"))
        {
            audioManager.setSelfMuted(false);
        }

        //clear command
        if(args[0].equalsIgnoreCase(prefix+"clear"))
        {
            for (int i = 0;i < musicManager.scheduler.queue.size();i++)
            {
                musicManager.scheduler.queue.clear();
            }
            event.getChannel().sendMessage("Succesfully cleared!").queue();
        }

        //resume command
        if(args[0].equalsIgnoreCase(prefix+"resume"))
        {
            musicManager.scheduler.audioPlayer.setPaused(false);
            event.getChannel().sendMessage("Song succesfully resumed!").queue();
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


        //now playing command
        if(args[0].equalsIgnoreCase(prefix+"nowplaying"))
        {
            final AudioTrack track = musicManager.scheduler.audioPlayer.getPlayingTrack();
            final String duration = String.valueOf(track.getDuration()/1000);

            event.getChannel().sendMessage("Author: "
                            +(track.getInfo().author)
                            +("\nTitle: ")
                            +(track.getInfo().title)
                            +("\nUrl: ")
                            +(track.getInfo().uri)
                            +("\nQueue position: ")
                            +(String.valueOf(musicManager.scheduler.queue.size()+1))
                            +("\nDuration: ")
                            +(duration))
                    .queue();
        }

        //mute command
        if(args[0].equalsIgnoreCase(prefix+"mute"))
        {
            audioManager.setSelfMuted(true);
        }

        //repeat command
        if(args[0].equalsIgnoreCase(prefix+"repeat"))
        {
            final AudioTrack track = musicManager.scheduler.audioPlayer.getPlayingTrack();
            track.setPosition(0);
            event.getChannel().sendMessage("Song succesfully repeated!").queue();
        }

        //queue command
        if(args[0].equalsIgnoreCase(prefix+"queue"))
        {
            final TextChannel channel = event.getTextChannel();
            PlayerManager.getInstance().getMusicManager(event.getGuild());
            BlockingQueue<AudioTrack> queue = musicManager.scheduler.queue;

            if(queue.isEmpty())
            {
                channel.sendMessage("The current queue is empty \uD83C\uDFB5").queue();
                return;
            }

            final int trackCount = Math.min(queue.size(), 20);
            final List<AudioTrack> trackList = new ArrayList<>(queue);

            MessageAction messageAction = event.getChannel().sendMessage("**Current Queue**\n");
            for(int i = 0; i<trackCount; i++)
            {
                final AudioTrack track = trackList.get(i);
                final AudioTrackInfo info = track.getInfo();
                final String duration = String.valueOf(track.getDuration()/1000);

                messageAction.append('#')
                        .append(String.valueOf(i+1))
                        .append(" `")
                        .append(info.title)
                        .append(" By ")
                        .append(info.author)
                        .append("` [`")
                        .append(duration)
                        .append("`]\n");
            }
            if(trackList.size() > trackCount)
            {
                messageAction.append("And `")
                        .append(String.valueOf(trackList.size() - trackCount))
                        .append("more...");
            }
            messageAction.queue();
        }



        if(args[0].equalsIgnoreCase(prefix+"leave"))
        {
            audioManager.closeAudioConnection();
            event.getChannel().sendMessage("leaving "+memberChannel.getName()+" \uD83D\uDCE2").queue();
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
