package com.yapp.memeserver.domain.meme.dto;

import com.yapp.memeserver.domain.meme.domain.Meme;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MemeResDto {

    private Long memeId;
    private String name;
    private String description;
    private String imageUrl;
    private Integer viewCount;
    private LocalDateTime createDate;
    private LocalDateTime modifiedDate;

    public static MemeResDto of(Meme meme) {
        return MemeResDto.builder()
                .memeId(meme.getId())
                .name(meme.getName())
                .description(meme.getDescription())
                .imageUrl(meme.getImageUrl())
                .viewCount(meme.getViewCount())
                .createDate(meme.getCreateDate())
                .modifiedDate(meme.getModifiedDate())
                .build();
    }

}