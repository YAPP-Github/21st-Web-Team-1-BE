package com.yapp.memeserver.domain.meme.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ImageTest {

    @Test
    void 이미지_생성_테스트() {
        String name = "밈 제목";
        String imageUrl = "https://yappmemebucket.s3.ap-northeast-2.amazonaws.com/memes/images/1.png";
        Integer width = 960;
        Integer height = 987;

        Meme meme = createMeme(name);
        Image image = createImage(imageUrl, width, height, meme);

        assertThat(image.getImageUrl()).isEqualTo(imageUrl);
        assertThat(image.getWidth()).isEqualTo(width);
        assertThat(image.getHeight()).isEqualTo(height);
        assertThat(image.getMeme()).isEqualTo(meme);
    }

    private Image createImage(String imageUrl, Integer width, Integer height, Meme meme) {
        return Image.builder()
                .imageUrl(imageUrl)
                .width(width)
                .height(height)
                .meme(meme)
                .build();
    }

    private Meme createMeme(String name) {
        return Meme.builder()
                .name(name)
                .build();
    }

}
