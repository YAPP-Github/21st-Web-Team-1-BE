package com.yapp.memeserver.domain.auth.dto;

import com.yapp.memeserver.domain.account.domain.Account;
import lombok.Builder;
import lombok.Getter;
import java.util.Map;

// CustomOAuth2UserService 를 통해 가져온 OAuth2User 의 attributes 를 담을 클래스이다.
@Getter
public class OAuthAttributes {

    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String name;
    private String email;
    private String password;

    @Builder
    public OAuthAttributes(Map<String, Object> attributes,
                           String nameAttributeKey, String name,
                           String email) {
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.name = name;
        this.email = email;
        this.password = getEncodedPassword();
    }

    // OAuth 제공해주는 서비스를 구분해주는 코드.
    public static OAuthAttributes of(String registrationId, String userNameAttributeName,
                                     Map<String, Object> attributes) {

        return ofKakao("id", attributes);
    }

    private static OAuthAttributes ofKakao(String userNameAttributeName, Map<String, Object> attributes) {
        // kakao는 kakao_account에 유저정보가 있다. (email)
        Map<String, Object> kakaoAccount = (Map<String, Object>)attributes.get("kakao_account");
        // kakao_account안에 또 profile이라는 JSON객체가 있다. (nickname, profile_image)
        Map<String, Object> kakaoProfile = (Map<String, Object>)kakaoAccount.get("profile");

        // 반환값이 Map이므로 builder 패턴으로 하나하나 변환해야 한다.
        return OAuthAttributes.builder()
                .name((String) kakaoProfile.get("nickname"))
                .email((String) kakaoAccount.get("email"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    public Account toEntity() {
        return Account.builder()
                .name(name)
                .email(email)
                .encodedPassword(password)
                .build();
    }

    private String getEncodedPassword() {
        return name + email;
    }
}