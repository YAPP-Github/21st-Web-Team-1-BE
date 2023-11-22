package com.yapp.memeserver.domain.meme.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.yapp.memeserver.domain.meme.domain.Image;
import com.yapp.memeserver.domain.meme.domain.Meme;
import com.yapp.memeserver.domain.meme.domain.MemeTag;
import com.yapp.memeserver.domain.meme.domain.Tag;
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

    private final AmazonS3Client amazonS3Client;

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

    public Image saveImage(String imageUrl, Meme meme, Integer width, Integer height, String ahash, String phash, String dhash, Integer priority) {
        Image image = Image.builder()
                .imageUrl(imageUrl)
                .meme(meme)
                .width(width)
                .height(height)
                .ahash(ahash)
                .phash(phash)
                .dhash(dhash)
                .priority(priority)
                .build();
        meme.addImage(image);
        return imageRepository.save(image);
    }

    private String changedImageName(String originName) { //이미지 이름 중복 방지를 위해 랜덤으로 생성
        String random = UUID.randomUUID().toString();
        return random+originName;
    }

    public void uploadImage(Meme meme, List<MultipartFile> multipartFileList) throws IOException {

        int index = 1;
        for(MultipartFile multipartFile: multipartFileList) {
            validateFileExists(multipartFile);

            String originalName = multipartFile.getOriginalFilename(); // 파일 이름
            System.out.println("originalName = " + originalName);
//            String changedName = changedImageName(originalName); //새로 생성된 이미지 이름

            ObjectMetadata objectMetaData = new ObjectMetadata();
            Long size = multipartFile.getSize(); // 파일 크기
            System.out.println("size = " + size);
            String ext = originalName.substring(originalName.lastIndexOf(".")); //확장자
            objectMetaData.setContentType("image/"+ext);
            objectMetaData.setContentLength(size);

            ImageIO.setUseCache(false);
            BufferedImage bufferedImage = ImageIO.read(new File(originalName));
            Integer width = bufferedImage.getWidth();
            Integer height = bufferedImage.getHeight();

//            File file = new File(originalName);
//            multipartFile.transferTo(file);

//            BufferedImage bufferedImage = ImageIO.read(file);
//            bufferedImage.
//            Integer width = bufferedImage.getWidth();
//            Integer height = bufferedImage.getHeight();

            // S3에 업로드
            amazonS3Client.putObject(
                    new PutObjectRequest(S3Bucket, originalName, multipartFile.getInputStream(), objectMetaData)
                            .withCannedAcl(CannedAccessControlList.PublicRead)
            );

            String imagePath = amazonS3Client.getUrl(S3Bucket, originalName).toString(); // 접근가능한 URL 가져오기
            saveImage(imagePath, meme, width, height, null, null, null, index);
            index++;
        }
    }

    private void validateFileExists(MultipartFile multipartFile) {
        if (multipartFile.isEmpty()) {
            throw new IllegalArgumentException("file is empty");
        }
    }
}
