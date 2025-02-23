package com.ll.hereispaw.domain.member.mypet.controller;


import com.ll.hereispaw.domain.member.member.entity.Member;
import com.ll.hereispaw.domain.member.mypet.dto.request.MyPetRequest;
import com.ll.hereispaw.domain.member.mypet.entity.MyPet;
import com.ll.hereispaw.domain.member.mypet.service.MyPetService;
import com.ll.hereispaw.global.webMvc.LoginUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
public class ApiV1MypetController {


    private final MyPetService myPetService;

    // 내 반려견 조회
    @GetMapping("/mypet")
    public ResponseEntity<?> getPets(@LoginUser Member loginUser) {
        if (loginUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 하지 않은 사용자입니다.");
        }

        return ResponseEntity.status(HttpStatus.OK).body(myPetService.getMyPets(loginUser));
    }

    // 내 반려견 등록
    @PostMapping("/mypet")
    public ResponseEntity<String> createPet(@LoginUser Member loginUser, @Valid @RequestBody MyPetRequest myPetRequest) {
        if (loginUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 하지 않은 사용자입니다.");
        }

        if (myPetRequest == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("본문 내용이 없습니다.");
        }

        myPetService.createMyPet(loginUser, myPetRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body("반려견이 생성되었습니다.");
    }

    // 내 반려견 단건 조회
    @GetMapping("/mypet/{id}")
    public ResponseEntity<?> findByMyPet(@LoginUser Member loginUser, @PathVariable Long id) {

        if (loginUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 하지 않은 사용자입니다.");
        }

        MyPet myPet = myPetService.findByMyPet(loginUser, id);

        return ResponseEntity.status(HttpStatus.OK).body(myPet);
    }

    // 내 반려견 수정
    @PatchMapping("/mypet/{id}")
    public ResponseEntity<String> modifyPet(@LoginUser Member loginUser, @Valid @RequestBody MyPetRequest myPetRequest, @PathVariable Long id) {

        if (loginUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 하지 않은 사용자입니다.");
        }

        myPetService.modifyMyPet(loginUser, id, myPetRequest);

        return ResponseEntity.status(HttpStatus.OK).body("수정 완료");
    }

    // 내 반려견 삭제
    @DeleteMapping("/mypet/{id}")
    public ResponseEntity<String> deletePet(@LoginUser Member loginUser, @PathVariable Long id) {

        if (loginUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 하지 않은 사용자입니다.");
        }

        myPetService.deleteMyPet(loginUser, id);

        return ResponseEntity.status(HttpStatus.OK).body("삭제 완료");
    }
}
