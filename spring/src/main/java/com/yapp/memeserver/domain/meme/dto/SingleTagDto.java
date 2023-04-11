package com.yapp.memeserver.domain.meme.dto;

import com.yapp.memeserver.domain.meme.domain.Tag;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SingleTagDto {

    private Long tagId;
    private String name;
    private Boolean isFav;

    public SingleTagDto(Tag tag, Boolean isFav) {
        this.tagId = tag.getId();
        this.name = tag.getName();
        this.isFav = isFav;
    }

    public static SingleTagDto of(Tag tag) {
        return new SingleTagDto(tag, true);
    }

    public static SingleTagDto checkFav(Tag tag, List<Long> favTagIdList) {
        if (favTagIdList.contains(tag.getId())) {
            return new SingleTagDto(tag, true);
        }
        return new SingleTagDto(tag, false);
    }

    public void setFav(boolean isFav) {
        this.isFav = isFav;
    }
}