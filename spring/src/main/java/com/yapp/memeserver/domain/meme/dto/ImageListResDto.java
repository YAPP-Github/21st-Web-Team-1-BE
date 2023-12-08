package com.yapp.memeserver.domain.meme.dto;

import com.yapp.memeserver.domain.meme.domain.Category;
import com.yapp.memeserver.domain.meme.domain.Image;
import com.yapp.memeserver.domain.meme.domain.Meme;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;


@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ImageListResDto {

    private List<SingleImage> images;
    private Integer count;

    @Getter
    public static class SingleImage {
        private Long imageId;
        private String imageUrl;
        private Integer imageWidth;
        private Integer imageHeight;

        public SingleImage(Image image) {
            this.imageId = image.getId();
            this.imageUrl = image.getImageUrl();
            this.imageWidth = image.getWidth();
            this.imageHeight = image.getHeight();
        }
        
        public static SingleImage of(Image image) {
            return new SingleImage(image);
        }
    }
    public static ImageListResDto of(List<Image> imageList) {
        return ImageListResDto.builder()
                .images(imageList.stream().map(SingleImage::of).collect(Collectors.toList()))
                .count(imageList.size())
                .build();
    }

}
