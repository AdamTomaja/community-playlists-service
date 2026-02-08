package com.cydercode.service;

import com.cydercode.model.MusicItem;
import com.cydercode.model.MusicItemType;
import com.cydercode.repository.MusicItemsRepository;
import java.net.IDN;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Locale;
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

  public MusicItem createItem(String userId,
                              String username,
                              String link,
                              String description) {
    MusicItemType type = itemTypeResolverService.resolve(secureLink(link));
    if(type == MusicItemType.UNKNOWN) {
      throw new IllegalArgumentException("Invalid item type: " + link);
    }

    String externalID =
        externalIDResolverService
            .resolveExternalID(link, type)
            .orElseThrow(() -> new IllegalArgumentException("Cannot recognize link type"));
    MusicItem musicItem = createMusicItem(userId, username, link, description, type, externalID);
    log.info("Saving new music item: {}", musicItem);
    return musicItemsRepository.save(musicItem);
  }

  private String secureLink(String link) {
    if (link == null) {
      throw new IllegalArgumentException("Link cannot be null");
    }

    String trimmed = link.trim();
    if (trimmed.isEmpty()) {
      throw new IllegalArgumentException("Link cannot be empty");
    }

    // Defensive limits & obvious injection vectors (also blocks CR/LF header injection).
    if (trimmed.length() > 2048) {
      throw new IllegalArgumentException("Link is too long");
    }
    for (int i = 0; i < trimmed.length(); i++) {
      char c = trimmed.charAt(i);
      if (c <= 0x1F || c == 0x7F) {
        throw new IllegalArgumentException("Link contains control characters");
      }
    }
    if (trimmed.indexOf('<') >= 0 || trimmed.indexOf('>') >= 0 || trimmed.indexOf('"') >= 0
            || trimmed.indexOf('\'') >= 0) {
      throw new IllegalArgumentException("Link contains forbidden characters");
    }

    final URI uri;
    try {
      uri = new URI(trimmed);
    } catch (URISyntaxException e) {
      throw new IllegalArgumentException("Invalid link syntax");
    }

    String scheme = uri.getScheme();
    if (scheme == null) {
      throw new IllegalArgumentException("Link must be absolute (missing scheme)");
    }
    scheme = scheme.toLowerCase(Locale.ROOT);
    if (!scheme.equals("http") && !scheme.equals("https")) {
      // Blocks e.g. javascript:, data:, vbscript:, file: etc.
      throw new IllegalArgumentException("Unsupported link scheme");
    }

    String host = uri.getHost();
    if (host == null || host.isBlank()) {
      throw new IllegalArgumentException("Link must include a host");
    }

    // Normalize host to ASCII (punycode) to avoid tricky Unicode lookalikes.
    String asciiHost = IDN.toASCII(host, IDN.USE_STD3_ASCII_RULES).toLowerCase(Locale.ROOT);

    // Remove fragment entirely (#...) – it’s client-side only and often abused in XSS contexts.
    // Also drop user-info if present.
    URI normalized;
    try {
      normalized =
              new URI(
                      scheme,
                      null, // userInfo removed
                      asciiHost,
                      uri.getPort(),
                      uri.getRawPath(),
                      uri.getRawQuery(),
                      null // fragment removed
              ).normalize();
    } catch (URISyntaxException e) {
      throw new IllegalArgumentException("Invalid link after normalization");
    }

    // Return ASCII form for consistent downstream matching/parsing.
    return normalized.toASCIIString();
  }

  private MusicItem createMusicItem(
      String userId, String username, String link, String description, MusicItemType type, String externalID) {
    return MusicItem.builder()
        .userId(userId)
        .username(username)
        .link(link)
        .description(description)
        .itemType(type)
        .externalId(externalID)
        .build();
  }
}
