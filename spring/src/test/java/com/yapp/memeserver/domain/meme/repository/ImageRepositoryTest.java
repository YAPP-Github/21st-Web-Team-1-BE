package com.yapp.memeserver.domain.meme.repository;

import com.yapp.memeserver.domain.meme.domain.Image;
import com.yapp.memeserver.domain.meme.domain.Meme;
import com.yapp.memeserver.domain.meme.domain.MemeTag;
import com.yapp.memeserver.domain.meme.domain.Tag;
import com.yapp.memeserver.global.test.RepositoryTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ImageRepositoryTest extends RepositoryTest {

    @Autowired
    ImageRepository imageRepository;
    @Autowired
    MemeRepository memeRepository;

    private Meme meme1;
    private Meme meme2;
    private Image image1;
    private Image image2;
    private Image image3;
    private Image image4;


    @BeforeEach
    public void setUp() {
        meme1 = createMeme("밈1");
        meme2 = createMeme("밈2");
        image1 = createImage("https://yappmemebucket.s3.ap-northeast-2.amazonaws.com/memes/images/1.png", 960, 987, meme1);
        image2 = createImage("https://yappmemebucket.s3.ap-northeast-2.amazonaws.com/memes/images/2.png", 517, 706, meme2);
        image3 = createImage("https://yappmemebucket.s3.ap-northeast-2.amazonaws.com/memes/images/3.png", 466, 631, meme2);
        image4 = createImage("https://yappmemebucket.s3.ap-northeast-2.amazonaws.com/memes/images/4.png", 488, 566, meme2);
    }

    @Test
    public void saveTest() {
        // When
        imageRepository.save(image1);

        // Then
        assertThat(image1.getId()).isNotNull();
        assertThat(image1.getImageUrl()).isEqualTo("https://yappmemebucket.s3.ap-northeast-2.amazonaws.com/memes/images/1.png");
        assertThat(image1.getWidth()).isEqualTo(960);
        assertThat(image1.getHeight()).isEqualTo(987);
        assertThat(image1.getMeme()).isEqualTo(meme1);
    }

    @Test
    public void findByMemeTest() {
        //Given
        List<Image> imageList = Arrays.asList(image2, image3, image4);

        // When
        List<Image> result = imageRepository.findByMeme(meme2);

        // Then
        assertThat(result.size()).isEqualTo(3);
        assertThat(result).isEqualTo(imageList);
    }

    @Test
    public void countTest() {
        // When
        long count = imageRepository.count();

        // Then
        assertThat(count).isEqualTo(4);
    }


    private Meme createMeme(String name) {
        Meme meme = Meme.builder()
                .name(name)
                .build();
        return memeRepository.save(meme);
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
}
