package com.yapp.memeserver.domain.auth.service;

import com.yapp.memeserver.domain.account.domain.Account;
import com.yapp.memeserver.domain.auth.dto.OAuthAttributes;
import com.yapp.memeserver.global.jwt.JwtProvider;
import com.yapp.memeserver.global.redis.RedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final JwtProvider jwtProvider;
    private final RedisService redisService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        DefaultOAuth2User principal = (DefaultOAuth2User) authentication.getPrincipal();
        OAuthAttributes attributes = OAuthAttributes.ofKakao("id", principal.getAttributes());
        String url = makeRedirectUrl(attributes.getEmail());
        ResponseCookie responseCookie = generateRefreshTokenCookie(attributes.getEmail());
        response.setHeader("Set-Cookie", responseCookie.toString());

        if (response.isCommitted()) {
            logger.info("응답이 이미 커밋된 상태입니다. " + url + "로 리다이렉트하도록 바꿀 수 없습니다.");
            return;
        }

        getRedirectStrategy().sendRedirect(request, response, url);
    }

    // UTF-8로 인코딩 해서 반환.
    private String makeRedirectUrl(String email) {
        String accessToken = jwtProvider.generateAccessToken(email);

        return UriComponentsBuilder.fromHttpUrl("https://app.thismeme.me/oauth2/redirect")
                .queryParam("accessToken", accessToken)
                .build()
                .encode()
                .toUriString();
    }

    public ResponseCookie generateRefreshTokenCookie(String email) {
        String refreshToken = jwtProvider.generateRefreshToken(email);
        Long refreshTokenValidationMs = jwtProvider.getRefreshTokenValidationMs();

        redisService.setData("RefreshToken:" + email, refreshToken, refreshTokenValidationMs);

        return ResponseCookie.from("refreshToken", refreshToken)
                .path("/") // 해당 경로 하위의 페이지에서만 쿠키 접근 허용. 모든 경로에서 접근 허용한다.
                .domain("thismeme.me")
                .maxAge(TimeUnit.MILLISECONDS.toSeconds(refreshTokenValidationMs)) // 쿠키 만료 시기(초). 없으면 브라우저 닫힐 때 제거
                .secure(true) // HTTPS로 통신할 때만 쿠키가 전송된다.
                .sameSite("none")
                .httpOnly(true) // JS를 통한 쿠키 접근을 막아, XSS 공격 등을 방어하기 위한 옵션이다.
                .build();
    }
}
