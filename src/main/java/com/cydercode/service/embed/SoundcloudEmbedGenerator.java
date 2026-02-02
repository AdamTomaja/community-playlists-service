package com.cydercode.service.embed;

import com.cydercode.model.MusicItem;
import org.springframework.stereotype.Component;

@Component
public class SoundcloudEmbedGenerator implements EmbedCodeGenerator {

  private static final String TEMPLATE =
"""

<iframe
width="100%"
height="166"
scrolling="no"
frameborder="no"
allow="autoplay"
src="https://w.soundcloud.com/player/?url={{ext_id}}&color=%23ff5500">
</iframe>""";

  @Override
  public String generateEmbedCode(MusicItem item) {
    return switch(item.getItemType()) {
      case SOUNDCLOUD_TRACK,SOUNDCLOUD_PLAYLIST -> replaceExtId(TEMPLATE, item.getLink());
      default -> throw new IllegalArgumentException("Invalid music item type: " + item.getItemType());
    };
  }
}
