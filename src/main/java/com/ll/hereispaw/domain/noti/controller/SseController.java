package com.ll.hereispaw.domain.noti.controller;

import com.ll.hereispaw.domain.noti.service.SseService;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sse")
@Tag(name = "SSE API", description = "SSE API")
public class SseController {
  // SSE 연결들을 관리하는 컴포넌트
  private final SseService sseService;

  // 클라이언트의 SSE 연결 요청을 처리하는 엔드포인트
  // /sse/connect로 GET 요청이 오면 SSE 스트림을 생성
  @GetMapping(value = "/connect", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
  public ResponseEntity<SseEmitter> connect() {
    // 새로운 SSE 연결 생성 (기본 타임아웃 30초)
    SseEmitter emitter = new SseEmitter();

    // 생성된 emitter를 컬렉션에 추가하여 관리
    sseService.add(emitter);

    try {
      // 연결된 클라이언트에게 초기 연결 성공 메시지 전송
      emitter.send(SseEmitter.event()
          .name("connect")    // 이벤트 이름을 "connect"로 지정
          .data("connected!")); // 전송할 데이터
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return ResponseEntity.ok(emitter);
  }
}
