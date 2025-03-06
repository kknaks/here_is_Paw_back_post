package com.ll.hereispaw.global_msa.security;

import com.ll.hereispaw.domain_msa.member.member.entity.Member;
import com.ll.hereispaw.domain_msa.member.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
// 로그인 시 사용자 인증 > 소셜 로그인은 안 씀
public class CustomUserDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

        return new SecurityUser(
                member.getId(),
                member.getUsername(),
                member.getPassword(),
                member.getNickname(),
                member.getAuthorities()
        );
    }
}