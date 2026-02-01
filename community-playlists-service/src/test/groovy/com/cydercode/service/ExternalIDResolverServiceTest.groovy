package com.cydercode.service

import com.cydercode.model.MusicItemType
import spock.lang.Specification

class ExternalIDResolverServiceTest extends Specification {

    def "should resolve external id for known music items"() {
        given:
        def service = new ExternalIDResolverService()

        when:
        def extId = service.resolveExternalID(link, type)

        then:
        extId.isPresent()
        extId.get() == expectedExtId

        where:
        link | type || expectedExtId
        // Yt Music
        "https://music.youtube.com/playlist?list=PLgkwWN5AN-eggOPdv7L3tmjboc7M6JIeNo" | MusicItemType.YT_MUSIC_PLAYLIST || "PLgkwWN5AN-eggOPdv7L3tmjboc7M6JIeNo"
        "https://music.youtube.com/watch?v=wjJUJGBlAeM&list=PLgkwWN5AN-eggOPdv7L3tmjbo7M6JIeNo" | MusicItemType.YT_MUSIC_TRACK || "wjJUJGBlAeM"

        // Soundcloud
        "https://soundcloud.com/garage-gabbers/sets/garage-ravvers-v6-0-exploit" | MusicItemType.SOUNDCLOUD_PLAYLIST || "garage-gabbers/sets/garage-ravvers-v6-0-exploit"
        "https://soundcloud.com/paul-s-omka/gt-twistem-dimension" | MusicItemType.SOUNDCLOUD_TRACK || "paul-s-omka/gt-twistem-dimension"

        // Spotify
        "https://open.spotify.com/playlist/62iqkmY2efBloLlTV4gL9y?nd=1&dlsi=9447fe1f39464793" | MusicItemType.SPOTIFY_PLAYLIST || "62iqkmY2efBloLlTV4gL9y"
        "https://open.spotify.com/track/3lumjgjP1vWGksXqtxAK8R" | MusicItemType.SPOTIFY_TRACK || "3lumjgjP1vWGksXqtxAK8R"
        "https://open.spotify.com/album/6di6mCEyCOdexfVNH6T0XY" | MusicItemType.SPOTIFY_ALBUM || "6di6mCEyCOdexfVNH6T0XY"
    }

    def "should return empty for unknown music item"() {
        expect:
        new ExternalIDResolverService().resolveExternalID("https://asdasd", MusicItemType.UNKNOWN).isEmpty()
    }

}
