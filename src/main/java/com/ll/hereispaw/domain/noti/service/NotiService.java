package com.ll.hereispaw.domain.noti.service;

import com.ll.hereispaw.domain.noti.repository.NotiRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotiService {
  private final NotiRepository notiRepository;

}
