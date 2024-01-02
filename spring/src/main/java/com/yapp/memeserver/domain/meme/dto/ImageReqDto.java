package com.yapp.memeserver.domain.meme.dto;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ImageReqDto {

        private String imageUrl;
        private Integer width;
        private Integer height;

}
