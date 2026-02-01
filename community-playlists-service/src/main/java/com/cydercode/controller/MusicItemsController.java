package com.cydercode.controller;

import com.cydercode.dto.CreateMusicItemRequest;
import com.cydercode.dto.CreateMusicItemResponse;
import com.cydercode.dto.ListMusicItemsResponse;
import com.cydercode.mapper.MusicItemMapper;
import com.cydercode.service.MusicItemsService;
import com.cydercode.service.QueryMusicItemsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/music-items")
public class MusicItemsController {

  private final MusicItemsService musicItemsService;
  private final QueryMusicItemsService queryMusicItemsService;
  private final MusicItemMapper mapper;

  @PostMapping()
  public CreateMusicItemResponse createMusicItem(
      @RequestBody @Valid CreateMusicItemRequest request) {
    log.info(
        "Creating music item with link: [{}] for user_id: [{}]",
        request.getLink(),
        request.getUserID());
    return mapper.toResponse(musicItemsService.createItem(request.getUserID(), request.getLink()));
  }

  @GetMapping()
  public ListMusicItemsResponse listMusicItems() {
    log.info("Listing all music items");
    return mapper.toResponse(queryMusicItemsService.listMusicItems());
  }

  @GetMapping("/users/{userId}")
  public ListMusicItemsResponse listMusicItemsByUserId(@PathVariable String userId) {
    log.info("Listing music items by userId: {}", userId);
    return mapper.toResponse(queryMusicItemsService.listMusicItemsByUserId(userId));
  }
}
