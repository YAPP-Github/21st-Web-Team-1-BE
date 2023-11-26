package com.yapp.memeserver.domain.meme.dto;

import com.yapp.memeserver.domain.meme.domain.Image;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ImageResDto {

    private Long imageId;
    private String imageUrl;
    private Integer width;
    private Integer height;

    public ImageResDto(Image image) {
        this.imageId = image.getId();
        this.imageUrl = image.getImageUrl();
        this.width = image.getWidth();
        this.height = image.getHeight();
    }

    public static ImageResDto of(Image image) {
        return new ImageResDto(image);
    }
}
