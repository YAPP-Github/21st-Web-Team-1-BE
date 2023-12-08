package com.yapp.memeserver.domain.meme.service;

import com.yapp.memeserver.domain.meme.domain.Image;
import com.yapp.memeserver.domain.meme.domain.Meme;
import com.yapp.memeserver.domain.meme.domain.MemeTag;
import com.yapp.memeserver.domain.meme.domain.Tag;
import com.yapp.memeserver.domain.meme.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ImageService {

    private final ImageRepository imageRepository;

    @Transactional(readOnly = true)
    public List<Image> findByMeme(Meme meme) {
        List<Image> imageList = imageRepository.findByMeme(meme);
        return imageList;
    }

    @Transactional(readOnly = true)
    public String getRandomImageUrl() {
        long qty = imageRepository.count();
        if (qty == 0) {
            throw new RuntimeException("이미지가 없습니다.");
        }
        // 가져온 개수 중 랜덤한 하나의 인덱스를 뽑는다.
        int idx = (int)(Math.random() * qty);
        // 페이징하여 하나만 추출해낸다.
        Page<Image> imagePage = imageRepository
                .findAll(PageRequest.of(idx, 1));

        if (!imagePage.hasContent()) {
            throw new RuntimeException("이미지가 없습니다.");
        }
        Image image = imagePage.getContent().get(0);
        if (image == null) {
            throw new RuntimeException("이미지가 없습니다.");
        }
        return image.getImageUrl();
    }

    @Transactional(readOnly = true)
    public List<String> getTagRankImageLists(List<Tag> tagList) {
        List<String> tagRankImageLists = new ArrayList<>();
        for (Tag tag : tagList) {
            List<String> tagRankImageList = imageRepository.findTagRankImageList(tag.getId());
            String newImageUrl = null;
            for (String imageUrl : tagRankImageList) {
                if (tagRankImageLists.contains(imageUrl)) {
                    continue;
                } else {
                    newImageUrl = imageUrl;
                    break;
                }
            }
            if (newImageUrl == null) {
                newImageUrl = tagRankImageList.get(0);
            }
            tagRankImageLists.add(newImageUrl);
        }
        return tagRankImageLists;
    }
}
