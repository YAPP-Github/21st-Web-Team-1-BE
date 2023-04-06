package com.yapp.memeserver.domain.meme.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TagTest {

    @Test
    void 태그_생성_테스트() {
        String name = "태그명";

        Tag tag = createTag(name);

        assertThat(tag.getName()).isEqualTo(name);
    }

    @Test
    void 태그_수정_테스트() {
        String name = "태그명";
        String newName = "새 태그명";

        Tag tag = createTag(name);
        tag.updateTag(newName);

        assertThat(tag.getName()).isEqualTo(newName);
    }

    @Test
    void 태그_이미지_테스트() {
        String name = "태그명";
        String imageUrl = "https://yappmemebucket.s3.ap-northeast-2.amazonaws.com/memes/images/1.png";

        Tag tag = createTag(name);
        tag.updateImageUrl(imageUrl);

        assertThat(tag.getImageUrl()).isEqualTo(imageUrl);
    }

    private Tag createTag(String name) {
        return Tag.builder()
                .name(name)
                .build();
    }
}