package com.cydercode.repository;

import com.cydercode.model.MusicItem;
import java.util.List;
import org.springframework.data.repository.ListCrudRepository;

public interface MusicItemsRepository extends ListCrudRepository<MusicItem, Long> {

  List<MusicItem> findAllByUserId(String userId);
}
