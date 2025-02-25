package com.ll.hereispaw.domain.find.find.repository;

import com.ll.hereispaw.domain.find.find.entity.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FindPhotoRepository extends JpaRepository<Photo, Long> {
}
