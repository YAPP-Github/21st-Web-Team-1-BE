package com.yapp.memeserver.domain.meme.service;

import com.yapp.memeserver.domain.account.domain.Account;
import com.yapp.memeserver.domain.account.repository.AccountRepository;
import com.yapp.memeserver.domain.meme.domain.*;
import com.yapp.memeserver.domain.meme.repository.MemeCollectionRepository;
import com.yapp.memeserver.domain.meme.repository.TagFavRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MemeCollectionService {

    private final AccountRepository accountRepository;
    private final MemeCollectionRepository memeCollectionRepository;
    private final CollectionService collectionService;
    private final MemeService memeService;

    @Transactional(readOnly = true)
    public boolean isExistsByMemeAndCollection(Meme meme, Collection collection) {
        return memeCollectionRepository.existsByMemeAndCollection(meme, collection);
    }

    @Transactional(readOnly = true)
    public MemeCollection findByMemeAndCollection(Meme meme, Collection collection) {
        return memeCollectionRepository.findByMemeAndCollection(meme, collection)
                .orElseThrow(() -> new EntityNotFoundException("해당 밈콜렉션을 찾을 수 없습니다. id = "+ meme + collection ));
    }

    public void create(Meme meme, Account account) {
        Collection collection = collectionService.findCollection(account);
        if (isExistsByMemeAndCollection(meme, collection)) {
            throw new RuntimeException("이미 밈 컬렉션이 추가되었습니다.");
        }
        MemeCollection memeCollection = MemeCollection.builder()
                .meme(meme)
                .collection(collection)
                .build();
        memeCollectionRepository.save(memeCollection);
        accountRepository.updateSaveCount(account.getId(), 1);
    }

    public void delete(Meme meme, Account account) {
        Collection collection = collectionService.findCollection(account);
        if (!isExistsByMemeAndCollection(meme, collection)) {
            throw new RuntimeException("이미 밈 콜렉션이 삭제되었습니다.");
        }
        MemeCollection memeCollection = findByMemeAndCollection(meme, collection);
        accountRepository.updateSaveCount(account.getId(), -1);
        memeCollectionRepository.delete(memeCollection);
    }

    public void createShare(Meme meme, Account account) {
        Collection collection = collectionService.findSharedCollection(account);
        if (isExistsByMemeAndCollection(meme, collection)) {
            throw new RuntimeException("이미 밈 공유 콜렉션에 추가되었습니다.");
        }
        MemeCollection memeCollection = MemeCollection.builder()
                .meme(meme)
                .collection(collection)
                .build();
        memeCollectionRepository.save(memeCollection);
        accountRepository.updateShareCount(account.getId(), 1);
    }
}
