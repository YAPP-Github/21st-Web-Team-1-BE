package com.yapp.memeserver.domain.meme.service;

import com.yapp.memeserver.domain.account.domain.Account;
import com.yapp.memeserver.domain.meme.domain.Category;
import com.yapp.memeserver.domain.meme.domain.Collection;
import com.yapp.memeserver.domain.meme.repository.CollectionRepository;
import com.yapp.memeserver.domain.meme.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CollectionService {

    private final CollectionRepository collectionRepository;

    @Transactional(readOnly = true)
    public Collection findById(Long collectionId) {
        return collectionRepository.findById(collectionId)
                .orElseThrow(() -> new EntityNotFoundException("해당 id를 가진 Collection 을 찾을 수 없습니다. id = "+ collectionId ));
    }

    @Transactional(readOnly = true)
    public List<Collection> findByAccount(Account account) {
        return collectionRepository.findByAccount(account);
    }

    @Transactional(readOnly = true)
    public Collection findCollection(Account account) {
        return collectionRepository.findByAccountAndIsShared(account, false)
                .orElseThrow(() -> new EntityNotFoundException("해당 계정의 Collection 을 찾을 수 없습니다. id = "+ account ));
    }

    @Transactional(readOnly = true)
    public Collection findSharedCollection(Account account) {
        return collectionRepository.findByAccountAndIsShared(account, true)
                .orElseThrow(() -> new EntityNotFoundException("해당 계정의 Shared Collection 을 찾을 수 없습니다. id = "+ account ));
    }

    public void createCollection(Account account) {
        Collection collection = Collection.builder()
                .account(account)
                .isShared(false)
                .build();
        account.addCollection(collection);
    }

    public void createSharedCollection(Account account) {
        Collection collection = Collection.builder()
                .account(account)
                .isShared(true)
                .build();
        account.addCollection(collection);
    }

}
