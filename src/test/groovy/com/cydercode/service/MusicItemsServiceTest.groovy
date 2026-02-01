package com.cydercode.service

import com.cydercode.model.MusicItem
import com.cydercode.model.MusicItemType
import com.cydercode.repository.MusicItemsRepository
import spock.lang.Specification
import spock.lang.Subject

class MusicItemsServiceTest extends Specification {

    def "should create item from link with resolved type and externalID"() {
        given:
        def repository = Stub(MusicItemsRepository)
        def typeResolverService = Stub(ItemTypeResolverService)
        def externalIdResolverService = Stub(ExternalIDResolverService)

        repository.save(_) >> { MusicItem item ->
            item.id = 1L
            return item
        }

        typeResolverService.resolve("the-link") >> MusicItemType.SOUNDCLOUD_PLAYLIST
        externalIdResolverService.resolveExternalID("the-link", MusicItemType.SOUNDCLOUD_PLAYLIST) >> Optional.of("ext-id")

        @Subject
                def service = new MusicItemsService(repository, typeResolverService, externalIdResolverService)

        when:
        def item = service.createItem("usr-id", "the-link")

        then:
        item.id == 1L
        item.itemType == MusicItemType.SOUNDCLOUD_PLAYLIST
        item.externalId == "ext-id"
    }
}
