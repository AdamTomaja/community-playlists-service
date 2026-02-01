package com.cydercode.dto;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ListMusicItemsResponse {

  private List<CreateMusicItemResponse> items;
}
