package com.cydercode.service.embed;

import com.cydercode.model.MusicItem;
import org.springframework.stereotype.Component;

@Component
public class YoutubeEmbedGenerator implements EmbedCodeGenerator {

  private static final String PLAYLIST_TEMPLATE =
"""

<iframe
width="100%"
src="https://www.youtube.com/embed/videoseries?list={{ext_id}}"
title="YouTube video player" frameborder="0"
allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share"
referrerpolicy="strict-origin-when-cross-origin" allowfullscreen>
</iframe>""";

  private static final String TRACK_TEMPLATE =
"""        

<iframe
width="100%"
src="https://www.youtube.com/embed/{{ext_id}}"
title="YouTube video player"
frameborder="0"
allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share"
referrerpolicy="strict-origin-when-cross-origin"
allowfullscreen></iframe>""";

  @Override
  public String generateEmbedCode(MusicItem item) {
    return switch(item.getItemType()) {
      case YT_MUSIC_TRACK -> replaceExtId(TRACK_TEMPLATE, item.getExternalId());
      case YT_MUSIC_PLAYLIST -> replaceExtId(PLAYLIST_TEMPLATE, item.getExternalId());
      default -> throw new RuntimeException("Unsupported item type: " + item.getItemType());
    };
  }
}
