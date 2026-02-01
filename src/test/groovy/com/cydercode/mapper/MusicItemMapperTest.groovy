package com.cydercode.mapper

import com.cydercode.model.MusicItem
import com.cydercode.model.MusicItemType
import spock.lang.Specification

class MusicItemMapperTest extends Specification {

    def "should map MusicItem to CreateMusicItemResponse"() {
        given:
        def mapper = new MusicItemMapper()
        MusicItem musicItem = createMusicItem(1)

        when:
        def mapped = mapper.toResponse(musicItem)

        then:
        mapped.id == 1L
        mapped.itemType == MusicItemType.SOUNDCLOUD_PLAYLIST
        mapped.externalID == "ext-id"
        mapped.link == "https://mylink.com"
        mapped.userID == "usr-id"
        mapped.username == "usrname"
    }

    def "should map list of MusicItems to list of CreateMusicItemResponse"() {
        given:
        def mapper = new MusicItemMapper()
        def items = [
            createMusicItem(1L),
            createMusicItem(2L)
        ]

        when:
        def mapped = mapper.toResponse(items)

        then:
        mapped.items[0].id == 1L
        mapped.items[1].id == 2L
    }


    private MusicItem createMusicItem(long id) {
        def musicItem = MusicItem.builder()
                .id(id)
                .itemType(MusicItemType.SOUNDCLOUD_PLAYLIST)
                .externalId("ext-id")
                .link("https://mylink.com")
                .userId("usr-id")
                .username("usrname")
                .build()
        musicItem
    }
}
