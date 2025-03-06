package com.ll.hereispaw.global_msa.security;


import com.ll.hereispaw.domain_msa.member.member.entity.Member;
import com.ll.hereispaw.domain_msa.member.member.service.MemberService;
import com.ll.hereispaw.global_msa.rq.Rq;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomOAuth2AuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    private final MemberService memberService;
    private final Rq rq;
    @SneakyThrows
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        Member actor = memberService.findById(rq.getActor().getId()).get();
        rq.makeAuthCookies(actor);
        String redirectUrl = request.getParameter("state");
        response.sendRedirect(redirectUrl);
    }
}