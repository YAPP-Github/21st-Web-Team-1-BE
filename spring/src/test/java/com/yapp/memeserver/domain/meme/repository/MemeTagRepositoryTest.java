package com.yapp.memeserver.domain.meme.repository;

import com.yapp.memeserver.domain.meme.domain.Category;
import com.yapp.memeserver.domain.meme.domain.Meme;
import com.yapp.memeserver.domain.meme.domain.MemeTag;
import com.yapp.memeserver.domain.meme.domain.Tag;
import com.yapp.memeserver.global.test.RepositoryTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.annotation.DirtiesContext.MethodMode.BEFORE_METHOD;

class MemeTagRepositoryTest extends RepositoryTest {


    @Autowired
    private MemeTagRepository memeTagRepository;
    @Autowired
    private MemeRepository memeRepository;
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private CategoryRepository categoryRepository;


    private Meme meme1;
    private Meme meme2;
    private Meme meme3;
    private Tag tag1;
    private Tag tag2;
    private Tag tag3;
    private MemeTag memeTag1;
    private MemeTag memeTag2;
    private MemeTag memeTag3;
    private MemeTag memeTag4;
    private MemeTag memeTag5;
    private MemeTag memeTag6;


    @BeforeEach
    public void setUp() {
        meme1 = createMeme("밈1");
        meme2 = createMeme("밈2");
        meme3 = createMeme("밈3");
        tag1 = createTag("태그1");
        tag2 = createTag("태그2");
        tag3 = createTag("태그3");
        memeTag1 = createMemeTag(meme1, tag1);
        memeTag2 = createMemeTag(meme1, tag3);
        memeTag3 = createMemeTag(meme2, tag1);
        memeTag4 = createMemeTag(meme3, tag1);
        memeTag5 = createMemeTag(meme3, tag2);
        memeTag6 = createMemeTag(meme3, tag3);
    }

    @Test
    public void saveTest() {
        // When
        memeTagRepository.save(memeTag1);

        // Then
        assertThat(memeTag1.getId()).isNotNull();
        assertThat(memeTag1.getMeme()).isEqualTo(meme1);
        assertThat(memeTag1.getTag()).isEqualTo(tag1);
    }

    @DirtiesContext(methodMode = BEFORE_METHOD) // 특정 케이스를 시작하기 전에 context 재생성
    @Test
    public void findByTagTestPaging() {
        // Given
        Pageable pageable = PageRequest.of(0, 2, Sort.Direction.DESC, "id");

        // When
        Page<MemeTag> result = memeTagRepository.findByTag(tag1, pageable);

        // 총 몇 페이지
        assertThat(result.getTotalPages()).isEqualTo(2);
        // 전체 개수
        assertThat(result.getTotalElements()).isEqualTo(3);
        // 현재 페이지 번호 0부터 시작
        assertThat(result.getNumber()).isEqualTo(0);
        // 페이지당 데이터 개수
        assertThat(result.getSize()).isEqualTo(2);
        // 다음 페이지 존재 여부
        assertThat(result.hasNext()).isEqualTo(true);
        // 시작 페이지(0) 여부
        assertThat(result.isFirst()).isEqualTo(true);
        assertThat(result.getContent().get(0)).isEqualTo(memeTag4);
        assertThat(result.getContent().get(1)).isEqualTo(memeTag3);
    }

    @Test
    public void findByMemeTestDoes() {
        // Given
        List<MemeTag> memeTagList = Arrays.asList(memeTag1, memeTag2);

        // When
        List<MemeTag> result = memeTagRepository.findByMeme(meme1);

        // Then
        assertThat(result.size()).isEqualTo(2);
        assertThat(result).isEqualTo(memeTagList);
    }

    private Meme createMeme(String name) {
        Meme meme = Meme.builder()
                .name(name)
                .build();
        return memeRepository.save(meme);
    }

    private Category createCategory(String name) {
        Category category = Category.builder()
                .name(name)
                .build();
        return categoryRepository.save(category);
    }

    private Tag createTag(String name) {
        Tag tag = Tag.builder()
                .category(createCategory("카테고리1"))
                .name(name)
                .build();
        return tagRepository.save(tag);
    }

    private MemeTag createMemeTag(Meme meme, Tag tag) {
        MemeTag memeTag = MemeTag.builder()
                .meme(meme)
                .tag(tag)
                .build();
        return memeTagRepository.save(memeTag);
    }
}