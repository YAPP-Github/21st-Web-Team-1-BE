package com.yapp.memeserver.domain.meme.service;

import com.yapp.memeserver.domain.meme.domain.Category;
import com.yapp.memeserver.domain.meme.domain.Meme;
import com.yapp.memeserver.domain.meme.domain.MemeTag;
import com.yapp.memeserver.domain.meme.domain.Tag;
import com.yapp.memeserver.domain.meme.repository.CategoryRepository;
import com.yapp.memeserver.domain.meme.repository.MemeTagRepository;
import com.yapp.memeserver.domain.meme.repository.TagRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;

class MemeTagServiceTest {

    @Mock
    private MemeTagRepository memetagRepository;

    @InjectMocks
    private MemeTagService memetagService;


    private MemeTag createMemeTag(Meme meme, Tag tag) {
        return MemeTag.builder()
                .meme(meme)
                .tag(tag)
                .build();
    }

    private Meme createMeme(String name) {
        return Meme.builder()
                .name(name)
                .build();
    }

    private Category createCategory(String name) {
        return Category.builder()
                .name(name)
                .build();
    }

    private Tag createTag(String name, Category category) {
        return Tag.builder()
                .category(category)
                .name(name)
                .build();
    }
}