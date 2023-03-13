package com.yapp.memeserver.domain.meme.repository;

import com.yapp.memeserver.domain.meme.domain.Category;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.annotation.DirtiesContext.MethodMode.BEFORE_METHOD;

public class TagRepositoryTest extends RepositoryTest {

    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    private Tag tag1;
    private Tag tag2;
    private Tag tag3;


    @BeforeEach
    public void setUp() {
        tag1 = createTag("태그1");
        tag2 = createTag("태그2");
        tag3 = createTag("태그3");
    }

    @Test
    public void findAllPagingTest() {
        int newViewCount1 = 4;
        int newViewCount2 = 9;
        int newViewCount3 = 1;
        changeViewCount(tag1, newViewCount1);
        changeViewCount(tag2, newViewCount2);
        changeViewCount(tag3, newViewCount3);
        Pageable pageable = PageRequest.of(0, 5, Sort.Direction.DESC, "viewCount");

        Page<Tag> result = tagRepository.findAll(pageable);

        for (Tag tag : result.getContent()) {
            System.out.println("tag.getViewCount() = " + tag.getViewCount());
        }

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

    }

    @DirtiesContext(methodMode = BEFORE_METHOD) // 특정 케이스를 시작하기 전에 context 재생성
    @Test
    void updateViewCountTest() {
        Long tagId = 1L;
        Tag tag = tagRepository.findById(tagId).get();
        tagRepository.increaseViewCount(tagId); // @Query 로 작성하여 영속성 컨텍스트에 반영 안됨
        assertThat(tag.getViewCount()).isEqualTo(0); // 1차 캐시에 남아 있는 객체를 조회하므로 반영 안됨
        assertThat(tagRepository.findById(tagId).get().getViewCount()).isEqualTo(1); // 새로 조회해서 반영 됨
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


    private void changeViewCount(Tag tag, int newViewCount) {
        for (int i = 0; i < newViewCount; i++) {
            tagRepository.increaseViewCount(tag.getId());
        }
    }
}
