package com.cydercode.service.embed

import com.cydercode.model.MusicItem
import com.cydercode.model.MusicItemType
import spock.lang.Specification

class SpotifyEmbedGeneratorTest extends Specification {
  static def EXPECTED_TRACK_EMBED = """
<iframe
src="https://open.spotify.com/embed/track/ext-id"
width="100%"
height="380"
frameborder="0"
allow="autoplay; clipboard-write; encrypted-media; fullscreen; picture-in-picture">
</iframe>
"""

  static def EXPECTED_ALBUM_EMBED = """
<iframe
src="https://open.spotify.com/embed/album/ext-id"
width="100%"
height="380"
frameborder="0"
allow="autoplay; clipboard-write; encrypted-media; fullscreen; picture-in-picture">
</iframe>
"""

  static def EXPECTED_PLAYLIST_EMBED = """
<iframe
src="https://open.spotify.com/embed/playlist/ext-id"
width="100%"
height="380"
frameborder="0"
allow="autoplay; clipboard-write; encrypted-media; fullscreen; picture-in-picture">
</iframe>
"""

  def "should generate embed for spotify links"() {
    given:
    def generator = new SpotifyEmbedGenerator()

    when:
    def embed = generator.generateEmbedCode(createItem(itemType))

    then:
    embed == expectedEmbed

    where:
    itemType                        || expectedEmbed
    MusicItemType.SPOTIFY_ALBUM || EXPECTED_ALBUM_EMBED
    MusicItemType.SPOTIFY_TRACK || EXPECTED_TRACK_EMBED
    MusicItemType.SPOTIFY_PLAYLIST || EXPECTED_PLAYLIST_EMBED
  }

  private MusicItem createItem(MusicItemType type) {
    return MusicItem.builder()
            .itemType(type)
            .externalId("ext-id")
            .build()
  }
}
