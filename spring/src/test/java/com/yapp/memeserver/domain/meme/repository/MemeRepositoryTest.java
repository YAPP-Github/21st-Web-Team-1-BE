package com.yapp.memeserver.domain.meme.repository;

import com.yapp.memeserver.domain.meme.domain.Meme;
import com.yapp.memeserver.global.test.RepositoryTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class MemeRepositoryTest extends RepositoryTest {

    @Autowired
    private MemeRepository memeRepository;

    private Meme meme1;
    private Meme meme2;
    private Meme meme3;


    @BeforeEach
    public void setUp() {
        meme1 = createMeme("밈1", "https://s3.us-west-2.amazonaws.com/secure.notion-static.com/cad31965-0c62-44ca-ae45-4e050d4ec9b8t");
        meme2 = createMeme("밈2", "https://s3.us-west-2.amazonaws.com/secure.notion-static.com/cad31965-0c62-44ca-ae45-4e050d4ec9b8t");
        meme3 = createMeme("밈3", "https://s3.us-west-2.amazonaws.com/secure.notion-static.com/cad31965-0c62-44ca-ae45-4e050d4ec9b8t");
    }

    @Test
    public void findAllPagingTest() {
        int newViewCount1 = 4;
        int newViewCount2 = 9;
        int newViewCount3 = 1;
        changeViewCount(meme1, newViewCount1);
        changeViewCount(meme2, newViewCount2);
        changeViewCount(meme3, newViewCount3);
        Pageable pageable = PageRequest.of(0, 5, Sort.Direction.DESC, "viewCount");

        Page<Meme> result = memeRepository.findAll(pageable);

        // 총 몇 페이지
        assertThat(result.getTotalPages()).isEqualTo(1);
        // 전체 개수
        assertThat(result.getTotalElements()).isEqualTo(3);
        // 현재 페이지 번호 0부터 시작
        assertThat(result.getNumber()).isEqualTo(0);
        // 페이지당 데이터 개수
        assertThat(result.getSize()).isEqualTo(5);
        // 다음 페이지 존재 여부
        assertThat(result.hasNext()).isEqualTo(false);
        // 시작 페이지(0) 여부
        assertThat(result.isFirst()).isEqualTo(true);

        for (Meme meme : result.getContent()) {
            System.out.println("meme.getViewCount() = " + meme.getViewCount());
        }

    }


    private Meme createMeme(String title, String imageUrl) {
        Meme meme = Meme.builder()
                .title(title)
                .imageUrl(imageUrl)
                .build();
        return memeRepository.save(meme);
    }

    private void changeViewCount(Meme meme, int newViewCount) {
        for (int i = 0; i < newViewCount; i++) {
            meme.updateViewCount();
        }
    }



}