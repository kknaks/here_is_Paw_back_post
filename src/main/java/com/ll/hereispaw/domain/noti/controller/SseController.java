package com.ll.hereispaw.domain.noti.controller;

import com.ll.hereispaw.domain.noti.entity.Noti;
import com.ll.hereispaw.domain.noti.service.SseService;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/sse")
@Tag(name = "SSE API", description = "SSE API")
public class SseController {
  private final SseService sseService;

  @GetMapping(value = "/connect/{userId}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
  public ResponseEntity<SseEmitter> add(@PathVariable String userId) {
    return ResponseEntity.ok(sseService.add(userId));
  }

  @GetMapping(value = "/disconnect/{userId}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
  public ResponseEntity<Void> remove(@PathVariable String userId) {
    sseService.removeEmitter(userId);
    return ResponseEntity.ok().build();
  }

}
