package com.cydercode.service;

import com.cydercode.model.MusicItemType;
import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class ExternalIDResolverService {

  public Optional<String> resolveExternalID(String link, MusicItemType type) {
    try {
      URI uri = new URI(link);
      String path = uri.getPath();
      Map<String, String> query = parseQuery(uri.getQuery());

      return Optional.of(
          switch (type) {

            /* ================= YOUTUBE MUSIC ================= */
            case YT_MUSIC_PLAYLIST -> query.get("list");

            case YT_MUSIC_TRACK -> query.get("v");

            /* ================= SPOTIFY ================= */
            case SPOTIFY_TRACK -> extractSpotifyId(path, "/track/");

            case SPOTIFY_PLAYLIST -> extractSpotifyId(path, "/playlist/");

            case SPOTIFY_ALBUM -> extractSpotifyId(path, "/album/");

            /* ================= SOUNDCLOUD ================= */
            case SOUNDCLOUD_PLAYLIST -> extractSoundCloudPath(path);

            case SOUNDCLOUD_TRACK -> extractSoundCloudPath(path);

            default -> null;
          });

    } catch (Exception e) {
      return Optional.empty();
    }
  }

  /* ================= HELPERS ================= */

  private String extractSpotifyId(String path, String prefix) {
    if (path.startsWith(prefix)) {
      return path.substring(prefix.length());
    }
    return null;
  }

  private String extractSoundCloudPath(String path) {
    // /user/track-name  OR  /user/sets/playlist-name
    return path.startsWith("/") ? path.substring(1) : path;
  }

  private Map<String, String> parseQuery(String query) {
    Map<String, String> map = new HashMap<>();
    if (query == null || query.isEmpty()) {
      return map;
    }

    for (String param : query.split("&")) {
      String[] parts = param.split("=", 2);
      map.put(
          URLDecoder.decode(parts[0], StandardCharsets.UTF_8),
          parts.length > 1 ? URLDecoder.decode(parts[1], StandardCharsets.UTF_8) : "");
    }
    return map;
  }
}
