package com.cydercode.mapper;

import com.cydercode.dto.CreateMusicItemResponse;
import com.cydercode.dto.ListMusicItemsResponse;
import com.cydercode.model.MusicItem;
import com.cydercode.service.EmbedCodeGeneratorService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MusicItemMapper {

  private final EmbedCodeGeneratorService embedCodeGeneratorServicee;

  public ListMusicItemsResponse toResponse(List<MusicItem> musicItems) {
    return ListMusicItemsResponse.builder()
        .items(musicItems.stream().map(this::toResponse).toList())
        .build();
  }

  public CreateMusicItemResponse toResponse(MusicItem item) {
    return CreateMusicItemResponse.builder()
        .id(item.getId())
        .userID(item.getUserId())
        .username(item.getUsername())
        .itemType(item.getItemType())
        .link(item.getLink())
        .externalID(item.getExternalId())
        .embedCode(embedCodeGeneratorServicee.generateEmbedCode(item))
        .build();
  }
}
