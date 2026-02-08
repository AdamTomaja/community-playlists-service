package com.cydercode.repository;

import com.cydercode.model.MusicItem;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface MusicItemsRepository extends JpaRepository<MusicItem, Long> {

  List<MusicItem> findAllByUserId(String userId);
}
