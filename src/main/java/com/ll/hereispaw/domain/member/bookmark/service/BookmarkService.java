package com.ll.hereispaw.domain.member.bookmark.service;

import com.ll.hereispaw.domain.member.bookmark.dto.response.BookmarkDto;
import com.ll.hereispaw.domain.member.bookmark.entity.Bookmark;
import com.ll.hereispaw.domain.member.bookmark.repository.BookmarkRepository;
import com.ll.hereispaw.domain.member.member.entity.Member;
import com.ll.hereispaw.domain.missing.missing.repository.MissingRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.service.spi.ServiceException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookmarkService {

    private final BookmarkRepository bookmarkRepository;
    private final MissingRepository missingRepository;
//    private final FindPostRepository findPostRepository;


    /**
     * Missing Post 북마크 추가
     */
    @Transactional
    public String addMissingBookmark(Member loginUser, Long missingId) {
        // Missing Post 존재 확인
        missingRepository.findById(missingId)
                .orElseThrow(() -> new ServiceException("해당 게시물이 없습니다."));

        // 이미 북마크된 게시물인지 확인
        bookmarkRepository.findByMemberAndPostIdAndType(loginUser, missingId, Bookmark.BookmarkType.MISSING)
                .ifPresent(b -> {
                    throw new ServiceException("이미 북마크 되어있습니다.");
                });

        Bookmark bookmark = new Bookmark();
        bookmark.setMember(loginUser);
        bookmark.setPostId(missingId);
        bookmark.setType(Bookmark.BookmarkType.MISSING); // MISSING, FIND

        bookmarkRepository.save(bookmark);

        return "저장 성공";
    }

    /**
     * Find Post 북마크 추가
     */
//    @Transactional
//    public Bookmark addFindPostBookmark(Long memberId, Long findPostId) {
//        // Find Post 존재 확인
//        FindPost findPost = findPostRepository.findById(findPostId)
//                .orElseThrow(() -> new EntityNotFoundException("Find Post not found"));
//
//        // 이미 북마크된 게시물인지 확인
//        bookmarkRepository.findByMemberIdAndPostIdAndPostTypeAndType(
//                        memberId, findPostId, 2, true)
//                .ifPresent(b -> {
//                    throw new IllegalStateException("Already bookmarked");
//                });
//
//        Bookmark bookmark = new Bookmark();
//        bookmark.setMemberId(memberId);
//        bookmark.setPostId(findPostId);
//        bookmark.setPostType(2); // Find Post = 2
//        bookmark.setType(true);  // true = 북마크
//        bookmark.setCreatedAt(LocalDateTime.now());
//
//        return bookmarkRepository.save(bookmark);
//    }

    /**
     * 북마크 삭제
     */
    @Transactional
    public String removeBookmark(Long id) {
        bookmarkRepository.deleteById(id);
        return "삭제 완료";
    }

    /**
     * 회원의 모든 북마크 조회
     */
    public List<BookmarkDto> getBookmarks(Member loginUser) {
        List<Bookmark> bookmarks = bookmarkRepository.findByMember(loginUser);

        // DTO로 변환
        return bookmarks.stream()
                .map(bookmark -> {
                    BookmarkDto dto = new BookmarkDto(bookmark);

                    // 게시물 타입에 따라 추가 정보 조회
                    if (bookmark.getType().equals(Bookmark.BookmarkType.MISSING)) {
                        missingRepository.findById(bookmark.getPostId())
                                .ifPresent(post -> {
                                    dto.setTitle(post.getName());
                                    dto.setBreed(post.getBreed());
                                    dto.setLocation(post.getLocation());
                                    // 다른 필요한 정보 추가
                                });
                    } else if (bookmark.getType().equals(Bookmark.BookmarkType.FIND)) {
//                        findPostRepository.findById(bookmark.getPostId())
//                                .ifPresent(post -> {
//                                    dto.setTitle(post.getName());
//                                    dto.setBreed(post.getBreed());
//                                    dto.setLocation(post.getLocation());
//                                    // 다른 필요한 정보 추가
//                                });
                    }

                    return dto;
                })
                .collect(Collectors.toList());
    }
}