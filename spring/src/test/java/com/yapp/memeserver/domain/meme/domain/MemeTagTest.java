package com.yapp.memeserver.domain.meme.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MemeTagTest {
    @Test
    void 밈_생성_테스트() {
        String name = "밈 제목";
        String imageUrl = "https://user-images.githubusercontent.com/62461857/201794604-7f9f6389-1bc9-44e2-8dbb-eb7b1fd3c513.png";
        String tagName = "태그명";

        Meme meme = createMeme(name, imageUrl);
        Tag tag = createTag(tagName);
        MemeTag memeTag = createMemeTag(meme, tag);

        assertThat(memeTag.getMeme()).isEqualTo(meme);
        assertThat(memeTag.getTag()).isEqualTo(tag);
    }

    private MemeTag createMemeTag(Meme meme, Tag tag) {
        return MemeTag.builder()
                .meme(meme)
                .tag(tag)
                .build();
    }

    private Meme createMeme(String name, String imageUrl) {
        return Meme.builder()
                .name(name)
                .imageUrl(imageUrl)
                .build();
    }

    private Tag createTag(String name) {
        return Tag.builder()
                .name(name)
                .build();
    }
}