package com.cydercode.service

import com.cydercode.model.MusicItemType
import spock.lang.Specification

class ItemTypeResolverServiceTest extends Specification {
    def "should resolve youtube music playlist type"() {
        given:
        def resolverService = new ItemTypeResolverService()

        when:
        def resolved = resolverService.resolve(url)

        then:
        resolved == expectedType

        where:
        url || expectedType
        // Yt Music
        "https://music.youtube.com/playlist?list=PLgkwWN5AN-eggOPdv7L3tmjboc7M6JIeNo" || MusicItemType.YT_MUSIC_PLAYLIST
        "https://music.youtube.com/watch?v=wjJUJGBlAeM&list=PLgkwWN5AN-eggOPdv7L3tmjbo7M6JIeNo" || MusicItemType.YT_MUSIC_TRACK

        // Soundcloud
        "https://soundcloud.com/garage-gabbers/sets/garage-ravvers-v6-0-exploit" || MusicItemType.SOUNDCLOUD_PLAYLIST
        "https://soundcloud.com/paul-s-omka/gt-twistem-dimension" || MusicItemType.SOUNDCLOUD_TRACK

        // Spotify
        "https://open.spotify.com/playlist/62iqkmY2efBloLlTV4gL9y?nd=1&dlsi=9447fe1f39464793" || MusicItemType.SPOTIFY_PLAYLIST
        "https://open.spotify.com/track/3lumjgjP1vWGksXqtxAK8R" || MusicItemType.SPOTIFY_TRACK
        "https://open.spotify.com/album/6di6mCEyCOdexfVNH6T0XY" || MusicItemType.SPOTIFY_ALBUM
    }
}
