package com.yapp.memeserver.domain.meme.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CategoryTest {

    @Test
    void 카테고리_생성_테스트() {
        String name = "카테고리명";

        Category category = createCategory(name);

        assertThat(category.getName()).isEqualTo(name);
    }

    @Test
    void 카테고리_수정_테스트() {
        String name = "카테고리명";
        String newName = "새 카테고리명";

        Category category = createCategory(name);
        category.updateCategory(newName);

        assertThat(category.getName()).isEqualTo(newName);
    }

    @Test
    void 카테고리_우선순위_변경_테스트() {
        String name = "카테고리명";
        Integer priority = 101;

        Category category = createCategory(name);
        category.updatePriority(priority);

        assertThat(category.getPriority()).isEqualTo(priority);
    }

    private Category createCategory(String name) {
        return Category.builder()
                .name(name)
                .build();
    }
}