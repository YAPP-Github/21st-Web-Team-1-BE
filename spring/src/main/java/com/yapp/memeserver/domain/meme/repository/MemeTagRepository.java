package com.yapp.memeserver.domain.meme.repository;

import com.yapp.memeserver.domain.meme.domain.Meme;
import com.yapp.memeserver.domain.meme.domain.MemeTag;
import com.yapp.memeserver.domain.meme.domain.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemeTagRepository extends JpaRepository<MemeTag, Long> {

    List<MemeTag> findByMeme(Meme meme);
    Page<MemeTag> findByTag(Tag tag, Pageable pageable);
}
