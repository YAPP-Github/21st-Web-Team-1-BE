package com.yapp.memeserver.domain.meme.repository;

import com.yapp.memeserver.domain.meme.domain.Meme;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemeRepository extends JpaRepository<Meme, Long> {

    // 페이징 처리. 전체 밈 조회, 조회순 밈 조회에 사용
    Page<Meme> findAll(Pageable pageable);
}
