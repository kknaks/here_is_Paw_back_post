package com.ll.hereispaw.domain.missing.Auhtor.repository;

import com.ll.hereispaw.domain.missing.Auhtor.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
}
