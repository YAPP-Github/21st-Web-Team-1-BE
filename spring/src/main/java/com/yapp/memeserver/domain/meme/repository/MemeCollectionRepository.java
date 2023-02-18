package com.yapp.memeserver.domain.meme.repository;

import com.yapp.memeserver.domain.meme.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemeCollectionRepository extends JpaRepository<MemeCollection, Long> {

    boolean existsByMemeAndCollection(Meme meme, Collection collection);
    Optional<MemeCollection> findByMemeAndCollection(Meme meme, Collection collection);
    List<MemeCollection> findByMeme(Meme meme);
    List<MemeCollection> findByCollection(Collection collection);

}
