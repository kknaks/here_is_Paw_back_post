package com.ll.hereispaw.domain.find.find.repository;

import com.ll.hereispaw.domain.find.find.entity.FindPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FindRepository extends JpaRepository<FindPost, Long> {
}
