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
        assertThat(meme.getDescription()).isEqualTo(description);
        assertThat(meme.getViewCount()).isEqualTo(0);
        assertThat(meme.getShareCount()).isEqualTo(0);
    }

    @Test
    void 밈_수정_테스트() {
        String name = "밈 이름";
        String description = "밈 설명입니다.";
        String newName = "새 밈 이름";
        String newDescription = "새 밈 설명입니다.";

        Meme meme = createMeme(name, description);
        meme.updateMeme(newName, newDescription);

        assertThat(meme.getName()).isEqualTo(newName);
        assertThat(meme.getDescription()).isEqualTo(newDescription);
    }

    private Meme createMeme(String name, String description) {
        return Meme.builder()
                .name(name)
                .description(description)
                .build();
    }
}