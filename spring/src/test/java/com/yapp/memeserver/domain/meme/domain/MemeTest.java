package com.yapp.memeserver.domain.meme.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MemeTest {

    @Test
    void 밈_생성_테스트() {
        String title = "밈 제목";
        String imageUrl = "https://user-images.githubusercontent.com/62461857/201794604-7f9f6389-1bc9-44e2-8dbb-eb7b1fd3c513.png";

        Meme meme = createMeme(title, imageUrl);

        assertThat(meme.getTitle()).isEqualTo(title);
        assertThat(meme.getImageUrl()).isEqualTo(imageUrl);
        assertThat(meme.getViewCount()).isEqualTo(0);
    }

    @Test
    void 밈_수정_테스트() {
        String title = "밈 제목";
        String imageUrl = "https://user-images.githubusercontent.com/62461857/201794604-7f9f6389-1bc9-44e2-8dbb-eb7b1fd3c513.png";
        String newTitle = "새 밈 제목";
        String newImageUrl = "https://user-images.githubusercontent.com/62461857/201794714-a8e7ed02-f327-4c8b-840f-afce02805759.png";

        Meme meme = createMeme(title, imageUrl);
        meme.updateMeme(newTitle, newImageUrl);

        assertThat(meme.getTitle()).isEqualTo(newTitle);
        assertThat(meme.getImageUrl()).isEqualTo(newImageUrl);
    }

    @Test
    void 밈_조회수_증가_테스트() {
        String title = "밈 제목";
        String imageUrl = "https://user-images.githubusercontent.com/62461857/201794604-7f9f6389-1bc9-44e2-8dbb-eb7b1fd3c513.png";

        Meme meme = createMeme(title, imageUrl);
        for (int i = 0; i < 10; i++) {
            meme.updateViewCount();
        }

        assertThat(meme.getTitle()).isEqualTo(title);
        assertThat(meme.getImageUrl()).isEqualTo(imageUrl);
        assertThat(meme.getViewCount()).isEqualTo(10);
    }

    private Meme createMeme(String title, String imageUrl) {
        return Meme.builder()
                .title(title)
                .imageUrl(imageUrl)
                .build();
    }
}