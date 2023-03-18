package com.yapp.memeserver.domain.meme.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

import java.util.Optional;


import com.yapp.memeserver.domain.meme.repository.CategoryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.yapp.memeserver.domain.meme.domain.Category;
import com.yapp.memeserver.domain.meme.domain.Tag;
import com.yapp.memeserver.domain.meme.repository.TagRepository;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
public class TagServiceTest {

    @Mock
    private TagRepository tagRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private TagService tagService;

    @Test
    @DisplayName("Find tag by ID")
    public void testFindById() {
        // given
        Category category = createCategory("카테고리1");
        Tag tag = createTag("태그1", category);
        Long tagId = 1L;
        ReflectionTestUtils.setField(tag, "id", tagId);

        // mock
        given(tagRepository.findById(tagId)).willReturn(Optional.ofNullable(tag));

        // when
        Tag findTag = tagService.findById(tagId);

        // then
        assertEquals(tag.getId(), findTag.getId());
        assertEquals(tag.getName(), findTag.getName());
        assertEquals(tag.getCategory(), findTag.getCategory());
        assertEquals(tag.getViewCount(), findTag.getViewCount());
    }

    private Category createCategory(String name) {
        return Category.builder()
                .name(name)
                .build();
    }

    private Tag createTag(String name, Category category) {
        return Tag.builder()
                .category(category)
                .name(name)
                .build();
    }

}