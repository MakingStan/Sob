package org.stan.commands.lavaplayer;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;

public class GuildMusicManager {
    public final AudioPlayer audioPlayer;
    public final TrackScheduler scheduler;

    private final AudioPlayerSendHandler sendHandler;

    public GuildMusicManager(AudioPlayerManager manager)
    {
        this.audioPlayer = manager.createPlayer();
        this.sendHandler = new AudioPlayerSendHandler(this.audioPlayer);
        this.scheduler = new TrackScheduler(this.audioPlayer);
        this.audioPlayer.addListener(this.scheduler);

    }

    public AudioPlayerSendHandler getSendHandler()
    {
        return sendHandler;
    }
}
