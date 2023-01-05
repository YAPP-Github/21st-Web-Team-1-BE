package com.yapp.memeserver.domain.meme.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MemeTest {

    @Test
    void 밈_생성_테스트() {
        String name = "밈 이름";
        String description = "밈 설명입니다.";

        Meme meme = createMeme(name, description);

        assertThat(meme.getName()).isEqualTo(name);
        assertThat(meme.getViewCount()).isEqualTo(0);
        assertThat(meme.getShareCount()).isEqualTo(0);
    }

    @Test
    void 밈_수정_테스트() {
        String name = "밈 이름";
        String newName = "새 밈 이름";
        String description = "새 밈 설명입니다.";

        Meme meme = createMeme(name, description);
        meme.updateMeme(newName, description);

        assertThat(meme.getName()).isEqualTo(newName);
    }

    private Meme createMeme(String name, String description) {
        return Meme.builder()
                .name(name)
                .description(description)
                .build();
    }
}