package com.yapp.memeserver.domain.meme.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class MainCategoryTest {

    @Test
    void 메인카테고리_생성_테스트() {
        String name = "메인카테고리명";
        String icon = "https://raw.githubusercontent.com/toss/tossface/cec7ea0420b7f17d6f546fd7359da9bd4cb3315c/dist/svg/u1F60E.svg";

        MainCategory mainCategory = createMainCategory(name, icon);

        assertThat(mainCategory.getName()).isEqualTo(name);
        assertThat(mainCategory.getIcon()).isEqualTo(icon);
    }

    @Test
    void 메인카테고리_수정_테스트() {
        String name = "메인카테고리명";
        String icon = "https://raw.githubusercontent.com/toss/tossface/cec7ea0420b7f17d6f546fd7359da9bd4cb3315c/dist/svg/u1F60E.svg";

        String newName = "새 메인카테고리명";
        String newIcon = "https://raw.githubusercontent.com/toss/tossface/cec7ea0420b7f17d6f546fd7359da9bd4cb3315c/dist/svg/u1FAF5.svg";


        MainCategory mainCategory = createMainCategory(name, icon);
        mainCategory.updateMainCategory(newName, newIcon);

        assertThat(mainCategory.getName()).isEqualTo(newName);
        assertThat(mainCategory.getIcon()).isEqualTo(newIcon);
    }

    @Test
    void 메인카테고리_우선순위_변경_테스트() {
        String name = "메인카테고리명";
        String icon = "https://raw.githubusercontent.com/toss/tossface/cec7ea0420b7f17d6f546fd7359da9bd4cb3315c/dist/svg/u1F60E.svg";
        Integer priority = 101;

        MainCategory mainCategory = createMainCategory(name, icon);
        mainCategory.updatePriority(priority);

        assertThat(mainCategory.getPriority()).isEqualTo(priority);
    }

    @Test
    void 메인카테고리_하위카테고리유무_변경_테스트() {
        String name = "메인카테고리명";
        String icon = "https://raw.githubusercontent.com/toss/tossface/cec7ea0420b7f17d6f546fd7359da9bd4cb3315c/dist/svg/u1F60E.svg";
        Boolean hasSub = true;

        MainCategory mainCategory = createMainCategory(name, icon);
        mainCategory.updateHasSub(hasSub);

        assertThat(mainCategory.getHasSub()).isEqualTo(hasSub);
    }

    private MainCategory createMainCategory(String name, String icon) {
        return MainCategory.builder()
                .name(name)
                .icon(icon)
                .build();
    }
}
