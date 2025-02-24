package com.ll.hereispaw.domain.noti.service;

import com.ll.hereispaw.domain.noti.entity.NotiRequest;
import com.ll.hereispaw.domain.noti.entity.Noti;
import com.ll.hereispaw.domain.noti.repository.NotiRepository;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Service
@RequiredArgsConstructor
@Slf4j
public class SseService {
  private final Map<String, SseEmitter> emitters = new ConcurrentHashMap<>();
  private final NotiRepository notiRepository;

  public SseEmitter add(String userId){
    if (emitters.containsKey(userId)) {
      return emitters.get(userId);
    }

    SseEmitter emitter = new SseEmitter(3600000L);

    try {
      emitter.send(SseEmitter.event()
          .name("connect")
          .data("connected!"+userId));

      sendUnreadNoti(userId, emitter);
    } catch (IOException e) {
      emitter.completeWithError(e);
      return emitter;
    }
    // 클라이언트와의 연결이 완료되면 컬렉션에서 제거하는 콜백
    emitter.onCompletion(() -> {
      this.emitters.remove(emitter);
    });

    // 연결이 타임아웃되면 완료 처리하는 콜백
    emitter.onTimeout(() -> {
      emitter.complete();
      emitters.remove(userId);
    });

    emitter.onError(e -> {
      log.error("SSE 에러 발생: {}", e.getMessage());
      emitters.remove(userId);
    });

    emitters.put(userId, emitter);
    return emitter;
  }

  public void sendNoti(String userId, String eventName, NotiRequest notiRequest){

    Noti noti = Noti.builder()
        .userId(userId)
        .eventName(eventName)
        .notiRequest(notiRequest)
        .read(false)
        .build();

    notiRepository.save(noti);

    SseEmitter emitter = emitters.get(userId);

    if (emitter != null) {
      try {
        emitter.send(SseEmitter.event()
            .name(eventName)
            .data(notiRequest));

        noti.markAsRead();
        notiRepository.save(noti);

      } catch (IOException e) {
        emitters.remove(userId);
        emitter.completeWithError(e);
      }
    }
  }

  private void sendUnreadNoti(String userId, SseEmitter emitter) throws IOException {
    List<Noti> unreadNotifications = notiRepository.findByUserIdAndReadOrderByCreatedAtDesc(userId, false);

    for (Noti noti : unreadNotifications) {
      emitter.send(SseEmitter.event()
          .name(noti.getEventName())
          .data(noti.getNotiRequest())
          .id(String.valueOf(noti.getId())));
      log.debug("Unread noti sent: {}", noti.getId());
      // 전송 후 읽음 처리
      noti.markAsRead();
    }

    // 일괄 저장
    if (!unreadNotifications.isEmpty()) {
      notiRepository.saveAll(unreadNotifications);
    }
  }

  public void sendNotificationToAll(String eventName, NotiRequest data) {
    emitters.forEach((userId, emitter) -> {
      sendNoti(userId, eventName, data);
    });
  }

  public void removeEmitter(String userId) {
    emitters.remove(userId);
  }
}
