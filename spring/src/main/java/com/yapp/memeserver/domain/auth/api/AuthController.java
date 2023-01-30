package com.yapp.memeserver.domain.auth.api;

import com.yapp.memeserver.domain.account.service.AccountService;
import com.yapp.memeserver.domain.auth.dto.AccessTokenDto;
import com.yapp.memeserver.domain.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;

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

        return ResponseEntity.ok()
                .body(resDto);
    }
}
