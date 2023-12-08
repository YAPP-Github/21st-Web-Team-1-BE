package com.yapp.memeserver.domain.meme.dto;

import com.yapp.memeserver.domain.meme.domain.Tag;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SimpleTagDto {
    private Long tagId;
    private String name;

    public SimpleTagDto(Tag tag) {
        this.tagId = tag.getId();
        this.name = tag.getName();
    }
    public static SimpleTagDto of(Tag tag) {
        return new SimpleTagDto(tag);
    }
}