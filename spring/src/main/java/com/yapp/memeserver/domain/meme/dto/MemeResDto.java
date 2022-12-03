package com.yapp.memeserver.domain.meme.dto;

import com.yapp.memeserver.domain.meme.domain.Meme;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MemeResDto {

    private Long memeId;
    private String title;
    private String imageUrl;
    private Integer viewCount;

    public static MemeResDto of(Meme meme) {
        return MemeResDto.builder()
                .memeId(meme.getId())
                .title(meme.getTitle())
                .imageUrl(meme.getImageUrl())
                .viewCount(meme.getViewCount())
                .build();
    }

}