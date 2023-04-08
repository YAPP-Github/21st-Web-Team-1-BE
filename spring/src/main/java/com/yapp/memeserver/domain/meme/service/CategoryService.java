package com.yapp.memeserver.domain.meme.service;

import com.yapp.memeserver.domain.meme.domain.Category;
import com.yapp.memeserver.domain.meme.domain.MainCategory;
import com.yapp.memeserver.domain.meme.domain.Tag;
import com.yapp.memeserver.domain.meme.repository.CategoryRepository;
import com.yapp.memeserver.domain.meme.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.HashMap;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    private final TagRepository tagRepository;

    @Transactional(readOnly = true)
    public Category findById(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException("해당 id를 가진 Category 을 찾을 수 없습니다. id = "+ categoryId ));
    }

    public List<Category> findAllOrderByPriority() {
        return categoryRepository.findAllByOrderByPriorityAsc();
    }

    @Transactional(readOnly = true)
    public List<Category> findByMainCategory(MainCategory mainCategory) {
        return categoryRepository.findByMainCategoryOrderByPriorityAsc(mainCategory);
    }

    @Transactional(readOnly = true)
    public HashMap<Long, HashMap<Category, List<Tag>>> getCategoryMap(List<MainCategory> mainCategoryList) {
        HashMap<Long, HashMap<Category, List<Tag>>> categoryMap = new HashMap<>();
        for(MainCategory mainCategory : mainCategoryList) {
            HashMap<Category, List<Tag>> tagMap = new HashMap<>();
            List<Category> categoryList = findByMainCategory(mainCategory);
            for (Category category : categoryList) {
                Long categoryId = category.getId();
                List<Tag> tagList = tagRepository.findCategoryTag(categoryId);
                tagMap.put(category, tagList);
            }
            categoryMap.put(mainCategory.getId(), tagMap);
        }
        return categoryMap;
    }
}