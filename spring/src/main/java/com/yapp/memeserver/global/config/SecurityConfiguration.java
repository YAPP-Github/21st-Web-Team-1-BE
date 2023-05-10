package com.yapp.memeserver.global.config;

import com.yapp.memeserver.domain.auth.service.CustomOAuth2UserService;
import com.yapp.memeserver.domain.auth.service.CookieAuthorizationRequestRepository;
import com.yapp.memeserver.domain.auth.service.OAuth2AuthenticationSuccessHandler;
import com.yapp.memeserver.global.jwt.JwtAccessDeniedHandler;
import com.yapp.memeserver.global.jwt.JwtEntryPoint;
import com.yapp.memeserver.global.jwt.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Collections;

@Configuration
@EnableMethodSecurity(prePostEnabled = true) // preAuthorize 활성화
@EnableWebSecurity  // Spring Security 설정 활성화
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtEntryPoint jwtEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final JwtFilter jwtFilter;
    private final CustomOAuth2UserService customOAuth2UserService;
    private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;

    private final CookieAuthorizationRequestRepository cookieAuthorizationRequestRepository;


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
                .anyRequest().permitAll()// 우선 다 허용
                .and()
                .cors().configurationSource(corsConfigurationSource()) // CorsConfigurationSource 를 cors 정책의 설정파일 등록

                .and()
                .oauth2Login() // oauth2 로그인 시작점
                .authorizationEndpoint()
                //  인증 요청을 쿠키에 임시 보관하는 리포지토리에 대한 설정으로, 인증 후 프론트에 redirect할 url이 저장되어 있다.
                .authorizationRequestRepository(cookieAuthorizationRequestRepository)
                .and()
                .userInfoEndpoint() // 로그인 성공하면 사용자 정보 가져올 때 설정을 담당
                // oauth2 로그인에 성공하면, 유저 데이터를 가지고 아래 Service에서 처리하겠다.
                .userService(customOAuth2UserService)
                .and()
                .successHandler(oAuth2AuthenticationSuccessHandler)

                .and() // 시큐리티는 기본적으로 세션을 사용하지만, 우리는 세션을 사용하지 않기 때문에 Stateless로 설정
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                // JwtFilter를 인증을 처리하는 UsernamePasswordAuthenticationFilter 전에 추가한다.
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)

                .exceptionHandling() // 예외 처리 기능 작동
                .authenticationEntryPoint(jwtEntryPoint) // 인증 실패시 처리
                .accessDeniedHandler(jwtAccessDeniedHandler); // 인가 실패시 처리

        return http.build();
    }

    // CORS 허용 적용
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        configuration.setAllowCredentials(true);
        configuration.setAllowedOriginPatterns(Collections.singletonList("*"));;

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
