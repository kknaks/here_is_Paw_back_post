package com.ll.hereispaw.domain.noti.service;

import com.ll.hereispaw.domain.noti.dto.kafka.dto.ImageMatchDto;
import com.ll.hereispaw.domain.noti.entity.Noti;
import com.ll.hereispaw.domain.noti.repository.NotiRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NotiService {
  private final NotiRepository notiRepository;
  private final SseService sseService;

  public void createImageMatchNoti(Long userId, ImageMatchDto matchDto){
//    Noti noti = Noti.builder()
//        .userId(userId)
//        .title("새로운 이미지 매치가 발견되었습니다")
//        .content("유사도: " + String.format("%.1f%%", matchDto.getSimilarity() * 100))
//        .type("IMAGE_MATCH")
//        .resourceId(matchDto.getImageId())
//        .targetId(matchDto.getPostId())
//        .read(false)
//        .build();

//    notiRepository.save(noti);
//    sseService.sendNoti(userId, "noti", matchDto);
  }

  // 사용자의 모든 알림 조회
  public List<Noti> getAllNotifications(String userId) {
    return notiRepository.findByUserIdOrderByCreatedAtDesc(userId);
  }

  // 사용자의 읽지 않은 알림 조회
  public List<Noti> getUnreadNotifications(String userId) {
    return notiRepository.findByUserIdAndReadOrderByCreatedAtDesc(userId, false);
  }

  // 특정 알림 읽음 처리
  public void markAsRead(Long notificationId) {
    notiRepository.findById(notificationId).ifPresent(notification -> {
      notification.markAsRead();
      notiRepository.save(notification);
    });
  }

  // 사용자의 모든 알림 읽음 처리
  public void markAllAsRead(String userId) {
    List<Noti> notifications = notiRepository.findByUserIdAndReadOrderByCreatedAtDesc(userId, false);
    for (Noti notification : notifications) {
      notification.markAsRead();
    }
    notiRepository.saveAll(notifications);
  }

//  public void markAllAsRead(Long userId) {
//    List<Noti> unreadNotis = notiRepository.findByUserIdAndReadFalse(userId);
//    unreadNotis.forEach(noti -> noti.setRead(true));
//    notiRepository.saveAll(unreadNotis);
//  }
}
