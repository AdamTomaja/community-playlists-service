package com.cydercode.service;

import com.cydercode.model.MusicItemType;
import org.springframework.stereotype.Service;

@Service
public class ItemTypeResolverService {

  public MusicItemType resolve(String link) {
    return MusicItemType.SOUNDCLOUD_SET;
  }
}
