package com.ll.hereispaw.domain.noti.service;

import com.ll.hereispaw.domain.noti.entity.Noti;
import com.ll.hereispaw.domain.noti.repository.NotiRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotiService {
  private final NotiRepository notiRepository;
  private final SseService sseService;

  // 사용자의 모든 알림 조회
  public List<Noti> getAllNotifications(String userId) {
    return notiRepository.findByUserIdOrderByCreatedAtDesc(userId);
  }

  // 사용자의 읽지 않은 알림 조회
  public List<Noti> getUnreadNotifications(String userId) {
    return notiRepository.findByUserIdAndReadOrderByCreatedAtDesc(userId, false);
  }

  // 특정 알림 읽음 처리
  public void markNotificationAsRead(Long notificationId) {
    notiRepository.findById(notificationId).ifPresent(notification -> {
      notification.markAsRead();
      notiRepository.save(notification);
    });
  }

  // 사용자의 모든 알림 읽음 처리
  public void markAllNotificationsAsRead(String userId) {
    List<Noti> notifications = notiRepository.findByUserIdAndReadOrderByCreatedAtDesc(userId, false);
    for (Noti notification : notifications) {
      notification.markAsRead();
    }
    notiRepository.saveAll(notifications);
  }
}
