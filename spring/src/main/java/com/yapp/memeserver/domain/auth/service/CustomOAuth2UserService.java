package com.yapp.memeserver.domain.auth.service;


import com.yapp.memeserver.domain.account.domain.Account;
import com.yapp.memeserver.domain.account.repository.AccountRepository;
import com.yapp.memeserver.domain.auth.dto.OAuthAttributes;
import com.yapp.memeserver.domain.meme.service.CollectionService;
import com.yapp.memeserver.domain.meme.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
@Transactional
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private final AccountRepository accountRepository;
    private final ImageService imageService;
    private final CollectionService collectionService;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        // DefaultOAuth2User 서비스를 통해 User 정보를 가져와야 하기 때문에 대리자 생성
        OAuth2UserService<OAuth2UserRequest, OAuth2User> service = new DefaultOAuth2UserService();

        // Oath2 정보를 가져옴
        OAuth2User oAuth2User = service.loadUser(userRequest);

        // 소셜 정보 가져옴
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        // OAuth2 로그인 진행시 키가 되는 필드값 프라이머리키와 같은 값 네이버 카카오 지원 x
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
                .getUserInfoEndpoint().getUserNameAttributeName();

        // OAuth2UserService 를 통해 가져온 데이터를 담을 클래스
        OAuthAttributes attributes = OAuthAttributes
                .of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        // Account 를 저장하고, 이미 있는 데이터면 Update
        Account user = saveOrUpdate(attributes);

        return new DefaultOAuth2User(Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")),
                attributes.getAttributes(),
                attributes.getNameAttributeKey());
    }

    private Account saveOrUpdate(OAuthAttributes attributes){
        // 비효율적인 코드인 듯. 비밀번호 안전성도 부족 (password encoder를 불러오면 순환 참조 오류)
        Account user =  accountRepository.findByEmail(attributes.getEmail())
                .map(entity -> {
                    entity.updateMyAccount(attributes.getEmail(), attributes.getName(),
                            attributes.getPassword());
                            return accountRepository.findByEmail(attributes.getEmail()).get();
                })
                .orElseGet(() -> attributes.toEntity(imageService.getRandomImageUrl()));
        collectionService.createCollection(user);
        collectionService.createSharedCollection(user);

        return accountRepository.save(user);
    }
}
