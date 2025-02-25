package com.ll.hereispaw.domain.member.bookmark.dto.response;

import com.ll.hereispaw.domain.member.bookmark.entity.Bookmark;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class BookmarkDto {
    private Long id;
    private Long postId;
    private Boolean type;

    private Integer postType;
    private String title;       // 게시물 제목 (pet name)
    private String breed;       // 품종
    private String location;    // 발견/실종 위치

    public BookmarkDto(Bookmark bookmark) {
        this.id = bookmark.getId();
        this.postId = bookmark.getPostId();
        this.type = bookmarkType(bookmark.getType());

    }

    private boolean bookmarkType(Bookmark.BookmarkType bookmarkType) {
        return bookmarkType != Bookmark.BookmarkType.MISSING;
    }
}


