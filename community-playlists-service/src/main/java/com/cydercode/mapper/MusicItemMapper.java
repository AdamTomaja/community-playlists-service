package com.cydercode.mapper;

import com.cydercode.dto.CreateMusicItemResponse;
import com.cydercode.dto.ListMusicItemsResponse;
import com.cydercode.model.MusicItem;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class MusicItemMapper {
  public ListMusicItemsResponse toResponse(List<MusicItem> musicItems) {
    return ListMusicItemsResponse.builder()
        .items(musicItems.stream().map(this::toResponse).toList())
        .build();
  }

  public CreateMusicItemResponse toResponse(MusicItem item) {
    return CreateMusicItemResponse.builder()
        .id(item.getId())
        .userID(item.getUserId())
        .itemType(item.getItemType())
        .link(item.getLink())
        .externalID(item.getExternalId())
        .build();
  }
}
