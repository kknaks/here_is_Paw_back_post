package com.ll.hereispaw.domain.noti.controller;

import com.ll.hereispaw.domain.noti.entity.NotiRequest;
import com.ll.hereispaw.domain.noti.entity.Noti;
import com.ll.hereispaw.domain.noti.service.NotiService;
import com.ll.hereispaw.domain.noti.service.SseService;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/noti")
@RequiredArgsConstructor
public class NotiController {
  private final NotiService notiService;
  private final SseService sseService;
  private final List<Noti> notiList = new ArrayList<>();

  @PostMapping("/send/{userId}")
  public void sendNotification(
      @PathVariable String userId,
//      @RequestParam String eventName,
      @RequestBody NotiRequest notiRequest) {
    String eventName = "noti";

    sseService.sendNoti(userId, eventName, notiRequest);
  }

  // 알림 목록 조회
  @GetMapping
  public ResponseEntity<List<Noti>> getNotifications(@AuthenticationPrincipal User user) {
    Long userId = Long.parseLong(user.getUsername());
    List<Noti> notifications = notiService.getUserNotifications(userId);
    return ResponseEntity.ok(notifications);
  }

  // 알림 읽음 처리
  @PostMapping("/{notiId}/read")
  public ResponseEntity<Void> markAsRead(@PathVariable Long notiId) {
    notiService.markAsRead(notiId);
    return ResponseEntity.ok().build();
  }

  // 모든 알림 읽음 처리
  @PostMapping("/read-all")
  public ResponseEntity<Void> markAllAsRead(@AuthenticationPrincipal User user) {
    Long userId = Long.parseLong(user.getUsername());
    notiService.markAllAsRead(userId);
    return ResponseEntity.ok().build();
  }
}
