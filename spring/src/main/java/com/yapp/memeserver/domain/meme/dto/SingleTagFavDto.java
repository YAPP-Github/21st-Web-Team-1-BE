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
public class SingleTagFavDto {

    private Long tagId;
    private String name;
    private Boolean isFav;

    public SingleTagFavDto(Tag tag, Boolean isFav) {
        this.tagId = tag.getId();
        this.name = tag.getName();
        this.isFav = isFav;
    }

    public static SingleTagFavDto of(Tag tag) {
        return new SingleTagFavDto(tag, true);
    }

    public static SingleTagFavDto checkFav(Tag tag, List<Long> favTagIdList) {
        if (favTagIdList.contains(tag.getId())) {
            return new SingleTagFavDto(tag, true);
        }
        return new SingleTagFavDto(tag, false);
    }

    public void setFav(boolean isFav) {
        this.isFav = isFav;
    }
}