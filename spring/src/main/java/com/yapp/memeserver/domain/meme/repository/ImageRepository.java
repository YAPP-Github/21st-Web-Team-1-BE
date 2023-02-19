package com.yapp.memeserver.domain.meme.repository;

import com.yapp.memeserver.domain.meme.domain.Image;
import com.yapp.memeserver.domain.meme.domain.Meme;
import com.yapp.memeserver.domain.meme.domain.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {

    List<Image> findByMeme(Meme meme);
    long count();
}
