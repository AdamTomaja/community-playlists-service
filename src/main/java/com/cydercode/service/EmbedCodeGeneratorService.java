package com.cydercode.service;

import com.cydercode.model.MusicItem;
import com.cydercode.model.MusicItemType;
import com.cydercode.service.embed.SoundcloudEmbedGenerator;
import com.cydercode.service.embed.SpotifyEmbedGenerator;
import com.cydercode.service.embed.YoutubeEmbedGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmbedCodeGeneratorService {

  private final YoutubeEmbedGenerator youtubeEmbedGenerator;
  private final SoundcloudEmbedGenerator soundcloudEmbedGenerator;
  private final SpotifyEmbedGenerator spotifyEmbedGenerator;

  public String generateEmbedCode(MusicItem musicItem) {
    return switch(musicItem.getItemType()) {
      case MusicItemType.SOUNDCLOUD_PLAYLIST, MusicItemType.SOUNDCLOUD_TRACK ->
              soundcloudEmbedGenerator.generateEmbedCode(musicItem);
      case MusicItemType.YT_MUSIC_PLAYLIST. YT_MUSIC_TRACK ->
              youtubeEmbedGenerator.generateEmbedCode(musicItem);
      case MusicItemType.SPOTIFY_ALBUM,  MusicItemType.SPOTIFY_TRACK, SPOTIFY_PLAYLIST ->
              spotifyEmbedGenerator.generateEmbedCode(musicItem);
      default -> throw new IllegalArgumentException("Invalid music item type");
    };
  }
}
