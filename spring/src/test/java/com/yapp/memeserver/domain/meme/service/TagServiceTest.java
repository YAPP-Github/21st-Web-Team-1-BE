package com.yapp.memeserver.domain.meme.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.transaction.annotation.Transactional;

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
    void findById_shouldReturnTag_whenTagExists() {
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
        verify(tagRepository,times(1)).findById(tagId);
    }

    @Test
    void findById_shouldThrowEntityNotFoundException_whenTagDoesNotExist() {
        // Given
        Category category = createCategory("Category 1");
        Tag tag = createTag("Tag 1", category);
        Long tagId = 1L;
        Long nonExistingTagId = 2L;
        ReflectionTestUtils.setField(tag, "id", tagId);

        // Mock
        given(tagRepository.findById(nonExistingTagId)).willReturn(Optional.empty());

        // When, Then
        assertThrows(EntityNotFoundException.class, () -> {
            tagService.findById(nonExistingTagId);
        });
        verify(tagRepository, times(1)).findById(nonExistingTagId);
    }

    @Test
    void testFindByCategory() {
        // Given
        Category category = createCategory("Category 1");
        Tag tag1 = createTag("Tag 1", category);
        Tag tag2 = createTag("Tag 2", category);
        Long categoryId = 1L;
        ReflectionTestUtils.setField(category, "id", categoryId);
        List<Tag> tagList = new ArrayList<>();
        tagList.add(tag1);
        tagList.add(tag2);

        // Mock
        given(categoryService.findById(categoryId)).willReturn(category);
        given(tagRepository.findByCategory(category)).willReturn(tagList);

        // When
        List<Tag> result = tagService.findByCategory(categoryId);

        // Then
        assertThat(result).isEqualTo(tagList);
        verify(categoryService).findById(categoryId);
        verify(tagRepository).findByCategory(category);
    }

    @Test
    void testFindAllPaging() {
        // Given
        List<Tag> tagList = new ArrayList<>();
        Category category = createCategory("Category 1");
        Tag tag1 = createTag("Tag 1", category);
        Tag tag2 = createTag("Tag 2", category);
        Tag tag3 = createTag("Tag 3", category);
        Tag tag4 = createTag("Tag 4", category);
        tagList.add(tag1);
        tagList.add(tag2);
        tagList.add(tag3);
        Page<Tag> mockTagPage = new PageImpl<>(tagList);

        given(tagRepository.findAll(any(PageRequest.class))).willReturn(mockTagPage);

        // When
        List<Tag> result = tagService.findAllPaging(PageRequest.of(0, 3));

        // Then
        assertThat(result).isEqualTo(tagList);
    }

    @Test
    void findByNameContains_whenWordIsNotEmpty_shouldReturnMatchingTags() {
        // given
        Category category = createCategory("Category 1");
        String word = "funny";
        Tag tag1 = createTag("funny Tag 1", category);
        Tag tag2 = createTag("funny Tag 2", category);
        Tag tag3 = createTag("Tag 3", category);
        List<Tag> tagList = Arrays.asList(tag1, tag2);

        // when
        given(tagRepository.findByNameContainsOrderByViewCountDesc(word)).willReturn(tagList);
        List<Tag> result = new TagService(tagRepository, null).findByNameContains(word);

        // then
        assertThat(result).isEqualTo(tagList);
    }

    @Test
    void findByNameContains_whenWordIsEmpty_shouldReturnEmptyList() {
        // given
        String word = "";

        // when
        List<Tag> result = tagService.findByNameContains(word);

        // then
        assertThat(result).isEmpty();
        verify(tagRepository, never()).findByNameContainsOrderByViewCountDesc(word);
    }

    @Test
    void findByNameContains_whenWordIsNull_shouldReturnEmptyList() {
        // given
        String word = null;

        // when
        List<Tag> result = tagService.findByNameContains(word);

        // then
        assertThat(result).isEmpty();
        verify(tagRepository, never()).findByNameContainsOrderByViewCountDesc(word);
    }

    @Test
    void testRead() {
        // Given
        Long tagId = 1L;
        Category category = createCategory("Category 1");
        Tag tag = createTag("Tag 1", category);
        ReflectionTestUtils.setField(tag, "id", tagId);

        // Mock
        given(tagRepository.existsById(tagId)).willReturn(true);

        // When
        tagService.read(tagId);

        // Then
        verify(tagRepository, times(1)).increaseViewCount(tagId);
    }

    @Test
    void testReadNonExisting() {
        // Given
        Long tagId = 1L;
        Category category = createCategory("Category 1");
        Tag tag = createTag("Tag 1", category);
        ReflectionTestUtils.setField(tag, "id", tagId);
        Long nonExistingTagId = 2L;

        // Mock
        given(tagRepository.existsById(nonExistingTagId)).willReturn(false);

        // When, Then
        assertThrows(EntityNotFoundException.class, () -> {
            tagService.read(nonExistingTagId);
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