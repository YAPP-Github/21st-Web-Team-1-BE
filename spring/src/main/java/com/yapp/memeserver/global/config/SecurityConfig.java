package com.yapp.memeserver.global.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity  // Spring Security 설정 활성화
@RequiredArgsConstructor
public class SecurityConfig {

    // Spring Security에서 제공하는 비밀번호 암호화 클래스.
    // Service에서 사용할 수 있도록 Bean으로 등록한다.
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // Non-Browser Clients만을 위한 API 서버이므로, csrf 보호기능 해제
                .headers().frameOptions().sameOrigin() // h2-console 화면을 보기 위한 처리.

                .and()
                .authorizeRequests() // URL 별로 리소스에 대한 접근 권한 관리
                .anyRequest().permitAll();// 우선 다 허용

        return http.build();
    }
}
