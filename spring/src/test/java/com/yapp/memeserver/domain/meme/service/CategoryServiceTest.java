package com.yapp.memeserver.domain.meme.service;

import com.yapp.memeserver.domain.meme.domain.Category;
import com.yapp.memeserver.domain.meme.repository.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import javax.persistence.EntityNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    @Test
    void findById_shouldReturnCategory_whenCategoryExists() {
        // Given
        Long categoryId = 1L;
        Category category = createCategory("Category 1");
        ReflectionTestUtils.setField(category, "id", categoryId);

        // Mock
        given(categoryRepository.findById(categoryId)).willReturn(Optional.ofNullable(category));

        // When
        Category findCategory = categoryService.findById(categoryId);

        // Then
        assertThat(findCategory).isNotNull();
        assertThat(findCategory).isEqualTo(category);
        verify(categoryRepository, times(1)).findById(categoryId);
    }

    @Test
    void findById_shouldThrowEntityNotFoundException_whenCategoryDoesNotExist() {
        // Given
        Long categoryId = 1L;
        Long nonExistingCategoryId = 2L;
        Category category = createCategory("Category 1");
        ReflectionTestUtils.setField(category, "id", categoryId);

        // Mock
        given(categoryRepository.findById(nonExistingCategoryId)).willReturn(Optional.empty());

        // When, Then
        assertThrows(EntityNotFoundException.class, () -> {
            categoryService.findById(nonExistingCategoryId);
        });
        verify(categoryRepository, times(1)).findById(nonExistingCategoryId);
    }

    @Test
    void findAllOrderByPriority_shouldReturnCategoriesSortedByPriority() {
        // Given
        Category category1 = createCategory("Category 1");
        category1.updatePriority(2);
        Category category2 = createCategory("Category 2");
        category2.updatePriority(1);
        Category category3 = createCategory("Category 3");
        category3.updatePriority(3);
        List<Category> expectedCategoryList = Arrays.asList(category2, category1, category3);

        // Mock
        given(categoryRepository.findAllByOrderByPriorityAsc()).willReturn(expectedCategoryList);

        // When
        List<Category> sortedCategoryList = categoryService.findAllOrderByPriority();

        // Then
        assertThat(sortedCategoryList).containsExactlyElementsOf(expectedCategoryList); // 목록에 예상 목록과 동일한 순서로 동일한 요소가 있는지 확인
        verify(categoryRepository, times(1)).findAllByOrderByPriorityAsc();
    }

    public Category createCategory(String name) {
        return Category.builder()
                .name(name)
                .build();
    }
}