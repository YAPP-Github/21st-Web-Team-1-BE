package com.yapp.memeserver.domain.meme.service;

import static org.assertj.core.api.Assertions.assertThat;
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

import javax.persistence.EntityNotFoundException;

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
    @DisplayName("Test findById method")
    void testFindById() {
        // Given
        Category category = createCategory("Category 1");
        Tag tag = createTag("Tag 1", category);
        Long tagId = 1L;
        ReflectionTestUtils.setField(tag, "id", tagId);

        // Mock
        given(tagRepository.findById(tagId)).willReturn(Optional.ofNullable(tag));

        // When
        Tag findTag = tagService.findById(tagId);

        // Then
        assertThat(findTag).isNotNull();
        assertThat(findTag.getId()).isEqualTo(tag.getId());
        assertThat(findTag.getName()).isEqualTo(tag.getName());
        assertThat(findTag.getCategory()).isEqualTo(tag.getCategory());
        assertThat(findTag.getViewCount()).isEqualTo(tag.getViewCount());

        // Test with non-existing tag ID
        Long nonExistingTagId = 2L;
        given(tagRepository.findById(nonExistingTagId)).willReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            tagService.findById(nonExistingTagId);
        });
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