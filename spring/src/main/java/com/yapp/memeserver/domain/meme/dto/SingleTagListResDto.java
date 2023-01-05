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
public class SingleTagListResDto {

    private List<SingleTagDto> tags;

    public static SingleTagListResDto of(List<Tag> tagList) {
        return SingleTagListResDto.builder()
                .tags(tagList.stream().map(SingleTagDto::of).collect(Collectors.toList()))
                .build();
    }
}
