package com.yapp.memeserver.domain.meme.service;

import com.yapp.memeserver.domain.meme.domain.Image;
import com.yapp.memeserver.domain.meme.domain.Meme;
import com.yapp.memeserver.domain.meme.repository.ImageRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ImageServiceTest {

    @Mock
    private ImageRepository imageRepository;

    @InjectMocks
    private ImageService imageService;

    @Test
    void testFindByMeme() {
        // Given
        Meme meme = createMeme("Meme 1");
        Image image1 = createImage("https://example.com/image1.jpg", 640, 480, meme);
        Image image2 = createImage("https://example.com/image2.jpg", 800, 600, meme);
        List<Image> expectedImageList = Arrays.asList(image1, image2);

        // Mock
        given(imageRepository.findByMeme(any(Meme.class))).willReturn(expectedImageList);

        // When
        List<Image> actualImageList = imageService.findByMeme(meme);

        // Then
        assertThat(actualImageList).containsExactlyElementsOf(expectedImageList);
    }

    @Test
    void testGetRandomImageUrl_NoImagesFound() {
        // Given
        List<Image> expectedImageList = Collections.emptyList();

        // Mock
        given(imageRepository.count()).willReturn((long) expectedImageList.size());

        // When, Then
        assertThrows(RuntimeException.class, () -> imageService.getRandomImageUrl());
    }

    @Test
    void testGetRandomImageUrl() {
        // Given
        Image image1 = createImage("https://example.com/image1.jpg", 640, 480, createMeme("Meme 1"));
        Image image2 = createImage("https://example.com/image2.jpg", 800, 600, createMeme("Meme 1"));
        List<Image> expectedImageList = Arrays.asList(image1, image2);

        // Mock
        given(imageRepository.count()).willReturn((long) expectedImageList.size());
        given(imageRepository.findAll(any(Pageable.class))).willAnswer(invocation -> {
            Pageable pageable = invocation.getArgument(0);
            int pageSize = pageable.getPageSize();
            int currentPage = pageable.getPageNumber();
            int startItem = currentPage * pageSize;
            List<Image> list;

            if (expectedImageList.size() < startItem) {
                list = Collections.emptyList();
            } else {
                int toIndex = Math.min(startItem + pageSize, expectedImageList.size());
                list = expectedImageList.subList(startItem, toIndex);
            }

            return new PageImpl<>(list, pageable, expectedImageList.size());
        });
        given(imageRepository.save(any(Image.class))).willReturn(image1);

        // When
        String actualImageUrl = imageService.getRandomImageUrl();

        // Then
        assertThat(actualImageUrl).isNotBlank();
    }

    private Image createImage(String imageUrl, Integer width, Integer height, Meme meme) {
        Image image = Image.builder()
                .imageUrl(imageUrl)
                .width(width)
                .height(height)
                .meme(meme)
                .build();
        return imageRepository.save(image);
    }
    private Meme createMeme(String name) {
        return Meme.builder()
                .name(name)
                .build();
    }
}