package com.cydercode.service;

import com.cydercode.model.MusicItemType;
import org.springframework.stereotype.Service;

@Service
public class ExternalIDResolverService {

  public String resolveExternalID(String link, MusicItemType type) {
    return link;
  }
}
