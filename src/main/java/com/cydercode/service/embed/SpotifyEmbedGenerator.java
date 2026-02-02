package com.cydercode.service.embed;

import com.cydercode.model.MusicItem;
import org.springframework.stereotype.Component;

@Component
public class SpotifyEmbedGenerator implements EmbedCodeGenerator {

  private static final String PLAYLIST_TEMPLATE =
"""

<iframe
src="https://open.spotify.com/embed/{{spotify_type}}/{{ext_id}}"
width="100%"
height="380"
frameborder="0"
allow="autoplay; clipboard-write; encrypted-media; fullscreen; picture-in-picture">
</iframe>
""";

  @Override
  public String generateEmbedCode(MusicItem item) {
    final String extIdReplaced = replaceExtId(PLAYLIST_TEMPLATE, item.getExternalId());
    return switch(item.getItemType()) {
      case SPOTIFY_ALBUM -> replaceTag(extIdReplaced, "spotify_type", "album");
      case SPOTIFY_PLAYLIST -> replaceTag(extIdReplaced, "spotify_type", "playlist");
      case SPOTIFY_TRACK -> replaceTag(extIdReplaced, "spotify_type", "track");
      default ->
  throw new IllegalArgumentException("Invalid music item type");
    };
  }
}
