package com.yapp.memeserver.domain.meme.repository;

import com.yapp.memeserver.domain.account.domain.Account;
import com.yapp.memeserver.domain.meme.domain.Tag;
import com.yapp.memeserver.domain.meme.domain.TagFav;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TagFavRepository extends JpaRepository<TagFav, Long> {

    Integer countByTag(Tag tag);
    List<TagFav> findByAccount(Account account);
    boolean existsByAccountAndTag(Account account, Tag tag);
    Optional<TagFav> findByAccountAndTag(Account account, Tag tag);
}
