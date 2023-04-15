package com.yapp.memeserver.domain.meme.repository;

import com.yapp.memeserver.domain.meme.domain.Category;
import com.yapp.memeserver.domain.meme.domain.MainCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MainCategoryRepository extends JpaRepository<MainCategory, Long> {
    List<MainCategory> findAllByOrderByPriorityAsc();
}
