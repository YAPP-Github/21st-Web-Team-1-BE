package com.yapp.memeserver.domain.auth.api;

import com.yapp.memeserver.domain.account.service.AccountService;
import com.yapp.memeserver.domain.auth.dto.AccessTokenDto;
import com.yapp.memeserver.domain.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import java.util.concurrent.TimeUnit;

import static org.springframework.http.HttpHeaders.SET_COOKIE;

@Slf4j
@RestController
@RequestMapping("/token")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/refresh")
    public ResponseEntity<AccessTokenDto> refresh(@RequestBody AccessTokenDto requestDto,
                                                  @CookieValue(value = "refreshToken", required = false) Cookie rtCookie) {

        String refreshToken = rtCookie.getValue();

        AccessTokenDto resDto = authService.refresh(requestDto, refreshToken);

        return ResponseEntity.ok()
                .body(resDto);
    }

    @PostMapping("/blacklist")
    public ResponseEntity<AccessTokenDto> signOut(@RequestBody AccessTokenDto requestDto) {
        AccessTokenDto resDto = authService.signOut(requestDto);
        ResponseCookie responseCookie = removeRefreshTokenCookie();

        return ResponseEntity.ok()
                .header(SET_COOKIE, responseCookie.toString())
                .body(resDto);
    }

    public ResponseCookie removeRefreshTokenCookie() {
        return ResponseCookie.from("refreshToken1", null)
                .path("/") // 해당 경로 하위의 페이지에서만 쿠키 접근 허용. 모든 경로에서 접근 허용한다.
                .maxAge(0) // 쿠키의 expiration 타임을 0으로 하여 없앤다.
                .secure(true) // HTTPS로 통신할 때만 쿠키가 전송된다.
                .httpOnly(true) // JS를 통한 쿠키 접근을 막아, XSS 공격 등을 방어하기 위한 옵션이다.
                .build();
    }
}
