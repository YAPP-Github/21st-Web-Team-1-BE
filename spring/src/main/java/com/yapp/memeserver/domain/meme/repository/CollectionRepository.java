package com.yapp.memeserver.domain.meme.repository;

import com.yapp.memeserver.domain.account.domain.Account;
import com.yapp.memeserver.domain.meme.domain.Collection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CollectionRepository extends JpaRepository<Collection, Long> {

    List<Collection> findByAccount(Account account);
    Optional<Collection> findByAccountAndIsShared(Account account, Boolean isShared);
}
