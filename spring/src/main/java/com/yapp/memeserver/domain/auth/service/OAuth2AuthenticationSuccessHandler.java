package com.yapp.memeserver.domain.auth.service;

import com.yapp.memeserver.global.jwt.JwtProvider;
import com.yapp.memeserver.global.redis.RedisService;
import com.yapp.memeserver.global.util.CookieUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static com.yapp.memeserver.domain.auth.service.CookieAuthorizationRequestRepository.REDIRECT_URL_PARAM_COOKIE_KEY;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final JwtProvider jwtProvider;
    private final RedisService redisService;
    private final CookieAuthorizationRequestRepository CookieAuthorizationRequestRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        DefaultOAuth2User principal = (DefaultOAuth2User) authentication.getPrincipal();
        String email = (String) ((Map<String, Object>) principal.getAttributes().get("kakao_account")).get("email");

        String targetUrl = determineTargetUrl(request, response, authentication);
        System.out.println("targetUrl = " + targetUrl);
        String url = makeRedirectUrl(email, targetUrl);

        ResponseCookie responseCookie = generateRefreshTokenCookie(email);
        response.setHeader("Set-Cookie", responseCookie.toString());
        response.getWriter().write(url);

        if (response.isCommitted()) {
            logger.info("응답이 이미 커밋된 상태입니다. " + url + "로 리다이렉트하도록 바꿀 수 없습니다.");
            return;
        }

        clearAuthenticationAttributes(request, response);
        getRedirectStrategy().sendRedirect(request, response, url);
    }

    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        Optional<String> redirectUrl = CookieUtils.getCookie(request, REDIRECT_URL_PARAM_COOKIE_KEY).map(Cookie::getValue);
        String targetUrl = redirectUrl.orElse(getDefaultTargetUrl());
        return UriComponentsBuilder.fromUriString(targetUrl)
                .build().toUriString();
    }

    protected void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {
        super.clearAuthenticationAttributes(request);
        CookieAuthorizationRequestRepository.removeAuthorizationRequestCookies(request, response);
    }

    // UTF-8로 인코딩 해서 반환.
    private String makeRedirectUrl(String email, String redirectUrl) {

        if (redirectUrl == null) {
            redirectUrl = "http://localhost:3000/";
        }
        System.out.println("redirectUrl = " + redirectUrl);

        String accessToken = jwtProvider.generateAccessToken(email);

        return UriComponentsBuilder.fromHttpUrl(redirectUrl + "oauth2/redirect")
                .queryParam("accessToken", accessToken)
                .queryParam("redirectUrl", redirectUrl)
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
//                .domain("thismeme.me")
                .maxAge(TimeUnit.MILLISECONDS.toSeconds(refreshTokenValidationMs)) // 쿠키 만료 시기(초). 없으면 브라우저 닫힐 때 제거
                .secure(true) // HTTPS로 통신할 때만 쿠키가 전송된다.
                .sameSite("None") // 크로스 사이트에도 쿠키 전송 가능
                .httpOnly(true) // JS를 통한 쿠키 접근을 막아, XSS 공격 등을 방어하기 위한 옵션이다.
                .build();
    }
}