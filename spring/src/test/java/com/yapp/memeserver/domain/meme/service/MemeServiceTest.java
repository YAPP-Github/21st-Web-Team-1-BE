package com.yapp.memeserver.domain.meme.service;


import com.yapp.memeserver.domain.meme.domain.Category;
import com.yapp.memeserver.domain.meme.domain.Meme;
import com.yapp.memeserver.domain.meme.domain.Tag;
import com.yapp.memeserver.domain.meme.repository.CategoryRepository;
import com.yapp.memeserver.domain.meme.repository.MemeRepository;
import com.yapp.memeserver.domain.meme.repository.TagRepository;
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

import javax.persistence.EntityNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class MemeServiceTest {

    @Mock
    private MemeRepository memeRepository;

    @InjectMocks
    private MemeService memeService;

    @Test
    void findById_shouldReturnMeme_whenMemeExists() {
        // Given
        Long memeId = 1L;
        Meme meme = createMeme("Meme 1");
        ReflectionTestUtils.setField(meme, "id", memeId);

        // Mock
        given(memeRepository.findById(memeId)).willReturn(Optional.ofNullable(meme));

        // When
        Meme findMeme = memeService.findById(memeId);

        // Then
        assertThat(findMeme).isNotNull();
        assertThat(findMeme.getId()).isEqualTo(meme.getId());
        assertThat(findMeme.getName()).isEqualTo(meme.getName());
        assertThat(findMeme.getViewCount()).isEqualTo(meme.getViewCount());
        verify(memeRepository, times(1)).findById(memeId); // 정확히 한번만 호출된 지 확인
    }

    @Test
    void findById_shouldThrowEntityNotFoundException_whenMemeDoesNotExist() {
        // Given
        Long memeId = 1L;
        Long nonExistingMemeId = 2L;
        Meme meme = createMeme("Meme 1");
        ReflectionTestUtils.setField(meme, "id", memeId);

        // Mock
        given(memeRepository.findById(nonExistingMemeId)).willReturn(Optional.empty());

        // When, Then
        assertThrows(EntityNotFoundException.class, () -> {
            memeService.findById(nonExistingMemeId);
        });
        verify(memeRepository, times(1)).findById(nonExistingMemeId);
    }

    @Test
    void findAllPaging_shouldReturnMemeList_whenCalledWithPageable() {
        // Given
        Pageable pageable = PageRequest.of(0, 10);
        List<Meme> memes = Arrays.asList(createMeme("Meme 1"), createMeme("Meme 2"));
        Page<Meme> memePage = new PageImpl<>(memes, pageable, memes.size());

        // Mock
        given(memeRepository.findAll(pageable)).willReturn(memePage);

        // When
        List<Meme> result = memeService.findAllPaging(pageable);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(memes.size());
        assertThat(result.get(0).getName()).isEqualTo(memes.get(0).getName());
        assertThat(result.get(1).getName()).isEqualTo(memes.get(1).getName());
        verify(memeRepository, times(1)).findAll(pageable);
    }

    @Test
    void read_shouldIncreaseViewCount_whenCalledWithMemeId() {
        // Given
        Long memeId = 1L;
        Meme meme = createMeme("Meme 1");
        ReflectionTestUtils.setField(meme, "id", memeId);

        // When
        memeService.read(memeId);

        // Then
        verify(memeRepository, times(1)).increaseViewCount(memeId);
    }

    private Meme createMeme(String name) {
        return Meme.builder()
                .name(name)
                .build();
    }

}
