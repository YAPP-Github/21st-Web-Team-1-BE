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
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

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
    public HashMap<Category, List<Tag>> getCategoryMap(MainCategory mainCategory) {
            HashMap<Category, List<Tag>> categoryMap = new HashMap<>();
            List<Category> categoryList = findByMainCategory(mainCategory);
            for (Category category : categoryList) {
                Long categoryId = category.getId();
                List<Tag> tagList = tagRepository.findCategoryTag(categoryId);
                categoryMap.put(category, tagList);
            }
        return categoryMap;
    }

    public List<Tag> getCarouselUser(HashMap<Category, List<Tag>> categoryMap) {
        return categoryMap.values().stream()
                .flatMap(List::stream)
                .sorted()
                .collect(Collectors.toList());
    }

    public List<Tag> getCarouselEmotion(HashMap<Category, List<Tag>> categoryMap, int i) {
        return categoryMap.values().stream()
                .flatMap(List::stream)
                .filter(tag -> tag.getName().length() == i)
                .sorted(Comparator.comparing(Tag::getViewCount).reversed())
                .limit(10)
                .collect(Collectors.toList());
    }
}