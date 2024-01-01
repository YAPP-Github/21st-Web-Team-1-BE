package com.yapp.memeserver.domain.meme.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.yapp.memeserver.domain.meme.domain.Image;
import com.yapp.memeserver.domain.meme.domain.Meme;
import com.yapp.memeserver.domain.meme.domain.MemeTag;
import com.yapp.memeserver.domain.meme.domain.Tag;
import com.yapp.memeserver.domain.meme.dto.MemeCreateReqDto;
import com.yapp.memeserver.domain.meme.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.persistence.EntityNotFoundException;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ImageService {

    @Value("${cloud.aws.s3.bucket}")
    private String S3Bucket;

//    private final AmazonS3Client amazonS3Client;

    private final ImageRepository imageRepository;

    @Transactional(readOnly = true)
    public Image findById(Long imageId) {
        return imageRepository.findById(imageId)
                .orElseThrow(() -> new EntityNotFoundException("해당 id를 가진 Image 을 찾을 수 없습니다. id = "+ imageId ));
    }

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

    private void validateFileExists(MultipartFile multipartFile) {
        if (multipartFile.isEmpty()) {
            throw new IllegalArgumentException("file is empty");
        }
    }

    public void setMemeImage(Meme meme, List<MemeCreateReqDto.SingleImage> images) {
        for (MemeCreateReqDto.SingleImage imageDto : images) {
            Image image = findById(imageDto.getImageId());
            meme.addImage(image, imageDto.getPriority());
            imageRepository.save(image);
        }
    }
}
