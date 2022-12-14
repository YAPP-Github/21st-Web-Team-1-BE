package com.yapp.memeserver.domain.meme.dto;

import com.yapp.memeserver.domain.meme.domain.Tag;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SingleTagDto {

    private Long tagId;
    private String name;
    private Integer viewCount;

    public SingleTagDto(Tag tag) {
        this.tagId = tag.getId();
        this.name = tag.getName();
        this.viewCount = tag.getViewCount();
    }

    public static SingleTagDto of(Tag tag) {
        return new SingleTagDto(tag);
    }
}