package com.ll.hereispaw.domain.member.bookmark.repository;

import com.ll.hereispaw.domain.member.bookmark.entity.Bookmark;
import com.ll.hereispaw.domain.member.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
    Optional<Bookmark> findByMemberAndPostIdAndType(Member member, Long postId, Bookmark.BookmarkType type);
    List<Bookmark> findByMember(Member loginUser);
}
