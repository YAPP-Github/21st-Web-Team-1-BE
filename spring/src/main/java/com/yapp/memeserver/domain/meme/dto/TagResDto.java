package com.yapp.memeserver.domain.meme.dto;

import com.yapp.memeserver.domain.meme.domain.Tag;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TagResDto {

    private Long tagId;
    private String name;
    private Integer viewCount;
    private Long categoryId;
    private String categoryName;
    private Boolean isFav;


    public static TagResDto of(Tag tag) {
        return TagResDto.builder()
                .tagId(tag.getId())
                .name(tag.getName())
                .viewCount(tag.getViewCount())
                .categoryId(tag.getCategory().getId())
                .categoryName(tag.getCategory().getName())
                .isFav(false)
                .build();
    }

    public void setFav(boolean isFav) {
        this.isFav = isFav;
    }

}
