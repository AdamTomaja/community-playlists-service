package com.cydercode.service;

import com.cydercode.model.MusicItem;
import com.cydercode.repository.MusicItemsRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class QueryMusicItemsService {

  private final MusicItemsRepository repository;

  public List<MusicItem> listMusicItems() {
    return repository.findAll();
  }

  public List<MusicItem> listMusicItemsByUserId(String userId) {
    return repository.findAllByUserId(userId);
  }
}
