package com.yapp.memeserver.domain.meme.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CategoryTest {

    @Test
    void 카테고리_생성_테스트() {
        String name = "카테고리명";
        String icon = "https://raw.githubusercontent.com/toss/tossface/cec7ea0420b7f17d6f546fd7359da9bd4cb3315c/dist/svg/u1F60E.svg";

        Category category = createCategory(name, icon);

        assertThat(category.getName()).isEqualTo(name);
        assertThat(category.getIcon()).isEqualTo(icon);
    }

    @Test
    void 카테고리_수정_테스트() {
        String name = "카테고리명";
        String icon = "https://raw.githubusercontent.com/toss/tossface/cec7ea0420b7f17d6f546fd7359da9bd4cb3315c/dist/svg/u1F60E.svg";

        String newName = "새 카테고리명";
        String newIcon = "https://raw.githubusercontent.com/toss/tossface/cec7ea0420b7f17d6f546fd7359da9bd4cb3315c/dist/svg/u1FAF5.svg";


        Category category = createCategory(name, icon);
        category.updateCategory(newName, newIcon);

        assertThat(category.getName()).isEqualTo(newName);
        assertThat(category.getIcon()).isEqualTo(newIcon);
    }
    
    @Test
    void 카테고리_우선순위_변경_테스트() {
        String name = "카테고리명";
        String icon = "https://raw.githubusercontent.com/toss/tossface/cec7ea0420b7f17d6f546fd7359da9bd4cb3315c/dist/svg/u1F60E.svg";
        Integer priority = 101;

        Category category = createCategory(name, icon);
        category.updatePriority(priority);

        assertThat(category.getPriority()).isEqualTo(priority);
    }

    private Category createCategory(String name, String icon) {
        return Category.builder()
                .name(name)
                .icon(icon)
                .build();
    }
}