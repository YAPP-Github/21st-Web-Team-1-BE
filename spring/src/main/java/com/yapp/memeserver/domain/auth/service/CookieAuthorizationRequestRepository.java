package com.yapp.memeserver.domain.auth.service;

import com.nimbusds.oauth2.sdk.util.StringUtils;
import com.yapp.memeserver.global.util.CookieUtils;
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class CookieAuthorizationRequestRepository implements AuthorizationRequestRepository<OAuth2AuthorizationRequest> {

    public static final String OAUTH2_AUTHORIZATION_REQUEST_COOKIE_KEY = "oauth2AuthRequest";
    public static final String REDIRECT_URL_PARAM_COOKIE_KEY = "redirectUrl";
    private static final int cookieExpireSeconds = 180;

    // 쿠키에 저장된 인증요청 정보 가져옴
    @Override
    public OAuth2AuthorizationRequest loadAuthorizationRequest(HttpServletRequest request) {
        OAuth2AuthorizationRequest oAuth2AuthorizationRequest =  CookieUtils.getCookie(request, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_KEY)
                .map(cookie -> CookieUtils.deserialize(cookie, OAuth2AuthorizationRequest.class))
                .orElse(null);
        return oAuth2AuthorizationRequest;
    }

    // 쿠키에 인증요청 정보를 저장
    @Override
    public void saveAuthorizationRequest(OAuth2AuthorizationRequest authorizationRequest, HttpServletRequest request, HttpServletResponse response) {
        if (authorizationRequest == null) {
            removeAuthorizationRequest(request, response);
            return;
        }
        CookieUtils.addCookie(response, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_KEY, CookieUtils.serialize(authorizationRequest), cookieExpireSeconds);
        String redirectUriAfterLogin = request.getParameter(REDIRECT_URL_PARAM_COOKIE_KEY);
        if (StringUtils.isNotBlank(redirectUriAfterLogin)) {
            CookieUtils.addCookie(response, REDIRECT_URL_PARAM_COOKIE_KEY, redirectUriAfterLogin, cookieExpireSeconds);
        }
    }

    // 쿠키에 인증요청 정보를 삭제
    @Override
    public OAuth2AuthorizationRequest removeAuthorizationRequest(HttpServletRequest request) {
        return this.loadAuthorizationRequest(request);
    }

    public void removeAuthorizationRequestCookies(HttpServletRequest request, HttpServletResponse response) {
        CookieUtils.deleteCookie(request, response, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_KEY);
        CookieUtils.deleteCookie(request, response, REDIRECT_URL_PARAM_COOKIE_KEY);
    }

}