package com.ll.hereispaw.find.find.controler;

import com.ll.hereispaw.find.find.dto.FindDto;
import com.ll.hereispaw.find.find.dto.FindWithPhotoRequest;
import com.ll.hereispaw.find.find.service.FindService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/find")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173",
        allowedHeaders = "*",
        allowCredentials = "true")
public class FindController {

    private final FindService findService;

    // 유기견 발견 전체 조회
    @GetMapping
    public List<FindDto> showFind() {
        List<FindDto> findDtos = findService.findAll();

        return findDtos;
    }

    // 유기견 발견 신고시 저장
    @PostMapping("/new")
    public ResponseEntity<Map<String, Object>> newFind(@RequestBody FindWithPhotoRequest request) {

        // 상태 0: 발견, 1: 보호, 2:완료
        int state = 0;

        // saveFind 로 새로 저장 될 때 해당 id 값을 받아옴
        Long findPostId = findService.saveFind(
                request.getBreed(), request.getGeo(), request.getLocation(),
                request.getName(), request.getColor(), request.getEtc(), request.getGender(),
                 request.getAge(),
                state, request.isNeutered(), request.getFind_date(),
                request.getMember_id(), request.getShelter_id()
        );

        // 위에서 받아온 id를 담아서 저장, 이미지 경로를 받아옴
        String path_url = findService.saveFindPhoto(
                request.getPath_url(),
                request.getMember_id(), findPostId
        );

        Map<String, Object> response = new HashMap<>();
        response.put("message", "저장 완료");
        response.put("findPostId", findPostId);

        System.out.println("사진 경로 : " + path_url);

        return ResponseEntity.ok(response);
    }

}
