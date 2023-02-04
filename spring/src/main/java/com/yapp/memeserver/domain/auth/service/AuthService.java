package com.yapp.memeserver.domain.auth.service;

import com.yapp.memeserver.domain.auth.dto.AccessTokenDto;
import com.yapp.memeserver.global.jwt.JwtProvider;
import com.yapp.memeserver.global.redis.RedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {

    private final JwtProvider jwtProvider;
    private final RedisService redisService;

    public AccessTokenDto refresh(String refreshToken) {
        // 들어온 refreshToekn 검증
        if (!jwtProvider.validateToken(refreshToken)) {
            log.error("유효하지 않은 토큰입니다. {}", refreshToken);
            throw new RuntimeException("Refresh Token 검증에 실패한 토큰입니다. : " + refreshToken);
        }
        // refreshToken에서 Authentication 추출하기
        Authentication authentication = jwtProvider.getAuthentication(refreshToken);

        // Redis의 RefreshToken을 가져오면서, 로그아웃된 사용자인 경우 예외 처리
        String findRefreshToken = redisService.getRefreshToken(authentication.getName())
                .orElseThrow(() -> new RuntimeException(authentication.getName()));

        // 저장되어있던 refreshToken과 일치하는지 확인
        if (!refreshToken.equals(findRefreshToken)) {
            log.error("저장된 토큰과 일치하지 않습니다. {} {}", refreshToken, findRefreshToken);
            throw new RuntimeException("Refresh Token 저장된 토큰과 일치하지 않습니다. : " + refreshToken);
        }

        // 토큰 생성을 위해 accessToken에서 Claims 추출
        String newAccessToken = jwtProvider.generateAccessToken(authentication.getName());

        return new AccessTokenDto(newAccessToken);
    }

    public AccessTokenDto signOut(AccessTokenDto requestDto) {
        // accessToken에서 Authentication 추출하기
        String accessToken = requestDto.getAccessToken();
        Authentication authentication = jwtProvider.getAuthentication(accessToken);

        // Redis의 RefreshToken을 가져오면서, 이미 로그아웃된 사용자인 경우 예외 처리
        String refreshToken = redisService.getRefreshToken(authentication.getName())
                .orElseThrow(() -> new RuntimeException("RefreshToken NotFound:" + authentication.getName()));

        // AccessToken의 남은 시간 추출 후 BlackList에 저장
        Long remainingTime = jwtProvider.getRemainingTime(accessToken);
        redisService.setData("BlackList:" + accessToken, "signOut", remainingTime);
        redisService.deleteData("RefreshToken:" + authentication.getName());

        return new AccessTokenDto(accessToken);
    }

}
