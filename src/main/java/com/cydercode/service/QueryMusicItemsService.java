package com.cydercode.service;

import com.cydercode.model.MusicItem;
import com.cydercode.repository.MusicItemsRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class QueryMusicItemsService {

  private static final int DEFAULT_LIST_LIMIT = 50;

  private final MusicItemsRepository repository;

  public List<MusicItem> listMusicItems() {
    Pageable pageRequest = PageRequest.of(0, DEFAULT_LIST_LIMIT)
            .withSort(Sort.by(Sort.Direction.DESC, "createdAt"));
    return repository.findAll(pageRequest).getContent();
  }

  public List<MusicItem> listMusicItemsByUserId(String userId) {
    return repository.findAllByUserId(userId);
  }
}
