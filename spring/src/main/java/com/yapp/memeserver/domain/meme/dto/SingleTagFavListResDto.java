package com.yapp.memeserver.domain.meme.dto;

import com.yapp.memeserver.domain.meme.domain.Tag;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SingleTagFavListResDto {

    private List<SingleTagFavDto> tags;

    public static SingleTagFavListResDto of(List<Tag> tagList) {
        return SingleTagFavListResDto.builder()
                .tags(tagList.stream().map(SingleTagFavDto::of).collect(Collectors.toList()))
                .build();
    }
}
