package com.cydercode.service.embed

import com.cydercode.model.MusicItem
import com.cydercode.model.MusicItemType
import spock.lang.Specification

class SoundcloudEmbedGeneratorTest extends Specification {
  static def EXPECTED_EMBED = """
<iframe
width="100%"
height="166"
scrolling="no"
frameborder="no"
allow="autoplay"
src="https://w.soundcloud.com/player/?url=sc-url&color=%23ff5500">
</iframe>"""

  def "should generate embed for soundcloud items"() {
    given:
    def generator = new SoundcloudEmbedGenerator()

    when:
    def embed = generator.generateEmbedCode(createItem(itemType))

    then:
    embed == expectedEmbed

    where:
    itemType                        || expectedEmbed
    MusicItemType.SOUNDCLOUD_PLAYLIST || EXPECTED_EMBED
    MusicItemType.SOUNDCLOUD_TRACK || EXPECTED_EMBED
  }

  private MusicItem createItem(MusicItemType type) {
    return MusicItem.builder()
            .itemType(type)
            .link("sc-url")
            .build()
  }
}
