package com.yapp.memeserver.domain.meme.repository;

import com.yapp.memeserver.domain.meme.domain.Category;
import com.yapp.memeserver.domain.meme.domain.MainCategory;
import com.yapp.memeserver.domain.meme.domain.MemeTag;
import com.yapp.memeserver.domain.meme.domain.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findAllByOrderByPriorityAsc();
    List<Category> findByMainCategoryOrderByPriorityAsc(MainCategory mainCategory);
}
