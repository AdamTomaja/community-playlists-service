package com.cydercode.service.embed

import com.cydercode.model.MusicItem
import com.cydercode.model.MusicItemType
import spock.lang.Specification

class YoutubeEmbedGeneratorTest extends Specification {

  static def EXPECTED_TRACK_EMBED = """
<iframe
width="100%"
height="300"
src="https://www.youtube.com/embed/ext-id"
title="YouTube video player"
frameborder="0"
allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share"
referrerpolicy="strict-origin-when-cross-origin"
allowfullscreen></iframe>"""
  static def EXPECTED_PLAYLIST_EMBED = """
<iframe
width="100%"
height="300"
src="https://www.youtube.com/embed/videoseries?list=ext-id"
title="YouTube video player" frameborder="0"
allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share"
referrerpolicy="strict-origin-when-cross-origin" allowfullscreen>
</iframe>"""

  def "should generate embed for youtube links"() {
    given:
    def generator = new YoutubeEmbedGenerator()

    when:
    def embed = generator.generateEmbedCode(createItem(itemType))

    then:
    embed == expectedEmbed

    where:
    itemType                        || expectedEmbed
    MusicItemType.YT_MUSIC_TRACK    || EXPECTED_TRACK_EMBED
    MusicItemType.YT_MUSIC_PLAYLIST || EXPECTED_PLAYLIST_EMBED
  }

  private MusicItem createItem(MusicItemType type) {
    return MusicItem.builder()
            .itemType(type)
            .externalId("ext-id")
            .build()
  }
}
