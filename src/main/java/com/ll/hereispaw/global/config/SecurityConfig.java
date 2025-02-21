package com.ll.hereispaw.global.config;

import com.ll.hereispaw.global.security.CustomAuthenticationFilter;
import com.ll.hereispaw.global.security.CustomAuthorizationRequestResolver;
import com.ll.hereispaw.global.security.CustomOAuth2AuthenticationSuccessHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final CustomAuthenticationFilter customAuthenticationFilter;
    private final CustomOAuth2AuthenticationSuccessHandler customOAuth2AuthenticationSuccessHandler;
    private final CustomAuthorizationRequestResolver customAuthorizationRequestResolver;

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
//                log.debug(AppConfig.getDevFrontUrl());

                registry.addMapping("/**")  // 모든 경로에 대해 CORS 설정
                        .allowedOrigins("http://localhost:5173")  // 클라이언트의 도메인 (프론트엔드 도메인)
                        .allowedMethods("GET", "POST", "PUT", "DELETE")  // 허용할 HTTP 메소드
                        .allowedHeaders("*")  // 허용할 헤더
                        .allowCredentials(true);  // 자격 증명 포함 요청 허용
            }
        };
    }

    @Bean
    public SecurityFilterChain baseSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(Customizer.withDefaults())
//                .securityMatcher("")
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/h2-console/**")
                                .permitAll()
                                .requestMatchers("/api/*/**")
                                .authenticated()
                                .anyRequest()
                                .permitAll()
                )
                .headers(
                        headers ->
                                headers.frameOptions(
                                        HeadersConfigurer.FrameOptionsConfig::sameOrigin
                                )
                )
                .csrf(
                        AbstractHttpConfigurer::disable
                )
                .formLogin(
                        AbstractHttpConfigurer::disable
                )
                .sessionManagement((sessionManagement) -> sessionManagement
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .formLogin(
                        AbstractHttpConfigurer::disable
                )
                .oauth2Login(
                        oauth2Login -> oauth2Login
                                .successHandler(customOAuth2AuthenticationSuccessHandler)
                                .authorizationEndpoint(
                                        authorizationEndpoint ->
                                                authorizationEndpoint
                                                        .authorizationRequestResolver(customAuthorizationRequestResolver)
                                )
                )
                .addFilterBefore(customAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
