package com.cydercode.service

import com.cydercode.model.MusicItem
import com.cydercode.model.MusicItemType
import com.cydercode.repository.MusicItemsRepository
import spock.lang.Specification
import spock.lang.Subject

class MusicItemsServiceTest extends Specification {



  def "should create item from link with resolved type and externalID"() {
        given:
        def link = "https://somelink.com"
        def repository = Stub(MusicItemsRepository)
        def typeResolverService = Stub(ItemTypeResolverService)
        def externalIdResolverService = Stub(ExternalIDResolverService)

        repository.save(_) >> { MusicItem item ->
            item.id = 1L
            return item
        }

        typeResolverService.resolve(link) >> MusicItemType.SOUNDCLOUD_PLAYLIST
        externalIdResolverService.resolveExternalID(link, MusicItemType.SOUNDCLOUD_PLAYLIST) >> Optional.of("ext-id")

        @Subject
                def service = new MusicItemsService(repository, typeResolverService, externalIdResolverService)

        when:
        def item = service.createItem("usr-id", "usrname", link, "Desc")

        then:
        item.id == 1L
        item.itemType == MusicItemType.SOUNDCLOUD_PLAYLIST
        item.externalId == "ext-id"
        item.userId == "usr-id"
        item.username == "usrname"
        item.description == "Desc"
    }

    def "should throw when unknown link type"() {
        given:
        def typeResolverService = Stub(ItemTypeResolverService)
        typeResolverService.resolve(_) >> MusicItemType.UNKNOWN

        @Subject
        def service = new MusicItemsService(null, typeResolverService, null)

        when:
        service.createItem("asd", "Asd", "Asd", "Desc")

        then:
        thrown(IllegalArgumentException)
    }

  def "should reject potential XSS payload in link field"() {
    given:
    def repository = Stub(MusicItemsRepository)
    def typeResolverService = Stub(ItemTypeResolverService)
    def externalIdResolverService = Stub(ExternalIDResolverService)

    @Subject
    def service = new MusicItemsService(repository, typeResolverService, externalIdResolverService)

    when:
    service.createItem("usr-id", "usrname", maliciousLink, "Desc")

    then:
    thrown(IllegalArgumentException)

    where:
    maliciousLink << [
            "javascript:alert(1)",
            "data:text/html,<script>alert(1)</script>",
            "<script>alert(1)</script>",
            "\"><img src=x onerror=alert(1)>",
    ]
  }
}
