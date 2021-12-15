package org.stan.commands.lavaplayer;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerManager {

    private static PlayerManager INSTANCE;

    private final Map<Long, GuildMusicManager> musicManagers;
    private final AudioPlayerManager audioPlayerManager;

    public PlayerManager() {
        this.musicManagers = new HashMap<>();
        this.audioPlayerManager = new DefaultAudioPlayerManager();

        AudioSourceManagers.registerRemoteSources(this.audioPlayerManager);
        AudioSourceManagers.registerLocalSource(this.audioPlayerManager);


    }

    public GuildMusicManager getMusicManager(Guild guild)
    {
        return this.musicManagers.computeIfAbsent(guild.getIdLong(), (guildId) -> {
            final GuildMusicManager guildMusicManager = new GuildMusicManager(this.audioPlayerManager);

            guild.getAudioManager().setSendingHandler(guildMusicManager.getSendHandler());
            return guildMusicManager;
        });
    }

    public void loadAndPlay(TextChannel channel, String trackUrl)
    {
        final GuildMusicManager musicManager = this.getMusicManager(channel.getGuild());

        this.audioPlayerManager.loadItemOrdered(musicManager, trackUrl, new AudioLoadResultHandler() {
            @Override
            public void trackLoaded(AudioTrack track) {
                musicManager.scheduler.queue(track);
                final String duration = String.valueOf(track.getDuration()/1000);


                channel.sendMessage(("Author: ")
                        +(track.getInfo().author)
                        +("\nTitle: ")
                        +(track.getInfo().title)
                        +("\nUrl: ")
                        +(track.getInfo().uri)
                        +("\nQueue position: ")
                        +(String.valueOf(musicManager.scheduler.queue.size()+1))
                        +("\nDuration: ")
                        +(duration)).queue();
            }

            @Override
            public void playlistLoaded(AudioPlaylist playlist)
            {
                final List<AudioTrack> tracks = playlist.getTracks();
                final AudioTrack track = tracks.get(0);
                final String duration = String.valueOf(track.getDuration()/1000);
                musicManager.scheduler.queue(track);

                channel.sendMessage(("Author: ")
                        +(track.getInfo().author)
                        +("\nTitle: ")
                        +(track.getInfo().title)
                        +("\nUrl: ")
                        +(track.getInfo().uri)
                        +("\nQueue position: ")
                        +(String.valueOf(musicManager.scheduler.queue.size()+1))
                        +("\nDuration: ")
                        +(duration)).queue();
            }

            @Override
            public void noMatches() {

            }

            @Override
            public void loadFailed(FriendlyException exception) {
                System.out.println("load failed");
            }
        });
    }

    public static PlayerManager getInstance()
    {
        if(INSTANCE == null)
        {
            INSTANCE = new PlayerManager();
        }
        return INSTANCE;
    }
}
