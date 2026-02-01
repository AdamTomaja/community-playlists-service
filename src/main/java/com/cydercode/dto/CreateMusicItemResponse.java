package com.cydercode.dto;

import com.cydercode.model.MusicItemType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateMusicItemResponse {

  private long id;
  private String userID;
  private String username;
  private MusicItemType itemType;
  private String link;
  private String externalID;
}
