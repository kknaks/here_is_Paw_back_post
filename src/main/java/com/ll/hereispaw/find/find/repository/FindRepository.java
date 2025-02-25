package com.ll.hereispaw.find.find.repository;

import com.ll.hereispaw.find.find.entity.FindPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FindRepository extends JpaRepository<FindPost, Long> {
}
