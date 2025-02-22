package com.ll.hereispaw.domain.noti.controller;

import com.ll.hereispaw.domain.noti.dto.request.NotiRequest;
import com.ll.hereispaw.domain.noti.entity.Noti;
import com.ll.hereispaw.domain.noti.service.NotiService;
import com.ll.hereispaw.domain.noti.service.SseService;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
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

  @PostMapping("/send")
  public void sendNoti(
      @RequestBody NotiRequest notiRequest
  ) {
    notiList.add(Noti.builder()
        .title(notiRequest.getTitle())
        .content(notiRequest.getContent())
        .build());
    sseService.noti("noti", Ut.mapOf("notiList", notiList));
  }
}
