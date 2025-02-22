package com.ll.hereispaw.global.initData;

import com.ll.hereispaw.domain.member.member.entity.Member;
import com.ll.hereispaw.domain.missing.Auhtor.entity.Author;
import com.ll.hereispaw.domain.missing.missing.dto.request.MissingRequestDTO;
import com.ll.hereispaw.domain.missing.missing.dto.response.MissingDTO;
import com.ll.hereispaw.domain.missing.missing.service.MissingService;
import com.ll.hereispaw.domain.member.member.service.MemberService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.transaction.annotation.Transactional;

@Profile("!prod")
@Configuration
@RequiredArgsConstructor
@Slf4j
public class InitData {
    private final MemberService memberService;
    private final MissingService missingService;

    @Bean
    @Order(3)
    public ApplicationRunner initDataNotProd() {
        return new ApplicationRunner() {

            @Transactional
            @Override
            public void run (ApplicationArguments args) {
//                if (memberService.count() > 0)  return;
                Member member1 = memberService.join("user1", "1234", "유저1").getData();
                Member member2 =memberService.join("user1", "1234", "유저1").getData();
                Member member3 =memberService.join("user1", "1234", "유저1").getData();

                Author author1 = missingService.of(member1);
                Author author2 = missingService.of(member2);
                Author author3 = missingService.of(member3);

                @NonNull MissingDTO post1 = missingService.write(new MissingRequestDTO(author1, "제목","내용"));
                @NonNull MissingDTO post2 =  missingService.write(new MissingRequestDTO(author2, "제목","내용"));
                @NonNull MissingDTO post3 = missingService.write(new MissingRequestDTO(author3, "제목","내용"));
            }
        };
    }
}
