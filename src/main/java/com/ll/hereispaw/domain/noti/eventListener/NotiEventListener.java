package com.ll.hereispaw.domain.noti.eventListener;

import com.ll.hereispaw.domain.noti.dto.kafka.dto.ImageMatchResponseDto;
import com.ll.hereispaw.domain.noti.service.NotiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Transactional
@Slf4j
public class NotiEventListener {

  private final NotiService notiService;

  @KafkaListener(
      topics = "dog-face-compare-response",
      groupId = "noti", // 직접 그룹 ID 지정
      containerFactory = "kafkaListenerContainerFactory"
  )
  public void consumeImageMatchResult(ImageMatchResponseDto responseDto) {
    log.info("Received image match result: {}", responseDto);

    if ("success".equals(responseDto.getStatus())) {
      log.info("Found {} matches", responseDto.getMatches().size());

      // 매치 결과 처리 로직
      responseDto.getMatches().forEach(match -> {
        log.info("Match: imageId={}, postId={}, similarity={}",
            match.getImageId(), match.getPostId(), match.getSimilarity());

//        // 여기서 사용자에게 알림을 보내는 로직 추가
//        Long userId = match.getPostId(); // 예시: postId를 userId로 사용
//        notiService.createImageMatchNoti(userId, match);
      });
    } else {
      log.warn("Image match processing failed with status: {}", responseDto.getStatus());
    }
  }
}
