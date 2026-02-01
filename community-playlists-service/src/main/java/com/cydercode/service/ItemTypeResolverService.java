package com.cydercode.service;

import com.cydercode.model.MusicItemType;
import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class ItemTypeResolverService {

  public MusicItemType resolve(String url) {
    try {
      URI uri = new URI(url);
      String host = uri.getHost();
      String path = uri.getPath();
      Map<String, String> query = parseQuery(uri.getQuery());

      if (host == null) {
        return MusicItemType.UNKNOWN;
      }

      /* ================= YOUTUBE MUSIC ================= */
      if (host.contains("music.youtube.com")) {
        if (path.equals("/playlist") && query.containsKey("list")) {
          return MusicItemType.YT_MUSIC_PLAYLIST;
        }
        if (path.equals("/watch") && query.containsKey("v")) {
          return MusicItemType.YT_MUSIC_TRACK;
        }
      }

      /* ================= SPOTIFY ================= */
      if (host.contains("spotify.com")) {
        if (path.startsWith("/playlist/")) {
          return MusicItemType.SPOTIFY_PLAYLIST;
        }
        if (path.startsWith("/track/")) {
          return MusicItemType.SPOTIFY_TRACK;
        }
        if (path.startsWith("/album/")) {
          return MusicItemType.SPOTIFY_ALBUM;
        }
      }

      /* ================= SOUNDCLOUD ================= */
      if (host.contains("soundcloud.com")) {
        // playlist: /user/sets/playlist
        if (path.contains("/sets/")) {
          return MusicItemType.SOUNDCLOUD_PLAYLIST;
        }
        // track: /user/track
        if (path.split("/").length >= 3) {
          return MusicItemType.SOUNDCLOUD_TRACK;
        }
      }

    } catch (Exception e) {
      return MusicItemType.UNKNOWN;
    }

    return MusicItemType.UNKNOWN;
  }

  private static Map<String, String> parseQuery(String query) {
    Map<String, String> map = new HashMap<>();
    if (query == null || query.isEmpty()) {
      return map;
    }

    for (String param : query.split("&")) {
      String[] parts = param.split("=", 2);
      String key = URLDecoder.decode(parts[0], StandardCharsets.UTF_8);
      String value = parts.length > 1
              ? URLDecoder.decode(parts[1], StandardCharsets.UTF_8)
              : "";
      map.put(key, value);
    }
    return map;
  }
}
