package com.yapp.memeserver.domain.meme.repository;

import com.yapp.memeserver.domain.meme.domain.Category;
import com.yapp.memeserver.domain.meme.domain.Meme;
import com.yapp.memeserver.domain.meme.domain.Tag;
import com.yapp.memeserver.global.test.RepositoryTest;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

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


    @BeforeEach
    public void setUp() {
        meme1 = createMeme("밈1");
        meme2 = createMeme("밈2");
        meme3 = createMeme("밈3");
        tag1 = createTag("태그1");
        tag2 = createTag("태그2");
        tag3 = createTag("태그3");
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
}