package com.yapp.memeserver.domain.meme.service;

import com.yapp.memeserver.domain.meme.domain.Category;
import com.yapp.memeserver.domain.meme.domain.Meme;
import com.yapp.memeserver.domain.meme.domain.MemeTag;
import com.yapp.memeserver.domain.meme.domain.Tag;
import com.yapp.memeserver.domain.meme.repository.MemeRepository;
import com.yapp.memeserver.domain.meme.repository.MemeTagRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MemeTagServiceTest {

    @Mock
    private MemeTagRepository memeTagRepository;

    @InjectMocks
    private MemeTagService memeTagService;

    private Meme meme1;
    private Meme meme2;
    private Meme meme3;
    private Tag tag1;
    private Tag tag2;
    private MemeTag memeTag1;
    private MemeTag memeTag2;
    private MemeTag memeTag3;
    private MemeTag memeTag4;

    @BeforeEach
    public void setup() {
        tag1 = createTag("Tag 1");
        tag2 = createTag("Tag 2");
        meme1 = createMeme("Meme 1");
        meme2 = createMeme("Meme 2");
        meme3 = createMeme("Meme 3");

        memeTag1 = createMemeTag(meme1, tag1);
        memeTag2 = createMemeTag(meme2, tag1);
        memeTag3 = createMemeTag(meme2, tag2);
        memeTag4 = createMemeTag(meme3, tag1);
    }



    @Test
    public void testFindByMeme() {
        List<MemeTag> expected = Arrays.asList(memeTag2, memeTag3);

        when(memeTagRepository.findByMeme(meme2)).thenReturn(expected);

        List<MemeTag> actual = memeTagService.findByMeme(meme2);

        assertEquals(expected, actual);
        verify(memeTagRepository, times(1)).findByMeme(meme2);

    }

    @Test
    public void testFindByTagPaging() {
        Pageable pageable = Pageable.unpaged();

        Page<MemeTag> expected = new PageImpl<>(Arrays.asList(memeTag1, memeTag2, memeTag4));

        when(memeTagRepository.findByTag(tag1, pageable)).thenReturn(expected);

        Page<MemeTag> actual = memeTagService.findByTagPaging(tag1, pageable);

        assertEquals(expected, actual);
        verify(memeTagRepository, times(1)).findByTag(tag1, pageable);
    }

    @Test
    public void testFindByRelTagPaging() {
        Pageable pageable = Pageable.unpaged();
        Long memeId = 1L;
        Long tagId = 1L;
        ReflectionTestUtils.setField(meme1, "id", memeId);
        ReflectionTestUtils.setField(tag1, "id", tagId);
        Page<MemeTag> expected = new PageImpl<>(Arrays.asList(memeTag1));

        when(memeTagRepository.findByRelTag(memeId, Arrays.asList(tag1.getId()), pageable)).thenReturn(expected);

        Page<MemeTag> actual = memeTagService.findByRelTagPaging(memeId, Arrays.asList(tag1.getId()), pageable);

        assertEquals(expected, actual);
    }

    @Test
    public void testFindTagMemeList() {
        List<MemeTag> memeTagList = Arrays.asList(memeTag2, memeTag3);
        List<Tag> expected = Arrays.asList(tag1, tag2);

        List<Tag> actual = memeTagService.findTagMemeList(memeTagList);

        assertEquals(expected, actual);
    }
    @Test
    public void testFindTagIdList() {
        Long tag1Id = 1L;
        Long tag2Id = 2L;
        ReflectionTestUtils.setField(tag1, "id", tag1Id);
        ReflectionTestUtils.setField(tag2, "id", tag2Id);
        List<MemeTag> memeTagList = Arrays.asList(memeTag2, memeTag3);
        List<Long> expected = Arrays.asList(tag1.getId(), tag2.getId());

        List<Long> actual = memeTagService.findTagIdList(memeTagList);

        assertEquals(expected, actual);
    }

    @Test
    public void testFindMemeTagList() {
        Page<MemeTag> memeTagPage = new PageImpl<>(Arrays.asList(memeTag1, memeTag2));
        List<Meme> expected = Arrays.asList(meme1, meme2);

        List<Meme> actual = memeTagService.findMemeTagList(memeTagPage);

        assertEquals(expected, actual);
    }


    private Tag createTag(String name) {
        return Tag.builder()
                .name(name)
                .build();
    }

    private Meme createMeme(String name) {
        return Meme.builder()
                .name(name)
                .build();
    }

    private MemeTag createMemeTag(Meme meme, Tag tag) {
        return MemeTag.builder()
                .meme(meme)
                .tag(tag)
                .build();
    }

}
