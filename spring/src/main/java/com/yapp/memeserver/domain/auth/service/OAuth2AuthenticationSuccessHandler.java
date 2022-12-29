package com.yapp.memeserver.domain.auth.service;

import com.yapp.memeserver.domain.account.domain.Account;
import com.yapp.memeserver.domain.auth.dto.OAuthAttributes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        DefaultOAuth2User principal = (DefaultOAuth2User) authentication.getPrincipal();
        OAuthAttributes attributes = OAuthAttributes.ofKakao("id", principal.getAttributes());
        String url = makeRedirectUrl(attributes.getName(), attributes.getEmail());

        if (response.isCommitted()) {
            logger.debug("응답이 이미 커밋된 상태입니다. " + url + "로 리다이렉트하도록 바꿀 수 없습니다.");
            return;
        }

        getRedirectStrategy().sendRedirect(request, response, url);
    }

    // UTF-8로 인코딩 해서 반환. name과 email을 인자로 넣는다.
    private String makeRedirectUrl(String name, String email) {
        return UriComponentsBuilder.fromHttpUrl("http://localhost:3000/oauth2/redirect")
                .queryParam("name", name)
                .queryParam("email", email)
                .build()
                .encode()
                .toUriString();
    }
}
