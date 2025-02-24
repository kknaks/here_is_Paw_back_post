package com.ll.hereispaw.domain.noti.controller;

import com.ll.hereispaw.domain.noti.entity.NotiRequest;
import com.ll.hereispaw.domain.noti.entity.Noti;
import com.ll.hereispaw.domain.noti.service.NotiService;
import com.ll.hereispaw.domain.noti.service.SseService;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
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
}
