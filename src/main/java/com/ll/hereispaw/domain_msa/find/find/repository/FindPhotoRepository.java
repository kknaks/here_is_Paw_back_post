package com.ll.hereispaw.domain_msa.find.find.repository;

import com.ll.hereispaw.domain_msa.find.find.entity.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FindPhotoRepository extends JpaRepository<Photo, Long> {
    Photo findByPostId(Long post_id);
}
