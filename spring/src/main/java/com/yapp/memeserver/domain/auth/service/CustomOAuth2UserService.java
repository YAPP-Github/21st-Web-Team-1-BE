package com.yapp.memeserver.domain.auth.service;


import com.yapp.memeserver.domain.account.domain.Account;
import com.yapp.memeserver.domain.account.domain.Adjective;
import com.yapp.memeserver.domain.account.domain.Noun;
import com.yapp.memeserver.domain.account.repository.AccountRepository;
import com.yapp.memeserver.domain.account.repository.AdjectiveRepository;
import com.yapp.memeserver.domain.account.repository.NounRepository;
import com.yapp.memeserver.domain.account.service.AccountService;
import com.yapp.memeserver.domain.auth.dto.OAuthAttributes;
import com.yapp.memeserver.domain.meme.service.CollectionService;
import com.yapp.memeserver.domain.meme.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private final AccountRepository accountRepository;
    private final AdjectiveRepository adjectiveRepository;
    private final NounRepository nounRepository;
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
        Map<String, Object> oAuth2UserAttributes = new HashMap<>(oAuth2User.getAttributes());

        // Account 를 저장하고, 이미 있는 데이터면 Update
        Account user = saveOrUpdate(attributes);

        return new DefaultOAuth2User(Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")),
                oAuth2UserAttributes,
                attributes.getNameAttributeKey());
    }

    private Account saveOrUpdate(OAuthAttributes attributes){
        // 비효율적인 코드인 듯. 비밀번호 안전성도 부족 (password encoder를 불러오면 순환 참조 오류)
        Account user =  accountRepository.findByEmail(attributes.getEmail())
                .map(entity -> {
                    entity.updateMyAccount(attributes.getEmail(), attributes.getPassword());
                    return accountRepository.findByEmail(attributes.getEmail()).get();
                })
                .orElseGet(() -> {
                    // 프로필 사진 설정, Collection 생성
                    Account account = attributes.toEntity(getRandomName(), imageService.getRandomImageUrl());
                    collectionService.createCollection(account);
                    collectionService.createSharedCollection(account);
                    return account;
                });
        return accountRepository.save(user);
    }

    @Transactional(readOnly = true)
    public String getRandomName() {
        long aqty = adjectiveRepository.count();
        long nqty = nounRepository.count();
        // 가져온 개수 중 랜덤한 하나의 인덱스를 뽑는다.
        int aidx = (int)(Math.random() * aqty);
        int nidx = (int)(Math.random() * nqty);

        // 페이징하여 하나만 추출해낸다.
        Page<Adjective> adjectivePage = adjectiveRepository
                .findAll(PageRequest.of(aidx, 1));
        Page<Noun> nounPage = nounRepository
                .findAll(PageRequest.of(nidx, 1));

        if (!adjectivePage.hasContent()) {
            new RuntimeException("형용사가 없습니다.");
        }
        if (!nounPage.hasContent()) {
            new RuntimeException("명사가 없습니다.");
        }
        Adjective adjective = adjectivePage.getContent().get(0);
        Noun noun = nounPage.getContent().get(0);
        return adjective.getWord() + ' ' + noun.getWord();
    }
}