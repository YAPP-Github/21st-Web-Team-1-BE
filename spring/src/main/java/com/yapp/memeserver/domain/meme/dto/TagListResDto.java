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
public class TagListResDto {

    private List<TagResDto> tags;

    public static TagListResDto of(List<Tag> tagList) {
        return TagListResDto.builder()
                .tags(tagList.stream().map(TagResDto::of).collect(Collectors.toList()))
                .build();
    }
}
