package com.cydercode.service;

import com.cydercode.model.MusicItem;
import com.cydercode.model.MusicItemType;
import com.cydercode.repository.MusicItemsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MusicItemsService {

  private final MusicItemsRepository musicItemsRepository;
  private final ItemTypeResolverService itemTypeResolverService;
  private final ExternalIDResolverService externalIDResolverService;

  public MusicItem createItem(String userId, String username, String link) {
    MusicItemType type = itemTypeResolverService.resolve(link);
    String externalID =
        externalIDResolverService
            .resolveExternalID(link, type)
            .orElseThrow(() -> new IllegalArgumentException("Cannot recognize link type"));
    MusicItem musicItem = createMusicItem(userId, username, link, type, externalID);
    log.info("Saving new music item: {}", musicItem);
    return musicItemsRepository.save(musicItem);
  }

  private MusicItem createMusicItem(
      String userId, String username, String link, MusicItemType type, String externalID) {
    return MusicItem.builder()
        .userId(userId)
        .username(username)
        .link(link)
        .itemType(type)
        .externalId(externalID)
        .build();
  }
}
