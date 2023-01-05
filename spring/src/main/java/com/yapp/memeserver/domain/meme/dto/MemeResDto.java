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
    private Integer viewCount;
    private Integer shareCount;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    public static MemeResDto of(Meme meme) {
        return MemeResDto.builder()
                .memeId(meme.getId())
                .name(meme.getName())
                .description(meme.getDescription())
                .viewCount(meme.getViewCount())
                .shareCount(meme.getShareCount())
                .createdDate(meme.getCreatedDate())
                .modifiedDate(meme.getModifiedDate())
                .build();
    }

}